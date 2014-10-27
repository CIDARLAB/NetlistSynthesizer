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
import BU.ParseVerilog.parseVerilogFile;
import BU.booleanLogic.BooleanSimulator;
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
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


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
    
    public static CircuitDetails caseCirc;
    
    /**
     * @param args the command line arguments
     */
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

    
    
    
    
    /**Function*************************************************************
    <br>
    Synopsis    []
    <br>
    Description []
    <br>
    SideEffects []
    <br>
    SeeAlso     []
    ***********************************************************************/
    public static void initializeFilepath() {
        Filepath = NetSynth.class.getClassLoader().getResource(".").getPath();
        if(Filepath.contains("build/classes/"))
            Filepath = Filepath.substring(0,Filepath.lastIndexOf("build/classes/"));
        else if(Filepath.contains("src"))
            Filepath = Filepath.substring(0,Filepath.lastIndexOf("src/"));
    }
    
    
    
    
    /**Function*************************************************************
    <br>
    Synopsis    [Controller function in NetSynth. Parses Verilog to a Directed Acyclic Graph]
    <br>
    Description [This is the default function. Takes the verilog file's filepath as the input parameter and gives an optimal result]
    <br>
    SideEffects []
    <br>
    SeeAlso     []
     * @param vfilepath
     * @return 
    ***********************************************************************/
    public static DAGW runNetSynth(String vfilepath)
    {
        return runNetSynth(vfilepath, NetSynthSwitches.defaultmode, NetSynthSwitches.defaultmode, NetSynthSwitches.defaultmode,NetSynthSwitches.defaultmode, NetSynthSwitches.defaultmode ,  NetSynthSwitches.defaultmode, NetSynthSwitches.defaultmode );
    }
    
    /**Function*************************************************************
    <br>
    Synopsis    []
    <br>
    Description []
    <br>
    SideEffects []
    <br>
    SeeAlso     []
     * @param vfilepath
     * @param synthesis
     * @param invcheck
     * @param outputor
     * @param outputOR3
     * @param twonotstonor
     * @param nor3
     * @param and2
     * @return 
    ***********************************************************************/
    public static DAGW runNetSynth(String vfilepath, String synthesis, String invcheck, String outputor,String outputOR3, String twonotstonor, String nor3, String and2)
    {
        NetSynthSwitches synth = null;
        NetSynthSwitches inv = null;
        NetSynthSwitches outpor = null;
        NetSynthSwitches twonots2nor = null;
        NetSynthSwitches nor3inp = null;
        NetSynthSwitches outor3 = null;
        NetSynthSwitches findAnd2 = null;
        
        try
        {
            synth = NetSynthSwitches.valueOf(synthesis);
            inv = NetSynthSwitches.valueOf(invcheck);
            outpor = NetSynthSwitches.valueOf(outputor);
            twonots2nor = NetSynthSwitches.valueOf(twonotstonor);
            nor3inp = NetSynthSwitches.valueOf(nor3);
            outor3 = NetSynthSwitches.valueOf(outputOR3);
            findAnd2 = NetSynthSwitches.valueOf(and2);
        }
        catch(Exception e)
        {
            System.out.println("Error : "+ e.toString());
        }
        
        return runNetSynth(vfilepath,synth, inv,outpor,outor3,twonots2nor, nor3inp,findAnd2);
    }
    
    
    
    /**Function*************************************************************
    <br>
    Synopsis    [Controller function in NetSynth. Parses Verilog to a Directed Acyclic Graph]
    <br>
    Description [This is the default function. Takes the verilog file's filepath as the input parameter and gives an optimal result]
    <br>
    SideEffects []
    <br>
    SeeAlso     []
     * @param vfilepath    
     * @param synthesis
     * @param invcheck
     * @param outputor
     * @param outputOR3
     * @param twonotstonor
     * @param nor3
     * @return 
    ***********************************************************************/
    public static DAGW runNetSynth(String vfilepath,NetSynthSwitches synthesis, NetSynthSwitches invcheck, NetSynthSwitches outputor, NetSynthSwitches outputOR3, NetSynthSwitches twonotstonor ,NetSynthSwitches nor3,NetSynthSwitches and2)
    {
        
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
        
        
        netlist = getNetlist(vfilepath, synthesis,  invcheck,  outputor,outputOR3,  twonotstonor, nor3,and2 );
        
        netlist = rewireNetlist(netlist);
        //System.out.println("\nFinal Netlist");
        //printNetlist(netlist);
        
        //BooleanSimulator.printTruthTable(netlist, inputnames);
        finaldag = CreateMultDAGW(netlist);
        
        if(hasCaseStatements)
        {
            finaldag = DAGW.addDanglingInputs(finaldag,inputnames);
        }
        finaldag = DAGW.reorderinputs(finaldag,inputnames);
        
        //List<String> netlistTT = new ArrayList<String>();
        //System.out.println("Final Netlist");
        //printNetlist(netlist);
       
        //BooleanSimulator.printTruthTable(netlist, inputnames);
        //netlistTT = BooleanSimulator.getTruthTable(netlist, inputnames);
        
        //for(Gate xgate:finaldag.Gates)
        //{
        //    System.out.println(xgate.Name +" : " + xgate.Type);
        //}
        
        return finaldag;
    }
    
    
    /**Function*************************************************************
    <br>
    Synopsis    []
    <br>
    Description []
    <br>
    SideEffects []
    <br>
    SeeAlso     []
     * @param inpnetlist
     * @return 
    ***********************************************************************/
    public static int getRepressorsCount(List<DGate> inpnetlist)
    {
        int count = 0;
        for(DGate xgate:inpnetlist)
        {
            if(!xgate.gtype.equals(DGateType.OR))
                count++;
        }
        
        return count;
    }
    
    /**Function*************************************************************
    <br>
    Synopsis    [Controller function in NetSynth. Parses Verilog to a Directed Acyclic Graph]
    <br>
    Description [This is the default function. Takes the verilog file's filepath as the input parameter and gives an optimal result]
    <br>
    SideEffects []
    <br>
    SeeAlso     []
     * @param vfilepath
     * @return 
    ***********************************************************************/
    public static List<DGate> getNetlist(String vfilepath)
    {
        return getNetlist(vfilepath, NetSynthSwitches.defaultmode, NetSynthSwitches.defaultmode, NetSynthSwitches.defaultmode, NetSynthSwitches.defaultmode,NetSynthSwitches.defaultmode , NetSynthSwitches.defaultmode, NetSynthSwitches.defaultmode );
    }
    
    /**Function*************************************************************
    <br>
    Synopsis    [Controller function in NetSynth. Parses Verilog to a Directed Acyclic Graph]
    <br>
    Description [This is the default function. Takes the verilog file's filepath as the input parameter and gives an optimal result]
    <br>
    SideEffects []
    <br>
    SeeAlso     []
     * @param vfilepath
     * @param synthesis
     * @param invcheck
     * @param outputor
     * @param outputOR3
     * @param twonotstonor
     * @param nor3
     * @return
    ***********************************************************************/
    public static List<DGate> getNetlist(String vfilepath, String synthesis, String invcheck, String outputor,String outputOR3, String twonotstonor, String nor3, String and2)
    {
        NetSynthSwitches synth = null;
        NetSynthSwitches inv = null;
        NetSynthSwitches outpor = null;
        NetSynthSwitches twonots2nor = null;
        NetSynthSwitches nor3inp = null;
        NetSynthSwitches outor3 = null;
        NetSynthSwitches findAnd2 = null;
        
        try
        {
            synth = NetSynthSwitches.valueOf(synthesis);
            inv = NetSynthSwitches.valueOf(invcheck);
            outpor = NetSynthSwitches.valueOf(outputor);
            twonots2nor = NetSynthSwitches.valueOf(twonotstonor);
            nor3inp = NetSynthSwitches.valueOf(nor3);
            outor3 = NetSynthSwitches.valueOf(outputOR3);
            findAnd2 = NetSynthSwitches.valueOf(and2);
            
        }
        catch(Exception e)
        {
            System.out.println("Error : "+ e.toString());
        }
        
        return getNetlist(vfilepath,synth, inv,outpor,outor3,twonots2nor, nor3inp,findAnd2);
    }
    
    /**Function*************************************************************
    <br>
    Synopsis    [Controller function in NetSynth. Parses Verilog to a Directed Acyclic Graph]
    <br>
    Description [This is the default function. Takes the verilog file's filepath as the input parameter and gives an optimal result]
    <br>
    SideEffects []
    <br>
    SeeAlso
     * @param vfilepath   
     * @param synthesis
     * @param invcheck
     * @param outputor
     * @param outputOR3
     * @param twonotstonor
     * @param nor3
     * @param and2
     * @return 
    ***********************************************************************/
    public static List<DGate> getNetlist(String vfilepath,NetSynthSwitches synthesis, NetSynthSwitches invcheck, NetSynthSwitches outputor, NetSynthSwitches outputOR3, NetSynthSwitches twonotstonor, NetSynthSwitches nor3, NetSynthSwitches and2 )
    {
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
        int dirsize = 0;
        int invsize = 0;
        int structsize =0;
        int precompsize =0;
        int invprecompsize =0;
        int netsize =0;
        //<editor-fold desc="Structural Verilog">
        if(isStructural)
        {
            
            naivenetlist = parseVerilogFile.parseStructural(alllines); //Convert Verilog File to List of DGates 
            if(synthesis.equals(NetSynthSwitches.originalstructural))
            {
                for(DGate xgate:naivenetlist)
                {
                    structnetlist.add(xgate);
                }
            }
            else
            {
                structnetlist = parseStructuralVtoNORNOT(naivenetlist,nor3,synthesis); // Convert Naive Netlist to List of DGates containing only NOR and NOTs
            }
            
            if(synthesis.equals(NetSynthSwitches.originalstructuralANDOR))
            {
                structnetlist = convertORbeforeAND(structnetlist);
            }
            
            
            List<String> ttValues = new ArrayList<String>(); 
            List<String> invttValues = new ArrayList<String>();
            
            ttValues = BooleanSimulator.getTruthTable(structnetlist, inputnames);  // Compute Truth Table of Each Output
            invttValues = BooleanSimulator.invertTruthTable(ttValues); // Compute Inverse Truth Table of Each Output
            
            CircuitDetails direct = new CircuitDetails(inputnames,outputnames,ttValues); 
            CircuitDetails inverted = new CircuitDetails(inputnames, outputnames,invttValues);
            
            dirnetlist = runEspressoAndABC(direct,synthesis,outputor,twonotstonor,nor3);
            invnetlist = runInvertedEspressoAndABC(inverted,synthesis,outputor,twonotstonor,nor3);
            
            dirsize = getRepressorsCount(dirnetlist);
            invsize = getRepressorsCount(invnetlist);
            structsize = getRepressorsCount(structnetlist);
            
            if(synthesis.equals(NetSynthSwitches.originalstructural) || synthesis.equals(NetSynthSwitches.originalstructuralAND) || synthesis.equals(NetSynthSwitches.originalstructuralANDOR))
            {
                for(DGate xgate:structnetlist)
                        netlist.add(xgate);
            }
            else
            {
            
                if(invcheck.equals(NetSynthSwitches.noinv))
                {
                    if(structsize < dirsize)
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
                    if ((structsize < dirsize) && (structsize < invsize)) 
                    {
                        for (DGate xgate : structnetlist) 
                        {
                            netlist.add(xgate);
                        }
                    } 
                    else 
                    {
                        if (dirsize < invsize) 
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
                    dirnetlist = runDCEspressoAndABC(direct,synthesis,outputor,twonotstonor, nor3);
                    invnetlist = runInvertedDCEspressoAndABC(inverted,synthesis,outputor,twonotstonor, nor3);
                    
                    dirsize = getRepressorsCount(dirnetlist);
                    invsize = getRepressorsCount(invnetlist);
                    
                    if(invcheck.equals(NetSynthSwitches.noinv))
                    {
                        for (DGate xgate : dirnetlist) 
                        {
                            netlist.add(xgate);
                        }
                    }
                    else
                    {
                        if (dirsize < invsize) 
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
                    
                    
                    
                    dirnetlist = runEspressoAndABC(direct,synthesis,outputor,twonotstonor,nor3);
                    invnetlist = runInvertedEspressoAndABC(inverted,synthesis,outputor,twonotstonor,nor3);
                    
                    
                    
                    
                    dirsize = getRepressorsCount(dirnetlist);
                    invsize = getRepressorsCount(invnetlist);
                    
                    if(invcheck.equals(NetSynthSwitches.noinv))
                    {
                        for (DGate xgate : dirnetlist) 
                        {
                            netlist.add(xgate);
                        }
                    }
                    else
                    {
                        if (dirsize < invsize) 
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
        
        
        netsize = getRepressorsCount(netlist);
        
        //boolean precomputetriggered = false;
        if((synthesis.equals(NetSynthSwitches.precompute) || synthesis.equals(NetSynthSwitches.defaultmode)) && (inputnames.size()==3) && (outputnames.size()==1))
        {
            //System.out.println("Reached precompute");
            precompTT = BooleanSimulator.getTruthTable(netlist, inputnames);
            int ttval = Convert.bintoDec(precompTT.get(0));
            int invttval = Convert.bintoDec(Convert.invBin(precompTT.get(0)));
            
            if((ttval!=0) && (ttval!= 255))
            {
                
                List<List<DGate>> precompnetlist;
                precompnetlist = PreCompute.parseNetlistFile();
                
                List<DGate> dirprecompnet = new ArrayList<DGate>();
                List<DGate> invprecompnet = new ArrayList<DGate>();
            
                dirprecompnet = precompnetlist.get(ttval-1);
                invprecompnet = precompnetlist.get(invttval-1);
                dirprecompnet = runPrecomp(dirprecompnet,inputnames, outputnames,outputor,twonotstonor,nor3);
                invprecompnet = runinvPrecomp(invprecompnet,inputnames, outputnames,outputor,twonotstonor,nor3);
                
                precompsize = getRepressorsCount(dirprecompnet);
                invprecompsize = getRepressorsCount(invprecompnet);
                
                /*System.out.println("\n\nDirect Netlist\n");
                printNetlist(dirprecompnet);
                System.out.println("----------------------------");
                System.out.println("\n\nInverted Netlist\n");
                printNetlist(invprecompnet);
                System.out.println("----------------------------");
                */
                if(synthesis.equals(NetSynthSwitches.precompute))
                {
                    netlist = new ArrayList<DGate>();
                    //Over write netlist with precompute values
                    if(invcheck.equals(NetSynthSwitches.noinv))
                    {
                        for (DGate xgate : dirprecompnet) 
                        {
                            netlist.add(xgate);
                        }
                    }
                    else
                    {
                        if (precompsize < invprecompsize) 
                        {
                            for (DGate xgate : dirprecompnet) 
                            {
                                netlist.add(xgate);
                            }
                        } 
                        else 
                        {
                            for (DGate xgate : invprecompnet) 
                            {
                                netlist.add(xgate);
                            }
                        }
                    }
                }
                else
                {
                   
                    if(invcheck.equals(NetSynthSwitches.noinv))
                    {
                        if(precompsize <  netsize)
                        {
                            netlist = new ArrayList<DGate>();
                            for (DGate xgate : dirprecompnet) 
                            {
                                netlist.add(xgate);
                            }
                        }
                    }   
                    else
                    {
                        if((precompsize <  netsize) &&  (precompsize<= invprecompsize))
                        {
                            netlist = new ArrayList<DGate>();
                            for (DGate xgate : dirprecompnet) 
                            {
                                netlist.add(xgate);
                            }
                        }
                        else if((invprecompsize <  netsize) &&  (invprecompsize<= precompsize))
                        {
                            netlist = new ArrayList<DGate>();
                            for (DGate xgate : invprecompnet) 
                            {
                                netlist.add(xgate);
                            }
                        } 
                    }
                }
            }
        }
        if(outputOR3.equals(NetSynthSwitches.outputOR3))
        {
            netlist = convertToOutputOR3(netlist);
        }
        if(and2.equals(NetSynthSwitches.AND2) || and2.equals(NetSynthSwitches.AND2OR))
        {
            netlist = convertAND(netlist);
            if(and2.equals(NetSynthSwitches.AND2OR))
            {
                netlist = convertORbeforeAND(netlist);
            }
        }
        netlist = rewireNetlist(netlist);
        //printNetlist(netlist);
        return netlist;
        
    }
    
    /**Function*************************************************************
    <br>
    Synopsis    []
    <br>
    Description []
    <br>
    SideEffects []
    <br>
    SeeAlso     []
     * @param inpnetlist
     * @param inpnames
     * @param outpnames
     * @param outpor
     * @param twonots2nor
     * @return 
    ***********************************************************************/
    public static List<DGate> runinvPrecomp(List<DGate> inpnetlist, List<String> inpnames, List<String> outpnames, NetSynthSwitches outpor, NetSynthSwitches twonots2nor, NetSynthSwitches nor3)
    {
        DGate finalnot = new DGate();
        finalnot.gtype = DGateType.NOT;
        inpnetlist.get(inpnetlist.size()-1).output.wtype = DWireType.connector;
        finalnot.input.add(inpnetlist.get(inpnetlist.size()-1).output);
        DWire notout = new DWire();
        notout.name = outpnames.get(0);
        notout.wtype = DWireType.output;
        finalnot.output = notout;
        inpnetlist.add(finalnot);
        return runPrecomp(inpnetlist,inpnames,outpnames,outpor,twonots2nor, nor3);
    }
    
    
    /**Function*************************************************************
    <br>
    Synopsis    []
    <br>
    Description []
    <br>
    SideEffects []
    <br>
    SeeAlso     []
     * @param inpnetlist
     * @param inpnames
     * @param outpnames
     * @param outpor
     * @param twonots2nor
     * @return 
    ***********************************************************************/
    public static List<DGate> runPrecomp(List<DGate> inpnetlist, List<String> inpnames, List<String> outpnames, NetSynthSwitches outpor, NetSynthSwitches twonots2nor, NetSynthSwitches nor3)
    {
        List<DGate> finalnetlist = new ArrayList<DGate>();
        inpnetlist.get(inpnetlist.size()-1).output.name = outpnames.get(0);
        inpnetlist.get(inpnetlist.size()-1).output.wtype = DWireType.output;
        
        for(DGate xgate:inpnetlist)
        {
            for(DWire xwire:xgate.input)
            {
                if(xwire.wtype == DWireType.input)
                {
                    if(xwire.name.trim().equals("a"))
                    {
                        xwire.name = inpnames.get(0);
                    }
                    if(xwire.name.trim().equals("b"))
                    {
                        xwire.name = inpnames.get(1);
                    }
                    if(xwire.name.trim().equals("c"))
                    {
                        xwire.name = inpnames.get(2);
                    }
                }
            }
        }
        boolean outor;
        boolean not2nor;
        boolean nor3in = true;
         if(nor3.equals(NetSynthSwitches.defaultmode))
            nor3in = false;
        if(outpor.equals(NetSynthSwitches.defaultmode))
            outor = true;
        else
            outor = false;
        
        if(twonots2nor.equals(NetSynthSwitches.defaultmode))
            not2nor = true;
        else
            not2nor = false;
        
        
        finalnetlist = optimizeNetlist(inpnetlist,outor,not2nor,nor3in);
        
        return finalnetlist;
    }
    
    
    /**Function*************************************************************
    <br>
    Synopsis    []
    <br>
    Description []
    <br>
    SideEffects []
    <br>
    SeeAlso     []
     * @param circ
     * @param synthmode
     * @param outpor
     * @param twonots2nor
     * @return 
    ***********************************************************************/
    public static List<DGate> runDCEspressoAndABC(CircuitDetails circ,NetSynthSwitches synthmode, NetSynthSwitches outpor, NetSynthSwitches twonots2nor, NetSynthSwitches nor3) 
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
        boolean nor3in = true;
         if(nor3.equals(NetSynthSwitches.defaultmode))
            nor3in = false;
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
            EspCircuit = optimizeNetlist(EspCircuit,outor,not2nor,nor3in);
        }
            
        if(!synthmode.equals(NetSynthSwitches.espresso))
            ABCCircuit = parseEspressoOutToABC(EspOutput, outpor,twonots2nor, nor3);
        
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
    
    /**Function*************************************************************
    <br>
    Synopsis    []
    <br>
    Description []
    <br>
    SideEffects []
    <br>
    SeeAlso     []
     * @param circ
     * @param synthmode
     * @param outpor
     * @param twonots2nor
     * @return 
    ***********************************************************************/
    public static List<DGate> runInvertedDCEspressoAndABC(CircuitDetails circ,NetSynthSwitches synthmode, NetSynthSwitches outpor, NetSynthSwitches twonots2nor,NetSynthSwitches nor3)
    {
        
        boolean outor;
        boolean not2nor;
        boolean nor3in = true;
         if(nor3.equals(NetSynthSwitches.defaultmode))
            nor3in = false;
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
        
        finalEspCircuit = optimizeNetlist(finalEspCircuit,outor,not2nor,nor3in);
        
        finalABCCircuit = separateOutputGates(finalABCCircuit);
        finalABCCircuit = optimizeNetlist(finalABCCircuit,outor,not2nor,nor3in);
        
        if(!synthmode.equals(NetSynthSwitches.espresso))
            ABCCircuit = parseEspressoOutToABC(EspOutput, outpor,twonots2nor,nor3);
        
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
    
    /**Function*************************************************************
    <br>
    Synopsis    []
    <br>
    Description []
    <br>
    SideEffects []
    <br>
    SeeAlso     []
     * @param circ
     * @param synthmode
     * @param outpor
     * @param twonots2nor
     * @param nor3
     * @return 
    ***********************************************************************/
    public static List<DGate> runEspressoAndABC(CircuitDetails circ,NetSynthSwitches synthmode, NetSynthSwitches outpor, NetSynthSwitches twonots2nor, NetSynthSwitches nor3)
    {
        
        boolean outor;
        boolean not2nor;
        boolean nor3in = true;
         if(nor3.equals(NetSynthSwitches.defaultmode))
            nor3in = false;
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
        EspCircuit = optimizeNetlist(EspCircuit,outor,not2nor,nor3in);
        
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
            
            abcoutput = optimizeNetlist(abcoutput,outor,not2nor,nor3in);
            
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
    
    /**Function*************************************************************
    <br>
    Synopsis    []
    <br>
    Description []
    <br>
    SideEffects []
    <br>
    SeeAlso     []
     * @param circ
     * @param synthmode
     * @param outpor
     * @param twonots2nor
     * @return 
    ***********************************************************************/
    public static List<DGate> runInvertedEspressoAndABC(CircuitDetails circ,NetSynthSwitches synthmode, NetSynthSwitches outpor, NetSynthSwitches twonots2nor, NetSynthSwitches nor3)
    {
        
        
        
        boolean outor;
        boolean not2nor;
        boolean nor3in = true;
         if(nor3.equals(NetSynthSwitches.defaultmode))
            nor3in = false;
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
        
        
        
        
        finalEspCircuit = optimizeNetlist(finalEspCircuit,outor,not2nor,nor3in);
        
        finalABCCircuit = separateOutputGates(finalABCCircuit);
        
        finalABCCircuit = optimizeNetlist(finalABCCircuit,outor,not2nor,nor3in);
        
        
        
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
    
    /**Function*************************************************************
    <br>
    Synopsis    []
    <br>
    Description []
    <br>
    SideEffects []
    <br>
    SeeAlso     []
     * @param netlist
     * @return 
    ***********************************************************************/
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
                                System.out.println("Found that case!!");
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
        
        
        
        addgate = new HashMap<Integer,DGate>();
        for(int i=0;i<(netlist.size()-1);i++)
        {
            
            outToOutflag = 0;
            if(netlist.get(i).output.wtype.equals(DWireType.output))
            {
                for(int j=(i+1);j<netlist.size();j++)
                {
                    
                    for(int m=0;m<netlist.get(j).input.size();m++)
                    {
                        if(netlist.get(j).input.get(m).wtype.equals(DWireType.output) && netlist.get(j).input.get(m).name.trim().equals(netlist.get(i).output.name.trim()))
                        {
                            outToOutflag = 1;
                            //System.out.println("Found that case!! "+ printGate(netlist.get(i)));
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
        
        
        
        return finalnetlist;
    }
    
    /**Function*************************************************************
    <br>
    Synopsis    []
    <br>
    Description []
    <br>
    SideEffects []
    <br>
    SeeAlso     []
     * @param filename
     * @return 
    ***********************************************************************/
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
            
            abcoutput = optimizeNetlist(abcoutput,true,true,false);
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
    
    /**Function*************************************************************
    <br>
    Synopsis    []
    <br>
    Description []
    <br>
    SideEffects []
    <br>
    SeeAlso     []
     * @param filelines
     * @param filename
     * @return 
    ***********************************************************************/
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
    
    
    /**Function*************************************************************
    <br>
    Synopsis    []
    <br>
    Description []
    <br>
    SideEffects []
    <br>
    SeeAlso     []
     * @param inputs
     * @param hex
     * @param filename
    ***********************************************************************/
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
    
    
    
    /**Function*************************************************************
    <br>
    Synopsis    []
    <br>
    Description []
    <br>
    SideEffects []
    <br>
    SeeAlso     []
     * @param espout
     * @param outpor
     * @param twonots2nor
     * @return 
    ***********************************************************************/
    public static List<DGate> parseEspressoOutToABC(List<String> espout,NetSynthSwitches outpor, NetSynthSwitches twonots2nor,NetSynthSwitches nor3) 
    {
        List<DGate> netlistout = new ArrayList<DGate>();
        //List<String> espout = new ArrayList<String>();
        //espout = runEspresso(filename);
        /*for(String xline:espout)
        {
            System.out.println(xline);
        }*/
        boolean outor=false;
        boolean not2nor=false;
        boolean nor3in = true;
        
        if(outpor.equals(NetSynthSwitches.defaultmode))
            outor = true;
        
        if(nor3.equals(NetSynthSwitches.defaultmode))
            nor3in = false;
        
        
        if(twonots2nor.equals(NetSynthSwitches.defaultmode))
            not2nor = true;
        
        
        
        List<String> vfilelines = new ArrayList<String>();
        
        vfilelines = convertEspressoOutputToVerilog(espout);
        String vfilepath = "";
        vfilepath = create_VerilogFile(vfilelines,"espressoVerilog");
        try {
            netlistout = runABCverilog("espressoVerilog");
            netlistout = separateOutputGates(netlistout);
            
            netlistout = optimizeNetlist(netlistout,outor,not2nor,nor3in);
            
        } catch (InterruptedException ex) {
            Logger.getLogger(NetSynth.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return netlistout;
    }
    
    /**Function*************************************************************
    <br>
    Synopsis    []
    <br>
    Description []
    <br>
    SideEffects []
    <br>
    SeeAlso     []
     * @param espout
     * @return 
    ***********************************************************************/
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
    
    /**Function*************************************************************
    <br>
    Synopsis    []
    <br>
    Description []
    <br>
    SideEffects []
    <br>
    SeeAlso     []
     * @param filename
     * @return 
    ***********************************************************************/
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
            netlistout = optimizeNetlist(netlistout,true,true,false);            
            //netlistout = removeDoubleInverters(netlistout);
            //netlistout = outputORopt(netlistout);
            //netlistout = convert2NOTsToNOR(netlistout);
            //netlistout = rewireNetlist(netlistout);
        } catch (InterruptedException ex) {
            Logger.getLogger(NetSynth.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return netlistout;
    }
    
  
    
    /**Function*************************************************************
    <br>
    Synopsis    []
    <br>
    Description []
    <br>
    SideEffects []
    <br>    
    SeeAlso     []
     * @param x
     * @return 
    ***********************************************************************/
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
 
  
    /**Function*************************************************************
    <br>
    Synopsis    []
    <br>
    Description []
    <br>
    SideEffects []
    <br>    
    SeeAlso     []
     * @param filename
     * @return 
     * @throws java.lang.InterruptedException
    ***********************************************************************/
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
    
    
    /**Function*************************************************************
    <br>
    Synopsis    []
    <br>
    Description []
    <br>
    SideEffects []
    <br>
    SeeAlso     []
     * @param filename
     * @return 
     * @throws java.lang.InterruptedException
    ***********************************************************************/
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
    
    
    /**Function*************************************************************
    <br>
    Synopsis    []
    <br>
    Description []
    <br>
    SideEffects []
    <br>
    SeeAlso     []
     * @return 
    ***********************************************************************/
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
    
    
    
          
    
    /**Function*************************************************************
    <br>
    Synopsis    []
    <br>
    Description []
    <br>
    SideEffects []
    <br>
    SeeAlso     []
     * @param netlist
     * @return 
    ***********************************************************************/
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
    
    
    /**Function*************************************************************
    <br>
    Synopsis    []
    <br>
    Description []
    <br>
    SideEffects []
    <br>
    SeeAlso     []
     * @param pathFile
     * @return 
    ***********************************************************************/
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
    
    
    /**Function*************************************************************
    <br>
    Synopsis    []
    <br>
    Description []
    <br>
    SideEffects []
    <br>
    SeeAlso     []
     * @param espinp
     * @return 
    ***********************************************************************/
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
    
    /**Function*************************************************************
    <br>
    Synopsis    []
    <br>
    Description []
    <br>
    SideEffects []
    <br>
    SeeAlso     []
     * @param espinp
     * @return 
    ***********************************************************************/
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
    
    
    /**Function*************************************************************
    <br>
    Synopsis    []
    <br>
    Description []
    <br>
    SideEffects []
    <br>
    SeeAlso     []
     * @param espinp
     * @return 
    ***********************************************************************/
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
    
    
    /**Function*************************************************************
    <br>
    Synopsis    []
    <br>
    Description []
    <br>
    SideEffects []
    <br>
    SeeAlso     []
     * @param netlist
     * @return 
    ***********************************************************************/
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
    
    
    /***************************************************************
    Function
    <br>
    Synopsis    []
    <br>
    Description []
    <br>
    SideEffects []
    <br>
    SeeAlso     []
    <br>
     * @param netlistinp
     * @return 
***********************************************************************/
    public static List<DGate> convertToNOR3(List<DGate> netlistinp)
    {
        //List<DGate> netlistout = new ArrayList<DGate>();
        //List<Integer> removegates = new ArrayList<Integer>();
        for(int i=0;i<netlistinp.size();i++)
        {
            if(netlistinp.get(i).gtype.equals(DGateType.NOR) && (netlistinp.get(i).input.size()<3))
            {
                for(int j=i+1;j<netlistinp.size();j++)
                {
                    if(netlistinp.get(j).gtype.equals(DGateType.NOT) && (netlistinp.get(j).input.get(0).name.trim().equals(netlistinp.get(i).output.name.trim())))
                    {
                        for(int k=j+1;k<netlistinp.size();k++)
                        {
                            if(netlistinp.get(k).gtype.equals(DGateType.NOR) && (netlistinp.get(i).input.size()<3))
                            {
                                int indx=0;
                                boolean found = false;
                                for(int m =0;m<netlistinp.get(k).input.size();m++)
                                {
                                    if(netlistinp.get(k).input.get(m).name.trim().equals(netlistinp.get(j).output.name.trim()))
                                    {
                                        found = true;
                                        indx = m;
                                    }
                                }
                                if(found)
                                {
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
        return netlistinp;
    }
    
    
    /***************************************************************
    Function
    <br>
    Synopsis    []
    <br>
    Description []
    <br>
    SideEffects []
    <br>
    SeeAlso     []
    <br>
     * @param netlistinp
     * @return 
***********************************************************************/
    public static List<DGate> convertToOutputOR3(List<DGate> netlistinp)
    {
        //List<DGate> netlistout = new ArrayList<DGate>();
        //List<Integer> removegates = new ArrayList<Integer>();
        for(int i=0;i<netlistinp.size();i++)
        {
            if(netlistinp.get(i).gtype.equals(DGateType.NOR) && (netlistinp.get(i).input.size()<3))
            {
                for(int j=i+1;j<netlistinp.size();j++)
                {
                    if(netlistinp.get(j).gtype.equals(DGateType.NOT) && (netlistinp.get(j).input.get(0).name.trim().equals(netlistinp.get(i).output.name.trim())))
                    {
                        for(int k=j+1;k<netlistinp.size();k++)
                        {
                            if(netlistinp.get(k).gtype.equals(DGateType.NOR) && (netlistinp.get(i).input.size()<3))
                            {
                                int indx=0;
                                boolean found = false;
                                for(int m =0;m<netlistinp.get(k).input.size();m++)
                                {
                                    if(netlistinp.get(k).input.get(m).name.trim().equals(netlistinp.get(j).output.name.trim()))
                                    {
                                        for(int l=k+1;l<netlistinp.size();l++)
                                        {
                                            if((netlistinp.get(l).gtype.equals(DGateType.NOT)) && (netlistinp.get(l).input.get(0).name.trim().equals(netlistinp.get(k).output.name.trim()))  && (netlistinp.get(l).output.wtype.equals(DWireType.output)) )
                                            {
                                                found = true;
                                                System.out.println("Found output OR 3");
                                                netlistinp.get(l).gtype = DGateType.OR;
                                                netlistinp.get(l).input.remove(0);
                                                netlistinp.get(l).input.addAll(netlistinp.get(i).input);
                                                for(int x =0;x<netlistinp.get(k).input.size();x++)
                                                {
                                                    if(x!=m)
                                                    {
                                                        netlistinp.get(l).input.add(netlistinp.get(k).input.get(x));
                                                    }
                                                }
                                            }
                                        }
                                        
                                        //
                                    }
                                }
                                
                            }
                        }
                    }
                }
            }
            if(netlistinp.get(i).gtype.equals(DGateType.NOR) && (netlistinp.get(i).input.size()==3))
            {
                for(int j=i+1;j<netlistinp.size();i++)
                {
                    if(netlistinp.get(j).gtype.equals(DGateType.NOT) && netlistinp.get(j).input.get(0).name.trim().equals(netlistinp.get(i).output.name.trim()) && netlistinp.get(j).output.wtype.equals(DWireType.output))
                    {
                        netlistinp.get(j).gtype = DGateType.OR;
                        netlistinp.get(j).input.remove(i);
                        netlistinp.get(j).input.addAll(netlistinp.get(i).input);
                        
                    }
                }
            }
        }
        netlistinp = removeDanglingGates(netlistinp);
        return netlistinp;
    }
    
    
    /***************************************************************
    Function
    <br>
    Synopsis    []
    <br>
    Description []
    <br>
    SideEffects []
    <br>
    SeeAlso     []
    <br>
     * @param netlistinp
     * @return 
***********************************************************************/
    public static List<DGate> convertORbeforeAND(List<DGate> netlistinp)
    {
        for(int i=0;i<netlistinp.size();i++)
        {
            if(netlistinp.get(i).gtype.equals(DGateType.NOR))
            {
                for(int j=i+1;j<netlistinp.size();j++)
                {
                    if(netlistinp.get(j).gtype.equals(DGateType.NOT) && netlistinp.get(j).input.get(0).name.trim().equals(netlistinp.get(i).output.name.trim()))
                    {
                        for(int k=j+1;k<netlistinp.size();k++)
                        {
                            if(netlistinp.get(k).gtype.equals(DGateType.AND))
                            {
                                int indx=0;
                                boolean found = false;
                                for(int m=0;m<netlistinp.get(k).input.size();m++)
                                {
                                    if(netlistinp.get(k).input.get(m).name.trim().equals(netlistinp.get(j).output.name.trim()))
                                    {
                                        indx = m;
                                        found = true;
                                    }
                                }
                                if(found)
                                {
                                    
                                    netlistinp.get(k).input.remove(indx);
                                    DGate orgate = new DGate();
                                    orgate.input.addAll(netlistinp.get(i).input);
                                    orgate.gtype = DGateType.OR;
                                    DWire orOutWire = new DWire("0Wire" +Global.wirecount++,netlistinp.get(j).output.wtype);
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
    
    
    /***************************************************************
    Function
    <br>
    Synopsis    []
    <br>
    Description []
    <br>
    SideEffects []
    <br>
    SeeAlso     []
    <br>
     * @param netlistinp
     * @return 
***********************************************************************/
    public static List<DGate> convertAND(List<DGate> netlistinp)
    {
        int countAND = 0;
        for(int i=0;i<netlistinp.size();i++)
        {
            if(netlistinp.get(i).gtype.equals(DGateType.NOT))
            {
                for(int j=i+1;j<netlistinp.size();j++)
                {
                    if(netlistinp.get(j).gtype.equals(DGateType.NOT))
                    {
                        for(int k=j+1;k<netlistinp.size();k++)
                        {
                            if(netlistinp.get(k).gtype.equals(DGateType.NOR) && (netlistinp.get(k).input.size() == 2))
                            {
                                if((netlistinp.get(k).input.get(0).name.trim().equals(netlistinp.get(i).output.name.trim()) && netlistinp.get(k).input.get(1).name.trim().equals(netlistinp.get(j).output.name.trim()))||(netlistinp.get(k).input.get(0).name.trim().equals(netlistinp.get(j).output.name.trim()) && netlistinp.get(k).input.get(1).name.trim().equals(netlistinp.get(i).output.name.trim())) )
                                {
                                    if(countAND == 2)
                                        break;
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
                    if(countAND == 2)
                        break;
                }
            }
            if(countAND == 2)
                      break;
        }
        
        netlistinp = removeDanglingGates(netlistinp);
        return netlistinp;
    }
    
    
    /**Function*************************************************************
    <br>
    Synopsis    [Search for the 2 NOTs to NOR motif and replace it with a NOR gate]
    <br>
    Description []
    <br>
    SideEffects []
    <br>
    SeeAlso     []
     * @param netlistinp
     * @return 
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
    <br>
    Synopsis    [Remove Double inverters motif]
    <br>
    Description []
    <br>
    SideEffects []
    <br>
    SeeAlso     []
     * @param netlistinp
     * @return 
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
    <br>
    Synopsis    [Replace Output_OR]
    <br>
    Description []
    <br>
    SideEffects []
    <br>
    SeeAlso     []
     * @param finalnetlist
     * @return 
    ***********************************************************************/
    public static List<DGate> outputORopt(List<DGate> finalnetlist)
    {
        
        List removegates = new ArrayList<Integer>();
        /*System.out.println("\nBefore Output_OR optimization");
        printNetlist(finalnetlist);
        System.out.println("--------------------------");
        */
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
    <br>
    Synopsis    [Removes Gates where the output is not of type DWireType.Output and the inputs are not outputs of any other Gate in the circuit]
    <br>
    Description []
    <br>
    SideEffects []
    <br>
    SeeAlso     []
     * @param netlist Input netlist
     * @return 
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
        for(int i=0;i<tempnetlist.size();i++)
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
    
    
    /**Function*************************************************************
    <br>
    Synopsis    []
    <br>
    Description []
    <br>
    SideEffects []
    <br>
    SeeAlso     []
     * @param naivenetlist
     * @param nor3
     * @param originalAND
     * @return 
    ***********************************************************************/
    public static List<DGate> parseStructuralVtoNORNOT(List<DGate> naivenetlist,NetSynthSwitches nor3, NetSynthSwitches originalAND)
    {
        List<DGate> structnetlist = new ArrayList<DGate>();
        //List<DGate> naivenetlist = new ArrayList<DGate>();
        //naivenetlist = parseVerilogFile.parseStructural(filepath);
        
        naivenetlist = removeDanglingGates(naivenetlist);
        //printNetlist(naivenetlist);
        //System.out.println("------------------------------------");
        List<DGate> reducedfanin = new ArrayList<DGate>();
        
        //System.out.println("Before Optimizing");
        //printNetlist(structnetlist);
        //if(!synthesis.equals(NetSynthSwitches.originalstructural))
        //{
        for(int i=0;i<naivenetlist.size();i++)
        {
            for(DGate redgate: NetlistConversionFunctions.ConvertToFanin2(naivenetlist.get(i)))
            {
                reducedfanin.add(redgate);
            }
        }
        for(int i=0;i<reducedfanin.size();i++)
        {
            for(DGate structgate: NetlistConversionFunctions.GatetoNORNOT(reducedfanin.get(i),originalAND))
            {
                structnetlist.add(structgate);
            }
        }
        
        
        
        boolean nor3in = true;
         if(nor3.equals(NetSynthSwitches.defaultmode))
            nor3in = false;
        structnetlist = optimizeNetlist(structnetlist,true,true,nor3in);
        //}
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
    
    /**Function*************************************************************
    <br>
    Synopsis    []
    <br>
    Description []
    <br>    
    SideEffects []
    <br>    
    SeeAlso     []
     * @param inpNetlist
     * @return 
    ***********************************************************************/
    public static List<DGate> removeDuplicateNots(List<DGate> inpNetlist)
    {
        List<DGate> outpNetlist = new ArrayList<DGate>();
        List<Integer> removelist = new ArrayList<Integer>();
        for(int i=0;i<inpNetlist.size();i++)
        {
            if(inpNetlist.get(i).gtype.equals(DGateType.NOT) && (!inpNetlist.get(i).output.wtype.equals(DWireType.output)))
            {
                String inpname = inpNetlist.get(i).input.get(0).name.trim();
                String setoutname = inpNetlist.get(i).output.name.trim();
                for(int j=i+1;j<inpNetlist.size();j++)
                {
                    if(inpNetlist.get(j).gtype.equals(DGateType.NOT) && (!inpNetlist.get(j).output.wtype.equals(DWireType.output)) && inpNetlist.get(j).input.get(0).name.trim().equals(inpname))
                    {
                        removelist.add(j);
                        String outpname = inpNetlist.get(j).output.name.trim();
                        for(int k=j+1;k<inpNetlist.size();k++)
                        {
                            for(int m=0;m<inpNetlist.get(k).input.size();m++)
                            {
                                if(inpNetlist.get(k).input.get(m).name.trim().equals(outpname))
                                {
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
        for(int i=0;i<inpNetlist.size();i++)
        {
            if(!removelist.contains(i))
            {
                outpNetlist.add(inpNetlist.get(i));
            }
        }
        
        return outpNetlist;
    }
    
    
    
    /**Function*************************************************************
    <br>
    Synopsis    []
    <br>
    Description []
    <br>
    SideEffects []
    <br>
    SeeAlso     []
     * @param inpNetlist
     * @param outputor
     * @param twoNotsToNor
     * @return 
    ***********************************************************************/
    public static List<DGate> optimizeNetlist(List<DGate> inpNetlist,boolean outputor,boolean twoNotsToNor, boolean nor3)
    {
        
       
        
        List<DGate> outpNetlist = new ArrayList<DGate>();
        
        outpNetlist = removeDanglingGates(inpNetlist);
        outpNetlist = separateOutputGates(outpNetlist);
        outpNetlist = removeDuplicateNots(outpNetlist);
        outpNetlist = removeDoubleInverters(outpNetlist);
        outpNetlist = removeDuplicateNots(outpNetlist);
        
        
        
        outpNetlist = removeDanglingGates(outpNetlist);
        if(outputor)
            outpNetlist = outputORopt(outpNetlist);
        if(twoNotsToNor)
            outpNetlist = convert2NOTsToNOR(outpNetlist);
        
        if(nor3)
            outpNetlist = convertToNOR3(outpNetlist);
            
        
        outpNetlist = removeDanglingGates(outpNetlist);
        
        outpNetlist = rewireNetlist(outpNetlist);
        
        return outpNetlist;
    }
    
    
    /**Function*************************************************************
    <br>
    Synopsis    []
    <br>
    Description []
    <br>
    SideEffects []
    <br>
    SeeAlso     []
     * @param espinp
     * @return 
    ***********************************************************************/
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
    
    /**Function*************************************************************
    <br>
    Synopsis    []
    <br>
    Description []
    <br>
    SideEffects []
    <br>
    SeeAlso     []
     * @param inpWires
     * @return 
    ***********************************************************************/
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
    
    
    
    /**Function*************************************************************
    <br>
    Synopsis    []
    <br>
    Description []
    <br>
    SideEffects []
    <br>
    SeeAlso     []
     * @param inpWires
     * @param gtype
     * @return 
    ***********************************************************************/
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
    
    
    /***************************************************************Function
    <br>
    Synopsis    []
    <br>
    Description []
    <br>
    SideEffects []
    <br>
    SeeAlso     []
     * @param inpWires
     * @param gOrAnd
     * @return 
    ***********************************************************************/
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
    
    /***************************************************************Function
    <br> 
    Synopsis    [Netlist to Directed Acyclic Graph]
    <br> 
    Description [Converts a Netlist into a Directed Acyclic Graph with multiple outputs]
    <br>
    SideEffects []
    <br>
    SeeAlso     []
     * @param netlist Input Netlist
     * @return Directed Acyclic Graph with multiple outputs
    ***********************************************************************/
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
            else if(netg.gtype.equals(DGateType.AND))
            {
                Gate norg = new Gate(indx,GateType.AND.toString());
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
   
    
    /***************************************************************Function
    <br> 
    Synopsis    [Netlist to Directed Acyclic Graph]
    <br> 
    Description [Converts a netlist into a Directed Acyclic Graph. DGates are Vertices and DWires are Arcs]
    <br> 
    SideEffects []
    <br> 
    SeeAlso     []
    <br> 
     * @param netlist Input netlist 
     * @return A Directed Acyclic Graph
    ***********************************************************************/
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
   
    /***************************************************************Function
     <br> 
    Synopsis    [prints a Netlist]
    <br> 
    Description [Takes a List of DGates as input, and prints each DGate sequentially]
    <br> 
    SideEffects []
    <br> 
    SeeAlso     []
    <br> 
     * @param netlist The netlist to be printed.
    ***********************************************************************/
    public static void printNetlist(List<DGate> netlist)
    {
        for(int i=0;i<netlist.size();i++)
            System.out.println(printGate(netlist.get(i)));
    }
    
    /***************************************************************
    Function
    <br> 
    Synopsis    [prints a DGate]
    <br>
    Description [Takes a DGate as an input, and prints in the following format: DGateType(Output, Input1, Input2,....Inputn)]
    <br> 
    SideEffects []
    <br> 
    SeeAlso     []
    <br> 
     * @param g The DGate to be printed
     * @return A string representation of the DGate
    ***********************************************************************/
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