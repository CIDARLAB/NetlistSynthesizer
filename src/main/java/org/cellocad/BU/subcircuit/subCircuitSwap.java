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
import org.cellocad.BU.ParseVerilog.Convert;
import org.cellocad.BU.booleanLogic.BooleanSimulator;
import org.cellocad.BU.netsynth.DGate;
import org.cellocad.BU.netsynth.DGateType;
import org.cellocad.BU.netsynth.DWire;
import org.cellocad.BU.netsynth.DWireType;
import org.cellocad.BU.netsynth.NetSynth;
import org.cellocad.BU.netsynth.NetSynthSwitches;

/**
 *
 * @author prash
 */
public class subCircuitSwap {
    
    //Need to make some framework. Can be done later. Implement 3 input 1 output motifs now. 
    public static List<DGate> implementSwap(List<DGate> netlist,List<NetSynthSwitches> switches, Map<Integer,Map<Integer,List<SubcircuitLibrary>>> sublibrary)
    {
        netlist = NetSynth.rewireNetlist(netlist);
        List<DGate> output = new ArrayList<DGate>();
        //System.out.println("Reached here");
        output = nodeRewrite(netlist,switches,netlist.size()-1, sublibrary);
       
        //NetSynth.printNetlist(output);
        //System.out.println("=========================");
        String wireName = output.get(output.size()-1).output.name;
        int indx = output.size()-1;
        
        do{
            for(int i=output.size()-1;i>=0;i--){
                if(output.get(i).output.name.equals(wireName)){
                    indx = i;
                }
            }
            if(indx == 0){
                break;
            }
            else{
                indx --;
            }
            List<DGate> temp = new ArrayList<DGate>();
            
            output = nodeRewrite(output,switches,indx, sublibrary);
            //Just Make sure this works all the time. Potential problem?
            wireName = output.get(indx).output.name;
        }while(true);
        
        return output;
    }
    
    public static int circCost(List<DGate> netlist){
        int count =0;
        for(DGate gate:netlist){
            if(gate.output.wtype.equals(DWireType.output) && gate.gtype.equals(DGateType.OR)){
                
            }
            else
            {
                count++;
            }
        }
        return count;
    }
    
    public static List<DGate> nodeRewrite(List<DGate> netlist,List<NetSynthSwitches> switches,int index,Map<Integer,Map<Integer,List<SubcircuitLibrary>>> sublibrary){
        List<DGate> output = new ArrayList<DGate>();
        for(DGate gate:netlist){
            output.add(new DGate(gate));
        }
        output = NetSynth.rewireNetlist(output);
        List<SubNetlist> subnet = new ArrayList<SubNetlist>();
        subnet = subCircuitEnumerator.getSubNetlistDetails(netlist, index);
        
        for(int i=0;i<subnet.size();i++){
            int tt = Convert.bintoDec(subnet.get(i).tt);
            int inpSize = subnet.get(i).inputs.size();
            
            Map<Integer,permutationMap> pmap = isomorphicFunction.getPermutationMapping(inpSize);
            for(int j=0;j<pmap.size();j++){
                int ttPerm = Convert.bintoDec(isomorphicFunction.getPermutedTT(subnet.get(i).tt, pmap.get(j).permutation));
                Map<String,String> inpMap = new HashMap<String,String>();
                for(int k=0;k<sublibrary.get(inpSize).get(ttPerm).size();k++){
                    inpMap = isomorphicFunction.getInputMapping(subnet.get(i).inputs,sublibrary.get(inpSize).get(ttPerm).get(k).inputs,pmap.get(j).inputOrder);
                    List<DGate> tempNetlist = new ArrayList<DGate>();
                    boolean canSwap = false;
                    if (sublibrary.get(inpSize).get(ttPerm).get(k).switches.size() == 0) {
                        canSwap = true;
                    } 
                    else {
                        canSwap = containsSwitches(switches,sublibrary.get(inpSize).get(ttPerm).get(k).switches);
                    }
                    if (canSwap) {
                        tempNetlist = insertSubcircuit(netlist, sublibrary.get(inpSize).get(ttPerm).get(k).netlist, inpMap, index);
                        if (circCost(tempNetlist) < circCost(output)) {
                            output = new ArrayList<DGate>();
                            System.out.println("Switch");
                            for (DGate gate : tempNetlist) {
                                output.add(new DGate(gate));
                            }
                            output = NetSynth.rewireNetlist(output);
                        }
                    }
                    
                }
            }
        }
        
        return output;
    }
    
    public static boolean containsSwitches(List<NetSynthSwitches> switches, List<NetSynthSwitches> constraints){
        boolean contains = true;
        for(NetSynthSwitches sw:constraints){
            if(!switches.contains(sw)){
                contains = false;
                break;
            }
        }
        return contains;
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
        
        //Rewire Nodes
        output = NetSynth.rewireNetlist(output);
        
        //Remove Dangling Nodes
        output = NetSynth.removeDanglingGates(output);
        
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
