/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cellocad.BU.subcircuit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.cellocad.BU.netsynth.DGate;
import org.cellocad.BU.netsynth.DWire;
import org.cellocad.BU.netsynth.DWireType;
import org.cellocad.BU.netsynth.NetSynth;

/**
 *
 * @author prash
 */
public class subCircuitSwap {
    
    //Need to make some framework. Can be done later. Implement 3 input 1 output motifs now. 
    public static void implementSwap(List<DGate> netlist)
    {
        
    }
    
    
    public static List<DGate> insertSubcircuit(List<DGate> netlist,List<DGate> subcircuit, Map<String,String> inputMap,int index){
        List<DGate> output = new ArrayList<DGate>();
        List<DGate> subcircopy = new ArrayList<DGate>();
        //Make copy of given netlist
        for(DGate gate:netlist){
            output.add(new DGate(gate));
        }
        for(DGate gate:subcircuit){
            subcircopy.add(new DGate(gate));
        }
        Map<String,String> netWMap = renameWires(output,false);
        Map<String,String> subnetWMap = renameWires(subcircopy,true);
        Map<String,String> updatedMap = renamedMapping(netWMap,subnetWMap,inputMap);
        Map<String,DWireType> wireTypeMap = getInputWtypes(output,updatedMap);
        
        renameSubcircuitWires(subcircopy,updatedMap,wireTypeMap);
        
        //Rename output Wire and reassign wire type.
        subcircopy.get(subcircopy.size()-1).output.name = output.get(index).output.name;
        subcircopy.get(subcircopy.size()-1).output.wtype = output.get(index).output.wtype;
        
        //Remove the last node of the sub-circuit being considered
        output.remove(index);
        
        //Insert the subcircuit in that position. (With updated netlist wire names)
        for(int i=subcircopy.size()-1;i>=0;i--){
            output.add(index, subcircopy.get(i));
        }
        
        //NetSynth.printDebugStatement("After adding new Subcircuit");
        //NetSynth.printNetlist(output);
        
        //Rewire Nodes
        output = NetSynth.rewireNetlist(output);
        
        //Remove Dangling Nodes
        output = NetSynth.removeDanglingGates(output);
        
        //NetSynth.printDebugStatement("Remove Dangling Nodes");
        //NetSynth.printNetlist(output);
        
        
        //Rewire Nodes
        output = NetSynth.rewireNetlist(output);
        
        //Remove Gates with Duplicate Logic
        List<String> inputNames = new ArrayList<String>();
        for(DGate gate:netlist){
            for(DWire wire:gate.input){
                if(wire.wtype.equals(DWireType.input)){
                    if(!inputNames.contains(wire.name)){
                        inputNames.add(wire.name);
                    }
                }
            }
        }
        
        output = NetSynth.assignWireLogic(inputNames, output);
        output = NetSynth.removeDuplicateLogicGate(output);
        
        //Rewire Nodes
        output = NetSynth.rewireNetlist(output);
        renameWires(output,false);
        
        NetSynth.printDebugStatement("Final Netlist");
        NetSynth.printNetlist(output);
        
        return output;
    }
    
    public static Map<String,DWireType> getInputWtypes(List<DGate> netlist, Map<String,String> inputMap){
        Map<String,DWireType> map = new HashMap<String,DWireType>();
        for(DGate gate:netlist){
            for(DWire inp:gate.input){
                if(inputMap.containsKey(inp.name)){
                    map.put(inp.name,inp.wtype);
                }
            }
            if(inputMap.containsKey(gate.output.name)){
                map.put(gate.output.name, gate.output.wtype);
            }
        }
        return map;
    }
    
    public static Map<String,String> renamedMapping(Map<String,String> renamedNetlistWires,Map<String,String> renamedSubNetlistWires, Map<String,String> inputMap){
        
        Map<String,String> updatedMap = new HashMap<String,String>();
        for(Map.Entry<String,String> pair:inputMap.entrySet()){
            String netlistW = pair.getKey();
            String subnetlistW = pair.getValue();
            if(renamedNetlistWires.containsKey(pair.getKey())){
                netlistW = renamedNetlistWires.get(pair.getKey());
            }
            if(renamedSubNetlistWires.containsKey(pair.getValue())){
                subnetlistW = renamedSubNetlistWires.get(pair.getValue());
            }
            updatedMap.put(netlistW, subnetlistW);
            
        }
        return updatedMap;
    }
    
    public static void renameSubcircuitWires(List<DGate> subcircuit,Map<String,String> inputMap,Map<String,DWireType> wireTypeMap){
        
        Map<String,String> reverseMap = new HashMap<String,String>();
        for(Map.Entry<String,String> pair: inputMap.entrySet()){
            reverseMap.put(pair.getValue(), pair.getKey());
        }
        for(DGate gate:subcircuit){
            for(DWire inp:gate.input){
                if(reverseMap.containsKey(inp.name)){
                    inp.name = reverseMap.get(inp.name);
                    inp.wtype = wireTypeMap.get(inp.name);
                }
            }
            if(reverseMap.containsKey(gate.output.name)){
                gate.output.name = reverseMap.get(gate.output.name);
                gate.output.wtype = wireTypeMap.get(gate.output.name);
            }
        }
    }
    
    public static Map<String,String> renameWires(List<DGate> netlist, boolean subcircuit){
        
        //NetSynth.printNetlist(netlist);
        String wirePrefix = "0w";
        if(subcircuit)
            wirePrefix = "0sw";
        Map<String,String> nameMap = new HashMap<String,String>();
        int indx = 0;
        for(DGate gate:netlist){
            for(DWire inp:gate.input){
                if(inp.wtype.equals(DWireType.connector)){
                    if(nameMap.containsKey(inp.name)){
                        inp.name = nameMap.get(inp.name);
                    }
                    //Should never reach this else block. Remove and see what happens.
                    /*else{
                        String wirename = wirePrefix + indx;
                        indx++;
                        nameMap.put(inp.name, wirename);
                        inp.name = wirename;
                    }*/
                }
            }
            if (gate.output.wtype.equals(DWireType.connector)) {
                
                if (!nameMap.containsKey(gate.output.name)) {
                    String wirename = wirePrefix + indx;
                    indx++;
                    nameMap.put(gate.output.name, wirename);
                    gate.output.name = wirename;
                } 
                //Should never reach this else block. Remove and see what happens.
                /*else {
                    gate.output.name = nameMap.get(gate.output.name);
                }*/
            }
        }
        //System.out.println("Renamed Circuit");
        //NetSynth.printNetlist(netlist);
        
        return nameMap;
    }

    
}
