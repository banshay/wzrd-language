package com.banshay.language;

import java.util.HashMap;
import java.util.Map;

public class WzrdGlobalScope {

  private final Map<String, Object> variables = new HashMap<>();

  public boolean newVariable(String name, Object value) {
    return variables.putIfAbsent(name, value) == null;
  }

  public boolean updateVariable(String name, Object value) {
    return variables.computeIfPresent(name, (k, v) -> value) != null;
  }

  public Object getVariable(String name){
    return variables.get(name);
  }
}
