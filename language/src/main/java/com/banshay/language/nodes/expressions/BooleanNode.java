package com.banshay.language.nodes.expressions;

import com.banshay.language.nodes.toplevel.WzrdExpressionNode;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.api.profiles.ConditionProfile;

public abstract class BooleanNode extends WzrdExpressionNode {

  @Child private WzrdExpressionNode leftNode;
  @Child private WzrdExpressionNode rightNode;

  private final ConditionProfile evaluateRightProfile = ConditionProfile.createCountingProfile();

  public BooleanNode(WzrdExpressionNode leftNode, WzrdExpressionNode rightNode) {
    this.leftNode = leftNode;
    this.rightNode = rightNode;
  }

  @Override
  public Object executeGeneric(VirtualFrame frame) {
    return executeBoolean(frame);
  }

  @Override
  public boolean executeBoolean(VirtualFrame frame) {
    boolean left;
    try {
      left = leftNode.executeBoolean(frame);
    } catch (UnexpectedResultException e) {
      throw new RuntimeException(
          "left side does not amount to a boolean expression %s".formatted(leftNode.toString()));
    }
    boolean right;
    try {
      if (evaluateRightProfile.profile(shouldEvaluateRight(left))) {
        right = rightNode.executeBoolean(frame);
      } else {
        right = false;
      }
    } catch (UnexpectedResultException e) {
      throw new RuntimeException(
          "right side does not amount to a boolean expression %s".formatted(rightNode.toString()));
    }
    return execute(left, right);
  }

  protected abstract boolean execute(boolean left, boolean right);

  protected abstract boolean shouldEvaluateRight(boolean left);
}
