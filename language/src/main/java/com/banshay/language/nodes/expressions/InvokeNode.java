package com.banshay.language.nodes.expressions;

import com.banshay.language.nodes.toplevel.WzrdExpressionNode;
import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.interop.ArityException;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.interop.UnsupportedTypeException;
import com.oracle.truffle.api.nodes.ExplodeLoop;

public class InvokeNode extends WzrdExpressionNode {

  @Child private WzrdExpressionNode functionNode;
  @Children private final WzrdExpressionNode[] argumentNodes;
  @Child private InteropLibrary library;

  public InvokeNode(WzrdExpressionNode functionNode, WzrdExpressionNode[] argumentNodes) {
    this.functionNode = functionNode;
    this.argumentNodes = argumentNodes;
    this.library = InteropLibrary.getFactory().createDispatched(3);
  }

  @ExplodeLoop
  @Override
  public Object executeGeneric(VirtualFrame frame) {
    var function = functionNode.executeGeneric(frame);

    CompilerAsserts.compilationConstant(argumentNodes.length);

    var argumentValues = new Object[argumentNodes.length];
    for (int i = 0; i < argumentNodes.length; i++) {
      argumentValues[i] = argumentNodes[i].executeGeneric(frame);
    }

    try {
      return library.execute(function, argumentValues);
    } catch (UnsupportedTypeException | ArityException | UnsupportedMessageException e) {
      throw new RuntimeException("failed to execute function");
    }
  }
}
