package com.banshay.language;

import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.interop.UnknownIdentifierException;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@ExportLibrary(InteropLibrary.class)
public class WzrdGlobalScope implements TruffleObject {

  private final Map<String, Object> variables = new HashMap<>();

  public boolean newVariable(String name, Object value) {
    return variables.putIfAbsent(name, value) == null;
  }

  public boolean updateVariable(String name, Object value) {
    return variables.computeIfPresent(name, (k, v) -> value) != null;
  }

  public Object getVariable(String name) {
    return variables.get(name);
  }

  @ExportMessage
  boolean isScope() {
    return true;
  }

  @ExportMessage
  boolean hasMembers() {
    return true;
  }

  @ExportMessage
  boolean isMemberReadable(String member) {
    return variables.containsKey(member);
  }

  @ExportMessage
  Object readMember(String member) throws UnknownIdentifierException {
    return Optional.ofNullable(variables.get(member))
        .orElseThrow(() -> UnknownIdentifierException.create(member));
  }

  @ExportMessage
  Object getMembers(boolean includeInternal) {
    return String.join(", ", variables.keySet());
  }

  @ExportMessage
  Object toDisplayString(boolean allowSideEffects) {
    return "global wzrd scope";
  }

  @ExportMessage
  boolean hasLanguage() {
    return true;
  }

  @ExportMessage
  Class<? extends TruffleLanguage<?>> getLanguage() {
    return WzrdLanguage.class;
  }
}
