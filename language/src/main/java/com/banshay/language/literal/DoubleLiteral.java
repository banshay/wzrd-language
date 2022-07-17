package com.banshay.language.literal;

import com.banshay.language.nodes.toplevel.WzrdExpressionNode;
import com.oracle.truffle.api.frame.VirtualFrame;

public final class DoubleLiteral extends WzrdExpressionNode {

  private final double value;

  public DoubleLiteral(double value) {
    this.value = value;
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
  public Object executeGeneric(VirtualFrame frame) {
    return value;
  }
}
