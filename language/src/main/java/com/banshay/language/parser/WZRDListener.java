// Generated from G:/Github/WZRD/language/src/main/java/com/banshay/language/parser\WZRD.g4 by ANTLR 4.10.1
package com.banshay.language.parser;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link WZRDParser}.
 */
public interface WZRDListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link WZRDParser#test}.
	 * @param ctx the parse tree
	 */
	void enterTest(WZRDParser.TestContext ctx);
	/**
	 * Exit a parse tree produced by {@link WZRDParser#test}.
	 * @param ctx the parse tree
	 */
	void exitTest(WZRDParser.TestContext ctx);
	/**
	 * Enter a parse tree produced by the {@code AddExpression}
	 * labeled alternative in {@link WZRDParser#testExpression}.
	 * @param ctx the parse tree
	 */
	void enterAddExpression(WZRDParser.AddExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code AddExpression}
	 * labeled alternative in {@link WZRDParser#testExpression}.
	 * @param ctx the parse tree
	 */
	void exitAddExpression(WZRDParser.AddExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code LiteralExpression}
	 * labeled alternative in {@link WZRDParser#testExpression}.
	 * @param ctx the parse tree
	 */
	void enterLiteralExpression(WZRDParser.LiteralExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code LiteralExpression}
	 * labeled alternative in {@link WZRDParser#testExpression}.
	 * @param ctx the parse tree
	 */
	void exitLiteralExpression(WZRDParser.LiteralExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link WZRDParser#wzrd}.
	 * @param ctx the parse tree
	 */
	void enterWzrd(WZRDParser.WzrdContext ctx);
	/**
	 * Exit a parse tree produced by {@link WZRDParser#wzrd}.
	 * @param ctx the parse tree
	 */
	void exitWzrd(WZRDParser.WzrdContext ctx);
	/**
	 * Enter a parse tree produced by {@link WZRDParser#class}.
	 * @param ctx the parse tree
	 */
	void enterClass(WZRDParser.ClassContext ctx);
	/**
	 * Exit a parse tree produced by {@link WZRDParser#class}.
	 * @param ctx the parse tree
	 */
	void exitClass(WZRDParser.ClassContext ctx);
	/**
	 * Enter a parse tree produced by {@link WZRDParser#dependency}.
	 * @param ctx the parse tree
	 */
	void enterDependency(WZRDParser.DependencyContext ctx);
	/**
	 * Exit a parse tree produced by {@link WZRDParser#dependency}.
	 * @param ctx the parse tree
	 */
	void exitDependency(WZRDParser.DependencyContext ctx);
	/**
	 * Enter a parse tree produced by {@link WZRDParser#function}.
	 * @param ctx the parse tree
	 */
	void enterFunction(WZRDParser.FunctionContext ctx);
	/**
	 * Exit a parse tree produced by {@link WZRDParser#function}.
	 * @param ctx the parse tree
	 */
	void exitFunction(WZRDParser.FunctionContext ctx);
	/**
	 * Enter a parse tree produced by {@link WZRDParser#block}.
	 * @param ctx the parse tree
	 */
	void enterBlock(WZRDParser.BlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link WZRDParser#block}.
	 * @param ctx the parse tree
	 */
	void exitBlock(WZRDParser.BlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link WZRDParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(WZRDParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link WZRDParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(WZRDParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link WZRDParser#object}.
	 * @param ctx the parse tree
	 */
	void enterObject(WZRDParser.ObjectContext ctx);
	/**
	 * Exit a parse tree produced by {@link WZRDParser#object}.
	 * @param ctx the parse tree
	 */
	void exitObject(WZRDParser.ObjectContext ctx);
	/**
	 * Enter a parse tree produced by {@link WZRDParser#member}.
	 * @param ctx the parse tree
	 */
	void enterMember(WZRDParser.MemberContext ctx);
	/**
	 * Exit a parse tree produced by {@link WZRDParser#member}.
	 * @param ctx the parse tree
	 */
	void exitMember(WZRDParser.MemberContext ctx);
	/**
	 * Enter a parse tree produced by {@link WZRDParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(WZRDParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link WZRDParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(WZRDParser.ExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link WZRDParser#literal}.
	 * @param ctx the parse tree
	 */
	void enterLiteral(WZRDParser.LiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link WZRDParser#literal}.
	 * @param ctx the parse tree
	 */
	void exitLiteral(WZRDParser.LiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link WZRDParser#type}.
	 * @param ctx the parse tree
	 */
	void enterType(WZRDParser.TypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link WZRDParser#type}.
	 * @param ctx the parse tree
	 */
	void exitType(WZRDParser.TypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link WZRDParser#primitive}.
	 * @param ctx the parse tree
	 */
	void enterPrimitive(WZRDParser.PrimitiveContext ctx);
	/**
	 * Exit a parse tree produced by {@link WZRDParser#primitive}.
	 * @param ctx the parse tree
	 */
	void exitPrimitive(WZRDParser.PrimitiveContext ctx);
}