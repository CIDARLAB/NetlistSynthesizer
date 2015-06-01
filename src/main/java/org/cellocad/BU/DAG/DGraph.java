/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cellocad.BU.DAG;

import java.util.ArrayList;
import java.util.List;
import org.cellocad.BU.netsynth.DGate;
import org.cellocad.BU.netsynth.DWire;
import org.cellocad.BU.netsynth.DWireType;

/**
 *
 * @author prash
 */
public class DGraph {
    public static DGNode createGraph(List<DGate> netlist){
        DGNode node = new DGNode();
        
        
        
        return node;
    }
    
    public static List<String> getInputs(List<DGate> netlist){
        List<String> inputs = new ArrayList<String>();
        for(DGate gate:netlist){
            for(DWire wire:gate.input){
                if(wire.wtype.equals(DWireType.input)){
                    if(!inputs.contains(wire.name)){
                        inputs.add(wire.name);
                    o}
                }
            }
        }
        return inputs;
    }
    public static List<String> getOutputs(List<DGate> netlist){
        List<String> outputs = new ArrayList<String>();
        for(DGate gate:netlist){
            if(gate.output.wtype.equals(DWireType.output)){
                if(!outputs.contains(gate.output.name)){
                    outputs.add(gate.output.name);
                }
            }
        }
        return outputs;
    }
}
