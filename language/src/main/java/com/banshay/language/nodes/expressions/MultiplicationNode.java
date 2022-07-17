package com.banshay.language.nodes.expressions;

import com.oracle.truffle.api.dsl.Specialization;

public abstract class MultiplicationNode extends WzrdBinaryNode {

  @Specialization(rewriteOn = ArithmeticException.class)
  public int multiplyInt(int leftNode, int rightNode) {
    return Math.multiplyExact(leftNode, rightNode);
  }

  @Specialization(replaces = "multiplyInt")
  public double multiplyDouble(double leftNode, double rightNode) {
    return leftNode * rightNode;
  }
}
