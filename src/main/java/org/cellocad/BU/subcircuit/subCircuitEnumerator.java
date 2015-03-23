/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cellocad.BU.subcircuit;

import java.util.ArrayList;
import java.util.List;
import org.cellocad.BU.ParseVerilog.Convert;
import org.cellocad.BU.netsynth.DGate;
import org.cellocad.BU.netsynth.DWireType;
import org.cellocad.BU.netsynth.NetSynth;

/**
 *
 * @author prash
 */
public class subCircuitEnumerator {

    public static void getSubcircuits(List<DGate> netlist) {
        for (int i = 0; i < netlist.size(); i++) {
            int high = getHigh(i);
            int low = getLow(i);
            for (int j = low; j <= high; j++) {
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
                int inpCount = getSubnetlistInputCount(modifiedsubNetlist);
                if(inpCount == 3){
                    NetSynth.printNetlist(modifiedsubNetlist);
                }    
                
                System.out.println("--");
            }
            System.out.println("--");
        }

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

    public static int getSubnetlistInputCount(List<DGate> netlist) {
        int inputcount = 0;
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
                if(!outputnames.contains(inp) && !connectors.contains(inp) && !inputnames.contains(inp)){
                    inputnames.add(inp);
                }
            }
        }
        inputcount = inputnames.size();
        return inputcount;
    }

}
