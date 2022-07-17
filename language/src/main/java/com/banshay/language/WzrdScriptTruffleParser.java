package com.banshay.language;

import com.banshay.language.literal.DoubleLiteral;
import com.banshay.language.literal.IntLiteral;
import com.banshay.language.literal.StringLiteral;
import com.banshay.language.nodes.WzrdRootNode;
import com.banshay.language.nodes.expressions.AddNodeGen;
import com.banshay.language.nodes.expressions.ArgumentNode;
import com.banshay.language.nodes.expressions.DivisionNodeGen;
import com.banshay.language.nodes.expressions.EqualsNodeGen;
import com.banshay.language.nodes.expressions.FunctionBodyNode;
import com.banshay.language.nodes.expressions.FunctionStatementNode;
import com.banshay.language.nodes.expressions.GlobalVariableWriteNodeGen;
import com.banshay.language.nodes.expressions.GreaterEqualsNodeGen;
import com.banshay.language.nodes.expressions.GreaterNodeGen;
import com.banshay.language.nodes.expressions.InvokeNode;
import com.banshay.language.nodes.expressions.LessEqualsNodeGen;
import com.banshay.language.nodes.expressions.LessNodeGen;
import com.banshay.language.nodes.expressions.LocalVariableReadNodeGen;
import com.banshay.language.nodes.expressions.LocalVariableWriteNodeGen;
import com.banshay.language.nodes.expressions.LogicalAndNode;
import com.banshay.language.nodes.expressions.LogicalOrNode;
import com.banshay.language.nodes.expressions.MultiplicationNodeGen;
import com.banshay.language.nodes.statements.IfStatement;
import com.banshay.language.nodes.statements.WhileStatement;
import com.banshay.language.nodes.statements.WzrdBlockNode;
import com.banshay.language.nodes.toplevel.WzrdExpressionNode;
import com.banshay.language.nodes.toplevel.WzrdStatementNode;
import com.banshay.language.parser.WZRDLexer;
import com.banshay.language.parser.WZRDParser;
import com.banshay.language.parser.WZRDParser.BindingExpressionContext;
import com.banshay.language.parser.WZRDParser.BlockContext;
import com.banshay.language.parser.WZRDParser.BooleanExpressionContext;
import com.banshay.language.parser.WZRDParser.ExpressionContext;
import com.banshay.language.parser.WZRDParser.IfStatementContext;
import com.banshay.language.parser.WZRDParser.StatementContext;
import com.banshay.language.parser.WZRDParser.WhileStatementContext;
import com.oracle.truffle.api.RootCallTarget;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.FrameDescriptor.Builder;
import com.oracle.truffle.api.frame.FrameSlotKind;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.antlr.v4.runtime.BailErrorStrategy;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

public class WzrdScriptTruffleParser {

  static class Scope {
    protected final Scope outer;
    protected final Map<String, Integer> locals;

    public Scope(Scope outer) {
      this.outer = outer;
      this.locals = new HashMap<>();
    }

    public Integer find(String name) {
      // no global support
      var result = locals.get(name);
      if (result == null && outer != null) {
        return outer.find(name);
      }
      return result;
    }
  }

  private static int addSlot(String name, FrameDescriptor.Builder frameDescBuilder, Scope scope) {
    var slot = frameDescBuilder.addSlot(FrameSlotKind.Illegal, name, null);
    scope.locals.put(name, slot);
    return slot;
  }

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

  private static WzrdStatementNode statementToNode(
      StatementContext statementContext, Builder frameDescriptorBuilder, Scope scope) {
    return switch (statementContext) {
      case WZRDParser.ExpressionStatementContext e -> expressionToNode(
          e.expression(), frameDescriptorBuilder, scope);
      case WZRDParser.FunctionStatementContext f -> functionStatementToNode(f);
      case WZRDParser.IfStatementContext ifStatementContext -> ifStatementToNode(
          ifStatementContext, frameDescriptorBuilder, scope);
      case WZRDParser.WhileStatementContext whileStatementContext -> whileStatementToNode(
          whileStatementContext, frameDescriptorBuilder, scope);
      default -> null;
    };
  }

