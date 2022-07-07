package com.banshay.language.nodes;

import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.RootNode;

public class WzrdRootNode extends RootNode {
    
    @Child
    private WzrdNode rootNode;
    
    public WzrdRootNode(WzrdNode wzrdNode, TruffleLanguage<Void> language) {
        super(language);
        this.rootNode = wzrdNode;
    }
    
    @Override
    public Object execute(VirtualFrame frame) {
        return this.rootNode.executeGeneric(frame);
    }
}
