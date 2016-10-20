/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cellocad.BU.simulators;

import org.cellocad.BU.parseVerilog.Convert;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.cellocad.BU.dom.DGate;
import org.cellocad.BU.dom.DWire;
import org.cellocad.BU.dom.DWireType;
import org.cellocad.BU.dom.DWire.DWireValue;

/**
 *
 * @author prashantvaidyanathan
 */
public class BooleanSimulator {

    public static DWireValue bfunctionWireValue(DGate gate) {
        switch (gate.gtype) {
            case AND:
                return BooleanFunctions.bANDWireValue(gate);

            case OR:
                return BooleanFunctions.bORWireValue(gate);

            case OUTPUT_OR:
                return BooleanFunctions.bORWireValue(gate);

            case NOR:
                return BooleanFunctions.bNORWireValue(gate);

            case NAND:
                return BooleanFunctions.bNANDWireValue(gate);

            case NOT:
                return BooleanFunctions.bNOTWireValue(gate);

            case BUF:
                return BooleanFunctions.bBUFWireValue(gate);

            case XOR:
                return BooleanFunctions.bXORWireValue(gate);

            case XNOR:
                return BooleanFunctions.bXNORWireValue(gate);

        }
        return null;
    }

    public static void bfunction(DGate gate) {
        switch (gate.gtype) {
            case AND:
                BooleanFunctions.bAND(gate);
                break;
            case OR:
                BooleanFunctions.bOR(gate);
                break;
            case OUTPUT_OR:
                BooleanFunctions.bOR(gate);
                break;
            case NOR:
                BooleanFunctions.bNOR(gate);
                break;
            case NAND:
                BooleanFunctions.bNAND(gate);
                break;
            case NOT:
                BooleanFunctions.bNOT(gate);
                break;
            case BUF:
                BooleanFunctions.bBUF(gate);
                break;
            case XOR:
                BooleanFunctions.bXOR(gate);
                break;
            case XNOR:
                BooleanFunctions.bXNOR(gate);
                break;
        }
    }

    public static String bpermute(List<DGate> netlist) {
        //DWireValue outvalue = DWireValue._x;
        String output = "";
        HashMap<String, DWire> inputsW = new HashMap<String, DWire>();
        List<DWire> inputL = new ArrayList<DWire>();

        for (DGate dg : netlist) {
            for (DWire dw : dg.input) {
                if (dw.wtype == DWireType.input) {
                    if (!inputsW.containsKey(dw.name.trim())) {
                        inputL.add(dw);

                        inputsW.put(dw.name.trim(), dw);
                    }
                }
            }
        }

        HashMap<String, Integer> inputWires = new HashMap<String, Integer>();
        int inpcnt = 0;
        for (DWire dwl : inputL) {
            inputWires.put(dwl.name.trim(), inpcnt);
            inpcnt++;
        }

        /*Iterator it = inputsW.entrySet().iterator();
         while(it.hasNext())
         {
         Map.Entry pair = (Map.Entry)it.next();
         String val = (String) pair.getKey();
         System.out.println(val);
         inputWires.put(val, inpcnt);
         inpcnt++;
         //DWire temp = (DWire)pair.getValue();
         }*/
        int inpsize = inputWires.size();
        int inppow = (int) Math.pow(2, inpsize);

        for (int i = 0; i < inppow; i++) {
            String xi = Convert.dectoBin(i, inpsize);
            for (DGate ng : netlist) {
                for (int j = 0; j < ng.input.size(); j++) {
                    DWire dw = ng.input.get(j);
                    if (dw.wtype == DWireType.input) {
                        int indx = inputWires.get(dw.name.trim());
                        if (xi.charAt(indx) == '0') {
                            ng.input.get(j).wValue = DWireValue._0;
                        } else if (xi.charAt(indx) == '1') {
                            ng.input.get(j).wValue = DWireValue._1;
                        }
                    } else if (dw.wtype == DWireType.Source) {
                        ng.input.get(j).wValue = DWireValue._1;
                    } else if (dw.wtype == DWireType.GND) {
                        ng.input.get(j).wValue = DWireValue._0;
                    }

                }
                bfunction(ng);

            }

            //int 
            DWireValue finaldw = netlist.get(netlist.size() - 1).output.wValue;
            if (finaldw == DWireValue._0) {
                output += "0";
            } else if (finaldw == DWireValue._1) {
                output += "1";
            } else if (finaldw == DWireValue._x) {
                output += "-";
            }

        }

        return output;
    }

