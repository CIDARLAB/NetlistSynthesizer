/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cellocad.Test;

import java.util.ArrayList;
import java.util.List;
import org.cellocad.BU.subcircuit.isomorphicFunction;

/**
 *
 * @author prash
 */
public class TestSubcircuit {
    public static void testPermutationMatrix()
    {
        List<String> inputs = new ArrayList<String>();
        inputs.add("a");
        inputs.add("b");
        inputs.add("c");
        
        List<String> inputsJumbled = new ArrayList<String>();
        inputsJumbled.add("b");
        inputsJumbled.add("a");
        
        
        
        //isomorphicFunction.getPermutationMatrix(inputs,inputsJumbled);
    }
    
    public static void testTTisomorphism()
    {
        List<String> tt1 = new ArrayList<String>();
        List<String> tt2 = new ArrayList<String>();
        List<String> i1 = new ArrayList<String>();
        List<String> i2 = new ArrayList<String>();
        
        tt1.add("00101100");
        tt2.add("01011000");
        
        i1.add("a");
        i1.add("b");
        i1.add("c");
        
        i2.add("x");
        i2.add("y");
        i2.add("z");
        isomorphicFunction.checkPequivalence("00101100", "01011000");
        
    }
    
}
