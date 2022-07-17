package com.banshay.language.nodes.toplevel;

import com.banshay.language.WzrdTypeSystem;
import com.banshay.language.WzrdTypeSystemGen;
import com.oracle.truffle.api.dsl.TypeSystemReference;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.UnexpectedResultException;

@TypeSystemReference(WzrdTypeSystem.class)
public abstract class WzrdExpressionNode extends WzrdStatementNode {

  public int executeInt(VirtualFrame frame) throws UnexpectedResultException {
    return WzrdTypeSystemGen.expectInteger(executeGeneric(frame));
  }

  public double executeDouble(VirtualFrame frame) throws UnexpectedResultException {
    return WzrdTypeSystemGen.expectDouble(executeGeneric(frame));
  }

  public long executeLong(VirtualFrame frame) throws UnexpectedResultException {
    return WzrdTypeSystemGen.expectLong(executeGeneric(frame));
  }

  public boolean executeBoolean(VirtualFrame frame) throws UnexpectedResultException {
    return WzrdTypeSystemGen.expectBoolean(executeGeneric(frame));
  }

  public abstract Object executeGeneric(VirtualFrame frame);

}
