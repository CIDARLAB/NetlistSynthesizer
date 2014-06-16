/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */



package BU.netsynth;

import BU.CelloGraph.DAGVertex.VertexType;
import BU.CelloGraph.DAGW;

import BU.ParseVerilog.Blif;
import BU.ParseVerilog.CircuitDetails;
import BU.ParseVerilog.Convert;
import BU.ParseVerilog.Espresso;
import BU.ParseVerilog.Parser;
import BU.ParseVerilog.parseVerilogFile;
import BU.booleanLogic.BooleanSimulator;
import BU.netsynth.DGate.DGateType;
import BU.netsynth.DWire.DWireType;
import BU.precomputation.PreCompute;
import BU.precomputation.genVerilogFile;
import MIT.dnacompiler.Gate;
import MIT.dnacompiler.Gate.GateType;
import MIT.dnacompiler.Wire;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author prashantvaidyanathan
 */
public class NetSynth {

    /**
     * @param args the command line arguments
     */
    public static DWire one;
    public static boolean POSmode;
    public static DWire zero;
    public static boolean functionOutp;
    public static String Filepath;
    
    public static CircuitDetails caseCirc;
    
    public static void main(String[] args) {
        // TODO code application logic here
        
        
        Global.wirecount = 0;
        Global.espinp =0;
        Global.espout =0;
        POSmode = false;
        functionOutp = false;
        one = new DWire("_one",DWireType.Source);
        zero = new DWire("_zero",DWireType.GND);
        Filepath = NetSynth.class.getClassLoader().getResource(".").getPath();
        if(Filepath.contains("build/classes/"))
            Filepath = Filepath.substring(0,Filepath.lastIndexOf("build/classes/")); 
        else if(Filepath.contains("src"))
            Filepath = Filepath.substring(0,Filepath.lastIndexOf("src/"));
        
        //LoadTables.getAllCombos(0.9994);
        //HeuristicSearch.beginSearch(null, 0.9);
        
        //for(int i=0;i<257;i++)
        //    System.out.println(Convert.InttoHex(i));
        
       //genVerilogFile.createVerilogFile(4, "0xBBBBB" );
        //System.out.println(Convert.HextoInt("6"));
       //vtestfunc();
        
        //verifyinverse();
        //histogram();
        //seg7dagw("seg7");
        //testABC();
        //test2notstonor();
        //EspressoVsABC(3);
        //EspressoVsABCinv(3);
        //EspressoVsABCSingle(3,109);
        
        //testABC();
        //testABCsingle(6);
        
        //testespressogen();
        //HistogramREU.calcHist(-0.803574133407);
        //DAGW xcasedag = testParser("",0,0);
        //HeuristicSearch.beginSearch(xcasedag, 0.95,0.5,-1,500,20000);
        
        
        //testespressogen();
        
        
        //verifyprecomute();
        //DAGraph x = precompute(2);2
        //DAGW y = computeDAGW(14);
        //testnetlistmodule();
        //testEspresso();
        
    }

    public static void initializeFilepath() {
        Filepath = NetSynth.class.getClassLoader().getResource(".").getPath();
        if(Filepath.contains("build/classes/"))
            Filepath = Filepath.substring(0,Filepath.lastIndexOf("build/classes/"));
        else if(Filepath.contains("src"))
            Filepath = Filepath.substring(0,Filepath.lastIndexOf("src/"));
    }
    
    /**Function*************************************************************
    Synopsis    [Controller function in NetSynth. Parses Verilog to a Directed Acyclic Graph]
    Description [This is the default function. Takes the verilog file's filepath as the input parameter and gives an optimal result]
    SideEffects []
    SeeAlso     []
    ***********************************************************************/
    public static DAGW runNetSynth(String vfilepath)
    {
        return runNetSynth(vfilepath, NetSynthSwitches.defaultmode, NetSynthSwitches.defaultmode, NetSynthSwitches.defaultmode, NetSynthSwitches.defaultmode, NetSynthSwitches.defaultmode  );
    }
    
