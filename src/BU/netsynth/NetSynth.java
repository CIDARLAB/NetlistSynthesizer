/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */



package BU.netsynth;

import BU.CelloGraph.DAGEdge;
import BU.CelloGraph.DAGVertex;
import BU.CelloGraph.DAGVertex.VertexType;
import BU.CelloGraph.DAGW;
import BU.CelloGraph.DAGraph;

import MIT.dnacompiler.Gate;
import MIT.dnacompiler.Gate.GateType;
import MIT.dnacompiler.Wire;

import BU.ParseVerilog.CircuitDetails;
import BU.ParseVerilog.Convert;
import BU.ParseVerilog.Espresso;
import BU.ParseVerilog.Parser;
import BU.ParseVerilog.parseCaseStatements;
import BU.booleanLogic.BooleanSimulator;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

import BU.netsynth.DGate.DGateType;
import BU.netsynth.DWire.DWireType;
import BU.precomputation.PreCompute;
import BU.precomputation.genVerilogFile;
import MIT.dnacompiler.HeuristicSearch;
import MIT.dnacompiler.LoadTables;


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
        HeuristicSearch.beginSearch(null, 0.999);
        
        //for(int i=0;i<257;i++)
        //    System.out.println(Convert.InttoHex(i));
        
       //genVerilogFile.createVerilogFile(4, "0xBBBBB" );
        //System.out.println(Convert.HextoInt("6"));
       //vtestfunc();
        
        //verifyinverse();
        //histogram();
        //DAGW xcasedag = testParser("",1,1);
        //verifyprecomute();
        //DAGraph x = precompute(2);
        //DAGW y = computeDAGW(14);
        //testnetlistmodule();
        //testEspresso();
        
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
    
    public static void histogram()
    {
        String filestring ="";
          if(Filepath.contains("prashant"))
          {
              filestring += Filepath+ "src/BU/resources/Histogram";
          }
          else
          {
              filestring += Filepath+ "BU/resources/Histogram";
          }
        
          
            //filestring += Global.espout++ ;
            filestring += ".csv";
            File fespinp = new File(filestring);
        try 
        {
            Writer output = new BufferedWriter(new FileWriter(fespinp));
            String Line = "SrNo,PreComputer,Espresspo\n";
            output.write(Line);
            List<List<DGate>> precomp;
            precomp = PreCompute.parseNetlistFile();
            CircuitDetails circ = new CircuitDetails();
            circ.inputNames.add("A");
            circ.inputNames.add("B");
            circ.inputNames.add("C");
            circ.outputNames.add("O");
            for(int i=0;i<256;i++)
            {
                Line = "";
                Line += (i + ",");
                if(i==0 || i==255)
                    Line += ("1,"); 
                else
                    Line += (precomp.get(i-1).size() + ",");
                
                
                
                circ.inputgatetable = i;
                List<String> eslines = new ArrayList<String>();
                eslines = Espresso.createFile(circ);
                String filestring2 = "";
                if(Filepath.contains("prashant"))
                {
                    filestring2 += Filepath+ "src/BU/resources/espresso";
                }
                else
                {
                    filestring2 += Filepath+ "BU/resources/espresso";
                }
                filestring2 += Global.espout++ ;
                filestring2 += ".txt";
                File fespinp2 = new File(filestring2);
                try 
                {
                    Writer output2 = new BufferedWriter(new FileWriter(fespinp2));
                    for(String xline:eslines)
                    {
                        String newl = (xline + "\n");
                        output2.write(newl);
                    }
                    output2.close();
                    List<String> espout2 = new ArrayList<String>();
                    espout2 = runEspresso(filestring2);
                    List<DGate> espoutput2 = new ArrayList<DGate>();
                    espoutput2 = parseEspressoToNORNAND(espout2);
                    String xf ="";
                    xf = BooleanSimulator.bpermute(espoutput2);
                    
                    //if(i==2)
                    //if(i==253 || i == 254 || i==251 || i==239)
                        //System.out.println(xf);
                    Line += espoutput2.size();
                    fespinp2.deleteOnExit();
              
                } 
                catch (IOException ex) 
                {
                    Logger.getLogger(NetSynth.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                Line += "\n";
                output.write(Line);
            }
            output.close();
        
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(NetSynth.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        caseCirc = parseCaseStatements.input3case(path);
        //System.out.println(caseCirc.inputgatetable);
        DAGW circuitDAG = new DAGW();
        DAGW circuitDAGinv = new DAGW();
        int invindx ;
        
        
        
        if(caseCirc.inputgatetable ==0 || caseCirc.inputgatetable ==255)
            return null;
        
         // <editor-fold desc="If 3 input circuit">
        if((caseCirc.inputNames.size() == 3) && (presynth ==1))
        {
            DAGW circ = computeDAGW(caseCirc.inputgatetable-1);
            circuitDAG = new DAGW(circ.Gates,circ.Wires);
            DAGW circinv = computeDAGW(255 - caseCirc.inputgatetable - 1);
            circuitDAGinv = new DAGW(circinv.Gates,circinv.Wires);
            
            if (circuitDAGinv.Gates.size() > 1 && (inv == 1)) 
            {
                int gatessize = circuitDAGinv.Gates.size();
                if (circuitDAGinv.Gates.get(1).Type == "NOR" || circuitDAGinv.Gates.get(1).Type == "NOT") 
                {
                    if (circuitDAG.Gates.size() > (circuitDAGinv.Gates.size() - 1)) 
                    {
                        circuitDAG = circuitDAGinv;
                        gatessize = circuitDAGinv.Gates.size();
                        if(circuitDAG.Gates.get(1).Type == "NOR")
                        {
                            circuitDAG.Gates.get(1).Type = GateType.OUTPUT_OR.toString();
                            circuitDAG.Gates.get(1).Name = circuitDAG.Gates.get(0).Name;
                            circuitDAG.Gates.remove(0);
                        }
                        else if(circuitDAG.Gates.get(1).Type == "NOT")
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
            String caseCirctt = Convert.dectoBin(caseCirc.inputgatetable, powr);
            String caseCircttinv;
            caseCircttinv = Convert.invBin(caseCirctt);
            int invval = Convert.bintoDec(caseCircttinv);
            CircuitDetails invcaseCirc = new CircuitDetails(caseCirc.inputNames,caseCirc.outputNames,invval);
            
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
                espoutput = parseEspressoToNORNAND(espout);
                
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
                espoutputinv = parseEspressoToNORNAND(espoutinv);
                
                //DAGW circ = computeDAGW(caseCirc.inputgatetable-1);
                circuitDAG = CreateDAGW(espoutput);
                //DAGW circinv = computeDAGW(255 - caseCirc.inputgatetable - 1);
                circuitDAGinv = CreateDAGW(espoutputinv);
                
                if (circuitDAGinv.Gates.size() > 1 && (inv == 1)) 
                {
                    
                    int gatessize = circuitDAGinv.Gates.size();
                    if (circuitDAGinv.Gates.get(1).Type == "NOR" || circuitDAGinv.Gates.get(1).Type == "NOT") 
                    {
                        if (circuitDAG.Gates.size() > (circuitDAGinv.Gates.size() - 1)) 
                        {
                            System.out.println("Special Case");
                            circuitDAG = circuitDAGinv;
                            gatessize = circuitDAGinv.Gates.size();
                            if(circuitDAG.Gates.get(1).Type == "NOR")
                            {
                                circuitDAG.Gates.get(1).Type = GateType.OUTPUT_OR.toString();
                                circuitDAG.Gates.get(1).Name = circuitDAG.Gates.get(0).Name;
                                circuitDAG.Gates.remove(0);
                            }
                            else if(circuitDAG.Gates.get(1).Type == "NOT")
                            {
                                circuitDAG.Gates.get(1).Type = GateType.OUTPUT.toString();
                                circuitDAG.Gates.get(1).Name = circuitDAG.Gates.get(0).Name;
                                circuitDAG.Gates.remove(0);
                            }
                        }
                    }
                }
                
                
                /*if(espoutput.size() > (espoutputinv.size() + 1))
                {
                    System.out.println("Special Condition");
                    espoutput = espoutputinv;
                    String Wirename = "Wire" + Global.wirecount++;
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
                for(DGate netgate:espoutput)
                {
                    System.out.println(netlist(netgate));
                }
                //circuitDAG = CreateDAGW(espoutput);
                fespinp.deleteOnExit();
                //System.out.println(espperm);
            } 
            catch (IOException ex) 
            {
                Logger.getLogger(NetSynth.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
        }
        
        
        
        
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
        circ = CreateDAGW(precomp.get(x));

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
                String gateString = netlist(SOPgates.get(0));
                System.out.println(gateString);
            
        }
        else
        {
            for(DGate g:SOPgates)
            {
               
                String gateString = netlist(g);
                System.out.println(gateString);
            }
        }
        
        NORgates = parseEspressoToNORNAND(espressoOut);
     
        System.out.println("\nUniversal Gates : ");
        if(functionOutp)
        {
                String gateString = netlist(NORgates.get(0));
                System.out.println(gateString);
            
        }
        else
        {
            for(DGate g:NORgates)
            {  
                String gateString = netlist(g);
                System.out.println(gateString);
            }
           
        }
        
    }
   
    public static List<String> runEspresso(String pathFile) {
    
        
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
        testgateType = DGateType.NOR2;
        DGate gtest = new DGate(testgateType,inputWires,outp);
        
        List<DGate> test = new ArrayList<DGate>();
        test = NetlistConversionFunctions.GatetoNORNOT(gtest);

        for(DGate gout:test)
        {
            
            String netbuilder = "";
            netbuilder = netlist(gout);
            System.out.println(netbuilder);
        
        }   
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
                    prodGates = AndORGates(minTemp,DGateType.OR2);         
                }
                else
                {
                    prodGates = AndORGates(minTemp,DGateType.AND2);         
                }
                orWires.add(prodGates.get(prodGates.size()-1).output);
                sopexp.addAll(prodGates);
            }
        }
        
        prodGates = new ArrayList<DGate>();
        if(POSmode)
        {
            prodGates = AndORGates(orWires,DGateType.AND2);
        }
        else
        {
            prodGates = AndORGates(orWires,DGateType.OR2);
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
    
    
    
    public static List<DGate> parseEspressoToNORNAND(List<String> espinp)
    {
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
                    prodGates = NORNANDGates(minTemp,DGateType.NOR2);         
                }
                else
                {
                    prodGates = NORNANDGates(minTemp,DGateType.NAND2);         
                }
                orWires.add(prodGates.get(prodGates.size()-1).output);
                sopexp.addAll(prodGates);
            }
        }
        boolean no2ndstageGate = false;
        prodGates = new ArrayList<DGate>();
        if(POSmode)
        {
            prodGates = NORNANDGates(orWires,DGateType.NOR2);
            
            if(prodGates.isEmpty())
            {
                no2ndstageGate = true;
            }
                
        }
        else
        {
            prodGates = NORNANDGates(orWires,DGateType.NAND2);
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
                String Wirename = "Wire" + Global.wirecount++;
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
    
    
    
    
    public static List<DGate> notGates(List<DWire> andWires)
    {
        List<DGate> notInp = new ArrayList<DGate>();
        for(DWire xWire:andWires)
        {
            String Wirename = "Wire" + Global.wirecount++;
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
                String Wirename = "Wire" + Global.wirecount++;
                
                DWire aout = new DWire(Wirename);
                DGate andG = new DGate(gtype,ainp,aout);
                minterm.add(andG);
                if(wireCount>2)
                {
                    Wirename = "Wire" + Global.wirecount++;
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
                String Wirename = "Wire" + Global.wirecount++;
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
                String Wirename = "Wire" + Global.wirecount++;
                
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
                String Wirename = "Wire" + Global.wirecount++;
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
        if(netlist.size() == 1 && netlist.get(0).gtype == DGateType.BUF)
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
        if(netlist.size()>1)
        {
            int lastnet = netlist.size()-1;
            if((netlist.get(lastnet).gtype == DGateType.NOT) && (netlist.get(lastnet-1).gtype == DGateType.NOR2))
            {
                netlist.get(lastnet).gtype = DGateType.OR2;
                netlist.get(lastnet).input = netlist.get(lastnet-1).input;
                netlist.remove(lastnet-1);
            }

        }
        
        
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
                if(netg.gtype == DGateType.OR2)
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
                    else if(netg.gtype == DGateType.NOR2)
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
                else if(netg.gtype == DGateType.NOR2)
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
           
            if((netg.output.wtype == DWireType.output) && (netg.gtype != DGateType.OR2))
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
   
   
    
    
    
    public static String netlist(DGate g)
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