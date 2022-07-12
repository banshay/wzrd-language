package com.banshay.language.nodes.expressions;

import com.banshay.language.nodes.toplevel.WzrdExpressionNode;
import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;

@NodeChild("leftNode")
@NodeChild("rightNode")
public abstract class MultiplicationNode extends WzrdExpressionNode {

  @Specialization(rewriteOn = ArithmeticException.class)
  public int multiplyInt(int leftNode, int rightNode) {
    return Math.multiplyExact(leftNode, rightNode);
  }

  @Specialization(replaces = "multiplyInt")
  public double multiplyDouble(double leftNode, double rightNode) {
    return leftNode * rightNode;
  }
}
