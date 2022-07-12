package com.banshay.language.nodes.toplevel;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;

public abstract class WzrdNode extends Node {

  public abstract Object execute(VirtualFrame frame);
}
