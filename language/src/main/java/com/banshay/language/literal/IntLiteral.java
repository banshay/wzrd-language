package com.banshay.language.literal;

import com.banshay.language.nodes.toplevel.WzrdExpressionNode;
import com.oracle.truffle.api.frame.VirtualFrame;

public final class IntLiteral extends WzrdExpressionNode {

  private final int value;

  public IntLiteral(int value) {
    this.value = value;
  }

  @Override
  public int executeInt(VirtualFrame frame) {
    return value;
  }

  @Override
  public double executeDouble(VirtualFrame frame) {
    return value;
  }


  @Override
  public Object executeGeneric(VirtualFrame frame) {
    return value;
  }
}
