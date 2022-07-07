// Generated from G:/Github/WZRD/language/src/main/java/com/banshay/language/parser\WZRD.g4 by ANTLR 4.10.1
package com.banshay.language.parser;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link WZRDParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface WZRDVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link WZRDParser#test}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTest(WZRDParser.TestContext ctx);
	/**
	 * Visit a parse tree produced by the {@code AddExpression}
	 * labeled alternative in {@link WZRDParser#testExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAddExpression(WZRDParser.AddExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code LiteralExpression}
	 * labeled alternative in {@link WZRDParser#testExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiteralExpression(WZRDParser.LiteralExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link WZRDParser#wzrd}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWzrd(WZRDParser.WzrdContext ctx);
	/**
	 * Visit a parse tree produced by {@link WZRDParser#class}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClass(WZRDParser.ClassContext ctx);
	/**
	 * Visit a parse tree produced by {@link WZRDParser#dependency}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDependency(WZRDParser.DependencyContext ctx);
	/**
	 * Visit a parse tree produced by {@link WZRDParser#function}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunction(WZRDParser.FunctionContext ctx);
	/**
	 * Visit a parse tree produced by {@link WZRDParser#block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlock(WZRDParser.BlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link WZRDParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(WZRDParser.StatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link WZRDParser#object}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitObject(WZRDParser.ObjectContext ctx);
	/**
	 * Visit a parse tree produced by {@link WZRDParser#member}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMember(WZRDParser.MemberContext ctx);
	/**
	 * Visit a parse tree produced by {@link WZRDParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression(WZRDParser.ExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link WZRDParser#literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiteral(WZRDParser.LiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link WZRDParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitType(WZRDParser.TypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link WZRDParser#primitive}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimitive(WZRDParser.PrimitiveContext ctx);
}