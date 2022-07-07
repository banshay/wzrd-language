package com.banshay.language.nodes;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.UnexpectedResultException;

public abstract class WzrdNode extends Node {
    
    public abstract int executeInt(VirtualFrame frame) throws UnexpectedResultException;
    
    public abstract double executeDouble(VirtualFrame frame);
    
    public abstract Object executeGeneric(VirtualFrame frame);
}
