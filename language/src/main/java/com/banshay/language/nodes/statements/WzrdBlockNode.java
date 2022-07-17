package com.banshay.language.nodes.statements;

import com.banshay.language.nodes.expressions.LocalVariableWriteNode;
import com.banshay.language.nodes.toplevel.WzrdStatementNode;
import com.banshay.language.types.WzrdNull;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.CompilerDirectives.CompilationFinal;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.BlockNode;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeUtil;
import com.oracle.truffle.api.nodes.NodeVisitor;
import java.util.ArrayList;
import java.util.Arrays;

public final class WzrdBlockNode extends WzrdStatementNode
    implements BlockNode.ElementExecutor<WzrdStatementNode> {

  @Child private BlockNode<WzrdStatementNode> block;

  @CompilationFinal(dimensions = 1)
  private LocalVariableWriteNode[] writeNodesCache;

  @CompilationFinal private int parentBlockIndex = -1;

  public WzrdBlockNode(WzrdStatementNode[] bodyNodes) {
    this.block = bodyNodes.length > 0 ? BlockNode.create(bodyNodes, this) : null;
  }

  @Override
  public Object executeGeneric(VirtualFrame frame) {
    if (this.block != null) {
      return this.block.executeGeneric(frame, BlockNode.NO_ARGUMENT);
    }
    return WzrdNull.INSTANCE;
  }

  @Override
  public Object executeGeneric(VirtualFrame frame, WzrdStatementNode node, int index, int argument) {
    return node.executeGeneric(frame);
  }

  @Override
  public void executeVoid(
      VirtualFrame frame, WzrdStatementNode statementNode, int index, int argument) {
    statementNode.executeGeneric(frame);
  }

  public int getParentBlockIndex() {
    return parentBlockIndex;
  }

  public LocalVariableWriteNode[] getDeclaredLocalVariables() {
    var writeNodes = writeNodesCache;
    if (writeNodes == null) {
      CompilerDirectives.transferToInterpreterAndInvalidate();
      writeNodesCache = writeNodes = findDeclaredLocalVariables();
    }
    return writeNodes;
  }

  private LocalVariableWriteNode[] findDeclaredLocalVariables() {
    if (block == null) {
      return new LocalVariableWriteNode[] {};
    }
    var writeNodes = new ArrayList<LocalVariableWriteNode>(5);
    var varsIndex = new int[] {0};
    NodeUtil.forEachChild(
        block,
        new NodeVisitor() {
          @Override
          public boolean visit(Node node) {
            if (node instanceof WzrdBlockNode blockNode) {
              blockNode.setVisibleVarsIndexStart(varsIndex[0]);
            }
            if (!(node instanceof WzrdBlockNode)) {
              NodeUtil.forEachChild(node, this);
            }
            if (node instanceof LocalVariableWriteNode writeNode) {
              if (writeNode.isDeclaration()) {
                writeNodes.add(writeNode);
                varsIndex[0]++;
              }
            }
            if (node instanceof WzrdBlockNode blockNode) {
              blockNode.setVisibleVarsIndexEnd(varsIndex[0]);
            }
            return true;
          }
        });
    var parentBlock = findBlock();
    LocalVariableWriteNode[] parentVariables = null;
    if (parentBlock instanceof WzrdBlockNode pbl) {
      parentVariables = pbl.getDeclaredLocalVariables();
    }
    LocalVariableWriteNode[] variables =
        writeNodes.toArray(new LocalVariableWriteNode[writeNodes.size()]);
    parentBlockIndex = variables.length;
    if (parentVariables == null || parentVariables.length == 0) {
      return variables;
    } else {
      int parentVarIndex = ((WzrdBlockNode) parentBlock).getParentBlockIndex();
      int visibleVarsIndex = getVisibleVarsIndexStart();
      int allVarsLength =
          variables.length + visibleVarsIndex + parentVariables.length - parentVarIndex;
      LocalVariableWriteNode[] allVariables = Arrays.copyOf(variables, allVarsLength);
      System.arraycopy(parentVariables, 0, allVariables, variables.length, visibleVarsIndex);
      System.arraycopy(
          parentVariables,
          parentVarIndex,
          allVariables,
          variables.length + visibleVarsIndex,
          parentVariables.length - parentVarIndex);
      return allVariables;
    }
  }
}
