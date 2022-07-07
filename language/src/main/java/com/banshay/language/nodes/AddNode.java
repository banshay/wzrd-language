package com.banshay.language.nodes;

import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;

@NodeChild("leftNode")
@NodeChild("rightNode")
public abstract class AddNode extends WzrdNode {
    
    @Specialization(rewriteOn = ArithmeticException.class)
    protected int addInt(int leftNode, int rightNode) {
        return Math.addExact(leftNode, rightNode);
    }
    
    @Specialization(replaces = "addInt")
    protected double addDouble(double leftNode, double rightNode) {
        return leftNode + rightNode;
    }
}
