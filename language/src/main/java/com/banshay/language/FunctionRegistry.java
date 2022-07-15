package com.banshay.language;

import com.banshay.language.nodes.runtime.WzrdFunction;
import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FunctionRegistry {

  private final WzrdLanguage language;

  private Map<String, WzrdFunction> functions = new ConcurrentHashMap<>();

  public FunctionRegistry(WzrdLanguage language) {
    this.language = language;
  }

  @TruffleBoundary
  public WzrdFunction lookup(String name, boolean createIfNotPresent) {
    var function = functions.get(name);
    if (function == null && createIfNotPresent) {
      function = new WzrdFunction(name, language);
      functions.put(name, function);
    }
    return function;
  }
}
