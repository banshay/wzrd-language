package com.banshay.language;

import com.banshay.language.nodes.WzrdUndefinedFunctionRootNode;
import com.oracle.truffle.api.Assumption;
import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.RootCallTarget;
import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.nodes.Node;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@TruffleLanguage.Registration(
    id = "wzrd",
    name = "Wizard",
    defaultMimeType = WzrdLanguage.MIME_TYPE,
    characterMimeTypes = WzrdLanguage.MIME_TYPE,
    contextPolicy = TruffleLanguage.ContextPolicy.SHARED,
    fileTypeDetectors = WzrdFileTypeDetector.class)
public class WzrdLanguage extends TruffleLanguage<WzrdContext> {

  public static final String MIME_TYPE = "application/x-wzrd";

  public static final LanguageReference<WzrdLanguage> REFERENCE =
      LanguageReference.create(WzrdLanguage.class);

  private final Assumption singleContext =
      Truffle.getRuntime().createAssumption("Single WZRD Context");

  private final Map<String, RootCallTarget> undefinedFunctions = new ConcurrentHashMap<>();

  @Override
  protected CallTarget parse(ParsingRequest request) throws Exception {
    var functions = WzrdScriptTruffleParser.parse(request.getSource().getReader(), this);
    var mainFunction = functions.get("main");
    if (mainFunction == null) {
      throw new RuntimeException("No main function present!");
    }
    return mainFunction;
  }

  public RootCallTarget getOrCreateUndefinedFunction(String name) {
    var target = undefinedFunctions.get(name);
    if (target == null) {
      target = new WzrdUndefinedFunctionRootNode(this, name).getCallTarget();
      var other = undefinedFunctions.putIfAbsent(name, target);
      if (other != null) {
        target = other;
      }
    }
    return target;
  }

  @Override
  protected WzrdContext createContext(Env env) {
    return new WzrdContext(this, new WzrdGlobalScope());
  }

  @Override
  protected Object getScope(WzrdContext context) {
    return context.globalScope;
  }

  public static WzrdLanguage get(Node node) {
    return REFERENCE.get(node);
  }

  public boolean isSingleContext() {
    return singleContext.isValid();
  }
}
