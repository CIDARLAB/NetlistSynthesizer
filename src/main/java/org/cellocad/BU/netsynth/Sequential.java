/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cellocad.BU.netsynth;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.cellocad.BU.dom.DGate;
import org.cellocad.BU.dom.DGateType;
import org.cellocad.BU.dom.DWire;
import org.cellocad.BU.dom.DWire.DWireValue;
import org.cellocad.BU.dom.DWireType;
import org.cellocad.BU.parseVerilog.Convert;
import org.cellocad.BU.simulators.BooleanSimulator;

/**
 *
 * @author prash
 */
public class Sequential {

    private static List<List<String>> getCyclicalOutputWireNamesFromNetlist(List<DGate> netlist) {
        List<List<String>> outputWireNames = new ArrayList<List<String>>();

        for (int i = 1; i < netlist.size(); i++) {
            for (int j = 0; j <= i; j++) {
                for (DWire input : netlist.get(j).input) {
                    if (netlist.get(i).output.name.equals(input.name)) {
                        List<String> wireNames = new ArrayList<String>();
                        wireNames.add(netlist.get(i).output.name);
                        wireNames.add(netlist.get(j).output.name);
                        outputWireNames.add(wireNames);
                    }
                }
            }
        }

        return outputWireNames;
    }
    
    public static List<List<String>> getCyclicalOutputWireNames(List<List<DGate>> cyclicGates){
        List<List<String>> outputWireNames = new ArrayList<List<String>>();
        for(List<DGate> gateTuples:cyclicGates){
            List<String> outWireTuples = new ArrayList<String>();
            for(DGate gate:gateTuples){
                outWireTuples.add(gate.output.name);
            }
            outputWireNames.add(outWireTuples);
        }
        
        return outputWireNames;
    }
    
    private static List<List<DWire>> getCyclicalOutputWiresFromNetlist(List<DGate> netlist) {
        List<List<DWire>> outputWireNames = new ArrayList<List<DWire>>();

        for (int i = 1; i < netlist.size(); i++) {
            for (int j = 0; j <= i; j++) {
                for (DWire input : netlist.get(j).input) {
                    if (netlist.get(i).output.name.equals(input.name)) {
                        List<DWire> wires = new ArrayList<DWire>();
                        wires.add(netlist.get(i).output);
                        wires.add(netlist.get(j).output);
                        outputWireNames.add(wires);
                    }
                }
            }
        }

        return outputWireNames;
    }
    
    public static List<List<DWire>> getCyclicalOutputWires(List<List<DGate>> cyclicGates){
        List<List<DWire>> outputWires = new ArrayList<List<DWire>>();
        for(List<DGate> gateTuples:cyclicGates){
            List<DWire> outWireTuples = new ArrayList<DWire>();
            for(DGate gate:gateTuples){
                outWireTuples.add(gate.output);
            }
            outputWires.add(outWireTuples);
        }
        return outputWires;
    }
    
    public static List<List<DGate>> getCyclicalGates(List<DGate> netlist){
        List<List<DGate>> gates = new ArrayList<List<DGate>>();

        for (int i = 1; i < netlist.size(); i++) {
            for (int j = 0; j <= i; j++) {
                for (DWire input : netlist.get(j).input) {
                    if (netlist.get(i).output.name.equals(input.name)) {
                        List<DGate> dgates = new ArrayList<DGate>();
                        dgates.add(netlist.get(i));
                        dgates.add(netlist.get(j));
                        gates.add(dgates);
                    }
                }
            }
        }
        return gates;
    }
    
    private static BiMap getSRLatchBiMap(List<List<DGate>> cyclicGates, DGateType type){
        BiMap<Integer,Integer> map = HashBiMap.create();
        for(List<DGate> cyclicTuple : cyclicGates){
            if (cyclicTuple.size() == 2) {
                if (cyclicTuple.get(0).gtype.equals(type) && cyclicTuple.get(1).gtype.equals(type)) {
                    if ((cyclicTuple.get(0).input.size() == 2) && (cyclicTuple.get(1).input.size() == 2)) {
                        if (cyclicTuple.get(0).containsWireAsInput(cyclicTuple.get(1).output) && cyclicTuple.get(1).containsWireAsInput(cyclicTuple.get(0).output)) {
                            //System.out.println("SR " + type.toString() +  " Latch found!");
                            //System.out.println(cyclicTuple.get(0).gindex);
                            //System.out.println(cyclicTuple.get(1).gindex);
                            if(map.containsKey(cyclicTuple.get(0).gindex)){
                                map.put(cyclicTuple.get(1).gindex, cyclicTuple.get(0).gindex);
                            } else {
                                if(map.containsValue(cyclicTuple.get(1).gindex)){
                                    map.put(cyclicTuple.get(1).gindex, cyclicTuple.get(0).gindex);
                                }
                                else{
                                    map.put(cyclicTuple.get(0).gindex, cyclicTuple.get(1).gindex);
                                }
                            }
                        }
                    }
                }
            }
        }
        
        return map;
    }
    
    
    public static BiMap getSRLatchNOR(List<List<DGate>> cyclicGates){
        return getSRLatchBiMap(cyclicGates, DGateType.NOR);
    }
    
