/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cellocad.Test;

import java.util.ArrayList;
import java.util.List;
import org.cellocad.BU.netsynth.DGate;
import org.cellocad.BU.netsynth.DGateType;
import org.cellocad.BU.netsynth.DWire;
import org.cellocad.BU.netsynth.DWireType;
import org.cellocad.BU.subcircuit.isomorphicFunction;
import org.cellocad.BU.subcircuit.subCircuitEnumerator;

/**
 *
 * @author prash
 */
public class TestSubcircuit {
    
    
    public static void testGetSubcircuit()
    {
        List<DGate> netlist = new ArrayList<DGate>();
        DWire in1 = new DWire();
        DWire in2 = new DWire();
        DWire in3 = new DWire();
        in1.name = "in1";
        in2.name = "in2";
        in3.name = "in3";
        
        in1.wtype = DWireType.input;
        in2.wtype = DWireType.input;
        in3.wtype = DWireType.input;
        
        DWire out = new DWire();
        out.name = "out";
        out.wtype = DWireType.output;
        
        DWire w1 = new DWire();
        DWire w2 = new DWire();
        DWire w3 = new DWire();
        
        w1.name = "w1";
        w2.name = "w2";
        w3.name = "w3";
        
        w1.wtype = DWireType.connector;
        w2.wtype = DWireType.connector;
        w3.wtype = DWireType.connector;
        
        
        DGate nor1 = new DGate();
        nor1.input.add(in1);
        nor1.input.add(in2);
        nor1.output = w1;
        nor1.gtype = DGateType.NOR;
        
        DGate not2 = new DGate();
        not2.input.add(in3);
        not2.output = w2;
        not2.gtype = DGateType.NOT;
        
        DGate nor3 = new DGate();
        nor3.input.add(w1);
        nor3.input.add(w2);
        nor3.output = w3;
        nor3.gtype = DGateType.NOR;
        
        DGate not4 = new DGate();
        not4.input.add(w3);
        not4.output = out;
        not4.gtype = DGateType.NOT;
        
        netlist.add(nor1);
        netlist.add(not2);
        netlist.add(nor3);
        netlist.add(not4);
        
        subCircuitEnumerator.getSubcircuits(netlist);
    }
    
    public static void testPermutationMatrix()
    {
        List<String> inputs = new ArrayList<String>();
        inputs.add("a");
        inputs.add("b");
        inputs.add("c");
        
        List<String> inputsJumbled = new ArrayList<String>();
        inputsJumbled.add("b");
        inputsJumbled.add("a");
        
        
        
        //isomorphicFunction.getPermutationMatrix(inputs,inputsJumbled);
    }
    
    public static void testTTisomorphism()
    {
        List<String> tt1 = new ArrayList<String>();
        List<String> tt2 = new ArrayList<String>();
        List<String> i1 = new ArrayList<String>();
        List<String> i2 = new ArrayList<String>();
        
        tt1.add("00101100");
        tt2.add("01011000");
        
        i1.add("a");
        i1.add("b");
        i1.add("c");
        
        i2.add("x");
        i2.add("y");
        i2.add("z");
        isomorphicFunction.checkPequivalence("00101100", "01011000");
        
    }
    
    public static void testStringPermutation(String tt, int permutationNumber){
        
        String result = isomorphicFunction.getPermutedTT(tt, isomorphicFunction.getPermutationMapping(3).get(permutationNumber).permutation); 
        System.out.println(tt + "::" + result);
    }
    
}