    public static String bpermutePreComp(List<DGate> netlist) {
        //DWireValue outvalue = DWireValue._x;
        String output = "";
        HashMap<String, DWire> inputsW = new HashMap<String, DWire>();
        List<DWire> inputL = new ArrayList<DWire>();

        for (DGate dg : netlist) {
            for (DWire dw : dg.input) {
                if (dw.wtype == DWireType.input) {
                    if (!inputsW.containsKey(dw.name.trim())) {
                        inputL.add(dw);

                        inputsW.put(dw.name.trim(), dw);
                    }
                }
            }
        }

        HashMap<String, Integer> inputWires = new HashMap<String, Integer>();

        for (DWire dwl : inputL) {
            if ("a".equals(dwl.name.trim())) {
                inputWires.put(dwl.name.trim(), 0);
            } else if ("b".equals(dwl.name.trim())) {
                inputWires.put(dwl.name.trim(), 1);
            } //else if("c".equals(dwl.name.trim()))
            else {
                inputWires.put(dwl.name.trim(), 2);
            }

        }
        /*Iterator it = inputsW.entrySet().iterator();
         while(it.hasNext())
         {
         Map.Entry pair = (Map.Entry)it.next();
         String val = (String) pair.getKey();
         System.out.println(val);
         inputWires.put(val, inpcnt);
         inpcnt++;
         //DWire temp = (DWire)pair.getValue();
         }*/

        int inpsize = 3;
        int inppow = (int) Math.pow(2, inpsize);
        for (int i = 0; i < inppow; i++) {
            String xi = Convert.dectoBin(i, inpsize);
            for (DGate ng : netlist) {
                for (int j = 0; j < ng.input.size(); j++) {
                    DWire dw = ng.input.get(j);
                    if (dw.wtype == DWireType.input) {
                        int indx = inputWires.get(dw.name.trim());
                        if (xi.charAt(indx) == '0') {
                            ng.input.get(j).wValue = DWireValue._0;
                        } else if (xi.charAt(indx) == '1') {
                            ng.input.get(j).wValue = DWireValue._1;
                        }
                    } else if (dw.wtype == DWireType.Source) {
                        ng.input.get(j).wValue = DWireValue._1;
                    } else if (dw.wtype == DWireType.GND) {
                        ng.input.get(j).wValue = DWireValue._0;
                    }

                }
                bfunction(ng);

            }

            //int 
            DWireValue finaldw = netlist.get(netlist.size() - 1).output.wValue;
            if (finaldw == DWireValue._0) {
                output += "0";
            } else if (finaldw == DWireValue._1) {
                output += "1";
            } else if (finaldw == DWireValue._x) {
                output += "-";
            }

        }

        return output;
    }