  private static WzrdStatementNode whileStatementToNode(
      WhileStatementContext statementContext, Builder frameDescriptorBuilder, Scope scope) {
    var whileContext = statementContext.whileExpression();
    var whileExpressionNode =
        expressionToNode(whileContext.expression(), frameDescriptorBuilder, scope);
    var bodyNode =
        new WzrdBlockNode(
            blockToStatements(whileContext.block(), frameDescriptorBuilder, new Scope(scope))
                .toArray(WzrdStatementNode[]::new));
    return new WhileStatement(whileExpressionNode, bodyNode);
  }

  private static WzrdStatementNode ifStatementToNode(
      IfStatementContext ifStatement, Builder frameDescriptorBuilder, Scope scope) {
    var ifEx = ifStatement.ifExpression();
    var ifBlockContext = ifEx.block(0);
    var ifBlock =
        new WzrdBlockNode(
            blockToStatements(ifBlockContext, frameDescriptorBuilder, new Scope(scope))
                .toArray(WzrdStatementNode[]::new));

    var elseBlockContext = ifEx.block(1);
    WzrdStatementNode elseBlock = null;
    if (elseBlockContext != null) {
      elseBlock =
          new WzrdBlockNode(
              blockToStatements(elseBlockContext, frameDescriptorBuilder, new Scope(scope))
                  .toArray(WzrdStatementNode[]::new));
    }

    return new IfStatement(
        expressionToNode(ifEx.expression(), frameDescriptorBuilder, scope), ifBlock, elseBlock);
  }

  private static WzrdExpressionNode functionStatementToNode(
      WZRDParser.FunctionStatementContext functionStatementContext) {
    var arguments =
        functionStatementContext.functionLiteral().expression().stream()
            .map(expressionContext -> expressionToNode(expressionContext, null, null))
            .toArray(WzrdExpressionNode[]::new);
    return new InvokeNode(
        new FunctionStatementNode(functionStatementContext.functionLiteral().ID().getText()),
        arguments);
  }

  private static Map<String, RootCallTarget> functionExpressionToTopLevelFunction(
      WZRDParser.WzrdContext context, WzrdLanguage language) {
    return context.function().stream()
        .map(
            function -> {
              var frameDescriptorBuilder = FrameDescriptor.newBuilder();
              var scope = new Scope(null);
              List<WzrdStatementNode> args = new ArrayList<>();
              for (int i = 0; i < function.ID().size(); i++) {
                var fnName = function.ID(i).getText();
                var slot = addSlot(fnName, frameDescriptorBuilder, scope);
                var argumentNode = new ArgumentNode(i);
                args.add(LocalVariableWriteNodeGen.create(argumentNode, slot, fnName, true));
              }
              var wzrdStatementNodes =
                  blockToStatements(function.block(), frameDescriptorBuilder, scope);
              args.addAll(wzrdStatementNodes);

              var blockNode = new WzrdBlockNode(args.toArray(WzrdStatementNode[]::new));
              var bodyNode = new FunctionBodyNode(blockNode);
              return new WzrdRootNode(
                  language,
                  frameDescriptorBuilder.build(),
                  function.functionName().ID().getText(),
                  bodyNode);
            })
        .collect(Collectors.toMap(WzrdRootNode::getName, WzrdRootNode::getCallTarget));
  }

  private static List<WzrdStatementNode> blockToStatements(
      BlockContext context, Builder frameDescriptorBuilder, Scope scope) {
    return context.statement().stream()
        .map(statementContext -> statementToNode(statementContext, frameDescriptorBuilder, scope))
        .toList();
  }

