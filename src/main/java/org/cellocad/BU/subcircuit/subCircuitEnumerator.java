/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cellocad.BU.subcircuit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.cellocad.BU.dom.DEdge;
import org.cellocad.BU.dom.DNode;
import org.cellocad.BU.parseVerilog.Convert;
import org.cellocad.BU.simulators.BooleanFunctions;
import org.cellocad.BU.simulators.BooleanSimulator;
import org.cellocad.BU.dom.DGate;
import org.cellocad.BU.dom.DWire;
import org.cellocad.BU.dom.DWire.DWireValue;
import org.cellocad.BU.dom.DWireType;
import org.cellocad.BU.netsynth.NetSynth;

/**
 *
 * @author prash
 */
public class subCircuitEnumerator {

    public static Map<String,DNode> createGraph(List<DGate> netlist){
        DNode output = new DNode();
        Map<String,DEdge> edges = new HashMap<String, DEdge>();
        Map<String,DNode> nodes = new HashMap<String, DNode>();
        nameGates(netlist);
        for (DGate gate : netlist) {
            
            //Make O gates?
            List<String> inputs = getInputs(netlist);
            List<String> outputs = getOutputs(netlist);
            DNode node = new DNode();
            node.gatename = gate.gname;
            node.type = gate.gtype;
            node.inputs = new ArrayList<DEdge>();
            for(DWire input:gate.input){
                if(!edges.containsKey(input.name)){
                    DEdge edge = new DEdge();
                    edge.type = input.wtype;
                    edge.wirename = input.name;
                    edges.put(edge.wirename, edge);
                }
                if(input.wtype.equals(DWireType.input)){
                    if(!nodes.containsKey(input.name)){
                        DNode inputNode = new DNode();
                        inputNode.gatename = input.name;
                        inputNode.output = edges.get(input.name);
                        nodes.put(inputNode.gatename, inputNode);
                    }
                    edges.get(input.name).from = nodes.get(input.name);
                }
                node.inputs.add(edges.get(input.name));
                edges.get(input.name).to.add(node);
            }
            if(!edges.containsKey(gate.output.name)){
                DEdge edge = new DEdge();
                edge.type = gate.output.wtype;
                edge.wirename = gate.output.name;
                edges.put(edge.wirename, edge);
            }
            node.output = edges.get(gate.output.name);
            edges.get(gate.output.name).from = node;
            nodes.put(node.gatename, node);
        }
        //return nodes.get(netlist.get(netlist.size()-1).gname);
        return nodes;
    }
    
    public static Set<Set<String>> getSubCircuitInputs(DNode node, int k){
        
        if(node.subcircuitLeaves.isEmpty()){
            Set<String> nodeVal = new HashSet<String>();
            nodeVal.add(node.gatename);
            node.subcircuitLeaves.add(nodeVal);
            List<Set<Set<String>>> sets = new ArrayList<Set<Set<String>>>();
            for(DEdge edge:node.inputs){
               sets.add(getSubCircuitInputs(edge.from,k));
            }
            node.subcircuitLeaves.addAll(computeSetUnion(sets,k));
        }
        return node.subcircuitLeaves;
    }
    
    public static List<SubNetlist> getSubNetlists(DNode node, Set<Set<String>> leaves,Map<String,DNode> nodes){
        List<SubNetlist> subnetlists = new ArrayList<SubNetlist>();
        for(Iterator<Set<String>> it = leaves.iterator();it.hasNext();){
            Set<String> set = new HashSet<String>();
            set = it.next();
            SubNetlist subnetlist = new SubNetlist();
            List<String> leaveValues = new ArrayList<String>();
            for(Iterator<String> leaf = set.iterator();leaf.hasNext();){
                String leafVal = leaf.next();
                subnetlist.inputs.add(nodes.get(leafVal).output.wirename);
                leaveValues.add(leafVal);
            }
            
            subnetlist.tt = getSubCircuitTruthTable(node,leaveValues);
            subnetlists.add(subnetlist);
            
            //System.out.println("Inputs ::" +subnetlist.inputs);
            //System.out.println("TT     ::" +subnetlist.tt);
            
        }
        
        return subnetlists;
    }
    
