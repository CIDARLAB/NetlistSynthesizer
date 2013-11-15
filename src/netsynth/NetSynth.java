/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package netsynth;

import CelloGraph.DAGEdge;
import CelloGraph.DAGVertex;
import CelloGraph.DAGVertex.VertexType;
import CelloGraph.DAGraph;
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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import netsynth.DGate.GateType;
import netsynth.DWire.WireType;
import precomputation.PreCompute;


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
    
    
    
    public static void main(String[] args) {
        // TODO code application logic here
        Global.wirecount = 0;
        Global.espinp =0;
        Global.espout =0;
        POSmode = false;
        functionOutp = false;
        one = new DWire("_one",WireType.Source);
        zero = new DWire("_zero",WireType.GND);
        Filepath = NetSynth.class.getClassLoader().getResource(".").getPath();
        
        DAGraph x = precompute(2);
        
        //testnetlistmodule();
        //testEspresso();
        
    }
    
    public static DAGraph precompute(int x)
    {
       DAGraph outdag = new DAGraph();
        List<List<DGate>> precomp;
        precomp = PreCompute.parseNetlistFile();
        outdag = CreateDAGraph(precomp.get(x));
        return outdag;
        
    }
    
    public static void testEspresso()
    {
        
        List<String> espressoOut = new ArrayList<String>();
        espressoOut = runEspresso();
        
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
            System.out.println("\n\n DAG Graph: \n");
          
        }
        
    }
   
    public static List<String> runEspresso() {
    
        
        List<String> espressoOutput = new ArrayList<String>();
        String x = System.getProperty("os.name");
        StringBuilder commandBuilder = null;
        //if("Linux".equals(x))
        //{
            //commandBuilder = new StringBuilder("./src/resources/espresso.linux -epos src/resources/test.txt");
        //System.out.println(Filepath);
        if(Filepath.contains("build/classes/"))
            Filepath = Filepath.substring(0,Filepath.lastIndexOf("build/classes/")); 
        else if(Filepath.contains("src"))
            Filepath = Filepath.substring(0,Filepath.lastIndexOf("src/"));
        
        //System.out.println(Filepath);
         
            commandBuilder = new StringBuilder(Filepath+"src/resources/espresso.linux -epos "+ Filepath+"src/resources/test.txt");
       
            //}
        
        //System.out.println(commandBuilder);
        String command = commandBuilder.toString();
        //System.out.println("So this is what is happening: "+command);
        
        Runtime runtime = Runtime.getRuntime();
        Process proc = null;
        try {
            proc = runtime.exec(command);
        } catch (IOException ex) {
            Logger.getLogger(NetSynth.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            String filestring = "";
            
            
            filestring += Filepath+ "src/resources/write";
            filestring += Global.espout;
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
            //fbool.deleteOnExit();
        } catch (IOException ex) {
            Logger.getLogger(NetSynth.class.getName()).log(Level.SEVERE, null, ex);
        }
         
        return espressoOutput;
    }
    
    
    public static void testnetlistmodule()
    {
        DGate and = new DGate();
        DWire w1 = new DWire("A",WireType.input);
        DWire w2 = new DWire("B",WireType.input);
        DWire outp = new DWire("outP",WireType.output);
        List<DWire> inputWires = new ArrayList<DWire>();
        inputWires.add(w1);
        inputWires.add(w2);
        
        GateType testgateType;
        testgateType = GateType.NOR2;
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
            wireInputs.add(new DWire(splitInp,WireType.input));
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
            wireOutputs.add(new DWire(splitInp,WireType.output));
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
            sopexp.add(new DGate(GateType.BUF,inp01,wireOutputs.get(0)));
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
            sopexp.add(new DGate(GateType.BUF,inp01,wireOutputs.get(0)));
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
                    prodGates = AndORGates(minTemp,GateType.OR2);         
                }
                else
                {
                    prodGates = AndORGates(minTemp,GateType.AND2);         
                }
                orWires.add(prodGates.get(prodGates.size()-1).output);
                sopexp.addAll(prodGates);
            }
        }
        
        prodGates = new ArrayList<DGate>();
        if(POSmode)
        {
            prodGates = AndORGates(orWires,GateType.AND2);
        }
        else
        {
            prodGates = AndORGates(orWires,GateType.OR2);
        }
        sopexp.addAll(prodGates);
        if(sopexp.isEmpty())
        {
            DGate bufgate = new DGate(GateType.BUF,orWires,wireOutputs.get(0));
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
            wireInputs.add(new DWire(splitInp,WireType.input));
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
            wireOutputs.add(new DWire(splitInp,WireType.output));
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
            sopexp.add(new DGate(GateType.BUF,inp01,wireOutputs.get(0)));
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
            sopexp.add(new DGate(GateType.BUF,inp01,wireOutputs.get(0)));
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
                    prodGates = AndORGates(minTemp,GateType.NOR2);         
                }
                else
                {
                    prodGates = AndORGates(minTemp,GateType.NAND2);         
                }
                orWires.add(prodGates.get(prodGates.size()-1).output);
                sopexp.addAll(prodGates);
            }
        }
        boolean no2ndstageGate = false;
        prodGates = new ArrayList<DGate>();
        if(POSmode)
        {
            prodGates = AndORGates(orWires,GateType.NOR2);
            
            if(prodGates.isEmpty())
            {
                no2ndstageGate = true;
            }
                
        }
        else
        {
            prodGates = AndORGates(orWires,GateType.NAND2);
        }
        sopexp.addAll(prodGates);
        if(sopexp.isEmpty())
        {
            DGate bufgate = new DGate(GateType.BUF,orWires,wireOutputs.get(0));
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
                DGate notfinal = new DGate(GateType.NOT, notfinalinp ,aout);
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
            notInp.add(new DGate(GateType.NOT,inpNot,aout));
        }
        return notInp;
    }
    
    public static List<DGate> NORNANDGates(List<DWire> inpWires, GateType gtype)
    {
        List<DGate> minterm = new ArrayList<DGate>();
        
        
        return minterm;
    }
    
    public static List<DGate> AndORGates(List<DWire> inpWires,GateType gOrAnd)
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
                    tempNot.gtype = GateType.NOT;
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
                if((xi.wtype == WireType.input) && (!(inplist.contains(xi))))
                {
                    inplist.add(xi);
                }
            }
            
            if(netg.output.wtype == WireType.output)
            {
                //System.out.println(IndX);
                DAGVertex out =null;
                DAGVertex lvert = null;
                if(netg.gtype == GateType.NOT)
                {
                    outpIndx = IndX;
                    out = new DAGVertex(IndX++, VertexType.OUTPUT_OR.toString());                 
                    lvert = new DAGVertex(IndX++,VertexType.NOT.toString());
                    vertexhash.put(netg, lvert);
                }
                else if(netg.gtype == GateType.NOR2)
                {
                    outpIndx = IndX;
                    out = new DAGVertex(IndX++, VertexType.OUTPUT.toString()); 
                    lvert = new DAGVertex(IndX++,VertexType.NOR.toString());
                    vertexhash.put(netg, lvert);
                }
                out.Name = netg.output.name;
                lvert.outW = netg.output;
                Vertices.add(out);
                Vertices.add(lvert);
            }
            else
            {
                DAGVertex vert=null;
                if(netg.gtype == GateType.NOT)
                {
                    vert = new DAGVertex(IndX++,VertexType.NOT.toString());
                }
                else if(netg.gtype == GateType.NOR2)
                {
                    vert = new DAGVertex(IndX++,VertexType.NOR.toString());
                }
                vert.outW = netg.output;
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
            //List<Wire> inpl = new ArrayList<Wire>();
            //inpl.add(inpx);
            //Gate Inp = new Gate(GateType.BUF,inpl,inpx);
            //vertexhash.put(Inp, vert);
        }
        int eind=0;
        for(int i=netlist.size()-1;i>=0;i--)
        {
            DGate netg = netlist.get(i);
           
            
            //System.out.println(netg.gtype.toString());
            
            if(netg.output.wtype == WireType.output)
            {
                DAGEdge out = new DAGEdge();
                out.From = Vertices.get(outpIndx);
                out.To = vertexhash.get(netg);
                //out.Index = out.From.Index;
                out.Index = eind++;
                out.Next = null;
                Edges.add(out);
            }
            DAGEdge temp = null;
            
            for(DWire inps:netg.input)
            {
               DAGVertex gTo=null;
               
               for(DAGVertex xvert:Vertices)
               {
                   if(xvert.outW.equals(inps))
                   {
                       gTo = xvert;
                       break;
                   }
               }
               DAGVertex gFrom = null;
               gFrom = vertexhash.get(netg);
               if(gFrom == null)
                   System.out.println(netg.gtype);
               DAGEdge newEdge = new DAGEdge(eind++,gFrom,gTo,temp);
             
               temp = newEdge;
               Edges.add(newEdge);
               
            }
        }
        
        for(DAGEdge edg:Edges)
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