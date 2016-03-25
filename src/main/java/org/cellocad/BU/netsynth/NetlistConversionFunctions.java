/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cellocad.BU.netsynth;

import org.cellocad.BU.dom.DWire;
import org.cellocad.BU.dom.DGate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import org.cellocad.BU.dom.DGateType;
import org.cellocad.BU.dom.DWireType;

/**
 *
 * @author prashantvaidyanathan
 */
public class NetlistConversionFunctions {

    public static List<DGate> GatetoNORNOT(DGate g, AtomicInteger wirecount) {
        List<DGate> nor_eq = new ArrayList<DGate>();

        if (g.gtype == DGateType.NOT) {
            g.calculateStage();
            nor_eq.add(g);
        } else if (g.gtype == DGateType.AND) {
            DGate not1 = new DGate();
            not1.gtype = DGateType.NOT;
            not1.input.add(g.input.get(0));
            DWire outp1 = new DWire();
            outp1.name = "0Wire" + wirecount.getAndIncrement();
            not1.output = outp1;

            DGate not2 = new DGate();
            not2.gtype = DGateType.NOT;
            not2.input.add(g.input.get(1));
            DWire outp2 = new DWire();
            outp2.name = "0Wire" + wirecount.getAndIncrement();
            not2.output = outp2;

            DGate nor1 = new DGate();
            nor1.gtype = DGateType.NOR;
            nor1.input.add(outp1);
            nor1.input.add(outp2);
            nor1.output = g.output;

            not1.calculateStage();
            not2.calculateStage();
            nor1.calculateStage();

            nor_eq.add(not1);
            nor_eq.add(not2);
            nor_eq.add(nor1);
        } else if (g.gtype == DGateType.NAND) {
            DGate not1 = new DGate();
            not1.gtype = DGateType.NOT;
            not1.input.add(g.input.get(0));
            DWire outp1 = new DWire();
            outp1.name = "0Wire" + wirecount.getAndIncrement();
            not1.output = outp1;

            DGate not2 = new DGate();
            not2.gtype = DGateType.NOT;
            not2.input.add(g.input.get(1));
            DWire outp2 = new DWire();
            outp2.name = "0Wire" + wirecount.getAndIncrement();
            not2.output = outp2;

            DGate nor1 = new DGate();
            nor1.gtype = DGateType.NOR;
            nor1.input.add(outp1);
            nor1.input.add(outp2);
            DWire outp3 = new DWire();
            outp3.name = "0Wire" + wirecount.getAndIncrement();
            nor1.output = outp3;

            DGate not3 = new DGate();
            not3.gtype = DGateType.NOT;
            not3.input.add(outp3);
            not3.output = g.output;

            not1.calculateStage();
            not2.calculateStage();
            nor1.calculateStage();
            not3.calculateStage();

            nor_eq.add(not1);
            nor_eq.add(not2);
            nor_eq.add(nor1);
            nor_eq.add(not3);

        } else if (g.gtype == DGateType.OR) {
            DGate nor1 = new DGate();
            nor1.gtype = DGateType.NOR;
            nor1.input.add(g.input.get(0));
            nor1.input.add(g.input.get(1));
            DWire outp1 = new DWire();
            outp1.name = "0Wire" + wirecount.getAndIncrement();
            nor1.output = outp1;

            DGate not1 = new DGate();
            not1.gtype = DGateType.NOT;
            not1.input.add(outp1);
            not1.output = g.output;

            nor1.calculateStage();
            not1.calculateStage();

            nor_eq.add(nor1);
            nor_eq.add(not1);

        } else if (g.gtype == DGateType.NOR) {
            g.calculateStage();
            nor_eq.add(g);
        } else if (g.gtype == DGateType.XOR) {
            DGate not1 = new DGate();
            not1.gtype = DGateType.NOT;
            not1.input.add(g.input.get(0));
            DWire outp1 = new DWire();
            outp1.name = "0Wire" + wirecount.getAndIncrement();
            not1.output = outp1;

            DGate not2 = new DGate();
            not2.gtype = DGateType.NOT;
            not2.input.add(g.input.get(1));
            DWire outp2 = new DWire();
            outp2.name = "0Wire" + wirecount.getAndIncrement();
            not2.output = outp2;

            DGate nor1 = new DGate();
            nor1.gtype = DGateType.NOR;
            nor1.input.add(g.input.get(0));
            nor1.input.add(g.input.get(1));
            DWire outp3 = new DWire();
            outp3.name = "0Wire" + wirecount.getAndIncrement();
            nor1.output = outp3;

            DGate nor2 = new DGate();
            nor2.gtype = DGateType.NOR;
            nor2.input.add(outp1);
            nor2.input.add(outp2);
            DWire outp4 = new DWire();
            outp4.name = "0Wire" + wirecount.getAndIncrement();
            nor2.output = outp4;

            DGate nor3 = new DGate();
            nor3.gtype = DGateType.NOR;
            nor3.input.add(outp3);
            nor3.input.add(outp4);
            nor3.output = g.output;

            not1.calculateStage();
            not2.calculateStage();
            nor1.calculateStage();
            nor2.calculateStage();
            nor3.calculateStage();

            nor_eq.add(not1);
            nor_eq.add(not2);
            nor_eq.add(nor1);
            nor_eq.add(nor2);
            nor_eq.add(nor3);

        } else if (g.gtype == DGateType.XNOR) {
            DGate not1 = new DGate();
            not1.gtype = DGateType.NOT;
            not1.input.add(g.input.get(0));
            DWire outp1 = new DWire();
            outp1.name = "0Wire" + wirecount.getAndIncrement();
            not1.output = outp1;

            DGate not2 = new DGate();
            not2.gtype = DGateType.NOT;
            not2.input.add(g.input.get(1));
            DWire outp2 = new DWire();
            outp2.name = "0Wire" + wirecount.getAndIncrement();
            not2.output = outp2;

            DGate nor1 = new DGate();
            nor1.gtype = DGateType.NOR;
            nor1.input.add(outp1);
            nor1.input.add(g.input.get(1));
            DWire outp3 = new DWire();
            outp3.name = "0Wire" + wirecount.getAndIncrement();
            nor1.output = outp3;

            DGate nor2 = new DGate();
            nor2.gtype = DGateType.NOR;
            nor2.input.add(g.input.get(0));
            nor2.input.add(outp2);
            DWire outp4 = new DWire();
            outp4.name = "0Wire" + wirecount.getAndIncrement();
            nor2.output = outp4;

            DGate nor3 = new DGate();
            nor3.gtype = DGateType.NOR;
            nor3.input.add(outp3);
            nor3.input.add(outp4);
            nor3.output = g.output;

            not1.calculateStage();
            not2.calculateStage();
            nor1.calculateStage();
            nor2.calculateStage();
            nor3.calculateStage();

            nor_eq.add(not1);
            nor_eq.add(not2);
            nor_eq.add(nor1);
            nor_eq.add(nor2);
            nor_eq.add(nor3);
        }
        return nor_eq;
    }

