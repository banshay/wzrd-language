package com.banshay.language;

import com.oracle.truffle.api.TruffleLanguage.ContextReference;
import com.oracle.truffle.api.nodes.Node;

public class WzrdContext {
  public static final ContextReference<WzrdContext> REFERENCE =
      ContextReference.create(WzrdLanguage.class);

  private final FunctionRegistry functionRegistry;

  public final WzrdGlobalScope globalScope;

  public WzrdContext(WzrdLanguage language, WzrdGlobalScope globalScope) {
    this.globalScope = globalScope;
    this.functionRegistry = new FunctionRegistry(language);
  }

  public static WzrdContext get(Node node) {
    return REFERENCE.get(node);
  }

  public FunctionRegistry getFunctionRegistry() {
    return functionRegistry;
  }
}