    public static Set<Set<String>> computeSetUnion(List<Set<Set<String>>> sets, int k){
        Set<Set<String>> union = new HashSet<Set<String>>();
        if(sets.size() == 1)
        {
            return sets.get(0);
        }
        else if(sets.size() > 1){
            union = compute2SetUnion(sets.get(0),sets.get(1),k);
            for(int i=2;i<sets.size();i++){
                union = compute2SetUnion(union,sets.get(i),k);
            }
        }
        return union;
    }
    
    public static Set<Set<String>> compute2SetUnion(Set<Set<String>> set1,Set<Set<String>> set2, int k){
        Set<Set<String>> union = new HashSet<Set<String>>();
        for(Iterator<Set<String>> it1 = set1.iterator();it1.hasNext();){
            Set<String> set1subset = new HashSet<String>();
            set1subset = it1.next();
            for(Iterator<Set<String>> it2 = set2.iterator();it2.hasNext();){
                Set<String> set2subset = new HashSet<String>();
                set2subset = it2.next();
                Set<String> subset = new HashSet<String>();
                subset.addAll(set1subset);
                subset.addAll(set2subset);
                if(subset.size() <= k){
                    union.add(subset);
                }
            }            
        }
        return union;
    }
    
    public static String getSubCircuitTruthTable(DNode node, List<String> leaves){
        String tt = "";
        int pow = (int)Math.pow(2, leaves.size());
        for(int i=0;i<pow;i++){
            String inpRow = Convert.dectoBin(i, leaves.size());
            Map<String,DWireValue> inputMap = new HashMap<String,DWireValue>();
            for(int j=0;j<leaves.size();j++){
                DWireValue val;
                switch(inpRow.charAt(j)){
                    case '1': val = DWireValue._1;
                        break;
                    case '0': val = DWireValue._0;
                        break;
                    default : val = DWireValue._x;
                        break;
                }
                inputMap.put(leaves.get(j), val);
            }
            DWireValue output;
            output = getEdgeValue(node,inputMap);
            switch (output) {
                case _1: tt += "1";
                    break;
                case _0: tt += "0";
                    break;
                default: tt += "-";
                    break;
            }
        }
        return tt;
    }
    
    public static DWireValue getEdgeValue(DNode node,Map<String,DWireValue> inputMap){
        if(inputMap.containsKey(node.gatename)){
            return inputMap.get(node.gatename);
        }
        else{
            List<DWireValue> gateInputValues = new ArrayList<DWireValue>();
            for(DEdge gateInput : node.inputs){
                gateInputValues.add(getEdgeValue(gateInput.from,inputMap));
            }
            return BooleanFunctions.bcompute(node.type, gateInputValues);
        }
    }
    
    public static List<DNode> getSubcircuit(DNode node, Set<String> leaves, List<DNode> allNodes, Set<String> listOfNodes){
        //List<DNode> subnodes = new ArrayList<DNode>();
        if(leaves.contains(node.gatename)){
            
        }
        else{
            if(!listOfNodes.contains(node.gatename)){
                allNodes.add(node);
                listOfNodes.add(node.gatename);
            }
            for(DEdge edge:node.inputs){
                getSubcircuit(edge.from,leaves,allNodes,listOfNodes);
            }
        }
        return allNodes;
    }
    
    public static List<String> getInputs(List<DGate> netlist){
        List<String> inputs = new ArrayList<String>();
        for(DGate gate:netlist){
            for(DWire wire:gate.input){
                if(wire.wtype.equals(DWireType.input)){
                    if(!inputs.contains(wire.name)){
                        inputs.add(wire.name);
                    }
                }
            }
        }
        return inputs;
    }
    
    public static List<String> getOutputs(List<DGate> netlist){
        List<String> outputs = new ArrayList<String>();
        for(DGate gate:netlist){
            if (gate.output.wtype.equals(DWireType.output)) {
                if (!outputs.contains(gate.output.name)) {
                    outputs.add(gate.output.name);
                }
            }
        }
        return outputs;
    }
    
    public static void nameGates(List<DGate> netlist){
        
        int count=0;
        for(DGate gate:netlist){
            gate.gindex = count;
            gate.gname = "g" + count;
            count++;
        }
    }
    
    public static List<SubNetlist> getSubNetlistDetailsGraph(List<DGate> netlist,int index,int k){
        List<SubNetlist> subnetlist = new ArrayList<SubNetlist>();
        Map<String,DNode> nodeMap = new HashMap();
        
        //Change this to create Graph for netlist from index to end?
        nodeMap = createGraph(netlist);
        //node = nodes.get(netlist.get(netlist.size()-1).gname);
        Set<Set<String>> sets = new HashSet<Set<String>>();
        sets = subCircuitEnumerator.getSubCircuitInputs(nodeMap.get(netlist.get(index).gname), k);
        subnetlist = subCircuitEnumerator.getSubNetlists(nodeMap.get(netlist.get(index).gname),sets,nodeMap);
        
        return subnetlist;
    }
    
