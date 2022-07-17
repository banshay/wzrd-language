package com.banshay.language.nodes.expressions;

import com.oracle.truffle.api.dsl.Fallback;
import com.oracle.truffle.api.dsl.Specialization;

public abstract class LessNode extends WzrdBinaryNode {

  @Specialization
  protected boolean greaterThanEqual(int leftNode, int rightNode) {
    return leftNode < rightNode;
  }

  @Specialization
  protected boolean greaterThanEqual(double leftNode, double rightNode) {
    return leftNode < rightNode;
  }

  @Specialization
  protected boolean greaterThanEqual(long leftNode, long rightNode) {
    return leftNode < rightNode;
  }

  @Fallback
  protected Object typeError(Object leftNode, Object rightNode) {
    throw new RuntimeException("Cannot compare values %s and %s".formatted(leftNode, rightNode));
  }
}
