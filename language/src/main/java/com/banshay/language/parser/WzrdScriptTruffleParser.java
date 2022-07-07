package com.banshay.language.parser;

import com.banshay.language.literal.DoubleLiteral;
import com.banshay.language.literal.IntLiteral;
import com.banshay.language.nodes.AddNodeGen;
import com.banshay.language.nodes.WzrdNode;
import org.antlr.v4.runtime.BailErrorStrategy;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.IOException;
import java.io.Reader;

public class WzrdScriptTruffleParser {
    
    public static WzrdNode parse(String program) throws IOException {
        return parse(CharStreams.fromString(program));
    }
    
    public static WzrdNode parse(Reader program) throws IOException {
        return parse(CharStreams.fromReader(program));
    }
    
    public static WzrdNode parse(CharStream inputStream) {
        var lexer = new WZRDLexer(inputStream);
        lexer.removeErrorListeners();
        
        var parser = new WZRDParser(new CommonTokenStream(lexer));
        parser.removeErrorListeners();
        
        parser.setErrorHandler(new BailErrorStrategy());
        var expression = parser.test().testExpression();
        return expressionToTruffleNode(expression);
    }
    
    private static WzrdNode expressionToTruffleNode(WZRDParser.TestExpressionContext expression) {
        return expression instanceof WZRDParser.AddExpressionContext ? addExpressionToAddNode((WZRDParser.AddExpressionContext) expression)
            : literalExpressionToLiteralNode((WZRDParser.LiteralExpressionContext) expression);
    }
    
    private static WzrdNode addExpressionToAddNode(WZRDParser.AddExpressionContext addExpression) {
        return AddNodeGen.create(expressionToTruffleNode(addExpression.left), expressionToTruffleNode(addExpression.right));
    }
    
    private static WzrdNode literalExpressionToLiteralNode(WZRDParser.LiteralExpressionContext literalExpression) {
        var number = literalExpression.literal().NUMBER();
        return parseIntLiteral(number.getText());
    }
    
    private static WzrdNode parseIntLiteral(String text) {
        try {
            return new IntLiteral(Integer.parseInt(text));
        } catch (NumberFormatException e) {
            return parseDoubleLiteral(text);
        }
    }
    
    private static WzrdNode parseDoubleLiteral(String text) {
        return new DoubleLiteral(Double.parseDouble(text));
    }
    
}
