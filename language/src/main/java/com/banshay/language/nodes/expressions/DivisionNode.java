package com.banshay.language.nodes.expressions;

import com.oracle.truffle.api.dsl.Specialization;

public abstract class DivisionNode extends WzrdBinaryNode {

  @Specialization(rewriteOn = ArithmeticException.class)
  public int divideInt(int leftNode, int rightNode) {
    return leftNode / rightNode;
  }

  @Specialization(replaces = "divideInt", rewriteOn = ArithmeticException.class)
  public double divideDouble(double leftNode, double rightNode) {
    return leftNode / rightNode;
  }

  @Specialization(replaces = "divideDouble")
  public long divideLong(long leftNode, long rightNode) {
    return leftNode / rightNode;
  }
}
