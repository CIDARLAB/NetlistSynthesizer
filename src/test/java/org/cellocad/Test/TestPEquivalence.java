/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cellocad.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.cellocad.BU.parseVerilog.Convert;
import org.cellocad.BU.subcircuit.isomorphicFunction;
import org.cellocad.BU.subcircuit.permutationMap;

/**
 *
 * @author prash
 */
public class TestPEquivalence {
    public static void main(String[] args) {
        testNinputPE(4);
    }
    
    public static void testNinputPE(int n){
        if(n>4 || n<1)
        {
            System.out.println("Permutation matrices not ready");
        }
        else
        {
            Map<Integer,List<Integer>> pEq = new HashMap<Integer,List<Integer>>();
            Map<Integer,permutationMap> map = isomorphicFunction.getPermutationMapping(n);
            int p1 = (int)Math.pow(2, n);
            int pow = (int)Math.pow(2, p1);
            List<Integer> completed = new ArrayList<Integer>();
            for(int i=0;i<pow;i++)
            {
                if(completed.contains(i)){
                    continue;
                }
                else
                {
                    //System.out.println(i);
                    String boolEq = Convert.dectoBin(i, p1);
                    List<Integer> listNum= new ArrayList<Integer>();
                    for(int j=0;j<map.size();j++)
                    {
                        String permuttedTT = isomorphicFunction.getPermutedTT(boolEq, map.get(j).permutation);
                        int intVal = Convert.bintoDec(permuttedTT);
                        if(!completed.contains(intVal)){
                            listNum.add(intVal);
                            completed.add(intVal);
                        }
                    }
                    pEq.put(i, listNum);
                    System.out.println("Integer: "+i+" :: List Size :"+listNum.size());
                }
            }
            System.out.println("Size of the Map:"+pEq.size());
        }
    }
}
