package com.banshay.language;

import com.banshay.language.nodes.WzrdRootNode;
import com.banshay.language.parser.WzrdScriptTruffleParser;
import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.TruffleLanguage;

@TruffleLanguage.Registration(id = "wzrd", name = "Wizard", defaultMimeType = WzrdLanguage.MIME_TYPE, characterMimeTypes = WzrdLanguage.MIME_TYPE,
    contextPolicy = TruffleLanguage.ContextPolicy.SHARED, fileTypeDetectors = WzrdFileTypeDetector.class)
public class WzrdLanguage extends TruffleLanguage<Void> {
    
    public static final String MIME_TYPE = "application/x-wzrd";
    
    @Override
    protected CallTarget parse(ParsingRequest request) throws Exception {
        var node = WzrdScriptTruffleParser.parse(request.getSource().getReader());
        var rootNode = new WzrdRootNode(node, this);
        return rootNode.getCallTarget();
    }
    
    @Override
    protected Void createContext(Env env) {
        return null;
    }
}
