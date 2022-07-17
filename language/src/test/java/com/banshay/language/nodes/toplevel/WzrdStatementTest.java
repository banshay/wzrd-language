package com.banshay.language.nodes.toplevel;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.graalvm.polyglot.Context;
import org.junit.jupiter.api.Test;

class WzrdStatementTest {

  @Test
  void should_execute_conditionally() {
    var code =
        """
          fn main(){
            a = 5
            if(a > 4){
              "A is greater than 4"
            } else {
              "A is smaller than 4"
            }
          }
          """;
    try (var context = Context.create()) {
      var result = context.eval("wzrd", code);
      assertEquals("A is greater than 4", result.asString());
    }
  }

  @Test
  void should_execute_loop() {
    var code =
        """
          fn main(){
            a = 1
            i = 0
            while(a < 100){
              i = i + 1
              a = a + a
            }
            i
          }
          """;
    try (var context = Context.create()) {
      var result = context.eval("wzrd", code);
      assertEquals(7, result.asInt());
    }
  }
}
