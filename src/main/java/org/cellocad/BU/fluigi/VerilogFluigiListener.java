// Generated from VerilogFluigi.g4 by ANTLR 4.5.3

    package org.cellocad.BU.fluigi;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link VerilogFluigiParser}.
 */
public interface VerilogFluigiListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link VerilogFluigiParser#root}.
	 * @param ctx the parse tree
	 */
	void enterRoot(VerilogFluigiParser.RootContext ctx);
	/**
	 * Exit a parse tree produced by {@link VerilogFluigiParser#root}.
	 * @param ctx the parse tree
	 */
	void exitRoot(VerilogFluigiParser.RootContext ctx);
	/**
	 * Enter a parse tree produced by {@link VerilogFluigiParser#modDec}.
	 * @param ctx the parse tree
	 */
	void enterModDec(VerilogFluigiParser.ModDecContext ctx);
	/**
	 * Exit a parse tree produced by {@link VerilogFluigiParser#modDec}.
	 * @param ctx the parse tree
	 */
	void exitModDec(VerilogFluigiParser.ModDecContext ctx);
	/**
	 * Enter a parse tree produced by {@link VerilogFluigiParser#stats}.
	 * @param ctx the parse tree
	 */
	void enterStats(VerilogFluigiParser.StatsContext ctx);
	/**
	 * Exit a parse tree produced by {@link VerilogFluigiParser#stats}.
	 * @param ctx the parse tree
	 */
	void exitStats(VerilogFluigiParser.StatsContext ctx);
	/**
	 * Enter a parse tree produced by {@link VerilogFluigiParser#stat}.
	 * @param ctx the parse tree
	 */
	void enterStat(VerilogFluigiParser.StatContext ctx);
	/**
	 * Exit a parse tree produced by {@link VerilogFluigiParser#stat}.
	 * @param ctx the parse tree
	 */
	void exitStat(VerilogFluigiParser.StatContext ctx);
	/**
	 * Enter a parse tree produced by {@link VerilogFluigiParser#decl}.
	 * @param ctx the parse tree
	 */
	void enterDecl(VerilogFluigiParser.DeclContext ctx);
	/**
	 * Exit a parse tree produced by {@link VerilogFluigiParser#decl}.
	 * @param ctx the parse tree
	 */
	void exitDecl(VerilogFluigiParser.DeclContext ctx);
	/**
	 * Enter a parse tree produced by {@link VerilogFluigiParser#assignStat}.
	 * @param ctx the parse tree
	 */
	void enterAssignStat(VerilogFluigiParser.AssignStatContext ctx);
	/**
	 * Exit a parse tree produced by {@link VerilogFluigiParser#assignStat}.
	 * @param ctx the parse tree
	 */
	void exitAssignStat(VerilogFluigiParser.AssignStatContext ctx);
	/**
	 * Enter a parse tree produced by {@link VerilogFluigiParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterExp(VerilogFluigiParser.ExpContext ctx);
	/**
	 * Exit a parse tree produced by {@link VerilogFluigiParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitExp(VerilogFluigiParser.ExpContext ctx);
	/**
	 * Enter a parse tree produced by {@link VerilogFluigiParser#lhs}.
	 * @param ctx the parse tree
	 */
	void enterLhs(VerilogFluigiParser.LhsContext ctx);
	/**
	 * Exit a parse tree produced by {@link VerilogFluigiParser#lhs}.
	 * @param ctx the parse tree
	 */
	void exitLhs(VerilogFluigiParser.LhsContext ctx);
	/**
	 * Enter a parse tree produced by {@link VerilogFluigiParser#rhs}.
	 * @param ctx the parse tree
	 */
	void enterRhs(VerilogFluigiParser.RhsContext ctx);
	/**
	 * Exit a parse tree produced by {@link VerilogFluigiParser#rhs}.
	 * @param ctx the parse tree
	 */
	void exitRhs(VerilogFluigiParser.RhsContext ctx);
	/**
	 * Enter a parse tree produced by {@link VerilogFluigiParser#op}.
	 * @param ctx the parse tree
	 */
	void enterOp(VerilogFluigiParser.OpContext ctx);
	/**
	 * Exit a parse tree produced by {@link VerilogFluigiParser#op}.
	 * @param ctx the parse tree
	 */
	void exitOp(VerilogFluigiParser.OpContext ctx);
	/**
	 * Enter a parse tree produced by {@link VerilogFluigiParser#modName}.
	 * @param ctx the parse tree
	 */
	void enterModName(VerilogFluigiParser.ModNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link VerilogFluigiParser#modName}.
	 * @param ctx the parse tree
	 */
	void exitModName(VerilogFluigiParser.ModNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link VerilogFluigiParser#input}.
	 * @param ctx the parse tree
	 */
	void enterInput(VerilogFluigiParser.InputContext ctx);
	/**
	 * Exit a parse tree produced by {@link VerilogFluigiParser#input}.
	 * @param ctx the parse tree
	 */
	void exitInput(VerilogFluigiParser.InputContext ctx);
	/**
	 * Enter a parse tree produced by {@link VerilogFluigiParser#output}.
	 * @param ctx the parse tree
	 */
	void enterOutput(VerilogFluigiParser.OutputContext ctx);
	/**
	 * Exit a parse tree produced by {@link VerilogFluigiParser#output}.
	 * @param ctx the parse tree
	 */
	void exitOutput(VerilogFluigiParser.OutputContext ctx);
	/**
	 * Enter a parse tree produced by {@link VerilogFluigiParser#wire}.
	 * @param ctx the parse tree
	 */
	void enterWire(VerilogFluigiParser.WireContext ctx);
	/**
	 * Exit a parse tree produced by {@link VerilogFluigiParser#wire}.
	 * @param ctx the parse tree
	 */
	void exitWire(VerilogFluigiParser.WireContext ctx);
}