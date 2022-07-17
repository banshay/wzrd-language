package com.banshay.language;

import com.banshay.language.nodes.runtime.WzrdFunction;
import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;
import com.oracle.truffle.api.RootCallTarget;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FunctionRegistry {

  private final WzrdLanguage language;

  private Map<String, WzrdFunction> functions = new ConcurrentHashMap<>();
  private final Map<Map<String, RootCallTarget>, Void> registeredFunctions = new IdentityHashMap<>();

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

  WzrdFunction register(String name, RootCallTarget callTarget) {
    var function = functions.get(name);
    if (function == null) {
      function = new WzrdFunction(name, callTarget);
      functions.put(name, function);
    }else {
      function.setCallTarget(callTarget);
    }
    return function;
  }

  @TruffleBoundary
  public void register(Map<String, RootCallTarget> newFunctions) {
    if (registeredFunctions.containsKey(newFunctions)) {
      return;
    }
    newFunctions.forEach(this::register);
    registeredFunctions.put(newFunctions, null);
  }
}
