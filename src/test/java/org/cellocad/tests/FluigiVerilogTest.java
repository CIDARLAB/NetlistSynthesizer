/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cellocad.tests;

import java.util.ArrayList;
import java.util.List;
import org.cellocad.BU.fluigi.VerilogFluigiGrammar;
import org.cellocad.BU.netsynth.Utilities;
import org.junit.Test;

/**
 *
 * @author prash
 */
public class FluigiVerilogTest {
    
    @Test
    public void testFluigiVerilog(){
        String filepath = Utilities.getNetSynthResourcesFilepath();
        filepath += "fluigi.v";
        List<String> lines = new ArrayList<String>();
        lines = Utilities.getFileContentAsStringList(filepath);
        String fileLine = "";
        for(String line:lines){
            fileLine += (line + "\n");
        }
        System.out.println("FILE LINE :: \n" + fileLine);
        VerilogFluigiGrammar.getParseTree(fileLine);
    }
}
