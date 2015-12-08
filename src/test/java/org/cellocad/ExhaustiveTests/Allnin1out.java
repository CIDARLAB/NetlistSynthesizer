/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cellocad.ExhaustiveTests;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.cellocad.BU.ParseVerilog.Convert;
import org.cellocad.BU.booleanLogic.BooleanSimulator;
import org.cellocad.BU.netsynth.DGate;
import org.cellocad.BU.netsynth.NetSynth;
import static org.cellocad.BU.netsynth.NetSynth.getResourcesFilepath;
import org.cellocad.BU.netsynth.NetSynthSwitch;
import org.cellocad.BU.precomputation.genVerilogFile;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author prash
 */
public class Allnin1out {
    public static boolean verifyNin1out(List<NetSynthSwitch> switches,int size){
        boolean result = true;
        
        String filepath = "";
        filepath = getResourcesFilepath();
        filepath += "testVerilog.v";
        int ttSize = (int)Math.pow(2, size);
        int noOfTT = (int)Math.pow(2, ttSize);
        for(int i=0;i<noOfTT;i++){
            try {
                
                if(i==0 || i==(noOfTT-1)){
                    continue;
                }
                System.out.println("Truth Table :: " +i);
                List<String> verilogFileLines = new ArrayList<String>();
                verilogFileLines = genVerilogFile.createSingleOutpVerilogFile(size, i);
                File file = new File(filepath);
                Writer output = new BufferedWriter(new FileWriter(file));
                for(String line:verilogFileLines){  
                    String lineToWrite = line + "\n";
                    output.write(lineToWrite);
                    output.flush();
                }
                
                List<String> inputnames = new ArrayList<String>();
                for(int j=1;j<=size;j++){
                    String inpName = "inp" + j;
                    inputnames.add(inpName);
                }
                List<DGate> netlist = new ArrayList<DGate>();
                
                long startTime = System.nanoTime();
                netlist = NetSynth.getNetlist(filepath, switches);
                long endTime = System.nanoTime();
                
                long duration = (endTime - startTime)/1000000;
                System.out.println("That took ::"+duration+" milliseconds");
                String tt = BooleanSimulator.getTruthTable(netlist, inputnames).get(0); 
                
                System.out.println("Circuit Size = " + netlist.size()+"\n");
                
                
                int ttIntVal = Convert.bintoDec(tt);
                if(ttIntVal != i){
                    result = false;
                    System.out.println("Circuit "+i+ " does not work\n");
                }
                
                //file.delete();
                
                //result = true;
                //System.out.println("Verilog File Lines" + verilogFileLines);
            } catch (IOException ex) {
                Logger.getLogger(Allnin1out.class.getName()).log(Level.SEVERE, null, ex);
            } 
        }
        
        return result;
    }
    
    @Test
    public void testAllCombinations(){
       boolean result;
       List<NetSynthSwitch> switches = new ArrayList<NetSynthSwitch>();
       //switches.add(NetSynthSwitches.espresso);
       //switches.add(NetSynthSwitch.outputOR);
       //switches.add(NetSynthSwitch.abc);
       //switches.add(NetSynthSwitch.noswap);
       int size = 3;
       result = verifyNin1out(switches,size);
       String assertMessage = size + " Input 1 Output Test Failed.";
       assertTrue(assertMessage,result);
    }
    
}