    public static void printTruthTable(List<DGate> netlist) {
        List<String> inputnames = new ArrayList<String>();
        List<String> outputnames = new ArrayList<String>();
        HashMap<String, Integer> inpindex = new HashMap<String, Integer>();
        //HashMap<String,Integer> outpindex = new HashMap<String,Integer>();

        int inpindx = 0;
        for (int i = 0; i < netlist.size(); i++) {
            for (int j = 0; j < netlist.get(i).input.size(); j++) {
                if (netlist.get(i).input.get(j).wtype.equals(DWireType.input)) {
                    if (!inputnames.contains(netlist.get(i).input.get(j).name.trim())) {
                        inputnames.add(netlist.get(i).input.get(j).name.trim());
                        inpindex.put(netlist.get(i).input.get(j).name.trim(), inpindx);
                        inpindx++;
                    }
                }
            }
            if (netlist.get(i).output.wtype.equals(DWireType.output)) {
                if (!outputnames.contains(netlist.get(i).output.name.trim())) {
                    outputnames.add(netlist.get(i).output.name.trim());
                }
            }
        }
        System.out.println("\n------------------------------------");
        System.out.println("---------Truth Table----------------");
        String inputstring = "Input Order  : ";
        String outputstring = "Output Order : ";
        for (int i = 0; i < inputnames.size(); i++) {
            inputstring += inputnames.get(i) + " ";
        }
        for (int i = 0; i < outputnames.size(); i++) {
            outputstring += outputnames.get(i) + " ";
        }
        System.out.println(inputstring);
        System.out.println(outputstring);
        System.out.println("------------------------------------");
        int ttrows = (int) Math.pow(2, inputnames.size());
        for (int i = 0; i < ttrows; i++) {

            String ttRow = "| ";
            String rrinputrowboolean = Convert.dectoBin(i, inputnames.size());
            for (int j = 0; j < rrinputrowboolean.length(); j++) {
                ttRow += (rrinputrowboolean.charAt(j) + " ");
            }
            ttRow += "| ";
            for (int j = 0; j < netlist.size(); j++) {
                for (int k = 0; k < netlist.get(j).input.size(); k++) {
                    if (netlist.get(j).input.get(k).wtype.equals(DWireType.input)) {
                        int tempindx = inpindex.get(netlist.get(j).input.get(k).name.trim());
                        if (rrinputrowboolean.charAt(tempindx) == '1') {
                            netlist.get(j).input.get(k).wValue = DWireValue._1;
                        } else if (rrinputrowboolean.charAt(tempindx) == '0') {
                            netlist.get(j).input.get(k).wValue = DWireValue._0;
                        }
                    } else if (netlist.get(j).input.get(k).wtype.equals(DWireType.GND)) {
                        netlist.get(j).input.get(k).wValue = DWireValue._0;
                    } else if (netlist.get(j).input.get(k).wtype.equals(DWireType.Source)) {
                        netlist.get(j).input.get(k).wValue = DWireValue._1;
                    }
                }
                bfunction(netlist.get(j));
                if (netlist.get(j).output.wtype.equals(DWireType.output)) {
                    if (netlist.get(j).output.wValue.equals(DWireValue._0)) {
                        ttRow += "0 ";
                    }
                    if (netlist.get(j).output.wValue.equals(DWireValue._1)) {
                        ttRow += "1 ";
                    }
                    if (netlist.get(j).output.wValue.equals(DWireValue._x)) {
                        ttRow += "- ";
                    }
                }
            }
            System.out.println(ttRow);

        }
        System.out.println("------------------------------------");

    }

    public static void printTruthTable(List<DGate> netlist, List<String> inpnames) {
        List<String> inputnames = new ArrayList<String>();
        List<String> outputnames = new ArrayList<String>();
        HashMap<String, Integer> inpindex = new HashMap<String, Integer>();
        //HashMap<String,Integer> outpindex = new HashMap<String,Integer>();

        int inpindx = 0;

        for (int i = 0; i < inpnames.size(); i++) {
            if (!inputnames.contains(inpnames.get(i).trim())) {
                inputnames.add(inpnames.get(i).trim());
                inpindex.put(inpnames.get(i).trim(), inpindx);
                inpindx++;
            }
        }

        for (int i = 0; i < netlist.size(); i++) {
            /*for(int j=0;j<netlist.get(i).input.size();j++)
             {
             if(netlist.get(i).input.get(j).wtype.equals(DWireType.input))
             {
             if(!inputnames.contains(netlist.get(i).input.get(j).name.trim()))
             {
             inputnames.add(netlist.get(i).input.get(j).name.trim());
             inpindex.put(netlist.get(i).input.get(j).name.trim(), inpindx);
             inpindx++;
             }
             }
             }*/
            if (netlist.get(i).output.wtype.equals(DWireType.output)) {
                if (!outputnames.contains(netlist.get(i).output.name.trim())) {
                    outputnames.add(netlist.get(i).output.name.trim());
                }
            }
        }
        System.out.println("\n------------------------------------");
        System.out.println("---------Truth Table----------------");
        String inputstring = "Input Order  : ";
        String outputstring = "Output Order : ";
        for (int i = 0; i < inputnames.size(); i++) {
            inputstring += inputnames.get(i) + " ";
        }
        for (int i = 0; i < outputnames.size(); i++) {
            outputstring += outputnames.get(i) + " ";
        }
        System.out.println(inputstring);
        System.out.println(outputstring);
        System.out.println("------------------------------------");
        int ttrows = (int) Math.pow(2, inputnames.size());
        for (int i = 0; i < ttrows; i++) {

            String ttRow = "| ";
            String rrinputrowboolean = Convert.dectoBin(i, inputnames.size());
            for (int j = 0; j < rrinputrowboolean.length(); j++) {
                ttRow += (rrinputrowboolean.charAt(j) + " ");
            }
            ttRow += "| ";
            for (int j = 0; j < netlist.size(); j++) {
                for (int k = 0; k < netlist.get(j).input.size(); k++) {
                    if (netlist.get(j).input.get(k).wtype.equals(DWireType.input)) {
                        int tempindx = inpindex.get(netlist.get(j).input.get(k).name.trim());
                        if (rrinputrowboolean.charAt(tempindx) == '1') {
                            netlist.get(j).input.get(k).wValue = DWireValue._1;
                        } else if (rrinputrowboolean.charAt(tempindx) == '0') {
                            netlist.get(j).input.get(k).wValue = DWireValue._0;
                        }
                    } else if (netlist.get(j).input.get(k).wtype.equals(DWireType.GND)) {
                        netlist.get(j).input.get(k).wValue = DWireValue._0;
                    } else if (netlist.get(j).input.get(k).wtype.equals(DWireType.Source)) {
                        netlist.get(j).input.get(k).wValue = DWireValue._1;
                    }
                }
                bfunction(netlist.get(j));
                if (netlist.get(j).output.wtype.equals(DWireType.output)) {
                    if (netlist.get(j).output.wValue.equals(DWireValue._0)) {
                        ttRow += "0 ";
                    }
                    if (netlist.get(j).output.wValue.equals(DWireValue._1)) {
                        ttRow += "1 ";
                    }
                    if (netlist.get(j).output.wValue.equals(DWireValue._x)) {
                        ttRow += "- ";
                    }
                }
            }
            System.out.println(ttRow);

        }
        System.out.println("------------------------------------");

    }

