/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cellocad.tests;

import java.util.ArrayList;
import java.util.List;
import org.cellocad.BU.dom.DGate;
import org.cellocad.BU.dom.DWire;
import org.cellocad.BU.dom.uFGate;
import org.cellocad.BU.fluigi.VerilogFluigiGrammar;
import org.cellocad.BU.fluigi.VerilogFluigiWalker;
import org.cellocad.BU.netsynth.NetSynth;
import org.cellocad.BU.netsynth.Utilities;
import org.junit.Test;

/**
 *
 * @author prash
 */
public class FluigiVerilogTest {
    
    @Test
    public void testFluigiVerilog(){
        String filepath = Utilities.getNetSynthResourcesFilepath();
        filepath += "fluigi.v";
        //List<String> lines = new ArrayList<String>();
        //lines = Utilities.getFileContentAsStringList(filepath);
        //String fileLine = "";
        //for(String line:lines){
        //    fileLine += (line + "\n");
        //}
        
        String line = "";
        line = Utilities.getFileContentAsString(filepath);
        
        //System.out.println("FILE LINE :: \n" + fileLine);
        VerilogFluigiWalker walker = VerilogFluigiGrammar.getuFWalker(line);
        
        
        System.out.println("Module Name :: " + walker.details.modulename);
        System.out.println("Inputs :: " + walker.details.inputs);
        System.out.println("Outputs :: " + walker.details.outputs);
        System.out.println("Wires :: " + walker.details.wires);
        
//        System.out.println("Netlist :: \n");
//        NetSynth.printNetlist(walker.netlist);
        
        System.out.println("uF Netlist :: \n");
        NetSynth.printuFNetlist(walker.netlist);
        
        uFGate gate0 = walker.netlist.get(0);
        System.out.println("INPUTS for Gate 0 " + gate0.input);
        for(DWire input:gate0.input){
            System.out.println("WIRE name :: " + input.name);
            System.out.println("WIRE type :: " + input.wtype);
        }
        
        System.out.println("Output Wire name for Gate 0 :: " + gate0.output.name);
        System.out.println("Output Wire type for Gate 0 :: " + gate0.output.wtype);
        
        System.out.println("Gate Symbol :: " + gate0.symbol);
        
       
    }
}
