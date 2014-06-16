/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Test;

import BU.CelloGraph.DAGW;
import BU.ParseVerilog.CircuitDetails;
import BU.ParseVerilog.Convert;
import BU.ParseVerilog.Espresso;
import BU.ParseVerilog.parseVerilogFile;
import BU.booleanLogic.BooleanSimulator;
import BU.netsynth.DGate;
import BU.netsynth.DGate.DGateType;
import BU.netsynth.DWire;
import BU.netsynth.DWire.DWireType;
import BU.netsynth.Global;
import BU.netsynth.NetSynth;
import static BU.netsynth.NetSynth.Filepath;
import static BU.netsynth.NetSynth.parseEspressoToNORNAND;
import static BU.netsynth.NetSynth.runEspresso;
import BU.netsynth.NetlistConversionFunctions;
import BU.precomputation.PreCompute;
import BU.precomputation.genVerilogFile;
import MIT.dnacompiler.Gate;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author prashantvaidyanathan
 */
public class TestSynthesis {
    
    /**
     * 
     * 
     * 
     * 
     * 
     */
    
    
    
    public static void testAllnInputVerilog(int n)
    {
        int maxpow = (int) Math.pow(2,Math.pow(2,n));   
        for(int i=0;i<maxpow;i++)
        {
            List<String> verilogFileLines = new ArrayList<String>();
            verilogFileLines = genVerilogFile.createSingleOutpVerilogFile(n, i);
            String filepath = NetSynth.create_VerilogFile(verilogFileLines, "TestNinput");
            DAGW newdag = new DAGW();
            newdag = NetSynth.runNetSynth(filepath);
            
            /*if(newdag.Gates.size() == 4)
            {
                System.out.println(i);
                
                for(Gate xgate:newdag.Gates)
                    System.out.println(xgate.Type + ":" + xgate.Name );
            }
            */
            System.out.println(i + " : "+(newdag.Gates.size()));
        }
    }
    
