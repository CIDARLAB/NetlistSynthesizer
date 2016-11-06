/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cellocad.tests;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import java.util.ArrayList;
import java.util.List;
import org.cellocad.BU.dom.DGate;
import org.cellocad.BU.dom.DGateType;
import org.cellocad.BU.dom.DWire;
import org.cellocad.BU.dom.DWireType;
import org.cellocad.BU.netsynth.NetSynth;
import org.cellocad.BU.netsynth.Sequential;
import org.cellocad.BU.simulators.BooleanSimulator;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author prash
 */
public class SequentialTest {
    
    public static List<DGate> createSRLatch(){
        List<DGate> netlist = new ArrayList<DGate>();
        
        DWire r = new DWire("r",DWireType.input);
        DWire s = new DWire("s",DWireType.input);
        
        DWire q = new DWire("q",DWireType.output);
        DWire qnot = new DWire("qnot",DWireType.output);
        
        DGate nor1 = new DGate();
        DGate nor2 = new DGate();
        
        nor1.input.add(r);
        nor1.input.add(qnot);
        
        nor1.output = q;
        
        nor2.input.add(s);
        nor2.input.add(q);
        
        nor2.output = qnot;
        
        nor1.gtype = DGateType.NOR;
        nor2.gtype = DGateType.NOR;
        
        netlist.add(nor2);
        netlist.add(nor1);
        
        
        return netlist;
    }
    
    
    public static List<DGate> createSRNANDLatch(){
        List<DGate> netlist = new ArrayList<DGate>();
        
        DWire r = new DWire("r",DWireType.input);
        DWire s = new DWire("s",DWireType.input);
        
        DWire q = new DWire("q",DWireType.output);
        DWire qnot = new DWire("qnot",DWireType.output);
        
        DGate nand1 = new DGate();
        DGate nand2 = new DGate();
        
        nand1.input.add(r);
        nand1.input.add(qnot);
        
        nand1.output = q;
        
        nand2.input.add(s);
        nand2.input.add(q);
        
        nand2.output = qnot;
        
        nand1.gtype = DGateType.NAND;
        nand2.gtype = DGateType.NAND;
        
        netlist.add(nand2);
        netlist.add(nand1);
        
        
        return netlist;
    }
    
    
    /*
    module A(output Q, input E, A, B);

   wire w1, w2, w3, w4, w5, w6;

   nor (w1, A, B);
   not (w2, w1);
   not (w3, E);
   nor (w4, w3, w2);
   nor (w5, w2, Q);
   nor (w6, E, Q);
   nor (Q, w4, w5, w6);
    */
    
    public static List<DGate> createEarleLatch(){
        DWire a = new DWire("a",DWireType.input);
        DWire b = new DWire("b",DWireType.input);
        DWire e = new DWire("e",DWireType.input);
        
        DWire q = new DWire("q",DWireType.output);
        
        DWire w1 = new DWire("w1",DWireType.connector);
        DWire w2 = new DWire("w2",DWireType.connector);
        DWire w3 = new DWire("w3",DWireType.connector);
        DWire w4 = new DWire("w4",DWireType.connector);
        DWire w5 = new DWire("w5",DWireType.connector);
        DWire w6 = new DWire("w6",DWireType.connector);
        
        DGate nor1 = new DGate();
        DGate not1 = new DGate();
        DGate not2 = new DGate();
        DGate nor2 = new DGate();
        DGate nor3 = new DGate();
        DGate nor4 = new DGate();
        DGate nor5 = new DGate();
        
        List<DGate> netlist = new ArrayList<DGate>();
        
        nor1.input.add(a);
        nor1.input.add(b);
        nor1.output = w1;
        nor1.gtype = DGateType.NOR;
        
        not1.input.add(w1);
        not1.output = w2;
        not1.gtype = DGateType.NOT;
        
        not2.input.add(e);
        not2.output = w3;
        not2.gtype = DGateType.NOT;
        
        nor2.input.add(w2);
        nor2.input.add(w3);
        nor2.output = w4;
        nor2.gtype = DGateType.NOR;
        
        nor3.input.add(q);
        nor3.input.add(w2);
        nor3.output = w5;
        nor3.gtype = DGateType.NOR;
        
        nor4.input.add(e);
        nor4.input.add(q);
        nor4.output = w6;
        nor4.gtype = DGateType.NOR;
        
        nor5.input.add(w4);
        nor5.input.add(w5);
        nor5.input.add(w6);
        nor5.output = q;
        nor5.gtype = DGateType.NOR;
        
        netlist.add(nor1);
        netlist.add(not1);
        netlist.add(not2);
        netlist.add(nor2);
        netlist.add(nor3);
        netlist.add(nor4);
        netlist.add(nor5);
        
        
        
        return netlist;
    }
    
