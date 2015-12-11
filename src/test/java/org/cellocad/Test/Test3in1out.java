package org.cellocad.Test;

import org.cellocad.BU.DAG.DAGW;
import org.cellocad.BU.netsynth.NetSynth;
import org.cellocad.BU.netsynth.NetSynthSwitch;
import org.cellocad.MIT.dnacompiler.Gate;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Test;
import org.junit.Test;
//import org.json.JSONArray;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Bryan Der on 12/9/15.
 */
public class Test3in1out {


    @Test
    public void testAll3in1out() throws IOException, ParseException {

        FileReader reader = new FileReader("resources/netsynthResources/netlist_in3out1_OUTPUT_OR.json");
        JSONParser jsonParser = new JSONParser();
        JSONArray jsonArray = (JSONArray) jsonParser.parse(reader);
        org.json.JSONArray motifLibrary = new org.json.JSONArray();
        for(int i=0; i<jsonArray.size(); ++i) {
            Object obj = (Object) jsonArray.get(i);
            motifLibrary.put(obj);
        }


        String[] files = Util.filenamesInDirectory("resources/verilog/3-input");
        ArrayList<String> verilogs = new ArrayList<>();
        for (String file : files) {
            if (file.endsWith(".v")) {
                verilogs.add(file);
            }
        }

        String result = "";
        for (String verilog : verilogs) {


            if(verilog.equals("0x00.v") || verilog.equals("0xFF.v")) {
                continue;
            }

            String verilog_file = "resources/verilog/3-input/" + verilog;
            DAGW dagw = NetSynth.runNetSynth(verilog_file, new ArrayList<NetSynthSwitch>(), motifLibrary);

            int gate_count = 0;
            for(Gate g: dagw.Gates) {

                System.out.println(g.Type);

                if(g.Type.equals(Gate.GateType.NOR)) {
                    gate_count++;
                }
                if(g.Type.equals(Gate.GateType.NOT)) {
                    gate_count++;
                }
            }
            result += verilog + " " + gate_count + "\n";
        }

        Util.fileWriter("gateCounts2.txt", result, false);



//        for (int i = 0; i < jsonArray.size(); ++i) {
//            JSONObject obj = (JSONObject) jsonArray.get(i);
//            System.out.println(i + " " + obj.get("collection"));
//        }
    }

}
