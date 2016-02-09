/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cellocad.tests;

import java.util.ArrayList;
import java.util.List;
import org.cellocad.BU.netsynth.NetSynth;
import org.cellocad.BU.netsynth.NetSynthSwitch;
import org.junit.Test;

/**
 *
 * @author prash
 */
public class NetSynthTest {
    
    
    @Test
    public void testGetNetlistCode(){
        System.out.println("\ntestGetNetlistCode");
        List<NetSynthSwitch> switches = new ArrayList<NetSynthSwitch>();
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
        
        NetSynth.printNetlist(NetSynth.getNetlistCode(verilogCode, switches));    
    }
    
    @Test
    public void testGetNetlistCodeSingleLine(){
        System.out.println("\ntestGetNetlistCodeSingleLine");
        List<NetSynthSwitch> switches = new ArrayList<NetSynthSwitch>();
        String verilogCode = "module A(output out1,  input in1, in2); always@(in1,in2) begin case({in1,in2}) 2'b00: {out1} = 1'b0; 2'b01: {out1} = 1'b0; 2'b10: {out1} = 1'b1; 2'b11: {out1} = 1'b0; endcase end endmodule";
        NetSynth.printNetlist(NetSynth.getNetlistCode(verilogCode, switches));    
    }
    
}