    public static BiMap getSRLatchNAND(List<List<DGate>> cyclicGates){
        return getSRLatchBiMap(cyclicGates, DGateType.NAND);
    }

    
    public static void printCompleteTruthtable(List<DGate> netlist) {
        List<String> inputs = new ArrayList<String>();
        List<String> outputs = new ArrayList<String>();
        assignWireStage(netlist);
        for (DGate gate : netlist) {
            for (DWire input : gate.input) {
                if (input.wtype.equals(DWireType.input)) {
                    if (!inputs.contains(input.name)) {
                        inputs.add(input.name);
                    }
                }
            }
            if(gate.output.wtype.equals(DWireType.output)){
                outputs.add(gate.output.name);
            }
        }

        Set<String> states = new HashSet<String>();
        
        for (List<DWire> outputWires : getCyclicalOutputWiresFromNetlist(netlist)) {
            for (DWire outputWire : outputWires) {
                states.add(outputWire.name);
            }
            String wireName = getHighestStageWire(outputWires).name;
            if (!inputs.contains(wireName)) {
                inputs.add(wireName);
            }
        }
        List<String> statesList = new ArrayList<String>(states);
        System.out.println("INPUTS :: " + inputs);
        System.out.println("STATES :: " + states);
        List<String> nonInputStates = new ArrayList<String>();
        System.out.println("TRUTH TABLE");
        System.out.println("===========================");
        Map<String,Integer> inputValues = new HashMap<String,Integer>();
        Map<String,Integer> currentState = new HashMap<String,Integer>();
        Map<String,Integer> nextState = new HashMap<String,Integer>();
        Map<String,Integer> nextToNextState = new HashMap<String,Integer>();
        
        for(String input:inputs){
            System.out.print(input + " ");
        }
        for(String state:states){
            if(!inputs.contains(state)){
                System.out.print(state + " ");
                nonInputStates.add(state);
            }
        }
        for(String state:statesList){
            System.out.print(state + "+1 ");
        }
        List<String> nonStateOutputs = new ArrayList<String>();
        for(String output:outputs){
            if(!states.contains(output)){
                System.out.print(output + " ");
                nonStateOutputs.add(output);
            }
        }
        System.out.println("");
        for(int i =0;i< Math.pow(2, inputs.size());i++){
            
            String bin = Convert.dectoBin(i, inputs.size());
            for(int j=0;j<inputs.size();j++){
                String binaryVal = "";
                binaryVal += bin.charAt(j);
                int binValue = Integer.valueOf(binaryVal);
                inputValues.put(inputs.get(j),binValue);
                String space = "";
                for(int m =0; m<inputs.get(j).length();m++){
                    space += " ";
                }
                System.out.print(binValue + space);
            }
            
            for(String state:states){
                if(inputValues.containsKey(state)){
                    currentState.put(state, inputValues.get(state));   
                }
            }
            
            
            
            //System.out.println("INPUTS :: " + inputValues);
            //Current State
            //System.out.println("Current State");
            for(DGate gate:netlist){
                for(DWire wire:gate.input){
                    if(inputValues.containsKey(wire.name)){
                        if(inputValues.get(wire.name) == 0){
                            wire.wValue = DWireValue._0;
                        }
                        if(inputValues.get(wire.name) == 1){
                            wire.wValue = DWireValue._1;
                        }
                    }
                }
                BooleanSimulator.bfunction(gate);
                if(states.contains(gate.output.name) && !inputValues.containsKey(gate.output.name)){
                    int binVal = -1;
                    if(gate.output.wValue.equals(DWireValue._0)){
                        binVal = 0;
                    }
                    if(gate.output.wValue.equals(DWireValue._1)){
                        binVal = 1;
                    }
                    currentState.put(gate.output.name, binVal);
                }
                if(states.contains(gate.output.name) && inputValues.containsKey(gate.output.name)){
                    int binVal = -1;
                    if(gate.output.wValue.equals(DWireValue._0)){
                        binVal = 0;
                    }
                    if(gate.output.wValue.equals(DWireValue._1)){
                        binVal = 1;
                    }
                    nextState.put(gate.output.name, binVal);
                    inputValues.put(gate.output.name, binVal);
                }
                //System.out.println(gate.output.name + ":" + gate.output.wValue);
                
            }
            for(String nonInputState:nonInputStates ){
                String space = "";
                for(int m=0;m<nonInputState.length();m++){
                    space += " ";
                }
                System.out.print(currentState.get(nonInputState) + space);
            }
           //System.out.println("Current State :: " + currentState);
            //System.out.println("\n\n");
            
            //Next State
            //System.out.println("Next State");
            for(DGate gate:netlist){
                for(DWire wire:gate.input){
                    if(inputValues.containsKey(wire.name)){
                        if(inputValues.get(wire.name) == 0){
                            wire.wValue = DWireValue._0;
                        }
                        if(inputValues.get(wire.name) == 1){
                            wire.wValue = DWireValue._1;
                        }
                    }
                }
                BooleanSimulator.bfunction(gate);
                if(states.contains(gate.output.name) && !inputValues.containsKey(gate.output.name)){
                    int binVal = -1;
                    if(gate.output.wValue.equals(DWireValue._0)){
                        binVal = 0;
                    }
                    if(gate.output.wValue.equals(DWireValue._1)){
                        binVal = 1;
                    }
                    nextState.put(gate.output.name, binVal);
                }
                if(states.contains(gate.output.name) && inputValues.containsKey(gate.output.name)){
                    int binVal = -1;
                    if(gate.output.wValue.equals(DWireValue._0)){
                        binVal = 0;
                    }
                    if(gate.output.wValue.equals(DWireValue._1)){
                        binVal = 1;
                    }
                    nextToNextState.put(gate.output.name, binVal);
                    inputValues.put(gate.output.name, binVal);
                }
                //System.out.println(gate.output.name + ":" + gate.output.wValue);
            }
            //System.out.println("Next State :: " + nextState);
            //System.out.println("\n\n");
            for (String state : statesList) {
                String space = "";
                for(int m =0;m<state.length() + 2;m++){
                    space += " ";
                }
                System.out.print(nextState.get(state) + space);
            }
            
            //Next to next State
            //System.out.println("Next To Next State");
            for(DGate gate:netlist){
                for(DWire wire:gate.input){
                    if(inputValues.containsKey(wire.name)){
                        if(inputValues.get(wire.name) == 0){
                            wire.wValue = DWireValue._0;
                        }
                        if(inputValues.get(wire.name) == 1){
                            wire.wValue = DWireValue._1;
                        }
                    }
                }
                BooleanSimulator.bfunction(gate);
                if(states.contains(gate.output.name) && !inputValues.containsKey(gate.output.name)){
                    int binVal = -1;
                    if(gate.output.wValue.equals(DWireValue._0)){
                        binVal = 0;
                    }
                    if(gate.output.wValue.equals(DWireValue._1)){
                        binVal = 1;
                    }
                    nextToNextState.put(gate.output.name, binVal);
                }
                //System.out.println(gate.output.name + ":" + gate.output.wValue);
            }
            //System.out.println("Next to Next State :: " + nextToNextState);
            //System.out.println("\n\n");
            System.out.println("");
            
        }
        
    }
    
    
    public static void truthtable(List<DGate> netlist) {
        List<String> inputs = new ArrayList<String>();
        List<String> outputs = new ArrayList<String>();
        assignWireStage(netlist);
        NetSynth.assignGateIndex(netlist);
        
        for (DGate gate : netlist) {
            for (DWire input : gate.input) {
                if (input.wtype.equals(DWireType.input)) {
                    if (!inputs.contains(input.name)) {
                        inputs.add(input.name);
                    }
                }
            }
            if(gate.output.wtype.equals(DWireType.output)){
                outputs.add(gate.output.name);
            }
        }

        Set<String> states = new HashSet<String>();
        List<List<DGate>> cyclicGates = getCyclicalGates(netlist);
        for (List<DWire> outputWires : getCyclicalOutputWires(cyclicGates)) {
            for (DWire outputWire : outputWires) {
                states.add(outputWire.name);
            }
            String wireName = getHighestStageWire(outputWires).name;
            if (!inputs.contains(wireName)) {
                inputs.add(wireName);
            }
        }
        
        BiMap<Integer,Integer> srnormap = getSRLatchNOR(cyclicGates);
        BiMap<Integer,Integer> srnandmap = getSRLatchNAND(cyclicGates);
        
        List<String> statesList = new ArrayList<String>(states);
        System.out.println("INPUTS :: " + inputs);
        System.out.println("STATES :: " + states);
        List<String> nonInputStates = new ArrayList<String>();
        System.out.println("TRUTH TABLE");
        System.out.println("===========================");
        Map<String,Integer> inputValues = new HashMap<String,Integer>();
        Map<String,Integer> currentState = new HashMap<String,Integer>();
        Map<String,Integer> nextState = new HashMap<String,Integer>();
        Map<String,Integer> nextToNextState = new HashMap<String,Integer>();
        
        for(String input:inputs){
            System.out.print(input + " ");
        }
        for(String state:states){
            if(!inputs.contains(state)){
                System.out.print(state + " ");
                nonInputStates.add(state);
            }
        }
        for(String state:statesList){
            System.out.print(state + "+1 ");
        }
        List<String> nonStateOutputs = new ArrayList<String>();
        for(String output:outputs){
            if(!states.contains(output)){
                System.out.print(output + " ");
                nonStateOutputs.add(output);
            }
        }
        boolean bistable;
        Set<Integer> srnand = new HashSet<Integer>();
        Set<Integer> srnor = new HashSet<Integer>();
        
        System.out.println("");
        for(int i =0;i< Math.pow(2, inputs.size());i++){
            
            String bin = Convert.dectoBin(i, inputs.size());
            for(int j=0;j<inputs.size();j++){
                String binaryVal = "";
                binaryVal += bin.charAt(j);
                int binValue = Integer.valueOf(binaryVal);
                inputValues.put(inputs.get(j),binValue);
                String space = "";
                for(int m =0; m<inputs.get(j).length();m++){
                    space += " ";
                }
                System.out.print(binValue + space);
            }
            
            for(String state:states){
                if(inputValues.containsKey(state)){
                    currentState.put(state, inputValues.get(state));   
                }
            }
            
            
            srnand = new HashSet<Integer>();
            srnor = new HashSet<Integer>();
            bistable = false;
            
            //System.out.println("INPUTS :: " + inputValues);
            //Current State
            //System.out.println("Current State");
            for(DGate gate:netlist){
                
                for(DWire wire:gate.input){
                    if(inputValues.containsKey(wire.name)){
                        if(inputValues.get(wire.name) == 0){
                            wire.wValue = DWireValue._0;
                        }
                        if(inputValues.get(wire.name) == 1){
                            wire.wValue = DWireValue._1;
                        }
                    }
                }
                
                //SR NOR Bistable check
                if(gate.gtype.equals(DGateType.NOR)){
                    if(srnormap.containsKey(gate.gindex)){
                        srnor.add(gate.gindex);
                        if(srnor.contains(srnormap.get(gate.gindex))){
                            if(bistableSRNOR(gate,netlist.get(srnormap.get(gate.gindex)))){
                                bistable = true;
                                break;
                            }
                        }
                    }
                    if(srnormap.containsValue(gate.gindex)){
                        srnor.add(gate.gindex);
                        if(srnor.contains(srnormap.inverse().get(gate.gindex))){
                            if(bistableSRNOR(gate,netlist.get(srnormap.get(gate.gindex)))){
                                bistable = true;
                                break;
                            }
                        }
                    }
                }
                
                //SR NAND Bistable check
                if(gate.gtype.equals(DGateType.NAND)){
                    
                }
                
                
                BooleanSimulator.bfunction(gate);
                if(states.contains(gate.output.name) && !inputValues.containsKey(gate.output.name)){
                    int binVal = -1;
                    if(gate.output.wValue.equals(DWireValue._0)){
                        binVal = 0;
                    }
                    if(gate.output.wValue.equals(DWireValue._1)){
                        binVal = 1;
                    }
                    currentState.put(gate.output.name, binVal);
                }
                if(states.contains(gate.output.name) && inputValues.containsKey(gate.output.name)){
                    int binVal = -1;
                    if(gate.output.wValue.equals(DWireValue._0)){
                        binVal = 0;
                    }
                    if(gate.output.wValue.equals(DWireValue._1)){
                        binVal = 1;
                    }
                    nextState.put(gate.output.name, binVal);
                    inputValues.put(gate.output.name, binVal);
                }
                //System.out.println(gate.output.name + ":" + gate.output.wValue);
                
            }
            if(bistable){
                System.out.println("BISTABLE!!");
                continue;
            }
            for(String nonInputState:nonInputStates ){
                String space = "";
                for(int m=0;m<nonInputState.length();m++){
                    space += " ";
                }
                System.out.print(currentState.get(nonInputState) + space);
            }
           //System.out.println("Current State :: " + currentState);
            //System.out.println("\n\n");
            
            //Next State
            //System.out.println("Next State");
            for(DGate gate:netlist){
                for(DWire wire:gate.input){
                    if(inputValues.containsKey(wire.name)){
                        if(inputValues.get(wire.name) == 0){
                            wire.wValue = DWireValue._0;
                        }
                        if(inputValues.get(wire.name) == 1){
                            wire.wValue = DWireValue._1;
                        }
                    }
                }
                BooleanSimulator.bfunction(gate);
                if(states.contains(gate.output.name) && !inputValues.containsKey(gate.output.name)){
                    int binVal = -1;
                    if(gate.output.wValue.equals(DWireValue._0)){
                        binVal = 0;
                    }
                    if(gate.output.wValue.equals(DWireValue._1)){
                        binVal = 1;
                    }
                    nextState.put(gate.output.name, binVal);
                }
                if(states.contains(gate.output.name) && inputValues.containsKey(gate.output.name)){
                    int binVal = -1;
                    if(gate.output.wValue.equals(DWireValue._0)){
                        binVal = 0;
                    }
                    if(gate.output.wValue.equals(DWireValue._1)){
                        binVal = 1;
                    }
                    nextToNextState.put(gate.output.name, binVal);
                    inputValues.put(gate.output.name, binVal);
                }
                //System.out.println(gate.output.name + ":" + gate.output.wValue);
            }
            //System.out.println("Next State :: " + nextState);
            //System.out.println("\n\n");
            for (String state : statesList) {
                String space = "";
                for(int m =0;m<state.length() + 2;m++){
                    space += " ";
                }
                System.out.print(nextState.get(state) + space);
            }
            
            //Next to next State
            //System.out.println("Next To Next State");
            for(DGate gate:netlist){
                for(DWire wire:gate.input){
                    if(inputValues.containsKey(wire.name)){
                        if(inputValues.get(wire.name) == 0){
                            wire.wValue = DWireValue._0;
                        }
                        if(inputValues.get(wire.name) == 1){
                            wire.wValue = DWireValue._1;
                        }
                    }
                }
                BooleanSimulator.bfunction(gate);
                if(states.contains(gate.output.name) && !inputValues.containsKey(gate.output.name)){
                    int binVal = -1;
                    if(gate.output.wValue.equals(DWireValue._0)){
                        binVal = 0;
                    }
                    if(gate.output.wValue.equals(DWireValue._1)){
                        binVal = 1;
                    }
                    nextToNextState.put(gate.output.name, binVal);
                }
                //System.out.println(gate.output.name + ":" + gate.output.wValue);
            }
            //System.out.println("Next to Next State :: " + nextToNextState);
            //System.out.println("\n\n");
            System.out.println("");
            
        }
        
    }
    
