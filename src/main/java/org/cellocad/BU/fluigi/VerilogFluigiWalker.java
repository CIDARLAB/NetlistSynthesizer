/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cellocad.BU.fluigi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.cellocad.BU.dom.DGate;
import org.cellocad.BU.dom.DGateType;
import org.cellocad.BU.dom.DWire;
import org.cellocad.BU.dom.DWireType;
import org.cellocad.BU.dom.LayerType;

/**
 *
 * @author prash
 */
public class VerilogFluigiWalker implements VerilogFluigiListener {
    
    public FluigiCircuitDetails details;
    public Map<String,DWire> wireMap;
    public List<DGate> netlist;
    public DGate currentGate;
    private boolean inLHS = false;
    private boolean inRHS = false;
    
    public VerilogFluigiWalker(){
        details = new FluigiCircuitDetails();
        wireMap = new HashMap<>();
        netlist = new ArrayList<>();
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
        currentGate = new DGate();
        currentGate.gtype = DGateType.uF;
    }

    @Override
    public void exitAssignStat(VerilogFluigiParser.AssignStatContext ctx) {
        netlist.add(currentGate);
    }

    @Override
    public void enterExp(VerilogFluigiParser.ExpContext ctx) {
    }

    @Override
    public void exitExp(VerilogFluigiParser.ExpContext ctx) {
    }

    @Override
    public void enterLhs(VerilogFluigiParser.LhsContext ctx) {
        inLHS = true;
    }

    @Override
    public void exitLhs(VerilogFluigiParser.LhsContext ctx) {
        inLHS = false;
    }

    @Override
    public void enterRhs(VerilogFluigiParser.RhsContext ctx) {
        inRHS = true;
    }

    @Override
    public void exitRhs(VerilogFluigiParser.RhsContext ctx) {
        inRHS = false;
    }

    @Override
    public void enterOp(VerilogFluigiParser.OpContext ctx) {
        currentGate.symbol = ctx.getText();
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
            DWire inp = new DWire();
            inp.name = ctx.getText();
            inp.wtype = DWireType.input;
            currentGate = new DGate();
            currentGate.gtype = DGateType.uF_IN;
            currentGate.output = inp;
            wireMap.put(ctx.getText(), inp);        
        }
    }

    @Override
    public void exitInput(VerilogFluigiParser.InputContext ctx) {
    }

    @Override
    public void enterOutput(VerilogFluigiParser.OutputContext ctx) {
        if(!details.outputs.contains(ctx.getText())){
            details.outputs.add(ctx.getText());
            DWire out = new DWire(); 
           out.name = ctx.getText();
            out.wtype = DWireType.output;
            currentGate = new DGate();
            currentGate.gtype = DGateType.uF_OUT;
            currentGate.input.add(out);
            wireMap.put(ctx.getText(), out);
        }
    }

    @Override
    public void exitOutput(VerilogFluigiParser.OutputContext ctx) {
    }

    @Override
    public void enterWire(VerilogFluigiParser.WireContext ctx) {
        if(!details.wires.contains(ctx.getText())){
            details.wires.add(ctx.getText());
            DWire wire = new DWire();
            wire.name = ctx.getText();
            wire.wtype = DWireType.connector;
            wireMap.put(ctx.getText(), wire);
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
        if(inLHS){
            currentGate.output = wireMap.get(ctx.getText());
        }
        if(inRHS){
            currentGate.input.add(wireMap.get(ctx.getText()));
        }
    }

    @Override
    public void exitVar(VerilogFluigiParser.VarContext ctx) {
    }

    @Override
    public void enterFinput(VerilogFluigiParser.FinputContext ctx) {
        if (!details.inputs.contains(ctx.getText())) {
            details.inputs.add(ctx.getText());
            DWire inp = new DWire();
            inp.name = ctx.getText();
            inp.wtype = DWireType.finput;
            inp.layer = LayerType.flow;       //label finput channels as flow layer
            wireMap.put(ctx.getText(), inp);
        }
    }

    @Override
    public void exitFinput(VerilogFluigiParser.FinputContext ctx) {
    }

    @Override
    public void enterCinput(VerilogFluigiParser.CinputContext ctx) {
        if (!details.inputs.contains(ctx.getText())) {
            details.inputs.add(ctx.getText());
            DWire inp = new DWire();
            inp.name = ctx.getText();
            inp.wtype = DWireType.cinput;
            inp.layer = LayerType.control;    //label cinput channels as control layer
            wireMap.put(ctx.getText(), inp);
        }
    }

    @Override
    public void exitCinput(VerilogFluigiParser.CinputContext ctx) {
    }

    @Override
    public void enterFoutput(VerilogFluigiParser.FoutputContext ctx) {
        if (!details.outputs.contains(ctx.getText())) {
            details.outputs.add(ctx.getText());
            DWire out = new DWire();
            out.name = ctx.getText();
            out.wtype = DWireType.foutput;
            out.layer = LayerType.flow;   //label foutput channels as flow layer
            wireMap.put(ctx.getText(), out);
        }
    }

    @Override
    public void exitFoutput(VerilogFluigiParser.FoutputContext ctx) {
    }

    @Override
    public void enterCoutput(VerilogFluigiParser.CoutputContext ctx) {
        if (!details.outputs.contains(ctx.getText())) {
            details.outputs.add(ctx.getText());
            DWire out = new DWire();
            out.name = ctx.getText();
            out.wtype = DWireType.coutput;
            out.layer = LayerType.control;    //label coutput channels as control layer
            wireMap.put(ctx.getText(), out);
        }
    }

    @Override
    public void exitCoutput(VerilogFluigiParser.CoutputContext ctx) {
    }

    @Override
    public void enterBufferVar(VerilogFluigiParser.BufferVarContext ctx) {
        this.currentGate.gtype = DGateType.BUF;
    }

    @Override
    public void exitBufferVar(VerilogFluigiParser.BufferVarContext ctx) {
    }

    @Override
    public void enterCchannel(VerilogFluigiParser.CchannelContext ctx) {
        if (!details.wires.contains(ctx.getText())) {
            details.wires.add(ctx.getText());
            DWire wire = new DWire();
            wire.name = ctx.getText();
            wire.wtype = DWireType.cchannel;
            wire.layer = LayerType.control;   //label cchannel channels as control layer
            wireMap.put(ctx.getText(), wire);
        }
    }

    @Override
    public void exitCchannel(VerilogFluigiParser.CchannelContext ctx) {
    }

    @Override
    public void enterFchannel(VerilogFluigiParser.FchannelContext ctx) {
        if (!details.wires.contains(ctx.getText())) {
            details.wires.add(ctx.getText());
            DWire wire = new DWire();
            wire.name = ctx.getText();
            wire.wtype = DWireType.fchannel;
            wire.layer = LayerType.flow;  //label fchannel channels as flow layer
            wireMap.put(ctx.getText(), wire);
        }
    }

    @Override
    public void exitFchannel(VerilogFluigiParser.FchannelContext ctx) {
    }
    
}
