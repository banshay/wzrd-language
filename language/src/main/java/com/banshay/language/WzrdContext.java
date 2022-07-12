package com.banshay.language;

import com.oracle.truffle.api.TruffleLanguage.ContextReference;
import com.oracle.truffle.api.nodes.Node;

public class WzrdContext {

  public static final ContextReference<WzrdContext> REFERENCE =
      ContextReference.create(WzrdLanguage.class);

  public final WzrdGlobalScope globalScope;

  public WzrdContext(WzrdGlobalScope globalScope) {
    this.globalScope = globalScope;
  }

  public static WzrdContext get(Node node){
    return REFERENCE.get(node);
  }
}
