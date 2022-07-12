package com.banshay.language.nodes.expressions;

import com.banshay.language.nodes.toplevel.WzrdExpressionNode;
import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;

@NodeChild("leftNode")
@NodeChild("rightNode")
public abstract class AddNode extends WzrdExpressionNode {

  @Specialization(rewriteOn = ArithmeticException.class)
  protected int addInt(int leftNode, int rightNode) {
    return Math.addExact(leftNode, rightNode);
  }

  @Specialization(replaces = "addInt")
  protected double addDouble(double leftNode, double rightNode) {
    return leftNode + rightNode;
  }
}