    public static List<DGate> GatetoNOR(DGate g, AtomicInteger wirecount) {
        List<DGate> nor_eq = new ArrayList<DGate>();

        if (g.gtype == DGateType.NOT) {
            DGate nor1 = new DGate();
            nor1.gtype = DGateType.NOR;

            nor1.input.add(g.input.get(0));
            nor1.input.add(g.input.get(0));

            nor1.output = g.output;
            nor1.calculateStage();
            nor_eq.add(nor1);

        } else if (g.gtype == DGateType.AND) {
            DGate nor1 = new DGate();
            nor1.gtype = DGateType.NOR;
            nor1.input.add(g.input.get(0));
            nor1.input.add(g.input.get(0));
            DWire outp1 = new DWire();
            outp1.name = "0Wire" + wirecount.getAndIncrement();
            nor1.output = outp1;

            DGate nor2 = new DGate();
            nor2.gtype = DGateType.NOR;
            nor2.input.add(g.input.get(1));
            nor2.input.add(g.input.get(1));
            DWire outp2 = new DWire();
            outp2.name = "0Wire" + wirecount.getAndIncrement();
            nor2.output = outp2;

            DGate nor3 = new DGate();
            nor3.gtype = DGateType.NOR;
            nor3.input.add(outp1);
            nor3.input.add(outp2);
            nor3.output = g.output;

            nor1.calculateStage();
            nor2.calculateStage();
            nor3.calculateStage();

            nor_eq.add(nor1);
            nor_eq.add(nor2);
            nor_eq.add(nor3);

        } else if (g.gtype == DGateType.NAND) {
            DGate nor1 = new DGate();
            nor1.gtype = DGateType.NOR;
            nor1.input.add(g.input.get(0));
            nor1.input.add(g.input.get(0));
            DWire outp1 = new DWire();
            outp1.name = "0Wire" + wirecount.getAndIncrement();
            nor1.output = outp1;

            DGate nor2 = new DGate();
            nor2.gtype = DGateType.NOR;
            nor2.input.add(g.input.get(1));
            nor2.input.add(g.input.get(1));
            DWire outp2 = new DWire();
            outp2.name = "0Wire" + wirecount.getAndIncrement();
            nor2.output = outp2;

            DGate nor3 = new DGate();
            nor3.gtype = DGateType.NOR;
            nor3.input.add(outp1);
            nor3.input.add(outp2);
            DWire outp3 = new DWire();
            outp3.name = "0Wire" + wirecount.getAndIncrement();
            nor3.output = outp3;

            DGate nor4 = new DGate();
            nor4.gtype = DGateType.NOR;
            nor4.input.add(outp3);
            nor4.input.add(outp3);
            nor4.output = g.output;

            nor1.calculateStage();
            nor2.calculateStage();
            nor3.calculateStage();
            nor4.calculateStage();

            nor_eq.add(nor1);
            nor_eq.add(nor2);
            nor_eq.add(nor3);
            nor_eq.add(nor4);

        } else if (g.gtype == DGateType.OR) {
            DGate nor1 = new DGate();
            nor1.gtype = DGateType.NOR;
            nor1.input.add(g.input.get(0));
            nor1.input.add(g.input.get(1));
            DWire outp1 = new DWire();
            outp1.name = "0Wire" + wirecount.getAndIncrement();
            nor1.output = outp1;

            DGate nor2 = new DGate();
            nor2.gtype = DGateType.NOR;
            nor2.input.add(outp1);
            nor2.input.add(outp1);
            nor2.output = g.output;

            nor1.calculateStage();
            nor2.calculateStage();

            nor_eq.add(nor1);
            nor_eq.add(nor2);

        } else if (g.gtype == DGateType.NOR) {
            g.calculateStage();
            nor_eq.add(g);
        } else if (g.gtype == DGateType.XOR) {
            DGate nor1 = new DGate();
            nor1.gtype = DGateType.NOR;
            nor1.input.add(g.input.get(0));
            nor1.input.add(g.input.get(0));
            DWire outp1 = new DWire();
            outp1.name = "0Wire" + wirecount.getAndIncrement();
            nor1.output = outp1;

            DGate nor2 = new DGate();
            nor2.gtype = DGateType.NOR;
            nor2.input.add(g.input.get(1));
            nor2.input.add(g.input.get(1));
            DWire outp2 = new DWire();
            outp2.name = "0Wire" + wirecount.getAndIncrement();
            nor2.output = outp2;

            DGate nor3 = new DGate();
            nor3.gtype = DGateType.NOR;
            nor3.input.add(g.input.get(0));
            nor3.input.add(g.input.get(1));
            DWire outp3 = new DWire();
            outp3.name = "0Wire" + wirecount.getAndIncrement();
            nor3.output = outp3;

            DGate nor4 = new DGate();
            nor4.gtype = DGateType.NOR;
            nor4.input.add(outp1);
            nor4.input.add(outp2);
            DWire outp4 = new DWire();
            outp4.name = "0Wire" + wirecount.getAndIncrement();
            nor4.output = outp4;

            DGate nor5 = new DGate();
            nor5.gtype = DGateType.NOR;
            nor5.input.add(outp3);
            nor5.input.add(outp4);
            nor5.output = g.output;

            nor1.calculateStage();
            nor2.calculateStage();
            nor3.calculateStage();
            nor4.calculateStage();
            nor5.calculateStage();

            nor_eq.add(nor1);
            nor_eq.add(nor2);
            nor_eq.add(nor3);
            nor_eq.add(nor4);
            nor_eq.add(nor5);

        } else if (g.gtype == DGateType.XNOR) {
            DGate nor1 = new DGate();
            nor1.gtype = DGateType.NOR;
            nor1.input.add(g.input.get(0));
            nor1.input.add(g.input.get(0));
            DWire outp1 = new DWire();
            outp1.name = "0Wire" + wirecount.getAndIncrement();
            nor1.output = outp1;

            DGate nor2 = new DGate();
            nor2.gtype = DGateType.NOR;
            nor2.input.add(g.input.get(1));
            nor2.input.add(g.input.get(1));
            DWire outp2 = new DWire();
            outp2.name = "0Wire" + wirecount.getAndIncrement();
            nor2.output = outp2;

            DGate nor3 = new DGate();
            nor3.gtype = DGateType.NOR;
            nor3.input.add(outp1);
            nor3.input.add(g.input.get(1));
            DWire outp3 = new DWire();
            outp3.name = "0Wire" + wirecount.getAndIncrement();
            nor3.output = outp3;

            DGate nor4 = new DGate();
            nor4.gtype = DGateType.NOR;
            nor4.input.add(g.input.get(0));
            nor4.input.add(outp2);
            DWire outp4 = new DWire();
            outp4.name = "0Wire" + wirecount.getAndIncrement();
            nor4.output = outp4;

            DGate nor5 = new DGate();
            nor5.gtype = DGateType.NOR;
            nor5.input.add(outp3);
            nor5.input.add(outp4);
            nor5.output = g.output;

            nor1.calculateStage();
            nor2.calculateStage();
            nor3.calculateStage();
            nor4.calculateStage();
            nor5.calculateStage();

            nor_eq.add(nor1);
            nor_eq.add(nor2);
            nor_eq.add(nor3);
            nor_eq.add(nor4);
            nor_eq.add(nor5);
        }

        return nor_eq;
    }

