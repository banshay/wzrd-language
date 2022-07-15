package com.banshay.language.nodes;

import com.banshay.language.WzrdContext;
import com.banshay.language.nodes.expressions.FunctionBodyNode;
import com.banshay.language.nodes.expressions.LocalVariableWriteNode;
import com.banshay.language.nodes.expressions.ReadArgumentNode;
import com.banshay.language.nodes.statements.WzrdBlockNode;
import com.banshay.language.nodes.toplevel.WzrdExpressionNode;
import com.banshay.language.nodes.toplevel.WzrdStatementNode;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeUtil;
import com.oracle.truffle.api.nodes.NodeVisitor;
import com.oracle.truffle.api.nodes.RootNode;
import java.util.ArrayList;

public class WzrdRootNode extends RootNode {

  private final String name;
  @Child private WzrdExpressionNode bodyNode;

  @CompilerDirectives.CompilationFinal(dimensions = 1)
  private volatile LocalVariableWriteNode[] argumentNodesCache;

  public WzrdRootNode(
      String name,
      TruffleLanguage<WzrdContext> language,
      FrameDescriptor frameDescriptor,
      WzrdExpressionNode bodyNode) {
    super(language, frameDescriptor);
    this.bodyNode = bodyNode;
    this.name = name;
  }

  public WzrdRootNode(
      TruffleLanguage<?> language,
      FrameDescriptor frameDescriptor,
      String name,
      WzrdExpressionNode bodyNode) {
    super(language, frameDescriptor);
    this.bodyNode = bodyNode;
    this.name = name;
  }

  @Override
  public Object execute(VirtualFrame frame) {
    return bodyNode.executeGeneric(frame);
  }

  public LocalVariableWriteNode[] getDeclaredArguments() {
    var argumentNodes = argumentNodesCache;
    if (argumentNodes == null) {
      CompilerDirectives.transferToInterpreterAndInvalidate();
      argumentNodesCache = argumentNodes = findDeclaredArguments();
    }
    return argumentNodes;
  }

  private LocalVariableWriteNode[] findDeclaredArguments() {
    var writeArgNodes = new ArrayList<LocalVariableWriteNode>(5);
    NodeUtil.forEachChild(
        bodyNode,
        new NodeVisitor() {

          private LocalVariableWriteNode writeNode;

          @Override
          public boolean visit(Node node) {
            if (node instanceof LocalVariableWriteNode wn) {
              writeNode = wn;
              var all = NodeUtil.forEachChild(wn, this);
              writeNode = null;
              return all;
            } else if (writeNode != null && node instanceof ReadArgumentNode readNode) {
              writeArgNodes.add(writeNode);
              return true;
            } else if (writeNode == null
                && node instanceof WzrdStatementNode statementNode
                && !(node instanceof WzrdBlockNode || node instanceof FunctionBodyNode)) {
              return false;
            }
            return NodeUtil.forEachChild(node, this);
          }
        });
    return writeArgNodes.toArray(LocalVariableWriteNode[]::new);
  }
}