  private static WzrdExpressionNode expressionToNode(
      ExpressionContext expressionContext, Builder frameDescriptorBuilder, Scope scope) {
    return switch (expressionContext) {
      case WZRDParser.LiteralExpressionContext l -> literalExpressionToLiteralNode(l.literal());
      case WZRDParser.AddExpressionContext a -> AddNodeGen.create(
          expressionToNode(a.expression(0), frameDescriptorBuilder, scope),
          expressionToNode(a.expression(1), frameDescriptorBuilder, scope));
      case WZRDParser.DivisionExpressionContext d -> DivisionNodeGen.create(
          expressionToNode(d.expression(0), frameDescriptorBuilder, scope),
          expressionToNode(d.expression(1), frameDescriptorBuilder, scope));
      case WZRDParser.MultiplicationExpressionContext m -> MultiplicationNodeGen.create(
          expressionToNode(m.expression(0), frameDescriptorBuilder, scope),
          expressionToNode(m.expression(1), frameDescriptorBuilder, scope));
      case WZRDParser.BindingExpressionContext e -> createBinding(e, frameDescriptorBuilder, scope);
      case WZRDParser.VariableExpressionContext v -> LocalVariableReadNodeGen.create(
          scope.find(v.ID().getText()));
      case WZRDParser.BooleanExpressionContext booleanExpressionContext -> booleanExpressionToNode(
          booleanExpressionContext, frameDescriptorBuilder, scope);
      case WZRDParser.NestedExpressionContext nestedExpression -> expressionToNode(
          nestedExpression.expression(), frameDescriptorBuilder, scope);
      default -> null;
    };
  }

  private static WzrdExpressionNode booleanExpressionToNode(
      BooleanExpressionContext booleanExpressionContext,
      Builder frameDescriptorBuilder,
      Scope scope) {
    var left = booleanExpressionContext.expression(0);
    var right = booleanExpressionContext.expression(1);
    var token = booleanExpressionContext.comparisonToken().getText();
    return switch (token) {
      case "==" -> EqualsNodeGen.create(
          expressionToNode(left, frameDescriptorBuilder, scope),
          expressionToNode(right, frameDescriptorBuilder, scope));
      case "<=" -> LessEqualsNodeGen.create(
          expressionToNode(left, frameDescriptorBuilder, scope),
          expressionToNode(right, frameDescriptorBuilder, scope));
      case ">=" -> GreaterEqualsNodeGen.create(
          expressionToNode(left, frameDescriptorBuilder, scope),
          expressionToNode(right, frameDescriptorBuilder, scope));
      case "<" -> LessNodeGen.create(
          expressionToNode(left, frameDescriptorBuilder, scope),
          expressionToNode(right, frameDescriptorBuilder, scope));
      case ">" -> GreaterNodeGen.create(
          expressionToNode(left, frameDescriptorBuilder, scope),
          expressionToNode(right, frameDescriptorBuilder, scope));
      case "&&" -> new LogicalAndNode(
          expressionToNode(left, frameDescriptorBuilder, scope),
          expressionToNode(right, frameDescriptorBuilder, scope));
      case "||" -> new LogicalOrNode(
          expressionToNode(left, frameDescriptorBuilder, scope),
          expressionToNode(right, frameDescriptorBuilder, scope));
      default -> null;
    };
  }

  private static WzrdExpressionNode createBinding(
      BindingExpressionContext bindingExpression,
      FrameDescriptor.Builder frameDescriptorBuilder,
      Scope scope) {
    var name = bindingExpression.binding().ID().getText();
    if (frameDescriptorBuilder == null) {
      return GlobalVariableWriteNodeGen.create(
          expressionToNode(bindingExpression.binding().expression(), null, null), name);
    }
    var slot = scope.find(name);
    if (slot == null) {
      slot = addSlot(name, frameDescriptorBuilder, scope);
    }
    return LocalVariableWriteNodeGen.create(
        expressionToNode(bindingExpression.binding().expression(), frameDescriptorBuilder, scope),
        slot,
        name,
        true);
  }

  private static WzrdExpressionNode literalExpressionToLiteralNode(
      WZRDParser.LiteralContext literalContext) {
    return switch (literalContext) {
      case WZRDParser.NumberLiteralContext numberLiteralContext -> parseNumberLiteral(
          numberLiteralContext.getText());
      case WZRDParser.StringLiteralContext stringLiteralContext -> new StringLiteral(
          stringLiteralContext.STR().getText().replace("\"", ""));
      default -> new StringLiteral(literalContext.getText().replace("\"", ""));
    };
  }

  private static WzrdExpressionNode parseNumberLiteral(String text) {
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
