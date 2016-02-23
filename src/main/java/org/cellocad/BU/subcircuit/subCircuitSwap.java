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
import org.cellocad.BU.parseVerilog.Convert;
import org.cellocad.BU.simulators.BooleanSimulator;
import org.cellocad.BU.dom.DGate;
import org.cellocad.BU.dom.DGateType;
import org.cellocad.BU.dom.DWire;
import org.cellocad.BU.dom.DWireType;
import org.cellocad.BU.netsynth.NetSynth;
import org.cellocad.BU.netsynth.NetSynthSwitch;

/**
 *
 * @author prash
 */
public class subCircuitSwap {
    
    public static int swapCount;
    //Need to make some framework. Can be done later. Implement 3 input 1 output motifs now. 
    public static List<DGate> implementSwap(List<DGate> netlist, List<NetSynthSwitch> switches, Map<Integer,Map<Integer,List<SubcircuitLibrary>>> sublibrary)
    {
        swapCount =0;
        NetSynth.rewireNetlist(netlist);
        renameWires(netlist,false);
        NetSynth.rewireNetlist(netlist);
        
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
        
        List<DGate> output = new ArrayList<DGate>();
        //System.out.println("Reached here");
        output = nodeRewrite(netlist,switches,netlist.size()-1, sublibrary);
        output = NetSynth.assignWireLogic(inputNames, output);
        String wireLogic = output.get(output.size()-1).output.logicValue;
        int indx = output.size()-1;
        
        do{
            for(int i=output.size()-1;i>=0;i--){
                if(output.get(i).output.logicValue.equals(wireLogic)){
                    indx = i;
                }
            }
            if(indx == 0){
                break;
            }
            else{
                indx --;
            }
            //List<DGate> temp = new ArrayList<DGate>();
            
            //System.out.println("Index : "+indx);
            wireLogic = output.get(indx).output.logicValue;    
            output = nodeRewrite(output,switches,indx, sublibrary);
            output = NetSynth.assignWireLogic(inputNames, output);
            
        }while(true);
        //System.out.println("Total Swaps :: "+swapCount);
        return output;
    }
    
    public static int circCost(List<DGate> netlist){
        int count =0;
        for(DGate gate:netlist){
            if(gate.output.wtype.equals(DWireType.output) && gate.gtype.equals(DGateType.OUTPUT_OR)){
                
            }
            else
            {
                count++;
            }
        }
        return count;
    }
    