    public static List<SubNetlist> getSubNetlistDetails(List<DGate> netlist,int index){
        List<SubNetlist> subNList = new ArrayList<SubNetlist>();
        List<List<Integer>> allIndices = new ArrayList<List<Integer>>();
        
        int high = getHigh(index);
        int low = getLow(index);
        for(int j=high;j>=low;j--){
            String binEq = Convert.dectoBin(j, index + 1);
            String revBin = new StringBuilder(binEq).reverse().toString();
                List<DGate> subNetlist = new ArrayList<DGate>();
                for (int k = 0; k < revBin.length(); k++) {
                    if (revBin.charAt(k) == '1') {
                        subNetlist.add(netlist.get(k));
                    }
                }
                List<DGate> modifiedsubNetlist = new ArrayList<DGate>();
                modifiedsubNetlist = removeDanglingNodeInSubnetlist(subNetlist);
                List<String> inputs = getSubnetlistInputs(modifiedsubNetlist);
                NetSynth.rewireNetlist(modifiedsubNetlist);
                if (inputs.size() <= 3) {
                    List<String> tt = BooleanSimulator.getTruthTable(modifiedsubNetlist, inputs);
                    //NetSynth.printNetlist(modifiedsubNetlist);
                    List<Integer> indices = new ArrayList<Integer>();
                    for(int k=0;k<modifiedsubNetlist.size();k++)
                        indices.add(modifiedsubNetlist.get(k).gindex);
                    
                    if(!containsSubcircuit(indices,allIndices)){
                        allIndices.add(indices);
                        SubNetlist newsubnlist = new SubNetlist();
                        newsubnlist.tt = tt.get(0);
                        newsubnlist.inputs.addAll(inputs);
                        subNList.add(newsubnlist);
                        
                        //allSubcircuits.add(modifiedsubNetlist);
                    }
                }
        }
        return subNList;
    }
    
    public static List<List<DGate>> getSubcircuits(List<DGate> netlist) {
        NetSynth.assignGateIndex(netlist);
        List<List<Integer>> allIndices = new ArrayList<List<Integer>>();
        List<List<DGate>> allSubcircuits = new ArrayList<List<DGate>>();
        
        for (int i = netlist.size()-1; i >=0; i--) {
            int high = getHigh(i);
            int low = getLow(i);
            for (int j = high; j >= low; j--) {
                String binEq = Convert.dectoBin(j, i + 1);
                String revBin = new StringBuilder(binEq).reverse().toString();
                List<DGate> subNetlist = new ArrayList<DGate>();
                for (int k = 0; k < revBin.length(); k++) {
                    if (revBin.charAt(k) == '1') {
                        subNetlist.add(netlist.get(k));
                    }
                }
                List<DGate> modifiedsubNetlist = new ArrayList<DGate>();
                modifiedsubNetlist = removeDanglingNodeInSubnetlist(subNetlist);
                List<String> inputs = getSubnetlistInputs(modifiedsubNetlist);
                NetSynth.rewireNetlist(modifiedsubNetlist);
                if (inputs.size() <= 4) {
                    List<String> tt = BooleanSimulator.getTruthTable(modifiedsubNetlist, inputs);
                    //NetSynth.printNetlist(modifiedsubNetlist);
                    List<Integer> indices = new ArrayList<Integer>();
                    for(int k=0;k<modifiedsubNetlist.size();k++)
                        indices.add(modifiedsubNetlist.get(k).gindex);
                    
                    if(!containsSubcircuit(indices,allIndices)){
                        allIndices.add(indices);
                        allSubcircuits.add(modifiedsubNetlist);
                    }
                    
                    /*System.out.println("Inputs : " + inputs);
                    System.out.println(DWire.printDWire(modifiedsubNetlist.get(modifiedsubNetlist.size() - 1).output));
                    System.out.println("Subnetlist Truthtable : " + tt.get(0));
                    System.out.println("Gate Indices : " + indices);*/
                }
                //System.out.println("--");
            }
            //System.out.println("--");
        }
        System.out.println("All Subcircuits:");
        for(List<DGate> subcirc:allSubcircuits){
            System.out.println("\nNetlist:\n------------");
            NetSynth.printNetlist(subcirc);
            System.out.println("------------\nSize:"+subcirc.size()+"\n------------");
        }
        return allSubcircuits;
    }
    
