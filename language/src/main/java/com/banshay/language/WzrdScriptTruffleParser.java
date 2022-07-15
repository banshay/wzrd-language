package com.banshay.language;

import com.banshay.language.literal.DoubleLiteral;
import com.banshay.language.literal.IntLiteral;
import com.banshay.language.literal.StringLiteral;
import com.banshay.language.nodes.WzrdRootNode;
import com.banshay.language.nodes.expressions.AddNodeGen;
import com.banshay.language.nodes.expressions.DivisionNodeGen;
import com.banshay.language.nodes.expressions.FunctionBodyNode;
import com.banshay.language.nodes.expressions.GlobalVariableReadNodeGen;
import com.banshay.language.nodes.expressions.GlobalVariableWriteNodeGen;
import com.banshay.language.nodes.expressions.MultiplicationNodeGen;
import com.banshay.language.nodes.statements.WzrdBlockNode;
import com.banshay.language.nodes.toplevel.WzrdExpressionNode;
import com.banshay.language.nodes.toplevel.WzrdStatementNode;
import com.banshay.language.parser.WZRDLexer;
import com.banshay.language.parser.WZRDParser;
import com.banshay.language.parser.WZRDParser.BindingExpressionContext;
import com.oracle.truffle.api.RootCallTarget;
import com.oracle.truffle.api.frame.FrameDescriptor;
import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.antlr.v4.runtime.BailErrorStrategy;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

public class WzrdScriptTruffleParser {

  private Map<String, Object> functionLocals;
  private FrameDescriptor frameDescriptor;

  //  private static final Map<String, RootCallTarget> allFunctions = new ConcurrentHashMap<>();

  public static Map<String, RootCallTarget> parse(String program, WzrdLanguage language) {
    return parse(CharStreams.fromString(program), language);
  }

  public static Map<String, RootCallTarget> parse(Reader program, WzrdLanguage language)
      throws IOException {
    return parse(CharStreams.fromReader(program), language);
  }

  public static Map<String, RootCallTarget> parse(CharStream inputStream, WzrdLanguage language) {
    var lexer = new WZRDLexer(inputStream);
    lexer.removeErrorListeners();

    var parser = new WZRDParser(new CommonTokenStream(lexer));
    parser.removeErrorListeners();

    parser.setErrorHandler(new BailErrorStrategy());
    var expression = parser.wzrd();
    return functionExpressionToTopLevelFunction(expression, language);
  }

  private static WzrdStatementNode statementToNode(WZRDParser.StatementContext statementContext) {
    return switch (statementContext) {
      case WZRDParser.ExpressionStatementContext e -> expressionToNode(e.expression());
      case WZRDParser.FunctionStatementContext f -> functionExpressionToNode(f);
      default -> null;
    };
  }

  private static WzrdExpressionNode functionExpressionToNode(
      WZRDParser.FunctionStatementContext functionStatementContext) {
    return null;
  }

  private static Map<String, RootCallTarget> functionExpressionToTopLevelFunction(
      WZRDParser.WzrdContext context, WzrdLanguage language) {
    return context.function().stream()
        .map(
            function -> {
              var blockNode =
                  new WzrdBlockNode(
                      blockToStatements(function.block()).toArray(WzrdStatementNode[]::new));
              var bodyNode = new FunctionBodyNode(blockNode);
              return new WzrdRootNode(
                  language,
                  FrameDescriptor.newBuilder().build(),
                  function.functionName().ID().getText(),
                  bodyNode);
            })
        .collect(Collectors.toMap(WzrdRootNode::getName, WzrdRootNode::getCallTarget));
  }

  private static List<WzrdStatementNode> blockToStatements(WZRDParser.BlockContext context) {
    return context.statement().stream().map(WzrdScriptTruffleParser::statementToNode).toList();
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
      case WZRDParser.VariableExpressionContext v -> GlobalVariableReadNodeGen.create(
          v.ID().getText());
      default -> null;
    };
  }

  private static WzrdExpressionNode createBinding(BindingExpressionContext bindingExpression) {
    // implement reassignment ID = ID
    return GlobalVariableWriteNodeGen.create(
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
