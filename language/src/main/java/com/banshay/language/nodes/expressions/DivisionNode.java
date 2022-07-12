package com.banshay.language.nodes.expressions;

import com.banshay.language.nodes.toplevel.WzrdExpressionNode;
import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;

@NodeChild("leftNode")
@NodeChild("rightNode")
public abstract class DivisionNode extends WzrdExpressionNode {

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
