package com.banshay.language.nodes.expressions;

import com.oracle.truffle.api.dsl.Specialization;

public abstract class AddNode extends WzrdBinaryNode {

  @Specialization(rewriteOn = ArithmeticException.class)
  protected int addInt(int leftNode, int rightNode) {
    return Math.addExact(leftNode, rightNode);
  }

  @Specialization(replaces = "addInt")
  protected double addDouble(double leftNode, double rightNode) {
    return leftNode + rightNode;
  }
}