    public static List<DGate> createDLatch(){
        
        DWire a = new DWire("a",DWireType.input);
        DWire b = new DWire("b",DWireType.input);
        DWire c = new DWire("c",DWireType.input);
        DWire e = new DWire("e",DWireType.input);
        
        DWire qa = new DWire("qa",DWireType.output);
        DWire qb = new DWire("qb",DWireType.output);
        
        DWire w1 = new DWire("w1",DWireType.connector);
        DWire w2 = new DWire("w2",DWireType.connector);
        DWire w3 = new DWire("w3",DWireType.connector);
        DWire w4 = new DWire("w4",DWireType.connector);
        DWire w5 = new DWire("w5",DWireType.connector);
        DWire w6 = new DWire("w6",DWireType.connector);
        
        DGate not1 = new DGate();
        DGate nor1 = new DGate();
        DGate not2 = new DGate();
        DGate nor2 = new DGate();
        DGate nor3 = new DGate();
        DGate nor4 = new DGate();
        DGate nor5 = new DGate();
        
        List<DGate> netlist = new ArrayList<DGate>();
        
        not1.input.add(a);
        not1.output =  w1;
        not1.gtype = DGateType.NOT;
        
        nor1.input.add(w1);
        nor1.input.add(b);
        nor1.input.add(c);
        nor1.output = w2;
        nor1.gtype = DGateType.NOR;
        
        not2.input.add(w2);
        not2.output = w3;
        not2.gtype = DGateType.NOT;
        
        nor2.input.add(e);
        nor2.input.add(w2);
        nor2.output = w4;
        nor2.gtype = DGateType.NOR;
        
        nor3.input.add(e);
        nor3.input.add(w3);
        nor3.output = w5;
        nor3.gtype = DGateType.NOR;
        
        nor4.input.add(w4);
        nor4.input.add(qb);
        nor4.output = qa;
        nor4.gtype = DGateType.NOR;
        
        nor5.input.add(w5);
        nor5.input.add(qa);
        nor5.output = qb;
        nor5.gtype = DGateType.NOR;
        
        
        netlist.add(not1);
        netlist.add(nor1);
        netlist.add(not2);
        netlist.add(nor2);
        netlist.add(nor3);
        netlist.add(nor4);
        netlist.add(nor5);
        
        return netlist;
    }
    
    @Test
    public void testCycles(){
        
        assertTrue(NetSynth.hasCycles(SequentialTest.createSRLatch()));
        assertTrue(NetSynth.hasCycles(SequentialTest.createEarleLatch()));
        assertTrue(NetSynth.hasCycles(SequentialTest.createDLatch()));
        
    }
 
    //@Test
    public void testTruthTable(){
        
        System.out.println("Print Bistable States");
        System.out.println("Earle Latch :: ");
        //System.out.println(Sequential.getCyclicalOutputWireNamesFromNetlist(SequentialTest.createEarleLatch()));
        NetSynth.printNetlist(SequentialTest.createEarleLatch());
        Sequential.truthtable(SequentialTest.createEarleLatch());
        
        System.out.println("\n\nD Latch :: ");
        //System.out.println(Sequential.getCyclicalOutputWireNamesFromNetlist(SequentialTest.createDLatch()));
        NetSynth.printNetlist(SequentialTest.createDLatch());
        Sequential.truthtable(SequentialTest.createDLatch());
        
        System.out.println("\n\nSR Latch :: ");
        //System.out.println(Sequential.getCyclicalOutputWireNamesFromNetlist(SequentialTest.createSRLatch()));
        NetSynth.printNetlist(SequentialTest.createSRLatch());
        Sequential.truthtable(SequentialTest.createSRLatch());
        
        System.out.println("\n\nSR NAND Latch :: ");
        //System.out.println(Sequential.getCyclicalOutputWireNamesFromNetlist(SequentialTest.createSRLatch()));
        NetSynth.printNetlist(SequentialTest.createSRNANDLatch());
        Sequential.truthtable(SequentialTest.createSRNANDLatch());
        
    }
    
