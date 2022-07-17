package com.banshay.language.nodes.expressions;

import com.banshay.language.nodes.toplevel.WzrdExpressionNode;
import com.banshay.language.types.WzrdNull;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.profiles.BranchProfile;

public class ArgumentNode extends WzrdExpressionNode {

  private final int index;
  private final BranchProfile outOfBoundsToken = BranchProfile.create();

  public ArgumentNode(int index) {
    this.index = index;
  }

  @Override
  public Object executeGeneric(VirtualFrame frame) {
    var arguments = frame.getArguments();
    if (index < arguments.length) {
      return arguments[index];
    }
    outOfBoundsToken.enter();
    return WzrdNull.INSTANCE;
  }
}
