/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cellocad.BU.netsynth;


import org.cellocad.BU.dom.DWire;
import org.cellocad.BU.dom.DWireType;
import org.cellocad.BU.dom.DGate;
import org.cellocad.BU.dom.DGateType;
import org.cellocad.BU.dom.DAGW;
import org.cellocad.BU.adaptors.BlifAdaptor;
import org.cellocad.BU.parseVerilog.CircuitDetails;
import org.cellocad.BU.parseVerilog.Convert;
import org.cellocad.BU.parseVerilog.parseVerilogFile;
import org.cellocad.BU.simulators.BooleanSimulator;
import org.cellocad.BU.dom.DWire.DWireValue;
import org.cellocad.BU.precomputation.PreCompute;
import org.cellocad.BU.precomputation.genVerilogFile;
import org.cellocad.MIT.dnacompiler.Gate;
import org.cellocad.MIT.dnacompiler.Gate.GateType;
import org.cellocad.MIT.dnacompiler.Wire;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.cellocad.BU.adaptors.ABCAdaptor;
import org.cellocad.BU.adaptors.EspressoAdaptor;
import org.cellocad.BU.subcircuit.SubcircuitLibrary;
import org.cellocad.BU.subcircuit.subCircuitSwap;
import org.json.JSONArray;

/**
 *
 * @author prashantvaidyanathan
 */
public class NetSynth {

    public static DWire one;
    public static boolean POSmode;
    public static DWire zero;
    public static boolean functionOutp;
    public static String Filepath;
    
    /**
     *
     */
    public static Map<Integer,Map<Integer,List<SubcircuitLibrary>>> sublibrary;
    //sublibrary.get(i) gives a Map of all Subcircuits with i inputs
    //sublibrary.get(i).get(4) Gives a List of all subcircuits with i inputs, and truthtable == 4. 
    
