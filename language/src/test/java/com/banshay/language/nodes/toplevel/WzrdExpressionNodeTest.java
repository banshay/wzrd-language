package com.banshay.language.nodes.toplevel;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.graalvm.polyglot.Context;
import org.junit.jupiter.api.Test;

class WzrdExpressionNodeTest {


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

  @Test
  void should_execute_variable_addition_with_fn() {
    var code =
        """
          fn main(){
            add(5, 3)
          }
          
          fn add(a, b){
            a + b
          }
          """;
    try (var context = Context.create()) {
      var result = context.eval("wzrd", code);
      assertEquals(8, result.asInt());
    }
  }
}
