package com.banshay.language.nodes.runtime;

import com.banshay.language.WzrdLanguage;
import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.RootCallTarget;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.ReportPolymorphism;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;
import com.oracle.truffle.api.nodes.DirectCallNode;
import com.oracle.truffle.api.nodes.IndirectCallNode;

@ExportLibrary(InteropLibrary.class)
public class WzrdFunction implements TruffleObject {

  private final String name;
  public CallTarget callTarget;

  public WzrdFunction(String name, WzrdLanguage language) {
    this(name, language.getOrCreateUndefinedFunction(name));
  }

  public WzrdFunction(String name, RootCallTarget callTarget) {
    this.name = name;
    this.callTarget = callTarget;
  }

  @ExportMessage
  final boolean isExecutable() {
    return true;
  }

  public void setCallTarget(RootCallTarget callTarget) {
    this.callTarget = callTarget;
  }

  @ExportMessage
  @ReportPolymorphism
  abstract static class Execute {

    @Specialization(guards = "function.callTarget == directCallNode.getCallTarget()", limit = "2")
    protected static Object dispatchDirect(
        WzrdFunction function,
        Object[] arguments,
        @Cached("create(function.callTarget)") DirectCallNode directCallNode) {
      return directCallNode.call(arguments);
    }

    @Specialization(replaces = "dispatchDirect")
    protected static Object dispatchIndirect(
        WzrdFunction function, Object[] arguments, @Cached IndirectCallNode indirectCallNode) {
      return indirectCallNode.call(function.callTarget, arguments);
    }
  }
}
