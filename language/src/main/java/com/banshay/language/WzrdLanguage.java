package com.banshay.language;

import com.banshay.language.nodes.WzrdRootNode;
import com.banshay.language.nodes.WzrdUndefinedFunctionRootNode;
import com.banshay.language.nodes.toplevel.WzrdNode;
import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.RootCallTarget;
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

  private final Map<String, RootCallTarget> undefinedFunctions = new ConcurrentHashMap<>();

  @Override
  protected CallTarget parse(ParsingRequest request) throws Exception {
    var nodes = WzrdScriptTruffleParser.parse(request.getSource().getReader());
    var rootNode = new WzrdRootNode("main", this, nodes.toArray(WzrdNode[]::new));
    return rootNode.getCallTarget();
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
    return new WzrdContext(new WzrdGlobalScope());
  }

  public static WzrdLanguage get(Node node) {
    return REFERENCE.get(node);
  }
}
