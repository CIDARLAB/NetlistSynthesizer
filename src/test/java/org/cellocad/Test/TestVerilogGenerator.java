/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cellocad.Test;

import java.util.ArrayList;
import java.util.List;
import org.cellocad.BU.precomputation.genVerilogFile;

/**
 *
 * @author prash
 */
public class TestVerilogGenerator {
    
    public static void testVerilogFromEq()
    {
        List<String> eq = new ArrayList<>();
        //eq.add("f=(e.a'+b)'''+a+b+(c+d);");
        eq.add("out1 = a+b;");
        eq.add("out2 = c + (a.b) + (a'.b);");
        List<String> veriloglines = genVerilogFile.createVerilogFromEq(eq);
        for(String xline:veriloglines)
            System.out.println(xline);
    }
    
    
    
}
