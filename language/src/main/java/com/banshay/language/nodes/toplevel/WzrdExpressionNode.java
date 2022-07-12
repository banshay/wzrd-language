package com.banshay.language.nodes.toplevel;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.UnexpectedResultException;

public abstract class WzrdExpressionNode extends WzrdNode {

  public abstract int executeInt(VirtualFrame frame) throws UnexpectedResultException;

  public abstract double executeDouble(VirtualFrame frame);

  public abstract long executeLong(VirtualFrame frame);

  public abstract boolean executeBoolean(VirtualFrame frame);

  public abstract Object executeGeneric(VirtualFrame frame);

  @Override
  public Object execute(VirtualFrame frame) {
    return executeGeneric(frame);
  }
}
