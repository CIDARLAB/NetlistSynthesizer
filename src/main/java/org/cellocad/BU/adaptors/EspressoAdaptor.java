/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cellocad.BU.adaptors;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.cellocad.BU.dom.DGate;
import org.cellocad.BU.dom.DGateType;
import org.cellocad.BU.dom.DWire;
import org.cellocad.BU.dom.DWireType;
import org.cellocad.BU.netsynth.Global;
import org.cellocad.BU.netsynth.NetSynth;
import org.cellocad.BU.netsynth.Utilities;
import org.cellocad.BU.parseVerilog.CircuitDetails;
import org.cellocad.BU.parseVerilog.Convert;

/**
 *
 * @author prash
 */
public class EspressoAdaptor {

    public static List<String> createFile(CircuitDetails circ) {
        List<String> esfile = new ArrayList<String>();
        int pw = (int) Math.pow(2, circ.inputNames.size());
        List<String> truthfunc = new ArrayList<String>();
        for (String xtt : circ.truthTable) {
            truthfunc.add(xtt);
        }
        String line = "";
        line += (".i " + circ.inputNames.size());
        esfile.add(line);
        line = "";
        line += (".o " + circ.outputNames.size());
        esfile.add(line);
        line = "";
        line += ".ilb";
        for (String xinp : circ.inputNames) {
            line += " " + xinp;
        }
        esfile.add(line);
        line = "";
        line += ".ob";
        for (String xout : circ.outputNames) {
            line += " " + xout;
        }
        esfile.add(line);
        for (int i = 0; i < pw; i++) {
            line = "";
            String tempinp = Convert.dectoBin(i, circ.inputNames.size());
            line += tempinp;
            line += " ";
            for (int j = 0; j < circ.outputNames.size(); j++) {
                line += truthfunc.get(j).charAt(i);
            }
            esfile.add(line);
        }
        line = "";
        line += ".e";
        esfile.add(line);
        return esfile;
    }

