package com.banshay.language.nodes.statements;

import com.banshay.language.nodes.toplevel.WzrdExpressionNode;
import com.banshay.language.nodes.toplevel.WzrdStatementNode;
import com.banshay.language.types.WzrdNull;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.api.profiles.ConditionProfile;

public final class IfStatement extends WzrdStatementNode {

  @Child private WzrdExpressionNode ifExpression;
  @Child private WzrdStatementNode thenBlock;
  @Child private WzrdStatementNode elseBlock;

  private final ConditionProfile conditionProfile = ConditionProfile.createCountingProfile();

  public IfStatement(
      WzrdExpressionNode ifExpression, WzrdStatementNode thenBlock, WzrdStatementNode elseBlock) {
    this.ifExpression = ifExpression;
    this.thenBlock = thenBlock;
    this.elseBlock = elseBlock;
  }

  @Override
  public Object executeGeneric(VirtualFrame frame) {
    if (conditionProfile.profile(evaluateBoolean(frame))) {
      return thenBlock.executeGeneric(frame);
    } else {
      if (elseBlock != null) {
        return elseBlock.executeGeneric(frame);
      }
    }
    return WzrdNull.INSTANCE;
  }

  private boolean evaluateBoolean(VirtualFrame frame) {
    try {
      return ifExpression.executeBoolean(frame);
    } catch (UnexpectedResultException e) {
      throw new RuntimeException("No boolean expression in if statement", e);
    }
  }
}
