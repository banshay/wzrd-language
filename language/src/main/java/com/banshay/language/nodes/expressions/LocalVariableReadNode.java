package com.banshay.language.nodes.expressions;

import com.banshay.language.nodes.toplevel.WzrdExpressionNode;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.NodeField;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;

@NodeField(name = "slot", type = int.class)
public abstract class LocalVariableReadNode extends WzrdExpressionNode {
  protected abstract int getSlot();

  @Specialization(guards = "frame.isInt(getSlot())")
  protected int readInt(VirtualFrame frame) {
    return frame.getInt(getSlot());
  }

  @Specialization(guards = "frame.isLong(getSlot())")
  protected long readLong(VirtualFrame frame) {
    return frame.getLong(getSlot());
  }

  @Specialization(guards = "frame.isBoolean(getSlot())")
  protected boolean readBoolean(VirtualFrame frame) {
    return frame.getBoolean(getSlot());
  }

  @Specialization(replaces = {"readLong", "readBoolean", "readInt"})
  protected Object readObject(VirtualFrame frame) {
    if (!frame.isObject(getSlot())) {
      CompilerDirectives.transferToInterpreter();
      var result = frame.getValue(getSlot());
      frame.setObject(getSlot(), result);
      return result;
    }
    return frame.getObject(getSlot());
  }
}
