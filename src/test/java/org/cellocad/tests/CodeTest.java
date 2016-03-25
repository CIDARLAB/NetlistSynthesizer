/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cellocad.tests;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import org.cellocad.BU.netsynth.NetSynth;
import org.cellocad.BU.netsynth.NetSynthSwitch;
import org.cellocad.BU.netsynth.Utilities;
import org.cellocad.BU.parseVerilog.parseVerilogFile;

/**
 *
 * @author prash
 */
public class CodeTest {
    
    public static void main(String[] args) {
        //NetSynth netsynth = new NetSynth("demosomething");
        //NetSynth netsynth4 = new NetSynth();
        /*
        String verilogCode = "module A(output out1,  input in1, in2);\n" +
                             "  always@(in1,in2)\n" +
                             "    begin\n" +
                             "      case({in1,in2})\n" +
                             "        2'b00: {out1} = 1'b0;\n" +
                             "        2'b01: {out1} = 1'b0;\n" +
                             "        2'b10: {out1} = 1'b1;\n" +
                             "        2'b11: {out1} = 1'b0;\n" +
                             "      endcase\n" +
                             "    end\n" +
                             "endmodule";
        
        parseVerilogFile.parseVerilog(verilogCode);
        */
        /*
        NetSynth.printNetlist(netsynth.getNetlistCode(verilogCode, new ArrayList<NetSynthSwitch>()));
        
        String filepath = Utilities.getNetSynthResourcesFilepath();
        System.out.println("PRE CHECK :: " + NetSynth.precheck(filepath));
        */
        //NetSynth net = new NetSynth();
        //NetSynth net1 = new NetSynth("testJob");
        //NetSynth net2 = new NetSynth("testJob");
        //System.out.println("The error should show up after this::");
//        String filepath = Utilities.getNetSynthResourcesFilepath();
//        
//        NetSynth net3 = new NetSynth("testJob",filepath,"/home/prash/cidar/netsynth1/");
//        
//        System.out.println(netsynth.getJobid());
    }
    
    
    
}
