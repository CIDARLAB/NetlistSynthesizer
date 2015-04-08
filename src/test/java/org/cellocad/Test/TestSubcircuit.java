/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cellocad.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.cellocad.BU.netsynth.DGate;
import org.cellocad.BU.netsynth.DGateType;
import org.cellocad.BU.netsynth.DWire;
import org.cellocad.BU.netsynth.DWireType;
import org.cellocad.BU.netsynth.NetSynth;
import org.cellocad.BU.subcircuit.isomorphicFunction;
import org.cellocad.BU.subcircuit.subCircuitEnumerator;
import org.cellocad.BU.subcircuit.subCircuitSwap;

/**
 *
 * @author prash
 */
public class TestSubcircuit {
    
    public static List<DGate> getSampleNetlist1(){
        
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
        return netlist;
    }
    
    public static List<DGate> getSampleNetlist2(){
        List<DGate> netlist = new ArrayList<DGate>();
        
        DWire a = new DWire("a",DWireType.input);
        DWire b = new DWire("b",DWireType.input);
        DWire c = new DWire("c",DWireType.input);
        DWire d = new DWire("d",DWireType.input);
        
        DWire outp1 = new DWire("out1",DWireType.output);
        DWire outp2 = new DWire("out2",DWireType.output);
        DWire outp3 = new DWire("out3",DWireType.output);
        
        DWire w1 = new DWire("wire1",DWireType.connector);
        DWire w2 = new DWire("wire2",DWireType.connector);
        DWire w3 = new DWire("wire3",DWireType.connector);
        DWire w4 = new DWire("wire4",DWireType.connector);
        DWire w5 = new DWire("wire5",DWireType.connector);
        DWire w6 = new DWire("wire6",DWireType.connector);
        DWire w7 = new DWire("wire7",DWireType.connector);
        DWire w8 = new DWire("wire8",DWireType.connector);
        
        
        DGate nor1 = new DGate();
        DGate not2 = new DGate();
        DGate nor3 = new DGate();
        DGate nor4 = new DGate();
        DGate not5 = new DGate();
        DGate not6 = new DGate();
        DGate nor7 = new DGate();
        DGate not8 = new DGate();
        DGate nor9 = new DGate();
        DGate not10 = new DGate();
        DGate not11 = new DGate();
        
        nor1.gtype = DGateType.NOR;
        nor1.input.add(a);
        nor1.input.add(b);
        nor1.output = w1;
        netlist.add(nor1);
        
        not2.gtype = DGateType.NOT;
        not2.input.add(c);
        not2.output = w2;
        netlist.add(not2);
        
        nor3.gtype = DGateType.NOR;
        nor3.input.add(a);
        nor3.input.add(b);
        nor3.output = w3;
        netlist.add(nor3);
        
        nor4.gtype = DGateType.NOR;
        nor4.input.add(d);
        nor4.input.add(w1);
        nor4.output = w4;
        netlist.add(nor4);
        
        not5.gtype = DGateType.NOT;
        not5.input.add(w1);
        not5.output = w5;
        netlist.add(not5);
        
        not6.gtype = DGateType.NOT;
        not6.input.add(w2);
        not6.output = w6;
        netlist.add(not6);
        
        nor7.gtype = DGateType.NOR;
        nor7.input.add(w3);
        nor7.input.add(d);
        nor7.output = outp3;
        netlist.add(nor7);
        
        not8.gtype = DGateType.NOT;
        not8.input.add(w5);
        not8.output = w7;
        netlist.add(not8);
        
        nor9.gtype = DGateType.NOR;
        nor9.input.add(w7);
        nor9.input.add(w6);
        nor9.output = w8;
        netlist.add(nor9);
        
        not10.gtype = DGateType.NOT;
        not10.input.add(w4);
        not10.output = outp1;
        netlist.add(not10);
        
        not11.gtype = DGateType.NOT;
        not11.input.add(w8);
        not11.output = outp2;
        netlist.add(not11);
        
        return netlist;
    }
    
    
    public static List<DGate> getSampleNetlist3(){
        List<DGate> netlist = new ArrayList<DGate>();
        
        DWire in1 = new DWire("in1",DWireType.input);
        DWire in2 = new DWire("in2",DWireType.input);
        DWire in3 = new DWire("in3",DWireType.input);
        
        DWire out = new DWire("out",DWireType.output);
        
        DWire w = new DWire("w",DWireType.connector);
        
        DGate nor1 = new DGate();
        DGate nor2 = new DGate();
        
        nor1.gtype = DGateType.NOR;
        nor1.input.add(in2);
        nor1.input.add(in1);
        nor1.output = w;
        netlist.add(nor1);
        
        nor2.gtype = DGateType.NOR;
        nor2.input.add(w);
        nor2.input.add(in3);
        nor2.output = out;
        netlist.add(nor2);
        
        return netlist;
    }
    
    
    public static void testRenameWires(){
        List<DGate> netlist = getSampleNetlist1();
        
        subCircuitSwap.renameWires(netlist, false);
    }
    
    public static void testGetSubcircuit()
    {
        List<DGate> netlist = getSampleNetlist1();
        subCircuitEnumerator.getSubcircuits(netlist);
    }
    
    
    public static void testInsertSubCircuit(){
        List<DGate> netlist = new ArrayList<DGate>();
        List<DGate> subcircuit = new ArrayList<DGate>();
        
        netlist = getSampleNetlist2();
        subcircuit = getSampleNetlist3();
        Map<String,String> inputMap = new HashMap<String,String>();
        
        inputMap.put("a", "in2");
        inputMap.put("b", "in1");
        inputMap.put("c", "in3");
        
        NetSynth.assignGateIndex(netlist);
        NetSynth.assignGateIndex(subcircuit);
        
        NetSynth.printDebugStatement("Initial Netlist");
        NetSynth.printNetlist(netlist);
        
        NetSynth.printDebugStatement("Subcircuit");
        NetSynth.printNetlist(subcircuit);
        
        
        NetSynth.printDebugStatement("Post Swap");
        subCircuitSwap.insertSubcircuit(netlist, subcircuit, inputMap, 8);
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
