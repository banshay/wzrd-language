package com.banshay.language.literal;

import com.banshay.language.nodes.toplevel.WzrdExpressionNode;
import com.oracle.truffle.api.frame.VirtualFrame;

public final class StringLiteral extends WzrdExpressionNode {

  private final String value;

  public StringLiteral(String value) {
    this.value = value;
  }

  @Override
  public int executeInt(VirtualFrame frame) {
    return Integer.parseInt(value);
  }

  @Override
  public double executeDouble(VirtualFrame frame) {
    return Double.parseDouble(value);
  }

  @Override
  public long executeLong(VirtualFrame frame) {
    return Long.parseLong(value);
  }

  @Override
  public boolean executeBoolean(VirtualFrame frame) {
    return Boolean.getBoolean(value);
  }

  @Override
  public Object executeGeneric(VirtualFrame frame) {
    return value;
  }
}
