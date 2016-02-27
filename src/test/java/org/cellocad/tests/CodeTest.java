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

/**
 *
 * @author prash
 */
public class CodeTest {
    
    public static void main(String[] args) {
        NetSynth netsynth = new NetSynth("testJob");
        //NetSynth netsynth4 = new NetSynth();
        
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
        
        
        netsynth.getNetlistCode(verilogCode, new ArrayList<NetSynthSwitch>());
        
        
//        System.out.println(netsynth.getJobid());
    }
    
    
    
}
