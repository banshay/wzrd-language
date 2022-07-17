package com.banshay.language.nodes;

import com.banshay.language.WzrdContext;
import com.banshay.language.WzrdLanguage;
import com.banshay.language.types.WzrdNull;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.CompilerDirectives.CompilationFinal;
import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;
import com.oracle.truffle.api.RootCallTarget;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.DirectCallNode;
import com.oracle.truffle.api.nodes.RootNode;
import java.util.Collections;
import java.util.Map;

public final class WzrdEvalNode extends RootNode {

  private final Map<String, RootCallTarget> functions;

  @CompilationFinal private boolean registered;

  @Child private DirectCallNode mainCallNode;

  private WzrdLanguage language;

  public WzrdEvalNode(
      WzrdLanguage language,
      Map<String, RootCallTarget> functions,
      RootCallTarget rootFunction) {
    super(language);
    this.language = language;
    this.functions = Collections.unmodifiableMap(functions);
    this.mainCallNode = rootFunction != null ? DirectCallNode.create(rootFunction) : null;
  }

  @Override
  public boolean isInternal() {
    return true;
  }

  @Override
  protected boolean isInstrumentable() {
    return false;
  }

  @Override
  public String getName() {
    return "root eval";
  }

  @Override
  public Object execute(VirtualFrame frame) {
    if (language.isSingleContext()) {
      if (!registered) {
        CompilerDirectives.transferToInterpreterAndInvalidate();
        registerFunctions();
        registered = true;
      }
    }else{
      registerFunctions();
    }
    if (mainCallNode == null) {
      return WzrdNull.INSTANCE;
    }else{
      var arguments = frame.getArguments();
      return mainCallNode.call(arguments);
    }
  }

  @TruffleBoundary
  private void registerFunctions(){
    WzrdContext.get(this).getFunctionRegistry().register(functions);
  }
}
