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
public class SubcircuitLibrary {
    public List<DGate> netlist;
    public List<String> inputs;
    public String output;
    public String truthtable;
    public List<NetSynthSwitch> switches;
    
    public SubcircuitLibrary(){
        netlist = new ArrayList<DGate>();
        inputs = new ArrayList<String>();
        switches = new ArrayList<NetSynthSwitch>();
        output = "";
        truthtable = "";
    }
    public SubcircuitLibrary(List<DGate> _netlist){
        netlist = new ArrayList<DGate>();
        inputs = new ArrayList<String>();
        switches = new ArrayList<NetSynthSwitch>();
        output = "";
        truthtable = "";
        
        for(DGate gate:_netlist){
            netlist.add(gate);
        }
        assignGIndex();
        inputs = getInputs();
        output = getOutput();
        truthtable = BooleanSimulator.getTruthTable(netlist, inputs).get(0);
        
    }
    
    public SubcircuitLibrary(List<DGate> _netlist,List<String> inputOrder){
        netlist = new ArrayList<DGate>();
        inputs = new ArrayList<String>();
        switches = new ArrayList<NetSynthSwitch>();
        output = "";
        truthtable = "";
        
        for(DGate gate:_netlist){
            netlist.add(gate);
        }
        assignGIndex();
        
        for(String inp:inputOrder){
            inputs.add(inp);
        }
        
        output = getOutput();
        truthtable = BooleanSimulator.getTruthTable(netlist, inputs).get(0);
        
    }
    
    
    public SubcircuitLibrary(List<DGate> _netlist,List<String> inputOrder,String _output){
        netlist = new ArrayList<DGate>();
        inputs = new ArrayList<String>();
        switches = new ArrayList<NetSynthSwitch>();
        truthtable = "";
        
        for(DGate gate:_netlist){
            netlist.add(gate);
        }
        assignGIndex();
        
        for(String inp:inputOrder){
            inputs.add(inp);
        }
        
        output = _output;
        String out = getOutput();
        
        if(!out.equals(_output))
        {
            netlist.get(netlist.size()-1).output.name = _output;
        }
        truthtable = BooleanSimulator.getTruthTable(netlist, inputs).get(0);
        
    }
    
    
    public int getInputCount(){
        return inputs.size();
    }
    
    public int getTTDecimalVal(){
        return Convert.bintoDec(truthtable);
    }
    
    private void assignGIndex(){
        for(int i=0;i<netlist.size();i++){
            netlist.get(i).gindex = i;
        }
    }
    
    public void setInputs(){
        inputs = new ArrayList<String>();
        for(DGate gate:netlist){
            for(DWire input:gate.input){
                if(input.wtype.equals(DWireType.input)){
                    if(!inputs.contains(input.name))
                        inputs.add(input.name);
                }
            }
        }
    }
    
    public void setTT(){
        truthtable = "";
        truthtable = BooleanSimulator.getTruthTable(netlist, inputs).get(0);
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
    private String getOutput() {
        return netlist.get(netlist.size()-1).output.name;
    }
    
    public String printSubcircuit(){
        String str = "";
        str+= "Inputs:\n";
        for(int i=0;i<inputs.size();i++) {
            str+= (inputs.get(i)+" ");
        }
        str += "\nOutputs:\n";
        str += output;
        str += "\nNetlist:\n";
        for(DGate gate: netlist){
            str += NetSynth.printGate(gate);
            str += "\n";
        }
        str += "TruthTable:";
        str += truthtable;
        
        return str;
    }
}
