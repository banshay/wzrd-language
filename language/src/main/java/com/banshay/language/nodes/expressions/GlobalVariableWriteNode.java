package com.banshay.language.nodes.expressions;

import com.banshay.language.WzrdContext;
import com.banshay.language.nodes.toplevel.WzrdExpressionNode;
import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;
import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.NodeField;
import com.oracle.truffle.api.dsl.Specialization;

@NodeChild(value = "bindingExpression", type = WzrdExpressionNode.class)
@NodeField(name = "name", type = String.class)
public abstract class GlobalVariableWriteNode extends WzrdExpressionNode {

  public abstract WzrdExpressionNode getBindingExpression();

  public abstract String getName();

  @Specialization
  @TruffleBoundary
  protected Object createVariable(Object value) {
    var context = WzrdContext.get(null);
    String variableName = this.getName();

    if (!context.globalScope.newVariable(variableName, value)) {
      throw new RuntimeException("Variable %s already exists".formatted(variableName));
    }

    return null;
  }
}
