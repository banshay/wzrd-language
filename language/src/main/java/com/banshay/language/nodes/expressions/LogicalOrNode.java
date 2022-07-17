package com.banshay.language.nodes.expressions;

import com.banshay.language.nodes.toplevel.WzrdExpressionNode;

public final class LogicalOrNode extends BooleanNode {

  public LogicalOrNode(WzrdExpressionNode leftNode,
      WzrdExpressionNode rightNode) {
    super(leftNode, rightNode);
  }

  @Override
  protected boolean execute(boolean left, boolean right) {
    return left || right;
  }

  @Override
  protected boolean shouldEvaluateRight(boolean left) {
    return !left;
  }
}
