// Generated from VerilogFluigi.g4 by ANTLR 4.5.3

    package org.cellocad.BU.fluigi;

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link VerilogFluigiParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface VerilogFluigiVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link VerilogFluigiParser#root}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRoot(VerilogFluigiParser.RootContext ctx);
	/**
	 * Visit a parse tree produced by {@link VerilogFluigiParser#modDec}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitModDec(VerilogFluigiParser.ModDecContext ctx);
	/**
	 * Visit a parse tree produced by {@link VerilogFluigiParser#stats}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStats(VerilogFluigiParser.StatsContext ctx);
	/**
	 * Visit a parse tree produced by {@link VerilogFluigiParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStat(VerilogFluigiParser.StatContext ctx);
	/**
	 * Visit a parse tree produced by {@link VerilogFluigiParser#decl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDecl(VerilogFluigiParser.DeclContext ctx);
	/**
	 * Visit a parse tree produced by {@link VerilogFluigiParser#assignStat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignStat(VerilogFluigiParser.AssignStatContext ctx);
	/**
	 * Visit a parse tree produced by {@link VerilogFluigiParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExp(VerilogFluigiParser.ExpContext ctx);
	/**
	 * Visit a parse tree produced by {@link VerilogFluigiParser#lhs}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLhs(VerilogFluigiParser.LhsContext ctx);
	/**
	 * Visit a parse tree produced by {@link VerilogFluigiParser#rhs}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRhs(VerilogFluigiParser.RhsContext ctx);
	/**
	 * Visit a parse tree produced by {@link VerilogFluigiParser#op}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOp(VerilogFluigiParser.OpContext ctx);
	/**
	 * Visit a parse tree produced by {@link VerilogFluigiParser#bufferVar}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBufferVar(VerilogFluigiParser.BufferVarContext ctx);
	/**
	 * Visit a parse tree produced by {@link VerilogFluigiParser#var}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVar(VerilogFluigiParser.VarContext ctx);
	/**
	 * Visit a parse tree produced by {@link VerilogFluigiParser#modName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitModName(VerilogFluigiParser.ModNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link VerilogFluigiParser#input}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInput(VerilogFluigiParser.InputContext ctx);
	/**
	 * Visit a parse tree produced by {@link VerilogFluigiParser#output}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOutput(VerilogFluigiParser.OutputContext ctx);
	/**
	 * Visit a parse tree produced by {@link VerilogFluigiParser#finput}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFinput(VerilogFluigiParser.FinputContext ctx);
	/**
	 * Visit a parse tree produced by {@link VerilogFluigiParser#cinput}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCinput(VerilogFluigiParser.CinputContext ctx);
	/**
	 * Visit a parse tree produced by {@link VerilogFluigiParser#foutput}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFoutput(VerilogFluigiParser.FoutputContext ctx);
	/**
	 * Visit a parse tree produced by {@link VerilogFluigiParser#coutput}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCoutput(VerilogFluigiParser.CoutputContext ctx);
	/**
	 * Visit a parse tree produced by {@link VerilogFluigiParser#wire}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWire(VerilogFluigiParser.WireContext ctx);
}