    public static List<String> getTruthTable(List<DGate> netlist) {
        List<String> inputnames = new ArrayList<String>();
        List<String> outputnames = new ArrayList<String>();
        HashMap<String, Integer> inpindex = new HashMap<String, Integer>();
        HashMap<String, Integer> outpindex = new HashMap<String, Integer>();
        List<String> outTruthTables = new ArrayList<String>();
        int inpindx = 0;
        int outindx = 0;
        for (int i = 0; i < netlist.size(); i++) {
            for (int j = 0; j < netlist.get(i).input.size(); j++) {
                if (netlist.get(i).input.get(j).wtype.equals(DWireType.input)) {
                    if (!inputnames.contains(netlist.get(i).input.get(j).name.trim())) {
                        inputnames.add(netlist.get(i).input.get(j).name.trim());
                        inpindex.put(netlist.get(i).input.get(j).name.trim(), inpindx);
                        inpindx++;
                    }
                }
            }
            if (netlist.get(i).output.wtype.equals(DWireType.output)) {
                if (!outputnames.contains(netlist.get(i).output.name.trim())) {
                    outputnames.add(netlist.get(i).output.name.trim());
                    outpindex.put(netlist.get(i).output.name.trim(), outindx);
                    outindx++;
                    String outTT = "";
                    outTruthTables.add(outTT);
                }
            }
        }

        int ttrows = (int) Math.pow(2, inputnames.size());
        for (int i = 0; i < ttrows; i++) {

            String rrinputrowboolean = Convert.dectoBin(i, inputnames.size());
            for (int j = 0; j < netlist.size(); j++) {
                for (int k = 0; k < netlist.get(j).input.size(); k++) {
                    if (netlist.get(j).input.get(k).wtype.equals(DWireType.input)) {
                        int tempindx = inpindex.get(netlist.get(j).input.get(k).name.trim());
                        if (rrinputrowboolean.charAt(tempindx) == '1') {
                            netlist.get(j).input.get(k).wValue = DWireValue._1;
                        } else if (rrinputrowboolean.charAt(tempindx) == '0') {
                            netlist.get(j).input.get(k).wValue = DWireValue._0;
                        }
                    } else if (netlist.get(j).input.get(k).wtype.equals(DWireType.GND)) {
                        netlist.get(j).input.get(k).wValue = DWireValue._0;
                    } else if (netlist.get(j).input.get(k).wtype.equals(DWireType.Source)) {
                        netlist.get(j).input.get(k).wValue = DWireValue._1;
                    }
                }
                bfunction(netlist.get(j));
                if (netlist.get(j).output.wtype.equals(DWireType.output)) {
                    int tempoutindx = outpindex.get(netlist.get(j).output.name.trim());
                    if (netlist.get(j).output.wValue.equals(DWireValue._0)) {
                        String ttRow = outTruthTables.get(tempoutindx);
                        ttRow += "0";
                        outTruthTables.set(tempoutindx, ttRow);
                    }
                    if (netlist.get(j).output.wValue.equals(DWireValue._1)) {
                        String ttRow = outTruthTables.get(tempoutindx);
                        ttRow += "1";
                        outTruthTables.set(tempoutindx, ttRow);
                    }
                    if (netlist.get(j).output.wValue.equals(DWireValue._x)) {
                        String ttRow = outTruthTables.get(tempoutindx);
                        ttRow += "-";
                        outTruthTables.set(tempoutindx, ttRow);
                    }
                }
            }
        }
        return outTruthTables;
    }