    //public static List<List<Map<List<Integer>,List<CircuitLibrary>>>> library;//?? Works?? 
    //This would be something like. Rows = # of Inputs. Columns = # of Outputs
    //So library.get(i).get(o) would give the library of all subnetlists with i+1 inputs and o+1 outputs
    //Integer = Decimal value of the truthtable. 
    public static CircuitDetails caseCirc;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Global.wirecount = 0;
        Global.espinp = 0;
        Global.espout = 0;
        POSmode = false;
        functionOutp = false;
        one = new DWire("_one", DWireType.Source);
        zero = new DWire("_zero", DWireType.GND);
        Filepath = Utilities.getFilepath();

    }
    
    //<editor-fold desc="Initialize Subcircuit Library">
    public static void initializeSubLibrary(){
        sublibrary = new HashMap<Integer,Map<Integer,List<SubcircuitLibrary>>>();
        Map<Integer,List<SubcircuitLibrary>> init1 = new HashMap<Integer,List<SubcircuitLibrary>>();
        for(int i=0;i<4;i++){
            init1.put(i, new ArrayList<SubcircuitLibrary>());
        }
        sublibrary.put(1, init1);
        Map<Integer,List<SubcircuitLibrary>> init2 = new HashMap<Integer,List<SubcircuitLibrary>>();
        for(int i=0;i<16;i++){
            init2.put(i, new ArrayList<SubcircuitLibrary>());
        }
        sublibrary.put(2, init2);
        Map<Integer,List<SubcircuitLibrary>> init3 = new HashMap<Integer,List<SubcircuitLibrary>>();
        for(int i=0;i<256;i++){
            init3.put(i, new ArrayList<SubcircuitLibrary>());
        }
        sublibrary.put(3, init3);
        
        String filepathNet3in1out = Utilities.getResourcesFilepath() + "/netlist_in3out1.json";
        String filepathNet3in1out_or = Utilities.getResourcesFilepath() + "/netlist_in3out1_OR.json";
        
        List<SubcircuitLibrary> net3in1out = new ArrayList<SubcircuitLibrary>();
        net3in1out = PreCompute.getCircuitLibrary(filepathNet3in1out);
        List<SubcircuitLibrary> net3in1outOr = new ArrayList<SubcircuitLibrary>();
        net3in1outOr = PreCompute.getCircuitLibrary(filepathNet3in1out_or);
        
        for(int i=0;i<net3in1out.size();i++)
        {
            SubcircuitLibrary subcirc = new SubcircuitLibrary();
            subcirc = net3in1out.get(i);
            subcirc.setInputs();
            subcirc.setTT();
            sublibrary.get(subcirc.getInputCount()).get(subcirc.getTTDecimalVal()).add(subcirc);
        }
        for(int i=0;i<net3in1outOr.size();i++)
        {
            SubcircuitLibrary subcirc = new SubcircuitLibrary();
            subcirc = net3in1outOr.get(i);
            subcirc.setInputs();
            subcirc.setTT();
            if(containsOUTPUT_OR(subcirc.netlist)){
                subcirc.switches.add(NetSynthSwitch.output_or);           
            }         
            sublibrary.get(subcirc.getInputCount()).get(subcirc.getTTDecimalVal()).add(subcirc);
        }
        //System.out.println("sublibrary"+sublibrary);
    }
    
    public static void initializeSubLibrary(String filepath){
        
        sublibrary = new HashMap<Integer,Map<Integer,List<SubcircuitLibrary>>>();
        Map<Integer,List<SubcircuitLibrary>> init1 = new HashMap<Integer,List<SubcircuitLibrary>>();
        for(int i=0;i<4;i++){
            init1.put(i, new ArrayList<SubcircuitLibrary>());
        }
        sublibrary.put(1, init1);
        Map<Integer,List<SubcircuitLibrary>> init2 = new HashMap<Integer,List<SubcircuitLibrary>>();
        for(int i=0;i<16;i++){
            init2.put(i, new ArrayList<SubcircuitLibrary>());
        }
        sublibrary.put(2, init2);
        Map<Integer,List<SubcircuitLibrary>> init3 = new HashMap<Integer,List<SubcircuitLibrary>>();
        for(int i=0;i<256;i++){
            init3.put(i, new ArrayList<SubcircuitLibrary>());
        }
        sublibrary.put(3, init3);
        
        List<SubcircuitLibrary> subcircuits = new ArrayList<SubcircuitLibrary>();
        subcircuits = PreCompute.getCircuitLibrary(filepath);
        
        for(int i=0;i<subcircuits.size();i++)
        {
            SubcircuitLibrary subcirc = new SubcircuitLibrary();
            subcirc = subcircuits.get(i);
            subcirc.setInputs();
            subcirc.setTT();
            if(containsOUTPUT_OR(subcirc.netlist)){
                subcirc.switches.add(NetSynthSwitch.output_or);           
            }
            sublibrary.get(subcirc.getInputCount()).get(subcirc.getTTDecimalVal()).add(subcirc);
        }
    }
    //</editor-fold>
    
    public static boolean containsOUTPUT_OR(List<DGate> netlist){
        for(DGate gate:netlist){
            if(gate.gtype.equals(DGateType.OUTPUT_OR))
                return true;
        }
        return false;
    }
    
    //<editor-fold desc="Run NetSynth">
    public static DAGW runNetSynthCode(String codeLines, List<NetSynthSwitch> switches, JSONArray subcircuits){
        String filepathSubCirc = Utilities.getResourcesFilepath() + "/userSubcircuits.json"; 
        File file = new File(filepathSubCirc);
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(subcircuits.toString());
            writer.flush();
            writer.close();
            initializeSubLibrary(filepathSubCirc);
        } catch (IOException ex) {
            Logger.getLogger(NetSynth.class.getName()).log(Level.SEVERE, null, ex);
        }
        return runNetSynthCode(codeLines,switches);
    }
    
    public static DAGW runNetSynthCode(String codeLines, List<NetSynthSwitch> switches) {

        DAGW finaldag = new DAGW();
        
        String filepath = "";
        filepath = Utilities.getResourcesFilepath();
        filepath += "tempVerilog.v";
        File fespinp = new File(filepath);
        try {
            Writer output = new BufferedWriter(new FileWriter(fespinp));
            output.write(codeLines);
            output.close();
        } catch (IOException ex) {
            Logger.getLogger(NetSynth.class.getName()).log(Level.SEVERE, null, ex);
        }
        finaldag = runNetSynth(filepath,switches);

        return finaldag;
    }
    
    /**
     * Function ************************************************************
     * <br>
     * Synopsis [Controller function in NetSynth. Parses Verilog to a Directed
     * Acyclic Graph]
     * <br>
     * Description [This is the default function. Takes the verilog file's
     * filepath as the input parameter and gives an optimal result]
     * <br>
     * SideEffects []
     * <br>
     * SeeAlso []
     *
     * @param vfilepath
     * @return
     * *********************************************************************
     */
    public static DAGW runNetSynth(String vfilepath) {
        return runNetSynth(vfilepath, new ArrayList<NetSynthSwitch>());
    }
    
    public static DAGW runNetSynth(String vfilepath, List<NetSynthSwitch> switches, JSONArray subcircuits){
        
        DAGW finaldag = new DAGW();

        String filepathSubCirc = Utilities.getResourcesFilepath() + "/userSubcircuits.json"; 
        File file = new File(filepathSubCirc);
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(subcircuits.toString());
            writer.flush();
            writer.close();
            initializeSubLibrary(filepathSubCirc);
        } catch (IOException ex) {
            Logger.getLogger(NetSynth.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return runNetSynth(vfilepath,switches);
    }
    
    public static DAGW runNetSynth(String vfilepath, List<NetSynthSwitch> switches) {

        DAGW finaldag = new DAGW();

        List<String> inputnames = new ArrayList<String>();
        List<String> outputnames = new ArrayList<String>();
        List<DGate> netlist = new ArrayList<DGate>();

        boolean isStructural = false;
        boolean hasCaseStatements = false;

        String alllines = parseVerilogFile.verilogFileLines(vfilepath);
        isStructural = parseVerilogFile.isStructural(alllines);
        hasCaseStatements = parseVerilogFile.hasCaseStatements(alllines);

        inputnames = parseVerilogFile.getInputNames(alllines);
        outputnames = parseVerilogFile.getOutputNames(alllines);

        netlist = getNetlist(vfilepath, switches);
        netlist = rewireNetlist(netlist);
        
        finaldag = CreateMultDAGW(netlist);
        //System.out.println(inputnames);
        
        finaldag = DAGW.addDanglingInputs(finaldag, inputnames);
        finaldag = DAGW.reorderinputs(finaldag, inputnames);

        return finaldag;
    }
    
    //</editor-fold>
    
    //<editor-fold desc="Get Netlist">
    /**
     * Function ************************************************************
     * <br>
     * Synopsis [Controller function in NetSynth. Parses Verilog to a Directed
     * Acyclic Graph]
     * <br>
     * Description [This is the default function. Takes the verilog file's
     * filepath as the input parameter and gives an optimal result]
     * <br>
     * SideEffects []
     * <br>
     * SeeAlso []
     *
     * @param vfilepath
     * @return
     * *********************************************************************
     */
    public static List<DGate> getNetlist(String vfilepath) {
        
        return getNetlist(vfilepath, new ArrayList<NetSynthSwitch>());
    }
    
    public static List<DGate> getNetlist(String vfilepath,List<NetSynthSwitch> switches, JSONArray subcircuits){
        String filepathSubCirc = Utilities.getResourcesFilepath() + "/userSubcircuits.json";
        File file = new File(filepathSubCirc);
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(subcircuits.toString());
            writer.flush();
            writer.close();
            initializeSubLibrary(filepathSubCirc);
        } catch (IOException ex) {
            Logger.getLogger(NetSynth.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return getNetlist(vfilepath,switches);
    
    }

    public static List<DGate> getNetlist(String vfilepath,List<NetSynthSwitch> switches){
        if(sublibrary == null){
            initializeSubLibrary();
        }
        if(sublibrary.isEmpty()){
            initializeSubLibrary();
        }
        List<String> inputnames = new ArrayList<String>();
        List<String> outputnames = new ArrayList<String>();
        
        double dirsize = 0;
        double invsize = 0;
        double structsize = 0;
        double netsize = 0;
        
        List<DGate> netlist = new ArrayList<DGate>();
        List<DGate> dirnetlist = new ArrayList<DGate>();
        List<DGate> invnetlist = new ArrayList<DGate>();
        List<DGate> naiveNetlist = new ArrayList<DGate>();
        List<DGate> structNetlist = new ArrayList<DGate>();
        
        boolean isStructural = false;
        boolean hasCaseStatements = false;
        boolean hasDontCares = false;
        
        String alllines = parseVerilogFile.verilogFileLines(vfilepath);
        isStructural = parseVerilogFile.isStructural(alllines);
        inputnames = parseVerilogFile.getInputNames(alllines);
        outputnames = parseVerilogFile.getOutputNames(alllines);
        if(isStructural){
            naiveNetlist = parseVerilogFile.parseStructural(alllines); //Convert Verilog File to List of DGates 
            naiveNetlist = rewireNetlist(naiveNetlist);
            if (switches.contains(NetSynthSwitch.originalstructural)) {
                return naiveNetlist;
            } 
            else {
                
                structNetlist = convertNaiveToNORNOT(naiveNetlist); // Convert Naive Netlist to List of DGates containing only NOR and NOTs
            }
            List<String> ttValues = new ArrayList<String>();
            List<String> invttValues = new ArrayList<String>();

            ttValues = BooleanSimulator.getTruthTable(structNetlist, inputnames);  // Compute Truth Table of Each Output
            invttValues = BooleanSimulator.invertTruthTable(ttValues); // Compute Inverse Truth Table of Each Output

            CircuitDetails direct = new CircuitDetails(inputnames, outputnames, ttValues);
            CircuitDetails inverted = new CircuitDetails(inputnames, outputnames, invttValues);
            
            dirnetlist = runEspressoAndABC(direct,switches);
            invnetlist = runInvertedEspressoAndABC(inverted,switches);
            
            if(!switches.contains(NetSynthSwitch.noswap)){
                structNetlist = subCircuitSwap.implementSwap(structNetlist,switches,sublibrary);
                structNetlist = subCircuitSwap.implementSwap(structNetlist,switches,sublibrary);
                
                dirnetlist = subCircuitSwap.implementSwap(dirnetlist, switches, sublibrary);
                dirnetlist = subCircuitSwap.implementSwap(dirnetlist, switches, sublibrary);
                
                invnetlist = subCircuitSwap.implementSwap(invnetlist, switches, sublibrary);
                invnetlist = subCircuitSwap.implementSwap(invnetlist, switches, sublibrary);
            }
            
            dirsize = getRepressorsCost(dirnetlist);
            invsize = getRepressorsCost(invnetlist);
            structsize = getRepressorsCost(structNetlist);
            
            if ((structsize <= dirsize) && (structsize <= invsize)) {
                return structNetlist;
            } 
            else {
                if (dirsize == invsize) {
                    if (NOR3count(dirnetlist) < NOR3count(invnetlist)) {
                        return dirnetlist;
                    } else {
                        return invnetlist;
                    }
                } else if (dirsize < invsize) {
                    return dirnetlist;
                } else {
                    return invnetlist;
                }
            }
        }
        else{
            hasCaseStatements = parseVerilogFile.hasCaseStatements(alllines);
            if(hasCaseStatements){
                CircuitDetails direct = new CircuitDetails();
                direct = parseVerilogFile.parseCaseStatements(alllines);
                List<String> invttValues = new ArrayList<String>();
                invttValues = BooleanSimulator.invertTruthTable(direct.truthTable);
                CircuitDetails inverted = new CircuitDetails(direct.inputNames, direct.outputNames, invttValues);

                hasDontCares = parseVerilogFile.hasDontCares(direct.truthTable);
                //<editor-fold desc="Behavioral- Case Statements - Has Don't Cares">
                if (hasDontCares) {
                    dirnetlist = runDCEspressoAndABC(direct, switches);
                    invnetlist = runInvertedDCEspressoAndABC(inverted, switches);
                    
                    if (!switches.contains(NetSynthSwitch.noswap)) {
                        dirnetlist = subCircuitSwap.implementSwap(dirnetlist, switches, sublibrary);
                        dirnetlist = subCircuitSwap.implementSwap(dirnetlist, switches, sublibrary);
                        
                        invnetlist = subCircuitSwap.implementSwap(invnetlist, switches, sublibrary);
                        invnetlist = subCircuitSwap.implementSwap(invnetlist, switches, sublibrary);
                    }
                    dirsize = getRepressorsCost(dirnetlist);
                    invsize = getRepressorsCost(invnetlist);

                    if (dirsize <= invsize) {
                        return dirnetlist;
                    } else {
                        return invnetlist;                        
                    }
                    
                } //</editor-fold>
                //<editor-fold desc="Behavioral- Case Statements - NO Don't Cares">
                else {
                    
                    dirnetlist = runEspressoAndABC(direct, switches);
                    invnetlist = runInvertedEspressoAndABC(inverted, switches);
                    if (!switches.contains(NetSynthSwitch.noswap)) {
                        dirnetlist = subCircuitSwap.implementSwap(dirnetlist, switches, sublibrary);
                        dirnetlist = subCircuitSwap.implementSwap(dirnetlist, switches, sublibrary);
                        
                        invnetlist = subCircuitSwap.implementSwap(invnetlist, switches, sublibrary);
                        invnetlist = subCircuitSwap.implementSwap(invnetlist, switches, sublibrary);
                    }
                    dirsize = getRepressorsCost(dirnetlist);
                    invsize = getRepressorsCost(invnetlist);

                    if (dirsize <= invsize) {
                        return dirnetlist;
                    } else {
                        return invnetlist;
                    }
                }
                //</editor-fold>
            }
            else
            {
                try {
                    String modifiedVfilepath = genVerilogFile.modifyAssignVerilog(vfilepath);
                    List<DGate> abcNetlist = ABCAdaptor.runABCverilog_fullFilePath(modifiedVfilepath);

                    List<String> ttValues = new ArrayList<String>();
                    List<String> invttValues = new ArrayList<String>();

                    inputnames = parseVerilogFile.getInputNames(alllines);
                    outputnames = parseVerilogFile.getOutputNames(alllines);

                    ttValues = BooleanSimulator.getTruthTable(abcNetlist, inputnames);  // Compute Truth Table of Each Output
                    invttValues = BooleanSimulator.invertTruthTable(ttValues); // Compute Inverse Truth Table of Each Output

                    CircuitDetails direct = new CircuitDetails(inputnames, outputnames, ttValues);
                    CircuitDetails inverted = new CircuitDetails(inputnames, outputnames, invttValues);

                    dirnetlist = runEspressoAndABC(direct,switches);
                    invnetlist = runInvertedEspressoAndABC(inverted, switches);
                    
                    if (!switches.contains(NetSynthSwitch.noswap)) {
                        dirnetlist = subCircuitSwap.implementSwap(dirnetlist, switches, sublibrary);
                        dirnetlist = subCircuitSwap.implementSwap(dirnetlist, switches, sublibrary);
                        
                        invnetlist = subCircuitSwap.implementSwap(invnetlist, switches, sublibrary);
                        invnetlist = subCircuitSwap.implementSwap(invnetlist, switches, sublibrary);
                    }
                    dirsize = getRepressorsCost(dirnetlist);
                    invsize = getRepressorsCost(invnetlist);

                    if (dirsize < invsize) {
                        return dirnetlist;
                    } else {
                        return invnetlist;
                    }

                    //printNetlist(abcNetlist);
                } catch (InterruptedException ex) {
                    Logger.getLogger(NetSynth.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            //seehere
        }
        
        
        return netlist;
    }
    //</editor-fold>
    
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
     * @param netlist
     * @return
     * *********************************************************************
     */
    public static double getRepressorsCost(List<DGate> netlist) {
        double count = 0;
        for (DGate xgate : netlist) {
            if (xgate.gtype.equals(DGateType.NOR)) {
                count += 1.5;
            }
            if (xgate.gtype.equals(DGateType.NOT)) {
                count += 1;
            }
            if (xgate.gtype.equals(DGateType.AND)) {
                count += 2;
            }

        }

        return count;
    }

    //<editor-fold desc="Run ABC & Espresso">
    public static List<DGate> runDCEspressoAndABC(CircuitDetails circ, List<NetSynthSwitch> synthmode) {
        List<DGate> EspCircuit = new ArrayList<DGate>();
        List<DGate> ABCCircuit = new ArrayList<DGate>();
        List<String> espressoFile = new ArrayList<String>();
        List<String> blifFile = new ArrayList<String>();

        espressoFile = EspressoAdaptor.createFile(circ);
        blifFile = BlifAdaptor.createFile(circ);
        String filestring = "";

        filestring = Utilities.getResourcesFilepath();
        String filestringblif = "";
        filestringblif = filestring + "Blif_File";
        filestringblif += ".blif";
        String filestringesp = "";

        filestringesp += filestring + "Espresso_File" + ".txt";
        File fespinp = new File(filestringesp);
        //Writer output;
        try {
            BufferedWriter output = new BufferedWriter(new FileWriter(fespinp));

            for (String xline : espressoFile) {
                String newl = (xline + "\n");
                output.write(newl);
            }
            output.close();
        } catch (IOException ex) {
            Logger.getLogger(NetSynth.class.getName()).log(Level.SEVERE, null, ex);
            Logger.getLogger(NetSynth.class.getName()).log(Level.SEVERE,null,"Expresso_File.txt missing. Please verify if Espresso can run on your machine.\n");
        }
        List<String> EspOutput = new ArrayList<String>();
        EspOutput = EspressoAdaptor.runEspresso(filestringesp);
        fespinp.deleteOnExit();

        
        if (!synthmode.contains(NetSynthSwitch.abc)) {
            EspCircuit = EspressoAdaptor.convertPOStoNORNOT(EspOutput);
            EspCircuit = optimize(EspCircuit);
        }

        if (!synthmode.contains(NetSynthSwitch.espresso)) {
            ABCCircuit = parseEspressoOutToABC(EspOutput);
        }

        if (synthmode.contains(NetSynthSwitch.abc)) {
            return ABCCircuit;
        } else if (synthmode.contains(NetSynthSwitch.espresso)) {
            return EspCircuit;
        } else {
            if (EspCircuit.size() < ABCCircuit.size()) {
                return EspCircuit;
            } else {
                return ABCCircuit;
            }
        }
    }
    
    public static List<DGate> runInvertedDCEspressoAndABC(CircuitDetails circ, List<NetSynthSwitch> synthmode) {

        List<DGate> EspCircuit = new ArrayList<DGate>();
        List<DGate> ABCCircuit = new ArrayList<DGate>();
        List<String> espressoFile = new ArrayList<String>();
        List<String> blifFile = new ArrayList<String>();

        espressoFile = EspressoAdaptor.createFile(circ);
        blifFile = BlifAdaptor.createFile(circ);
        String filestring = "";

        filestring = Utilities.getResourcesFilepath();

        String filestringblif = "";
        filestringblif = filestring + "Blif_File";
        filestringblif += ".blif";
        String filestringesp = "";

        filestringesp += filestring + "Espresso_File" + ".txt";
        File fespinp = new File(filestringesp);
        //Writer output;
        try {
            Writer output = new BufferedWriter(new FileWriter(fespinp));

            for (String xline : espressoFile) {
                String newl = (xline + "\n");
                output.write(newl);
            }
            output.close();
        } catch (IOException ex) {
            Logger.getLogger(NetSynth.class.getName()).log(Level.SEVERE, null, ex);
        }
        List<String> EspOutput = new ArrayList<String>();
        EspOutput = EspressoAdaptor.runEspresso(filestringesp);
        fespinp.deleteOnExit();

        EspCircuit = EspressoAdaptor.convertPOStoNORNOT(EspOutput);
        ABCCircuit = parseINVEspressoOutToABC(EspOutput);

        List<DGate> finalEspCircuit = new ArrayList<DGate>();
        List<DGate> finalABCCircuit = new ArrayList<DGate>();

        for (int i = 0; i < EspCircuit.size(); i++) {
            if (EspCircuit.get(i).output.wtype.equals(DWireType.output)) {
                String outname = "";
                outname = EspCircuit.get(i).output.name.trim();
                String notinpwirename = "0Wire" + Global.wirecount++;
                DWire notinp = new DWire(notinpwirename, DWireType.connector);
                EspCircuit.get(i).output = notinp;
                finalEspCircuit.add(EspCircuit.get(i));
                DGate outnot = new DGate();
                outnot.gtype = DGateType.NOT;
                outnot.input.add(notinp);
                outnot.output = new DWire(outname, DWireType.output);
                finalEspCircuit.add(outnot);
            } else {
                finalEspCircuit.add(EspCircuit.get(i));
            }
        }
        for (int i = 0; i < ABCCircuit.size(); i++) {
            if (ABCCircuit.get(i).output.wtype.equals(DWireType.output)) {
                String outname = "";
                outname = ABCCircuit.get(i).output.name.trim();
                String notinpwirename = "0Wire" + Global.wirecount++;
                DWire notinp = new DWire(notinpwirename, DWireType.connector);
                ABCCircuit.get(i).output = notinp;
                finalABCCircuit.add(ABCCircuit.get(i));
                DGate outnot = new DGate();
                outnot.gtype = DGateType.NOT;
                outnot.input.add(notinp);
                outnot.output = new DWire(outname, DWireType.output);
                finalABCCircuit.add(outnot);
            } else {
                finalABCCircuit.add(ABCCircuit.get(i));
            }
        }

        finalEspCircuit = optimize(finalEspCircuit);

        finalABCCircuit = separateOutputGates(finalABCCircuit);
        finalABCCircuit = optimize(finalABCCircuit);

        if (!synthmode.contains(NetSynthSwitch.espresso)) {
            ABCCircuit = parseEspressoOutToABC(EspOutput);
        }

        if (synthmode.contains(NetSynthSwitch.abc) && !synthmode.contains(NetSynthSwitch.espresso)) {
            return finalABCCircuit;
        } else if (synthmode.contains(NetSynthSwitch.espresso) && !synthmode.contains(NetSynthSwitch.abc)) {
            return finalEspCircuit;
        } else {
            if (finalEspCircuit.size() < finalABCCircuit.size()) {
                return finalEspCircuit;
            } else {
                return finalABCCircuit;
            }
        }
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
     * @param circ
     * @param synthmode
     * @param outpor
     * @param twonots2nor
     * @param nor3
     * @return
     * *********************************************************************
     */
    public static List<DGate> runEspressoAndABC(CircuitDetails circ, List<NetSynthSwitch> synthmode) {

        
        List<DGate> EspCircuit = new ArrayList<DGate>();
        List<DGate> ABCCircuit = new ArrayList<DGate>();
        List<String> espressoFile = new ArrayList<String>();
        List<String> blifFile = new ArrayList<String>();

        espressoFile = EspressoAdaptor.createFile(circ);
        blifFile = BlifAdaptor.createFile(circ);
        String filestring = "";
        
        filestring = Utilities.getResourcesFilepath();
        String filestringblif = "";
        filestringblif = filestring + "Blif_File";
        filestringblif += ".blif";
        String filestringesp = "";

        filestringesp += filestring + "Espresso_File" + ".txt";
        File fespinp = new File(filestringesp);
        //Writer output;
        try {
            Writer output = new BufferedWriter(new FileWriter(fespinp));

            for (String xline : espressoFile) {
                String newl = (xline + "\n");
                output.write(newl);
            }
            output.close();
        } catch (IOException ex) {
            Logger.getLogger(NetSynth.class.getName()).log(Level.SEVERE, null, ex);
        }
        List<String> EspOutput = new ArrayList<String>();
        EspOutput = EspressoAdaptor.runEspresso(filestringesp);
        fespinp.deleteOnExit();

        EspCircuit = EspressoAdaptor.convertPOStoNORNOT(EspOutput);
        EspCircuit = optimize(EspCircuit);
        
        
        File fabcinp = new File(filestringblif);
        try {
            Writer outputblif = new BufferedWriter(new FileWriter(fabcinp));
            for (String xline : blifFile) {
                String newl = (xline + "\n");
                outputblif.write(newl);
            }
            outputblif.close();
            List<DGate> abcoutput = new ArrayList<DGate>();

            try {
                abcoutput = ABCAdaptor.runABC("Blif_File");

            } catch (InterruptedException ex) {
                Logger.getLogger(NetSynth.class.getName()).log(Level.SEVERE, null, ex);
            }

            abcoutput = separateOutputGates(abcoutput);
            abcoutput = optimize(abcoutput);

            for (DGate xgate : abcoutput) {
                ABCCircuit.add(xgate);
            }
            ABCCircuit = removeDanglingGates(ABCCircuit);
            fabcinp.deleteOnExit();

        } catch (IOException ex) {
            Logger.getLogger(NetSynth.class.getName()).log(Level.SEVERE, null, ex);
        }
        fabcinp.deleteOnExit();

        if (synthmode.contains(NetSynthSwitch.abc) && !synthmode.contains(NetSynthSwitch.espresso)) {
            return ABCCircuit;
        } else if (synthmode.contains(NetSynthSwitch.espresso) && !synthmode.contains(NetSynthSwitch.abc)) {
            return EspCircuit;
        } else {
            if (EspCircuit.size() < ABCCircuit.size()) {
                return EspCircuit;
            } else {
                return ABCCircuit;
            }
        }
    }
    
    public static List<DGate> runInvertedEspressoAndABC(CircuitDetails circ, List<NetSynthSwitch> synthmode) {

        List<DGate> EspCircuit = new ArrayList<DGate>();
        List<DGate> ABCCircuit = new ArrayList<DGate>();
        List<String> espressoFile = new ArrayList<String>();
        List<String> blifFile = new ArrayList<String>();

        espressoFile = EspressoAdaptor.createFile(circ);
        blifFile = BlifAdaptor.createFile(circ);
        String filestring = "";

        filestring = Utilities.getResourcesFilepath();
        String filestringblif = "";
        filestringblif = filestring + "Blif_File";
        filestringblif += ".blif";
        String filestringesp = "";

        filestringesp += filestring + "Espresso_File" + ".txt";
        File fespinp = new File(filestringesp);
        //Writer output;
        try {
            Writer output = new BufferedWriter(new FileWriter(fespinp));

            for (String xline : espressoFile) {
                String newl = (xline + "\n");
                output.write(newl);
            }
            output.close();
        } catch (IOException ex) {
            Logger.getLogger(NetSynth.class.getName()).log(Level.SEVERE, null, ex);
        }
        List<String> EspOutput = new ArrayList<String>();
        EspOutput = EspressoAdaptor.runEspresso(filestringesp);
        fespinp.deleteOnExit();

        EspCircuit = EspressoAdaptor.convertPOStoNORNOT(EspOutput);
        
        File fabcinp = new File(filestringblif);
        try {
            Writer outputblif = new BufferedWriter(new FileWriter(fabcinp));
            for (String xline : blifFile) {
                String newl = (xline + "\n");
                outputblif.write(newl);
            }
            outputblif.close();
            List<DGate> abcoutput = new ArrayList<DGate>();

            try {
                abcoutput = ABCAdaptor.runABC("Blif_File");

            } catch (InterruptedException ex) {
                Logger.getLogger(NetSynth.class.getName()).log(Level.SEVERE, null, ex);
            }

            for (DGate xgate : abcoutput) {
                ABCCircuit.add(xgate);
            }
            ABCCircuit = removeDanglingGates(ABCCircuit);
            fabcinp.deleteOnExit();

        } catch (IOException ex) {
            Logger.getLogger(NetSynth.class.getName()).log(Level.SEVERE, null, ex);
        }

        fabcinp.deleteOnExit();
        
        List<DGate> finalEspCircuit = new ArrayList<DGate>();
        List<DGate> finalABCCircuit = new ArrayList<DGate>();

        finalEspCircuit = invertCircuit(EspCircuit);
        /*for (int i = 0; i < EspCircuit.size(); i++) {
            if (EspCircuit.get(i).output.wtype.equals(DWireType.output)) {
                String outname = "";
                outname = EspCircuit.get(i).output.name.trim();
                String notinpwirename = "0Wire" + Global.wirecount++;
                DWire notinp = new DWire(notinpwirename, DWireType.connector);
                EspCircuit.get(i).output = notinp;
                finalEspCircuit.add(EspCircuit.get(i));
                DGate outnot = new DGate();
                outnot.gtype = DGateType.NOT;
                outnot.input.add(notinp);
                outnot.output = new DWire(outname, DWireType.output);
                finalEspCircuit.add(outnot);
            } else {
                finalEspCircuit.add(EspCircuit.get(i));
            }
        }*/
        
        finalABCCircuit = invertCircuit(ABCCircuit);
        /*for (int i = 0; i < ABCCircuit.size(); i++) {
            if (ABCCircuit.get(i).output.wtype.equals(DWireType.output)) {
                String outname = "";
                outname = ABCCircuit.get(i).output.name.trim();
                String notinpwirename = "0Wire" + Global.wirecount++;
                DWire notinp = new DWire(notinpwirename, DWireType.connector);
                ABCCircuit.get(i).output = notinp;
                finalABCCircuit.add(ABCCircuit.get(i));
                DGate outnot = new DGate();
                outnot.gtype = DGateType.NOT;
                outnot.input.add(notinp);
                outnot.output = new DWire(outname, DWireType.output);
                finalABCCircuit.add(outnot);
            } else {
                finalABCCircuit.add(ABCCircuit.get(i));
            }
        }*/
        
        finalEspCircuit = rewireNetlist(finalEspCircuit);
        finalABCCircuit = rewireNetlist(finalABCCircuit);
        
        finalEspCircuit = optimize(finalEspCircuit);
        finalABCCircuit = separateOutputGates(finalABCCircuit);
        finalABCCircuit = optimize(finalABCCircuit);

        if (synthmode.contains(NetSynthSwitch.abc) && !synthmode.contains(NetSynthSwitch.espresso)) {
            return finalABCCircuit;
        } else if (synthmode.contains(NetSynthSwitch.espresso) && !synthmode.contains(NetSynthSwitch.abc)) {
            return finalEspCircuit;
        } else {
            if (finalEspCircuit.size() < finalABCCircuit.size()) {
                return finalEspCircuit;
            } else {
                return finalABCCircuit;
            }
        }

    }
    //</editor-fold>
    
    public static List<DGate> invertCircuit(List<DGate> netlist){
        for(int i=0;i<netlist.size();i++){
            if(netlist.get(i).output.wtype.equals(DWireType.output)){
                
                String notInpWireName = "0Wire" + Global.wirecount++;
                String outputName = netlist.get(i).output.name;
                DWire notInpWire = new DWire(notInpWireName,DWireType.connector);
                DGate notGate = new DGate();
                notGate.gtype = DGateType.NOT;
                notGate.input.add(notInpWire);
                notGate.output = new DWire(outputName,DWireType.output);
                
                netlist.get(i).output.name = notInpWire.name;
                netlist.get(i).output.wtype = notInpWire.wtype;
                for(int j=i+1;j<netlist.size();j++){
                    for(DWire input:netlist.get(j).input){
                        if(input.name.equals(outputName)){
                            input.name = notInpWire.name;
                            input.wtype = notInpWire.wtype; 
                        }
                    }
                }
                netlist.add(i+1, notGate);
                i = i+1;
                
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
     * @param netlist
     * @return
     * *********************************************************************
     */
    public static List<DGate> separateOutputGates(List<DGate> netlist) {
        List<DGate> finalnetlist = new ArrayList<DGate>();
        HashMap<Integer, DGate> addgate = new HashMap<Integer, DGate>();
        int outToOutflag = 0;

        do {
            addgate = new HashMap<Integer, DGate>();
            for (int i = 0; i < (netlist.size() - 1); i++) {

                outToOutflag = 0;
                if (netlist.get(i).output.wtype.equals(DWireType.output)) {
                    for (int j = (i + 1); j < netlist.size(); j++) {
                        if (netlist.get(j).output.wtype.equals(DWireType.output)) {
                            for (int m = 0; m < netlist.get(j).input.size(); m++) {
                                if (netlist.get(j).input.get(m).wtype.equals(DWireType.output) && netlist.get(j).input.get(m).name.trim().equals(netlist.get(i).output.name.trim())) {
                                    outToOutflag = 1;
                                }
                            }
                        }
                    }
                    if (outToOutflag == 1) {
                        DGate newg = new DGate();
                        newg.gtype = netlist.get(i).gtype;
                        newg.input.addAll(netlist.get(i).input);
                        String Wname = "0Wire" + Global.wirecount++;
                        DWire outW = new DWire(Wname, DWireType.connector);
                        newg.output = outW;
                        addgate.put(i, newg);
                        for (int j = (i + 1); j < netlist.size(); j++) {
                            if (netlist.get(j).output.wtype.equals(DWireType.output)) {
                                for (int m = 0; m < netlist.get(j).input.size(); m++) {
                                    if (netlist.get(j).input.get(m).wtype.equals(DWireType.output) && netlist.get(j).input.get(m).name.trim().equals(netlist.get(i).output.name.trim())) {
                                        netlist.get(j).input.set(m, outW);
                                    }
                                }
                            }
                        }
                    }
                }
            }
            finalnetlist = new ArrayList<DGate>();
            for (int i = 0; i < netlist.size(); i++) {
                if (addgate.containsKey(i)) {
                    finalnetlist.add(addgate.get(i));
                }
                finalnetlist.add(netlist.get(i));
            }
            netlist = new ArrayList<DGate>();
            netlist.addAll(finalnetlist);
        } while (!addgate.isEmpty());

        addgate = new HashMap<Integer, DGate>();
        for (int i = 0; i < (netlist.size() - 1); i++) {

            outToOutflag = 0;
            if (netlist.get(i).output.wtype.equals(DWireType.output)) {
                for (int j = (i + 1); j < netlist.size(); j++) {

                    for (int m = 0; m < netlist.get(j).input.size(); m++) {
                        if (netlist.get(j).input.get(m).wtype.equals(DWireType.output) && netlist.get(j).input.get(m).name.trim().equals(netlist.get(i).output.name.trim())) {
                            outToOutflag = 1;
                        }
                    }
                }
                if (outToOutflag == 1) {
                    DGate newg = new DGate();
                    newg.gtype = netlist.get(i).gtype;
                    newg.input.addAll(netlist.get(i).input);
                    String Wname = "0Wire" + Global.wirecount++;
                    DWire outW = new DWire(Wname, DWireType.connector);
                    newg.output = outW;
                    addgate.put(i, newg);
                    for (int j = (i + 1); j < netlist.size(); j++) {
                        for (int m = 0; m < netlist.get(j).input.size(); m++) {
                            if (netlist.get(j).input.get(m).wtype.equals(DWireType.output) && netlist.get(j).input.get(m).name.trim().equals(netlist.get(i).output.name.trim())) {
                                netlist.get(j).input.set(m, outW);
                                //System.out.println("Found that case!!");
                            }
                        }
                    }
                }
            }
        }
        finalnetlist = new ArrayList<DGate>();
        for (int i = 0; i < netlist.size(); i++) {
            if (addgate.containsKey(i)) {
                finalnetlist.add(addgate.get(i));
            }
            finalnetlist.add(netlist.get(i));
        }
        netlist = new ArrayList<DGate>();
        netlist.addAll(finalnetlist);

        return finalnetlist;
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
     * @param filelines
     * @param filename
     * @return
     * *********************************************************************
     */
    public static String create_VerilogFile(List<String> filelines, String filename) {
        String filestring = "";

        filestring = Utilities.getResourcesFilepath();
        filestring += filename + ".v";
        File fespinp = new File(filestring);
        //Writer output;
        try {
            Writer output = new BufferedWriter(new FileWriter(fespinp));
            //output = new BufferedWriter(new FileWriter(fespinp));
            for (String xline : filelines) {
                String newl = (xline + "\n");
                output.write(newl);
            }
            output.close();
        } catch (IOException ex) {
            Logger.getLogger(NetSynth.class.getName()).log(Level.SEVERE, null, ex);
        }
        return filestring;
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
     * @param inputs
     * @param hex
     * @param filename
     * *********************************************************************
     */
    public static void create_VerilogFile(int inputs, String hex, String filename) {
        List<String> filelines = new ArrayList<String>();
        filelines = genVerilogFile.createSingleOutpVerilogFile(inputs, hex);
        String filestring = "";
        if (Filepath.contains("prash")) {
            filestring += Filepath + "src/org/cellocad/BU/resources/netsynthResources/";
        } else {
            filestring += Filepath + "org/cellocad/BU/resources/netsynthResources/";
        }
        filestring += filename + ".v";
        File fespinp = new File(filestring);
        //Writer output;
        try {
            Writer output = new BufferedWriter(new FileWriter(fespinp));
            //output = new BufferedWriter(new FileWriter(fespinp));
            for (String xline : filelines) {
                String newl = (xline + "\n");
                output.write(newl);
            }
            output.close();
        } catch (IOException ex) {
            Logger.getLogger(NetSynth.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static List<DGate> parseEspressoOutToABC(List<String> espout) {
        List<DGate> netlistout = new ArrayList<DGate>();
        List<String> vfilelines = new ArrayList<String>();

        vfilelines = EspressoAdaptor.convertEspressoOutputToVerilog(espout);
        String vfilepath = "";
        vfilepath = create_VerilogFile(vfilelines, "espressoVerilog");
        try {
            netlistout = ABCAdaptor.runABCverilog("espressoVerilog");
            netlistout = separateOutputGates(netlistout);

            netlistout = optimize(netlistout);

        } catch (InterruptedException ex) {
            Logger.getLogger(NetSynth.class.getName()).log(Level.SEVERE, null, ex);
        }

        return netlistout;
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
     * @param espout
     * @return
     * *********************************************************************
     */
    public static List<DGate> parseINVEspressoOutToABC(List<String> espout) {
        List<DGate> netlistout = new ArrayList<DGate>();
        
        List<String> vfilelines = new ArrayList<String>();

        vfilelines = EspressoAdaptor.convertEspressoOutputToVerilog(espout);
        String vfilepath = "";
        vfilepath = create_VerilogFile(vfilelines, "espressoVerilog");
        try {
            netlistout = ABCAdaptor.runABCverilog("espressoVerilog");

        } catch (InterruptedException ex) {
            Logger.getLogger(NetSynth.class.getName()).log(Level.SEVERE, null, ex);
        }

        return netlistout;
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
     * @param filename
     * @return
     * *********************************************************************
     */
    public static List<DGate> parseEspressoFileToABC(String filename) {
        List<DGate> netlistout = new ArrayList<DGate>();
        List<String> espout = new ArrayList<String>();
        espout = EspressoAdaptor.runEspresso(filename);
        
        List<String> vfilelines = new ArrayList<String>();

        vfilelines = EspressoAdaptor.convertEspressoOutputToVerilog(espout);
        String vfilepath = "";
        vfilepath = create_VerilogFile(vfilelines, "espressoVerilog");
        try {

            netlistout = ABCAdaptor.runABCverilog("espressoVerilog");
            netlistout = optimizeNetlist(netlistout, true, true, false);
        
        } catch (InterruptedException ex) {
            Logger.getLogger(NetSynth.class.getName()).log(Level.SEVERE, null, ex);
        }

        return netlistout;
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
     * @param netlist
     * @return
     * *********************************************************************
     */
    public static List<DGate> rewireNetlist(List<DGate> netlist) {
        for (int i = 0; i < netlist.size(); i++) {
            for (int j = 0; j <= i; j++) {
                if (i != j) {
                    for (int m = 0; m < netlist.get(i).input.size(); m++) {
                        if (netlist.get(i).input.get(m).name.equals(netlist.get(j).output.name)) {
                            netlist.get(i).input.set(m, netlist.get(j).output);
                        }
                    }
                }
            }
        }
        return netlist;
    }

    /**
     * *************************************************************
     * Function
     * <br>
     * Synopsis []
     * <br>
     * Description []
     * <br>
     * SideEffects []
     * <br>
     * SeeAlso []
     * <br>
     *
     * @param netlistinp
     * @return
     * *********************************************************************
     */
    public static List<DGate> convertToNOR3(List<DGate> netlistinp) {
        List<DGate> tempNetlist = new ArrayList<DGate>();
        for (int i = 0; i < netlistinp.size(); i++) {
            tempNetlist.add(new DGate(netlistinp.get(i).gtype, netlistinp.get(i).input, netlistinp.get(i).output));
        }

        for (int i = 0; i < netlistinp.size(); i++) {
            if (netlistinp.get(i).gtype.equals(DGateType.NOR) && (netlistinp.get(i).input.size() < 3)) {
                for (int j = i + 1; j < netlistinp.size(); j++) {
                    if (netlistinp.get(j).gtype.equals(DGateType.NOT) && (netlistinp.get(j).input.get(0).name.trim().equals(netlistinp.get(i).output.name.trim()))) {
                        for (int k = j + 1; k < netlistinp.size(); k++) {
                            if (netlistinp.get(k).gtype.equals(DGateType.NOR) && (netlistinp.get(k).input.size() < 3)) {
                                int indx = 0;
                                boolean found = false;
                                for (int m = 0; m < netlistinp.get(k).input.size(); m++) {
                                    if (netlistinp.get(k).input.get(m).name.trim().equals(netlistinp.get(j).output.name.trim())) {
                                        found = true;
                                        indx = m;
                                    }
                                }
                                if (found) {
                                    netlistinp.get(k).input.remove(indx);
                                    netlistinp.get(k).input.addAll(netlistinp.get(i).input);

                                }
                            }
                        }
                    }
                }
            }
        }
        netlistinp = removeDanglingGates(netlistinp);
        
        if (netlistinp.size() == tempNetlist.size()) {
            return tempNetlist;
        }

        return netlistinp;

    }

    /**
     * *************************************************************
     * Function
     * <br>
     * Synopsis []
     * <br>
     * Description []
     * <br>
     * SideEffects []
     * <br>
     * SeeAlso []
     * <br>
     *
     * @param netlistinp
     * @return
     * *********************************************************************
     */
    public static List<DGate> convertToOutputOR3(List<DGate> netlistinp) {
        
        for (int i = 0; i < netlistinp.size(); i++) {
            if (netlistinp.get(i).gtype.equals(DGateType.NOR) && (netlistinp.get(i).input.size() < 3)) {
                for (int j = i + 1; j < netlistinp.size(); j++) {
                    if (netlistinp.get(j).gtype.equals(DGateType.NOT) && (netlistinp.get(j).input.get(0).name.trim().equals(netlistinp.get(i).output.name.trim()))) {
                        for (int k = j + 1; k < netlistinp.size(); k++) {
                            if (netlistinp.get(k).gtype.equals(DGateType.NOR) && (netlistinp.get(i).input.size() < 3)) {
                                int indx = 0;
                                boolean found = false;
                                for (int m = 0; m < netlistinp.get(k).input.size(); m++) {
                                    if (netlistinp.get(k).input.get(m).name.trim().equals(netlistinp.get(j).output.name.trim())) {
                                        for (int l = k + 1; l < netlistinp.size(); l++) {
                                            if ((netlistinp.get(l).gtype.equals(DGateType.NOT)) && (netlistinp.get(l).input.get(0).name.trim().equals(netlistinp.get(k).output.name.trim())) && (netlistinp.get(l).output.wtype.equals(DWireType.output))) {
                                                found = true;
                                                //System.out.println("Found output OR 3");
                                                netlistinp.get(l).gtype = DGateType.OR;
                                                netlistinp.get(l).input.remove(0);
                                                netlistinp.get(l).input.addAll(netlistinp.get(i).input);
                                                for (int x = 0; x < netlistinp.get(k).input.size(); x++) {
                                                    if (x != m) {
                                                        netlistinp.get(l).input.add(netlistinp.get(k).input.get(x));
                                                    }
                                                }
                                            }
                                        }

                                        //
                                    }
                                }
                            }
                            boolean nornotor = false;
                            int mindx = 0;
                            if (netlistinp.get(k).gtype.equals(DGateType.OR) && (netlistinp.get(i).input.size() < 3) && netlistinp.get(k).output.wtype.equals(DWireType.output)) {
                                for (int m = 0; m < netlistinp.get(k).input.size(); m++) {
                                    if (netlistinp.get(k).input.get(m).name.trim().equals(netlistinp.get(j).output.name.trim())) {
                                        nornotor = true;
                                        mindx = m;
                                    }
                                }
                            }
                            if (nornotor) {
                                netlistinp.get(k).input.remove(mindx);
                                netlistinp.get(k).input.addAll(netlistinp.get(i).input);
                            }
                        }
                    }
                }
            }
            if (netlistinp.get(i).gtype.equals(DGateType.NOR) && (netlistinp.get(i).input.size() == 3)) {
                for (int j = i + 1; j < netlistinp.size(); j++) {
                    if (netlistinp.get(j).gtype.equals(DGateType.NOT) && netlistinp.get(j).input.get(0).name.trim().equals(netlistinp.get(i).output.name.trim()) && netlistinp.get(j).output.wtype.equals(DWireType.output)) {
                        netlistinp.get(j).gtype = DGateType.OR;
                        netlistinp.get(j).input.remove(0);
                        netlistinp.get(j).input.addAll(netlistinp.get(i).input);

                    }
                }
            }
        }
        netlistinp = removeDanglingGates(netlistinp);
        return netlistinp;
    }

    /**
     * *************************************************************
     * Function
     * <br>
     * Synopsis []
     * <br>
     * Description []
     * <br>
     * SideEffects []
     * <br>
     * SeeAlso []
     * <br>
     *
     * @param netlistinp
     * @return
     * *********************************************************************
     */
    public static List<DGate> convertORbeforeAND(List<DGate> netlistinp) {
        for (int i = 0; i < netlistinp.size(); i++) {
            if (netlistinp.get(i).gtype.equals(DGateType.NOR)) {
                for (int j = i + 1; j < netlistinp.size(); j++) {
                    if (netlistinp.get(j).gtype.equals(DGateType.NOT) && netlistinp.get(j).input.get(0).name.trim().equals(netlistinp.get(i).output.name.trim())) {
                        for (int k = j + 1; k < netlistinp.size(); k++) {
                            if (netlistinp.get(k).gtype.equals(DGateType.AND)) {
                                int indx = 0;
                                boolean found = false;
                                for (int m = 0; m < netlistinp.get(k).input.size(); m++) {
                                    if (netlistinp.get(k).input.get(m).name.trim().equals(netlistinp.get(j).output.name.trim())) {
                                        indx = m;
                                        found = true;
                                    }
                                }
                                if (found) {

                                    netlistinp.get(k).input.remove(indx);
                                    DGate orgate = new DGate();
                                    orgate.input.addAll(netlistinp.get(i).input);
                                    orgate.gtype = DGateType.OR;
                                    DWire orOutWire = new DWire("0Wire" + Global.wirecount++, netlistinp.get(j).output.wtype);
                                    orgate.output = orOutWire;
                                    netlistinp.get(k).input.add(orOutWire);
                                    netlistinp.add(k, orgate);
                                }
                            }
                        }
                    }
                }
            }
        }

        netlistinp = removeDanglingGates(netlistinp);
        return netlistinp;
    }

    /**
     * *************************************************************
     * Function
     * <br>
     * Synopsis []
     * <br>
     * Description []
     * <br>
     * SideEffects []
     * <br>
     * SeeAlso []
     * <br>
     *
     * @param netlistinp
     * @return
     * *********************************************************************
     */
    public static List<DGate> convertAND(List<DGate> netlistinp) {
        int countAND = 0;
        for (int i = 0; i < netlistinp.size(); i++) {
            if (netlistinp.get(i).gtype.equals(DGateType.NOT)) {
                for (int j = i + 1; j < netlistinp.size(); j++) {
                    if (netlistinp.get(j).gtype.equals(DGateType.NOT)) {
                        for (int k = j + 1; k < netlistinp.size(); k++) {
                            if (netlistinp.get(k).gtype.equals(DGateType.NOR) && (netlistinp.get(k).input.size() == 2)) {
                                if ((netlistinp.get(k).input.get(0).name.trim().equals(netlistinp.get(i).output.name.trim()) && netlistinp.get(k).input.get(1).name.trim().equals(netlistinp.get(j).output.name.trim())) || (netlistinp.get(k).input.get(0).name.trim().equals(netlistinp.get(j).output.name.trim()) && netlistinp.get(k).input.get(1).name.trim().equals(netlistinp.get(i).output.name.trim()))) {
                                    if (countAND == 2) {
                                        break;
                                    }
                                    netlistinp.get(k).input.remove(1);
                                    netlistinp.get(k).input.remove(0);
                                    netlistinp.get(k).input.add(netlistinp.get(i).input.get(0));
                                    netlistinp.get(k).input.add(netlistinp.get(j).input.get(0));
                                    netlistinp.get(k).gtype = DGateType.AND;
                                    countAND++;
                                }
                            }
                        }
                    }
                    if (countAND == 2) {
                        break;
                    }
                }
            }
            if (countAND == 2) {
                break;
            }
        }

        netlistinp = removeDanglingGates(netlistinp);
        return netlistinp;
    }

    /**
     * *************************************************************
     * Function
     * <br>
     * Synopsis []
     * <br>
     * Description []
     * <br>
     * SideEffects []
     * <br>
     * SeeAlso []
     * <br>
     *
     * @param netlistinp
     * @return
     * *********************************************************************
     */
    public static List<DGate> convertFindORbeforeAND(List<DGate> netlistinp) {
        int countAND = 0;

        for (int i = 0; i < netlistinp.size(); i++) {
            if (netlistinp.get(i).gtype.equals(DGateType.NOR) || netlistinp.get(i).gtype.equals(DGateType.NOT)) {
                for (int j = i + 1; j < netlistinp.size(); j++) {
                    if (netlistinp.get(j).gtype.equals(DGateType.NOR) || netlistinp.get(j).gtype.equals(DGateType.NOT)) {
                        for (int k = j + 1; k < netlistinp.size(); k++) {
                            if (netlistinp.get(k).gtype.equals(DGateType.NOR) && (netlistinp.get(k).input.size() == 2)) {
                                if ((netlistinp.get(k).input.get(0).name.trim().equals(netlistinp.get(i).output.name.trim()) && netlistinp.get(k).input.get(1).name.trim().equals(netlistinp.get(j).output.name.trim())) || (netlistinp.get(k).input.get(0).name.trim().equals(netlistinp.get(j).output.name.trim()) && netlistinp.get(k).input.get(1).name.trim().equals(netlistinp.get(i).output.name.trim()))) {
                                    if (countAND == 2) {
                                        break;
                                    }
                                    //System.out.println("Found it");
                                    DGate or1 = new DGate();
                                    DGate or2 = new DGate();

                                    or1.gtype = DGateType.OR;
                                    or2.gtype = DGateType.OR;

                                    or1.input.addAll(netlistinp.get(i).input);
                                    or2.input.addAll(netlistinp.get(j).input);

                                    DWire orOut1 = new DWire("0Wire" + Global.wirecount++, DWireType.connector);
                                    DWire orOut2 = new DWire("0Wire" + Global.wirecount++, DWireType.connector);

                                    or1.output = orOut1;
                                    or2.output = orOut2;

                                    netlistinp.get(k).input.remove(1);
                                    netlistinp.get(k).input.remove(0);
                                    netlistinp.get(k).gtype = DGateType.AND;

                                    boolean or1add = false;
                                    boolean or2add = false;

                                    if (netlistinp.get(i).gtype.equals(DGateType.NOT)) {
                                        netlistinp.get(k).input.add(netlistinp.get(i).input.get(0));
                                    } else if (netlistinp.get(i).gtype.equals(DGateType.NOR)) {
                                        netlistinp.get(k).input.add(orOut1);
                                        or1add = true;

                                    }

                                    if (netlistinp.get(j).gtype.equals(DGateType.NOT)) {
                                        netlistinp.get(k).input.add(netlistinp.get(j).input.get(0));
                                    } else if (netlistinp.get(j).gtype.equals(DGateType.NOR)) {
                                        netlistinp.get(k).input.add(orOut2);
                                        or2add = true;
                                    }

                                    if (or1add) {
                                        netlistinp.add(k, or1);
                                    }
                                    if (or2add) {
                                        netlistinp.add(k, or2);
                                    }

                                    countAND++;
                                }
                            }
                        }
                    }
                    if (countAND == 2) {
                        break;
                    }
                }
            }
            if (countAND == 2) {
                break;
            }
        }
        //}

        netlistinp = removeDanglingGates(netlistinp);
        return netlistinp;
    }

    /**
     * Function ************************************************************
     * <br>
     * Synopsis [Search for the 2 NOTs to NOR motif and replace it with a NOR
     * gate]
     * <br>
     * Description []
     * <br>
     * SideEffects []
     * <br>
     * SeeAlso []
     *
     * @param netlistinp
     * @return
     * *********************************************************************
     */
    public static List<DGate> convert2NOTsToNOR(List<DGate> netlistinp) {
        List<Integer> removegates = new ArrayList<Integer>();
        List<Integer> addgates = new ArrayList<Integer>();

        List<DGate> netlistout = new ArrayList<DGate>();
        List<DGate> gatestoadd = new ArrayList<DGate>();
        
        for (int i = 0; i < netlistinp.size(); i++) {
            if (netlistinp.get(i).gtype.equals(DGateType.NOR)) {
                int inp1flag = 0;
                int inp2flag = 0;
                String inpstring1 = "";
                String inpstring2 = "";
                DWire w1 = new DWire();
                DWire w2 = new DWire();

                //String inp2string ="";
                for (int k = 0; k <= i; k++) {
                    if (i != k) {
                        if (netlistinp.get(k).gtype.equals(DGateType.NOT)) {
                            if (netlistinp.get(i).input.get(0).name.equals(netlistinp.get(k).output.name)) {
                                //System.out.println(netlist(netlistinp.get(i)) + " trigger for inp1flag");
                                inp1flag = 1;
                                inpstring1 = netlistinp.get(k).input.get(0).name;
                                inpstring2 = netlistinp.get(i).input.get(1).name;
                                w1 = new DWire(netlistinp.get(k).input.get(0));
                                w2 = new DWire(netlistinp.get(i).input.get(1));

                            }
                            if (netlistinp.get(i).input.get(1).name.equals(netlistinp.get(k).output.name)) {
                                inp2flag = 1;

                                //System.out.println(netlist(netlistinp.get(i)) + " trigger for inp2flag");
                                inpstring1 = netlistinp.get(k).input.get(0).name;
                                inpstring2 = netlistinp.get(i).input.get(0).name;
                                w1 = new DWire(netlistinp.get(k).input.get(0));
                                w2 = new DWire(netlistinp.get(i).input.get(0));

                            }
                        }
                    }
                }
                int inp2mainflag = 0;
                int gate2flag = 0;
                int secondnorgatepos = 0;
                String invstring2 = "";
                for (int k = 0; k < netlistinp.size(); k++) {
                    if (netlistinp.get(k).gtype.equals(DGateType.NOT)) {
                        if (netlistinp.get(k).input.get(0).name.equals(inpstring2)) {
                            invstring2 = netlistinp.get(k).output.name;
                            inp2mainflag = 1;
                            //System.out.println(netlist(netlistinp.get(k))+" trigger for inp2mainflag");
                        }
                    }
                }
                if ((inp1flag + inp2flag) == 1) {
                    if (inp2mainflag == 1) {
                        //System.out.println("inp1flag or inp2flag are 1 not both and inp2mainflag is on");
                        for (int j = i; j < netlistinp.size(); j++) {
                            if (j != i) {
                                if (netlistinp.get(j).gtype.equals(DGateType.NOR)) {
                                    if (netlistinp.get(j).input.get(0).name.equals(inpstring1) && netlistinp.get(j).input.get(1).name.equals(invstring2)) {
                                        gate2flag = 1;
                                        secondnorgatepos = j;
                                        //System.out.println(netlist(netlistinp.get(j))+" 2nd NOR gate located");
                                        //addgates.add(i);
                                        break;
                                    }
                                    if (netlistinp.get(j).input.get(1).name.equals(inpstring1) && netlistinp.get(j).input.get(0).name.equals(invstring2)) {
                                        gate2flag = 2;
                                        secondnorgatepos = j;
                                        //System.out.println(netlist(netlistinp.get(j))+" 2nd NOR gate located");
                                        //addgates.add(i);
                                        break;
                                    }
                                }
                            }
                        }

                        int norgateexists = 0;
                        DWire noroutW = new DWire();
                        int norpos = 0;
                        if ((gate2flag == 1) || (gate2flag == 2)) {
                            //System.out.println("gate2flag triggered");
                            for (int k = 0; k < netlistinp.size(); k++) {
                                if (netlistinp.get(k).gtype.equals(DGateType.NOR)) {
                                    if ((netlistinp.get(k).input.get(0).name.equals(inpstring1) && netlistinp.get(k).input.get(1).name.equals(inpstring2)) || (netlistinp.get(k).input.get(1).name.equals(inpstring1) && netlistinp.get(k).input.get(0).name.equals(inpstring2))) {
                                        if (!netlistinp.get(k).output.wtype.equals(DWireType.output)) {
                                            norgateexists = 1;
                                            norpos = k;
                                            noroutW = new DWire(netlistinp.get(k).output.name, netlistinp.get(k).output.wtype);
                                            if (k > i) {
                                                addgates.add(i);
                                                gatestoadd.add(netlistinp.get(k));
                                            }
                                            break;
                                        }
                                    }
                                }
                            }
                        }

                        if (norgateexists == 1) {

                            if (gate2flag == 1) {
                                //System.out.println("gate2flag =1");
                                //System.out.println(secondnorgatepos+"+"+netlist(netlistinp.get(secondnorgatepos)));
                                netlistinp.get(secondnorgatepos).input.remove(1);
                                netlistinp.get(secondnorgatepos).input.add(noroutW);
                            } else if (gate2flag == 2) {
                                //System.out.println("gate2flag =2");
                                //System.out.println(secondnorgatepos+"+"+netlist(netlistinp.get(secondnorgatepos)));
                                netlistinp.get(secondnorgatepos).input.remove(0);
                                netlistinp.get(secondnorgatepos).input.add(noroutW);
                            }
                            if (inp1flag == 1) {
                                //System.out.println("inp1flag =1");
                                //System.out.println(norpos+"+"+netlist(netlistinp.get(i)));
                                netlistinp.get(i).input.remove(0);
                                netlistinp.get(i).input.add(noroutW);
                            } else if (inp2flag == 1) {
                                //System.out.println("inp2flag =1");
                                //System.out.println(norpos+"+"+netlist(netlistinp.get(i)));
                                netlistinp.get(i).input.remove(1);
                                netlistinp.get(i).input.add(noroutW);
                            }
                            //System.out.println(noroutW.name);
                        } else {

                            if (gate2flag == 1 || gate2flag == 2) {

                                //System.out.println("NOR Gate does not exist");
                                DGate addnor = new DGate();
                                addnor.gtype = DGateType.NOR;
                                addnor.input.add(w1);
                                addnor.input.add(w2);

                                String noroutWname = "0Wire" + Global.wirecount++;
                                noroutW = new DWire(noroutWname, DWireType.connector);
                                addnor.output = noroutW;
                                if (gate2flag == 1) {
                                    netlistinp.get(secondnorgatepos).input.remove(1);
                                    netlistinp.get(secondnorgatepos).input.add(noroutW);
                                } else if (gate2flag == 2) {
                                    netlistinp.get(secondnorgatepos).input.remove(0);
                                    netlistinp.get(secondnorgatepos).input.add(noroutW);
                                }
                                if (inp1flag == 1) {
                                    netlistinp.get(i).input.remove(0);
                                    netlistinp.get(i).input.add(noroutW);
                                } else if (inp2flag == 1) {
                                    netlistinp.get(i).input.remove(1);
                                    netlistinp.get(i).input.add(noroutW);
                                }
                                gatestoadd.add(addnor);
                                addgates.add(i);
                            }
                        }

                    }
                }
            }
        }

        //List<DGate> netlistout = new ArrayList<DGate>();
        for (int i = 0; i < netlistinp.size(); i++) {
            if (addgates.contains(i)) {
                int pos = addgates.indexOf(i);
                netlistout.add(gatestoadd.get(pos));
            }
            netlistout.add(netlistinp.get(i));
        }

        List<DGate> finalnetlist;
        //<editor-fold desc="Remove Dangling Wires">
        //do{
        removegates = new ArrayList<Integer>();
        for (int i = 0; i < netlistout.size() - 1; i++) {
            String wireout = netlistout.get(i).output.name;
            if (netlistout.get(i).output.wtype.equals(DWireType.connector)) {
                int inpcount = 0;
                for (int j = i; j < netlistout.size(); j++) {
                    if (i != j) {
                        for (int m = 0; m < netlistout.get(j).input.size(); m++) {
                            if (netlistout.get(j).input.get(m).name.equals(wireout)) {
                                inpcount++;
                            }
                        }
                    }
                }
                if (inpcount == 0) {
                    removegates.add(i);
                }
            }
        }
        finalnetlist = new ArrayList<DGate>();
        for (int i = 0; i < netlistout.size(); i++) {
            if (!removegates.contains(i)) {
                finalnetlist.add(netlistout.get(i));
            }
        }
        //</editor-fold>

        //Start Removing duplicate NOR gates  
        removegates = new ArrayList<Integer>();
        for (int i = 0; i < finalnetlist.size(); i++) {
            if (finalnetlist.get(i).gtype.equals(DGateType.NOR) && (!finalnetlist.get(i).output.wtype.equals(DWireType.output))) {
                String inp1name = finalnetlist.get(i).input.get(0).name.trim();
                String inp2name = finalnetlist.get(i).input.get(1).name.trim();
                DWire noroutW = new DWire(finalnetlist.get(i).output.name, finalnetlist.get(i).output.wtype);
                for (int j = i; j < finalnetlist.size(); j++) {
                    if (i != j) {
                        if (finalnetlist.get(j).gtype.equals(DGateType.NOR) && (!finalnetlist.get(j).output.wtype.equals(DWireType.output))) {
                            String outname = finalnetlist.get(j).output.name.trim();
                            if ((finalnetlist.get(j).input.get(0).name.equals(inp1name) && finalnetlist.get(j).input.get(1).name.equals(inp2name)) || (finalnetlist.get(j).input.get(0).name.equals(inp2name) && finalnetlist.get(j).input.get(1).name.equals(inp1name))) {
                                //System.out.println(noroutW.name);
                                for (int k = j; k < finalnetlist.size(); k++) {
                                    if (k != j) {
                                        for (int m = 0; m < finalnetlist.get(k).input.size(); m++) {
                                            if (finalnetlist.get(k).input.get(m).name.equals(outname)) {
                                                finalnetlist.get(k).input.set(m, noroutW);
                                            }
                                        }
                                    }
                                }
                                if (!removegates.contains(j)) {
                                    removegates.add(j);
                                }
                            }
                        }
                    }
                }

            }
        }

        List<DGate> finalnetout = new ArrayList<DGate>();
        for (int i = 0; i < finalnetlist.size(); i++) {
            if (!removegates.contains(i)) {
                finalnetout.add(finalnetlist.get(i));
            }
        }

        return finalnetout;
    }

    /**
     * Function ************************************************************
     * <br>
     * Synopsis [Remove Double inverters motif]
     * <br>
     * Description []
     * <br>
     * SideEffects []
     * <br>
     * SeeAlso []
     *
     * @param netlistinp
     * @return
     * *********************************************************************
     */
    public static List<DGate> removeDoubleInverters(List<DGate> netlistinp) {
        //Remove Redundant NOT Gates

        List<Integer> removegates = new ArrayList<Integer>();
        for (int i = 0; i < netlistinp.size() - 1; i++) {
            if (netlistinp.get(i).gtype.equals(DGateType.NOT)) //Gate i is a NOT gate
            {
                for (int j = (i + 1); j < netlistinp.size(); j++) // Look at all gates j after Gate i
                {
                    if (netlistinp.get(j).gtype.equals(DGateType.NOT)) {

                        if (netlistinp.get(i).output.name.equals(netlistinp.get(j).input.get(0).name)) // If input of Gate j is the output of Gate i
                        {
                            if (netlistinp.get(j).output.wtype.equals(DWireType.output)) // if output of Gate j is an output for the circuit
                            {

                                if (netlistinp.get(i).input.get(0).wtype.equals(DWireType.input)) // input of Gate i is an input for the circuit
                                {
                                    DWire newW = new DWire(netlistinp.get(i).input.get(0).name, netlistinp.get(i).input.get(0).wtype);
                                    netlistinp.get(j).input.remove(0);
                                    netlistinp.get(j).input.add(newW);

                                    netlistinp.get(j).gtype = DGateType.BUF;

                                    int dntremoveflag = 0;
                                    for (int k = (i + 1); k < netlistinp.size(); k++) {
                                        if ((k != j) && (!netlistinp.get(k).output.wtype.equals(DWireType.output))) {
                                            for (int m = 0; m < netlistinp.get(k).input.size(); m++) {
                                                if (netlistinp.get(k).input.get(m).name.equals(netlistinp.get(j).output.name)) {
                                                    dntremoveflag = 1;
                                                }
                                            }
                                        }
                                    }
                                    if (dntremoveflag == 0) {
                                        if (!removegates.contains(i)) {
                                            int finalflag = 0;
                                            for (int m = i + 1; m < netlistinp.size(); m++) {
                                                if (netlistinp.get(m).gtype.equals(DGateType.NOR)) {
                                                    for (DWire xinp : netlistinp.get(m).input) {
                                                        if (xinp.name.trim().equals(netlistinp.get(i).output.name.trim())) {
                                                            finalflag = 1;
                                                        }
                                                    }
                                                }
                                            }
                                            if (finalflag == 0) {
                                                removegates.add(i);
                                            }
                                            //System.out.println("Remove Gate index:"+i);

                                        }
                                    }
                                } else // input of Gate i is a connecting wire from another gate. 
                                {
                                    String outwName = "";
                                    int inpcount = 0;
                                    int outwindx = 0;
                                    for (int k = 0; k < netlistinp.size(); k++) {
                                        if (netlistinp.get(k).output.name.equals(netlistinp.get(i).input.get(0).name)) {
                                            outwindx = k;
                                            outwName = netlistinp.get(k).output.name;
                                        }
                                    }
                                    for (int k = outwindx; k < netlistinp.size(); k++) {
                                        if (k != i) {
                                            for (int m = 0; m < netlistinp.get(k).input.size(); m++) {
                                                if (netlistinp.get(k).input.get(m).name.equals(outwName)) {
                                                    inpcount++;
                                                }
                                            }
                                        }
                                    }
                                    if (inpcount == 0) {

                                        netlistinp.get(outwindx).output.name = netlistinp.get(j).output.name;
                                        netlistinp.get(outwindx).output.wtype = netlistinp.get(j).output.wtype;

                                        if (!removegates.contains(j)) {

                                            int finalflag = 0;
                                            for (int m = j + 1; m < netlistinp.size(); m++) {
                                                if (netlistinp.get(m).gtype.equals(DGateType.NOR)) {
                                                    for (DWire xinp : netlistinp.get(m).input) {
                                                        if (xinp.name.trim().equals(netlistinp.get(j).output.name.trim())) {
                                                            finalflag = 1;
                                                        }
                                                    }
                                                }
                                            }
                                            if (finalflag == 0) //System.out.println("Remove Gate index:" + j);
                                            {
                                                removegates.add(j);
                                            }
                                        }
                                        if (!removegates.contains(i)) {
                                            int finalflag = 0;
                                            for (int m = i + 1; m < netlistinp.size(); m++) {
                                                if (netlistinp.get(m).gtype.equals(DGateType.NOR)) {
                                                    for (DWire xinp : netlistinp.get(m).input) {
                                                        if (xinp.name.trim().equals(netlistinp.get(i).output.name.trim())) {
                                                            finalflag = 1;
                                                        }
                                                    }
                                                }
                                            }
                                            if (finalflag == 0) //System.out.println("Remove Gate index:"+i);
                                            {
                                                removegates.add(i);
                                            }
                                        }

                                    }
                                }
                            } else // if output of Gate j is a connecting wire for another gate
                            {
                                for (int k = j; k < netlistinp.size(); k++) {
                                    if (k != j) {
                                        for (int m = 0; m < netlistinp.get(k).input.size(); m++) {
                                            if (netlistinp.get(k).input.get(m).name.equals(netlistinp.get(j).output.name)) {
                                                
                                                String wireJin = netlistinp.get(j).output.name;
                                                DWireType wireJtype = netlistinp.get(j).output.wtype;
                                                DWire newWout = new DWire(wireJin, wireJtype);
                                                DWire newWin = new DWire(netlistinp.get(j).input.get(0).name, netlistinp.get(j).input.get(0).wtype);
                                                List<DWire> jinp = new ArrayList<DWire>();
                                                jinp.add(newWin);
                                                DGate jGate = new DGate(netlistinp.get(j).gtype, jinp, newWout);

                                                netlistinp.set(j, jGate);

                                                netlistinp.get(k).input.get(m).name = netlistinp.get(i).input.get(0).name;
                                                netlistinp.get(k).input.get(m).wtype = netlistinp.get(i).input.get(0).wtype;

                                                if (!removegates.contains(j)) {
                                                    int finalflag = 0;
                                                    for (int z = j + 1; z < netlistinp.size(); z++) {
                                                        if (netlistinp.get(z).gtype.equals(DGateType.NOR)) {
                                                            for (DWire xinp : netlistinp.get(z).input) {
                                                                if (xinp.name.trim().equals(netlistinp.get(j).output.name.trim())) {
                                                                    finalflag = 1;
                                                                }
                                                            }
                                                        }
                                                    }
                                                    if (finalflag == 0) //System.out.println("Remove Gate index:"+j);
                                                    {
                                                        removegates.add(j);
                                                    }
                                                }

                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        List<DGate> optnetlist = new ArrayList<DGate>();

        for (int i = 0; i < netlistinp.size(); i++) {
            if (!removegates.contains(i)) {
                //System.out.println("Add:" + netlist(netlistinp.get(i)));
                optnetlist.add(netlistinp.get(i));
            }
        }

        removegates = new ArrayList<Integer>();

        //Remove Dangling Wires ---------------------------------------------------------------------------
        for (int i = 0; i < optnetlist.size() - 1; i++) {
            String wireout = optnetlist.get(i).output.name;
            if (optnetlist.get(i).output.wtype.equals(DWireType.connector)) {
                int inpcount = 0;
                for (int j = i; j < optnetlist.size(); j++) {
                    if (i != j) {
                        for (int m = 0; m < optnetlist.get(j).input.size(); m++) {
                            if (optnetlist.get(j).input.get(m).name.equals(wireout)) {
                                inpcount++;
                            }
                        }
                    }
                }
                if (inpcount == 0) {
                    removegates.add(i);
                }
            }
        }
        List<DGate> finalnetlist = new ArrayList<DGate>();
        for (int i = 0; i < optnetlist.size(); i++) {
            if (!removegates.contains(i)) {
                finalnetlist.add(optnetlist.get(i));
            }
        }

        return finalnetlist;
    }

    /**
     * Function ************************************************************
     * <br>
     * Synopsis [Replace Output_OR]
     * <br>
     * Description []
     * <br>
     * SideEffects []
     * <br>
     * SeeAlso []
     *
     * @param finalnetlist
     * @return
     * *********************************************************************
     */
    public static List<DGate> outputORopt(List<DGate> finalnetlist) {

        List removegates = new ArrayList<Integer>();
        for (int i = 0; i < finalnetlist.size() - 1; i++) {
            if (finalnetlist.get(i).gtype.equals(DGateType.NOR)) {
                for (int j = i + 1; j < finalnetlist.size(); j++) {
                    if (finalnetlist.get(j).gtype.equals(DGateType.NOT) && finalnetlist.get(j).output.wtype.equals(DWireType.output) && (finalnetlist.get(i).output.name.trim().equals(finalnetlist.get(j).input.get(0).name.trim()))) {
                        int inpcount = 0;
                        for (int k = i + 1; k < finalnetlist.size(); k++) {
                            if (k != j) {
                                for (int m = 0; m < finalnetlist.get(k).input.size(); m++) {
                                    if (finalnetlist.get(i).output.name.equals(finalnetlist.get(k).input.get(m).name)) {
                                        inpcount++;
                                    }
                                }
                            }
                        }
                        if (inpcount == 0) {
                            removegates.add(j);
                            finalnetlist.get(i).gtype = DGateType.OR;
                            finalnetlist.get(i).output.name = finalnetlist.get(j).output.name;
                            finalnetlist.get(i).output.wtype = finalnetlist.get(j).output.wtype;
                        }
                    }
                }
            }

        }
        List<DGate> outputornetlist = new ArrayList<DGate>();
        for (int i = 0; i < finalnetlist.size(); i++) {
            if (!removegates.contains(i)) {
                outputornetlist.add(finalnetlist.get(i));
            }
        }

        return outputornetlist;
    }

    public static List<DGate> removeDuplicateLogicGate(List<DGate> inpnetlist) {
        List<DGate> outnetlist = new ArrayList<>();
        int outCount = 0;
        int firstInstance = 0;
        String outName = "";
        //back here
        for (int i = 0; i < inpnetlist.size(); i++) {
            outCount = 0;
            String logic1 = inpnetlist.get(i).output.logicValue;
            for (int j = i + 1; j < inpnetlist.size(); j++) {
                String logic2 = inpnetlist.get(j).output.logicValue;
                if (logic1.equals(logic2)) {
                    
                    if (inpnetlist.get(i).output.wtype.equals(DWireType.output)) {
                        for (DGate xgate : inpnetlist) {
                            for (int k = 0; k < xgate.input.size(); k++) {
                                if (xgate.input.get(k).name.equals(inpnetlist.get(j).output.name) && xgate.input.get(k).wtype.equals(DWireType.connector)) {
                                    xgate.input.get(k).name = inpnetlist.get(i).output.name;
                                    xgate.input.get(k).wtype = inpnetlist.get(i).output.wtype;
                                }
                            }
                        }
                    } else if (inpnetlist.get(i).output.wtype.equals(DWireType.connector)) {
                        inpnetlist.get(i).output.name = inpnetlist.get(j).output.name;
                        inpnetlist.get(i).output.wtype = inpnetlist.get(j).output.wtype;
                        for (DGate xgate : inpnetlist) {
                            for (int k = 0; k < xgate.input.size(); k++) {
                                if (xgate.input.get(k).name.equals(inpnetlist.get(j).output.name) && xgate.input.get(k).wtype.equals(DWireType.connector)) {
                                    xgate.input.get(k).name = inpnetlist.get(i).output.name;
                                    xgate.input.get(k).wtype = inpnetlist.get(i).output.wtype;
                                }
                            }
                        }
                    }
                }
            }
        }
        outnetlist = removeDuplicateGates(inpnetlist);
        outnetlist = removeDanglingGates(outnetlist);

        return outnetlist;

    }

    public static List<DGate> removeDuplicateGates(List<DGate> inpnetlist) {
        List<DGate> outnetlist = new ArrayList<>();
        List<Integer> removeIndex = new ArrayList<>();
        for (int i = 0; i < inpnetlist.size(); i++) {
            for (int j = i + 1; j < inpnetlist.size(); j++) {
                if (inpnetlist.get(i).output.name.equals(inpnetlist.get(j).output.name)) {
                    if (!removeIndex.contains(j)) {
                        removeIndex.add(j);
                    }
                }
            }
        }
        for (int i = 0; i < inpnetlist.size(); i++) {
            if (!removeIndex.contains(i)) {
                outnetlist.add(inpnetlist.get(i));
            }
        }
        return outnetlist;
    }

    /**
     * Function ************************************************************
     * <br>
     * Synopsis [Removes Gates where the output is not of type DWireType.Output
     * and the inputs are not outputs of any other Gate in the circuit]
     * <br>
     * Description []
     * <br>
     * SideEffects []
     * <br>
     * SeeAlso []
     *
     * @param netlist Input netlist
     * @return
     * *********************************************************************
     */
    public static List<DGate> removeDanglingGates(List<DGate> netlist) {
        List<DGate> tempnetlist = new ArrayList<DGate>();
        for (int i = 0; i < netlist.size(); i++) {
            tempnetlist.add(netlist.get(i));
        }
        List<DGate> reducednetlist = new ArrayList<DGate>();
        List<Integer> removegates = new ArrayList<Integer>();
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
            for (int i = 0; i < reducednetlist.size(); i++) {
                tempnetlist.add(reducednetlist.get(i));
            }
        } while (!removegates.isEmpty());

        return reducednetlist;
    }

    public static void printDebugStatement(String message) {
        System.out.println("=======================");
        System.out.println("======= " + message);
        System.out.println("=======================");
    }

    public static List<DGate> convertNaiveToNORNOT(List<DGate> netlist){
        List<DGate> output = new ArrayList<DGate>();
        
        netlist = removeDanglingGates(netlist);
        //System.out.println("------------------------------------");
        List<DGate> reducedfanin = new ArrayList<DGate>();

        for (int i = 0; i < netlist.size(); i++) {
            for (DGate redgate : NetlistConversionFunctions.ConvertToFanin2(netlist.get(i))) {
                reducedfanin.add(redgate);
            }
        }
        for (int i = 0; i < reducedfanin.size(); i++) {
            for (DGate structgate : NetlistConversionFunctions.GatetoNORNOT(reducedfanin.get(i))) {
                output.add(structgate);
            }
        }

        output = optimize(output);

        return output;
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
     * @param inpNetlist
     * @return
     * *********************************************************************
     */
    public static List<DGate> removeDuplicateNots(List<DGate> inpNetlist) {
        List<DGate> outpNetlist = new ArrayList<DGate>();
        List<Integer> removelist = new ArrayList<Integer>();
        for (int i = 0; i < inpNetlist.size(); i++) {
            if (inpNetlist.get(i).gtype.equals(DGateType.NOT) && (!inpNetlist.get(i).output.wtype.equals(DWireType.output))) {
                String inpname = inpNetlist.get(i).input.get(0).name.trim();
                String setoutname = inpNetlist.get(i).output.name.trim();
                for (int j = i + 1; j < inpNetlist.size(); j++) {
                    if (inpNetlist.get(j).gtype.equals(DGateType.NOT) && (!inpNetlist.get(j).output.wtype.equals(DWireType.output)) && inpNetlist.get(j).input.get(0).name.trim().equals(inpname)) {
                        removelist.add(j);
                        String outpname = inpNetlist.get(j).output.name.trim();
                        for (int k = j + 1; k < inpNetlist.size(); k++) {
                            for (int m = 0; m < inpNetlist.get(k).input.size(); m++) {
                                if (inpNetlist.get(k).input.get(m).name.trim().equals(outpname)) {
                                    inpNetlist.get(k).input.get(m).name = setoutname;
                                }
                            }
                        }
                        inpNetlist.get(j).output.name = setoutname;
                    }
                }
            }
        }
        //System.out.println(removelist.size());
        for (int i = 0; i < inpNetlist.size(); i++) {
            if (!removelist.contains(i)) {
                outpNetlist.add(inpNetlist.get(i));
            }
        }

        return outpNetlist;
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
     * @param netlistinp
     * @return
     * *********************************************************************
     */
    public static int NOR3count(List<DGate> netlistinp) {
        int count = 0;
        for (int i = 0; i < netlistinp.size(); i++) {
            if (netlistinp.get(i).gtype.equals(DGateType.NOR) && (netlistinp.get(i).input.size() == 3)) {
                count++;
            }
        }
        return count;
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
     * @param inpNetlist
     * @param outputor
     * @param twoNotsToNor
     * @return
     * *********************************************************************
     */
    public static List<DGate> optimizeNetlist(List<DGate> inpNetlist, boolean outputor, boolean twoNotsToNor, boolean nor3) {

        List<DGate> outpNetlist = new ArrayList<DGate>();

        outpNetlist = removeDanglingGates(inpNetlist);

        outpNetlist = separateOutputGates(outpNetlist);

        outpNetlist = removeDuplicateNots(outpNetlist);
        outpNetlist = removeDoubleInverters(outpNetlist);
        outpNetlist = removeDuplicateNots(outpNetlist);

        outpNetlist = removeDanglingGates(outpNetlist);

        if (outputor) {
            outpNetlist = outputORopt(outpNetlist); // Need to change this for multiple Outputs.
        }
        if (twoNotsToNor) {
            outpNetlist = convert2NOTsToNOR(outpNetlist);
        }

        if (nor3) {
            outpNetlist = convertToNOR3(outpNetlist);
        }

        outpNetlist = removeDanglingGates(outpNetlist);

        outpNetlist = rewireNetlist(outpNetlist);

        return outpNetlist;
    }

    
    public static List<DGate> optimize(List<DGate> netlist){
        
        List<DGate> output = new ArrayList<DGate>();
        
        output = removeDanglingGates(netlist);
        output = rewireNetlist(output);
        
        output = separateOutputGates(output);
        output = rewireNetlist(output);
        
        //What is going on here???? Need to check this.
        output = removeDuplicateNots(output);
        output = removeDoubleInverters(output);
        output = removeDuplicateNots(output);
        output = rewireNetlist(output);
        
        
        output = removeDanglingGates(output);
        output = rewireNetlist(output);
        
        
        List<String> inputNames = new ArrayList<String>();
        for(DGate gate:output){
            for(DWire wire:gate.input){
                if(wire.wtype.equals(DWireType.input)){
                    if(!inputNames.contains(wire.name)){
                        inputNames.add(wire.name);
                    }
                }
            }
        }
        
        output = NetSynth.assignWireLogic(inputNames, output);
        output = removeDuplicateLogicGate(output);
        output = rewireNetlist(output);
        
        
        output = removeDanglingGates(output);
        output = rewireNetlist(output);
        
        return output;
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
     * @return
     * *********************************************************************
     */
    public static List<DGate> parseEspressoToNORNAND(List<String> espinp) {

        one = new DWire("_one", DWireType.Source);
        zero = new DWire("_zero", DWireType.GND);

        for (String lists : espinp) {
            System.out.println(lists);
        }

        List<DGate> sopexp = new ArrayList<DGate>();
        List<DWire> wireInputs = new ArrayList<DWire>();
        List<DWire> wireOutputs = new ArrayList<DWire>();
        List<DWire> invWires = new ArrayList<DWire>();
        List<DGate> inpInv = new ArrayList<DGate>();
        List<Boolean> notGateexists = new ArrayList<Boolean>();
        List<Boolean> notGateAdd = new ArrayList<Boolean>();

        functionOutp = false;
        String inpNames = "";
        String outNames = "";
        POSmode = false;
        int numberOfMinterms = 0;
        int expInd = 0;

        // <editor-fold defaultstate="collapsed" desc="Extract Input output and minterm lines from Espresso Output">
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
        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Get Input Names">
        for (String splitInp : inpNames.split(" ")) {
            if (splitInp.equals(one.name) || splitInp.equals(zero.name)) {
                splitInp += "I";
            }
            wireInputs.add(new DWire(splitInp, DWireType.input));
            //System.out.println("INPUT : "+splitInp);
        }
        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Create Not gates and NOT wires">
        inpInv = notGates(wireInputs);

        for (DGate gnots : inpInv) {
            notGateexists.add(false);
            notGateAdd.add(false);
            invWires.add(gnots.output);
        }
        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Get Output Names">
        for (String splitInp : outNames.split(" ")) {
            if (splitInp.equals(one.name) || splitInp.equals(zero.name)) {
                splitInp += "O";
            }
            wireOutputs.add(new DWire(splitInp, DWireType.output));
            //System.out.println("OUTPUT : "+splitInp);
        }
        // </editor-fold>        

        // <editor-fold defaultstate="collapsed" desc="Minterms = 0 and Output = 1 or 0">
        if (numberOfMinterms == 0) {
            functionOutp = true;
            List<DWire> inp01 = new ArrayList<DWire>();

            if (POSmode) {
                inp01.add(one);
            } else {
                inp01.add(zero);
            }
            sopexp.add(new DGate(DGateType.BUF, inp01, wireOutputs.get(0)));
            return sopexp;
        } // </editor-fold>
        // <editor-fold defaultstate="collapsed" desc="Minterm = 1 and Output = 1 or 0">
        else if (numberOfMinterms == 1) {
            String oneMinT = espinp.get(expInd).substring(0, (wireInputs.size()));
            int flag = 0;
            for (int j = 0; j < wireInputs.size(); j++) {
                if (oneMinT.charAt(j) != '-') {
                    flag = 1;
                    break;
                }
            }
            if (flag == 0) {
                functionOutp = true;
                List<DWire> inp01 = new ArrayList<DWire>();

                if (POSmode) {
                    inp01.add(zero);
                } else {
                    inp01.add(one);
                }
                sopexp.add(new DGate(DGateType.BUF, inp01, wireOutputs.get(0)));
                return sopexp;
            }

        }
        // </editor-fold>

        List<DWire> minTemp = new ArrayList<DWire>();
        List<DWire> orWires = new ArrayList<DWire>();
        List<DGate> prodGates;
        for (int i = expInd; i < (expInd + numberOfMinterms); i++) {

            String minT = espinp.get(i).substring(0, (wireInputs.size()));
            prodGates = new ArrayList<DGate>();
            minTemp = new ArrayList<DWire>();

            // <editor-fold defaultstate="collapsed" desc="Find a Minterm/Maxterm">
            for (int j = 0; j < wireInputs.size(); j++) {
                if (minT.charAt(j) == '-') {
                    continue;
                } else if (minT.charAt(j) == '0') {
                    if (POSmode) {
                        minTemp.add(wireInputs.get(j));
                    } else {
                        if (!notGateexists.get(j)) {
                            sopexp.add(inpInv.get(j));
                            notGateAdd.set(j, true);
                            notGateexists.set(j, true);
                        }
                        minTemp.add(inpInv.get(j).output);
                    }
                } else if (minT.charAt(j) == '1') {
                    if (POSmode) {
                        if (!notGateexists.get(j)) {
                            sopexp.add(inpInv.get(j));
                            notGateAdd.set(j, true);
                            notGateexists.set(j, true);
                        }
                        minTemp.add(inpInv.get(j).output);
                    } else {
                        minTemp.add(wireInputs.get(j));
                    }
                }
            }
            // </editor-fold>

            if (minTemp.size() == 1) {
                if (POSmode) {
                    if (numberOfMinterms != 1) {
                        if (wireInputs.contains(minTemp.get(0))) {
                            int xInd = wireInputs.indexOf(minTemp.get(0));
                            if (!notGateexists.get(xInd)) {
                                sopexp.add(inpInv.get(xInd));
                                notGateAdd.set(xInd, true);
                                notGateexists.set(xInd, true);
                            }
                            DWire tempmin = inpInv.get(xInd).output;
                            minTemp.remove(0);
                            minTemp.add(tempmin);
                        } else {
                            int xInd = invWires.indexOf(minTemp.get(0));
                            if (notGateAdd.get(xInd)) {
                                sopexp.remove(inpInv.get(xInd));
                                notGateAdd.set(xInd, false);
                                notGateexists.set(xInd, false);
                            }
                            DWire tempmin = wireInputs.get(xInd);
                            minTemp.remove(0);
                            minTemp.add(tempmin);
                        }
                    }
                    orWires.add(minTemp.get(0));
                    //if(notGateAdd.get(i))
                } else {
                    orWires.add(minTemp.get(0));
                }

            } else {
                if (POSmode) {
                    prodGates = NORNANDGates(minTemp, DGateType.NOR);
                } else {
                    prodGates = NORNANDGates(minTemp, DGateType.NAND);
                }
                orWires.add(prodGates.get(prodGates.size() - 1).output);
                sopexp.addAll(prodGates);
            }
        }
        boolean no2ndstageGate = false;
        prodGates = new ArrayList<DGate>();
        if (POSmode) {
            prodGates = NORNANDGates(orWires, DGateType.NOR);

            if (prodGates.isEmpty()) {
                no2ndstageGate = true;
            }
        } else {
            prodGates = NORNANDGates(orWires, DGateType.NAND);
        }
        sopexp.addAll(prodGates);
        if (sopexp.isEmpty()) {
            DGate bufgate = new DGate(DGateType.BUF, orWires, wireOutputs.get(0));
            sopexp.add(bufgate);
        } else {
            if (no2ndstageGate) {
                if (!((numberOfMinterms == 1) && (minTemp.size() == 1))) {
                    String Wirename = "0Wire" + Global.wirecount++;
                    DWire aout = new DWire(Wirename);
                    List<DWire> notfinalinp = new ArrayList<DWire>();
                    notfinalinp.add(sopexp.get(sopexp.size() - 1).output);
                    DGate notfinal = new DGate(DGateType.NOT, notfinalinp, aout);
                    sopexp.add(notfinal);
                }
            }
            sopexp.get(sopexp.size() - 1).output = wireOutputs.get(0);

        }
        return sopexp;
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
    public static List<DGate> notGates(List<DWire> inpWires) {
        List<DGate> notInp = new ArrayList<DGate>();
        for (DWire xWire : inpWires) {
            String Wirename = "0Wire" + Global.wirecount++;
            DWire aout = new DWire(Wirename);
            List<DWire> inpNot = new ArrayList<DWire>();
            inpNot.add(xWire);
            notInp.add(new DGate(DGateType.NOT, inpNot, aout));
        }
        return notInp;
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
    public static List<DGate> NORNANDGates(List<DWire> inpWires, DGateType gtype) {
        if (inpWires.isEmpty()) {
            return null;
        }
        List<DGate> minterm = new ArrayList<DGate>();

        List<DWire> nextLevelWires = new ArrayList<DWire>();
        List<DWire> temp = new ArrayList<DWire>();

        int wireCount, indx;
        nextLevelWires.addAll(inpWires);
        wireCount = inpWires.size();

        while (wireCount > 1) {
            temp = new ArrayList<DWire>();
            temp.addAll(nextLevelWires);
            nextLevelWires = new ArrayList<DWire>();
            indx = 0;
            while ((indx + 2) <= (wireCount)) {

                List<DWire> ainp = new ArrayList<DWire>();
                ainp.add(temp.get(indx));
                ainp.add(temp.get(indx + 1));
                String Wirename = "0Wire" + Global.wirecount++;

                DWire aout = new DWire(Wirename);
                DGate andG = new DGate(gtype, ainp, aout);
                minterm.add(andG);
                if (wireCount > 2) {
                    Wirename = "0Wire" + Global.wirecount++;
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
                nextLevelWires.add(temp.get(wireCount - 1));
            }
            wireCount = nextLevelWires.size();
            if (wireCount == 2) {
                List<DWire> ainp = new ArrayList<DWire>();
                ainp.add(nextLevelWires.get(0));
                ainp.add(nextLevelWires.get(1));
                String Wirename = "0Wire" + Global.wirecount++;
                DWire aout = new DWire(Wirename);
                DGate andG = new DGate(gtype, ainp, aout);
                minterm.add(andG);
                break;
            }
        }
        return minterm;
    }

    /**
     * *************************************************************Function
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
     * @param gOrAnd
     * @return
     * *********************************************************************
     */
    public static List<DGate> AndORGates(List<DWire> inpWires, DGateType gOrAnd) {
        if (inpWires.isEmpty()) {
            return null;
        }
        List<DGate> minterm = new ArrayList<DGate>();

        List<DWire> nextLevelWires = new ArrayList<DWire>();
        List<DWire> temp = new ArrayList<DWire>();

        int wireCount, indx;
        nextLevelWires.addAll(inpWires);
        wireCount = inpWires.size();

        while (wireCount > 1) {
            temp = new ArrayList<DWire>();
            temp.addAll(nextLevelWires);
            nextLevelWires = new ArrayList<DWire>();
            indx = 0;
            while ((indx + 2) <= (wireCount)) {

                List<DWire> ainp = new ArrayList<DWire>();
                ainp.add(temp.get(indx));
                ainp.add(temp.get(indx + 1));
                String Wirename = "0Wire" + Global.wirecount++;

                DWire aout = new DWire(Wirename);
                nextLevelWires.add(aout);
                DGate andG = new DGate(gOrAnd, ainp, aout);
                minterm.add(andG);
                indx += 2;

            }
            if (temp.size() % 2 != 0) {
                nextLevelWires.add(temp.get(wireCount - 1));
            }
            wireCount = nextLevelWires.size();
            if (wireCount == 2) {
                List<DWire> ainp = new ArrayList<DWire>();
                ainp.add(nextLevelWires.get(0));
                ainp.add(nextLevelWires.get(1));
                String Wirename = "0Wire" + Global.wirecount++;
                DWire aout = new DWire(Wirename);
                DGate andG = new DGate(gOrAnd, ainp, aout);
                minterm.add(andG);
                break;
            }
        }
        return minterm;
    }

    /**
     * *************************************************************Function
     * <br>
     * Synopsis [Netlist to Directed Acyclic Graph]
     * <br>
     * Description [Converts a Netlist into a Directed Acyclic Graph with
     * multiple outputs]
     * <br>
     * SideEffects []
     * <br>
     * SeeAlso []
     *
     * @param netlist Input Netlist
     * @return Directed Acyclic Graph with multiple outputs
     * *********************************************************************
     */
    public static DAGW CreateMultDAGW(List<DGate> netlist) {
        DAGW outDAG = new DAGW();
        List<DWire> inplist = new ArrayList<DWire>();
        List<Gate> Gates = new ArrayList<Gate>();
        List<Wire> Wires = new ArrayList<Wire>();
        HashMap<DGate, Gate> vertexhash = new HashMap<DGate, Gate>();
        int IndX = 0;
        int outpIndx = 0;
        
        //<editor-fold desc="Single Gate in netlist of type Buffer" > 
        if (netlist.size() == 1 && netlist.get(0).gtype == DGateType.BUF) // This step is incase there is a single gate in the netlist and it is of type Buffer
        {
            Gate outb = new Gate();
            Gate inpb = new Gate();

            outb.Type = GateType.OUTPUT;
            outb.Name = netlist.get(0).output.name;
            outb.Index = 0;

            inpb.Index = 1;
            inpb.Name = netlist.get(0).input.get(0).name;
            inpb.Type = GateType.INPUT;

            Wire edge = new Wire();

            edge.From = outb;
            edge.To = inpb;
            edge.Index = 0;
            edge.Next = null;

            outb.Outgoing = edge;
            inpb.Outgoing = null;

            outDAG.Gates.add(outb);
            outDAG.Gates.add(inpb);
            outDAG.Wires.add(edge);
            return outDAG;

        }
        //</editor-fold>

        //<editor-fold  desc="Replace every Nor gate with one input as Ground, with a NOT gate">
        for (int i = netlist.size() - 1; i >= 0; i--) // This step is for a NOR Gate that has a Zero as an input. Replace it with a simple NOT gate. 
        {
            DGate netg = netlist.get(i);
            if (netg.input.contains(zero)) {
                DGate tempNot = new DGate();
                for (DWire xi : netg.input) {
                    if (!(xi.equals(zero))) {
                        tempNot.input.add(xi);
                    }
                    tempNot.output = netg.output;
                    tempNot.gtype = DGateType.NOT;
                }
                netlist.get(i).gtype = tempNot.gtype;
                netlist.get(i).input = tempNot.input;
                netlist.get(i).output = tempNot.output;
            }
        }
        //</editor-fold>

        List<String> inputwires = new ArrayList<String>();
        List<String> outputwires = new ArrayList<String>();
        List<String> outputORwires = new ArrayList<String>();

        for (DGate xgate : netlist) {
            if (xgate.output.wtype.equals(DWireType.output)) {
                outputwires.add(xgate.output.name.trim());
                if (xgate.gtype.equals(DGateType.OUTPUT_OR)) {
                    outputORwires.add(xgate.output.name.trim());
                }
            }
            for (DWire xinp : xgate.input) {
                if (xinp.wtype.equals(DWireType.input)) {
                    if (!inputwires.contains(xinp.name.trim())) {
                        inputwires.add(xinp.name.trim());
                    }
                }

            }
        }
        int indx = 0;
        for (int i = 0; i < outputwires.size(); i++) {
            Gate outg = new Gate();
            outg.Name = outputwires.get(i);
            if (outputORwires.contains(outputwires.get(i))) {
                outg.Type = GateType.OUTPUT_OR;
                outg.outW = new DWire(outputwires.get(i), DWireType.output);
            } else {
                outg.Type = GateType.OUTPUT;
            }

            outg.Index = indx;
            indx++;
            Gates.add(outg);
        }
        for (int i = netlist.size() - 1; i >= 0; i--) {
            DGate netg = netlist.get(i);
            if (netg.gtype.equals(DGateType.NOR)) {
                Gate norg = new Gate(indx, GateType.NOR);
                norg.outW = netg.output;

                indx++;
                Gates.add(norg);
            } else if (netg.gtype.equals(DGateType.AND)) {
                Gate norg = new Gate(indx, GateType.AND);
                norg.outW = netg.output;

                indx++;
                Gates.add(norg);
            } else if (netg.gtype.equals(DGateType.OR) && (!netg.output.wtype.equals(DWireType.output))) {
                Gate norg = new Gate(indx, GateType.OR);
                norg.outW = netg.output;

                indx++;
                Gates.add(norg);
            } else if (netg.gtype.equals(DGateType.NOT)) {
                Gate notg = new Gate(indx, GateType.NOT);
                notg.outW = netg.output;
                indx++;
                Gates.add(notg);
            }
        }
        for (int i = 0; i < inputwires.size(); i++) {
            Gate ing = new Gate();
            ing.Name = inputwires.get(i);
            ing.Type = GateType.INPUT;
            ing.Index = indx;
            indx++;
            Gates.add(ing);
        }
        int windx = 0;
        for (int i = netlist.size() - 1; i >= 0; i--) {
            DGate netg = netlist.get(i);
            if (netg.gtype.equals(DGateType.BUF)) {
                Wire bufwire = new Wire();
                bufwire.Index = windx;
                windx++;
                bufwire.Next = null;
                int fromindx = 0;
                for (Gate gout : Gates) {
                    if (gout.Name.trim().equals(netg.output.name.trim())) {
                        bufwire.From = gout;
                        fromindx = gout.Index;
                    }
                    if (gout.Name.trim().equals(netg.input.get(0).name.trim())) {
                        bufwire.To = gout;
                    }
                }
                for (Gate gout : Gates) {
                    if (gout.Index == fromindx) {
                        gout.Outgoing = bufwire;
                    }
                }
                Wires.add(bufwire);
            } else {
                Wire temp = null;
                if (netg.output.wtype.equals(DWireType.output) && !netg.gtype.equals(DGateType.OR)) {
                    Wire outpwire = new Wire();
                    int fromindx = 0;
                    outpwire.Index = windx;
                    windx++;
                    outpwire.Next = null;
                    for (Gate gout : Gates) {
                        if (gout.Name.trim().equals(netg.output.name.trim())) {
                            outpwire.From = gout;
                            fromindx = gout.Index;

                        }
                        if (gout.outW.name.trim().equals(netg.output.name.trim())) {
                            outpwire.To = gout;
                        }
                    }
                    for (Gate gout : Gates) {
                        if (gout.Index == fromindx) {
                            gout.Outgoing = outpwire;

                        }
                    }
                    Wires.add(outpwire);
                    temp = null;
                    //for(DWire gwireinp:netg.input)
                    for (int j = netg.input.size() - 1; j >= 0; j--) {
                        DWire gwireinp = netg.input.get(j);
                        Wire connwire = new Wire();
                        connwire.Index = windx;
                        windx++;
                        int gfromindx = 0;
                        if (gwireinp.wtype.equals(DWireType.input)) {
                            for (Gate xgate : Gates) {
                                if (xgate.Name.trim().equals(gwireinp.name.trim())) {
                                    connwire.To = xgate;
                                }
                                if (xgate.outW.name.trim().equals(netg.output.name.trim())) {
                                    connwire.From = xgate;
                                    gfromindx = xgate.Index;
                                }
                            }
                            connwire.Next = temp;

                            if (j == 0) {
                                for (Gate xgate : Gates) {
                                    if (xgate.Index == gfromindx) {
                                        xgate.Outgoing = connwire;
                                    }
                                }
                            }
                            Wires.add(connwire);
                            temp = connwire;
                        } else {
                            for (Gate xgate : Gates) {
                                if (gwireinp.name.trim().equals(xgate.outW.name.trim())) {
                                    connwire.To = xgate;
                                }
                                if (netg.output.name.trim().equals(xgate.outW.name.trim())) {
                                    connwire.From = xgate;
                                    gfromindx = xgate.Index;
                                }
                            }
                            connwire.Next = temp;
                            if (j == 0) {
                                for (Gate xgate : Gates) {
                                    if (xgate.Index == gfromindx) {
                                        xgate.Outgoing = connwire;
                                    }
                                }
                            }
                            Wires.add(connwire);
                            temp = connwire;
                        }
                    }
                } else {
                    temp = null;
                    //for(DWire gwireinp:netg.input)
                    for (int j = netg.input.size() - 1; j >= 0; j--) {
                        DWire gwireinp = netg.input.get(j);
                        Wire connwire = new Wire();
                        connwire.Index = windx;
                        windx++;
                        int gfromindx = 0;
                        if (gwireinp.wtype.equals(DWireType.input)) {
                            for (Gate xgate : Gates) {
                                if (xgate.Name.trim().equals(gwireinp.name.trim())) {
                                    connwire.To = xgate;
                                }
                                if (xgate.outW.name.trim().equals(netg.output.name.trim())) {
                                    connwire.From = xgate;
                                    gfromindx = xgate.Index;
                                }
                            }
                            connwire.Next = temp;

                            if (j == 0) {
                                for (Gate xgate : Gates) {
                                    if (xgate.Index == gfromindx) {
                                        xgate.Outgoing = connwire;
                                    }
                                }
                            }
                            Wires.add(connwire);
                            temp = connwire;
                        } else {
                            for (Gate xgate : Gates) {
                                if (gwireinp.name.trim().equals(xgate.outW.name.trim())) {
                                    connwire.To = xgate;
                                }
                                if (netg.output.name.trim().equals(xgate.outW.name.trim())) {
                                    connwire.From = xgate;
                                    gfromindx = xgate.Index;
                                }
                            }
                            connwire.Next = temp;

                            if (j == 0) {
                                for (Gate xgate : Gates) {
                                    if (xgate.Index == gfromindx) {
                                        xgate.Outgoing = connwire;
                                    }
                                }
                            }
                            Wires.add(connwire);
                            temp = connwire;
                        }
                    }
                }
            }
        }

        for (Wire xdedge : Wires) {
            outDAG.Wires.add(new Wire(xdedge));
        }
        for (Gate xdvert : Gates) {
            outDAG.Gates.add(new Gate(xdvert));
        }
        //DAGW outputDAG = new DAGW(outDAG.Gates,outDAG.Wires);
        return outDAG;
    }

    public static boolean equalLogicInputs(List<List<String>> inplogic) {
        boolean equal = true;

        List<List<String>> inpcopy = new ArrayList<List<String>>();
        for (int i = 0; i < inplogic.size(); i++) {
            inpcopy.add(new ArrayList<String>(inplogic.get(i)));
        }
        for (int i = 0; i < inpcopy.size(); i++) {
            Collections.sort(inpcopy.get(i));
        }

        for (int i = 0; i < inpcopy.size() - 1; i++) {
            if (!inpcopy.get(i).equals(inpcopy.get(i + 1))) {
                equal = false;
                return equal;
            }
        }
        return equal;
    }

    public static List<DGate> assignWireLogic(List<String> inputNames, List<DGate> netlist) {
        int pow = (int) Math.pow(2, inputNames.size());
        for(DGate gate:netlist){
            gate.output.logicValue = "";
        }
        for (int i = 0; i < pow; i++) {
            String inputBool = Convert.dectoBin(i, inputNames.size());
            Map<String, Character> inputVals = new HashMap<String, Character>();
            for (int j = 0; j < inputNames.size(); j++) {
                inputVals.put(inputNames.get(j), inputBool.charAt(j));
            }
            for (int j = 0; j < netlist.size(); j++) {
                for (int k = 0; k < netlist.get(j).input.size(); k++) {
                    if (netlist.get(j).input.get(k).wtype.equals(DWireType.input)) {
                        netlist.get(j).input.get(k).logicValue += inputVals.get(netlist.get(j).input.get(k).name);
                        if (inputVals.get(netlist.get(j).input.get(k).name).equals('1')) {
                            netlist.get(j).input.get(k).wValue = DWireValue._1;
                        } else if (inputVals.get(netlist.get(j).input.get(k).name).equals('0')) {
                            netlist.get(j).input.get(k).wValue = DWireValue._0;
                        }
                    }
                }

                BooleanSimulator.bfunction(netlist.get(j));
                if (netlist.get(j).output.wValue.equals(DWireValue._0)) {
                    netlist.get(j).output.logicValue += '0';
                } else if (netlist.get(j).output.wValue.equals(DWireValue._1)) {
                    netlist.get(j).output.logicValue += '1';
                } else {
                    netlist.get(j).output.logicValue += 'X';
                }
            }
        }

        //for (int i = 0; i < netlist.size(); i++) {
        //    System.out.println(netlist.get(i).output.logicValue + " :" + printGate(netlist.get(i)));
        //}

        return netlist;
    }

    /**
     * *************************************************************Function
     * <br>
     * Synopsis [Netlist to Directed Acyclic Graph]
     * <br>
     * Description [Converts a netlist into a Directed Acyclic Graph. DGates are
     * Vertices and DWires are Arcs]
     * <br>
     * SideEffects []
     * <br>
     * SeeAlso []
     * <br>
     *
     * @param netlist Input netlist
     * @return A Directed Acyclic Graph
     * *********************************************************************
     */
    public static DAGW CreateDAGW(List<DGate> netlist) {
        DAGW outDAG = new DAGW();
        List<DWire> inplist = new ArrayList<DWire>();
        List<Gate> Gates = new ArrayList<Gate>();
        List<Wire> Wires = new ArrayList<Wire>();
        HashMap<DGate, Gate> vertexhash = new HashMap<DGate, Gate>();
        int IndX = 0;
        int outpIndx = 0;
        
        //<editor-fold desc="Single Gate in netlist of type Buffer" > 
        if (netlist.size() == 1 && netlist.get(0).gtype == DGateType.BUF) // This step is incase there is a single gate in the netlist and it is of type Buffer
        {
            Gate outb = new Gate();
            Gate inpb = new Gate();

            outb.Type = GateType.OUTPUT;
            outb.Name = netlist.get(0).output.name;
            outb.Index = 0;

            inpb.Index = 1;
            inpb.Name = netlist.get(0).input.get(0).name;
            inpb.Type = GateType.INPUT;

            Wire edge = new Wire();

            edge.From = outb;
            edge.To = inpb;
            edge.Index = 0;
            edge.Next = null;

            outb.Outgoing = edge;
            inpb.Outgoing = null;

            outDAG.Gates.add(outb);
            outDAG.Gates.add(inpb);
            outDAG.Wires.add(edge);
            return outDAG;

        }
        //</editor-fold>

        //<editor-fold  desc="Replace every Nor gate with one input as Ground, with a NOT gate">
        for (int i = netlist.size() - 1; i >= 0; i--) // This step is for a NOR Gate that has a Zero as an input. Replace it with a simple NOT gate. 
        {
            DGate netg = netlist.get(i);
            if (netg.input.contains(zero)) {
                DGate tempNot = new DGate();
                for (DWire xi : netg.input) {
                    if (!(xi.equals(zero))) {
                        tempNot.input.add(xi);
                    }
                    tempNot.output = netg.output;
                    tempNot.gtype = DGateType.NOT;
                }
                netlist.get(i).gtype = tempNot.gtype;
                netlist.get(i).input = tempNot.input;
                netlist.get(i).output = tempNot.output;
            }
        }
        //</editor-fold>

        for (int i = netlist.size() - 1; i >= 0; i--) {
            DGate netg = netlist.get(i);

            for (DWire xi : netg.input) {
                if ((xi.wtype == DWireType.input) && (!(inplist.contains(xi)))) {
                    int flag = 0;
                    for (DWire di : inplist) {
                        if (di.name.trim().equals(xi.name.trim())) {
                            flag = 1;
                            break;
                        }
                    }
                    if (flag == 0) {
                        inplist.add(xi);
                    }

                }
            }

            if (netg.output.wtype == DWireType.output) {
                if (netg.gtype == DGateType.OUTPUT_OR) {
                    Gate outor = null;
                    outpIndx = IndX;
                    outor = new Gate(IndX++, GateType.OUTPUT_OR);
                    outor.Name = netg.output.name;
                    vertexhash.put(netg, outor);
                    Gates.add(outor);
                } else {
                    Gate out = null;
                    Gate lvert = null;
                    if (netg.gtype == DGateType.NOT) {
                        outpIndx = IndX;
                        out = new Gate(IndX++, GateType.OUTPUT);
                        lvert = new Gate(IndX++, GateType.NOT);
                        vertexhash.put(netg, lvert);
                    } else if (netg.gtype == DGateType.NOR) {
                        outpIndx = IndX;
                        out = new Gate(IndX++, GateType.OUTPUT);
                        lvert = new Gate(IndX++, GateType.NOR);
                        vertexhash.put(netg, lvert);
                    }
                    out.Name = netg.output.name;
                    lvert.outW = netg.output;

                    Gates.add(out);
                    Gates.add(lvert);
                }
                //System.out.println(IndX);

            } else {
                Gate vert = null;
                if (netg.gtype == DGateType.NOT) {
                    vert = new Gate(IndX++, GateType.NOT);
                } else if (netg.gtype == DGateType.NOR) {
                    vert = new Gate(IndX++, GateType.NOR);
                }
                vert.outW = netg.output;

                vertexhash.put(netg, vert);
                Gates.add(vert);
            }
        }
        for (DWire inpx : inplist) {
            Gate vert = new Gate(IndX++, GateType.INPUT);
            vert.outW = inpx;
            vert.Name = inpx.name;
            vert.Outgoing = null;

            Gates.add(vert);
        }
        int eind = 0;

        for (int i = netlist.size() - 1; i >= 0; i--) {
            DGate netg = netlist.get(i);

            if ((netg.output.wtype == DWireType.output) && (netg.gtype != DGateType.OR)) {
                Wire out = new Wire();
                out.From = Gates.get(outpIndx);
                out.To = vertexhash.get(netg);
                out.Index = eind++;
                out.Next = null;
                Wires.add(out);
            }
            Wire temp = null;

            for (DWire inps : netg.input) {

                Gate gTo = null;
                //System.out.println(inps.name);
                for (Gate xvert : Gates) {

                    if (xvert.outW.name.trim().equals(inps.name.trim())) {
                        gTo = xvert;
                        break;
                    }
                }
                Gate gFrom = null;
                gFrom = vertexhash.get(netg);
                Wire newEdge = new Wire(eind++, gFrom, gTo, temp);

                temp = newEdge;
                Wires.add(newEdge);

            }
        }

        for (Wire edg : Wires) {
            if (edg.From.Type.equals(GateType.NOR) || edg.From.Type.equals(GateType.OUTPUT_OR)) {
                if (!(edg.Next == null)) {
                    int count = 0;

                    for (Gate dvert : Gates) {
                        if (edg.From.equals(dvert)) {
                            break;
                        }
                        count++;
                    }

                    Gates.get(count).Outgoing = edg;

                }
            } else {
                if (edg.Next == null) {
                    int count = 0;

                    for (Gate dvert : Gates) {
                        if (edg.From.equals(dvert)) {
                            break;
                        }
                        count++;
                    }
                    Gates.get(count).Outgoing = edg;

                }
            }
        }

        for (Wire xdedge : Wires) {
            outDAG.Wires.add(new Wire(xdedge));
        }
        for (Gate xdvert : Gates) {
            outDAG.Gates.add(new Gate(xdvert));
        }
        //DAGW outputDAG = new DAGW(outDAG.Gates,outDAG.Wires);
        return outDAG;
    }
    
    public static void assignGateIndex(List<DGate> netlist){
        for(int i=0;i<netlist.size();i++){
            netlist.get(i).gindex = i;
        }
    }
    
    
    /**
     * *************************************************************Function
     * <br>
     * Synopsis [prints a Netlist]
     * <br>
     * Description [Takes a List of DGates as input, and prints each DGate
     * sequentially]
     * <br>
     * SideEffects []
     * <br>
     * SeeAlso []
     * <br>
     *
     * @param netlist The netlist to be printed.
     * *********************************************************************
     */
    public static void printNetlist(List<DGate> netlist) {
        for (int i = 0; i < netlist.size(); i++) {
            System.out.println(printGate(netlist.get(i)));
        }
    }

    /**
     * *************************************************************
     * Function
     * <br>
     * Synopsis [prints a DGate]
     * <br>
     * Description [Takes a DGate as an input, and prints in the following
     * format: DGateType(Output, Input1, Input2,....Inputn)]
     * <br>
     * SideEffects []
     * <br>
     * SeeAlso []
     * <br>
     *
     * @param g The DGate to be printed
     * @return A string representation of the DGate
     * *********************************************************************
     */
    public static String printGate(DGate g) {
        String netbuilder = "";
        netbuilder += g.gtype;
        netbuilder += "(";
        netbuilder += g.output.name;

        for (DWire x : g.input) {
            netbuilder += ",";
            netbuilder += x.name;
        
        }
        netbuilder += ")";
        return netbuilder;
    }
}