    //@Test
    public void testPrintCompleteTruthTable(){
        
        System.out.println("\n\nPrint Complete Truth table\n\n===============================================================================");
        System.out.println("Earle Latch :: ");
        //System.out.println(Sequential.getCyclicalOutputWireNamesFromNetlist(SequentialTest.createEarleLatch()));
        NetSynth.printNetlist(SequentialTest.createEarleLatch());
        Sequential.printCompleteTruthtable(SequentialTest.createEarleLatch());
        
        System.out.println("\n\nD Latch :: ");
        //System.out.println(Sequential.getCyclicalOutputWireNamesFromNetlist(SequentialTest.createDLatch()));
        NetSynth.printNetlist(SequentialTest.createDLatch());
        Sequential.printCompleteTruthtable(SequentialTest.createDLatch());
        
        System.out.println("\n\nSR Latch :: ");
        //System.out.println(Sequential.getCyclicalOutputWireNamesFromNetlist(SequentialTest.createSRLatch()));
        NetSynth.printNetlist(SequentialTest.createSRLatch());
        Sequential.printCompleteTruthtable(SequentialTest.createSRLatch());
        
        System.out.println("\n\nSR NAND Latch :: ");
        //System.out.println(Sequential.getCyclicalOutputWireNamesFromNetlist(SequentialTest.createSRLatch()));
        NetSynth.printNetlist(SequentialTest.createSRNANDLatch());
        Sequential.printCompleteTruthtable(SequentialTest.createSRNANDLatch());
        
    }
    
    
    @Test
    public void testWaveform(){
        System.out.println("WAVEFORM!!");
        List<DGate> sr = SequentialTest.createSRLatch();
        
        List<DGate> d = SequentialTest.createDLatch();
        NetSynth.assignGateIndex(d);
        
        List<DGate> earle = SequentialTest.createEarleLatch();
        NetSynth.assignGateIndex(earle);
        
        Sequential.createWaveform(sr);
        Sequential.createWaveform(d);
        Sequential.createWaveform(earle);
        
        
    }
    
    //@Test
    public void testSRLatchPresence(){
        
        List<DGate> sr = SequentialTest.createSRLatch();
        NetSynth.assignGateIndex(sr);
        
        List<DGate> d = SequentialTest.createDLatch();
        NetSynth.assignGateIndex(d);
        
        List<DGate> earle = SequentialTest.createEarleLatch();
        NetSynth.assignGateIndex(earle);
        
        System.out.println("NOR");
        System.out.println("SR Latch");
        Sequential.getSRLatchNOR(Sequential.getCyclicalGates(sr));
        
        System.out.println("D Latch");
        Sequential.getSRLatchNOR(Sequential.getCyclicalGates(d));
        
        System.out.println("Earle Latch");
        Sequential.getSRLatchNOR(Sequential.getCyclicalGates(earle));
        
//        System.out.println("NAND");
//        System.out.println("SR Latch");
//        Sequential.getSRLatchNAND(Sequential.getCyclicalGates(SequentialTest.createSRLatch()));
//        
//        System.out.println("D Latch");
//        Sequential.getSRLatchNAND(Sequential.getCyclicalGates(SequentialTest.createDLatch()));
//        
//        System.out.println("Earle Latch");
//        Sequential.getSRLatchNAND(Sequential.getCyclicalGates(SequentialTest.createEarleLatch()));
//        
    
    }
    
 
}
