package com.banshay.language.nodes.expressions;

import com.banshay.language.nodes.toplevel.WzrdExpressionNode;
import com.oracle.truffle.api.dsl.NodeChild;

@NodeChild("leftNode")
@NodeChild("rightNode")
public abstract class BooleanNode extends WzrdExpressionNode {

  public boolean evaluate(boolean leftNode, boolean rightNode) {
    return leftNode == rightNode;
  }
}
