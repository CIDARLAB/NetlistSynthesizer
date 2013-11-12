/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package netsynth;

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
import netsynth.DagGraph.DAGType;
import netsynth.Gate.GateType;
import netsynth.Wire.WireType;
import precomputation.Input;


/**
 *
 * @author prashantvaidyanathan
 */
public class NetSynth {

    /**
     * @param args the command line arguments
     */
    public static Wire one;
    public static boolean POSmode;
    public static Wire zero;
    public static boolean functionOutp;
    public static String Filepath;
    
    
    
    public static void main(String[] args) {
        // TODO code application logic here
        Global.wirecount = 0;
        Global.espinp =0;
        Global.espout =0;
        POSmode = false;
        functionOutp = false;
        one = new Wire("_one",WireType.Source);
        zero = new Wire("_zero",WireType.GND);
        Filepath = NetSynth.class.getClassLoader().getResource(".").getPath();
        
        precompute();
        
        //testnetlistmodule();
        //testEspresso();
        
    }
    
    public static void precompute()
    {
       
        List<List<Gate>> precomp;
        precomp = Input.parseNetlistFile();
        
    }
    
    public static void testEspresso()
    {
        
        List<String> espressoOut = new ArrayList<String>();
        espressoOut = runEspresso();
        
        List<Gate> SOPgates = new ArrayList<Gate>();
        List<Gate> NORgates = new ArrayList<Gate>();
        
        SOPgates = parseEspressoOutput(espressoOut);
        
        System.out.println("POS format : ");
        
        if(functionOutp)
        {
                String gateString = netlist(SOPgates.get(0));
                System.out.println(gateString);
            
        }
        else
        {
            for(Gate g:SOPgates)
            {
               
                String gateString = netlist(g);
                System.out.println(gateString);
            }
        }
        
        NORgates = parseEspressoToNORNAND(espressoOut);
        List<DagGraph> dglist = new ArrayList<DagGraph>();
        dglist = CreateDAG(NORgates);
        System.out.println("\nUniversal Gates : ");
        if(functionOutp)
        {
                String gateString = netlist(NORgates.get(0));
                System.out.println(gateString);
            
        }
        else
        {
            for(Gate g:NORgates)
            {  
                String gateString = netlist(g);
                System.out.println(gateString);
            }
            System.out.println("\n\n DAG Graph: \n");
            for(DagGraph dg:dglist)
            {
                String dagstring = printDAG(dg);
                System.out.println(dagstring);
            }
        }
        
    }
    
    public static String printDAG(DagGraph dg)
    {
        String outp="";
        outp += "Index : ";
        outp += (dg.Index + "\n");
        outp += ("Vertex Name : " + dg.V.name + "\n");
        outp += ("Vertex Type : " + dg.V.vertexD + "\n");
        if(!dg.C.isEmpty())
        {
        for(DagGraph.Child ech: dg.C)
        {
            outp+= "Child Name : " + ech.name + "\n" ;
            outp+= "Child Type : " + ech.childD + "\n";
            
        }
        }
        outp+= "\n";
        return outp;
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
        Gate and = new Gate();
        Wire w1 = new Wire("A",WireType.input);
        Wire w2 = new Wire("B",WireType.input);
        Wire outp = new Wire("outP",WireType.output);
        List<Wire> inputWires = new ArrayList<Wire>();
        inputWires.add(w1);
        inputWires.add(w2);
        
        GateType testgateType;
        testgateType = GateType.NOR2;
        Gate gtest = new Gate(testgateType,inputWires,outp);
        
        List<Gate> test = new ArrayList<Gate>();
        test = NetlistConversionFunctions.GatetoNORNOT(gtest);

        for(Gate gout:test)
        {
            
            String netbuilder = "";
            netbuilder = netlist(gout);
            System.out.println(netbuilder);
        
        }   
    }
    
    
    public static List<Gate> parseEspressoOutput(List<String> espinp)
    {
        List<Gate> sopexp = new ArrayList<Gate>();
        List<Wire> wireInputs = new ArrayList<Wire>();
        List<Wire> wireOutputs = new ArrayList<Wire>();
        List<Wire> invWires = new ArrayList<Wire>();
        List<Gate> inpInv = new ArrayList<Gate>();
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
            wireInputs.add(new Wire(splitInp,WireType.input));
        }
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Create Not gates and NOT wires">
        inpInv = notGates(wireInputs);
        for(Gate gnots:inpInv)
        {
            invWires.add(gnots.output);
        }
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Get Output Names">
        for(String splitInp:outNames.split(" "))
        {
            if(splitInp.equals(one.name) || splitInp.equals(zero.name))
                splitInp += "O";
            wireOutputs.add(new Wire(splitInp,WireType.output));
        }
        // </editor-fold>        
        
