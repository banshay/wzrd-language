package com.banshay.language.nodes.toplevel;

import com.banshay.language.WzrdLanguage;
import com.oracle.truffle.api.RootCallTarget;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.utilities.CyclicAssumption;

@ExportLibrary(InteropLibrary.class)
public class FunctionNode implements TruffleObject {

  private final String name;

  private RootCallTarget callTarget;

  private final CyclicAssumption callTargetStable;

  public FunctionNode(WzrdLanguage language, String name) {
    this(name, language.getOrCreateUndefinedFunction(name));
  }

  public FunctionNode(String name, RootCallTarget callTarget) {
    this.name = name;
    this.callTargetStable = new CyclicAssumption(name);
    this.callTarget = callTarget;
  }
}
