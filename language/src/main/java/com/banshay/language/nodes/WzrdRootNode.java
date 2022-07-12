package com.banshay.language.nodes;

import com.banshay.language.WzrdContext;
import com.banshay.language.nodes.toplevel.WzrdNode;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.RootNode;

public class WzrdRootNode extends RootNode {

  private final String name;
  @Children private WzrdNode[] rootNodes;

  public WzrdRootNode(String name, TruffleLanguage<WzrdContext> language, WzrdNode... wzrdNodes) {
    super(language);
    this.rootNodes = wzrdNodes;
    this.name = name;
  }

  public WzrdRootNode(
      TruffleLanguage<?> language,
      FrameDescriptor frameDescriptor,
      String name,
      WzrdNode... rootNode) {
    super(language, frameDescriptor);
    this.rootNodes = rootNode;
    this.name = name;
  }

  @Override
  public Object execute(VirtualFrame frame) {
    Object returnValue = null;
    for (WzrdNode rootNode : rootNodes) {
      returnValue = rootNode.execute(frame);
    }
    return returnValue;
  }
}
