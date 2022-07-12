package com.banshay.language.literal;

import com.banshay.language.nodes.toplevel.WzrdExpressionNode;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.UnexpectedResultException;

public final class DoubleLiteral extends WzrdExpressionNode {

  private final double value;

  public DoubleLiteral(double value) {
    this.value = value;
  }

  @Override
  public int executeInt(VirtualFrame frame) throws UnexpectedResultException {
    throw new UnexpectedResultException(value);
  }

  @Override
  public double executeDouble(VirtualFrame frame) {
    return value;
  }

  @Override
  public long executeLong(VirtualFrame frame) {
    return Double.doubleToLongBits(value);
  }

  @Override
  public boolean executeBoolean(VirtualFrame frame) {
    return value != 0;
  }

  @Override
  public Object executeGeneric(VirtualFrame frame) {
    return value;
  }
}
