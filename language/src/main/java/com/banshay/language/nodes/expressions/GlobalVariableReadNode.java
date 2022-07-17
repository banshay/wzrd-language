package com.banshay.language.nodes.expressions;

import com.banshay.language.WzrdContext;
import com.banshay.language.nodes.toplevel.WzrdExpressionNode;
import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;
import com.oracle.truffle.api.dsl.NodeField;
import com.oracle.truffle.api.dsl.Specialization;

@NodeField(name = "name", type = String.class)
public abstract class GlobalVariableReadNode extends WzrdExpressionNode {
  protected abstract String getName();

  @Specialization
  @TruffleBoundary
  protected Object readVariable() {
    var context = WzrdContext.get(this);
    var value = context.globalScope.getVariable(this.getName());
    if (value == null) {
      throw new RuntimeException("Variable %s is not defined".formatted(this.getName()));
    }
    return value;
  }
}