    /**
     * *************************************************************Function
     *
     * Synopsis [Returns equivalent netlist with each gate Fanin less than equal
     * to 2]
     *
     * Description []
     *
     * SideEffects []
     *
     * SeeAlso []
    **********************************************************************
     */
    public static List<DGate> ConvertToFanin2(DGate g, AtomicInteger wirecount) {
        List<DGate> fanin2 = new ArrayList<DGate>();
        if (g.input.size() < 3) {
            fanin2.add(g);
        } else {
            for (DGate xgate : reduceTofanin2(g, g.gtype,wirecount)) {
                fanin2.add(xgate);
            }
//            NetSynth.renameWires(fanin2);
        }

        return fanin2;
    }

    /**
     * *************************************************************
     * Function
     *
     * Synopsis [Produces a Netlist where Fanin of each gate is 2]
     *
     * Description [The final netlist is functionally equivalent to the input
     * gate. The input gate can have fanin greater than 2. The final netlist
     * will comprise of gates where fanin is 2 or 1(in case of NOT gates)]
     *
     * SideEffects [If the input is a nor, nand or xnor gate, all the gates in
     * the netlist will comprise of a or, and or xor gate respectively followed
     * by a not gate at the end]
     *
     * SeeAlso []
     *
     **********************************************************************
     */
    public static List<DGate> reduceTofanin2(DGate inpgate, DGateType gtype, AtomicInteger wirecount) {
        List<DGate> redfanin = new ArrayList<DGate>();
        List<DWire> remainingwires = new ArrayList<DWire>();
        DGateType ngtype = gtype;
        int invflag = 0;
        if (gtype.equals(DGateType.NAND)) {
            ngtype = DGateType.AND;
            invflag = 1;
        }
        if (gtype.equals(DGateType.NOR)) {
            ngtype = DGateType.OR;
            invflag = 1;
        }
        if (gtype.equals(DGateType.XNOR)) {
            ngtype = DGateType.XOR;
            invflag = 1;
        }

        for (DWire xwire : inpgate.input) {
            remainingwires.add(xwire);
        }
        List<DWire> newinpwires = new ArrayList<DWire>();
        while (remainingwires.size() > 2) {
            newinpwires = new ArrayList<DWire>();
            for (DWire xwire : remainingwires) {
                newinpwires.add(xwire);
            }
            remainingwires = new ArrayList<DWire>();
            for (int i = 0; i < newinpwires.size() - 1; i += 2) {
                DGate ngate = new DGate();
                ngate.input.add(newinpwires.get(i));
                ngate.input.add(newinpwires.get(i + 1));
                String outWname = "0Wire" + wirecount.getAndIncrement();
                DWire outW = new DWire(outWname, DWireType.connector);
                ngate.output = outW;
                remainingwires.add(outW);
                ngate.gtype = ngtype;
                redfanin.add(ngate);
            }
            if (newinpwires.size() % 2 != 0) {
                remainingwires.add(newinpwires.get(newinpwires.size() - 1));
            }
        }

        if (invflag == 1) {
            DGate ngate = new DGate();
            ngate.input.add(remainingwires.get(0));
            ngate.input.add(remainingwires.get(1));
            String outWname = "0Wire" + wirecount.getAndIncrement();
            DWire outW = new DWire(outWname, DWireType.connector);
            ngate.output = outW;
            remainingwires.add(outW);
            ngate.gtype = ngtype;
            redfanin.add(ngate);
            DGate noutgate = new DGate();
            noutgate.gtype = DGateType.NOT;
            noutgate.input.add(outW);
            noutgate.output = inpgate.output;
            redfanin.add(noutgate);
        } else {
            DGate ngate = new DGate();
            ngate.input.add(remainingwires.get(0));
            ngate.input.add(remainingwires.get(1));
            ngate.gtype = ngtype;
            ngate.output = inpgate.output;
            redfanin.add(ngate);
        }
        return redfanin;
    }

}
