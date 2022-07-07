package com.banshay.language.nodes;

import com.banshay.language.literal.DoubleLiteral;
import com.banshay.language.parser.WzrdScriptTruffleParser;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WzrdAddNodeTest {
    
    @Test
    void should_add_two_numbers() {
        var addNode = AddNodeGen.create(new DoubleLiteral(Integer.MAX_VALUE), new DoubleLiteral(5));
        var rootNode = new WzrdRootNode(addNode, null);
        var callTarget = rootNode.getCallTarget();
        
        var result = callTarget.call(rootNode);
        
        assertEquals(Integer.MAX_VALUE + 5d, result);
    }
    
    @Test
    void should_use_program_to_add_numbers() throws IOException {
        var wzrdNode = WzrdScriptTruffleParser.parse("1 + 4 + 5");
        var rootNode = new WzrdRootNode(wzrdNode, null);
        var callTarget = rootNode.getCallTarget();
        
        var result = callTarget.call(rootNode);
        
        assertEquals(10, result);
    }
}