    /**
     * Function ************************************************************
     * <br>
     * Synopsis []
     * <br>
     * Description []
     * <br>
     * SideEffects []
     * <br>
     * SeeAlso []
     *
     * @param pathFile
     * @param resourcesFilepath
     * @return
     * *********************************************************************
     */
    public static List<String> runEspresso(String pathFile, String resourcesFilepath, String resultsFilePath) {
       
        //String filepath = Utilities.getFilepath();
        List<String> espressoOutput = new ArrayList<String>();
        String x = System.getProperty("os.name");
        StringBuilder commandBuilder = null;
        if (Utilities.isMac(x)) {
            commandBuilder = new StringBuilder(resourcesFilepath + "espresso.mac -epos " + pathFile);
        } else if (Utilities.isLinux(x)) {
            commandBuilder = new StringBuilder(resourcesFilepath + "espresso.linux -epos " + pathFile);
        } else if (Utilities.isWindows(x)) {
            commandBuilder = new StringBuilder(resourcesFilepath + "espresso.exe -epos " + pathFile);
        }
        String command = commandBuilder.toString();
        Runtime runtime = Runtime.getRuntime();
        Process proc = null;
        try {
            proc = runtime.exec(command);
        } catch (IOException ex) {
            Logger.getLogger(NetSynth.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            //String filestring = "";
            //int writeoutputcount =0;
            //filestring += (resultsFilePath + "write");
            //do{
            //    filestring = (resultsFilePath + "write" + (writeoutputcount++) + ".txt");
            //}while(Utilities.validFilepath(filestring));
            //filestring += NetSynth.espout++; //CHANGE
            //File fbool = new File(filestring);
            //Writer output = new BufferedWriter(new FileWriter(fbool));
            InputStream in = proc.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line = null;
            while ((line = br.readLine()) != null) {
                espressoOutput.add(line);
                //line += "\n";
                //output.write(line);
            }
            //output.close();
            //fbool.delete();
        } catch (IOException ex) {
            Logger.getLogger(NetSynth.class.getName()).log(Level.SEVERE, null, ex);
        }
        return espressoOutput;
    }

//    /**
//     * Function ************************************************************
//     * <br>
//     * Synopsis []
//     * <br>
//     * Description []
//     * <br>
//     * SideEffects []
//     * <br>
//     * SeeAlso []
//     *
//     * @param espinp
//     * @return
//     * *********************************************************************
//     */
//    public static List<DGate> parseEspressoOutput(List<String> espinp) {
//        Global.one = new DWire("_one", DWireType.Source);
//        Global.zero = new DWire("_zero", DWireType.GND);
//        List<DGate> sopexp = new ArrayList<DGate>();
//        List<DWire> wireInputs = new ArrayList<DWire>();
//        List<DWire> wireOutputs = new ArrayList<DWire>();
//        List<DWire> invWires = new ArrayList<DWire>();
//        List<DGate> inpInv = new ArrayList<DGate>();
//        NetSynth.functionOutp = false;
//        String inpNames = "";
//        String outNames = "";
//        boolean POSmode = false;
//        int numberOfMinterms = 0;
//        int expInd = 0;
//        for (int i = 0; i < espinp.size(); i++) {
//            if (espinp.get(i).startsWith(".ilb")) {
//                inpNames = (espinp.get(i).substring(5));
//            } else if (espinp.get(i).startsWith(".ob")) {
//                outNames = (espinp.get(i).substring(4));
//            } else if (espinp.get(i).startsWith("#.phase")) {
//                POSmode = true;
//            } else if (espinp.get(i).startsWith(".p")) {
//                numberOfMinterms = Integer.parseInt(espinp.get(i).substring(3));
//                expInd = i + 1;
//                break;
//            }
//        }
//        for (String splitInp : inpNames.split(" ")) {
//            if (splitInp.equals(Global.one.name) || splitInp.equals(Global.zero.name)) {
//                splitInp += "I";
//            }
//            wireInputs.add(new DWire(splitInp, DWireType.input));
//        }
//        inpInv = NetSynth.notGates(wireInputs);
//        for (DGate gnots : inpInv) {
//            invWires.add(gnots.output);
//        }
//        for (String splitInp : outNames.split(" ")) {
//            if (splitInp.equals(Global.one.name) || splitInp.equals(Global.zero.name)) {
//                splitInp += "O";
//            }
//            wireOutputs.add(new DWire(splitInp, DWireType.output));
//        }
//        if (numberOfMinterms == 0) {
//            NetSynth.functionOutp = true;
//            List<DWire> inp01 = new ArrayList<DWire>();
//            if (POSmode) {
//                inp01.add(Global.one);
//            } else {
//                inp01.add(Global.zero);
//            }
//            sopexp.add(new DGate(DGateType.BUF, inp01, wireOutputs.get(0)));
//            return sopexp;
//        } else if (numberOfMinterms == 1) {
//            String oneMinT = espinp.get(expInd).substring(0, wireInputs.size());
//            int flag = 0;
//            for (int j = 0; j < wireInputs.size(); j++) {
//                if (oneMinT.charAt(j) != '-') {
//                    flag = 1;
//                    break;
//                }
//            }
//            if (flag == 0) {
//                NetSynth.functionOutp = true;
//                List<DWire> inp01 = new ArrayList<DWire>();
//                if (POSmode) {
//                    inp01.add(Global.zero);
//                } else {
//                    inp01.add(Global.one);
//                }
//                sopexp.add(new DGate(DGateType.BUF, inp01, wireOutputs.get(0)));
//                return sopexp;
//            }
//        }
//        List<DWire> minTemp = new ArrayList<DWire>();
//        List<DWire> orWires = new ArrayList<DWire>();
//        List<DGate> prodGates;
//        for (int i = expInd; i < (expInd + numberOfMinterms); i++) {
//            String minT = espinp.get(i).substring(0, wireInputs.size());
//            prodGates = new ArrayList<DGate>();
//            minTemp = new ArrayList<DWire>();
//            for (int j = 0; j < wireInputs.size(); j++) {
//                if (minT.charAt(j) == '-') {
//                    continue;
//                } else if (minT.charAt(j) == '0') {
//                    if (POSmode) {
//                        minTemp.add(wireInputs.get(j));
//                    } else {
//                        if (!sopexp.contains(inpInv.get(j))) {
//                            sopexp.add(inpInv.get(j));
//                        }
//                        minTemp.add(inpInv.get(j).output);
//                    }
//                } else if (minT.charAt(j) == '1') {
//                    if (POSmode) {
//                        if (!sopexp.contains(inpInv.get(j))) {
//                            sopexp.add(inpInv.get(j));
//                        }
//                        minTemp.add(inpInv.get(j).output);
//                    } else {
//                        minTemp.add(wireInputs.get(j));
//                    }
//                }
//            }
//            if (minTemp.size() == 1) {
//                orWires.add(minTemp.get(0));
//            } else {
//                if (POSmode) {
//                    prodGates = NetSynth.AndORGates(minTemp, DGateType.OR);
//                } else {
//                    prodGates = NetSynth.AndORGates(minTemp, DGateType.AND);
//                }
//                orWires.add(prodGates.get(prodGates.size() - 1).output);
//                sopexp.addAll(prodGates);
//            }
//        }
//        prodGates = new ArrayList<DGate>();
//        if (POSmode) {
//            prodGates = NetSynth.AndORGates(orWires, DGateType.AND);
//        } else {
//            prodGates = NetSynth.AndORGates(orWires, DGateType.OR);
//        }
//        sopexp.addAll(prodGates);
//        if (sopexp.isEmpty()) {
//            DGate bufgate = new DGate(DGateType.BUF, orWires, wireOutputs.get(0));
//            sopexp.add(bufgate);
//        } else {
//            sopexp.get(sopexp.size() - 1).output = wireOutputs.get(0);
//        }
//        return sopexp;
//    }

    /**
     * Function ************************************************************
     * <br>
     * Synopsis []
     * <br>
     * Description []
     * <br>
     * SideEffects []
     * <br>
     * SeeAlso []
     *
     * @param espinp
     * @return
     * *********************************************************************
     */
    public static List<String> convertEspressoOutputToVerilog(List<String> espinp) {
        List<String> verilogfile = new ArrayList<String>();
        Global.one = new DWire("_one", DWireType.Source);
        Global.zero = new DWire("_zero", DWireType.GND);
        String inpNames = null;
        String outNames = null;
        int numberOfMinterms = 0;
        int expInd = 0;
        boolean POSmode = false;
        List<String> inputWires = new ArrayList<String>();
        List<String> outputWires = new ArrayList<String>();
        List<DWire> inputINVWires = new ArrayList<DWire>();
        List<DGate> inputINVGates = new ArrayList<DGate>();
        List<Boolean> notGateexists = new ArrayList<Boolean>();
        List<Boolean> notGateAdd = new ArrayList<Boolean>();
        for (int i = 0; i < espinp.size(); i++) {
            if (espinp.get(i).startsWith(".ilb")) {
                inpNames = (espinp.get(i).substring(5));
            } else if (espinp.get(i).startsWith(".ob")) {
                outNames = (espinp.get(i).substring(4));
            } else if (espinp.get(i).startsWith("#.phase")) {
                POSmode = true;
            } else if (espinp.get(i).startsWith(".p")) {
                numberOfMinterms = Integer.parseInt(espinp.get(i).substring(3));
                expInd = i + 1;
                break;
            }
        }
        for (String splitInp : inpNames.split(" ")) {
            if (splitInp.equals(Global.one.name) || splitInp.equals(Global.zero.name)) {
                splitInp += "I";
            }
            inputWires.add(splitInp.trim());
        }
        for (String splitInp : outNames.split(" ")) {
            if (splitInp.equals(Global.one.name) || splitInp.equals(Global.zero.name)) {
                splitInp += "O";
            }
            outputWires.add(splitInp.trim());
        }
        verilogfile.add("module EspToVerilog (");
        String inputlist = "";
        for (int i = 0; i < inputWires.size(); i++) {
            inputlist += inputWires.get(i);
            if (i != (inputWires.size() - 1)) {
                inputlist += ", ";
            }
        }
        String moduleinput = inputlist + ",";
        verilogfile.add(moduleinput);
        String outputlist = "";
        for (int i = 0; i < outputWires.size(); i++) {
            outputlist += outputWires.get(i);
            if (i != (outputWires.size() - 1)) {
                outputlist += ", ";
            }
        }
        String moduleoutput = outputlist + " );";
        verilogfile.add(moduleoutput);
        String inputdeclare = "input " + inputlist + ";";
        String outputdeclare = "output " + outputlist + ";";
        verilogfile.add(inputdeclare);
        verilogfile.add(outputdeclare);
        String wiredeclaration = "wire";
        List<String> wirenames = new ArrayList<String>();
        for (int i = 0; i < numberOfMinterms; i++) {
            String wName = "w" + i;
            wiredeclaration += (" " + wName);
            wirenames.add(wName);
            if (i == numberOfMinterms - 1) {
                wiredeclaration += ";";
            } else {
                wiredeclaration += ",";
            }
        }
        verilogfile.add(wiredeclaration);
        List<List<DWire>> outputsum = new ArrayList<List<DWire>>();
        List<List<String>> outputassignlines = new ArrayList<List<String>>();
        for (int i = 0; i < outputWires.size(); i++) {
            List<String> outassign = new ArrayList<String>();
            outputassignlines.add(outassign);
            List<DWire> outsums = new ArrayList<DWire>();
            outputsum.add(outsums);
        }
        int wIndx = 0;
        for (int i = expInd; i < espinp.size() - 1; i++) {
            List<DWire> sumterm = new ArrayList<DWire>();
            String assgnwires = "assign " + wirenames.get(wIndx) + " =";
            String[] maxterm = espinp.get(i).split(" ");
            boolean startassign = false;
            for (int j = 0; j < inputWires.size(); j++) {
                if (maxterm[0].charAt(j) == '1') {
                    if (startassign) {
                        assgnwires += " |";
                    }
                    startassign = true;
                    assgnwires += " ~" + inputWires.get(j);
                } else if (maxterm[0].charAt(j) == '0') {
                    if (startassign) {
                        assgnwires += " |";
                    }
                    startassign = true;
                    assgnwires += " " + inputWires.get(j);
                }
                if (j == (inputWires.size() - 1)) {
                    assgnwires += ";";
                }
            }
            verilogfile.add(assgnwires);
            for (int j = 0; j < outputWires.size(); j++) {
                if (maxterm[1].charAt(j) == '1') {
                    outputassignlines.get(j).add(wirenames.get(wIndx));
                }
            }
            wIndx++;
        }
        for (int i = 0; i < outputassignlines.size(); i++) {
            String tempoutassign = "assign " + outputWires.get(i) + " = ";
            for (int j = 0; j < outputassignlines.get(i).size(); j++) {
                tempoutassign += (" " + outputassignlines.get(i).get(j));
                if (j == (outputassignlines.get(i).size() - 1)) {
                    tempoutassign += ";";
                } else {
                    tempoutassign += " &";
                }
            }
            verilogfile.add(tempoutassign);
        }
        verilogfile.add("endmodule");
        return verilogfile;
    }

    /**
     * Function ************************************************************
     * <br>
     * Synopsis []
     * <br>
     * Description []
     * <br>
     * SideEffects []
     * <br>
     * SeeAlso []
     *
     * @param espinp
     * @param wirecount
     * @return
     * *********************************************************************
     */
    public static List<DGate> convertPOStoNORNOT(List<String> espinp, AtomicInteger wirecount) {
        List<DGate> netlist = new ArrayList<DGate>();
        Global.one = new DWire("_one", DWireType.Source);
        Global.zero = new DWire("_zero", DWireType.GND);
        boolean POSmode = false;
        String inpNames = null;
        String outNames = null;
        int numberOfMinterms;
        int expInd = 0;
        List<DWire> inputWires = new ArrayList<DWire>();
        List<DWire> outputWires = new ArrayList<DWire>();
        List<DWire> inputINVWires = new ArrayList<DWire>();
        List<DGate> inputINVGates = new ArrayList<DGate>();
        List<Boolean> notGateexists = new ArrayList<Boolean>();
        List<Boolean> notGateAdd = new ArrayList<Boolean>();
        for (int i = 0; i < espinp.size(); i++) {
            if (espinp.get(i).startsWith(".ilb")) {
                inpNames = (espinp.get(i).substring(5));
            } else if (espinp.get(i).startsWith(".ob")) {
                outNames = (espinp.get(i).substring(4));
            } else if (espinp.get(i).startsWith("#.phase")) {
                POSmode = true;
            } else if (espinp.get(i).startsWith(".p")) {
                numberOfMinterms = Integer.parseInt(espinp.get(i).substring(3));
                expInd = i + 1;
                break;
            }
        }
        for (String splitInp : inpNames.split(" ")) {
            if (splitInp.equals(Global.one.name) || splitInp.equals(Global.zero.name)) {
                splitInp += "I";
            }
            inputWires.add(new DWire(splitInp, DWireType.input));
        }
        inputINVGates = notGates(inputWires, wirecount);
        for (DGate gnots : inputINVGates) {
            notGateexists.add(false);
            notGateAdd.add(false);
            inputINVWires.add(gnots.output);
        }
        for (String splitInp : outNames.split(" ")) {
            if (splitInp.equals(Global.one.name) || splitInp.equals(Global.zero.name)) {
                splitInp += "O";
            }
            outputWires.add(new DWire(splitInp, DWireType.output));
        }
        List<List<DWire>> outputsum = new ArrayList<List<DWire>>();
        for (int i = 0; i < outputWires.size(); i++) {
            List<DWire> outsums = new ArrayList<DWire>();
            outputsum.add(outsums);
        }
        for (int i = expInd; i < espinp.size() - 1; i++) {
            List<DWire> sumterm = new ArrayList<DWire>();
            String[] maxterm = espinp.get(i).split(" ");
            for (int j = 0; j < inputWires.size(); j++) {
                if (maxterm[0].charAt(j) == '1') {
                    if (notGateexists.get(j) == false) {
                        netlist.add(inputINVGates.get(j));
                        notGateexists.set(j, true);
                    }
                    sumterm.add(inputINVWires.get(j));
                } else if (maxterm[0].charAt(j) == '0') {
                    sumterm.add(inputWires.get(j));
                }
            }
            List<DGate> sumtermG = new ArrayList<DGate>();
            DWire sumlast = new DWire();
            if (sumterm.size() > 0) {
                if (sumterm.size() == 1) {
                    List<DWire> notsumterminp = new ArrayList<DWire>();
                    notsumterminp.addAll(sumterm);
                    String Wirename = "0Wire" + wirecount.getAndIncrement();
                    DWire notsumtermout = new DWire(Wirename);
                    DGate notsumtermgate = new DGate(DGateType.NOT, notsumterminp, notsumtermout);
                    netlist.add(notsumtermgate);
                    sumlast = notsumtermout;
                } else {
                    sumtermG = NORNANDGates(sumterm, DGateType.NOR, wirecount);
                    netlist.addAll(sumtermG);
                    sumlast = sumtermG.get(sumtermG.size() - 1).output;
                }
            } else {
                sumlast = new DWire(Global.zero);
            }
            for (int j = 0; j < outputWires.size(); j++) {
                if (maxterm[1].charAt(j) == '1') {
                    outputsum.get(j).add(sumlast);
                }
            }
        }
        for (int j = 0; j < outputWires.size(); j++) {
            if (outputsum.get(j).isEmpty()) {
                List<DWire> inputbuflist = new ArrayList<DWire>();
                inputbuflist.add(Global.one);
                DGate bufgate = new DGate(DGateType.BUF, inputbuflist, outputWires.get(j));
                netlist.add(bufgate);
            } else if (outputsum.get(j).size() == 1) {
                if (outputsum.get(j).get(0).wtype == DWireType.GND) {
                    List<DWire> inputbuflist = new ArrayList<DWire>();
                    inputbuflist.add(Global.zero);
                    DGate bufgate = new DGate(DGateType.BUF, inputbuflist, outputWires.get(j));
                    netlist.add(bufgate);
                } else {
                    List<DWire> singleNOTmaxterm = new ArrayList<DWire>();
                    singleNOTmaxterm.add(outputsum.get(j).get(0));
                    DGate singlemaxtermgate = new DGate(DGateType.NOT, singleNOTmaxterm, outputWires.get(j));
                    netlist.add(singlemaxtermgate);
                }
            } else {
                List<DGate> productGates = new ArrayList<DGate>();
                productGates = NORNANDGates(outputsum.get(j), DGateType.NOR, wirecount);
                productGates.get(productGates.size() - 1).output = outputWires.get(j);
                netlist.addAll(productGates);
            }
        }
        return netlist;
    }
    
    /**
     * Function ************************************************************
     * <br>
     * Synopsis []
     * <br>
     * Description []
     * <br>
     * SideEffects []
     * <br>
     * SeeAlso []
     *
     * @param inpWires
     * @param gtype
     * @return
     * *********************************************************************
     */
    private static List<DGate> NORNANDGates(List<DWire> inpWires, DGateType gtype, AtomicInteger wirecount) {
        if (inpWires.isEmpty()) {
            return null;
        }
        List<DGate> minterm = new ArrayList<DGate>();

        List<DWire> nextLevelWires = new ArrayList<DWire>();
        List<DWire> temp = new ArrayList<DWire>();

        int numberofWires, indx;
        nextLevelWires.addAll(inpWires);
        numberofWires = inpWires.size();

        while (numberofWires > 1) {
            temp = new ArrayList<DWire>();
            temp.addAll(nextLevelWires);
            nextLevelWires = new ArrayList<DWire>();
            indx = 0;
            while ((indx + 2) <= (numberofWires)) {

                List<DWire> ainp = new ArrayList<DWire>();
                ainp.add(temp.get(indx));
                ainp.add(temp.get(indx + 1));
                String Wirename = "0Wire" + wirecount.getAndIncrement();

                DWire aout = new DWire(Wirename);
                DGate andG = new DGate(gtype, ainp, aout);
                minterm.add(andG);
                if (numberofWires > 2) {
                    Wirename = "0Wire" + wirecount.getAndIncrement();
                    List<DWire> ninp = new ArrayList<DWire>();
                    ninp.add(aout);
                    DWire anout = new DWire(Wirename);
                    DGate notG = new DGate(DGateType.NOT, ninp, anout);
                    minterm.add(notG);
                    nextLevelWires.add(anout);
                } else {
                    nextLevelWires.add(aout);
                }
                indx += 2;

            }
            if (temp.size() % 2 != 0) {
                nextLevelWires.add(temp.get(numberofWires - 1));
            }
            numberofWires = nextLevelWires.size();
            if (numberofWires == 2) {
                List<DWire> ainp = new ArrayList<DWire>();
                ainp.add(nextLevelWires.get(0));
                ainp.add(nextLevelWires.get(1));
                String Wirename = "0Wire" + wirecount.getAndIncrement();
                DWire aout = new DWire(Wirename);
                DGate andG = new DGate(gtype, ainp, aout);
                minterm.add(andG);
                break;
            }
        }
        return minterm;
    }
    
    
    /**
     * Function ************************************************************
     * <br>
     * Synopsis []
     * <br>
     * Description []
     * <br>
     * SideEffects []
     * <br>
     * SeeAlso []
     *
     * @param inpWires
     * @return
     * *********************************************************************
     */
    private static List<DGate> notGates(List<DWire> inpWires, AtomicInteger wirecount) {
        List<DGate> notInp = new ArrayList<DGate>();
        for (DWire xWire : inpWires) {
            String Wirename = "0Wire" + wirecount.getAndIncrement();
            DWire aout = new DWire(Wirename);
            List<DWire> inpNot = new ArrayList<DWire>();
            inpNot.add(xWire);
            notInp.add(new DGate(DGateType.NOT, inpNot, aout));
        }
        return notInp;
    }
    
}
