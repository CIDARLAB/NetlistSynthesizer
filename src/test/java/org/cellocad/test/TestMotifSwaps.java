package org.cellocad.test;


import org.cellocad.BU.dom.DAGW;
import org.cellocad.BU.netsynth.NetSynth;
import org.cellocad.BU.netsynth.NetSynthSwitch;
import org.cellocad.MIT.dnacompiler.Gate;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

public class TestMotifSwaps {

    String verilog_0xFE = "module A(output out1,  input in1, in2, in3);\n" +
            "  always@(in1,in2,in3)\n" +
            "    begin\n" +
            "      case({in1,in2,in3})\n" +
            "        3'b000: {out1} = 1'b1;\n" +
            "        3'b001: {out1} = 1'b1;\n" +
            "        3'b010: {out1} = 1'b1;\n" +
            "        3'b011: {out1} = 1'b1;\n" +
            "        3'b100: {out1} = 1'b1;\n" +
            "        3'b101: {out1} = 1'b1;\n" +
            "        3'b110: {out1} = 1'b1;\n" +
            "        3'b111: {out1} = 1'b0;\n" +
            "      endcase\n" +
            "    end\n" +
            "endmodule\n";


    private HashMap<String, Integer> getGateTypeCounts(DAGW GW) {
        int nor = 0;
        int not = 0;
        int output_or = 0;
        int or = 0;
        int and = 0;


        for(Gate g: GW.Gates) {
            if(g.Type.equals(Gate.GateType.NOR)) {
                nor++;
            }
            if(g.Type.equals(Gate.GateType.NOT)) {
                not++;
            }
            if(g.Type.equals(Gate.GateType.OUTPUT_OR)) {
                output_or++;
            }
            if(g.Type.equals(Gate.GateType.OR)) {
                or++;
            }
            if(g.Type.equals(Gate.GateType.AND)) {
                and++;
            }
        }

        HashMap<String, Integer> gateTypeCounts = new HashMap();
        gateTypeCounts.put("NOR", nor);
        gateTypeCounts.put("NOT", not);
        gateTypeCounts.put("OUTPUT_OR", output_or);
        gateTypeCounts.put("OR", or);
        gateTypeCounts.put("AND", and);

        return gateTypeCounts;
    }

    @Test
    public void test_OUTPUT_OR_0xFE() throws JSONException {

        String verilog = verilog_0xFE;

        ArrayList<String> inputs = new ArrayList();
        inputs.add("a");
        inputs.add("b");
        ArrayList<String> outputs = new ArrayList();
        outputs.add("y");
        ArrayList<String> netlist = new ArrayList();
        netlist.add("OUTPUT_OR(y, a, b)");

        JSONObject motif = new JSONObject();
        motif.put("collection", "motif_library");
        motif.put("inputs", inputs);
        motif.put("outputs", outputs);
        motif.put("netlist", netlist);

        JSONArray motifs = new JSONArray();
        motifs.put(motif);

        DAGW GW = NetSynth.runNetSynthCode(verilog, new ArrayList<NetSynthSwitch>(), motifs);
        System.out.println(GW.printGraph());

        HashMap<String, Integer> gateTypeCounts = getGateTypeCounts(GW);
        assert (gateTypeCounts.get("NOR") == 1);
        assert (gateTypeCounts.get("NOT") == 4);
        assert (gateTypeCounts.get("OUTPUT_OR") == 1);
        assert (gateTypeCounts.get("AND") == 0);
    }

    @Test
    public void test_AND_0xFE() throws JSONException {

        String verilog = verilog_0xFE;

        ArrayList<String> inputs = new ArrayList();
        inputs.add("a");
        inputs.add("b");
        ArrayList<String> outputs = new ArrayList();
        outputs.add("y");
        ArrayList<String> netlist = new ArrayList();
        netlist.add("AND(y, a, b)");

        JSONObject motif = new JSONObject();
        motif.put("collection", "motif_library");
        motif.put("inputs", inputs);
        motif.put("outputs", outputs);
        motif.put("netlist", netlist);

        JSONArray motifs = new JSONArray();
        motifs.put(motif);

        DAGW GW = NetSynth.runNetSynthCode(verilog, new ArrayList<NetSynthSwitch>(), motifs);
        System.out.println(GW.printGraph());

        HashMap<String, Integer> gateTypeCounts = getGateTypeCounts(GW);
        assert (gateTypeCounts.get("NOR") == 0);
        assert (gateTypeCounts.get("NOT") == 1);
        assert (gateTypeCounts.get("OUTPUT_OR") == 0);
        assert (gateTypeCounts.get("AND") == 2);
    }

    @Test
    public void test_OR_0xFE() throws JSONException {

        String verilog = verilog_0xFE;

        ArrayList<String> inputs = new ArrayList();
        inputs.add("a");
        inputs.add("b");
        ArrayList<String> outputs = new ArrayList();
        outputs.add("y");
        ArrayList<String> netlist = new ArrayList();
        netlist.add("OR(y, a, b)");

        JSONObject motif = new JSONObject();
        motif.put("collection", "motif_library");
        motif.put("inputs", inputs);
        motif.put("outputs", outputs);
        motif.put("netlist", netlist);

        JSONArray motifs = new JSONArray();
        motifs.put(motif);

        DAGW GW = NetSynth.runNetSynthCode(verilog, new ArrayList<NetSynthSwitch>(), motifs);
        System.out.println(GW.printGraph());

        HashMap<String, Integer> gateTypeCounts = getGateTypeCounts(GW);
//        assert (gateTypeCounts.get("NOR") == 0);
//        assert (gateTypeCounts.get("NOT") == 1);
//        assert (gateTypeCounts.get("OUTPUT_OR") == 0);
//        assert (gateTypeCounts.get("AND") == 2);

    }

    @Test
    public void test_NOR3_0xFE() throws JSONException {

        String verilog = verilog_0xFE;

        ArrayList<String> inputs = new ArrayList();
        inputs.add("a");
        inputs.add("b");
        inputs.add("c");
        ArrayList<String> outputs = new ArrayList();
        outputs.add("y");
        ArrayList<String> netlist = new ArrayList();
        netlist.add("NOR(y, a, b, c)");

        JSONObject motif = new JSONObject();
        motif.put("collection", "motif_library");
        motif.put("inputs", inputs);
        motif.put("outputs", outputs);
        motif.put("netlist", netlist);

        JSONArray motifs = new JSONArray();
        motifs.put(motif);

        DAGW GW = NetSynth.runNetSynthCode(verilog, new ArrayList<NetSynthSwitch>(), motifs);
        System.out.println(GW.printGraph());

        HashMap<String, Integer> gateTypeCounts = getGateTypeCounts(GW);
        assert (gateTypeCounts.get("NOR") == 1);
        assert (gateTypeCounts.get("NOT") == 4);
        assert (gateTypeCounts.get("OUTPUT_OR") == 0);
        assert (gateTypeCounts.get("AND") == 0);

    }
}
