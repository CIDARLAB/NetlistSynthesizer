/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cellocad.BU.subcircuit;

import java.util.ArrayList;
import java.util.List;
import org.cellocad.BU.DAG.DNode;
import org.cellocad.BU.ParseVerilog.Convert;
import org.cellocad.BU.booleanLogic.BooleanSimulator;
import org.cellocad.BU.netsynth.DGate;
import org.cellocad.BU.netsynth.DWire;
import org.cellocad.BU.netsynth.DWireType;
import org.cellocad.BU.netsynth.NetSynth;

/**
 *
 * @author prash
 */
public class subCircuitEnumerator {

    public static DNode getGraph(List<DGate> netlist){
        DNode output = new DNode();
        
        
        
        return output;
    }
    
    public static void nameGates(List<DGate> netlist){
        
        int count=0;
        for(DGate gate:netlist){
            gate.gname = "g" + count;
            count++;
        }
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
                modifiedsubNetlist = NetSynth.rewireNetlist(modifiedsubNetlist);
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
    
    public static List<SubNetlist> getSubNetlistDetails_PolyMethod(List<DGate> netlist,int index){
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
                modifiedsubNetlist = NetSynth.rewireNetlist(modifiedsubNetlist);
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
                modifiedsubNetlist = NetSynth.rewireNetlist(modifiedsubNetlist);
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
