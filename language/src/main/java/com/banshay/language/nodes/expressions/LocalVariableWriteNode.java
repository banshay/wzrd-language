package com.banshay.language.nodes.expressions;

import com.banshay.language.nodes.toplevel.WzrdExpressionNode;
import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.NodeField;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.FrameSlotKind;
import com.oracle.truffle.api.frame.VirtualFrame;

@NodeChild(value = "bindingExpression", type = WzrdExpressionNode.class)
@NodeField(name = "slot", type = int.class)
@NodeField(name = "name", type = String.class)
@NodeField(name = "declaration", type = boolean.class)
public abstract class LocalVariableWriteNode extends WzrdExpressionNode {

  public abstract int getSlot();

  public abstract WzrdExpressionNode getBindingExpression();

  public abstract String getName();

  public abstract boolean isDeclaration();

  @Specialization(guards = "isIntOrIllegal(frame)")
  protected int writeInt(VirtualFrame frame, int value) {
    frame.getFrameDescriptor().setSlotKind(getSlot(), FrameSlotKind.Int);
    frame.setInt(getSlot(), value);
    return value;
  }

  @Specialization(guards = "isLongOrIllegal(frame)")
  protected long writeLong(VirtualFrame frame, long value) {
    frame.getFrameDescriptor().setSlotKind(getSlot(), FrameSlotKind.Long);
    frame.setLong(getSlot(), value);
    return value;
  }

  @Specialization(guards = "isBooleanOrIllegal(frame)")
  protected boolean writeBoolean(VirtualFrame frame, boolean value) {
    frame.getFrameDescriptor().setSlotKind(getSlot(), FrameSlotKind.Boolean);
    frame.setBoolean(getSlot(), value);
    return value;
  }

  @Specialization(replaces = {"writeInt", "writeLong", "writeBoolean"})
  protected Object write(VirtualFrame frame, Object value) {
    frame.getFrameDescriptor().setSlotKind(getSlot(), FrameSlotKind.Object);
    frame.setObject(getSlot(), value);
    return value;
  }

  public abstract void executeWrite(VirtualFrame frame, Object value);

  protected boolean isIntOrIllegal(VirtualFrame frame) {
    var kind = frame.getFrameDescriptor().getSlotKind(getSlot());
    return kind == FrameSlotKind.Int || kind == FrameSlotKind.Illegal;
  }

  protected boolean isLongOrIllegal(VirtualFrame frame) {
    var kind = frame.getFrameDescriptor().getSlotKind(getSlot());
    return kind == FrameSlotKind.Long || kind == FrameSlotKind.Illegal;
  }

  protected boolean isBooleanOrIllegal(VirtualFrame frame) {
    var kind = frame.getFrameDescriptor().getSlotKind(getSlot());
    return kind == FrameSlotKind.Boolean || kind == FrameSlotKind.Illegal;
  }
}