    private static boolean bistableSRNOR(DGate gate1, DGate gate2){
        
        for(DWire gate1input: gate1.input){
            if(!gate1input.name.equals(gate2.output.name)){
                if(gate1input.wValue.equals(DWireValue._0)){
                    return false; 
                }
                break;
            }
        }
        for(DWire gate2input: gate2.input){
            if(!gate2input.name.equals(gate1.output.name)){
                if(gate2input.wValue.equals(DWireValue._0)){
                    return false; 
                }
                break;
            }
        }
        
        return true;
    }
    
    
    private static boolean bistableSRNAND(DGate gate1, DGate gate2){
        
        for(DWire gate1input: gate1.input){
            if(!gate1input.name.equals(gate2.output.name)){
                if(gate1input.wValue.equals(DWireValue._1)){
                    return false; 
                }
                break;
            }
        }
        for(DWire gate2input: gate2.input){
            if(!gate2input.name.equals(gate1.output.name)){
                if(gate2input.wValue.equals(DWireValue._1)){
                    return false; 
                }
                break;
            }
        }
        
        return true;
    }
    
    
    public static DWire getHighestStageWire(List<DWire> wires) {
        int stage = wires.get(0).getWirestage();
        int pos = 0;
        for (int i = 0; i < wires.size(); i++) {
            if (wires.get(i).getWirestage() > stage) {
                pos = i;
            }
        }
        return wires.get(pos);
    }

    public static void assignWireStage(List<DGate> netlist) {

        int min = -2;
        for (DGate gate : netlist) {
            min = -2;
            for (DWire input : gate.input) {
                if (input.wtype.equals(DWireType.input)) {
                    input.setWirestage(0);

                }
                if (input.getWirestage() == -1) {
                    continue;
                }
                if (input.getWirestage() > min) {
                    min = input.getWirestage();
                }
            }
            gate.output.setWirestage(min + 1);
        }
    }

}
