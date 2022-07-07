package com.banshay.language;

import org.graalvm.polyglot.Context;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WzrdLanguageTest {
    
    
    @Test
    void should_execute_wzrd_code() {
        var context = Context.create();
        var result = context.eval("wzrd", "6 + 12 + 2");
        
        assertEquals(20, result.asDouble(), 0.0);
    }
}