    public static List<String> getTruthTable(List<DGate> netlist, List<String> inpnames) {
        List<String> inputnames = new ArrayList<String>();
        List<String> outputnames = new ArrayList<String>();
        HashMap<String, Integer> inpindex = new HashMap<String, Integer>();
        HashMap<String, Integer> outpindex = new HashMap<String, Integer>();
        List<String> outTruthTables = new ArrayList<String>();
        int inpindx = 0;
        int outindx = 0;

        for (int i = 0; i < inpnames.size(); i++) {
            if (!inputnames.contains(inpnames.get(i).trim())) {
                inputnames.add(inpnames.get(i).trim());
                inpindex.put(inpnames.get(i).trim(), inpindx);
                inpindx++;
            }
        }

        for (int i = 0; i < netlist.size(); i++) {
            if (netlist.get(i).output.wtype.equals(DWireType.output)) {
                if (!outputnames.contains(netlist.get(i).output.name.trim())) {
                    outputnames.add(netlist.get(i).output.name.trim());
                    outpindex.put(netlist.get(i).output.name.trim(), outindx);
                    outindx++;
                    String outTT = "";
                    outTruthTables.add(outTT);
                }
            }
        }

        int ttrows = (int) Math.pow(2, inputnames.size());
        for (int i = 0; i < ttrows; i++) {
            String rrinputrowboolean = Convert.dectoBin(i, inputnames.size());
            //System.out.println(rrinputrowboolean);
            for (int j = 0; j < netlist.size(); j++) {
                for (int k = 0; k < netlist.get(j).input.size(); k++) {
                    if (netlist.get(j).input.get(k).wtype.equals(DWireType.input)) {
                        int tempindx = inpindex.get(netlist.get(j).input.get(k).name.trim());
                        if (rrinputrowboolean.charAt(tempindx) == '1') {
                            netlist.get(j).input.get(k).wValue = DWireValue._1;
                        } else if (rrinputrowboolean.charAt(tempindx) == '0') {
                            netlist.get(j).input.get(k).wValue = DWireValue._0;
                        }
                    } else if (netlist.get(j).input.get(k).wtype.equals(DWireType.GND)) {
                        netlist.get(j).input.get(k).wValue = DWireValue._0;
                    } else if (netlist.get(j).input.get(k).wtype.equals(DWireType.Source)) {
                        netlist.get(j).input.get(k).wValue = DWireValue._1;
                    }
                }
                bfunction(netlist.get(j));
                if (netlist.get(j).output.wtype.equals(DWireType.output)) {
                    int tempoutindx = outpindex.get(netlist.get(j).output.name.trim());
                    if (netlist.get(j).output.wValue.equals(DWireValue._0)) {
                        String ttRow = outTruthTables.get(tempoutindx);
                        ttRow += "0";
                        outTruthTables.set(tempoutindx, ttRow);
                    }
                    if (netlist.get(j).output.wValue.equals(DWireValue._1)) {
                        String ttRow = outTruthTables.get(tempoutindx);
                        ttRow += "1";
                        outTruthTables.set(tempoutindx, ttRow);
                    }
                    if (netlist.get(j).output.wValue.equals(DWireValue._x)) {
                        String ttRow = outTruthTables.get(tempoutindx);
                        ttRow += "-";
                        outTruthTables.set(tempoutindx, ttRow);
                    }
                }
            }
        }
        return outTruthTables;
    }

    public static List<String> invertTruthTable(List<String> ttVals) {
        List<String> invttVals = new ArrayList<String>();

        for (int i = 0; i < ttVals.size(); i++) {
            String invtt = "";
            for (int j = 0; j < ttVals.get(i).length(); j++) {
                if (ttVals.get(i).charAt(j) == '1') {
                    invtt += "0";
                } else if (ttVals.get(i).charAt(j) == '0') {
                    invtt += "1";
                } else {
                    invtt += "-";
                }
            }
            invttVals.add(invtt);
        }
        return invttVals; 
   }
}
