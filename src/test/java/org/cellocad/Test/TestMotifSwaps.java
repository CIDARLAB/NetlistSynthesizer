package org.cellocad.Test;


import org.cellocad.BU.DAG.DAGW;
import org.cellocad.BU.netsynth.NetSynth;
import org.cellocad.BU.netsynth.NetSynthSwitch;
import org.cellocad.MIT.dnacompiler.Gate;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.util.ArrayList;

public class TestMotifSwaps {

    @Test
    public void test_OR_0xFE() throws JSONException {

        String verilog = "module A(output out1,  input in1, in2, in3);\n" +
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

        int nor = 0;
        int not = 0;
        int output_or = 0;

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
        }

        System.out.println(nor);
        System.out.println(not);
        System.out.println(output_or);

    }
}
