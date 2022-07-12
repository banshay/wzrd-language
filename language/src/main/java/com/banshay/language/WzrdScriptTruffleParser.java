package com.banshay.language;

import com.banshay.language.literal.DoubleLiteral;
import com.banshay.language.literal.IntLiteral;
import com.banshay.language.literal.StringLiteral;
import com.banshay.language.nodes.expressions.AddNodeGen;
import com.banshay.language.nodes.expressions.BindingNodeGen;
import com.banshay.language.nodes.expressions.DivisionNodeGen;
import com.banshay.language.nodes.expressions.MultiplicationNodeGen;
import com.banshay.language.nodes.expressions.VariableNodeGen;
import com.banshay.language.nodes.toplevel.WzrdExpressionNode;
import com.banshay.language.nodes.toplevel.WzrdNode;
import com.banshay.language.parser.WZRDLexer;
import com.banshay.language.parser.WZRDParser;
import com.banshay.language.parser.WZRDParser.BindingExpressionContext;
import java.io.IOException;
import java.io.Reader;
import java.util.List;
import org.antlr.v4.runtime.BailErrorStrategy;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

public class WzrdScriptTruffleParser {

  public static List<WzrdNode> parse(String program) {
    return parse(CharStreams.fromString(program));
  }

  public static List<WzrdNode> parse(Reader program) throws IOException {
    return parse(CharStreams.fromReader(program));
  }

  public static List<WzrdNode> parse(CharStream inputStream) {
    var lexer = new WZRDLexer(inputStream);
    lexer.removeErrorListeners();

    var parser = new WZRDParser(new CommonTokenStream(lexer));
    parser.removeErrorListeners();

    parser.setErrorHandler(new BailErrorStrategy());
    var expression = parser.wzrd().statement();
    return expression.stream().map(WzrdScriptTruffleParser::statementToNode).toList();
  }

  private static WzrdNode statementToNode(WZRDParser.StatementContext statementContext) {
    return switch (statementContext) {
      case WZRDParser.ExpressionStatementContext e -> expressionToNode(e.expression());
      default -> null;
    };
  }

  private static WzrdExpressionNode expressionToNode(
      WZRDParser.ExpressionContext expressionContext) {
    return switch (expressionContext) {
      case WZRDParser.LiteralExpressionContext l -> literalExpressionToLiteralNode(l.literal());
      case WZRDParser.AddExpressionContext a -> AddNodeGen.create(
          expressionToNode(a.expression(0)), expressionToNode(a.expression(1)));
      case WZRDParser.DivisionExpressionContext d -> DivisionNodeGen.create(
          expressionToNode(d.expression(0)), expressionToNode(d.expression(1)));
      case WZRDParser.MultiplicationExpressionContext m -> MultiplicationNodeGen.create(
          expressionToNode(m.expression(0)), expressionToNode(m.expression(1)));
      case WZRDParser.BindingExpressionContext e -> createBinding(e);
      case WZRDParser.VariableExpressionContext v -> VariableNodeGen.create(v.ID().getText());
      default -> null;
    };
  }

  private static WzrdExpressionNode createBinding(BindingExpressionContext bindingExpression) {
    // implement reassignment ID = ID
    return BindingNodeGen.create(
        expressionToNode(bindingExpression.binding().expression()),
        bindingExpression.binding().ID().getText());
  }

  private static WzrdExpressionNode literalExpressionToLiteralNode(
      WZRDParser.LiteralContext literalContext) {
    var number = literalContext.NUMBER();
    if (number != null) {
      return parseIntLiteral(number.getText());
    }
    return new StringLiteral(literalContext.getText());
  }

  private static WzrdExpressionNode parseIntLiteral(String text) {
    try {
      return new IntLiteral(Integer.parseInt(text));
    } catch (NumberFormatException e) {
      return parseDoubleLiteral(text);
    }
  }

  private static WzrdExpressionNode parseDoubleLiteral(String text) {
    return new DoubleLiteral(Double.parseDouble(text));
  }
}
