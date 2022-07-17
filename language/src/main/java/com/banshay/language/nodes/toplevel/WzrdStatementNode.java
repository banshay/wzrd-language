package com.banshay.language.nodes.toplevel;

import com.oracle.truffle.api.frame.VirtualFrame;

public abstract class WzrdStatementNode extends WzrdNode {

  public void executeVoid(VirtualFrame frame){
    executeGeneric(frame);
  }
  public abstract Object executeGeneric(VirtualFrame frame);
}
