package com.banshay.language.types;

import com.banshay.language.WzrdLanguage;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;
import com.oracle.truffle.api.utilities.TriState;

@ExportLibrary(InteropLibrary.class)
public class WzrdNull implements TruffleObject {

  private WzrdNull() {}

  public static final WzrdNull INSTANCE = new WzrdNull();

  public static final int IDENTITY_HASH = System.identityHashCode(INSTANCE);

  @Override
  public String toString() {
    return "null";
  }

  @ExportMessage
  boolean isNull() {
    return true;
  }

  @ExportMessage
  boolean hasLanguage() {
    return true;
  }

  @ExportMessage
  Class<? extends TruffleLanguage<?>> getLanguage() {
    return WzrdLanguage.class;
  }

  @ExportMessage
  static int identityHashCode(WzrdNull receiver) {
    return IDENTITY_HASH;
  }

  @ExportMessage
  final Object toDisplayString(boolean allowSideEffects) {
    return "wzrd null";
  }

  @ExportMessage
  final TriState isIdenticalOrUndefined(Object other) {
    return TriState.valueOf(WzrdNull.INSTANCE == other);
  }
}
