/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cellocad.BU.subcircuit;

import java.util.ArrayList;
import java.util.List;
import org.cellocad.BU.booleanLogic.BooleanSimulator;
import org.cellocad.BU.netsynth.DGate;
import org.cellocad.BU.netsynth.DWire;
import org.cellocad.BU.netsynth.DWireType;

/**
 *
 * @author prash
 */
public class CircuitLibrary {
    public List<DGate> netlist;
    public List<String> inputs;
    public List<String> outputs;
    public List<String> truthtables;
    
    public CircuitLibrary(){
        netlist = new ArrayList<DGate>();
        inputs = new ArrayList<String>();
        outputs = new ArrayList<String>();
        truthtables = new ArrayList<String>();
    }
    public CircuitLibrary(List<DGate> _netlist){
        netlist = new ArrayList<DGate>();
        inputs = new ArrayList<String>();
        outputs = new ArrayList<String>();
        truthtables = new ArrayList<String>();
        
        for(DGate gate:_netlist){
            netlist.add(gate);
        }
        assignGIndex();
        inputs = getInputs();
        outputs = getOutputs();
        truthtables = BooleanSimulator.getTruthTable(netlist, inputs);
        
    }
    
    public int getInputCount(){
        return inputs.size();
    }
    public int getOutputCount(){
        return outputs.size();
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
}
