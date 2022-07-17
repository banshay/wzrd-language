package com.banshay.language.nodes.statements;

import com.banshay.language.nodes.runtime.WzrdRepeatingNode;
import com.banshay.language.nodes.toplevel.WzrdExpressionNode;
import com.banshay.language.nodes.toplevel.WzrdStatementNode;
import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.LoopNode;

public class WhileStatement extends WzrdStatementNode {

  @Child private LoopNode loopNode;

  public WhileStatement(WzrdExpressionNode expressionNode, WzrdStatementNode body) {
    this.loopNode = Truffle.getRuntime().createLoopNode(new WzrdRepeatingNode(expressionNode, body));
  }

  @Override
  public Object executeGeneric(VirtualFrame frame) {
    loopNode.execute(frame);
    return null;
  }
}
