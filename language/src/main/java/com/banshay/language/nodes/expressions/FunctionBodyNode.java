package com.banshay.language.nodes.expressions;

import com.banshay.language.nodes.toplevel.WzrdExpressionNode;
import com.banshay.language.nodes.toplevel.WzrdStatementNode;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.UnexpectedResultException;

public final class FunctionBodyNode extends WzrdExpressionNode {
  @Child private WzrdStatementNode bodyNode;

  public FunctionBodyNode(WzrdStatementNode bodyNode) {
    this.bodyNode = bodyNode;
  }

  @Override
  public Object executeGeneric(VirtualFrame frame) {
    try {
      bodyNode.executeVoid(frame);
    } catch (Exception ignore) {
    }
    return null;
  }

  @Override
  public int executeInt(VirtualFrame frame) throws UnexpectedResultException {
    return 0;
  }

  @Override
  public double executeDouble(VirtualFrame frame) {
    return 0;
  }

  @Override
  public long executeLong(VirtualFrame frame) {
    return 0;
  }

  @Override
  public boolean executeBoolean(VirtualFrame frame) {
    return false;
  }
}
