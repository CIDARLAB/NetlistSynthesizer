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
import java.util.concurrent.ThreadLocalRandom;
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

    public static List<List<String>> getCyclicalOutputWireNames(List<List<DGate>> cyclicGates) {
        List<List<String>> outputWireNames = new ArrayList<List<String>>();
        for (List<DGate> gateTuples : cyclicGates) {
            List<String> outWireTuples = new ArrayList<String>();
            for (DGate gate : gateTuples) {
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

    public static List<List<DWire>> getCyclicalOutputWires(List<List<DGate>> cyclicGates) {
        List<List<DWire>> outputWires = new ArrayList<List<DWire>>();
        for (List<DGate> gateTuples : cyclicGates) {
            List<DWire> outWireTuples = new ArrayList<DWire>();
            for (DGate gate : gateTuples) {
                outWireTuples.add(gate.output);
            }
            outputWires.add(outWireTuples);
        }
        return outputWires;
    }

    public static List<List<DGate>> getCyclicalGates(List<DGate> netlist) {
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

    private static BiMap getSRLatchBiMap(List<List<DGate>> cyclicGates, DGateType type) {
        BiMap<Integer, Integer> map = HashBiMap.create();
        for (List<DGate> cyclicTuple : cyclicGates) {
            if (cyclicTuple.size() == 2) {
                if (cyclicTuple.get(0).gtype.equals(type) && cyclicTuple.get(1).gtype.equals(type)) {
                    if ((cyclicTuple.get(0).input.size() == 2) && (cyclicTuple.get(1).input.size() == 2)) {
                        if (cyclicTuple.get(0).containsWireAsInput(cyclicTuple.get(1).output) && cyclicTuple.get(1).containsWireAsInput(cyclicTuple.get(0).output)) {
                            //System.out.println("SR " + type.toString() +  " Latch found!");
                            //System.out.println(cyclicTuple.get(0).gindex);
                            //System.out.println(cyclicTuple.get(1).gindex);
                            if (map.containsKey(cyclicTuple.get(0).gindex)) {
                                map.put(cyclicTuple.get(1).gindex, cyclicTuple.get(0).gindex);
                            } else {
                                if (map.containsValue(cyclicTuple.get(1).gindex)) {
                                    map.put(cyclicTuple.get(1).gindex, cyclicTuple.get(0).gindex);
                                } else {
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

    public static BiMap getSRLatchNOR(List<List<DGate>> cyclicGates) {
        return getSRLatchBiMap(cyclicGates, DGateType.NOR);
    }

    public static BiMap getSRLatchNAND(List<List<DGate>> cyclicGates) {
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
            if (gate.output.wtype.equals(DWireType.output)) {
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
        Map<String, Integer> inputValues = new HashMap<String, Integer>();
        Map<String, Integer> currentState = new HashMap<String, Integer>();
        Map<String, Integer> nextState = new HashMap<String, Integer>();
        Map<String, Integer> nextToNextState = new HashMap<String, Integer>();

        for (String input : inputs) {
            System.out.print(input + " ");
        }
        for (String state : states) {
            if (!inputs.contains(state)) {
                System.out.print(state + " ");
                nonInputStates.add(state);
            }
        }
        for (String state : statesList) {
            System.out.print(state + "+1 ");
        }
        List<String> nonStateOutputs = new ArrayList<String>();
        for (String output : outputs) {
            if (!states.contains(output)) {
                System.out.print(output + " ");
                nonStateOutputs.add(output);
            }
        }
        System.out.println("");
        for (int i = 0; i < Math.pow(2, inputs.size()); i++) {

            String bin = Convert.dectoBin(i, inputs.size());
            for (int j = 0; j < inputs.size(); j++) {
                String binaryVal = "";
                binaryVal += bin.charAt(j);
                int binValue = Integer.valueOf(binaryVal);
                inputValues.put(inputs.get(j), binValue);
                String space = "";
                for (int m = 0; m < inputs.get(j).length(); m++) {
                    space += " ";
                }
                System.out.print(binValue + space);
            }

            for (String state : states) {
                if (inputValues.containsKey(state)) {
                    currentState.put(state, inputValues.get(state));
                }
            }

            //System.out.println("INPUTS :: " + inputValues);
            //Current State
            //System.out.println("Current State");
            for (DGate gate : netlist) {
                for (DWire wire : gate.input) {
                    if (inputValues.containsKey(wire.name)) {
                        if (inputValues.get(wire.name) == 0) {
                            wire.wValue = DWireValue._0;
                        }
                        if (inputValues.get(wire.name) == 1) {
                            wire.wValue = DWireValue._1;
                        }
                    }
                }
                BooleanSimulator.bfunction(gate);
                if (states.contains(gate.output.name) && !inputValues.containsKey(gate.output.name)) {
                    int binVal = -1;
                    if (gate.output.wValue.equals(DWireValue._0)) {
                        binVal = 0;
                    }
                    if (gate.output.wValue.equals(DWireValue._1)) {
                        binVal = 1;
                    }
                    currentState.put(gate.output.name, binVal);
                }
                if (states.contains(gate.output.name) && inputValues.containsKey(gate.output.name)) {
                    int binVal = -1;
                    if (gate.output.wValue.equals(DWireValue._0)) {
                        binVal = 0;
                    }
                    if (gate.output.wValue.equals(DWireValue._1)) {
                        binVal = 1;
                    }
                    nextState.put(gate.output.name, binVal);
                    inputValues.put(gate.output.name, binVal);
                }
                //System.out.println(gate.output.name + ":" + gate.output.wValue);

            }
            for (String nonInputState : nonInputStates) {
                String space = "";
                for (int m = 0; m < nonInputState.length(); m++) {
                    space += " ";
                }
                System.out.print(currentState.get(nonInputState) + space);
            }
           //System.out.println("Current State :: " + currentState);
            //System.out.println("\n\n");

            //Next State
            //System.out.println("Next State");
            for (DGate gate : netlist) {
                for (DWire wire : gate.input) {
                    if (inputValues.containsKey(wire.name)) {
                        if (inputValues.get(wire.name) == 0) {
                            wire.wValue = DWireValue._0;
                        }
                        if (inputValues.get(wire.name) == 1) {
                            wire.wValue = DWireValue._1;
                        }
                    }
                }
                BooleanSimulator.bfunction(gate);
                if (states.contains(gate.output.name) && !inputValues.containsKey(gate.output.name)) {
                    int binVal = -1;
                    if (gate.output.wValue.equals(DWireValue._0)) {
                        binVal = 0;
                    }
                    if (gate.output.wValue.equals(DWireValue._1)) {
                        binVal = 1;
                    }
                    nextState.put(gate.output.name, binVal);
                }
                if (states.contains(gate.output.name) && inputValues.containsKey(gate.output.name)) {
                    int binVal = -1;
                    if (gate.output.wValue.equals(DWireValue._0)) {
                        binVal = 0;
                    }
                    if (gate.output.wValue.equals(DWireValue._1)) {
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
                for (int m = 0; m < state.length() + 2; m++) {
                    space += " ";
                }
                System.out.print(nextState.get(state) + space);
            }

            //Next to next State
            //System.out.println("Next To Next State");
            for (DGate gate : netlist) {
                for (DWire wire : gate.input) {
                    if (inputValues.containsKey(wire.name)) {
                        if (inputValues.get(wire.name) == 0) {
                            wire.wValue = DWireValue._0;
                        }
                        if (inputValues.get(wire.name) == 1) {
                            wire.wValue = DWireValue._1;
                        }
                    }
                }
                BooleanSimulator.bfunction(gate);
                if (states.contains(gate.output.name) && !inputValues.containsKey(gate.output.name)) {
                    int binVal = -1;
                    if (gate.output.wValue.equals(DWireValue._0)) {
                        binVal = 0;
                    }
                    if (gate.output.wValue.equals(DWireValue._1)) {
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

    public static Map<String, List<Map<String, Integer>>> truthtable(List<DGate> netlist) {

        Map<String, List<Map<String, Integer>>> map = new HashMap<String, List<Map<String, Integer>>>();

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
            if (gate.output.wtype.equals(DWireType.output)) {
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

        BiMap<Integer, Integer> srnormap = getSRLatchNOR(cyclicGates);
        BiMap<Integer, Integer> srnandmap = getSRLatchNAND(cyclicGates);

        List<String> statesList = new ArrayList<String>(states);
        System.out.println("INPUTS :: " + inputs);
        System.out.println("STATES :: " + states);
        List<String> nonInputStates = new ArrayList<String>();
        System.out.println("TRUTH TABLE");
        System.out.println("===========================");
        Map<String, Integer> inputValues = new HashMap<String, Integer>();
        Map<String, Integer> currentState = new HashMap<String, Integer>();
        Map<String, Integer> nextState = new HashMap<String, Integer>();

        for (String input : inputs) {
            System.out.print(input + " ");
        }
        for (String state : states) {
            if (!inputs.contains(state)) {
                System.out.print(state + " ");
                nonInputStates.add(state);
            }
        }
        for (String state : statesList) {
            System.out.print(state + "+1 ");
        }
        List<String> nonStateOutputs = new ArrayList<String>();
        for (String output : outputs) {
            if (!states.contains(output)) {
                System.out.print(output + " ");
                nonStateOutputs.add(output);
            }
        }
        boolean bistable;
        Set<Integer> srnand = new HashSet<Integer>();
        Set<Integer> srnor = new HashSet<Integer>();

        System.out.println("");
        for (int i = 0; i < Math.pow(2, inputs.size()); i++) {

            inputValues = new HashMap<String, Integer>();
            currentState = new HashMap<String, Integer>();
            nextState = new HashMap<String, Integer>();

            String bin = Convert.dectoBin(i, inputs.size());
            for (int j = 0; j < inputs.size(); j++) {
                String binaryVal = "";
                binaryVal += bin.charAt(j);
                int binValue = Integer.valueOf(binaryVal);
                inputValues.put(inputs.get(j), binValue);
            }

            for (String state : states) {
                if (inputValues.containsKey(state)) {
                    currentState.put(state, inputValues.get(state));
                }
            }

            srnand = new HashSet<Integer>();
            srnor = new HashSet<Integer>();
            bistable = false;

            //Current State
            for (DGate gate : netlist) {

                for (DWire wire : gate.input) {
                    if (inputValues.containsKey(wire.name)) {
                        if (inputValues.get(wire.name) == 0) {
                            wire.wValue = DWireValue._0;
                        }
                        if (inputValues.get(wire.name) == 1) {
                            wire.wValue = DWireValue._1;
                        }
                    }
                }

                //SR NOR Bistable check
                if (gate.gtype.equals(DGateType.NOR)) {
                    if (srnormap.containsKey(gate.gindex)) {
                        srnor.add(gate.gindex);
                        if (srnor.contains(srnormap.get(gate.gindex))) {
                            if (bistableSRNOR(gate, netlist.get(srnormap.get(gate.gindex)))) {
                                bistable = true;
                                break;
                            }
                        }
                    }
                    if (srnormap.containsValue(gate.gindex)) {
                        srnor.add(gate.gindex);
                        if (srnor.contains(srnormap.inverse().get(gate.gindex))) {
                            if (bistableSRNOR(gate, netlist.get(srnormap.get(gate.gindex)))) {
                                bistable = true;
                                break;
                            }
                        }
                    }
                }

                //SR NAND Bistable check
                if (gate.gtype.equals(DGateType.NAND)) {
                    if (srnandmap.containsKey(gate.gindex)) {
                        srnand.add(gate.gindex);
                        if (srnand.contains(srnandmap.get(gate.gindex))) {
                            if (bistableSRNAND(gate, netlist.get(srnandmap.get(gate.gindex)))) {
                                bistable = true;
                                break;
                            }
                        }
                    }
                    if (srnandmap.containsValue(gate.gindex)) {
                        srnand.add(gate.gindex);
                        if (srnand.contains(srnandmap.inverse().get(gate.gindex))) {
                            if (bistableSRNAND(gate, netlist.get(srnandmap.get(gate.gindex)))) {
                                bistable = true;
                                break;
                            }
                        }
                    }
                }

                BooleanSimulator.bfunction(gate);
                if (states.contains(gate.output.name) && !inputValues.containsKey(gate.output.name)) {
                    int binVal = -1;
                    if (gate.output.wValue.equals(DWireValue._0)) {
                        binVal = 0;
                    }
                    if (gate.output.wValue.equals(DWireValue._1)) {
                        binVal = 1;
                    }
                    currentState.put(gate.output.name, binVal);
                }
                if (states.contains(gate.output.name) && inputValues.containsKey(gate.output.name)) {
                    int binVal = -1;
                    if (gate.output.wValue.equals(DWireValue._0)) {
                        binVal = 0;
                    }
                    if (gate.output.wValue.equals(DWireValue._1)) {
                        binVal = 1;
                    }
                    nextState.put(gate.output.name, binVal);
                    inputValues.put(gate.output.name, binVal);
                }

            }
            if (bistable) {
                System.out.println("BISTABLE!!");
                continue;
            }

            for (int j = 0; j < inputs.size(); j++) {
                String binaryVal = "";
                binaryVal += bin.charAt(j);
                int binValue = Integer.valueOf(binaryVal);
                String space = "";
                for (int m = 0; m < inputs.get(j).length(); m++) {
                    space += " ";
                }
                System.out.print(binValue + space);
            }

            for (String nonInputState : nonInputStates) {
                String space = "";
                for (int m = 0; m < nonInputState.length(); m++) {
                    space += " ";
                }
                System.out.print(currentState.get(nonInputState) + space);
            }

            //Next State
            for (DGate gate : netlist) {
                for (DWire wire : gate.input) {
                    if (inputValues.containsKey(wire.name)) {
                        if (inputValues.get(wire.name) == 0) {
                            wire.wValue = DWireValue._0;
                        }
                        if (inputValues.get(wire.name) == 1) {
                            wire.wValue = DWireValue._1;
                        }
                    }
                }
                BooleanSimulator.bfunction(gate);
                if (states.contains(gate.output.name) && !inputValues.containsKey(gate.output.name)) {
                    int binVal = -1;
                    if (gate.output.wValue.equals(DWireValue._0)) {
                        binVal = 0;
                    }
                    if (gate.output.wValue.equals(DWireValue._1)) {
                        binVal = 1;
                    }
                    nextState.put(gate.output.name, binVal);
                }
                if (states.contains(gate.output.name) && inputValues.containsKey(gate.output.name)) {
                    int binVal = -1;
                    if (gate.output.wValue.equals(DWireValue._0)) {
                        binVal = 0;
                    }
                    if (gate.output.wValue.equals(DWireValue._1)) {
                        binVal = 1;
                    }
                    inputValues.put(gate.output.name, binVal);
                }
            }

            List<Map<String, Integer>> stateList = new ArrayList<Map<String, Integer>>();
            stateList.add(inputValues);
            stateList.add(nextState);

            map.put(bin, stateList);

            for (String state : statesList) {
                String space = "";
                for (int m = 0; m < state.length() + 2; m++) {
                    space += " ";
                }
                System.out.print(nextState.get(state) + space);
            }

            System.out.println("");

        }

        return map;
    }

    public static String createWaveform(List<DGate> netlist) {
        List<String> ttInputs = new ArrayList<String>();
        List<String> inputs = new ArrayList<String>();
        for (DGate gate : netlist) {
            for (DWire wire : gate.input) {
                if (wire.wtype.equals(DWireType.input)) {
                    if (!ttInputs.contains(wire.name)) {
                        ttInputs.add(wire.name);
                        inputs.add(wire.name);
                    }
                }
            }
        }

        Map<String, List<Map<String, Integer>>> map = truthtable(netlist);
        List<String> states = new ArrayList<String>();
        for (List<DWire> outputWires : getCyclicalOutputWiresFromNetlist(netlist)) {
            for (DWire outputWire : outputWires) {
                if (!states.contains(outputWire.name)) {
                    states.add(outputWire.name);
                }
            }
            String wireName = getHighestStageWire(outputWires).name;
            if (!ttInputs.contains(wireName)) {
                ttInputs.add(wireName);
            }
        }
        List<String> remaining = new ArrayList<String>(map.keySet());
        String currentState = remaining.get(0);
        String space = "";
        for (int i = 0; i < inputs.size(); i++) {
            System.out.print(inputs.get(i) + " ");
        }
        for (int i = 0; i < states.size(); i++) {
            System.out.print(states.get(i) + " ");
        }
        System.out.println("");
        int lineCount = 0;
        List<List<String>> uniqueStates = new ArrayList<List<String>>();
        List<String> uniqueState = new ArrayList<String>();
        Map<String,String> statelines = new HashMap<String,String>();
        while (!remaining.isEmpty()) {
            lineCount++ ;
            String stateline = "";
            if (uniqueState.contains(currentState)) {
                uniqueStates.add(uniqueState);
                uniqueState = new ArrayList<String>();
            }

            uniqueState.add(currentState);

            for (int i = 0; i < inputs.size(); i++) {
                space = "";
                for (int j = 0; j < inputs.get(i).length(); j++) {
                    space += " ";
                }
                System.out.print(map.get(currentState).get(0).get(inputs.get(i)) + space);
                stateline += map.get(currentState).get(0).get(inputs.get(i)) + space;
            }

            for (int i = 0; i < states.size(); i++) {
                space = "";
                for (int j = 0; j < states.get(i).length(); j++) {
                    space += " ";
                }
                System.out.print(map.get(currentState).get(1).get(states.get(i)) + space);
                stateline += map.get(currentState).get(1).get(states.get(i)) + space;
            }
            String inputVals = currentState.substring(0, inputs.size());

            if (remaining.contains(currentState)) {
                remaining.remove(currentState);
            }
            if(!statelines.containsKey(currentState)){
                statelines.put(currentState, stateline);
            }
            String stateVals = "";
            for (int i = inputs.size(); i < ttInputs.size(); i++) {
                stateVals += map.get(currentState).get(1).get(ttInputs.get(i));
            }
            //System.out.println("Input :: " + inputVals);
            //System.out.println("State :: " + stateVals);
            List<String> flipsInRemaining = flipsInSet(getAllFlips(inputVals, stateVals), remaining);
            if (!flipsInRemaining.isEmpty()) {
                currentState = flipsInRemaining.get(random(0, flipsInRemaining.size() - 1));
            } else {
                boolean possibleState = false;
                while (!possibleState) {
                    String newInputVals = randomFlipState(inputVals);
                    currentState = newInputVals + stateVals;
                    if (map.containsKey(currentState)) {
                        possibleState = true;
                    }
                }
            }
            System.out.println("");
        }

        System.out.println("UNIQUE STATE LIST :: \n" + uniqueStates);
        int longestIndex = 0;
        int longestChain = uniqueStates.get(0).size();
        for(int i=0;i<uniqueStates.size();i++){
            if(uniqueStates.get(i).size() > longestChain){
                longestChain = uniqueStates.get(i).size();
                longestIndex = i;
            }
        }
        
        System.out.println("Longest Chain size :: " + longestChain);
        System.out.println("Longest Chain index :: " + longestIndex);
        System.out.println("Longest Chain :: " + uniqueStates.get(longestIndex));
        System.out.println("\n\n");
        
        Map<String,Integer> stateCounts = new HashMap<String,Integer>();
        for(String state:uniqueStates.get(longestIndex)){
            stateCounts.put(state, 1);
        }
        List<String> prevStates = new ArrayList<String>();
        List<String> nextStates = new ArrayList<String>();
        
        boolean maxLim = false;
        for(int i=longestIndex-1;i>=0;i--){
            for(int j= uniqueStates.get(i).size()-1; j>=0; j--){
                
                if(stateCounts.containsKey(uniqueStates.get(i).get(j))){
                    int val = stateCounts.get(uniqueStates.get(i).get(j)) + 1;
                    if(val == 3){
                        maxLim = true;
                        break;
                    }
                    stateCounts.put(uniqueStates.get(i).get(j), val);
                } else {
                    stateCounts.put(uniqueStates.get(i).get(j), 1);
                }
                prevStates.add(uniqueStates.get(i).get(j));
            }
            if(maxLim){
                break;
            }
        }
        
        maxLim = false;
        for(int i=longestIndex+1; i<uniqueStates.size();i++){
            for(int j = 0; j < uniqueStates.get(i).size(); j++){
                if(stateCounts.containsKey(uniqueStates.get(i).get(j))){
                    int val = stateCounts.get(uniqueStates.get(i).get(j)) + 1;
                    if(val == 3){
                        maxLim = true;
                        break;
                    }
                    stateCounts.put(uniqueStates.get(i).get(j), val);
                } else {
                    stateCounts.put(uniqueStates.get(i).get(j), 1);
                }
                nextStates.add(uniqueStates.get(i).get(j));
            }
            if(maxLim){
                break;
            }
        }
        
        Set<String> finalUniqueStates = new HashSet<String>();
        finalUniqueStates.addAll(nextStates);
        finalUniqueStates.addAll(prevStates);
        finalUniqueStates.addAll(uniqueStates.get(longestIndex));
        
        System.out.println("Initial Number of unique States :: " + map.keySet().size());
        System.out.println("Final Number of unique States :: " + finalUniqueStates.size());
        
        System.out.println("Initial Line count :: " + lineCount);
        System.out.println("Final Line count :: " + (nextStates.size() + uniqueStates.get(longestIndex).size() + prevStates.size()));
        System.out.println("\n\n");
        
        String waveform = "";
        
        System.out.println("FINAL WAVEFORM ########################################");
        for (int i = 0; i < inputs.size(); i++) {
            System.out.print(inputs.get(i) + " ");
            waveform += inputs.get(i) + " ";
        }
        for (int i = 0; i < states.size(); i++) {
            System.out.print(states.get(i) + " ");
            waveform += states.get(i) + " ";
        }
        System.out.println("");
        for(int i=prevStates.size()-1;i>=0; i--){
            System.out.println(statelines.get(prevStates.get(i)));
            waveform += statelines.get(prevStates.get(i)) + "\n";
        }
        waveform += "\n";
        for(String state: uniqueStates.get(longestIndex)){
            System.out.println(statelines.get(state));
            waveform += statelines.get(state) + "\n";
        }
        for(String state: nextStates){
            System.out.println(statelines.get(state));
            waveform += statelines.get(state) + "\n";
        }
        
        System.out.println("######################################\n\n");
        return waveform;

        
    }

    private static List<String> flipsInSet(List<String> flips, List<String> remaining) {
        List<String> inRemaining = new ArrayList<String>();
        for (String flip : flips) {
            if (remaining.contains(flip)) {
                inRemaining.add(flip);
            }
        }
        return inRemaining;
    }

    private static List<String> getAllFlips(String input, String state) {
        List<String> flips = new ArrayList<String>();
        for (int i = 0; i < input.length(); i++) {
            String flip = "";
            for (int j = 0; j < input.length(); j++) {
                if (i == j) {
                    if (input.charAt(j) == '1') {
                        flip += "0";
                    } else {
                        flip += "1";
                    }
                } else {
                    flip += input.charAt(j);
                }
            }
            flips.add(flip + state);
        }

        return flips;
    }

    private static boolean bistableSRNOR(DGate gate1, DGate gate2) {

        for (DWire gate1input : gate1.input) {
            if (!gate1input.name.equals(gate2.output.name)) {
                if (gate1input.wValue.equals(DWireValue._0)) {
                    return false;
                }
                break;
            }
        }
        for (DWire gate2input : gate2.input) {
            if (!gate2input.name.equals(gate1.output.name)) {
                if (gate2input.wValue.equals(DWireValue._0)) {
                    return false;
                }
                break;
            }
        }

        return true;
    }

    private static boolean bistableSRNAND(DGate gate1, DGate gate2) {

        for (DWire gate1input : gate1.input) {
            if (!gate1input.name.equals(gate2.output.name)) {
                if (gate1input.wValue.equals(DWireValue._1)) {
                    return false;
                }
                break;
            }
        }
        for (DWire gate2input : gate2.input) {
            if (!gate2input.name.equals(gate1.output.name)) {
                if (gate2input.wValue.equals(DWireValue._1)) {
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

    private static String randomFlipState(String state) {
        int index = random(0, state.length() - 1);
        String newState = "";
        for (int i = 0; i < state.length(); i++) {
            if (i == index) {
                if (state.charAt(i) == '1') {
                    newState += "0";
                } else {
                    newState += "1";
                }
            } else {
                newState += state.charAt(i);
            }
        }
        return newState;
    }

    private static int random(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

}
