package com.banshay.language.nodes.expressions;

import com.oracle.truffle.api.dsl.Specialization;

public abstract class SubtractNode extends WzrdBinaryNode {

  @Specialization(rewriteOn = ArithmeticException.class)
  protected int subInt(int leftNode, int rightNode) {
    return Math.addExact(leftNode, -rightNode);
  }

  @Specialization(replaces = "subInt")
  protected double subDouble(double leftNode, double rightNode) {
    return leftNode - rightNode;
  }
}