    public static boolean containsSubcircuit(List<Integer> indices, List<List<Integer>> subcircuits){
        boolean result = false;
            for(List<Integer> list:subcircuits){
                if(equalLists(indices,list)){
                    result = true;
                    break;
                }
            }
        
        return result;
    }
    
    public static boolean equalLists(List<Integer> list1, List<Integer> list2){
        boolean result = true;
        if(list1.size()!= list2.size())
            return false;
        for(int i=0;i<list1.size();i++){
            if(!list1.get(i).equals(list2.get(i))){
                result = false;
                break;
            }
        }
        
        return result;
    }
    
    public static int getHigh(int n) {
        int high = (int) ((Math.pow(2, n + 1) - 1));
        return high;
    }

    public static int getLow(int n) {
        int low = (int) (Math.pow(2, n));
        return low;
    }

    public static List<DGate> removeDanglingNodeInSubnetlist(List<DGate> netlist) {

        List<Integer> removegates = new ArrayList<Integer>();
        List<DGate> netlistCopy = new ArrayList<DGate>();
        List<DGate> tempnetlist = new ArrayList<DGate>();

        for (DGate x : netlist) {
            netlistCopy.add(new DGate(x));
        }
        //Preprocessing
        //netlist.get(netlist.size()-1).output.wtype = DWireType.output;
        for (int i = 0; i < netlistCopy.size() - 1; i++) {
            if (netlistCopy.get(i).output.wtype.equals(DWireType.output)) {
                removegates.add(i);
            }
        }
        for (int i = 0; i < netlistCopy.size(); i++) {
            if (!removegates.contains(i)) {
                tempnetlist.add(netlistCopy.get(i));
            }
        }
        tempnetlist.get(tempnetlist.size() - 1).output.wtype = DWireType.output;

        //------------
        /*List<DGate> tempnetlist = new ArrayList<DGate>();
         for (int i = 0; i < netlist.size(); i++) {
         tempnetlist.add(netlist.get(i));
         }*/
        List<DGate> reducednetlist = new ArrayList<DGate>();
        removegates = new ArrayList<Integer>();
        do {

            removegates = new ArrayList<Integer>();
            for (int i = 0; i < tempnetlist.size(); i++) {
                if (!tempnetlist.get(i).output.wtype.equals(DWireType.output)) {
                    String outname = tempnetlist.get(i).output.name.trim();
                    int inpcount = 0;
                    for (int j = i + 1; j < tempnetlist.size(); j++) {
                        for (int m = 0; m < tempnetlist.get(j).input.size(); m++) {
                            String inpname = tempnetlist.get(j).input.get(m).name.trim();
                            if (inpname.equals(outname)) {
                                inpcount++;
                            }
                        }
                    }
                    if (inpcount == 0) {
                        removegates.add(i);
                    }
                }
            }
            reducednetlist = new ArrayList<DGate>();
            for (int i = 0; i < tempnetlist.size(); i++) {
                if (!removegates.contains(i)) {
                    reducednetlist.add(tempnetlist.get(i));
                }
            }
            tempnetlist = new ArrayList<DGate>();
            tempnetlist.addAll(reducednetlist);

        } while (!removegates.isEmpty());

        return reducednetlist;
    }

    public static List<String> getSubnetlistInputs(List<DGate> netlist) {

        List<String> outputnames = new ArrayList<String>();
        List<String> inputnames = new ArrayList<String>();
        List<String> connectors = new ArrayList<String>();

        for (int i = 0; i < netlist.size(); i++) {
            if (netlist.get(i).output.wtype.equals(DWireType.output)) {
                outputnames.add(netlist.get(i).output.name);
            } else {
                connectors.add(netlist.get(i).output.name);
            }

            for (int j = 0; j < netlist.get(i).input.size(); j++) {
                String inp = netlist.get(i).input.get(j).name;
                if (!outputnames.contains(inp) && !connectors.contains(inp) && !inputnames.contains(inp)) {
                    netlist.get(i).input.get(j).wtype = DWireType.input;
                    inputnames.add(inp);
                }
            }
        }
        //inputcount = inputnames.size();
        return inputnames;
    }

}
