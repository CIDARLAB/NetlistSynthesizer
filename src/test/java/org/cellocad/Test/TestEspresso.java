/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.cellocad.Test;

import org.cellocad.BU.CelloGraph.DAGW;
import org.cellocad.BU.ParseVerilog.CircuitDetails;
import org.cellocad.BU.ParseVerilog.Convert;
import org.cellocad.BU.ParseVerilog.Espresso;
import org.cellocad.BU.ParseVerilog.parseVerilogFile;
import org.cellocad.BU.netsynth.DGate;
import org.cellocad.BU.netsynth.Global;
import org.cellocad.BU.netsynth.NetSynth;
import static org.cellocad.BU.netsynth.NetSynth.CreateMultDAGW;
import static org.cellocad.BU.netsynth.NetSynth.Filepath;
import static org.cellocad.BU.netsynth.NetSynth.caseCirc;
import static org.cellocad.BU.netsynth.NetSynth.computeDAGW;
import static org.cellocad.BU.netsynth.NetSynth.convertPOStoNORNOT;
import static org.cellocad.BU.netsynth.NetSynth.functionOutp;
import static org.cellocad.BU.netsynth.NetSynth.optimizeNetlist;
import static org.cellocad.BU.netsynth.NetSynth.parseEspressoOutput;
import static org.cellocad.BU.netsynth.NetSynth.parseEspressoToNORNAND;
import static org.cellocad.BU.netsynth.NetSynth.printGate;
import static org.cellocad.BU.netsynth.NetSynth.runEspresso;
import org.cellocad.MIT.dnacompiler.Gate;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.cellocad.MIT.dnacompiler.Gate.GateType;

/**
 *
 * @author prashantvaidyanathan
 */
public class TestEspresso {
    
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
                if (("NOR".equals(circuitDAGinv.Gates.get(1).Type) || "NOT".equals(circuitDAGinv.Gates.get(1).Type)) && (!circuitDAGinv.Gates.get(0).Type.equals(Gate.GateType.OUTPUT_OR.toString()))) 
                {
                    
                    if (circuitDAG.Gates.size() > (circuitDAGinv.Gates.size() - 1)) 
                    {
                        circuitDAG = circuitDAGinv;
                        gatessize = circuitDAGinv.Gates.size();
                        if("NOR".equals(circuitDAG.Gates.get(1).Type))
                        {
                            circuitDAG.Gates.get(1).Type = Gate.GateType.OUTPUT_OR;
                            circuitDAG.Gates.get(1).Name = circuitDAG.Gates.get(0).Name;
                            
                            circuitDAG.Gates.remove(0);
                        }
                        else if("NOT".equals(circuitDAG.Gates.get(1).Type))
                        {
                            circuitDAG.Gates.get(1).Type = Gate.GateType.OUTPUT;
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
                        uainp.Type = Gate.GateType.INPUT;
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
           
            if(Filepath.contains("prash"))
            {
                filestring += Filepath+ "src/org/cellocad/BU/resources/espresso";
            }
            else
            {
                filestring += Filepath+ "org/cellocad/BU/resources/espresso";
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
                
                espoutput = optimizeNetlist(espoutput,true,true,false);
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
                
                espoutputinv = optimizeNetlist(espoutputinv,true,true,false);
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
                    if (("NOR".equals(circuitDAGinv.Gates.get(1).Type) || "NOT".equals(circuitDAGinv.Gates.get(1).Type)) && (!circuitDAGinv.Gates.get(0).Type.equals(Gate.GateType.OUTPUT_OR.toString()))) 
                    {
                        if (circuitDAG.Gates.size() > (circuitDAGinv.Gates.size() - 1)) {
                            //System.out.println("Special Case");
                            circuitDAG = circuitDAGinv;
                            gatessize = circuitDAGinv.Gates.size();
                            if (circuitDAG.Gates.get(1).Type.equals(GateType.NOR)) {
                                circuitDAG.Gates.get(1).Type = Gate.GateType.OUTPUT_OR;
                                circuitDAG.Gates.get(1).Name = circuitDAG.Gates.get(0).Name;
                                circuitDAG.Gates.remove(0);
                                circuitDAG.Wires.remove(0);
                            } else if (circuitDAG.Gates.get(1).Type.equals(GateType.NOT) ) {
                                circuitDAG.Gates.get(1).Type = Gate.GateType.OUTPUT;
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
                    if(xgate.Type.equals(Gate.GateType.INPUT.toString()))
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
                            uainp.Type = Gate.GateType.INPUT;
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
    
    
    
}