    public static void testVerilogrunNetSynth()
    {
        
        String path = Filepath;
        Filepath = parseVerilogFile.class.getClassLoader().getResource(".").getPath();
         
        if(Filepath.contains("prashant"))
        {
            if(Filepath.contains("build/classes/"))
                Filepath = Filepath.substring(0,Filepath.lastIndexOf("build/classes/")); 
            else if(Filepath.contains("src"))
                Filepath = Filepath.substring(0,Filepath.lastIndexOf("src/"));
            Filepath += "src/BU/ParseVerilog/Verilog.v";
           //Filepath += "src/BU/resources/TestNinput.v";
            path = Filepath;
        }
        System.out.println(NetSynth.runNetSynth(path).Gates.size());
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
                
                
                circ.truthTable.add(Convert.dectoBin(i,circ.inputNames.size() ));
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
    
    
    
    public static void testParseVerilogFileFunctions()
    {
        String Filepath;
        String filestring ="";
        Filepath = TestSynthesis.class.getClassLoader().getResource(".").getPath();
        if(Filepath.contains("build/classes/"))
            Filepath = Filepath.substring(0,Filepath.lastIndexOf("build/classes/")); 
        else if(Filepath.contains("src"))
            Filepath = Filepath.substring(0,Filepath.lastIndexOf("src/"));
        if (Filepath.contains("prashant")) 
        {
            filestring += Filepath + "src/BU/ParseVerilog/Verilog.v";
        } 
        else 
        {
            filestring += Filepath + "BU/ParseVerilog/Verilog.v";
        }
        NetSynth.runNetSynth(filestring);
        /*String alllines = parseVerilogFile.verilogFileLines(filestring);
        boolean isStructural = parseVerilogFile.isStructural(alllines);
        if(isStructural)
            System.out.println("Structural Verilog Code!");
        else
        {
            boolean hasCaseStatements = parseVerilogFile.hasCaseStatements(alllines);
            if(hasCaseStatements)
                System.out.println("Has Case Statements!");
            else
                System.out.println("No case statements");
        }*/
    }
    
    
    public static void testespressotoabc()
    {
        String Filepath;
        String filestring ="";
        Filepath = TestSynthesis.class.getClassLoader().getResource(".").getPath();
        if(Filepath.contains("build/classes/"))
            Filepath = Filepath.substring(0,Filepath.lastIndexOf("build/classes/")); 
        else if(Filepath.contains("src"))
            Filepath = Filepath.substring(0,Filepath.lastIndexOf("src/"));
        if (Filepath.contains("prashant")) 
        {
            filestring += Filepath + "src/BU/resources/testespfile.txt";
        } 
        else 
        {
            filestring += Filepath + "BU/resources/testespfile.txt";
        }
        
        List<DGate> netlistoutp = new ArrayList<DGate>();
        netlistoutp = NetSynth.parseEspressoFileToABC(filestring);
        NetSynth.printNetlist(netlistoutp);
    }
      
    public static void test2notstonor()
    {
        List<DGate> netlistn = new ArrayList<DGate>();
        DWire inp1 = new DWire("a",DWireType.input);
        DWire inp2 = new DWire("b",DWireType.input);
        
        DWire wire1 = new DWire("w1",DWireType.connector);
        DWire wire2 = new DWire("w2",DWireType.connector);
        DWire out1 = new DWire("out1",DWireType.output);
        DWire out2 = new DWire("out2",DWireType.output);
        DWire w3 = new DWire("w3",DWireType.connector);
        DWire out3 = new DWire("out3",DWireType.output);
        
        DGate not1 = new DGate();
        DGate not2 = new DGate();
        not1.gtype = DGateType.NOT;
        not1.input.add(inp1);
        not1.output = wire1;
        
        not2.gtype = DGateType.NOT;
        not2.input.add(inp2);
        not2.output = wire2;
        
        
        DGate nor1 = new DGate();
        DGate nor2 = new DGate();
        DGate nor3 = new DGate();
        
        nor1.gtype = DGateType.NOR;
        nor1.input.add(inp1);
        nor1.input.add(wire2);
        nor1.output = out1;
        
        nor2.gtype = DGateType.NOR;
        nor2.input.add(inp2);
        nor2.input.add(wire1);
        nor2.output = out2;
        
        nor3.gtype = DGateType.NOR;
        nor3.input.add(inp1);
        nor3.input.add(inp2);
        nor3.output = w3;
        
        DGate not3 = new DGate();
        not3.gtype = DGateType.NOT;
        not3.input.add(w3);
        not3.output = out3;
        
        
        
        netlistn.add(not1);
        netlistn.add(not2); 
        netlistn.add(nor1);
        
        netlistn.add(nor2);
        netlistn.add(nor3);
        netlistn.add(not3);
        
        
        NetSynth.convert2NOTsToNOR(netlistn);
        
    }
    
    public static void testNORConversion(DGateType gtype)
    {
        DGate inputgate = new DGate();
        inputgate.gtype = gtype;
        for(int i=0;i<2;i++)
        {
            String wirename = "InpWire" + Global.wirecount++;
            DWire inpW = new DWire(wirename,DWireType.input);
            inputgate.input.add(inpW);
        }
        inputgate.output = new DWire("OutW",DWireType.output);
        
        System.out.println("----------------------------------\n Input Gate :");
        System.out.println(NetSynth.printGate(inputgate));
        
        System.out.println("-----------------------------------\n Converted to NOR Gate");
        List<DGate> convertednetlist = new ArrayList<DGate>();
        convertednetlist = NetlistConversionFunctions.GatetoNORNOT(inputgate);
        for(int i=0;i<convertednetlist.size();i++)
        {    
            System.out.println(NetSynth.printGate(convertednetlist.get(i)));
        }
        
    }
    
    public static void testprintNetsynth()
    {
        List<DGate> samplenetlist = new ArrayList<DGate>();
        //samplenetlist = NetSynth.parseStructuralVtoNORNOT("");
        NetSynth.printNetlist(samplenetlist);
        BooleanSimulator.printTruthTable(samplenetlist);
    }
    
    public static void testGetTT()
    {
        List<DGate> samplenetlist = new ArrayList<DGate>();
        //samplenetlist = NetSynth.parseStructuralVtoNORNOT("");
        List<String> ttvals = new ArrayList<String>();
        
        ttvals = BooleanSimulator.getTruthTable(samplenetlist);
        
        System.out.println("Truth Table Values :");
        for(String xtt:ttvals)
        {
            System.out.println(xtt);
        }
    }
    
    public static void testReducedFanin(DGateType gtype, int inputno)
    {
        DGate inputgate = new DGate();
        inputgate.gtype = gtype;
        for(int i=0;i<inputno;i++)
        {
            String wirename = "InpWire" + Global.wirecount++;
            DWire inpW = new DWire(wirename,DWireType.input);
            inputgate.input.add(inpW);
        }
        inputgate.output = new DWire("OutW",DWireType.output);
        
        System.out.println("----------------------------------\n Input Gate :");
        System.out.println(NetSynth.printGate(inputgate));
        
        System.out.println("-----------------------------------\n Reduced Fanin Gates");
        List<DGate> convertednetlist = new ArrayList<DGate>();
        convertednetlist = NetlistConversionFunctions.ConvertToFanin2(inputgate);
        for(int i=0;i<convertednetlist.size();i++)
        {    
            System.out.println(NetSynth.printGate(convertednetlist.get(i)));
        }
    }
    
    
    
    
}