    public static List<DGate> nodeRewrite(List<DGate> netlist,List<NetSynthSwitch> switches,int index,Map<Integer,Map<Integer,List<SubcircuitLibrary>>> sublibrary){
        
        List<DGate> output = new ArrayList<DGate>();
        for(DGate gate:netlist){
            output.add(new DGate(gate));
        }
        NetSynth.rewireNetlist(output);
        NetSynth.rewireNetlist(netlist);
        List<SubNetlist> subnet = new ArrayList<SubNetlist>();
        
        //This is where stuff changes
        //subnet = subCircuitEnumerator.getSubNetlistDetails(netlist, index);
        subnet = subCircuitEnumerator.getSubNetlistDetailsGraph(netlist, index, 3);
        
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
                        
                        //canSwap = containsSwitches(switches,sublibrary.get(inpSize).get(ttPerm).get(k).switches);
                        //if(canSwap){
                            //if(switches.contains(NetSynthSwitch.output_or)){
                                if(!netlist.get(index).output.wtype.equals(DWireType.output)){
                                    if(sublibrary.get(inpSize).get(ttPerm).get(k).switches.contains(NetSynthSwitch.output_or)){
                                        canSwap = false;
                                    }
                                }
                                else{
                                    canSwap = true;
                                }
                                    
                            //}
                        //}
                    }
                    if (canSwap) {
                        //System.out.println("TT ORG"+subnet.get(i).tt);
                        //System.out.println("TT"+Convert.dectoBin(ttPerm, inpSize));
                        tempNetlist = insertSubcircuit(netlist, sublibrary.get(inpSize).get(ttPerm).get(k).netlist, inpMap, index);
                        if (circCost(tempNetlist) < circCost(output)) {
                            
                            
                            List<String> inputNames = new ArrayList<String>();
                            //for (DGate gate : netlist) {
                            for (DGate gate : output) { //Previous. Input names incorrect
                                for (DWire wire : gate.input) {
                                    if (wire.wtype.equals(DWireType.input)) {
                                        if (!inputNames.contains(wire.name)) {
                                            inputNames.add(wire.name);
                                        }
                                    }
                                }
                            }
                            
                            //if(inputNames.get(0).equals("inp1")){
                                
                                //System.out.println("Breaks here");
                                
                                /*System.out.println("Input Names:" + inputNames);
                                NetSynth.printDebugStatement("TempNetlist");
                                NetSynth.printNetlist(tempNetlist);
                                
                                NetSynth.printDebugStatement("Netlist");
                                NetSynth.printNetlist(netlist);
                                
                                NetSynth.printDebugStatement("Output");
                                NetSynth.printNetlist(output);
                                */
                            //}
                            
                            List<String> tt1 = BooleanSimulator.getTruthTable(tempNetlist, inputNames);
                            List<String> tt2 = BooleanSimulator.getTruthTable(netlist,inputNames);
                            if (equalFunctions(tt1, tt2)) {
                                output = new ArrayList<DGate>();
                                for (DGate gate : tempNetlist) {
                                    output.add(new DGate(gate));
                                }
                                NetSynth.rewireNetlist(output);
                                swapCount++;
                            }
                            
                        }
                    }
                    
                }
            }
        }
        
        //NetSynth.printDebugStatement("Final Output");
        //NetSynth.printNetlist(output);
        
        return output;
    }
    
    public static boolean equalFunctions(List<String> tt1,List<String> tt2){
        boolean res = true;
        if(tt1.size() != tt2.size())
            return false;
        for(int i=0;i<tt1.size();i++)
        {
            if(!tt1.get(i).equals(tt2.get(i))){
                res = false;
                break;
            }
        }
            
        return res;
    }
    
    public static boolean containsSwitches(List<NetSynthSwitch> switches, List<NetSynthSwitch> constraints){
        boolean contains = true;
        for(NetSynthSwitch sw:constraints){
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
        NetSynth.rewireNetlist(output);
        
        //Remove Dangling Nodes
        output = NetSynth.removeDanglingGates(output);
        
        
        
        //Rewire Nodes
        NetSynth.rewireNetlist(output);
        
        
        
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
        NetSynth.rewireNetlist(output);
        
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
        
        String tempPrefix = "0z";
        Map<String,String> tempMap = new HashMap<String,String>();
        
        Map<String,String> swapMap = new HashMap<String,String>();
        
        String wirePrefix = "0w";
        if(subcircuit)
            wirePrefix = "0sw";
        Map<String,String> nameMap = new HashMap<String,String>();
        int indx = 0;
        for(DGate gate:netlist){
            for(DWire inp:gate.input){
                if(inp.wtype.equals(DWireType.connector)){
                    if(swapMap.containsKey(inp.name)){
                        inp.name = swapMap.get(inp.name);
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
                    String tempname = tempPrefix + indx;
                    tempMap.put(tempname, wirename);
                    swapMap.put(gate.output.name, tempname);
                    indx++;
                    nameMap.put(gate.output.name, wirename);
                    gate.output.name = tempname;
                } 
                //Should never reach this else block. Remove and see what happens.
                /*else {
                    gate.output.name = nameMap.get(gate.output.name);
                }*/
            }
        }
        
        for(DGate gate:netlist){
            if(tempMap.containsKey(gate.output.name)){
                gate.output.name = tempMap.get(gate.output.name);
            }
            for(DWire wire:gate.input){
                if(tempMap.containsKey(wire.name)){
                    wire.name = tempMap.get(wire.name);
                }
            }
        }
        //System.out.println("Renamed Circuit");
        //NetSynth.printNetlist(netlist);
        
        return nameMap;
    }

    
}
