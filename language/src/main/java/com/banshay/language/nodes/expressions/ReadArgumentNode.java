package com.banshay.language.nodes.expressions;

import com.banshay.language.nodes.toplevel.WzrdExpressionNode;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.profiles.BranchProfile;

public abstract class ReadArgumentNode extends WzrdExpressionNode {

  private final int index;

  private final BranchProfile outOfBoundsTaken = BranchProfile.create();

  public ReadArgumentNode(int index) {
    this.index = index;
  }

  @Override
  public Object executeGeneric(VirtualFrame frame) {
    var arguments = frame.getArguments();
    if (index < arguments.length) {
      return arguments[index];
    }
    outOfBoundsTaken.enter();
    return null;
  }
}
