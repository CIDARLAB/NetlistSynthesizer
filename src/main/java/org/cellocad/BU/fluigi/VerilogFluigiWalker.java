/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cellocad.BU.fluigi;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

/**
 *
 * @author prash
 */
public class VerilogFluigiWalker implements VerilogFluigiListener {
    
    public FluigiCircuitDetails details;
    
    public VerilogFluigiWalker(){
        details = new FluigiCircuitDetails();
    }
    
    @Override
    public void enterRoot(VerilogFluigiParser.RootContext ctx) {
    }

    @Override
    public void exitRoot(VerilogFluigiParser.RootContext ctx) {
    }

    @Override
    public void enterModDec(VerilogFluigiParser.ModDecContext ctx) {
    }

    @Override
    public void exitModDec(VerilogFluigiParser.ModDecContext ctx) {
    }

    @Override
    public void enterStats(VerilogFluigiParser.StatsContext ctx) {
    }

    @Override
    public void exitStats(VerilogFluigiParser.StatsContext ctx) {
    }

    @Override
    public void enterStat(VerilogFluigiParser.StatContext ctx) {
    }

    @Override
    public void exitStat(VerilogFluigiParser.StatContext ctx) {
    }

    @Override
    public void enterDecl(VerilogFluigiParser.DeclContext ctx) {
    }

    @Override
    public void exitDecl(VerilogFluigiParser.DeclContext ctx) {
    }

    @Override
    public void enterAssignStat(VerilogFluigiParser.AssignStatContext ctx) {
    }

    @Override
    public void exitAssignStat(VerilogFluigiParser.AssignStatContext ctx) {
    }

    @Override
    public void enterExp(VerilogFluigiParser.ExpContext ctx) {
    }

    @Override
    public void exitExp(VerilogFluigiParser.ExpContext ctx) {
    }

    @Override
    public void enterLhs(VerilogFluigiParser.LhsContext ctx) {
    }

    @Override
    public void exitLhs(VerilogFluigiParser.LhsContext ctx) {
    }

    @Override
    public void enterRhs(VerilogFluigiParser.RhsContext ctx) {
    }

    @Override
    public void exitRhs(VerilogFluigiParser.RhsContext ctx) {
    }

    @Override
    public void enterOp(VerilogFluigiParser.OpContext ctx) {
    }

    @Override
    public void exitOp(VerilogFluigiParser.OpContext ctx) {
    }

    @Override
    public void enterModName(VerilogFluigiParser.ModNameContext ctx) {
        details.modulename = ctx.getText();
    }

    @Override
    public void exitModName(VerilogFluigiParser.ModNameContext ctx) {
    }

    @Override
    public void enterInput(VerilogFluigiParser.InputContext ctx) {
        if(!details.inputs.contains(ctx.getText())){
            details.inputs.add(ctx.getText());
        }
    }

    @Override
    public void exitInput(VerilogFluigiParser.InputContext ctx) {
    }

    @Override
    public void enterOutput(VerilogFluigiParser.OutputContext ctx) {
        if(!details.outputs.contains(ctx.getText())){
            details.outputs.add(ctx.getText());
        }
    }

    @Override
    public void exitOutput(VerilogFluigiParser.OutputContext ctx) {
    }

    @Override
    public void enterWire(VerilogFluigiParser.WireContext ctx) {
        if(!details.wires.contains(ctx.getText())){
            details.wires.add(ctx.getText());
        }
    }

    @Override
    public void exitWire(VerilogFluigiParser.WireContext ctx) {
    }

    @Override
    public void visitTerminal(TerminalNode tn) {
    }

    @Override
    public void visitErrorNode(ErrorNode en) {
    }

    @Override
    public void enterEveryRule(ParserRuleContext prc) {
    }

    @Override
    public void exitEveryRule(ParserRuleContext prc) {
    }

    @Override
    public void enterVar(VerilogFluigiParser.VarContext ctx) {
    }

    @Override
    public void exitVar(VerilogFluigiParser.VarContext ctx) {
    }
    
}
