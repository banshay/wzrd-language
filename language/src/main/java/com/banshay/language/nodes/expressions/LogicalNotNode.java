package com.banshay.language.nodes.expressions;

import com.oracle.truffle.api.dsl.Fallback;
import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;

@NodeChild("valueNode")
public abstract class LogicalNotNode {

  @Specialization
  protected boolean doBoolean(boolean value){
    return !value;
  }

  @Fallback
  protected Object typeError(Object value){
    throw new RuntimeException("not a boolean expression %s".formatted(value));
  }
}