        // <editor-fold defaultstate="collapsed" desc="Minterms = 0 and Output = 1 or 0">
        if(numberOfMinterms == 0)
        {
            functionOutp = true;
            List<Wire> inp01 = new ArrayList<Wire>();
            
            if(POSmode)
            {
                inp01.add(one);
            }
            else
            {
                inp01.add(zero);
            }
            sopexp.add(new Gate(GateType.BUF,inp01,wireOutputs.get(0)));
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
            List<Wire> inp01 = new ArrayList<Wire>();
            
            if(POSmode)
            {
                inp01.add(zero);
            }
            else
            {
                inp01.add(one);
            }
            sopexp.add(new Gate(GateType.BUF,inp01,wireOutputs.get(0)));
            return sopexp;    
            }
                
        }
        // </editor-fold>
        
        
        List<Wire> minTemp = new ArrayList<Wire>();
        List<Wire> orWires = new ArrayList<Wire>();
        List<Gate> prodGates;
        
        for(int i=expInd;i<(expInd+numberOfMinterms);i++)
        {
            
            String minT = espinp.get(i).substring(0, (wireInputs.size()));
            prodGates = new ArrayList<Gate>();
            minTemp = new ArrayList<Wire>();
            
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
        
        prodGates = new ArrayList<Gate>();
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
            Gate bufgate = new Gate(GateType.BUF,orWires,wireOutputs.get(0));
            sopexp.add(bufgate);
        }
        else
            sopexp.get(sopexp.size()-1).output = wireOutputs.get(0);
        return sopexp;
    }
    
    
    
    public static List<Gate> parseEspressoToNORNAND(List<String> espinp)
    {
      List<Gate> sopexp = new ArrayList<Gate>();
        List<Wire> wireInputs = new ArrayList<Wire>();
        List<Wire> wireOutputs = new ArrayList<Wire>();
        List<Wire> invWires = new ArrayList<Wire>();
        List<Gate> inpInv = new ArrayList<Gate>();
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
            wireInputs.add(new Wire(splitInp,WireType.input));
        }
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Create Not gates and NOT wires">
        inpInv = notGates(wireInputs);
        
        for(Gate gnots:inpInv)
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
            wireOutputs.add(new Wire(splitInp,WireType.output));
        }
        // </editor-fold>        
        
        // <editor-fold defaultstate="collapsed" desc="Minterms = 0 and Output = 1 or 0">
        if(numberOfMinterms == 0)
        {
            functionOutp = true;
            List<Wire> inp01 = new ArrayList<Wire>();
            
            if(POSmode)
            {
                inp01.add(one);
            }
            else
            {
                inp01.add(zero);
            }
            sopexp.add(new Gate(GateType.BUF,inp01,wireOutputs.get(0)));
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
            List<Wire> inp01 = new ArrayList<Wire>();
            
            if(POSmode)
            {
                inp01.add(zero);
            }
            else
            {
                inp01.add(one);
            }
            sopexp.add(new Gate(GateType.BUF,inp01,wireOutputs.get(0)));
            return sopexp;    
            }
                
        }
        // </editor-fold>
        
        
        List<Wire> minTemp = new ArrayList<Wire>();
        List<Wire> orWires = new ArrayList<Wire>();
        List<Gate> prodGates;
        for(int i=expInd;i<(expInd+numberOfMinterms);i++)
        {
            
            String minT = espinp.get(i).substring(0, (wireInputs.size()));
            prodGates = new ArrayList<Gate>();
            minTemp = new ArrayList<Wire>();
            
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
                            Wire tempmin = inpInv.get(xInd).output;
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
                            Wire tempmin = wireInputs.get(xInd);
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
        prodGates = new ArrayList<Gate>();
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
            Gate bufgate = new Gate(GateType.BUF,orWires,wireOutputs.get(0));
            sopexp.add(bufgate);
        }
        else
        {
            if(no2ndstageGate)
            {
                if(!((numberOfMinterms == 1) && (minTemp.size() == 1)))
                {
                String Wirename = "Wire" + Global.wirecount++;
                Wire aout = new Wire(Wirename);
                List<Wire> notfinalinp = new ArrayList<Wire>();
                notfinalinp.add(sopexp.get(sopexp.size()-1).output);
                Gate notfinal = new Gate(GateType.NOT, notfinalinp ,aout);
                sopexp.add(notfinal);
                }
            }
            sopexp.get(sopexp.size()-1).output = wireOutputs.get(0);
            
        }
        return sopexp;
    }
    
    
    
    
    public static List<Gate> notGates(List<Wire> andWires)
    {
        List<Gate> notInp = new ArrayList<Gate>();
        for(Wire xWire:andWires)
        {
            String Wirename = "Wire" + Global.wirecount++;
            Wire aout = new Wire(Wirename);
            List<Wire> inpNot = new ArrayList<Wire>();
            inpNot.add(xWire);
            notInp.add(new Gate(GateType.NOT,inpNot,aout));
        }
        return notInp;
    }
    
    public static List<Gate> NORNANDGates(List<Wire> inpWires, GateType gtype)
    {
        List<Gate> minterm = new ArrayList<Gate>();
        
        
        return minterm;
    }
    
    public static List<Gate> AndORGates(List<Wire> inpWires,GateType gOrAnd)
    {
        if (inpWires.isEmpty())
        {    return null;}
        List<Gate> minterm = new ArrayList<Gate>();
        
       
        List<Wire> nextLevelWires = new ArrayList<Wire>();
        List<Wire> temp = new ArrayList<Wire>();
        
        int wireCount,indx;
        nextLevelWires.addAll(inpWires);
        wireCount = inpWires.size();
        
        while(wireCount > 1)
        {
            temp = new ArrayList<Wire>();
            temp.addAll(nextLevelWires);
            nextLevelWires = new ArrayList<Wire>();
            indx = 0;
            while((indx+2)<= (wireCount))
            {
                
                List<Wire> ainp = new ArrayList<Wire>();
                ainp.add(temp.get(indx));
                ainp.add(temp.get(indx+1));
                String Wirename = "Wire" + Global.wirecount++;
                
                Wire aout = new Wire(Wirename);
                nextLevelWires.add(aout);
                Gate andG = new Gate(gOrAnd,ainp,aout);
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
                List<Wire> ainp = new ArrayList<Wire>();
                ainp.add(nextLevelWires.get(0));
                ainp.add(nextLevelWires.get(1));
                String Wirename = "Wire" + Global.wirecount++;
                Wire aout = new Wire(Wirename);
                Gate andG = new Gate(gOrAnd,ainp,aout);
                minterm.add(andG);
                break;
            }
        }
        return minterm;
    }
  
    
   
    public static List<DagGraph> CreateDAG(List<Gate> netlist)
    {
        List<DagGraph> finalDag = new ArrayList<DagGraph>();
        int Dindx = 0;
        HashMap<String,Wire> Inputs = new HashMap<String,Wire>();
        for(int i=(netlist.size()-1);i>=0;i--)
        {
            Gate g = netlist.get(i);
            DagGraph x = new DagGraph();
            x.Index = Dindx;
            if(g.output.wtype == WireType.output)
            {
                x.V.name = g.output.name;
                DAGType child = DAGType.NOT;
                String childname;
                if(g.gtype == GateType.NOT)
                {
                    x.V.vertexD = DAGType.OUTPUT;
                }
                else if(g.gtype == GateType.NOR2)
                {
                    x.V.vertexD = DAGType.OUTPUT_OR;
                }
                child = calcDagType(g.gtype);
                childname = child.toString();
                DagGraph.Child e = new DagGraph.Child();
                e.childD = child;
                e.name = childname;
                x.C.add(e);
                finalDag.add(x);
                Dindx++;
                x = new DagGraph();
                x.Index = Dindx;
                x.V.name = e.name;
                x.V.vertexD = e.childD;
                
                // <editor-fold defaultstate="collapsed" desc="Calculate Child of DAG Graph"> 
                for(Wire inp:g.input)
                {
                    DagGraph.Child einp = new DagGraph.Child();
                    if(inp.wtype == WireType.input)
                    {
                        Inputs.put(inp.name, inp);
                        einp.name = inp.name;
                        einp.childD = DAGType.INPUT;
                    } 
                    else
                    {
                        for(Gate gf:netlist)
                        {
                            if(gf.output == inp)
                            {
                                einp.childD = calcDagType(gf.gtype);
                                einp.name = einp.childD.toString();
                            }
                        }
                    }
                    x.C.add(einp);
                }
                // </editor-fold>
                
                finalDag.add(x);
            }
            else
            {
                x.V.vertexD = calcDagType(g.gtype);
                x.V.name = x.V.vertexD.toString();        
                // <editor-fold defaultstate="collapsed" desc="Calculate Child of DAG Graph"> 
                for(Wire inp:g.input)
                {
                    DagGraph.Child einp = new DagGraph.Child();
                    if(inp.wtype == WireType.input)
                    {
                        Inputs.put(inp.name, inp);
                        einp.name = inp.name;
                        einp.childD = DAGType.INPUT;
                    } 
                    else
                    {
                        for(Gate gf:netlist)
                        {
                            if(gf.output == inp)
                            {
                                einp.childD = calcDagType(gf.gtype);
                                einp.name = einp.childD.toString();
                            }
                        }
                    }
                    x.C.add(einp);
                }
                // </editor-fold>
                
                finalDag.add(x);
            }
            
            Dindx++;
        }
        
        Iterator it = Inputs.entrySet().iterator();
        while(it.hasNext())
        {
            Map.Entry pairs = (Map.Entry)it.next();
            Wire xinp = (Wire)pairs.getValue();
            DagGraph x = new DagGraph();
            x.V.name = xinp.name;
            x.V.vertexD = DAGType.INPUT;
            x.Index = Dindx;
            Dindx++;
            finalDag.add(x);
        }
        return finalDag;
        
    }
    
    
    public static DAGType calcDagType(GateType gt)
    {
        DAGType out = null;
        if(gt == GateType.AND2)
            out = DAGType.AND;
        else if(gt == GateType.AND3)
            out = DAGType.AND;
        else if(gt == GateType.OR2)
            out = DAGType.OR;
        else if(gt == GateType.OR3)
            out = DAGType.OR;
        else if(gt == GateType.NOT)
            out = DAGType.NOT;
        else if(gt == GateType.NOR2)
            out = DAGType.NOR;
        else if(gt == GateType.NOR3)
            out = DAGType.NOR;
        else if(gt == GateType.NAND2)
            out = DAGType.NAND;
        else if(gt == GateType.NAND3)
            out = DAGType.NAND;
        else if(gt == GateType.XNOR2)
            out = DAGType.XNOR;
        else if(gt == GateType.XOR2)
            out = DAGType.XOR;
        else if(gt == GateType.BUF)
            out = DAGType.INPUT;                
        return out;
    }
    
    
    public static String netlist(Gate g)
    {
        String netbuilder="";
        netbuilder += g.gtype;
            netbuilder += "(";
            netbuilder += g.output.name;
            
            for(Wire x:g.input)
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