    public static DAGW runNetSynth(String vfilepath, String synthesis, String invcheck,String precompute, String outputor, String twonotstonor)
    {
        NetSynthSwitches synth = null;
        NetSynthSwitches inv = null;
        NetSynthSwitches precomp = null;
        NetSynthSwitches outpor = null;
        NetSynthSwitches twonots2nor = null;
        
        try
        {
            synth = NetSynthSwitches.valueOf(synthesis);
            inv = NetSynthSwitches.valueOf(invcheck);
            precomp = NetSynthSwitches.valueOf(precompute);
            outpor = NetSynthSwitches.valueOf(outputor);
            twonots2nor = NetSynthSwitches.valueOf(twonotstonor);
        }
        catch(Exception e)
        {
            System.out.println("Error : "+ e.toString());
        }
        
        return runNetSynth(vfilepath,synth, inv,precomp,outpor,twonots2nor);
    }
    /**Function*************************************************************
    Synopsis    [Controller function in NetSynth. Parses Verilog to a Directed Acyclic Graph]
    Description [This is the default function. Takes the verilog file's filepath as the input parameter and gives an optimal result]
    SideEffects []
    SeeAlso
     * @param vfilepath    []
     * @param synthesis
     * @param invcheck
     * @param precomp
     * @param outputor
     * @param twonotstonor
     * @return 
    ***********************************************************************/
    public static DAGW runNetSynth(String vfilepath,NetSynthSwitches synthesis, NetSynthSwitches invcheck,NetSynthSwitches precomp, NetSynthSwitches outputor, NetSynthSwitches twonotstonor )
    {
        DAGW finaldag = new DAGW();
        
        
        
        List<DGate> naivenetlist = new ArrayList<DGate>();
        List<DGate> structnetlist = new ArrayList<DGate>();
        
        List<DGate> dirnetlist = new ArrayList<DGate>();
        List<DGate> invnetlist = new ArrayList<DGate>();
        List<String> inputnames = new ArrayList<String>();
        List<String> outputnames = new ArrayList<String>();
        List<DGate> netlist = new ArrayList<DGate>();
        
        
        boolean isStructural = false;
        boolean hasCaseStatements = false;
        boolean hasDontCares = false;
        
        String alllines = parseVerilogFile.verilogFileLines(vfilepath);
        isStructural = parseVerilogFile.isStructural(alllines);
        inputnames = parseVerilogFile.getInputNames(alllines);
        outputnames = parseVerilogFile.getOutputNames(alllines);
        
        List<String> precompTT = new ArrayList<String>();
        
        //<editor-fold desc="Structural Verilog">
        if(isStructural)
        {
            naivenetlist = parseVerilogFile.parseStructural(alllines); //Convert Verilog File to List of DGates 
            structnetlist = parseStructuralVtoNORNOT(naivenetlist); // Convert Naive Netlist to List of DGates containing only NOR and NOTs
            
            List<String> ttValues = new ArrayList<String>(); 
            List<String> invttValues = new ArrayList<String>();
            
            ttValues = BooleanSimulator.getTruthTable(structnetlist, inputnames);  // Compute Truth Table of Each Output
            invttValues = BooleanSimulator.invertTruthTable(ttValues); // Compute Inverse Truth Table of Each Output
            
            CircuitDetails direct = new CircuitDetails(inputnames,outputnames,ttValues); 
            CircuitDetails inverted = new CircuitDetails(inputnames, outputnames,invttValues);
            
            dirnetlist = runEspressoAndABC(direct,synthesis,outputor,twonotstonor);
            invnetlist = runInvertedEspressoAndABC(inverted,synthesis,outputor,twonotstonor);
            
            if(synthesis.equals(NetSynthSwitches.noinv))
            {
                if(structnetlist.size() < dirnetlist.size())
                {
                    for(DGate xgate:structnetlist)
                        netlist.add(xgate);
                }
                else
                {
                    for(DGate xgate:dirnetlist)
                        netlist.add(xgate);
                }
            }
            else 
            {
                if ((structnetlist.size() < dirnetlist.size()) && (structnetlist.size() < invnetlist.size())) 
                {
                    for (DGate xgate : structnetlist) 
                    {
                        netlist.add(xgate);
                    }
                } 
                else 
                {
                    if (dirnetlist.size() < invnetlist.size()) 
                    {
                        for (DGate xgate : dirnetlist) 
                        {
                            netlist.add(xgate);
                        }
                    } 
                    else 
                    {
                        for (DGate xgate : invnetlist) 
                        {
                            netlist.add(xgate);
                        }
                    }
                }
            }
            
        }
        //</editor-fold >
        else
        {
            hasCaseStatements = parseVerilogFile.hasCaseStatements(alllines);
            //<editor-fold desc="Behavioral- Has Case Statements">
            if(hasCaseStatements)
            {
                //System.out.println("Has Case Statements!");
                CircuitDetails direct = new CircuitDetails();
                direct = parseVerilogFile.parseCaseStatements(alllines);
                List<String> invttValues = new ArrayList<String>();
                invttValues = BooleanSimulator.invertTruthTable(direct.truthTable);
                CircuitDetails inverted = new CircuitDetails(direct.inputNames, direct.outputNames, invttValues);
                
                hasDontCares = parseVerilogFile.hasDontCares(direct.truthTable);
                //<editor-fold desc="Behavioral- Case Statements - Has Don't Cares">
                if(hasDontCares)
                {
                    dirnetlist = runDCEspressoAndABC(direct,synthesis,outputor,twonotstonor);
                    invnetlist = runInvertedDCEspressoAndABC(inverted,synthesis,outputor,twonotstonor);
                    if(synthesis.equals(NetSynthSwitches.noinv))
                    {
                        for (DGate xgate : dirnetlist) 
                        {
                            netlist.add(xgate);
                        }
                    }
                    else
                    {
                        if (dirnetlist.size() < invnetlist.size()) 
                        {
                            for (DGate xgate : dirnetlist) 
                            {
                                netlist.add(xgate);
                            }
                        } 
                        else 
                        {
                            for (DGate xgate : invnetlist) 
                            {
                                netlist.add(xgate);
                            }
                        }
                    }
                }
                //</editor-fold>
                //<editor-fold desc="Behavioral- Case Statements - NO Don't Cares">
                else
                {
                    //System.out.println("No Dont Cares");
                    
                    
                    dirnetlist = runEspressoAndABC(direct,synthesis,outputor,twonotstonor);
                    invnetlist = runInvertedEspressoAndABC(inverted,synthesis,outputor,twonotstonor);
                    
                    if(synthesis.equals(NetSynthSwitches.noinv))
                    {
                        for (DGate xgate : dirnetlist) 
                        {
                            netlist.add(xgate);
                        }
                    }
                    else
                    {
                        if (dirnetlist.size() < invnetlist.size()) 
                        {
                            for (DGate xgate : dirnetlist) 
                            {
                                netlist.add(xgate);
                            }
                        } 
                        else 
                        {
                            for (DGate xgate : invnetlist) 
                            {
                                netlist.add(xgate);
                            }
                        }
                    }
                }
                //</editor-fold>
            }
            //</editor-fold>
            else
            {
                //System.out.println(alllines);
                System.out.println("No case statements.\nWill be supported soon");
            }
        }
        if((!precomp.equals(NetSynthSwitches.noprecompute)) && (outputnames.size()==1) && (inputnames.size() == 3))
        {
                precompTT = BooleanSimulator.getTruthTable(netlist, inputnames);
                List<List<DGate>> precompnetlist;
                precompnetlist = PreCompute.parseNetlistFile();
                int ttval = Convert.bintoDec(precompTT.get(0));
                if((ttval!=0) && (ttval!= 255))
                {
                    if(precompnetlist.get(ttval-1).size() < netlist.size())
                    {
                        netlist = new ArrayList<DGate>();
                        for (DGate xgate : precompnetlist.get(ttval-1)) 
                        {
                            netlist.add(xgate);
                        }
                    }
                }
                
        }
        
        
        
        
        netlist = rewireNetlist(netlist);
        
        //System.out.println("\nFinal Netlist");
        printNetlist(netlist);
        //BooleanSimulator.printTruthTable(netlist, inputnames);
        finaldag = CreateMultDAGW(netlist);
        
        if(hasCaseStatements)
        {
            finaldag = DAGW.addDanglingInputs(finaldag,inputnames);
        }
        
        finaldag = DAGW.reorderinputs(finaldag,inputnames);
        //for(Gate xgate:finaldag.Gates)
        //{
        //    System.out.println(xgate.Name +" : " + xgate.Type);
        //}
        
        return finaldag;
    }
    
    
    
    
    public static List<DGate> runDCEspressoAndABC(CircuitDetails circ,NetSynthSwitches synthmode, NetSynthSwitches outpor, NetSynthSwitches twonots2nor) 
    {
        List<DGate> EspCircuit = new ArrayList<DGate>();
        List<DGate> ABCCircuit = new ArrayList<DGate>();
        List<String> espressoFile = new ArrayList<String>();
        List<String> blifFile = new ArrayList<String>();
        
        espressoFile = Espresso.createFile(circ);
        blifFile = Blif.createFile(circ);
        String filestring = "";
        String Filepath = NetSynth.class.getClassLoader().getResource(".").getPath();
        
        if(Filepath.contains("build/classes/"))
            Filepath = Filepath.substring(0,Filepath.lastIndexOf("build/classes/")); 
        else if(Filepath.contains("src"))
            Filepath = Filepath.substring(0,Filepath.lastIndexOf("src/"));
        if (Filepath.contains("prashant")) 
        {
            filestring += Filepath + "src/BU/resources/";
        } 
        else 
        {
            filestring += Filepath + "BU/resources/";
        }
        String filestringblif = "";
        filestringblif = filestring + "Blif_File";
        filestringblif += ".blif";
        String filestringesp = "";
        
        filestringesp += filestring + "Espresso_File" +".txt";
        File fespinp = new File(filestringesp);
        //Writer output;
        try 
        {
            Writer output = new BufferedWriter(new FileWriter(fespinp));
            
             for (String xline :espressoFile) 
             {
                String newl = (xline + "\n");
                output.write(newl);
             }
             output.close();
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(NetSynth.class.getName()).log(Level.SEVERE, null, ex);
        }
        List<String> EspOutput = new ArrayList<String>();
        EspOutput = runEspresso(filestringesp);
        fespinp.deleteOnExit();
        
        boolean outor;
        boolean not2nor;
        
        if(outpor.equals(NetSynthSwitches.defaultmode))
            outor = true;
        else
            outor = false;
        
        if(twonots2nor.equals(NetSynthSwitches.defaultmode))
            not2nor = true;
        else
            not2nor = false;
        
        if(!synthmode.equals(NetSynthSwitches.abc))
        {
            EspCircuit = convertPOStoNORNOT(EspOutput);
            EspCircuit = optimizeNetlist(EspCircuit,outor,not2nor);
        }
            
        if(!synthmode.equals(NetSynthSwitches.espresso))
            ABCCircuit = parseEspressoOutToABC(EspOutput, outpor,twonots2nor);
        
        if(synthmode.equals(NetSynthSwitches.abc))
            return ABCCircuit;
        else if(synthmode.equals(NetSynthSwitches.espresso))
            return EspCircuit;
        else 
        {
            if (EspCircuit.size() < ABCCircuit.size()) 
            {
                return EspCircuit;
            } 
            else 
            {
                return ABCCircuit;
            }
        }
    }
    
    
    public static List<DGate> runInvertedDCEspressoAndABC(CircuitDetails circ,NetSynthSwitches synthmode, NetSynthSwitches outpor, NetSynthSwitches twonots2nor)
    {
        
        boolean outor;
        boolean not2nor;
        
        if(outpor.equals(NetSynthSwitches.defaultmode))
            outor = true;
        else
            outor = false;
        
        if(twonots2nor.equals(NetSynthSwitches.defaultmode))
            not2nor = true;
        else
            not2nor = false;
        List<DGate> EspCircuit = new ArrayList<DGate>();
        List<DGate> ABCCircuit = new ArrayList<DGate>();
        List<String> espressoFile = new ArrayList<String>();
        List<String> blifFile = new ArrayList<String>();
        
        espressoFile = Espresso.createFile(circ);
        blifFile = Blif.createFile(circ);
        String filestring = "";
        String Filepath = NetSynth.class.getClassLoader().getResource(".").getPath();
        if(Filepath.contains("build/classes/"))
            Filepath = Filepath.substring(0,Filepath.lastIndexOf("build/classes/")); 
        else if(Filepath.contains("src"))
            Filepath = Filepath.substring(0,Filepath.lastIndexOf("src/"));
        if (Filepath.contains("prashant")) 
        {
            filestring += Filepath + "src/BU/resources/";
        } 
        else 
        {
            filestring += Filepath + "BU/resources/";
        }
        String filestringblif = "";
        filestringblif = filestring + "Blif_File";
        filestringblif += ".blif";
        String filestringesp = "";
        
        filestringesp += filestring + "Espresso_File" +".txt";
        File fespinp = new File(filestringesp);
        //Writer output;
        try 
        {
            Writer output = new BufferedWriter(new FileWriter(fespinp));
            
             for (String xline :espressoFile) 
             {
                String newl = (xline + "\n");
                output.write(newl);
             }
             output.close();
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(NetSynth.class.getName()).log(Level.SEVERE, null, ex);
        }
        List<String> EspOutput = new ArrayList<String>();
        EspOutput = runEspresso(filestringesp);
        fespinp.deleteOnExit();
        
        EspCircuit = convertPOStoNORNOT(EspOutput);
        ABCCircuit = parseINVEspressoOutToABC(EspOutput);
        
        List<DGate> finalEspCircuit = new ArrayList<DGate>();
        List<DGate> finalABCCircuit = new ArrayList<DGate>();
        
        
        
        for(int i=0;i<EspCircuit.size();i++)
        {
            if(EspCircuit.get(i).output.wtype.equals(DWireType.output))
            {
                String outname = "";
                outname = EspCircuit.get(i).output.name.trim();
                String notinpwirename = "0Wire" +Global.wirecount++;
                DWire notinp = new DWire(notinpwirename,DWireType.connector);
                EspCircuit.get(i).output = notinp;
                finalEspCircuit.add(EspCircuit.get(i));
                DGate outnot = new DGate();
                outnot.gtype = DGateType.NOT;
                outnot.input.add(notinp);
                outnot.output = new DWire(outname,DWireType.output);
                finalEspCircuit.add(outnot);
            }
            else
            {
                finalEspCircuit.add(EspCircuit.get(i));
            }
        }
        for(int i=0;i<ABCCircuit.size();i++)
        {
            if(ABCCircuit.get(i).output.wtype.equals(DWireType.output))
            {
                String outname = "";
                outname = ABCCircuit.get(i).output.name.trim();
                String notinpwirename = "0Wire" +Global.wirecount++;
                DWire notinp = new DWire(notinpwirename,DWireType.connector);
                ABCCircuit.get(i).output = notinp;
                finalABCCircuit.add(ABCCircuit.get(i));
                DGate outnot = new DGate();
                outnot.gtype = DGateType.NOT;
                outnot.input.add(notinp);
                outnot.output = new DWire(outname,DWireType.output);
                finalABCCircuit.add(outnot);
            }
            else
            {
                finalABCCircuit.add(ABCCircuit.get(i));
            }
        }
        
        finalEspCircuit = optimizeNetlist(finalEspCircuit,outor,not2nor);
        
        finalABCCircuit = separateOutputGates(finalABCCircuit);
        finalABCCircuit = optimizeNetlist(finalABCCircuit,outor,not2nor);
        
        if(!synthmode.equals(NetSynthSwitches.espresso))
            ABCCircuit = parseEspressoOutToABC(EspOutput, outpor,twonots2nor);
        
        if(synthmode.equals(NetSynthSwitches.abc))
            return finalABCCircuit;
        else if(synthmode.equals(NetSynthSwitches.espresso))
            return finalEspCircuit;
        else 
        {
            if(finalEspCircuit.size() < finalABCCircuit.size())
            {
                return finalEspCircuit;
            }
            else
            {
                return finalABCCircuit;
            }
        }
    }
    
    
    public static List<DGate> runEspressoAndABC(CircuitDetails circ,NetSynthSwitches synthmode, NetSynthSwitches outpor, NetSynthSwitches twonots2nor)
    {
        
        boolean outor;
        boolean not2nor;
        
        if(outpor.equals(NetSynthSwitches.defaultmode))
            outor = true;
        else
            outor = false;
        
        if(twonots2nor.equals(NetSynthSwitches.defaultmode))
            not2nor = true;
        else
            not2nor = false;
        
        List<DGate> EspCircuit = new ArrayList<DGate>();
        List<DGate> ABCCircuit = new ArrayList<DGate>();
        List<String> espressoFile = new ArrayList<String>();
        List<String> blifFile = new ArrayList<String>();
        
        espressoFile = Espresso.createFile(circ);
        blifFile = Blif.createFile(circ);
        String filestring = "";
        String Filepath = NetSynth.class.getClassLoader().getResource(".").getPath();
        if(Filepath.contains("build/classes/"))
            Filepath = Filepath.substring(0,Filepath.lastIndexOf("build/classes/")); 
        else if(Filepath.contains("src"))
            Filepath = Filepath.substring(0,Filepath.lastIndexOf("src/"));
        if (Filepath.contains("prashant")) 
        {
            filestring += Filepath + "src/BU/resources/";
        } 
        else 
        {
            filestring += Filepath + "BU/resources/";
        }
        String filestringblif = "";
        filestringblif = filestring + "Blif_File";
        filestringblif += ".blif";
        String filestringesp = "";
        
        filestringesp += filestring + "Espresso_File" +".txt";
        File fespinp = new File(filestringesp);
        //Writer output;
        try 
        {
            Writer output = new BufferedWriter(new FileWriter(fespinp));
            
             for (String xline :espressoFile) 
             {
                String newl = (xline + "\n");
                output.write(newl);
             }
             output.close();
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(NetSynth.class.getName()).log(Level.SEVERE, null, ex);
        }
        List<String> EspOutput = new ArrayList<String>();
        EspOutput = runEspresso(filestringesp);
        fespinp.deleteOnExit();
        
        EspCircuit = convertPOStoNORNOT(EspOutput);
        EspCircuit = optimizeNetlist(EspCircuit,outor,not2nor);
        
        File fabcinp = new File(filestringblif);
        try 
        {
            Writer outputblif = new BufferedWriter(new FileWriter(fabcinp));
            for (String xline : blifFile) 
            {
                String newl = (xline + "\n");
                outputblif.write(newl);
            }
            outputblif.close();
            List<DGate> abcoutput = new ArrayList<DGate>();
            
            try 
            {
                abcoutput = runABC("Blif_File");

            } 
            catch (InterruptedException ex) 
            {
                Logger.getLogger(NetSynth.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            abcoutput = separateOutputGates(abcoutput);
            
            abcoutput = optimizeNetlist(abcoutput,outor,not2nor);
            
            for (DGate xgate : abcoutput) 
            {
                ABCCircuit.add(xgate);
            }
            ABCCircuit = removeDanglingGates(ABCCircuit);
            fabcinp.deleteOnExit();

        } 
        catch (IOException ex) 
        {
            Logger.getLogger(NetSynth.class.getName()).log(Level.SEVERE, null, ex);
        }
        fabcinp.deleteOnExit();
        
        if(synthmode.equals(NetSynthSwitches.abc))
            return ABCCircuit;
        else if(synthmode.equals(NetSynthSwitches.espresso))
            return EspCircuit;
        else 
        {
            if (EspCircuit.size() < ABCCircuit.size()) 
            {
                return EspCircuit;
            } 
            else 
            {
                return ABCCircuit;
            }
        }
    }
    
    
    public static List<DGate> runInvertedEspressoAndABC(CircuitDetails circ,NetSynthSwitches synthmode, NetSynthSwitches outpor, NetSynthSwitches twonots2nor)
    {
        
        boolean outor;
        boolean not2nor;
        
        if(outpor.equals(NetSynthSwitches.defaultmode))
            outor = true;
        else
            outor = false;
        
        if(twonots2nor.equals(NetSynthSwitches.defaultmode))
            not2nor = true;
        else
            not2nor = false;
        
        List<DGate> EspCircuit = new ArrayList<DGate>();
        List<DGate> ABCCircuit = new ArrayList<DGate>();
        List<String> espressoFile = new ArrayList<String>();
        List<String> blifFile = new ArrayList<String>();
        
        espressoFile = Espresso.createFile(circ);
        blifFile = Blif.createFile(circ);
        String filestring = "";
        String Filepath = NetSynth.class.getClassLoader().getResource(".").getPath();
        if(Filepath.contains("build/classes/"))
            Filepath = Filepath.substring(0,Filepath.lastIndexOf("build/classes/")); 
        else if(Filepath.contains("src"))
            Filepath = Filepath.substring(0,Filepath.lastIndexOf("src/"));
        if (Filepath.contains("prashant")) 
        {
            filestring += Filepath + "src/BU/resources/";
        } 
        else 
        {
            filestring += Filepath + "BU/resources/";
        }
        String filestringblif = "";
        filestringblif = filestring + "Blif_File";
        filestringblif += ".blif";
        String filestringesp = "";
        
        filestringesp += filestring + "Espresso_File" +".txt";
        File fespinp = new File(filestringesp);
        //Writer output;
        try 
        {
            Writer output = new BufferedWriter(new FileWriter(fespinp));
            
             for (String xline :espressoFile) 
             {
                String newl = (xline + "\n");
                output.write(newl);
             }
             output.close();
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(NetSynth.class.getName()).log(Level.SEVERE, null, ex);
        }
        List<String> EspOutput = new ArrayList<String>();
        EspOutput = runEspresso(filestringesp);
        fespinp.deleteOnExit();
        
        EspCircuit = convertPOStoNORNOT(EspOutput);
         
        File fabcinp = new File(filestringblif);
        try 
        {
            Writer outputblif = new BufferedWriter(new FileWriter(fabcinp));
            for (String xline : blifFile) 
            {
                String newl = (xline + "\n");
                outputblif.write(newl);
            }
            outputblif.close();
            List<DGate> abcoutput = new ArrayList<DGate>();
            
            try 
            {
                abcoutput = runABC("Blif_File");

            } 
            catch (InterruptedException ex) 
            {
                Logger.getLogger(NetSynth.class.getName()).log(Level.SEVERE, null, ex);
            }
            

            for (DGate xgate : abcoutput) 
            {
                ABCCircuit.add(xgate);
            }
            ABCCircuit = removeDanglingGates(ABCCircuit);
            fabcinp.deleteOnExit();

        } 
        catch (IOException ex) 
        {
            Logger.getLogger(NetSynth.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        fabcinp.deleteOnExit();
        
        List<DGate> finalEspCircuit = new ArrayList<DGate>();
        List<DGate> finalABCCircuit = new ArrayList<DGate>();
        
        
        
        for(int i=0;i<EspCircuit.size();i++)
        {
            if(EspCircuit.get(i).output.wtype.equals(DWireType.output))
            {
                String outname = "";
                outname = EspCircuit.get(i).output.name.trim();
                String notinpwirename = "0Wire" +Global.wirecount++;
                DWire notinp = new DWire(notinpwirename,DWireType.connector);
                EspCircuit.get(i).output = notinp;
                finalEspCircuit.add(EspCircuit.get(i));
                DGate outnot = new DGate();
                outnot.gtype = DGateType.NOT;
                outnot.input.add(notinp);
                outnot.output = new DWire(outname,DWireType.output);
                finalEspCircuit.add(outnot);
            }
            else
            {
                finalEspCircuit.add(EspCircuit.get(i));
            }
        }
        for(int i=0;i<ABCCircuit.size();i++)
        {
            if(ABCCircuit.get(i).output.wtype.equals(DWireType.output))
            {
                String outname = "";
                outname = ABCCircuit.get(i).output.name.trim();
                String notinpwirename = "0Wire" +Global.wirecount++;
                DWire notinp = new DWire(notinpwirename,DWireType.connector);
                ABCCircuit.get(i).output = notinp;
                finalABCCircuit.add(ABCCircuit.get(i));
                DGate outnot = new DGate();
                outnot.gtype = DGateType.NOT;
                outnot.input.add(notinp);
                outnot.output = new DWire(outname,DWireType.output);
                finalABCCircuit.add(outnot);
            }
            else
            {
                finalABCCircuit.add(ABCCircuit.get(i));
            }
        }
        
        finalEspCircuit = optimizeNetlist(finalEspCircuit,outor,not2nor);
        
        
        finalABCCircuit = separateOutputGates(finalABCCircuit);
        finalABCCircuit = optimizeNetlist(finalABCCircuit,outor,not2nor);
        
        
        
        if(synthmode.equals(NetSynthSwitches.abc))
            return finalABCCircuit;
        else if(synthmode.equals(NetSynthSwitches.espresso))
            return finalEspCircuit;
        else 
        {
            if(finalEspCircuit.size() < finalABCCircuit.size())
            {
                return finalEspCircuit;
            }
            else
            {
                return finalABCCircuit;
            }
        }
        
        
        
        
        
        
        
    }
    
    
    
    
    
    public static List<DGate> separateOutputGates(List<DGate> netlist)
    {
        List<DGate> finalnetlist = new ArrayList<DGate>();
        HashMap<Integer,DGate> addgate = new HashMap<Integer,DGate>();
        int outToOutflag = 0;
        
        do
        {
        addgate = new HashMap<Integer,DGate>();
        for(int i=0;i<(netlist.size()-1);i++)
        {
            
            outToOutflag = 0;
            if(netlist.get(i).output.wtype.equals(DWireType.output))
            {
                for(int j=(i+1);j<netlist.size();j++)
                {
                    if(netlist.get(j).output.wtype.equals(DWireType.output))
                    {
                        for(int m=0;m<netlist.get(j).input.size();m++)
                        {
                            if(netlist.get(j).input.get(m).wtype.equals(DWireType.output) && netlist.get(j).input.get(m).name.trim().equals(netlist.get(i).output.name.trim()))
                            {
                                outToOutflag = 1;
                                //System.out.println("Found that case!!");
                            }
                        }
                    }
                }
                if(outToOutflag == 1)
                {
                    DGate newg = new DGate();
                    newg.gtype = netlist.get(i).gtype;
                    newg.input.addAll(netlist.get(i).input);
                    String Wname = "0Wire" + Global.wirecount++;
                    DWire outW = new DWire(Wname, DWireType.connector);
                    newg.output = outW;
                    addgate.put(i, newg);
                    for(int j=(i+1);j<netlist.size();j++)
                    {
                        if(netlist.get(j).output.wtype.equals(DWireType.output))
                        {
                            for(int m=0;m<netlist.get(j).input.size();m++)
                            {
                                if(netlist.get(j).input.get(m).wtype.equals(DWireType.output) && netlist.get(j).input.get(m).name.trim().equals(netlist.get(i).output.name.trim()))
                                {
                                    netlist.get(j).input.set(m, outW);
                                    //System.out.println("Found that case!!");
                                }
                            }   
                        }
                    }
                }
            }
        }
        finalnetlist = new ArrayList<DGate>();
        for(int i=0;i<netlist.size();i++)
        {
            if(addgate.containsKey(i))
            {
                finalnetlist.add(addgate.get(i));
            }
            finalnetlist.add(netlist.get(i));
        }
        netlist = new ArrayList<DGate>();
        netlist.addAll(finalnetlist);
        }while(!addgate.isEmpty());
        
        
        
        return finalnetlist;
    }
    
    
    public static List<DGate> parseVerilogABC(String filename)
    {
        List<DGate> netlistResult = new ArrayList<DGate>();
        List<String> blifinput = new ArrayList<String>();
        CircuitDetails circ = new CircuitDetails();
        circ = parseVerilogFile.parseCaseStatements(filename);
        blifinput = Blif.createFile(circ);
        String filestring ="";
        String filestringblif = "";
        if(Filepath.contains("prashant"))
            {
                filestring += Filepath+ "src/BU/resources/";
            }
            else
            {
                filestring += Filepath+ "BU/resources/";
            }
        filestringblif = filestring + "blifinp";
        filestringblif += ".blif";
           
            
          
        File fabcinp = new File(filestringblif);
        try 
        {
            Writer outputblif = new BufferedWriter(new FileWriter(fabcinp));
            for (String xline : blifinput) 
            {
                String newl = (xline + "\n");
                outputblif.write(newl);
            }
            outputblif.close();
            List<DGate> abcoutput = new ArrayList<DGate>();
            
            try 
            {
                abcoutput = runABC("blifinp");

            } 
            catch (InterruptedException ex) 
            {
                Logger.getLogger(NetSynth.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            abcoutput = optimizeNetlist(abcoutput,true,true);
            //abcoutput = removeDoubleInverters(abcoutput);
            //abcoutput = outputORopt(abcoutput);
            //abcoutput = convert2NOTsToNOR(abcoutput);
            //abcoutput = rewireNetlist(abcoutput);

            for (DGate xgate : abcoutput) 
            {
                netlistResult.add(xgate);
            }
            netlistResult = removeDanglingGates(netlistResult);
            fabcinp.deleteOnExit();

        } 
        catch (IOException ex) 
        {
            Logger.getLogger(NetSynth.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return netlistResult;
    }
    
    
    public static String create_VerilogFile(List<String> filelines, String filename)
    {
        String filestring = "";
        Filepath = NetSynth.class.getClassLoader().getResource(".").getPath();
        if(Filepath.contains("build/classes/"))
            Filepath = Filepath.substring(0,Filepath.lastIndexOf("build/classes/")); 
        else if(Filepath.contains("src"))
            Filepath = Filepath.substring(0,Filepath.lastIndexOf("src/"));
        if (Filepath.contains("prashant")) 
        {
            filestring += Filepath + "src/BU/resources/";
        } 
        else 
        {
            filestring += Filepath + "BU/resources/";
        }
        filestring += filename +".v";
          File fespinp = new File(filestring);
        //Writer output;
        try 
        {
            Writer output = new BufferedWriter(new FileWriter(fespinp));
            //output = new BufferedWriter(new FileWriter(fespinp));
             for (String xline :filelines) 
             {
                String newl = (xline + "\n");
                output.write(newl);
             }
             output.close();
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(NetSynth.class.getName()).log(Level.SEVERE, null, ex);
        }
        return filestring;
    }
    
    
    
    public static void create_VerilogFile(int inputs,String hex, String filename)
    {
        List<String> filelines = new ArrayList<String>();
        filelines = genVerilogFile.createSingleOutpVerilogFile(inputs, hex);
        String filestring = "";
        if (Filepath.contains("prashant")) 
        {
            filestring += Filepath + "src/BU/resources/";
        } 
        else 
        {
            filestring += Filepath + "BU/resources/";
        }
        filestring += filename +".v";
        File fespinp = new File(filestring);
        //Writer output;
        try 
        {
            Writer output = new BufferedWriter(new FileWriter(fespinp));
            //output = new BufferedWriter(new FileWriter(fespinp));
             for (String xline :filelines) 
             {
                String newl = (xline + "\n");
                output.write(newl);
             }
             output.close();
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(NetSynth.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    
    
    public static void testespressogen()
    {
        List<String> eslines = new ArrayList<String>();
        CircuitDetails circ = new CircuitDetails();
        circ.inputNames.add("inp1");
        circ.inputNames.add("inp2");
        circ.inputNames.add("inp3");
        circ.outputNames.add("out1");
        circ.outputNames.add("out2");
        circ.truthTable.add(Convert.dectoBin(69, 3));
        circ.truthTable.add(Convert.dectoBin(96, 3));
        List<String> espoutcirc = new ArrayList<String>();
        espoutcirc = Espresso.createFile(circ);
        List<String> blifoutcirc = new ArrayList<String>();
        blifoutcirc = Blif.createFile(circ);
        for(String xesp:espoutcirc)
        {
            System.out.println(xesp);
        }
        String filestring = "";
        if (Filepath.contains("prashant")) 
        {
            filestring += Filepath + "src/BU/resources/espresso";
        } 
        else 
        {
            filestring += Filepath + "BU/resources/espresso";
        }
        filestring += Global.espout++;
        filestring += ".txt";
          File fespinp = new File(filestring);
        //Writer output;
        try 
        {
            Writer output = new BufferedWriter(new FileWriter(fespinp));
            //output = new BufferedWriter(new FileWriter(fespinp));
             for (String xline : espoutcirc) 
             {
                String newl = (xline + "\n");
                output.write(newl);
             }
             output.close();
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(NetSynth.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        List<String> espout = new ArrayList<String>();
        espout = runEspresso(filestring);
        List<DGate> espoutput = new ArrayList<DGate>();
        espoutput = parseEspressoToNORNAND(espout);
    }
    
    
    public static void vtestfunc()
    {
        String line = "module and3(output out, input wire in2,in6, bryan, output wire on1, input in1, in3, in4);";
        Parser.testfunction(line);
    }
    
    public static void verifyinverse()
    {
        String filestring ="";
        if(Filepath.contains("prashant"))
        {
            filestring += Filepath+ "src/BU/resources/Inverse";
        }
        else
        {
            filestring += Filepath+ "BU/resources/Inverse";
        }
          
            filestring += ".csv";
            File fespinp = new File(filestring);
        try {
            List<List<DGate>> precomp;
            precomp = PreCompute.parseNetlistFile();
            Writer output = new BufferedWriter(new FileWriter(fespinp));
            String Line;
            Line = "SrNo,Actual,Inverse\n";
            output.write(Line);
            for(int i=0;i<256;i++)
            {
                 Line ="";
                if(i==0||i==255)
                   Line += i + "\n";
                else
                {
                    int x = precomp.get(i-1).size();
                    int y = precomp.get(253- i + 1).size();
                    //int y=0;
                    Line = i+ ","+ x + "," + (y+1) + "\n";
                }
                output.write(Line);
            }
            output.close();
            
            
        } catch (IOException ex) {
            Logger.getLogger(NetSynth.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
    }
    
    public static void verifyprecomute()
    {
         List<List<DGate>> precomp;
         precomp = PreCompute.parseNetlistFile();
         int flag =0;
         for(int i=0;i<256;i++)
         {
             if(i==0 || i==255)
             {
                 flag =0;
             } 
             else
             {
                 String truthfunc ="";
                 truthfunc = BooleanSimulator.bpermutePreComp(precomp.get(i-1));
                 //System.out.println(truthfunc);
                 int k = Convert.bintoDec(truthfunc);
                 if(k!=i)
                 {
                     System.out.println(truthfunc);
                     System.out.println(i-1);
                     //flag =1;
                     //break;
                 }
             }
         }
         if(flag == 1)
         {
             System.out.println("ERROR!!!");
         }
    }
    
    
    
    
    
    public static List<DGate> parseEspressoOutToABC(List<String> espout,NetSynthSwitches outpor, NetSynthSwitches twonots2nor) 
    {
        List<DGate> netlistout = new ArrayList<DGate>();
        //List<String> espout = new ArrayList<String>();
        //espout = runEspresso(filename);
        /*for(String xline:espout)
        {
            System.out.println(xline);
        }*/
        boolean outor;
        boolean not2nor;
        
        if(outpor.equals(NetSynthSwitches.defaultmode))
            outor = true;
        else
            outor = false;
        
        if(twonots2nor.equals(NetSynthSwitches.defaultmode))
            not2nor = true;
        else
            not2nor = false;
        
        
        List<String> vfilelines = new ArrayList<String>();
        
        vfilelines = convertEspressoOutputToVerilog(espout);
        String vfilepath = "";
        vfilepath = create_VerilogFile(vfilelines,"espressoVerilog");
        try {
            netlistout = runABCverilog("espressoVerilog");
            netlistout = separateOutputGates(netlistout);
            
            netlistout = optimizeNetlist(netlistout,outor,not2nor);
            
        } catch (InterruptedException ex) {
            Logger.getLogger(NetSynth.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return netlistout;
    }
    public static List<DGate> parseINVEspressoOutToABC(List<String> espout) 
    {
        List<DGate> netlistout = new ArrayList<DGate>();
        //List<String> espout = new ArrayList<String>();
        //espout = runEspresso(filename);
        /*for(String xline:espout)
        {
            System.out.println(xline);
        }*/
        List<String> vfilelines = new ArrayList<String>();
        
        vfilelines = convertEspressoOutputToVerilog(espout);
        String vfilepath = "";
        vfilepath = create_VerilogFile(vfilelines,"espressoVerilog");
        try {
            netlistout = runABCverilog("espressoVerilog");
            
        } catch (InterruptedException ex) {
            Logger.getLogger(NetSynth.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return netlistout;
    }
    
    
    public static List<DGate> parseEspressoFileToABC(String filename) 
    {
        List<DGate> netlistout = new ArrayList<DGate>();
        List<String> espout = new ArrayList<String>();
        espout = runEspresso(filename);
        /*for(String xline:espout)
        {
            System.out.println(xline);
        }*/
        List<String> vfilelines = new ArrayList<String>();
        
        vfilelines = convertEspressoOutputToVerilog(espout);
        String vfilepath = "";
        vfilepath = create_VerilogFile(vfilelines,"espressoVerilog");
        try {
            
            netlistout = runABCverilog("espressoVerilog");
            netlistout = optimizeNetlist(netlistout,true,true);            
            //netlistout = removeDoubleInverters(netlistout);
            //netlistout = outputORopt(netlistout);
            //netlistout = convert2NOTsToNOR(netlistout);
            //netlistout = rewireNetlist(netlistout);
        } catch (InterruptedException ex) {
            Logger.getLogger(NetSynth.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return netlistout;
    }
    
    /*private static Comparator<String> ALPHABETICAL_ORDER = new Comparator<String>()
    {
            public int compare(String str1, String str2)
            {
                int res = String.CASE_INSENSITIVE_ORDER.compare(str1, str2);
                if(res ==0)
                {
                    res = str1.compareTo(str2);                
                }
                return res;
            }
    };*/
    
    public static DAGW testParser(String pathFile, int inv, int presynth)
    {
        
        String path = pathFile;
        Filepath = NetSynth.class.getClassLoader().getResource(".").getPath();
        if(Filepath.contains("build/classes/"))
            Filepath = Filepath.substring(0,Filepath.lastIndexOf("build/classes/")); 
        else if(Filepath.contains("src"))
            Filepath = Filepath.substring(0,Filepath.lastIndexOf("src/"));
        
        
        caseCirc = new CircuitDetails();
        caseCirc = parseVerilogFile.parseCaseStatements(path);
        //System.out.println(caseCirc.inputgatetable);
        DAGW circuitDAG = new DAGW();
        DAGW circuitDAGinv = new DAGW();
        int invindx ;
        
        
        
        if(Convert.bintoDec(caseCirc.truthTable.get(0)) ==0 || Convert.bintoDec(caseCirc.truthTable.get(0)) ==255)
            return null;
        
         // <editor-fold desc="If 3 input circuit and presynth option is true">
        if((caseCirc.inputNames.size() == 3) && (presynth ==1))
        {
            DAGW circ = computeDAGW(Convert.bintoDec(caseCirc.truthTable.get(0))-1);
            circuitDAG = new DAGW(circ.Gates,circ.Wires);
            DAGW circinv = computeDAGW(255 - Convert.bintoDec(caseCirc.truthTable.get(0)) - 1);
            circuitDAGinv = new DAGW(circinv.Gates,circinv.Wires);
            //System.out.println("Ratatatatatata Circus Afro"+circuitDAG.Gates.get(0).Type.toString());
            
            //System.out.println("Ratatatatatata Circus"+circuitDAG.Wires.get(0).From.Type.toString());
            if (circuitDAGinv.Gates.size() > 1 && (inv == 1)) 
            {
                int gatessize = circuitDAGinv.Gates.size();
                if (("NOR".equals(circuitDAGinv.Gates.get(1).Type) || "NOT".equals(circuitDAGinv.Gates.get(1).Type)) && (!circuitDAGinv.Gates.get(0).Type.equals(GateType.OUTPUT_OR.toString()))) 
                {
                    
                    if (circuitDAG.Gates.size() > (circuitDAGinv.Gates.size() - 1)) 
                    {
                        circuitDAG = circuitDAGinv;
                        gatessize = circuitDAGinv.Gates.size();
                        if("NOR".equals(circuitDAG.Gates.get(1).Type))
                        {
                            circuitDAG.Gates.get(1).Type = GateType.OUTPUT_OR.toString();
                            circuitDAG.Gates.get(1).Name = circuitDAG.Gates.get(0).Name;
                            
                            circuitDAG.Gates.remove(0);
                        }
                        else if("NOT".equals(circuitDAG.Gates.get(1).Type))
                        {
                            circuitDAG.Gates.get(1).Type = GateType.OUTPUT.toString();
                            circuitDAG.Gates.get(1).Name = circuitDAG.Gates.get(0).Name;
                            circuitDAG.Gates.remove(0);
                        }
                    }
                }
            }
            int i=0;
            int j=0;
            int caseinpcount =0;
            HashMap<Integer,String> activeinp = new HashMap<Integer,String>();
            
            for(Gate gdag:circuitDAG.Gates)
            {
                if("INPUT".equals(gdag.Type))
                {
                    if("c".equals(gdag.Name.trim()))
                    {
                        i=0;
                    }
                    else if("b".equals(gdag.Name.trim()))
                    {
                        i=1;
                    }
                    else 
                    {
                        i=2;
                    }
                    activeinp.put(i,gdag.Name);
                    gdag.Name = caseCirc.inputNames.get(i);
                    caseinpcount++;
                }
                if("OUTPUT".equals(gdag.Type) || "OUTPUT_OR".equals(gdag.Type))
                {
                    gdag.Name = caseCirc.outputNames.get(j);
                    j++; 
                }
            }
            
            if(caseinpcount != 3)
            {
                for(int jj=0;jj<3;jj++)
                {
                    if(!activeinp.containsKey(jj))
                    {
                        Gate uainp = new Gate();
                        uainp.Outgoing = null;
                        uainp.Index = -1;
                        uainp.Type = Gate.GateType.INPUT.toString();
                        uainp.Name = caseCirc.inputNames.get(jj);
                        circuitDAG.Gates.add(uainp);
                    }
                }
            }
            List<String> inputGates = new ArrayList<String>();
            HashMap<String,Gate> inpord = new HashMap<String,Gate>();
            
            for(Gate gdag:circuitDAG.Gates)
            {
                if(gdag.Type.equals("INPUT"))
                {
                    inputGates.add(gdag.Name.trim());
                    inpord.put(gdag.Name.trim(), gdag);
                    //System.out.println(gdag.Name);
                }
            }
            
            
            Collections.sort(inputGates);
            int kk=0;
           
            for(int ii=0;ii<circuitDAG.Gates.size();ii++)
            {
                Gate gdag = circuitDAG.Gates.get(ii);
                if(gdag.Type.equals("INPUT"))
                {
                   Gate val = inpord.get(inputGates.get(kk));
                   circuitDAG.Gates.set(ii, val);
                   kk++;
                }
            }
            for(Gate gdag:circuitDAG.Gates)
            {
                System.out.println(gdag.Name);
            }
            
            
        }
        // </editor-fold>
        
        else
        {
            
            List<String> eslines = new ArrayList<String>();
            List<String> eslinesinv = new ArrayList<String>();
            int powr = (int) Math.pow(2, caseCirc.inputNames.size());
            
            List<String> caseCirctt = new ArrayList<String>();
            for(int i=0;i<caseCirc.truthTable.size();i++)
            {
                //System.out.println("Truth Table value!!" + caseCirc.inputgatetable.get(i));
                caseCirctt.add(caseCirc.truthTable.get(i));
            }
            List<String> caseCircttinv = new ArrayList<String>();
            for(int i=0;i<caseCirc.truthTable.size();i++)
            {
                caseCircttinv.add(Convert.invBin(caseCirctt.get(i)));
            }
            List<String> invval = new ArrayList<String>();
            for(int i=0;i<caseCirc.truthTable.size();i++)
            {
                invval.add(caseCircttinv.get(i));
            }
            
            
            //int invval = ;
            
            CircuitDetails invcaseCirc = new CircuitDetails(caseCirc.inputNames,caseCirc.outputNames,invval);
            
            //System.out.println("Main Circuit: " + caseCirctt);
            //System.out.println("Main Circuit: " + caseCircttinv);
            
            eslines = Espresso.createFile(caseCirc);
            eslinesinv = Espresso.createFile(invcaseCirc);
            String filestring = "";
            String filestringinv = "";
           
            if(Filepath.contains("prashant"))
            {
                filestring += Filepath+ "src/BU/resources/espresso";
            }
            else
            {
                filestring += Filepath+ "BU/resources/espresso";
            }
            filestring += Global.espout++ ;
            filestringinv += filestring;
            filestring += ".txt";
            filestringinv += "inv.txt";
            
            File fespinp = new File(filestring);
            File fespinpinv = new File(filestringinv);
            try 
            {
                Writer output = new BufferedWriter(new FileWriter(fespinp));
                for(String xline:eslines)
                {
                    String newl = (xline + "\n");
                    output.write(newl);
                }
                output.close();
                List<String> espout = new ArrayList<String>();
                espout = runEspresso(filestring);
                List<DGate> espoutput = new ArrayList<DGate>();
                espoutput = convertPOStoNORNOT(espout);
                
                espoutput = optimizeNetlist(espoutput,true,true);
                //espoutput = removeDoubleInverters(espoutput);
                //espoutput = outputORopt(espoutput);
                //espoutput = parseEspressoToNORNAND(espout);
                
                System.out.println("\nPOST-OPTIMIZATION : NETLIST\n");
                for(int i=0;i<espoutput.size();i++)
                {
                    System.out.println(printGate(espoutput.get(i)));
                }
                
                Writer outputinv = new BufferedWriter(new FileWriter(fespinpinv));
                for(String xline:eslinesinv)
                {
                    String newl = (xline + "\n");
                    outputinv.write(newl);
                }
                outputinv.close();
                
                List<String> espoutinv = new ArrayList<String>();
                espoutinv = runEspresso(filestringinv);
                List<DGate> espoutputinv = new ArrayList<DGate>();
                
                //espoutputinv = parseEspressoToNORNAND(espoutinv);
                espoutputinv = convertPOStoNORNOT(espoutinv);
                
                espoutputinv = optimizeNetlist(espoutputinv,true,true);
                //espoutputinv = removeDoubleInverters(espoutputinv);
                //espoutputinv = outputORopt(espoutputinv);
                
                System.out.println("\nPOST-OPTIMIZATION : INV NETLIST\n");
                for(int i=0;i<espoutputinv.size();i++)
                {
                    System.out.println(printGate(espoutputinv.get(i)));
                }
                
                //System.out.println("\n\n==== CHECKING!!! ====");
                
                //System.out.println("\n==== Primary Circuit ====");
                //System.out.println(BooleanSimulator.bpermuteEsynth(espoutput));
                //for(DGate dg:espoutput)
                //    System.out.println(netlist(dg));
                
                //System.out.println("\n==== Inverse Circuit ====");
                //System.out.println(BooleanSimulator.bpermuteEsynth(espoutputinv));
                //for(DGate dg:espoutputinv)
                //    System.out.println(netlist(dg));
                
                //DAGW circ = computeDAGW(caseCirc.inputgatetable-1);
                //circuitDAG = CreateDAGW(espoutput);
                circuitDAG = CreateMultDAGW(espoutput);
                
                //DAGW circinv = computeDAGW(255 - caseCirc.inputgatetable - 1);
                //circuitDAGinv = CreateDAGW(espoutputinv);
                circuitDAGinv = CreateMultDAGW(espoutputinv);
                
                if (circuitDAGinv.Gates.size() > 1 && (inv == 1)) {

                    int gatessize = circuitDAGinv.Gates.size();
                    if (("NOR".equals(circuitDAGinv.Gates.get(1).Type) || "NOT".equals(circuitDAGinv.Gates.get(1).Type)) && (!circuitDAGinv.Gates.get(0).Type.equals(GateType.OUTPUT_OR.toString()))) 
                    {
                        if (circuitDAG.Gates.size() > (circuitDAGinv.Gates.size() - 1)) {
                            //System.out.println("Special Case");
                            circuitDAG = circuitDAGinv;
                            gatessize = circuitDAGinv.Gates.size();
                            if (circuitDAG.Gates.get(1).Type == "NOR") {
                                circuitDAG.Gates.get(1).Type = GateType.OUTPUT_OR.toString();
                                circuitDAG.Gates.get(1).Name = circuitDAG.Gates.get(0).Name;
                                circuitDAG.Gates.remove(0);
                                circuitDAG.Wires.remove(0);
                            } else if (circuitDAG.Gates.get(1).Type == "NOT") {
                                circuitDAG.Gates.get(1).Type = GateType.OUTPUT.toString();
                                circuitDAG.Gates.get(1).Name = circuitDAG.Gates.get(0).Name;
                                circuitDAG.Gates.remove(0);
                                circuitDAG.Wires.remove(0);
                            }
                        }
                    }
                }
                
                int activeinp=0;
                List<String> activeinplist = new ArrayList<String>();
                
                for(Gate xgate : circuitDAG.Gates)
                {
                    if(xgate.Type.equals(GateType.INPUT.toString()))
                    {
                        activeinp++;
                        activeinplist.add(xgate.Name);
                        //System.out.println(xgate.Name.trim());
                    }
                }
                if(activeinp < caseCirc.inputNames.size())
                {
                    for(String xinp:caseCirc.inputNames)
                    {
                        if(!activeinplist.contains(xinp.trim()))
                        {
                            Gate uainp = new Gate();
                            uainp.Outgoing = null;
                            uainp.Index = -1;
                            uainp.Type = Gate.GateType.INPUT.toString();
                            uainp.Name = xinp.trim();
                            circuitDAG.Gates.add(uainp);
                        }
                    }
                }
                
                HashMap<String, Gate> inpord = new HashMap<String, Gate>();
                List<String> inputGates = new ArrayList<String>();
                for(String xinp:caseCirc.inputNames)
                {
                    for (Gate gdag : circuitDAG.Gates) 
                    {
                        if (gdag.Type.equals("INPUT") && gdag.Name.equals(xinp)) 
                        {
                            inputGates.add(gdag.Name.trim());
                            inpord.put(gdag.Name.trim(), gdag);
                            //System.out.println(gdag.Name);
                        }
                    }   
                }
                //int kk = caseCirc.inputNames.size()-1;
                int kk=0;
                for (int ii = 0; ii < circuitDAG.Gates.size(); ii++) 
                {
                    Gate gdag = circuitDAG.Gates.get(ii);
                    if (gdag.Type.equals("INPUT")) 
                    {
                        Gate val = inpord.get(inputGates.get(kk));
                        circuitDAG.Gates.set(ii, val);
                        //kk--;
                        kk++;
                    }
                }
                
                /*for (Gate gdag : circuitDAG.Gates) 
                {
                    System.out.println(gdag.Name);
                }*/
                
                /*if(espoutput.size() > (espoutputinv.size() + 1))
                {
                    System.out.println("Special Condition");
                    espoutput = espoutputinv;
                    String Wirename = "0Wire" + Global.wirecount++;
                    espoutput.get(espoutput.size()-1).output.wtype = DWireType.connector;
                    List<DWire> inpwesp = new ArrayList<DWire>();
                    DWire outwesp = new DWire();
                    outwesp.name =  espoutput.get(espoutput.size()-1).output.name;
                    outwesp.wtype = DWireType.output;
                    espoutput.get(espoutput.size()-1).output.name = Wirename;
                    inpwesp.add(espoutput.get(espoutput.size()-1).output);
                    espoutput.add(new DGate(DGateType.NOT,inpwesp,outwesp));
                }*/
                
                //String espperm = BooleanSimulator.bpermute(espoutput);
                
                /*for(DGate netgate:espoutput)
                {
                    System.out.println(netlist(netgate));
                }*/
                //circuitDAG = CreateDAGW(espoutput);
                
                fespinp.deleteOnExit();
                
                //System.out.println(espperm);
            } 
            catch (IOException ex) 
            {
                Logger.getLogger(NetSynth.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
        }
        int ttpow = (int)Math.pow(2, caseCirc.inputNames.size());
        for(int i=0;i<caseCirc.truthTable.size();i++)
        {
            String bintt = caseCirc.truthTable.get(i);
            circuitDAG.truthtable.add(bintt);
        }
        System.out.println("\n\n\n\nDAGW!!!");
        for(int i=0;i<circuitDAG.Gates.size();i++)
            System.out.println(circuitDAG.Gates.get(i).Name + ":" + circuitDAG.Gates.get(i).Type);
        System.out.println("\n\n\n\n\n");
        
        String graphs = circuitDAG.printGraph();
        System.out.println(graphs);
        return circuitDAG;
        
    }
    
    
    
    public static DAGW computeDAGW(int x) 
    {
        one = new DWire("_one",DWireType.Source);
        zero = new DWire("_zero",DWireType.GND);
        DAGW outputdag = new DAGW();
        List<List<DGate>> precomp;
        precomp = PreCompute.parseNetlistFile();
        DAGW circ = new DAGW();
        
        //circ = CreateDAGW(precomp.get(x));
        circ = CreateMultDAGW(precomp.get(x));
        
        for(int i=0;i<precomp.get(x).size();i++)
            System.out.println(printGate(precomp.get(x).get(i)));
        
        for(int i=0;i<circ.Gates.size();i++)
            System.out.println(circ.Gates.get(i).Name);
        System.out.println("\n\n");
        outputdag = new DAGW(circ.Gates,circ.Wires);
        return outputdag;
    }
 
    //<editor-fold desc="precompute function">
    
    /*
    public static DAGraph precompute(int x)
    {
       DAGraph outdag = new DAGraph();
        List<List<DGate>> precomp;
        precomp = PreCompute.parseNetlistFile();
        outdag = CreateDAGraph(precomp.get(x));
        
        return outdag;
        
    }
    */
    //</editor-fold>
    
    public static void testEspresso()
    {
        
        List<String> espressoOut = new ArrayList<String>();
        espressoOut = runEspresso("");
        
        List<DGate> SOPgates = new ArrayList<DGate>();
        List<DGate> NORgates = new ArrayList<DGate>();
        
        SOPgates = parseEspressoOutput(espressoOut);
        
        System.out.println("POS format : ");
        
        if(functionOutp)
        {
                String gateString = printGate(SOPgates.get(0));
                System.out.println(gateString);
            
        }
        else
        {
            for(DGate g:SOPgates)
            {
               
                String gateString = printGate(g);
                System.out.println(gateString);
            }
        }
        
        NORgates = parseEspressoToNORNAND(espressoOut);
     
        System.out.println("\nUniversal Gates : ");
        if(functionOutp)
        {
                String gateString = printGate(NORgates.get(0));
                System.out.println(gateString);
            
        }
        else
        {
            for(DGate g:NORgates)
            {  
                String gateString = printGate(g);
                System.out.println(gateString);
            }
           
        }
        
    }
    public static void testABC()
    {
        String filename = "seg7";
        List<DGate> dseg7 = new ArrayList<DGate>();
        
        try {
            dseg7 = runABCverilog(filename);
        } catch (InterruptedException ex) {
            Logger.getLogger(NetSynth.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //dseg7 = removeDoubleInverters(dseg7);
        //dseg7 = outputORopt(dseg7);
        //dseg7 = convert2NOTsToNOR(dseg7);
        //dseg7 = rewireNetlist(dseg7);
        dseg7 = optimizeNetlist(dseg7,true,true);
        
        for(int i=0;i<dseg7.size();i++)
            System.out.println(printGate(dseg7.get(i)));
    }
    
    public static DAGW seg7dagw(String filename)
    {
        DAGW sevenseg = new DAGW();
        //String filename = "seg7";
        List<DGate> dseg7 = new ArrayList<DGate>();
        
        try {
            dseg7 = runABCverilog(filename);
        } catch (InterruptedException ex) {
            Logger.getLogger(NetSynth.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        //dseg7 = removeDoubleInverters(dseg7);
        //dseg7 = outputORopt(dseg7);
        //dseg7 = convert2NOTsToNOR(dseg7);
        //dseg7 = rewireNetlist(dseg7);
        
        dseg7 = optimizeNetlist(dseg7,true,true);
        
        for(int i=0;i<dseg7.size();i++)
            System.out.println(printGate(dseg7.get(i)));
        sevenseg = CreateMultDAGW(dseg7);
        
        return sevenseg;
    }
    
    
    
    public static List<DGate> runABC(String filename) throws InterruptedException 
    {
        initializeFilepath();
        String x = System.getProperty("os.name");
        StringBuilder commandBuilder = null;
        if(x.contains("Mac"))
        {
            commandBuilder = new StringBuilder(Filepath+"BU/resources/abc.mac -c \"read "+Filepath+"BU/resources/"+filename+".blif; strash;  rewrite; refactor; balance; write "+Filepath +"BU/resources/abcOutput.bench; quit\"");
        }
        else if("Linux".equals(x))
        {
            if(Filepath.contains("prashant"))
            {
                commandBuilder = new StringBuilder(Filepath+"src/BU/resources/abc -c \"read "+Filepath+"src/BU/resources/"+filename+".blif; strash; rewrite; refactor;  balance; write "+Filepath +"src/BU/resources/abcOutput.bench; quit\"");
            }
            else
            {
                commandBuilder = new StringBuilder(Filepath+"BU/resources/abc -c \"read "+Filepath+"BU/resources/"+filename+".blif; strash;  rewrite; refactor; balance; write "+Filepath +"BU/resources/abcOutput.bench; quit\"");
            }
            
        }
        
        String command = commandBuilder.toString();
        //System.out.println(command);
        
        String filestring = "";
        String clist = "";
        if (Filepath.contains("prashant")) 
        {
            filestring += Filepath + "src/BU/resources/script";
            clist = Filepath+"src/BU/resources/script";
        } 
        else 
        {
            filestring += Filepath + "BU/resources/script";
            clist = Filepath+"BU/resources/script";
        }
        File fespinp = new File(filestring);
        //Writer output;
        try 
        {
             Writer output = new BufferedWriter(new FileWriter(fespinp));
            //output = new BufferedWriter(new FileWriter(fespinp));
             output.write(command);
             output.close();
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(NetSynth.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        

        
        Runtime runtime = Runtime.getRuntime();
        Process proc = null;
        List<DGate> finalnetlist = new ArrayList<DGate>();
        try 
        {
            proc = runtime.exec(clist);
            proc.waitFor();
            finalnetlist = convertBenchToAIG();
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(NetSynth.class.getName()).log(Level.SEVERE, null, ex);
        }
        return finalnetlist;
        //convertBenchToAIG();
    }
    
    
    
    public static List<DGate> runABCverilog(String filename) throws InterruptedException 
    {
    
        String x = System.getProperty("os.name");
        StringBuilder commandBuilder = null;
        if(x.contains("Mac"))
        {
            commandBuilder = new StringBuilder(Filepath+"BU/resources/abc.mac -c \"read "+Filepath+"BU/resources/"+filename+".v; strash;  rewrite; refactor; balance; write "+Filepath +"BU/resources/abcOutput.bench; quit\"");
        }
        else if("Linux".equals(x))
        {
            if(Filepath.contains("prashant"))
            {
                commandBuilder = new StringBuilder(Filepath+"src/BU/resources/abc -c \"read "+Filepath+"src/BU/resources/"+filename+".v; strash; rewrite; refactor;  balance; write "+Filepath +"src/BU/resources/abcOutput.bench; quit\"");
            }
            else
            {
                commandBuilder = new StringBuilder(Filepath+"BU/resources/abc -c \"read "+Filepath+"BU/resources/"+filename+".v; strash;  rewrite; refactor; balance; write "+Filepath +"BU/resources/abcOutput.bench; quit\"");
            }
            
        }
        
        String command = commandBuilder.toString();
        //System.out.println(command);
        
        String filestring = "";
        if (Filepath.contains("prashant")) 
        {
            filestring += Filepath + "src/BU/resources/script";
        } 
        else 
        {
            filestring += Filepath + "BU/resources/script";
        }
        File fespinp = new File(filestring);
        //Writer output;
        try 
        {
             Writer output = new BufferedWriter(new FileWriter(fespinp));
            //output = new BufferedWriter(new FileWriter(fespinp));
             output.write(command);
             output.close();
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(NetSynth.class.getName()).log(Level.SEVERE, null, ex);
        }
        String clist ="";
        if (Filepath.contains("prashant")) 
        {
            clist = Filepath+"src/BU/resources/script";
        } 
        else 
        {
            clist = Filepath+"BU/resources/script";
        }
        
        
        
        Runtime runtime = Runtime.getRuntime();
        Process proc = null;
        List<DGate> finalnetlist = new ArrayList<DGate>();
        try 
        {
            proc = runtime.exec(clist);
            proc.waitFor();
            finalnetlist = convertBenchToAIG();
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(NetSynth.class.getName()).log(Level.SEVERE, null, ex);
        }
        return finalnetlist;
        //convertBenchToAIG();
    }
    
    public static List<DGate> convertBenchToAIG()
    {
        
        List<String> benchlines = new ArrayList<String>();
        String filestring = "";
        if (Filepath.contains("prashant")) 
        {
            filestring += Filepath + "src/BU/resources/abcOutput.bench";
        } 
        else 
        {
            filestring += Filepath + "BU/resources/abcOutput.bench";
        }
        
        File gate_file = new File(filestring);
	BufferedReader brgate;
	FileReader filebench;
	
	try {
	    filebench = new FileReader(gate_file);
	    brgate = new BufferedReader(filebench);
	    String line;
	    try 
            {
		while((line = brgate.readLine()) != null ) 
                {
	            benchlines.add(line);
                }
	    }
	    catch (IOException ex) {
		System.out.println("IOException when reading input file");
	    }
	} 
	catch (FileNotFoundException ex) {
	    System.out.println("FileNotFoundException when reading input file");
	}
        List<DGate> netlist = new ArrayList<DGate>();
        List<DWire> inputwires = new ArrayList<DWire>();
        List<DWire> outputwires = new ArrayList<DWire>();
        List<DWire> allwires = new ArrayList<DWire>();
        
        //for(int i=0;i<benchlines.size();i++)
        //{
        //    System.out.println(benchlines.get(i));
        //}
        for(int i=0;i<benchlines.size();i++)
        {
            if(benchlines.get(i).contains("INPUT"))
            {
                String inpname = benchlines.get(i).substring(benchlines.get(i).indexOf("INPUT(")+6);
                inpname = inpname.substring(0,inpname.indexOf(")"));
                inpname = inpname.trim();
                
                DWire inpw = new DWire(inpname,DWireType.input);
                inputwires.add(inpw);
                allwires.add(inpw);
            }
            if(benchlines.get(i).contains("OUTPUT"))
            {
                String outpname = benchlines.get(i).substring(benchlines.get(i).indexOf("OUTPUT(")+7);
                outpname = outpname.substring(0,outpname.indexOf(")"));
                outpname = outpname.trim();
                
                DWire outpw = new DWire(outpname,DWireType.output);
                outputwires.add(outpw);
                allwires.add(outpw);
            }
            if(benchlines.get(i).contains("="))
            {
                String outpwire = benchlines.get(i).substring(0,benchlines.get(i).indexOf("="));
                outpwire = outpwire.trim();
                int flag =0;
                for(DWire xwire:allwires)
                {
                    if(xwire.name.equals(outpwire))
                    {
                        flag =1;
                        break;
                    }
                }
                
                if(flag ==0)
                {
                    DWire connwire = new DWire(outpwire,DWireType.connector);
                    allwires.add(connwire);
                }
                String gatestring = benchlines.get(i).substring(benchlines.get(i).indexOf("=")+1);
                gatestring = gatestring.trim();
                if(gatestring.equals("vdd"))
                {
                    DGate xgate = new DGate();
                    xgate.gtype = DGateType.BUF;
                    xgate.input.add(one);
                    for(DWire xwire:allwires)
                    {
                        
                        if(xwire.name.equals(outpwire))
                        {
                            xgate.output = xwire;
                        }
                    }
                    netlist.add(xgate);
                }
                
                if(gatestring.contains("BUFF"))
                {
                    DGate xgate = new DGate();
                    xgate.gtype = DGateType.BUF;
                    String bufconn = gatestring.substring(gatestring.indexOf("(")+1,gatestring.indexOf(")"));
                    for(DWire xwire:allwires)
                    {
                        if(xwire.name.equals(bufconn))
                        {
                            xgate.input.add(xwire);
                        }
                        if(xwire.name.equals(outpwire))
                        {
                            xgate.output = xwire;
                        }
                    }
                    netlist.add(xgate);
                } 
                if(gatestring.contains("NOT"))
                {
                    DGate xgate = new DGate();
                    xgate.gtype = DGateType.NOT;
                    String notconn = gatestring.substring(gatestring.indexOf("(")+1,gatestring.indexOf(")"));
                    notconn = notconn.trim();
                    for(DWire xwire:allwires)
                    {
                        if(xwire.name.equals(notconn))
                        {
                            xgate.input.add(xwire);
                        }
                        if(xwire.name.equals(outpwire))
                        {
                            xgate.output = xwire;
                        }
                    }
                    netlist.add(xgate);
                }
                if(gatestring.contains("AND"))
                {
                    DGate xgate = new DGate();
                    xgate.gtype = DGateType.AND;
                    
                    String conn = gatestring.substring(gatestring.indexOf("(")+1,gatestring.indexOf(")"));
                    conn = conn.trim();
                    String[] connpieces = conn.split(",");
                    String conn1 = connpieces[0].trim();
                    String conn2 = connpieces[1].trim();
                    conn1 = conn1.trim();
                    conn2 = conn2.trim();
                    for(DWire xwire:allwires)
                    {
                        if(xwire.name.equals(conn1))
                        {
                            xgate.input.add(xwire);
                        }
                        if(xwire.name.equals(conn2))
                        {
                            xgate.input.add(xwire);
                        }
                        if(xwire.name.equals(outpwire))
                        {
                            xgate.output = xwire;
                        }
                    }
                    netlist.add(xgate);
                }
            }
        }
        //for(int i=0;i<netlist.size();i++)
        //    System.out.println(netlist(netlist.get(i)));
        List<DGate> netout = new ArrayList<DGate>();
        netout = convertAIGtoNORNOT(netlist);
        gate_file.deleteOnExit();
        return netout;
    }
    
    
    
          
    
    
    public static List<DGate> convertAIGtoNORNOT(List<DGate> netlist)
    {
        List<DGate> netout = new ArrayList<DGate>();
        
        List<DGate> notcreated = new ArrayList<DGate>();
        
        for(int i=0;i<netlist.size();i++)
        {
            if(netlist.get(i).gtype.equals(DGateType.NOT))
            {    
                netout.add(netlist.get(i));
            }
            if(netlist.get(i).gtype.equals(DGateType.BUF))
            {    
                netout.add(netlist.get(i));
            }
            if(netlist.get(i).gtype.equals(DGateType.AND))
            {
                int flag1 =0;
                int flag2 =0;
                DGate newnor = new DGate();
                newnor.gtype = DGateType.NOR;
                newnor.output = netlist.get(i).output;
                for(int j=0;j<notcreated.size();j++)
                {
                    if(netlist.get(i).input.get(0).name.equals(notcreated.get(j).input.get(0).name))
                    {
                        flag1 =1;
                        newnor.input.add(notcreated.get(j).output);
                        //break;
                    }
                    if(netlist.get(i).input.get(1).name.equals(notcreated.get(j).input.get(0).name))
                    {
                        flag2 =1;
                        newnor.input.add(notcreated.get(j).output);
                        //break;
                    }
                }
                if(flag1 ==0)
                {
                    String wirename = "0Wire" + Global.wirecount++;
                    DWire notout1 = new DWire(wirename,DWireType.connector);
                    DGate newnot1 = new DGate();
                    newnot1.gtype = DGateType.NOT;
                    newnot1.input.add(netlist.get(i).input.get(0));
                    newnot1.output = notout1;
                    newnor.input.add(newnot1.output);
                    netout.add(newnot1);
                    notcreated.add(newnot1);
                    
                }
                if(flag2 ==0)
                {
                    String wirename = "0Wire" + Global.wirecount++;
                    DWire notout2 = new DWire(wirename,DWireType.connector);
                    DGate newnot2 = new DGate();
                    newnot2.gtype = DGateType.NOT;
                    newnot2.input.add(netlist.get(i).input.get(1));
                    newnot2.output = notout2;
                    newnor.input.add(newnot2.output);
                    netout.add(newnot2);
                    notcreated.add(newnot2);
                }
                netout.add(newnor);
            }
        }
        //netout = optimizeNetlist(netout);
        
        //System.out.println("\n\n------------\nAfter AIG to NOR NOT and optmizing the result\n");
        //for(int i=0;i<netout.size();i++)
        //    System.out.println(netlist(netout.get(i)));
        
        return netout;
    }
    
    public static List<String> runEspresso(String pathFile) {
    
        Filepath = NetSynth.class.getClassLoader().getResource(".").getPath();
        if(Filepath.contains("build/classes/"))
            Filepath = Filepath.substring(0,Filepath.lastIndexOf("build/classes/")); 
        else if(Filepath.contains("src"))
            Filepath = Filepath.substring(0,Filepath.lastIndexOf("src/"));
        List<String> espressoOutput = new ArrayList<String>();
        String x = System.getProperty("os.name");
        StringBuilder commandBuilder = null;
        if(x.contains("Mac"))
        {
            commandBuilder = new StringBuilder(Filepath+"BU/resources/espresso.mac -epos "+ pathFile);
        }
        else if("Linux".equals(x))
        {
            if(Filepath.contains("prashant"))
            {
                commandBuilder = new StringBuilder(Filepath+"src/BU/resources/espresso.linux -epos "+ pathFile);
            }
            else
            {
                commandBuilder = new StringBuilder(Filepath+"BU/resources/espresso.linux -epos "+ pathFile);
            }
            
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
            String filestring = "";
            
            
             if(Filepath.contains("prashant"))
             {
                filestring += Filepath+ "src/BU/resources/write";
             }
            else
            {
                filestring += Filepath+ "BU/resources/write";
            }
        
            
            
            
            filestring += Global.espout++;
            filestring += ".txt";
            File fbool = new File(filestring);
            Writer output = new BufferedWriter(new FileWriter(fbool));
            InputStream in = proc.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line = null;
            
            while((line = br.readLine())!= null)
            {  
                espressoOutput.add(line);
                line += "\n";
                output.write(line);
            }
            output.close();
            fbool.deleteOnExit();
        } catch (IOException ex) {
            Logger.getLogger(NetSynth.class.getName()).log(Level.SEVERE, null, ex);
        }
         
        return espressoOutput;
    }
    
    
    public static void testnetlistmodule()
    {
        DGate and = new DGate();
        DWire w1 = new DWire("A",DWireType.input);
        DWire w2 = new DWire("B",DWireType.input);
        DWire outp = new DWire("outP",DWireType.output);
        List<DWire> inputWires = new ArrayList<DWire>();
        inputWires.add(w1);
        inputWires.add(w2);
        
        DGateType testgateType;
        testgateType = DGateType.NOR;
        DGate gtest = new DGate(testgateType,inputWires,outp);
        
        List<DGate> test = new ArrayList<DGate>();
        test = NetlistConversionFunctions.GatetoNORNOT(gtest);

        for(DGate gout:test)
        {
            
            String netbuilder = "";
            netbuilder = printGate(gout);
            System.out.println(netbuilder);
        
        }   
    }
    
    public static List<String> convertEspressoOutputToVerilog(List<String> espinp)
    {
        List<String> verilogfile = new ArrayList<String>();
        
        one = new DWire("_one",DWireType.Source);
        zero = new DWire("_zero",DWireType.GND);
        String inpNames = null;
        String outNames = null;
        int numberOfMinterms=0;
        int expInd=0;     
        List<String> inputWires = new ArrayList<String>();
        List<String> outputWires = new ArrayList<String>();
        
        List<DWire> inputINVWires = new ArrayList<DWire>();
        List<DGate> inputINVGates = new ArrayList<DGate>();
        List<Boolean> notGateexists = new ArrayList<Boolean>();
        List<Boolean> notGateAdd = new ArrayList<Boolean>();
        
        
        // <editor-fold defaultstate="collapsed" desc="Extract Input output and minterm lines from Espresso Output">
        for(int i=0;i<espinp.size();i++)
        {
            if(espinp.get(i).startsWith(".ilb"))
            {
                inpNames = (espinp.get(i).substring(5));
            }
            else if(espinp.get(i).startsWith(".ob"))
            {
                outNames = (espinp.get(i).substring(4));
            }
            else if(espinp.get(i).startsWith("#.phase"))
            {
                POSmode = true;
            }
            else if(espinp.get(i).startsWith(".p"))
            {
                numberOfMinterms = Integer.parseInt(espinp.get(i).substring(3));
                expInd = i+1;
                break;
            }
        }
        // </editor-fold>
                
        // <editor-fold defaultstate="collapsed" desc="Get Input Names">
        for(String splitInp:inpNames.split(" "))
        {
            if(splitInp.equals(one.name) || splitInp.equals(zero.name))
                splitInp += "I";
            inputWires.add(splitInp.trim());
            //System.out.println("INPUT : "+splitInp);
        }
        // </editor-fold>
             
        // <editor-fold defaultstate="collapsed" desc="Get Output Names">
        for(String splitInp:outNames.split(" "))
        {
            if(splitInp.equals(one.name) || splitInp.equals(zero.name))
                splitInp += "O";
            outputWires.add(splitInp.trim());
            //System.out.println("OUTPUT : "+splitInp);
        }
        // </editor-fold>        
        
        verilogfile.add("module EspToVerilog (");
        String inputlist = "";
        for(int i=0;i<inputWires.size();i++)
        {
            inputlist += inputWires.get(i);
            if(i!= (inputWires.size()-1))
                inputlist += ", ";
        }
        String moduleinput = inputlist + ",";
        verilogfile.add(moduleinput);
        
        String outputlist = "";
        for(int i=0;i<outputWires.size();i++)
        {
            outputlist += outputWires.get(i);
            if(i!= (outputWires.size()-1))
                outputlist += ", ";
        }
        String moduleoutput = outputlist + " );";
        verilogfile.add(moduleoutput);
        String inputdeclare = "input "  + inputlist + ";";
        String outputdeclare = "output "  + outputlist + ";";
        verilogfile.add(inputdeclare);
        verilogfile.add(outputdeclare);
        // <editor-fold defaultstate="collapsed" desc="Declare wires for verilog file">
        String wiredeclaration = "wire";
        List<String> wirenames = new ArrayList<String>();
        for(int i=0;i<numberOfMinterms;i++)
        {
            String wName = "w" + i;
            wiredeclaration += (" " + wName);
            wirenames.add(wName);
            if(i== numberOfMinterms-1)
                wiredeclaration += ";";
            else
                wiredeclaration += ",";
        }
        // </editor-fold>
        
        verilogfile.add(wiredeclaration);
        
        List<List<DWire>> outputsum = new ArrayList<List<DWire>>();
        List<List<String>> outputassignlines = new ArrayList<List<String>>();
        
        
        for(int i=0;i<outputWires.size();i++)
        {
            List<String> outassign = new ArrayList<String>();
            outputassignlines.add(outassign);
            List<DWire> outsums = new ArrayList<DWire>();
            outputsum.add(outsums);
        }
        
        int wIndx =0;
        for(int i=expInd;i<espinp.size()-1;i++)
        {
            //int null_literal =0;
            List<DWire> sumterm  = new ArrayList<DWire>();
            String assgnwires = ("assign " + wirenames.get(wIndx) + " =");
            String maxterm[] = espinp.get(i).split(" ");
            boolean startassign = false;
            for(int j=0;j<inputWires.size();j++)
            {
                if(maxterm[0].charAt(j) == '1')
                {
                    if(startassign)
                    {
                        assgnwires += " |";
                    }
                    startassign = true;
                    assgnwires += " ~"+inputWires.get(j);
                    
                }
                else if(maxterm[0].charAt(j) == '0')
                {
                    if(startassign)
                    {
                        assgnwires += " |";
                    }
                    startassign = true;
                    assgnwires += " "+inputWires.get(j);
                }
                
                if (j == (inputWires.size() - 1)) 
                {
                    assgnwires += ";";
                }
                
            }
           
            verilogfile.add(assgnwires);
            
            
            
            
            
            /*List<DGate> sumtermG = new ArrayList<DGate>();
            DWire sumlast = new DWire();
            if(sumterm.size() >0)
            {
                if(sumterm.size() == 1)
                {
                    List<DWire> notsumterminp = new ArrayList<DWire>();
                    notsumterminp.addAll(sumterm);
                    String Wirename = "0Wire" + Global.wirecount++;
                    DWire notsumtermout = new DWire(Wirename);
                    DGate notsumtermgate = new DGate(DGateType.NOT,notsumterminp,notsumtermout);
                    
                    sumlast = notsumtermout;
                }
                else
                {
                    //System.out.println("Sumterm size :" + sumterm.size());
                    sumtermG = NORNANDGates(sumterm,DGateType.NOR);
                   
                    sumlast = sumtermG.get(sumtermG.size()-1).output;
                }
            }
            else
            {
                sumlast = new DWire(zero);
                //System.out.println("Output connected to Ground");
            }*/
            for(int j=0;j<outputWires.size();j++)
            {
                if(maxterm[1].charAt(j) == '1')
                {
                    outputassignlines.get(j).add(wirenames.get(wIndx));
                    //outputsum.get(j).add(sumlast);
                }
            }
            wIndx++;
        }
        
        for(int i=0;i<outputassignlines.size();i++)
        {
            String tempoutassign = "assign " + outputWires.get(i) + " = ";
            for(int j=0;j<outputassignlines.get(i).size();j++)
            {
                tempoutassign += (" " +outputassignlines.get(i).get(j));
                if(j==(outputassignlines.get(i).size()-1))
                    tempoutassign += ";";
                else
                    tempoutassign += " &";
            }
            verilogfile.add(tempoutassign);
        }
        verilogfile.add("endmodule");
        /*for(int j=0;j<outputWires.size();j++)
        {
            
            if(outputsum.get(j).isEmpty())
            {
                List<DWire> inputbuflist = new ArrayList<DWire>();
                inputbuflist.add(one);
                //DGate bufgate = new DGate(DGateType.BUF,inputbuflist,outputWires.get(j));
                
                //System.out.println("Source = output");
            }
            else if(outputsum.get(j).size() == 1)
            {
                if(outputsum.get(j).get(0).wtype == DWireType.GND)
                {
                    List<DWire> inputbuflist = new ArrayList<DWire>();
                    inputbuflist.add(zero);
                    //DGate bufgate = new DGate(DGateType.BUF,inputbuflist,outputWires.get(j));
                    
                    //System.out.println("Ground = output");
                }
                else
                {
                    List<DWire> singleNOTmaxterm = new ArrayList<DWire>();
                    singleNOTmaxterm.add(outputsum.get(j).get(0));
                    //DGate singlemaxtermgate = new DGate(DGateType.NOT, singleNOTmaxterm,outputWires.get(j));
                    
                    //System.out.println("Single Maxterm which enters a not Gate, the output of which is a circuit output!!");
                }
            }
            else
            {
                List<DGate> productGates = new ArrayList<DGate>();
                productGates = NORNANDGates(outputsum.get(j),DGateType.NOR);
                //productGates.get(productGates.size()-1).output = outputWires.get(j);
               
                
                //System.out.println("OUTPUT SUM Terms: " +outputsum.get(j).size());    
            }
        }*/
        //System.out.println("\n----------------------------\nVerilog File\n----------------------------\n");
        //for(String xline:verilogfile)
        //    System.out.println(xline);
        
        return verilogfile;
      
    }
    
    
    public static List<DGate> parseEspressoOutput(List<String> espinp)
    {
        one = new DWire("_one",DWireType.Source);
        zero = new DWire("_zero",DWireType.GND);
        List<DGate> sopexp = new ArrayList<DGate>();
        List<DWire> wireInputs = new ArrayList<DWire>();
        List<DWire> wireOutputs = new ArrayList<DWire>();
        List<DWire> invWires = new ArrayList<DWire>();
        List<DGate> inpInv = new ArrayList<DGate>();
        functionOutp = false;
        String inpNames = "";
        String outNames = "";
        POSmode = false;
        int numberOfMinterms=0;
        int expInd=0;        

        // <editor-fold defaultstate="collapsed" desc="Extract Input output and minterm lines from Espresso Output">
        for(int i=0;i<espinp.size();i++)
        {
            if(espinp.get(i).startsWith(".ilb"))
            {
                inpNames = (espinp.get(i).substring(5));
            }
            else if(espinp.get(i).startsWith(".ob"))
            {
                outNames = (espinp.get(i).substring(4));
            }
            else if(espinp.get(i).startsWith("#.phase"))
            {
                POSmode = true;
            }
            else if(espinp.get(i).startsWith(".p"))
            {
                numberOfMinterms = Integer.parseInt(espinp.get(i).substring(3));
                expInd = i+1;
                break;
            }
        }
        // </editor-fold>
                
        // <editor-fold defaultstate="collapsed" desc="Get Input Names">
        for(String splitInp:inpNames.split(" "))
        {
            if(splitInp.equals(one.name) || splitInp.equals(zero.name))
                splitInp += "I";
            wireInputs.add(new DWire(splitInp,DWireType.input));
        }
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Create Not gates and NOT wires">
        inpInv = notGates(wireInputs);
        for(DGate gnots:inpInv)
        {
            invWires.add(gnots.output);
        }
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Get Output Names">
        for(String splitInp:outNames.split(" "))
        {
            if(splitInp.equals(one.name) || splitInp.equals(zero.name))
                splitInp += "O";
            wireOutputs.add(new DWire(splitInp,DWireType.output));
        }
        // </editor-fold>        
        
        // <editor-fold defaultstate="collapsed" desc="Minterms = 0 and Output = 1 or 0">
        if(numberOfMinterms == 0)
        {
            functionOutp = true;
            List<DWire> inp01 = new ArrayList<DWire>();
            
            if(POSmode)
            {
                inp01.add(one);
            }
            else
            {
                inp01.add(zero);
            }
            sopexp.add(new DGate(DGateType.BUF,inp01,wireOutputs.get(0)));
            return sopexp;
        }
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Minterm = 1 and Output = 1 or 0">
        else if(numberOfMinterms == 1)
        {
            String oneMinT = espinp.get(expInd).substring(0, (wireInputs.size()));
            int flag =0;
            for(int j=0;j<wireInputs.size();j++)
            {
                if(oneMinT.charAt(j) != '-')
                {
                    flag =1;
                    break;
                }
            }
            if(flag == 0)
            {
            functionOutp = true;
            List<DWire> inp01 = new ArrayList<DWire>();
            
            if(POSmode)
            {
                inp01.add(zero);
            }
            else
            {
                inp01.add(one);
            }
            sopexp.add(new DGate(DGateType.BUF,inp01,wireOutputs.get(0)));
            return sopexp;    
            }
                
        }
        // </editor-fold>
        
        
        List<DWire> minTemp = new ArrayList<DWire>();
        List<DWire> orWires = new ArrayList<DWire>();
        List<DGate> prodGates;
        
        for(int i=expInd;i<(expInd+numberOfMinterms);i++)
        {
            
            String minT = espinp.get(i).substring(0, (wireInputs.size()));
            prodGates = new ArrayList<DGate>();
            minTemp = new ArrayList<DWire>();
            
            // <editor-fold defaultstate="collapsed" desc="Find a Minterm/Maxterm">
            for(int j=0;j<wireInputs.size();j++)
            {
                if(minT.charAt(j)=='-')
                    continue;
                else if(minT.charAt(j) == '0')
                {
                    if(POSmode)
                    {
                        minTemp.add(wireInputs.get(j));
                    }
                    else
                    {
                        if(!sopexp.contains(inpInv.get(j)))
                        {
                            sopexp.add(inpInv.get(j));                        
                        }
                        minTemp.add(inpInv.get(j).output);
                    }   
                }
                else if(minT.charAt(j) == '1')
                {
                    if(POSmode)
                    {
                        if(!sopexp.contains(inpInv.get(j)))
                        {
                            sopexp.add(inpInv.get(j));                        
                        }
                        minTemp.add(inpInv.get(j).output);
                    }
                    else
                    {
                        minTemp.add(wireInputs.get(j));
                    }
                } 
            }
            // </editor-fold>

            
            if(minTemp.size() == 1)
            {
                orWires.add(minTemp.get(0));
            }
            else
            {
                if(POSmode)
                {
                    prodGates = AndORGates(minTemp,DGateType.OR);         
                }
                else
                {
                    prodGates = AndORGates(minTemp,DGateType.AND);         
                }
                orWires.add(prodGates.get(prodGates.size()-1).output);
                sopexp.addAll(prodGates);
            }
        }
        
        prodGates = new ArrayList<DGate>();
        if(POSmode)
        {
            prodGates = AndORGates(orWires,DGateType.AND);
        }
        else
        {
            prodGates = AndORGates(orWires,DGateType.OR);
        }
        sopexp.addAll(prodGates);
        if(sopexp.isEmpty())
        {
            DGate bufgate = new DGate(DGateType.BUF,orWires,wireOutputs.get(0));
            sopexp.add(bufgate);
        }
        else
            sopexp.get(sopexp.size()-1).output = wireOutputs.get(0);
        return sopexp;
    }
    
    public static List<DGate> convertPOStoNORNOT(List<String> espinp)
    {
        
        //for(String xespinp:espinp)
        //    System.out.println(xespinp);
        
        List<DGate> netlist = new ArrayList<DGate>();
        one = new DWire("_one",DWireType.Source);
        zero = new DWire("_zero",DWireType.GND);
        String inpNames = null;
        String outNames = null;
        int numberOfMinterms;
        int expInd=0;     
        List<DWire> inputWires = new ArrayList<DWire>();
        List<DWire> outputWires = new ArrayList<DWire>();
        
        List<DWire> inputINVWires = new ArrayList<DWire>();
        List<DGate> inputINVGates = new ArrayList<DGate>();
        List<Boolean> notGateexists = new ArrayList<Boolean>();
        List<Boolean> notGateAdd = new ArrayList<Boolean>();
        
        
        // <editor-fold defaultstate="collapsed" desc="Extract Input output and minterm lines from Espresso Output">
        for(int i=0;i<espinp.size();i++)
        {
            if(espinp.get(i).startsWith(".ilb"))
            {
                inpNames = (espinp.get(i).substring(5));
            }
            else if(espinp.get(i).startsWith(".ob"))
            {
                outNames = (espinp.get(i).substring(4));
            }
            else if(espinp.get(i).startsWith("#.phase"))
            {
                POSmode = true;
            }
            else if(espinp.get(i).startsWith(".p"))
            {
                numberOfMinterms = Integer.parseInt(espinp.get(i).substring(3));
                expInd = i+1;
                break;
            }
        }
        // </editor-fold>
                
        // <editor-fold defaultstate="collapsed" desc="Get Input Names">
        for(String splitInp:inpNames.split(" "))
        {
            if(splitInp.equals(one.name) || splitInp.equals(zero.name))
                splitInp += "I";
            inputWires.add(new DWire(splitInp,DWireType.input));
            //System.out.println("INPUT : "+splitInp);
        }
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Create Not gates and NOT wires">
        inputINVGates = notGates(inputWires);
        
        for(DGate gnots:inputINVGates)
        {
            notGateexists.add(false);
            notGateAdd.add(false);
            inputINVWires.add(gnots.output);
        }
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Get Output Names">
        for(String splitInp:outNames.split(" "))
        {
            if(splitInp.equals(one.name) || splitInp.equals(zero.name))
                splitInp += "O";
            outputWires.add(new DWire(splitInp,DWireType.output));
            //System.out.println("OUTPUT : "+splitInp);
        }
        // </editor-fold>        
        
        List<List<DWire>> outputsum = new ArrayList<List<DWire>>();
        
        for(int i=0;i<outputWires.size();i++)
        {
            List<DWire> outsums = new ArrayList<DWire>();
            outputsum.add(outsums);
        }
        
        for(int i=expInd;i<espinp.size()-1;i++)
        {
            //int null_literal =0;
            List<DWire> sumterm  = new ArrayList<DWire>();
            String maxterm[] = espinp.get(i).split(" ");
            for(int j=0;j<inputWires.size();j++)
            {
                 
                if(maxterm[0].charAt(j) == '1')
                {
                    if(notGateexists.get(j) == false)
                    {
                        netlist.add(inputINVGates.get(j));
                        notGateexists.set(j,true); 
                    }
                    sumterm.add(inputINVWires.get(j));
                }
                else if(maxterm[0].charAt(j) == '0')
                {
                    sumterm.add(inputWires.get(j));
                }
                /*else 
                {
                    null_literal++;
                }*/
                       
            }
            List<DGate> sumtermG = new ArrayList<DGate>();
            DWire sumlast = new DWire();
            if(sumterm.size() >0)
            {
                if(sumterm.size() == 1)
                {
                    List<DWire> notsumterminp = new ArrayList<DWire>();
                    notsumterminp.addAll(sumterm);
                    String Wirename = "0Wire" + Global.wirecount++;
                    DWire notsumtermout = new DWire(Wirename);
                    DGate notsumtermgate = new DGate(DGateType.NOT,notsumterminp,notsumtermout);
                    netlist.add(notsumtermgate);
                    sumlast = notsumtermout;
                }
                else
                {
                    //System.out.println("Sumterm size :" + sumterm.size());
                    sumtermG = NORNANDGates(sumterm,DGateType.NOR);
                    netlist.addAll(sumtermG);
                    sumlast = sumtermG.get(sumtermG.size()-1).output;
                }
            }
            else
            {
                sumlast = new DWire(zero);
                //System.out.println("Output connected to Ground");
            }
            for(int j=0;j<outputWires.size();j++)
            {
                if(maxterm[1].charAt(j) == '1')
                {
                    outputsum.get(j).add(sumlast);
                }
            }
        }
        
        for(int j=0;j<outputWires.size();j++)
        {
            
            if(outputsum.get(j).isEmpty())
            {
                List<DWire> inputbuflist = new ArrayList<DWire>();
                inputbuflist.add(one);
                DGate bufgate = new DGate(DGateType.BUF,inputbuflist,outputWires.get(j));
                netlist.add(bufgate);
                //System.out.println("Source = output");
            }
            else if(outputsum.get(j).size() == 1)
            {
                if(outputsum.get(j).get(0).wtype == DWireType.GND)
                {
                    List<DWire> inputbuflist = new ArrayList<DWire>();
                    inputbuflist.add(zero);
                    DGate bufgate = new DGate(DGateType.BUF,inputbuflist,outputWires.get(j));
                    netlist.add(bufgate);
                    //System.out.println("Ground = output");
                }
                else
                {
                    List<DWire> singleNOTmaxterm = new ArrayList<DWire>();
                    singleNOTmaxterm.add(outputsum.get(j).get(0));
                    DGate singlemaxtermgate = new DGate(DGateType.NOT, singleNOTmaxterm,outputWires.get(j));
                    netlist.add(singlemaxtermgate);
                    //System.out.println("Single Maxterm which enters a not Gate, the output of which is a circuit output!!");
                }
            }
            else
            {
                List<DGate> productGates = new ArrayList<DGate>();
                productGates = NORNANDGates(outputsum.get(j),DGateType.NOR);
                productGates.get(productGates.size()-1).output = outputWires.get(j);
                netlist.addAll(productGates);
                
                //System.out.println("OUTPUT SUM Terms: " +outputsum.get(j).size());    
            }
        }
        
        /*System.out.println("\nNetlist after POS to NOT NOR conversion:\n");
        for(int i=0;i<netlist.size();i++)
        {   
            System.out.println(netlist(netlist.get(i)));
        }
        System.out.println("-----------\n");*/
        
        //String ttval = BooleanSimulator.bpermuteEsynth(netlist);
        //System.out.println("This is the truthTable : "+ttval);
        
        return netlist;
    }
    
    
    
    public static List<DGate> rewireNetlist(List<DGate> netlist)
    {
        for(int i=0;i<netlist.size();i++)
        {
            for(int j=0;j<=i;j++)
            {
                if(i!=j)
                {
                    for(int m=0;m<netlist.get(i).input.size();m++)
                    {
                        if(netlist.get(i).input.get(m).name.equals(netlist.get(j).output.name))
                        {
                            netlist.get(i).input.set(m, netlist.get(j).output);
                        }
                    }
                }
            }
        }
        return netlist;
    }
    
    
    /**Function*************************************************************
    Synopsis    [Search for the 2 NOTs to NOR motif and replace it with a NOR gate]
    Description []
    SideEffects []
    SeeAlso     []
    ***********************************************************************/
    public static List<DGate> convert2NOTsToNOR(List<DGate> netlistinp)
    {
        List<Integer> removegates = new ArrayList<Integer>();
        List<Integer> addgates = new ArrayList<Integer>();
        
        List<DGate> netlistout = new ArrayList<DGate>();
        List<DGate> gatestoadd = new ArrayList<DGate>();
        //System.out.println("Before\n----------------------------");
        //for(int i=0;i<netlistinp.size();i++)
        //    System.out.println(i+ " :" +printGate(netlistinp.get(i)));
        
        for(int i=0;i<netlistinp.size();i++)
        {
            if(netlistinp.get(i).gtype.equals(DGateType.NOR))
            {
                int inp1flag =0;
                int inp2flag =0;
                String inpstring1 ="";
                String inpstring2 ="";
                DWire w1 = new DWire();
                DWire w2 = new DWire();
                
                //String inp2string ="";
                
                for(int k=0;k<=i;k++)
                {
                    if(i!=k)
                    {
                        if(netlistinp.get(k).gtype.equals(DGateType.NOT))
                        {
                            if(netlistinp.get(i).input.get(0).name.equals(netlistinp.get(k).output.name))
                            {
                                //System.out.println(netlist(netlistinp.get(i)) + " trigger for inp1flag");
                                inp1flag =1;
                                inpstring1 = netlistinp.get(k).input.get(0).name;
                                inpstring2 = netlistinp.get(i).input.get(1).name;
                                w1 = new DWire(netlistinp.get(k).input.get(0));
                                w2 = new DWire(netlistinp.get(i).input.get(1));
                                
                                //System.out.println("Wire 1 " + inpstring1);
                                //System.out.println("Wire 2 " + inpstring2);
                            }
                            if(netlistinp.get(i).input.get(1).name.equals(netlistinp.get(k).output.name))
                            {
                                inp2flag =1;
                                
                                //System.out.println(netlist(netlistinp.get(i)) + " trigger for inp2flag");
                                inpstring1 = netlistinp.get(k).input.get(0).name;
                                inpstring2 = netlistinp.get(i).input.get(0).name;
                                w1 = new DWire(netlistinp.get(k).input.get(0));
                                w2 = new DWire(netlistinp.get(i).input.get(0));
                                
                                //System.out.println("Wire 1 " + inpstring1);
                                //System.out.println("Wire 2 " + inpstring2);
                            
                            }
                        }
                    }
                }
                int inp2mainflag = 0;
                int gate2flag = 0;
                int secondnorgatepos =0;
                String invstring2 = "";
                for (int k = 0; k < netlistinp.size(); k++) 
                {
                    if (netlistinp.get(k).gtype.equals(DGateType.NOT)) 
                    {
                        if (netlistinp.get(k).input.get(0).name.equals(inpstring2)) 
                        {
                            invstring2 = netlistinp.get(k).output.name;
                            inp2mainflag = 1;
                            //System.out.println(netlist(netlistinp.get(k))+" trigger for inp2mainflag");
                        }
                    }
                }
                if ((inp1flag + inp2flag) == 1) 
                {
                    if (inp2mainflag == 1) 
                    {
                        //System.out.println("inp1flag or inp2flag are 1 not both and inp2mainflag is on");
                        for (int j = i; j < netlistinp.size(); j++) 
                        {
                            if (j != i) 
                            {
                                if (netlistinp.get(j).gtype.equals(DGateType.NOR)) 
                                {
                                    if(netlistinp.get(j).input.get(0).name.equals(inpstring1) && netlistinp.get(j).input.get(1).name.equals(invstring2))
                                    {
                                        gate2flag = 1;
                                        secondnorgatepos = j;
                                        //System.out.println(netlist(netlistinp.get(j))+" 2nd NOR gate located");
                                        //addgates.add(i);
                                        break;
                                    }
                                    if(netlistinp.get(j).input.get(1).name.equals(inpstring1) && netlistinp.get(j).input.get(0).name.equals(invstring2))
                                    {
                                        gate2flag = 2;
                                        secondnorgatepos = j;
                                        //System.out.println(netlist(netlistinp.get(j))+" 2nd NOR gate located");
                                        //addgates.add(i);
                                        break;
                                    }
                                }
                            }
                        }
                        
                        int norgateexists =0;
                        DWire noroutW = new DWire();
                        int norpos =0;
                        if((gate2flag == 1) || (gate2flag == 2))
                        {
                            //System.out.println("gate2flag triggered");
                            for(int k=0;k<netlistinp.size();k++)
                            {
                                if(netlistinp.get(k).gtype.equals(DGateType.NOR))
                                {
                                    if((netlistinp.get(k).input.get(0).name.equals(inpstring1) && netlistinp.get(k).input.get(1).name.equals(inpstring2))||(netlistinp.get(k).input.get(1).name.equals(inpstring1) && netlistinp.get(k).input.get(0).name.equals(inpstring2)))
                                    {
                                        if(!netlistinp.get(k).output.wtype.equals(DWireType.output))
                                        {
                                            norgateexists =1;
                                            norpos = k;
                                            noroutW = new DWire(netlistinp.get(k).output.name,netlistinp.get(k).output.wtype);
                                            if(k>i)
                                            {
                                                addgates.add(i);
                                                gatestoadd.add(netlistinp.get(k));
                                            }
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                        
                        if(norgateexists == 1)
                        {
                            
                            if(gate2flag == 1)
                            {
                                //System.out.println("gate2flag =1");
                                //System.out.println(secondnorgatepos+"+"+netlist(netlistinp.get(secondnorgatepos)));
                                netlistinp.get(secondnorgatepos).input.remove(1);
                                netlistinp.get(secondnorgatepos).input.add(noroutW);
                            }
                            else if(gate2flag == 2)
                            {
                                //System.out.println("gate2flag =2");
                                //System.out.println(secondnorgatepos+"+"+netlist(netlistinp.get(secondnorgatepos)));
                                netlistinp.get(secondnorgatepos).input.remove(0);
                                netlistinp.get(secondnorgatepos).input.add(noroutW);
                            }
                            if(inp1flag == 1)
                            {
                                //System.out.println("inp1flag =1");
                                //System.out.println(norpos+"+"+netlist(netlistinp.get(i)));
                                netlistinp.get(i).input.remove(0);
                                netlistinp.get(i).input.add(noroutW);
                            }
                            else if(inp2flag == 1)
                            {
                                //System.out.println("inp2flag =1");
                                //System.out.println(norpos+"+"+netlist(netlistinp.get(i)));
                                netlistinp.get(i).input.remove(1);
                                netlistinp.get(i).input.add(noroutW);
                            }
                            //System.out.println(noroutW.name);
                        }
                        else
                        {
                            
                            
                            if(gate2flag == 1 || gate2flag == 2)
                            {
                            
                                //System.out.println("NOR Gate does not exist");
                                DGate addnor = new DGate();
                                addnor.gtype = DGateType.NOR;
                                addnor.input.add(w1);
                                addnor.input.add(w2);
                            
                                String noroutWname = "0Wire" + Global.wirecount++;
                                noroutW = new DWire(noroutWname,DWireType.connector);
                                addnor.output = noroutW;
                                if(gate2flag == 1)
                                {
                                    netlistinp.get(secondnorgatepos).input.remove(1);
                                    netlistinp.get(secondnorgatepos).input.add(noroutW);
                                }
                                else if(gate2flag == 2)
                                {
                                    netlistinp.get(secondnorgatepos).input.remove(0);
                                    netlistinp.get(secondnorgatepos).input.add(noroutW);
                                }
                                if(inp1flag == 1)
                                {
                                    netlistinp.get(i).input.remove(0);
                                    netlistinp.get(i).input.add(noroutW);
                                }
                                else if(inp2flag == 1)
                                {
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
        for(int i=0;i<netlistinp.size();i++)
        {
            if(addgates.contains(i))
            {
                int pos = addgates.indexOf(i);
                netlistout.add(gatestoadd.get(pos));
            }
            netlistout.add(netlistinp.get(i));
        }
        
        
        
        List<DGate> finalnetlist;
        //<editor-fold desc="Remove Dangling Wires">
        //do{
        removegates = new ArrayList<Integer>();
        for(int i=0;i<netlistout.size()-1;i++)
        {
            String wireout = netlistout.get(i).output.name;
            if(netlistout.get(i).output.wtype.equals(DWireType.connector))
            {
                int inpcount =0;
                for(int j=i;j<netlistout.size();j++)
                {
                    if(i!=j)
                    {
                        for(int m=0;m<netlistout.get(j).input.size();m++)
                        {
                            if(netlistout.get(j).input.get(m).name.equals(wireout))
                                inpcount++;
                        }
                    }
                }
                if(inpcount == 0)
                    removegates.add(i);
            }
        }
            finalnetlist = new ArrayList<DGate>();
            for(int i=0;i<netlistout.size();i++)
            {
                if(!removegates.contains(i))
                    finalnetlist.add(netlistout.get(i));
            }
        //    netlistout = new ArrayList<DGate>();
        //    for(int i=0;i<finalnetlist.size();i++)
        //        netlistout.add(finalnetlist.get(i));
        //}while(removegates.size()!=0);
        //</editor-fold>
        
        //Start Removing duplicate NOR gates  
        removegates = new ArrayList<Integer>();
        for(int i=0;i<finalnetlist.size();i++)
        {
            if(finalnetlist.get(i).gtype.equals(DGateType.NOR) && (!finalnetlist.get(i).output.wtype.equals(DWireType.output)))
            {
                String inp1name = finalnetlist.get(i).input.get(0).name.trim();
                String inp2name = finalnetlist.get(i).input.get(1).name.trim();
                DWire noroutW  = new DWire(finalnetlist.get(i).output.name,finalnetlist.get(i).output.wtype);
                for(int j=i;j<finalnetlist.size();j++)
                {
                    if(i!=j)
                    {
                        if(finalnetlist.get(j).gtype.equals(DGateType.NOR) && (!finalnetlist.get(j).output.wtype.equals(DWireType.output))  )
                        {
                            String outname = finalnetlist.get(j).output.name.trim();
                            if( (finalnetlist.get(j).input.get(0).name.equals(inp1name) && finalnetlist.get(j).input.get(1).name.equals(inp2name)) || (finalnetlist.get(j).input.get(0).name.equals(inp2name) && finalnetlist.get(j).input.get(1).name.equals(inp1name)) )
                            {
                                //System.out.println(noroutW.name);
                                for(int k=j;k<finalnetlist.size();k++)
                                {
                                    if(k!=j)
                                    {
                                        for(int m=0;m<finalnetlist.get(k).input.size();m++)
                                        {
                                            if(finalnetlist.get(k).input.get(m).name.equals(outname))
                                            {
                                                finalnetlist.get(k).input.set(m, noroutW);
                                            }
                                        }
                                    }
                                }
                                if(!removegates.contains(j))
                                    removegates.add(j);
                            }
                        }
                    }
                }
                
            }
        }
        
        List<DGate> finalnetout = new ArrayList<DGate>();
        for(int i=0;i<finalnetlist.size();i++)
        {
            if(!removegates.contains(i))
                finalnetout.add(finalnetlist.get(i));
        }
        
        //System.out.println("\n\n\nAfter-------------------------");
        //for(int i=0;i<finalnetout.size();i++)
        //    System.out.println(printGate(finalnetout.get(i)));
        //System.out.println("--------------------------\n\n");
        
        return finalnetout;
    }
    
    
    /**Function*************************************************************
    Synopsis    [Remove Double inverters motif]
    Description []
    SideEffects []
    SeeAlso     []
    ***********************************************************************/
    public static List<DGate> removeDoubleInverters(List<DGate> netlistinp)
    {
        //Remove Redundant NOT Gates
        
        
        
        List<Integer> removegates = new ArrayList<Integer>();
        for(int i=0;i<netlistinp.size()-1;i++)
        {
            if(netlistinp.get(i).gtype.equals(DGateType.NOT)) //Gate i is a NOT gate
            {
                for(int j=(i+1);j<netlistinp.size();j++) // Look at all gates j after Gate i
                {
                    if(netlistinp.get(j).gtype.equals(DGateType.NOT))
                    {
                   if(netlistinp.get(i).output.name.equals(netlistinp.get(j).input.get(0).name)) // If input of Gate j is the output of Gate i
                   {
                       if(netlistinp.get(j).output.wtype.equals(DWireType.output)) // if output of Gate j is an output for the circuit
                       {
                           if(netlistinp.get(i).input.get(0).wtype.equals(DWireType.input)) // input of Gate i is an input for the circuit
                           {
                               /*System.out.println("Before Step 1");
                               System.out.println(i+":"+netlist(netlistinp.get(i)));
                               System.out.println(j+":"+netlist(netlistinp.get(j)));*/
                               DWire newW = new DWire(netlistinp.get(i).input.get(0).name,netlistinp.get(i).input.get(0).wtype);
                               netlistinp.get(j).input.remove(0);
                               netlistinp.get(j).input.add(newW);
                               
                               netlistinp.get(j).gtype = DGateType.BUF;
                               
                               /*System.out.println("After Step 1");
                               System.out.println(i+":"+netlist(netlistinp.get(i)));
                               System.out.println(j+":"+netlist(netlistinp.get(j)));*/
                               
                               int dntremoveflag =0;
                               for(int k=(i+1);k<netlistinp.size();k++)
                               {
                                   if((k!=j) && (!netlistinp.get(k).output.wtype.equals(DWireType.output)))
                                   {
                                        for(int m=0;m<netlistinp.get(k).input.size();m++)
                                        {
                                            if(netlistinp.get(k).input.get(m).name.equals(netlistinp.get(j).output.name))
                                            {
                                                dntremoveflag = 1;
                                            }
                                        }
                                   }
                               }
                               if(dntremoveflag == 0)
                               {
                                   if(!removegates.contains(i))
                                   {
                                       int finalflag =0;
                                       for(int m=i+1;m<netlistinp.size();m++)
                                       {
                                           if(netlistinp.get(m).gtype.equals(DGateType.NOR))
                                           {
                                               for(DWire xinp: netlistinp.get(m).input)
                                               {
                                                   if(xinp.name.trim().equals(netlistinp.get(i).output.name.trim()))
                                                   {
                                                       finalflag =1;
                                                   }
                                               }
                                           }
                                       }
                                       if(finalflag == 0)
                                           removegates.add(i);
                                        //System.out.println("Remove Gate index:"+i);
                                       
                                   }
                               }
                           }
                           else // input of Gate i is a connecting wire from another gate. 
                           {
                               String outwName = "";
                               int inpcount =0;
                               int outwindx = 0;
                               for(int k=0;k<netlistinp.size();k++)
                               {
                                   if(netlistinp.get(k).output.name.equals(netlistinp.get(i).input.get(0).name))
                                   {
                                       outwindx = k;
                                       outwName = netlistinp.get(k).output.name;
                                   }
                               }
                               for(int k=outwindx;k<netlistinp.size();k++)
                               {
                                   if(k!=i)
                                   {
                                       for(int m=0;m<netlistinp.get(k).input.size();m++)
                                       {
                                           if(netlistinp.get(k).input.get(m).name.equals(outwName))
                                           {
                                               inpcount++;
                                           }
                                       }
                                   }
                               }
                               if(inpcount==0)
                               {
                                   
                                   /*System.out.println("Before Step 2");
                                   System.out.println(outwindx+":"+netlist(netlistinp.get(outwindx)));
                                   System.out.println(j+":"+netlist(netlistinp.get(j)));
                                   */
                                   
                                   netlistinp.get(outwindx).output.name = netlistinp.get(j).output.name;
                                   netlistinp.get(outwindx).output.wtype = netlistinp.get(j).output.wtype;
                                   
                                   /*System.out.println("After Step 2");
                                   System.out.println(outwindx+":"+netlist(netlistinp.get(outwindx)));
                                   System.out.println(j+":"+netlist(netlistinp.get(j)));
                                   */
                                   
                                   if(!removegates.contains(j))
                                   {
                                       
                                       int finalflag =0;
                                       for(int m=j+1;m<netlistinp.size();m++)
                                       {
                                           if(netlistinp.get(m).gtype.equals(DGateType.NOR))
                                           {
                                               for(DWire xinp: netlistinp.get(m).input)
                                               {
                                                   if(xinp.name.trim().equals(netlistinp.get(j).output.name.trim()))
                                                   {
                                                       finalflag =1;
                                                   }
                                               }
                                           }
                                       }
                                       if(finalflag == 0)
                                       //System.out.println("Remove Gate index:" + j);
                                       removegates.add(j);
                                   }
                                   if(!removegates.contains(i))
                                   {
                                       int finalflag =0;
                                       for(int m=i+1;m<netlistinp.size();m++)
                                       {
                                           if(netlistinp.get(m).gtype.equals(DGateType.NOR))
                                           {
                                               for(DWire xinp: netlistinp.get(m).input)
                                               {
                                                   if(xinp.name.trim().equals(netlistinp.get(i).output.name.trim()))
                                                   {
                                                       finalflag =1;
                                                   }
                                               }
                                           }
                                       }
                                       if(finalflag == 0)
                                       //System.out.println("Remove Gate index:"+i);
                                       removegates.add(i);
                                   }
                                   
                                   
                                   
                               }
                           }
                       }
                       else // if output of Gate j is a connecting wire for another gate
                       {
                           for(int k=j;k<netlistinp.size();k++)
                           {
                               if(k!=j)
                               {
                                   for(int m=0;m<netlistinp.get(k).input.size();m++)
                                   {
                                       if(netlistinp.get(k).input.get(m).name.equals(netlistinp.get(j).output.name))
                                       {
                                           /*System.out.println("Before Step 3");
                                           System.out.println(i+":"+netlist(netlistinp.get(i)));
                                           System.out.println(j+":"+netlist(netlistinp.get(j)));
                                           System.out.println(k+":"+netlist(netlistinp.get(k)));
                                           */
                                           
                                           String wireJin = netlistinp.get(j).output.name;
                                           DWireType wireJtype = netlistinp.get(j).output.wtype;
                                           DWire newWout = new DWire(wireJin,wireJtype);
                                           DWire newWin = new DWire(netlistinp.get(j).input.get(0).name,netlistinp.get(j).input.get(0).wtype);
                                           List<DWire> jinp = new ArrayList<DWire>();
                                           jinp.add(newWin);
                                           DGate jGate = new DGate(netlistinp.get(j).gtype,jinp,newWout);
                                           
                                           netlistinp.set(j, jGate);
                                           
                                           
                                           netlistinp.get(k).input.get(m).name = netlistinp.get(i).input.get(0).name;
                                           netlistinp.get(k).input.get(m).wtype = netlistinp.get(i).input.get(0).wtype;
                                           
                                           
                                           /*System.out.println("After Step 3");
                                           //System.out.println("Wire safekeep:"+wireJout);
                                           System.out.println(i+":"+netlist(netlistinp.get(i)));
                                           System.out.println(j+":"+netlist(netlistinp.get(j)));
                                           System.out.println(k+":"+netlist(netlistinp.get(k)));
                                           */
                                           
                                           if(!removegates.contains(j))
                                           {
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
                                               if (finalflag == 0)
                                                   //System.out.println("Remove Gate index:"+j);
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
        List<DGate> optnetlist = new ArrayList<DGate>();
        
        /*System.out.println("\nBefore Removing Gates:");
        printNetlist(netlistinp);
        System.out.println("-------------------------\n");
        */
        for(int i=0;i<netlistinp.size();i++)
        {
            if(!removegates.contains(i))
            {
                //System.out.println("Add:" + netlist(netlistinp.get(i)));
                optnetlist.add(netlistinp.get(i));
            }
            //else
            //{
                //System.out.println("Remove:" + netlist(netlistinp.get(i)));
            //}
        }
        
        //System.out.println("First Set of remove indices");
        //for(int i=0;i<removegates.size();i++)
        //    System.out.println("Remove Gates :"+removegates.get(i));
        
        removegates = new ArrayList<Integer>();
        
        /*System.out.println("\nAfter Removing Gates:");
        printNetlist(optnetlist);
        System.out.println("-----------------------");
        */
        
        //Remove Dangling Wires ---------------------------------------------------------------------------
        for(int i=0;i<optnetlist.size()-1;i++)
        {
            String wireout = optnetlist.get(i).output.name;
            if(optnetlist.get(i).output.wtype.equals(DWireType.connector))
            {
                int inpcount =0;
                for(int j=i;j<optnetlist.size();j++)
                {
                    if(i!=j)
                    {
                        for(int m=0;m<optnetlist.get(j).input.size();m++)
                        {
                            if(optnetlist.get(j).input.get(m).name.equals(wireout))
                                inpcount++;
                        }
                    }
                }
                if(inpcount == 0)
                    removegates.add(i);
            }
        }
        List<DGate> finalnetlist = new ArrayList<DGate>();
        for(int i=0;i<optnetlist.size();i++)
        {
            if(!removegates.contains(i))
                finalnetlist.add(optnetlist.get(i));
        }
        
        //System.out.println("Final Set of remove indices");
        //for(int i=0;i<removegates.size();i++)
        //    System.out.println(removegates.get(i));
        
        
        /*System.out.println("\nFinal optimization");
        printNetlist(finalnetlist);
        System.out.println("--------------------------------------------------");
        */
        return finalnetlist;
    }
    
    
    /**Function*************************************************************
    Synopsis    [Replace Output_OR]
    Description []
    SideEffects []
    SeeAlso     []
    ***********************************************************************/
    public static List<DGate> outputORopt(List<DGate> finalnetlist)
    {
        
        List removegates = new ArrayList<Integer>();
        
        
        //System.out.println("\nBegin Output_OR optimization");
        for(int i=0;i<finalnetlist.size()-1;i++)
        {
            if(finalnetlist.get(i).gtype.equals(DGateType.NOR))
            {
                for(int j=i+1;j<finalnetlist.size();j++)
                {
                    if(finalnetlist.get(j).gtype.equals(DGateType.NOT) && finalnetlist.get(j).output.wtype.equals(DWireType.output) && (finalnetlist.get(i).output.name.trim().equals(finalnetlist.get(j).input.get(0).name.trim())))
                    {
                        int inpcount =0;
                        for(int k=i+1;k<finalnetlist.size();k++)
                        {
                            if(k!=j)
                            {
                                for(int m=0;m<finalnetlist.get(k).input.size();m++)
                                {
                                    if(finalnetlist.get(i).output.name.equals(finalnetlist.get(k).input.get(m).name))
                                    {
                                        inpcount++;
                                    }
                                }
                            }
                        }
                        if(inpcount==0)
                        {
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
        for(int i=0;i<finalnetlist.size();i++)
        {
            if(!removegates.contains(i))
            {
                outputornetlist.add(finalnetlist.get(i));
            }
        }
        
        /*System.out.println("\nPost Output_OR optimization");
        printNetlist(outputornetlist);
        System.out.println("--------------------------");
        */
         return outputornetlist;
    }
    
    
    
    
    /**Function*************************************************************
    Synopsis    [Removes Gates where the output is not of type DWireType.Output and the inputs are not outputs of any other Gate in the circuit]
    Description []
    SideEffects []
    SeeAlso     []
    ***********************************************************************/
    public static List<DGate> removeDanglingGates(List<DGate> netlist)
    {
        List<DGate> tempnetlist = new ArrayList<DGate>();
        for(int i=0;i<netlist.size();i++)
        {
            tempnetlist.add(netlist.get(i));
        }
        List<DGate> reducednetlist = new ArrayList<DGate>();
        List<Integer> removegates = new ArrayList<Integer>();
        do{
        
        removegates = new ArrayList<Integer>();
        for(int i=0;i<tempnetlist.size()-1;i++)
        {
            if(!tempnetlist.get(i).output.wtype.equals(DWireType.output))
            {
                String outname = tempnetlist.get(i).output.name.trim();
                int inpcount =0;
                for(int j=i+1;j<tempnetlist.size();j++)
                {
                    for(int m=0;m<tempnetlist.get(j).input.size();m++)
                    {
                        String inpname = tempnetlist.get(j).input.get(m).name.trim();
                        if(inpname.equals(outname))
                            inpcount++;
                    }
                }
                if(inpcount == 0)
                {
                    removegates.add(i);
                }
            }
        }
        reducednetlist = new ArrayList<DGate>();
        for(int i=0;i<tempnetlist.size();i++)
        {
            if(!removegates.contains(i))
                reducednetlist.add(tempnetlist.get(i));
        }
        tempnetlist = new ArrayList<DGate>();
        for(int i=0;i<reducednetlist.size();i++)
        {
            tempnetlist.add(reducednetlist.get(i));
        }
        }while(!removegates.isEmpty());
        
        return reducednetlist;
    }
    
    
    
    
    
    public static List<DGate> parseStructuralVtoNORNOT(List<DGate> naivenetlist)
    {
        List<DGate> structnetlist = new ArrayList<DGate>();
        //List<DGate> naivenetlist = new ArrayList<DGate>();
        //naivenetlist = parseVerilogFile.parseStructural(filepath);
        
        naivenetlist = removeDanglingGates(naivenetlist);
        //printNetlist(naivenetlist);
        //System.out.println("------------------------------------");
        List<DGate> reducedfanin = new ArrayList<DGate>();
        for(int i=0;i<naivenetlist.size();i++)
        {
            for(DGate redgate: NetlistConversionFunctions.ConvertToFanin2(naivenetlist.get(i)))
            {
                reducedfanin.add(redgate);
            }
        }
        for(int i=0;i<reducedfanin.size();i++)
        {
            for(DGate structgate: NetlistConversionFunctions.GatetoNORNOT(reducedfanin.get(i)))
            {
                structnetlist.add(structgate);
            }
        }
        
        //System.out.println("Before Optimizing");
        //printNetlist(structnetlist);
        
        structnetlist = optimizeNetlist(structnetlist,true,true);
        //structnetlist = removeDanglingGates(structnetlist);
        //printNetlist(structnetlist);
        //System.out.println("------------------------------------");
        //structnetlist = removeDoubleInverters(structnetlist);
        //structnetlist = outputORopt(structnetlist);
        //printNetlist(structnetlist);
        //System.out.println("------------------------------------");
        //System.out.println("Optimized Netlist: (Output OR and Double NOTS");
        //printNetlist(structnetlist);
        //System.out.println("------------------------------------");
        
        //structnetlist = convert2NOTsToNOR(structnetlist);
        //System.out.println("After 2 Nots to Nor");
        //printNetlist(structnetlist);
        //System.out.println("------------------------------------");
        
        //structnetlist = rewireNetlist(structnetlist);
        //printNetlist(structnetlist);
        return structnetlist;
    }
    
    /*public static List<DGate> optimizeNetlist(List<DGate> inpNetlist)
    {
        List<DGate> outpNetlist = new ArrayList<DGate>();
        
        outpNetlist = removeDanglingGates(inpNetlist);
        outpNetlist = outputORopt(outpNetlist);
        outpNetlist = convert2NOTsToNOR(outpNetlist);
        outpNetlist = rewireNetlist(outpNetlist);
        
        return outpNetlist;
    }*/

    /**
     *
     * @param inpNetlist This is the input Netlist you want to optimize
     * @param doubleinv Remove Double Inverters
     * @param outputor OutputOR Optimization
     * @param twoNotsToNor 2NOTStoNOR Optimization
     * @return Returns an optimized Netlist 
     */
    
    public static List<DGate> optimizeNetlist(List<DGate> inpNetlist,boolean outputor,boolean twoNotsToNor)
    {
        List<DGate> outpNetlist = new ArrayList<DGate>();
        
        outpNetlist = removeDanglingGates(inpNetlist);
        outpNetlist = removeDoubleInverters(outpNetlist);
        if(outputor)
            outpNetlist = outputORopt(outpNetlist);
        if(twoNotsToNor)
            outpNetlist = convert2NOTsToNOR(outpNetlist);
        
        outpNetlist = rewireNetlist(outpNetlist);
        
        return outpNetlist;
    }
    
    public static List<DGate> parseEspressoToNORNAND(List<String> espinp)
    {
        
        one = new DWire("_one",DWireType.Source);
        zero = new DWire("_zero",DWireType.GND);
        
        for(String lists: espinp)
            System.out.println(lists);
        
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
        int numberOfMinterms=0;
        int expInd=0;        

        // <editor-fold defaultstate="collapsed" desc="Extract Input output and minterm lines from Espresso Output">
        for(int i=0;i<espinp.size();i++)
        {
            if(espinp.get(i).startsWith(".ilb"))
            {
                inpNames = (espinp.get(i).substring(5));
            }
            else if(espinp.get(i).startsWith(".ob"))
            {
                outNames = (espinp.get(i).substring(4));
            }
            else if(espinp.get(i).startsWith("#.phase"))
            {
                POSmode = true;
            }
            else if(espinp.get(i).startsWith(".p"))
            {
                numberOfMinterms = Integer.parseInt(espinp.get(i).substring(3));
                expInd = i+1;
                break;
            }
        }
        // </editor-fold>
                
        // <editor-fold defaultstate="collapsed" desc="Get Input Names">
        for(String splitInp:inpNames.split(" "))
        {
            if(splitInp.equals(one.name) || splitInp.equals(zero.name))
                splitInp += "I";
            wireInputs.add(new DWire(splitInp,DWireType.input));
            //System.out.println("INPUT : "+splitInp);
        }
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Create Not gates and NOT wires">
        inpInv = notGates(wireInputs);
        
        for(DGate gnots:inpInv)
        {
            notGateexists.add(false);
            notGateAdd.add(false);
            invWires.add(gnots.output);
        }
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Get Output Names">
        for(String splitInp:outNames.split(" "))
        {
            if(splitInp.equals(one.name) || splitInp.equals(zero.name))
                splitInp += "O";
            wireOutputs.add(new DWire(splitInp,DWireType.output));
            //System.out.println("OUTPUT : "+splitInp);
        }
        // </editor-fold>        
        
        // <editor-fold defaultstate="collapsed" desc="Minterms = 0 and Output = 1 or 0">
        if(numberOfMinterms == 0)
        {
            functionOutp = true;
            List<DWire> inp01 = new ArrayList<DWire>();
            
            if(POSmode)
            {
                inp01.add(one);
            }
            else
            {
                inp01.add(zero);
            }
            sopexp.add(new DGate(DGateType.BUF,inp01,wireOutputs.get(0)));
            return sopexp;
        }
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Minterm = 1 and Output = 1 or 0">
        else if(numberOfMinterms == 1)
        {
            String oneMinT = espinp.get(expInd).substring(0, (wireInputs.size()));
            int flag =0;
            for(int j=0;j<wireInputs.size();j++)
            {
                if(oneMinT.charAt(j) != '-')
                {
                    flag =1;
                    break;
                }
            }
            if(flag == 0)
            {
            functionOutp = true;
            List<DWire> inp01 = new ArrayList<DWire>();
            
            if(POSmode)
            {
                inp01.add(zero);
            }
            else
            {
                inp01.add(one);
            }
            sopexp.add(new DGate(DGateType.BUF,inp01,wireOutputs.get(0)));
            return sopexp;    
            }
                
        }
        // </editor-fold>
        
        
        List<DWire> minTemp = new ArrayList<DWire>();
        List<DWire> orWires = new ArrayList<DWire>();
        List<DGate> prodGates;
        for(int i=expInd;i<(expInd+numberOfMinterms);i++)
        {
            
            String minT = espinp.get(i).substring(0, (wireInputs.size()));
            prodGates = new ArrayList<DGate>();
            minTemp = new ArrayList<DWire>();
            
            // <editor-fold defaultstate="collapsed" desc="Find a Minterm/Maxterm">
            for(int j=0;j<wireInputs.size();j++)
            {
                if(minT.charAt(j)=='-')
                    continue;
                else if(minT.charAt(j) == '0')
                {
                    if(POSmode)
                    {
                        minTemp.add(wireInputs.get(j));
                    }
                    else
                    {
                        if(!notGateexists.get(j))
                        {
                            sopexp.add(inpInv.get(j));
                            notGateAdd.set(j, true);
                            notGateexists.set(j, true);
                        }
                        minTemp.add(inpInv.get(j).output);
                    }   
                }
                else if(minT.charAt(j) == '1')
                {
                    if(POSmode)
                    {
                        if(!notGateexists.get(j))
                        {
                            sopexp.add(inpInv.get(j));
                            notGateAdd.set(j, true);
                            notGateexists.set(j, true);
                        }
                        minTemp.add(inpInv.get(j).output);
                    }
                    else
                    {
                        minTemp.add(wireInputs.get(j));
                    }
                } 
            }
            // </editor-fold>

            
            if(minTemp.size() == 1)
            {
                if(POSmode)
                {
                    if(numberOfMinterms != 1)
                    {
                        if(wireInputs.contains(minTemp.get(0)))
                        {
                            int xInd = wireInputs.indexOf(minTemp.get(0));
                            if(!notGateexists.get(xInd))
                            {
                                sopexp.add(inpInv.get(xInd));
                                notGateAdd.set(xInd, true);
                                notGateexists.set(xInd, true);
                            }
                            DWire tempmin = inpInv.get(xInd).output;
                            minTemp.remove(0);
                            minTemp.add(tempmin);
                        }
                        else
                        {
                            int xInd = invWires.indexOf(minTemp.get(0));
                            if(notGateAdd.get(xInd))
                            {
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
                }
                else
                {
                    orWires.add(minTemp.get(0));
                }
                
                
            }
            else
            {
                if(POSmode)
                {
                    prodGates = NORNANDGates(minTemp,DGateType.NOR);         
                }
                else
                {
                    prodGates = NORNANDGates(minTemp,DGateType.NAND);         
                }
                orWires.add(prodGates.get(prodGates.size()-1).output);
                sopexp.addAll(prodGates);
            }
        }
        boolean no2ndstageGate = false;
        prodGates = new ArrayList<DGate>();
        if(POSmode)
        {
            prodGates = NORNANDGates(orWires,DGateType.NOR);
            
            if(prodGates.isEmpty())
            {
                no2ndstageGate = true;
            }
                
        }
        else
        {
            prodGates = NORNANDGates(orWires,DGateType.NAND);
        }
        sopexp.addAll(prodGates);
        if(sopexp.isEmpty())
        {
            DGate bufgate = new DGate(DGateType.BUF,orWires,wireOutputs.get(0));
            sopexp.add(bufgate);
        }
        else
        {
            if(no2ndstageGate)
            {
                if(!((numberOfMinterms == 1) && (minTemp.size() == 1)))
                {
                String Wirename = "0Wire" + Global.wirecount++;
                DWire aout = new DWire(Wirename);
                List<DWire> notfinalinp = new ArrayList<DWire>();
                notfinalinp.add(sopexp.get(sopexp.size()-1).output);
                DGate notfinal = new DGate(DGateType.NOT, notfinalinp ,aout);
                sopexp.add(notfinal);
                }
            }
            sopexp.get(sopexp.size()-1).output = wireOutputs.get(0);
            
        }
        return sopexp;
    }
    
    
    
    
    public static List<DGate> notGates(List<DWire> inpWires)
    {
        List<DGate> notInp = new ArrayList<DGate>();
        for(DWire xWire:inpWires)
        {
            String Wirename = "0Wire" + Global.wirecount++;
            DWire aout = new DWire(Wirename);
            List<DWire> inpNot = new ArrayList<DWire>();
            inpNot.add(xWire);
            notInp.add(new DGate(DGateType.NOT,inpNot,aout));
        }
        return notInp;
    }
    
    public static List<DGate> NORNANDGates(List<DWire> inpWires, DGateType gtype)
    {
          if (inpWires.isEmpty())
        {    return null;}
        List<DGate> minterm = new ArrayList<DGate>();
        
       
        List<DWire> nextLevelWires = new ArrayList<DWire>();
        List<DWire> temp = new ArrayList<DWire>();
        
        int wireCount,indx;
        nextLevelWires.addAll(inpWires);
        wireCount = inpWires.size();
        
        while(wireCount > 1)
        {
            temp = new ArrayList<DWire>();
            temp.addAll(nextLevelWires);
            nextLevelWires = new ArrayList<DWire>();
            indx = 0;
            while((indx+2)<= (wireCount))
            {
                
                List<DWire> ainp = new ArrayList<DWire>();
                ainp.add(temp.get(indx));
                ainp.add(temp.get(indx+1));
                String Wirename = "0Wire" + Global.wirecount++;
                
                DWire aout = new DWire(Wirename);
                DGate andG = new DGate(gtype,ainp,aout);
                minterm.add(andG);
                if(wireCount>2)
                {
                    Wirename = "0Wire" + Global.wirecount++;
                    List<DWire> ninp = new ArrayList<DWire>();
                    ninp.add(aout);
                    DWire anout = new DWire(Wirename);
                    DGate notG = new DGate(DGateType.NOT, ninp ,anout);
                    minterm.add(notG);
                    nextLevelWires.add(anout);
                }
                else
                {
                    nextLevelWires.add(aout);
                }
                indx+=2;
            
            }
            if(temp.size() %2 != 0)
            {
                nextLevelWires.add(temp.get(wireCount-1));
            }
            wireCount = nextLevelWires.size();
            if(wireCount == 2)
            {
                List<DWire> ainp = new ArrayList<DWire>();
                ainp.add(nextLevelWires.get(0));
                ainp.add(nextLevelWires.get(1));
                String Wirename = "0Wire" + Global.wirecount++;
                DWire aout = new DWire(Wirename);
                DGate andG = new DGate(gtype,ainp,aout);
                minterm.add(andG);
                break;
            }
        }
        return minterm;
    }
    
    public static List<DGate> AndORGates(List<DWire> inpWires,DGateType gOrAnd)
    {
        if (inpWires.isEmpty())
        {    return null;}
        List<DGate> minterm = new ArrayList<DGate>();
        
       
        List<DWire> nextLevelWires = new ArrayList<DWire>();
        List<DWire> temp = new ArrayList<DWire>();
        
        int wireCount,indx;
        nextLevelWires.addAll(inpWires);
        wireCount = inpWires.size();
        
        while(wireCount > 1)
        {
            temp = new ArrayList<DWire>();
            temp.addAll(nextLevelWires);
            nextLevelWires = new ArrayList<DWire>();
            indx = 0;
            while((indx+2)<= (wireCount))
            {
                
                List<DWire> ainp = new ArrayList<DWire>();
                ainp.add(temp.get(indx));
                ainp.add(temp.get(indx+1));
                String Wirename = "0Wire" + Global.wirecount++;
                
                DWire aout = new DWire(Wirename);
                nextLevelWires.add(aout);
                DGate andG = new DGate(gOrAnd,ainp,aout);
                minterm.add(andG);
                indx+=2;
            
            }
            if(temp.size() %2 != 0)
            {
                nextLevelWires.add(temp.get(wireCount-1));
            }
            wireCount = nextLevelWires.size();
            if(wireCount == 2)
            {
                List<DWire> ainp = new ArrayList<DWire>();
                ainp.add(nextLevelWires.get(0));
                ainp.add(nextLevelWires.get(1));
                String Wirename = "0Wire" + Global.wirecount++;
                DWire aout = new DWire(Wirename);
                DGate andG = new DGate(gOrAnd,ainp,aout);
                minterm.add(andG);
                break;
            }
        }
        return minterm;
    }
    //<editor-fold desc="CreateDAGraph Function">
    
    /*
    public static DAGraph CreateDAGraph(List<DGate> netlist)
    {
        DAGraph outDAG = new DAGraph();
        List<DWire> inplist = new ArrayList<DWire>(); 
        List<DAGVertex> Vertices = new ArrayList<DAGVertex>();
        List<DAGEdge> Edges = new ArrayList<DAGEdge>();
        HashMap<DGate,DAGVertex> vertexhash = new HashMap<DGate,DAGVertex>();
        int IndX =0; 
        int outpIndx=0;
        
        
        for(int i=netlist.size()-1;i>=0;i--)
        {
            DGate netg = netlist.get(i);
             if(netg.input.contains(zero))
             {
                DGate tempNot = new DGate();
                for(DWire xi:netg.input)
                {
                    if(!(xi.equals(zero)))
                        tempNot.input.add(xi);
                    tempNot.output = netg.output;
                    tempNot.gtype = DGateType.NOT;
                }
                netlist.get(i).gtype = tempNot.gtype;
                netlist.get(i).input = tempNot.input;
                netlist.get(i).output = tempNot.output;
                
               
            }
        }
        
        
        for(int i=netlist.size()-1;i>=0;i--)
        {
            DGate netg = netlist.get(i);
           
           
            for(DWire xi:netg.input)
            {
                if((xi.wtype == DWireType.input) && (!(inplist.contains(xi))))
                {
                    inplist.add(xi);
                }
            }
            
            if(netg.output.wtype == DWireType.output)
            {
                //System.out.println(IndX);
                DAGVertex out =null;
                DAGVertex lvert = null;
                if(netg.gtype == DGateType.NOT)
                {
                    outpIndx = IndX;
                    out = new DAGVertex(IndX++, VertexType.OUTPUT_OR.toString());                 
                    lvert = new DAGVertex(IndX++,VertexType.NOT.toString());
                    vertexhash.put(netg, lvert);
                }
                else if(netg.gtype == DGateType.NOR2)
                {
                    outpIndx = IndX;
                    out = new DAGVertex(IndX++, VertexType.OUTPUT.toString()); 
                    lvert = new DAGVertex(IndX++,VertexType.NOR.toString());
                    vertexhash.put(netg, lvert);
                }
                out.Name = netg.output.name;
                lvert.outW = netg.output;
                
                //System.out.println(netg.output.wtype.toString());
                //System.out.println(netg.gtype.toString());                
                
                Vertices.add(out);
                Vertices.add(lvert);
            }
            else
            {
                DAGVertex vert=null;
                if(netg.gtype == DGateType.NOT)
                {
                    vert = new DAGVertex(IndX++,VertexType.NOT.toString());
                }
                else if(netg.gtype == DGateType.NOR2)
                {
                    vert = new DAGVertex(IndX++,VertexType.NOR.toString());
                }
                vert.outW = netg.output;
                
                
                
                //System.out.println(netg.output.wtype.toString());
                //System.out.println(netg.gtype.toString());
                vertexhash.put(netg, vert);
                Vertices.add(vert);
            }
        }
        for(DWire inpx:inplist)
        {
            DAGVertex vert = new DAGVertex(IndX++,VertexType.INPUT.toString());
            vert.outW = inpx;
            vert.Name = inpx.name;
            vert.Outgoing = null;
            
            Vertices.add(vert);
        }
        int eind=0;
        
        for(int i=netlist.size()-1;i>=0;i--)
        {
            DGate netg = netlist.get(i);
           
            if(netg.output.wtype == DWireType.output)
            {
                DAGEdge out = new DAGEdge();
                out.From = Vertices.get(outpIndx);
                out.To = vertexhash.get(netg);
                out.Index = eind++;
                out.Next = null;
                Edges.add(out);
            }
            DAGEdge temp = null;
            
            
            
            for(DWire inps:netg.input)
            {
                
               DAGVertex gTo=null;
               //System.out.println(inps.name);
               for(DAGVertex xvert:Vertices)
               {
                  
                   if(xvert.outW.name.trim().equals(inps.name.trim()))
                   {
                       gTo = xvert;
                       break;
                   }
               }
               DAGVertex gFrom = null;
               gFrom = vertexhash.get(netg);
               DAGEdge newEdge = new DAGEdge(eind++,gFrom,gTo,temp);
               
               temp = newEdge;
               Edges.add(newEdge);
               
            }
        }
        
        for(DAGEdge edg:Edges)
        {
            if(edg.From.Type == "NOR")
            {
                if(!(edg.Next == null))
                {
                int count=0;
                
                for(DAGVertex dvert:Vertices)
                {
                    if(edg.From.equals(dvert))
                    {
                        break;
                    }
                    count++;
                }
                
                Vertices.get(count).Outgoing = edg;
                
                }
            }
            else
            {
                if(edg.Next == null)
                {
                int count=0;
                
                for(DAGVertex dvert:Vertices)
                {
                    if(edg.From.equals(dvert))
                    {
                        break;
                    }
                    count++;
                }
                Vertices.get(count).Outgoing = edg;
                
                }
            }
        }
        
        for(DAGEdge xdedge:Edges)
        {
            outDAG.Edges.add(xdedge);
        }
        for(DAGVertex xdvert:Vertices)
        {
            outDAG.Vertices.add(xdvert);
        }
        
        return outDAG;
    }
    
    */
    //</editor-fold>
   
    
     public static DAGW CreateMultDAGW(List<DGate> netlist)
    {
         DAGW outDAG = new DAGW();
         List<DWire> inplist = new ArrayList<DWire>(); 
         List<Gate> Gates = new ArrayList<Gate>();
         List<Wire> Wires = new ArrayList<Wire>();
         HashMap<DGate,Gate> vertexhash = new HashMap<DGate,Gate>();
         int IndX =0; 
         int outpIndx=0;
        /*for(DGate xgate:netlist)
        {
            System.out.println(netlist(xgate));
        }*/
         
        
        //<editor-fold desc="Single Gate in netlist of type Buffer" > 
        if(netlist.size() == 1 && netlist.get(0).gtype == DGateType.BUF) // This step is incase there is a single gate in the netlist and it is of type Buffer
        {
            Gate outb = new Gate();
            Gate inpb = new Gate();
            
            outb.Type = "OUTPUT";
            outb.Name = netlist.get(0).output.name;
            outb.Index =0;
            
            inpb.Index = 1;
            inpb.Name = netlist.get(0).input.get(0).name;
            inpb.Type = "INPUT";
            
            
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
        for(int i=netlist.size()-1;i>=0;i--) // This step is for a NOR Gate that has a Zero as an input. Replace it with a simple NOT gate. 
        {
            DGate netg = netlist.get(i);
             if(netg.input.contains(zero))
             {
                DGate tempNot = new DGate();
                for(DWire xi:netg.input)
                {
                    if(!(xi.equals(zero)))
                        tempNot.input.add(xi);
                    tempNot.output = netg.output;
                    tempNot.gtype = DGateType.NOT;
                }
                netlist.get(i).gtype = tempNot.gtype;
                netlist.get(i).input = tempNot.input;
                netlist.get(i).output = tempNot.output;
            }
        }
        //</editor-fold>
        
        //<editor-fold desc="Replace NOR followed by NOT followed by an output with OUTPUT_OR - Commented out">
        //This section replaces a NOR followed by a NOT followed by an output with an Output_OR
        
        /*if(netlist.size()>1)
        {
            int lastnet = netlist.size()-1;
            if((netlist.get(lastnet).gtype == DGateType.NOT) && (netlist.get(lastnet-1).gtype == DGateType.NOR2))
            {
                netlist.get(lastnet).gtype = DGateType.OR2;
                netlist.get(lastnet).input = netlist.get(lastnet-1).input;
                netlist.remove(lastnet-1);
            }
        }*/
        //</editor-fold>
        
        
        List<String> inputwires = new ArrayList<String>();
        List<String> outputwires = new ArrayList<String>();
        List<String> outputORwires = new ArrayList<String>();
        
        for(DGate xgate:netlist)
        {
            if(xgate.output.wtype.equals(DWireType.output))
            {
                outputwires.add(xgate.output.name.trim());
                if(xgate.gtype.equals(DGateType.OR))
                    outputORwires.add(xgate.output.name.trim());
            }
            for(DWire xinp:xgate.input)
            {
                if(xinp.wtype.equals(DWireType.input))
                {
                    if(!inputwires.contains(xinp.name.trim()))
                        inputwires.add(xinp.name.trim());
                }
                
            }
        }
        //int indx = (netlist.size() + inputwires.size() + outputwires.size()) -1;
        int indx =0;
        for(int i=0;i<outputwires.size();i++)
        {
            Gate outg = new Gate();
            outg.Name = outputwires.get(i);
            if(outputORwires.contains(outputwires.get(i)))
            {
                outg.Type = GateType.OUTPUT_OR.toString();
                outg.outW = new DWire(outputwires.get(i),DWireType.output);
            }
            else
                outg.Type = GateType.OUTPUT.toString();
                
            outg.Index = indx;
            indx++;
            Gates.add(outg);
        }
        for(int i=netlist.size()-1;i>=0;i--)
        {
            DGate netg = netlist.get(i);
            if(netg.gtype.equals(DGateType.NOR))
            {
                Gate norg = new Gate(indx,GateType.NOR.toString());
                norg.outW = netg.output;
                
                indx++;
                Gates.add(norg);
            }
            else if(netg.gtype.equals(DGateType.NOT))
            {
                Gate notg = new Gate(indx,GateType.NOT.toString());
                notg.outW = netg.output;
                indx++;
                Gates.add(notg);
            }
        }
        for(int i=0;i<inputwires.size();i++)
        {
            Gate ing = new Gate();
            ing.Name = inputwires.get(i);
            ing.Type = GateType.INPUT.toString();
            ing.Index = indx;
            indx++;
            Gates.add(ing);
        }
        int windx =0;
        for(int i=netlist.size()-1;i>=0;i--)
        {
            DGate netg = netlist.get(i);
            if(netg.gtype.equals(DGateType.BUF))
            {
                Wire bufwire = new Wire();
                bufwire.Index = windx;
                windx++;
                bufwire.Next = null;
                int fromindx =0;
                for(Gate gout:Gates)
                {
                    if(gout.Name.trim().equals(netg.output.name.trim()))
                    {
                        bufwire.From = gout;
                        fromindx = gout.Index;
                    }
                    if(gout.Name.trim().equals(netg.input.get(0).name.trim()))
                    {
                        bufwire.To = gout;
                    }
                }
                for(Gate gout:Gates)
                {
                    if(gout.Index == fromindx)
                    {
                        gout.Outgoing = bufwire;
                    }
                }
                Wires.add(bufwire);
            }
            else
            {
                Wire temp = null;
                if(netg.output.wtype.equals(DWireType.output) && !netg.gtype.equals(DGateType.OR))
                {
                    Wire outpwire = new Wire();
                    int fromindx =0;
                    outpwire.Index = windx;
                    windx++;
                    outpwire.Next = null;
                    for(Gate gout:Gates)
                    {
                        if(gout.Name.trim().equals(netg.output.name.trim()))
                        {
                            outpwire.From = gout;
                            fromindx = gout.Index;
                            
                        }
                        if(gout.outW.name.trim().equals(netg.output.name.trim()))
                        {
                            outpwire.To = gout;
                        }
                    }
                    for(Gate gout:Gates)
                    {
                        if(gout.Index == fromindx)
                        {
                            gout.Outgoing = outpwire;
                            
                        }
                    }
                    Wires.add(outpwire);
                    temp = null;
                    //for(DWire gwireinp:netg.input)
                    for(int j=netg.input.size()-1;j>=0;j--)
                    {
                        DWire gwireinp = netg.input.get(j); 
                        Wire connwire = new Wire();
                        connwire.Index = windx;
                        windx++;
                        int gfromindx =0;
                        if(gwireinp.wtype.equals(DWireType.input))
                        {
                            for(Gate xgate:Gates)
                            {
                                if(xgate.Name.trim().equals(gwireinp.name.trim()))
                                {
                                    connwire.To = xgate;
                                }
                                if(xgate.outW.name.trim().equals(netg.output.name.trim()))
                                {
                                    connwire.From = xgate;
                                    gfromindx = xgate.Index;
                                }
                            }
                            connwire.Next = temp;
                            
                            if(j==0)
                            {
                                for(Gate xgate:Gates)
                                {
                                    if(xgate.Index == gfromindx)
                                    {
                                        xgate.Outgoing = connwire;
                                    }
                                }
                            }
                            Wires.add(connwire);
                            temp = connwire;
                        }
                        else
                        {
                            for(Gate xgate:Gates)
                            {
                                if(gwireinp.name.trim().equals(xgate.outW.name.trim()))
                                {
                                    connwire.To = xgate;
                                }
                                if(netg.output.name.trim().equals(xgate.outW.name.trim()))
                                {
                                    connwire.From = xgate;
                                    gfromindx = xgate.Index;
                                }
                            }
                            connwire.Next = temp;
                            if(j==0)
                            {
                                for(Gate xgate:Gates)
                                {
                                    if(xgate.Index == gfromindx)
                                    {
                                        xgate.Outgoing = connwire;
                                    }
                                }
                            }
                            Wires.add(connwire);
                            temp = connwire;
                        }
                    }
                }
                else
                {
                    temp = null;
                    //for(DWire gwireinp:netg.input)
                    for(int j=netg.input.size()-1;j>=0;j--)
                    {
                        DWire gwireinp = netg.input.get(j); 
                        Wire connwire = new Wire();
                        connwire.Index = windx;
                        windx++;
                        int gfromindx =0;
                        if(gwireinp.wtype.equals(DWireType.input))
                        {
                            for(Gate xgate:Gates)
                            {
                                if(xgate.Name.trim().equals(gwireinp.name.trim()))
                                {
                                    connwire.To = xgate;
                                }
                                if(xgate.outW.name.trim().equals(netg.output.name.trim()))
                                {
                                    connwire.From = xgate;
                                    gfromindx = xgate.Index;
                                }
                            }
                            connwire.Next = temp;
                            
                            if(j==0)
                            {
                                for(Gate xgate:Gates)
                                {
                                    if(xgate.Index == gfromindx)
                                    {
                                        xgate.Outgoing = connwire;
                                    }
                                }
                            }
                            Wires.add(connwire);
                            temp = connwire;
                        }
                        else
                        {
                            for(Gate xgate:Gates)
                            {
                                if(gwireinp.name.trim().equals(xgate.outW.name.trim()))
                                {
                                    connwire.To = xgate;
                                }
                                if(netg.output.name.trim().equals(xgate.outW.name.trim()))
                                {
                                    connwire.From = xgate;
                                    gfromindx = xgate.Index;
                                }
                            }
                            connwire.Next = temp;
                            
                            if(j==0)
                            {
                                for(Gate xgate:Gates)
                                {
                                    if(xgate.Index == gfromindx)
                                    {
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
        
        
        for(Wire xdedge:Wires)
        {
            outDAG.Wires.add(new Wire(xdedge));
        }
        for(Gate xdvert:Gates)
        {
            outDAG.Gates.add(new Gate(xdvert));
        }
        //DAGW outputDAG = new DAGW(outDAG.Gates,outDAG.Wires);
        return outDAG;
    }
   
   
    
    
    
    
     public static DAGW CreateDAGW(List<DGate> netlist)
    {
         DAGW outDAG = new DAGW();
         List<DWire> inplist = new ArrayList<DWire>(); 
         List<Gate> Gates = new ArrayList<Gate>();
         List<Wire> Wires = new ArrayList<Wire>();
         HashMap<DGate,Gate> vertexhash = new HashMap<DGate,Gate>();
         int IndX =0; 
         int outpIndx=0;
        /*for(DGate xgate:netlist)
        {
            System.out.println(netlist(xgate));
        }*/
         
        
        //<editor-fold desc="Single Gate in netlist of type Buffer" > 
        if(netlist.size() == 1 && netlist.get(0).gtype == DGateType.BUF) // This step is incase there is a single gate in the netlist and it is of type Buffer
        {
            Gate outb = new Gate();
            Gate inpb = new Gate();
            
            outb.Type = "OUTPUT";
            outb.Name = netlist.get(0).output.name;
            outb.Index =0;
            
            inpb.Index = 1;
            inpb.Name = netlist.get(0).input.get(0).name;
            inpb.Type = "INPUT";
            
            
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
        for(int i=netlist.size()-1;i>=0;i--) // This step is for a NOR Gate that has a Zero as an input. Replace it with a simple NOT gate. 
        {
            DGate netg = netlist.get(i);
             if(netg.input.contains(zero))
             {
                DGate tempNot = new DGate();
                for(DWire xi:netg.input)
                {
                    if(!(xi.equals(zero)))
                        tempNot.input.add(xi);
                    tempNot.output = netg.output;
                    tempNot.gtype = DGateType.NOT;
                }
                netlist.get(i).gtype = tempNot.gtype;
                netlist.get(i).input = tempNot.input;
                netlist.get(i).output = tempNot.output;
            }
        }
        //</editor-fold>
        
        //<editor-fold desc="Replace NOR followed by NOT followed by an output with OUTPUT_OR - Commented out">
        //This section replaces a NOR followed by a NOT followed by an output with an Output_OR
        
        /*if(netlist.size()>1)
        {
            int lastnet = netlist.size()-1;
            if((netlist.get(lastnet).gtype == DGateType.NOT) && (netlist.get(lastnet-1).gtype == DGateType.NOR2))
            {
                netlist.get(lastnet).gtype = DGateType.OR2;
                netlist.get(lastnet).input = netlist.get(lastnet-1).input;
                netlist.remove(lastnet-1);
            }
        }*/
        //</editor-fold>
        
        
        
        
        
        for(int i=netlist.size()-1;i>=0;i--)
        {
            DGate netg = netlist.get(i);
           
           
            for(DWire xi:netg.input)
            {
                if((xi.wtype == DWireType.input) && (!(inplist.contains(xi))))
                {
                    int flag =0;
                    for(DWire di:inplist)
                    {
                        if(di.name.trim().equals(xi.name.trim()))
                        {
                            flag =1;
                            break;
                        }
                    }
                    if(flag ==0)
                    {
                        //System.out.println("Added : " + xi.name);
                        inplist.add(xi);
                    }
                    
                }
            }
            
            if(netg.output.wtype == DWireType.output)
            {
                if(netg.gtype == DGateType.OR)
                {
                    Gate outor = null;
                    outpIndx = IndX;
                    outor = new Gate(IndX++, VertexType.OUTPUT_OR.toString()); 
                    outor.Name = netg.output.name;
                    vertexhash.put(netg, outor);
                    Gates.add(outor);
                }
                else
                {
                    Gate out =null;
                    Gate lvert = null;
                    if(netg.gtype == DGateType.NOT)
                    {
                        outpIndx = IndX;
                        out = new Gate(IndX++, VertexType.OUTPUT.toString());                 
                        lvert = new Gate(IndX++,VertexType.NOT.toString());
                        vertexhash.put(netg, lvert);
                    }
                    else if(netg.gtype == DGateType.NOR)
                    {
                        outpIndx = IndX;
                        out = new Gate(IndX++, VertexType.OUTPUT.toString()); 
                        lvert = new Gate(IndX++,VertexType.NOR.toString());
                        vertexhash.put(netg, lvert);
                    }
                    out.Name = netg.output.name;
                    lvert.outW = netg.output;
                
                    Gates.add(out);
                    Gates.add(lvert);
                }
                //System.out.println(IndX);
                
            }
            else
            {
                Gate vert=null;
                if(netg.gtype == DGateType.NOT)
                {
                    vert = new Gate(IndX++,VertexType.NOT.toString());
                }
                else if(netg.gtype == DGateType.NOR)
                {
                    vert = new Gate(IndX++,VertexType.NOR.toString());
                }
                vert.outW = netg.output;
                
                //System.out.println(netg.output.wtype.toString());
                //System.out.println(netg.gtype.toString());
                vertexhash.put(netg, vert);
                Gates.add(vert);
            }
        }
        for(DWire inpx:inplist)
        {
            Gate vert = new Gate(IndX++,VertexType.INPUT.toString());
            vert.outW = inpx;
            vert.Name = inpx.name;
            vert.Outgoing = null;
            
            Gates.add(vert);
        }
        int eind=0;
        
        for(int i=netlist.size()-1;i>=0;i--)
        {
            DGate netg = netlist.get(i);
           
            if((netg.output.wtype == DWireType.output) && (netg.gtype != DGateType.OR))
            {
                Wire out = new Wire();
                out.From = Gates.get(outpIndx);
                out.To = vertexhash.get(netg);
                out.Index = eind++; 
                out.Next = null;
                Wires.add(out);
            }
            Wire temp = null;
            
            
            
            for(DWire inps:netg.input)
            {
                
               Gate gTo= null;
               //System.out.println(inps.name);
               for(Gate xvert:Gates)
               {
                  
                   if(xvert.outW.name.trim().equals(inps.name.trim()))
                   {
                       gTo = xvert;
                       break;
                   }
               }
               Gate gFrom = null;
               gFrom = vertexhash.get(netg);
               Wire newEdge = new Wire(eind++,gFrom,gTo,temp);
               
               temp = newEdge;
               Wires.add(newEdge);
               
            }
        }
        
        for(Wire edg:Wires)
        {
            if(edg.From.Type == "NOR" || edg.From.Type == "OUTPUT_OR")
            {
                if(!(edg.Next == null))
                {
                int count=0;
                
                for(Gate dvert:Gates)
                {
                    if(edg.From.equals(dvert))
                    {
                        break;
                    }
                    count++;
                }
                
                Gates.get(count).Outgoing = edg;
                
                }
            }
            else
            {
                if(edg.Next == null)
                {
                int count=0;
                
                for(Gate dvert:Gates)
                {
                    if(edg.From.equals(dvert))
                    {
                        break;
                    }
                    count++;
                }
                Gates.get(count).Outgoing = edg;
                
                }
            }
        }
        
        for(Wire xdedge:Wires)
        {
            outDAG.Wires.add(new Wire(xdedge));
        }
        for(Gate xdvert:Gates)
        {
            outDAG.Gates.add(new Gate(xdvert));
        }
        //DAGW outputDAG = new DAGW(outDAG.Gates,outDAG.Wires);
        return outDAG;
    }
   
   
    public static void printNetlist(List<DGate> netlist)
    {
        for(int i=0;i<netlist.size();i++)
            System.out.println(printGate(netlist.get(i)));
    }
    
    
    public static String printGate(DGate g)
    {
        String netbuilder="";
        netbuilder += g.gtype;
            netbuilder += "(";
            netbuilder += g.output.name;
            
            for(DWire x:g.input)
            {
                netbuilder += ",";
                netbuilder += x.name;
            }
            netbuilder += ")";
            //netbuilder += "  Stage:";
            //netbuilder += g.gatestage;
        return netbuilder;
    }
}