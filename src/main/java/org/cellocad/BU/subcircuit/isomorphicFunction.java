/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cellocad.BU.subcircuit;

import java.util.ArrayList;
import java.util.List;
import org.cellocad.BU.ParseVerilog.Convert;

/**
 *
 * @author prash
 */
public class isomorphicFunction {
    
    
    public static void getPermutationMatrix(List<String> inputs,List<String> jumbledInputs)
    {
        System.out.println("Inputs : " + inputs);
        System.out.println("Input Size : " + inputs.size());
        int combinations = getFactorial(inputs.size());
        System.out.println("Possible Combinations : " + combinations);
        
        int[] mat = new int[inputs.size()];
        for(int i=0;i<inputs.size();i++)
            mat[i] = i;
        
        for(int i=0;i<combinations;i++)
        {
            
        }
    }
    
    
    public static boolean checkCircuitIsomorphism(List<String> tt1, List<String> tt2)
    {
        boolean result = false;
        
        
        return result;
    }
    
    public static boolean checkTTIsomorphism(String t1, String t2, List<String> i1, List<String> i2)
    {
        boolean result = false;
        if(t1.length() != t2.length())
            return false;
        
        int len = t1.length();
        int inputSize = i1.size();
        if((t1.charAt(0) != t2.charAt(0)) || (t1.charAt(len-1)!= t2.charAt(len-1)))
            return false;
        if(getOnes(t1)!=getOnes(t2))
            return false;
        
        List<Integer> diff = new ArrayList<Integer>();
        for(int i=1;i<(len-1);i++)
        {
            if(t1.charAt(i)!= t2.charAt(i))
            {
                diff.add(i);
            }
        //    String bin = Convert.dectoBin(i, inputSize);
        //    System.out.println("Binary " + bin);
        }
        
        System.out.println("Difference Matrix : "+diff);
        
        return result;
    }
    
    public static int getOnes(String t)
    {
        int count = 0;
        for(int i=0;i<t.length();i++)
        {
            if(t.charAt(i)=='1')
                count++;
        }
        return count;
    }
    
    public static int getFactorial(int n)
    {
        if(n == 1 || n == 0)
            return n;
        else
        {
            return n*getFactorial(n-1);
        }
    }
    
}
