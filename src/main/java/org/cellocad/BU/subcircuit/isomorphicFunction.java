/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cellocad.BU.subcircuit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.cellocad.BU.ParseVerilog.Convert;

/**
 *
 * @author prash
 */
public class isomorphicFunction {
    
    //Mapping based on number of inputs.
    public static Map<Integer,permutationMap> getPermutationMapping(int size)
    {
        Map<Integer,permutationMap> map = new HashMap<Integer,permutationMap>();
        
        if(size == 1)
            return map;//empty?
        
        if(size == 2)
        {
            permutationMap p0 = new permutationMap();
            permutationMap p1 = new permutationMap();
            
            p0.inputOrder.add(0);
            p0.inputOrder.add(1);
            
            p0.permutation.add(0);
            p0.permutation.add(1);
            p0.permutation.add(2);
            p0.permutation.add(3);
            
            p1.inputOrder.add(1);
            p1.inputOrder.add(0);
            
            p1.permutation.add(0);
            p1.permutation.add(2);
            p1.permutation.add(1);
            p1.permutation.add(3);
            
            map.put(0, p1);
            map.put(1, p1);
        }
        else if(size == 3){
            
            permutationMap p0 = new permutationMap();
            permutationMap p1 = new permutationMap();
            permutationMap p2 = new permutationMap();
            permutationMap p3 = new permutationMap();
            permutationMap p4 = new permutationMap();
            permutationMap p5 = new permutationMap();
            
            p0.inputOrder.add(0);
            p0.inputOrder.add(1);
            p0.inputOrder.add(2);
            
            p0.permutation.add(0);
            p0.permutation.add(1);
            p0.permutation.add(2);
            p0.permutation.add(3);
            p0.permutation.add(4);
            p0.permutation.add(5);
            p0.permutation.add(6);
            p0.permutation.add(7);
            
            p1.inputOrder.add(0);
            p1.inputOrder.add(2);
            p1.inputOrder.add(1);
            
            p1.permutation.add(0);
            p1.permutation.add(2);
            p1.permutation.add(1);
            p1.permutation.add(3);
            p1.permutation.add(4);
            p1.permutation.add(6);
            p1.permutation.add(5);
            p1.permutation.add(7);
            
            
            p2.inputOrder.add(2);
            p2.inputOrder.add(0);
            p2.inputOrder.add(1);
            
            p2.permutation.add(0);
            p2.permutation.add(4);
            p2.permutation.add(1);
            p2.permutation.add(5);
            p2.permutation.add(2);
            p2.permutation.add(6);
            p2.permutation.add(3);
            p2.permutation.add(7);
            
            
            p3.inputOrder.add(2);
            p3.inputOrder.add(1);
            p3.inputOrder.add(0);
            
            p3.permutation.add(0);
            p3.permutation.add(4);
            p3.permutation.add(2);
            p3.permutation.add(6);
            p3.permutation.add(1);
            p3.permutation.add(5);
            p3.permutation.add(3);
            p3.permutation.add(7);
            
            p4.inputOrder.add(1);
            p4.inputOrder.add(2);
            p4.inputOrder.add(0);
            
            p4.permutation.add(0);
            p4.permutation.add(2);
            p4.permutation.add(4);
            p4.permutation.add(6);
            p4.permutation.add(1);
            p4.permutation.add(3);
            p4.permutation.add(5);
            p4.permutation.add(7);
            
            
            p5.inputOrder.add(1);
            p5.inputOrder.add(0);
            p5.inputOrder.add(2);
            
            p5.permutation.add(0);
            p5.permutation.add(1);
            p5.permutation.add(4);
            p5.permutation.add(5);
            p5.permutation.add(2);
            p5.permutation.add(3);
            p5.permutation.add(6);
            p5.permutation.add(7);
            
            map.put(0, p0);
            map.put(1, p1);
            map.put(2, p2);
            map.put(3, p3);
            map.put(4, p4);
            map.put(5, p5);
        }
        
        
        return map;
    }
    
    
    //This is for multiple outputs. Requires NPNP equivalence. Can be done later. 
    public static boolean checkCircuitIsomorphism(List<String> tt1, List<String> tt2)
    {
        boolean result = false;
        
        
        return result;
    }
    
    
    //Based the permuted TT based on the permutation matrix.
    public static String getPermutedTT(String t,List<Integer> permutation){
        String result = "";
        for(int i=0;i<permutation.size();i++){
            result += t.charAt(permutation.get(i));
        }
        return result;
    }
    
    //To be done with NPN equivalence. 
    public static List<Integer> getInputPermutation(String t1, String t2){
        List<Integer> map = new ArrayList<Integer>();
        
        return map;
    }
    
    //Returns true if the two truth tables have a P equivalence. 
    public static boolean checkPequivalence(String t1, String t2)
    {
        if(t1.length() != t2.length())
            return false;
        
        int len = t1.length();
        if((t1.charAt(0) != t2.charAt(0)) || (t1.charAt(len-1)!= t2.charAt(len-1)))
            return false;
        if(getOnes(t1)!=getOnes(t2))
            return false;
        
        
        int n = (int)(Math.log10(len)/Math.log10(2));
        if(n>4)
        {
            System.out.println("Sub-circuits with more than 4 inputs not supported. Feature will be added soon");
            return false;
        }
        
        Map<Integer,permutationMap> map = getPermutationMapping(n);
        if(getPermutationIndex(t1,t2,map) == -1)
            return false;
        else 
            return true;
            
        
        /*
        //Implement a difference matrix approach. Need to check advantages. 
        List<Integer> diff = new ArrayList<Integer>();
        for(int i=1;i<(len-1);i++)
        {
            if(t1.charAt(i)!= t2.charAt(i))
            {
                //diff.add(i);
            }
        }
        */
    }
    
    //Given 2 truth tables, get the index of the permutation map which indicates the order in which the permutation occurs. t2 is permutated based on the map permutation matrices and matched with t1.
    public static int getPermutationIndex(String t1,String t2, Map<Integer,permutationMap> map){
        int result = -1;
        
        for(int i=0;i<map.size();i++)
        {
            String ptt = getPermutedTT(t2,map.get(i).inputOrder);
            if(ptt.equals(t1))
            {
                result = i;
                break;
            }
        }
        
        return result;
    }
    
    //Gets the number of '1's in the truth table.
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
