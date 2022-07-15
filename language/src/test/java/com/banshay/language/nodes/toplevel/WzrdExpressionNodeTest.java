package com.banshay.language.nodes.toplevel;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.graalvm.polyglot.Context;
import org.junit.jupiter.api.Test;

class WzrdExpressionNodeTest {

  //    @Test
  //    void should_add_two_numbers() {
  //      var addNode = AddNodeGen.create(new DoubleLiteral(Integer.MAX_VALUE), new
  // DoubleLiteral(5));
  //      var rootNode = new WzrdRootNode("add", null, addNode);
  //      var callTarget = rootNode.getCallTarget();
  //
  //      var result = callTarget.call(rootNode);
  //
  //      assertEquals(Integer.MAX_VALUE + 5d, result);
  //    }
  //
  //    @Test
  //    void should_use_program_to_add_numbers() {
  //      var wzrdNodes = WzrdScriptTruffleParser.parse("1 + 4 + 5");
  //      var rootNode = new WzrdRootNode("add", null, wzrdNodes.toArray(WzrdNode[]::new));
  //      var callTarget = rootNode.getCallTarget();
  //
  //      var result = callTarget.call(rootNode);
  //
  //      assertEquals(10, result);
  //    }
  //
  //    @Test
  //    void should_parse_boolean_expression() {
  //      var wzrdNodes = WzrdScriptTruffleParser.parse("1==1");
  //      var rootNode = new WzrdRootNode("main", null, wzrdNodes.toArray(WzrdNode[]::new));
  //
  //      var callTarget = rootNode.getCallTarget();
  //      var result = callTarget.call(rootNode);
  //    }

  @Test
  void should_execute_variable_addition() {
    var code =
        """
          fn main(){
            a = 5
            b = 11
            a + b
          }
          """;
    try (var context = Context.create()) {
      var result = context.eval("wzrd", code);
      assertEquals(16, result.asInt());
    }
  }
}
