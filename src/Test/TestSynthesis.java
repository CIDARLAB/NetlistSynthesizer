/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Test;

import BU.ParseVerilog.parseVerilogFile;
import BU.booleanLogic.BooleanSimulator;
import BU.netsynth.DGate;
import BU.netsynth.DGate.DGateType;
import BU.netsynth.DWire;
import BU.netsynth.DWire.DWireType;
import BU.netsynth.Global;
import BU.netsynth.NetSynth;
import BU.netsynth.NetlistConversionFunctions;
import java.util.ArrayList;
import java.util.List;

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
        netlistoutp = NetSynth.parseEspressoToABC(filestring);
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
