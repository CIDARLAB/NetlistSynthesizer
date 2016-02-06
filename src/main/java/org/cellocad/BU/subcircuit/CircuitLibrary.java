/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cellocad.BU.subcircuit;

import java.util.ArrayList;
import java.util.List;
import org.cellocad.BU.parseVerilog.Convert;
import org.cellocad.BU.simulators.BooleanSimulator;
import org.cellocad.BU.dom.DGate;
import org.cellocad.BU.dom.DWire;
import org.cellocad.BU.dom.DWireType;
import org.cellocad.BU.netsynth.NetSynth;
import org.cellocad.BU.netsynth.NetSynthSwitch;

/**
 *
 * @author prash
 */
public class CircuitLibrary {
    public List<DGate> netlist;
    public List<String> inputs;
    public List<String> outputs;
    public List<String> truthtables;
    public List<NetSynthSwitch> switches;
    
    public CircuitLibrary(){
        netlist = new ArrayList<DGate>();
        inputs = new ArrayList<String>();
        outputs = new ArrayList<String>();
        truthtables = new ArrayList<String>();
        switches = new ArrayList<NetSynthSwitch>();
    }
    public CircuitLibrary(List<DGate> _netlist){
        netlist = new ArrayList<DGate>();
        inputs = new ArrayList<String>();
        outputs = new ArrayList<String>();
        truthtables = new ArrayList<String>();
        switches = new ArrayList<NetSynthSwitch>();
        for(DGate gate:_netlist){
            netlist.add(gate);
        }
        assignGIndex();
        inputs = getInputs();
        outputs = getOutputs();
        truthtables = BooleanSimulator.getTruthTable(netlist, inputs);
        
    }
    public CircuitLibrary(List<DGate> _netlist,List<String> inputOrder){
        netlist = new ArrayList<DGate>();
        inputs = new ArrayList<String>();
        outputs = new ArrayList<String>();
        truthtables = new ArrayList<String>();
        switches = new ArrayList<NetSynthSwitch>();
        for(DGate gate:_netlist){
            netlist.add(gate);
        }
        assignGIndex();
        for(String inp:inputOrder){
            inputs.add(inp);
        }
        outputs = getOutputs();
        truthtables = BooleanSimulator.getTruthTable(netlist, inputs);
        
    }
    public CircuitLibrary(List<DGate> _netlist,List<String> inputOrder,List<String> outputOrder){
        netlist = new ArrayList<DGate>();
        inputs = new ArrayList<String>();
        outputs = new ArrayList<String>();
        truthtables = new ArrayList<String>();
        switches = new ArrayList<NetSynthSwitch>();
        for(DGate gate:_netlist){
            netlist.add(gate);
        }
        assignGIndex();
        for(String inp:inputOrder){
            inputs.add(inp);
        }
        for(String outp:outputOrder){
            outputs.add(outp);
        }
        truthtables = BooleanSimulator.getTruthTable(netlist, inputs);
        
    }
    
    public int getInputCount(){
        return inputs.size();
    }
    public int getOutputCount(){
        return outputs.size();
    }
    public List<Integer> getTTDecimalVal(){
        List<Integer> tts = new ArrayList<Integer>();
        for(String truthtable:truthtables){
            tts.add(Convert.bintoDec(truthtable));
        }
        return tts;
    }
    private void assignGIndex(){
        for(int i=0;i<netlist.size();i++){
            netlist.get(i).gindex = i;
        }
    }
    private List<String> getInputs(){
        List<String> inputnames = new ArrayList<String>();
        for(DGate gate:netlist){
            for(DWire input:gate.input){
                if(input.wtype.equals(DWireType.input)){
                    if(!inputnames.contains(input.name))
                        inputnames.add(input.name);
                }
            }
        }
        return inputnames;
    }
    private List<String> getOutputs() {
        List<String> outputnames = new ArrayList<String>();
        for (DGate gate : netlist) {
            if (gate.output.wtype.equals(DWireType.output)) {
                if (!outputnames.contains(gate.output.name)) {
                    outputnames.add(gate.output.name);
                }
            }
        }
        return outputnames;
    }
    public String printCircuit(){
        String str = "";
        str+= "Inputs:\n";
        for(int i=0;i<inputs.size();i++) {
            str+= (inputs.get(i)+" ");
        }
        str += "\nOutputs:\n";
        for(int i=0;i<outputs.size();i++) {
            str+= (outputs.get(i)+" ");
        }
        str += "\nNetlist:\n";
        for(DGate gate: netlist){
            str += NetSynth.printGate(gate);
            str += "\n";
        }
        str += "TruthTable:";
        for(int i=0;i<truthtables.size();i++) {
            str+= (truthtables.get(i)+" ");
        }
        return str;
    }
}
