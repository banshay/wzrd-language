package com.banshay.language.literal;

import com.banshay.language.nodes.WzrdNode;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.UnexpectedResultException;

public final class DoubleLiteral extends WzrdNode {
    
    private final double value;
    
    public DoubleLiteral(double value) {
        this.value = value;
    }
    
    @Override
    public int executeInt(VirtualFrame frame) throws UnexpectedResultException {
        throw new UnexpectedResultException(value);
    }
    
    @Override
    public double executeDouble(VirtualFrame frame) {
        return value;
    }
    
    @Override
    public Object executeGeneric(VirtualFrame frame) {
        return value;
    }
}
