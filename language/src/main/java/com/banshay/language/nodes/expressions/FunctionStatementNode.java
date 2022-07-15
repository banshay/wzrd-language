package com.banshay.language.nodes.expressions;

import com.banshay.language.WzrdContext;
import com.banshay.language.WzrdLanguage;
import com.banshay.language.nodes.runtime.WzrdFunction;
import com.banshay.language.nodes.toplevel.WzrdExpressionNode;
import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.CompilerDirectives.CompilationFinal;
import com.oracle.truffle.api.frame.VirtualFrame;

public abstract class FunctionStatementNode extends WzrdExpressionNode {

  private final String name;

  @CompilationFinal private WzrdFunction cachedFunction;

  public FunctionStatementNode(String name) {
    this.name = name;
  }

  @Override
  public Object executeGeneric(VirtualFrame frame) {
    var language = WzrdLanguage.get(this);
    CompilerAsserts.partialEvaluationConstant(language);

    WzrdFunction function;
    if (language.isSingleContext()) {
      function = this.cachedFunction;
      if (function == null) {
        CompilerDirectives.transferToInterpreterAndInvalidate();
        this.cachedFunction =
            function = WzrdContext.get(this).getFunctionRegistry().lookup(name, true);
      }
    } else {
      if (this.cachedFunction != null) {
        CompilerDirectives.transferToInterpreterAndInvalidate();
        this.cachedFunction = null;
      }
      function = WzrdContext.get(this).getFunctionRegistry().lookup(name, true);
    }
    return function;
  }
}
