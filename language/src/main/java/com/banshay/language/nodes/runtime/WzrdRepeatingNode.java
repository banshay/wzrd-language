package com.banshay.language.nodes.runtime;

import com.banshay.language.nodes.toplevel.WzrdExpressionNode;
import com.banshay.language.nodes.toplevel.WzrdStatementNode;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.RepeatingNode;
import com.oracle.truffle.api.nodes.UnexpectedResultException;

public class WzrdRepeatingNode extends Node implements RepeatingNode {

  @Child private WzrdExpressionNode conditionNode;
  @Child private WzrdStatementNode blockNode;

//  private final BranchProfile continueTaken = BranchProfile.create();
//  private final BranchProfile breakTaken = BranchProfile.create();

  public WzrdRepeatingNode(WzrdExpressionNode conditionNode, WzrdStatementNode bodyNode) {
    this.conditionNode = conditionNode;
    this.blockNode = bodyNode;
  }

  private boolean evaluateCondition(VirtualFrame frame) {
    try {
      return conditionNode.executeBoolean(frame);
    } catch (UnexpectedResultException e) {
      throw new RuntimeException("Invalid expression in while loop",e);
    }
  }

  @Override
  public boolean executeRepeating(VirtualFrame frame) {
    if (!evaluateCondition(frame)) {
    return false;
    }
    blockNode.executeVoid(frame);
    return true;
  }

  @Override
  public Object executeRepeatingWithValue(VirtualFrame frame) {
    return RepeatingNode.super.executeRepeatingWithValue(frame);
  }

  @Override
  public Object initialLoopStatus() {
    return RepeatingNode.super.initialLoopStatus();
  }

  @Override
  public boolean shouldContinue(Object returnValue) {
    return RepeatingNode.super.shouldContinue(returnValue);
  }
}
