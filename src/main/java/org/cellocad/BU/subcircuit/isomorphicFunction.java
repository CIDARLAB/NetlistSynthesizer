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

/**
 *
 * @author prash
 */
public class isomorphicFunction {
    
    //Mapping based on number of inputs. Has upto 4 inputs.
    public static Map<Integer,permutationMap> getPermutationMapping(int size)
    {
        Map<Integer,permutationMap> map = new HashMap<Integer,permutationMap>();
        
        //<editor-fold desc="Permutation Maps">
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
            
            map.put(0, p0);
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
        else if(size == 4){
            permutationMap p0 = new permutationMap();
            permutationMap p1 = new permutationMap();
            permutationMap p2 = new permutationMap();
            permutationMap p3 = new permutationMap();
            permutationMap p4 = new permutationMap();
            permutationMap p5 = new permutationMap();
            permutationMap p6 = new permutationMap();
            permutationMap p7 = new permutationMap();
            permutationMap p8 = new permutationMap();
            permutationMap p9 = new permutationMap();
            permutationMap p10 = new permutationMap();
            permutationMap p11 = new permutationMap();
            permutationMap p12 = new permutationMap();
            permutationMap p13 = new permutationMap();
            permutationMap p14 = new permutationMap();
            permutationMap p15 = new permutationMap();
            permutationMap p16 = new permutationMap();
            permutationMap p17 = new permutationMap();
            permutationMap p18 = new permutationMap();
            permutationMap p19 = new permutationMap();
            permutationMap p20 = new permutationMap();
            permutationMap p21 = new permutationMap();
            permutationMap p22 = new permutationMap();
            permutationMap p23 = new permutationMap();
            
            p0.inputOrder.add(0);
            p0.inputOrder.add(1);
            p0.inputOrder.add(2);
            p0.inputOrder.add(3);
            
            p0.permutation.add(0);
            p0.permutation.add(1);
            p0.permutation.add(2);
            p0.permutation.add(3);
            p0.permutation.add(4);
            p0.permutation.add(5);
            p0.permutation.add(6);
            p0.permutation.add(7);
            p0.permutation.add(8);
            p0.permutation.add(9);
            p0.permutation.add(10);
            p0.permutation.add(11);
            p0.permutation.add(12);
            p0.permutation.add(13);
            p0.permutation.add(14);
            p0.permutation.add(15);
            
            p1.inputOrder.add(0);
            p1.inputOrder.add(1);
            p1.inputOrder.add(3);
            p1.inputOrder.add(2);
            
            p1.permutation.add(0);
            p1.permutation.add(2);
            p1.permutation.add(1);
            p1.permutation.add(3);
            p1.permutation.add(4);
            p1.permutation.add(6);
            p1.permutation.add(5);
            p1.permutation.add(7);
            p1.permutation.add(8);
            p1.permutation.add(10);
            p1.permutation.add(9);
            p1.permutation.add(11);
            p1.permutation.add(12);
            p1.permutation.add(14);
            p1.permutation.add(13);
            p1.permutation.add(15);
            
            p2.inputOrder.add(0);
            p2.inputOrder.add(2);
            p2.inputOrder.add(1);
            p2.inputOrder.add(3);
            
            p2.permutation.add(0);
            p2.permutation.add(1);
            p2.permutation.add(4);
            p2.permutation.add(5);
            p2.permutation.add(2);
            p2.permutation.add(3);
            p2.permutation.add(6);
            p2.permutation.add(7);
            p2.permutation.add(8);
            p2.permutation.add(9);
            p2.permutation.add(12);
            p2.permutation.add(13);
            p2.permutation.add(10);
            p2.permutation.add(11);
            p2.permutation.add(14);
            p2.permutation.add(15);
            
            
            p3.inputOrder.add(0);
            p3.inputOrder.add(2);
            p3.inputOrder.add(3);
            p3.inputOrder.add(1);
            
            p3.permutation.add(0);
            p3.permutation.add(2);
            p3.permutation.add(4);
            p3.permutation.add(6);
            p3.permutation.add(1);
            p3.permutation.add(3);
            p3.permutation.add(5);
            p3.permutation.add(7);
            p3.permutation.add(8);
            p3.permutation.add(10);
            p3.permutation.add(12);
            p3.permutation.add(14);
            p3.permutation.add(9);
            p3.permutation.add(11);
            p3.permutation.add(13);
            p3.permutation.add(15);
           
            p4.inputOrder.add(0);
            p4.inputOrder.add(3);
            p4.inputOrder.add(1);
            p4.inputOrder.add(2);
            
            p4.permutation.add(0);
            p4.permutation.add(4);
            p4.permutation.add(1);
            p4.permutation.add(5);
            p4.permutation.add(2);
            p4.permutation.add(6);
            p4.permutation.add(3);
            p4.permutation.add(7);
            p4.permutation.add(8);
            p4.permutation.add(12);
            p4.permutation.add(9);
            p4.permutation.add(13);
            p4.permutation.add(10);
            p4.permutation.add(14);
            p4.permutation.add(11);
            p4.permutation.add(15);
            
            p5.inputOrder.add(0);
            p5.inputOrder.add(3);
            p5.inputOrder.add(2);
            p5.inputOrder.add(1);
            
            p5.permutation.add(0);
            p5.permutation.add(4);
            p5.permutation.add(2);
            p5.permutation.add(6);
            p5.permutation.add(1);
            p5.permutation.add(5);
            p5.permutation.add(3);
            p5.permutation.add(7);
            p5.permutation.add(8);
            p5.permutation.add(12);
            p5.permutation.add(10);
            p5.permutation.add(14);
            p5.permutation.add(9);
            p5.permutation.add(13);
            p5.permutation.add(11);
            p5.permutation.add(15);
            
            p6.inputOrder.add(1);
            p6.inputOrder.add(0);
            p6.inputOrder.add(2);
            p6.inputOrder.add(3);
            
            p6.permutation.add(0);
            p6.permutation.add(1);
            p6.permutation.add(2);
            p6.permutation.add(3);
            p6.permutation.add(8);
            p6.permutation.add(9);
            p6.permutation.add(10);
            p6.permutation.add(11);
            p6.permutation.add(4);
            p6.permutation.add(5);
            p6.permutation.add(6);
            p6.permutation.add(7);
            p6.permutation.add(12);
            p6.permutation.add(13);
            p6.permutation.add(14);
            p6.permutation.add(15);
            
            p7.inputOrder.add(1);
            p7.inputOrder.add(0);
            p7.inputOrder.add(3);
            p7.inputOrder.add(2);
            
            p7.permutation.add(0);
            p7.permutation.add(2);
            p7.permutation.add(1);
            p7.permutation.add(3);
            p7.permutation.add(8);
            p7.permutation.add(10);
            p7.permutation.add(9);
            p7.permutation.add(11);
            p7.permutation.add(4);
            p7.permutation.add(6);
            p7.permutation.add(5);
            p7.permutation.add(7);
            p7.permutation.add(12);
            p7.permutation.add(14);
            p7.permutation.add(13);
            p7.permutation.add(15);
            
            p8.inputOrder.add(1);
            p8.inputOrder.add(2);
            p8.inputOrder.add(0);
            p8.inputOrder.add(3);
            
            p8.permutation.add(0);
            p8.permutation.add(1);
            p8.permutation.add(4);
            p8.permutation.add(5);
            p8.permutation.add(8);
            p8.permutation.add(9);
            p8.permutation.add(12);
            p8.permutation.add(13);
            p8.permutation.add(2);
            p8.permutation.add(3);
            p8.permutation.add(6);
            p8.permutation.add(7);
            p8.permutation.add(10);
            p8.permutation.add(11);
            p8.permutation.add(14);
            p8.permutation.add(15);
            
            p9.inputOrder.add(1);
            p9.inputOrder.add(2);
            p9.inputOrder.add(3);
            p9.inputOrder.add(0);
            
            p9.permutation.add(0);
            p9.permutation.add(2);
            p9.permutation.add(4);
            p9.permutation.add(6);
            p9.permutation.add(8);
            p9.permutation.add(10);
            p9.permutation.add(12);
            p9.permutation.add(14);
            p9.permutation.add(1);
            p9.permutation.add(3);
            p9.permutation.add(5);
            p9.permutation.add(7);
            p9.permutation.add(9);
            p9.permutation.add(11);
            p9.permutation.add(13);
            p9.permutation.add(15);
            
            p10.inputOrder.add(1);
            p10.inputOrder.add(3);
            p10.inputOrder.add(0);
            p10.inputOrder.add(2);
            
            p10.permutation.add(0);
            p10.permutation.add(4);
            p10.permutation.add(1);
            p10.permutation.add(5);
            p10.permutation.add(8);
            p10.permutation.add(12);
            p10.permutation.add(9);
            p10.permutation.add(13);
            p10.permutation.add(2);
            p10.permutation.add(6);
            p10.permutation.add(3);
            p10.permutation.add(7);
            p10.permutation.add(10);
            p10.permutation.add(14);
            p10.permutation.add(11);
            p10.permutation.add(15);
            
            p11.inputOrder.add(1);
            p11.inputOrder.add(3);
            p11.inputOrder.add(2);
            p11.inputOrder.add(0);
            
            p11.permutation.add(0);
            p11.permutation.add(4);
            p11.permutation.add(2);
            p11.permutation.add(6);
            p11.permutation.add(8);
            p11.permutation.add(12);
            p11.permutation.add(10);
            p11.permutation.add(14);
            p11.permutation.add(1);
            p11.permutation.add(5);
            p11.permutation.add(3);
            p11.permutation.add(7);
            p11.permutation.add(9);
            p11.permutation.add(13);
            p11.permutation.add(11);
            p11.permutation.add(15);
            
            p12.inputOrder.add(2);
            p12.inputOrder.add(0);
            p12.inputOrder.add(1);
            p12.inputOrder.add(3);
            
            p12.permutation.add(0);
            p12.permutation.add(1);
            p12.permutation.add(8);
            p12.permutation.add(9);
            p12.permutation.add(2);
            p12.permutation.add(3);
            p12.permutation.add(10);
            p12.permutation.add(11);
            p12.permutation.add(4);
            p12.permutation.add(5);
            p12.permutation.add(12);
            p12.permutation.add(13);
            p12.permutation.add(6);
            p12.permutation.add(7);
            p12.permutation.add(14);
            p12.permutation.add(15);
            
            p13.inputOrder.add(2);
            p13.inputOrder.add(0);
            p13.inputOrder.add(3);
            p13.inputOrder.add(1);
            
            p13.permutation.add(0);
            p13.permutation.add(2);
            p13.permutation.add(8);
            p13.permutation.add(10);
            p13.permutation.add(1);
            p13.permutation.add(3);
            p13.permutation.add(9);
            p13.permutation.add(11);
            p13.permutation.add(4);
            p13.permutation.add(6);
            p13.permutation.add(12);
            p13.permutation.add(14);
            p13.permutation.add(5);
            p13.permutation.add(7);
            p13.permutation.add(13);
            p13.permutation.add(15);
            
            
            p14.inputOrder.add(2);
            p14.inputOrder.add(1);
            p14.inputOrder.add(0);
            p14.inputOrder.add(3);
            
            p14.permutation.add(0);
            p14.permutation.add(1);
            p14.permutation.add(8);
            p14.permutation.add(9);
            p14.permutation.add(4);
            p14.permutation.add(5);
            p14.permutation.add(12);
            p14.permutation.add(13);
            p14.permutation.add(2);
            p14.permutation.add(3);
            p14.permutation.add(10);
            p14.permutation.add(11);
            p14.permutation.add(6);
            p14.permutation.add(7);
            p14.permutation.add(14);
            p14.permutation.add(15);
            
            p15.inputOrder.add(2);
            p15.inputOrder.add(1);
            p15.inputOrder.add(3);
            p15.inputOrder.add(0);
            
            p15.permutation.add(0);
            p15.permutation.add(2);
            p15.permutation.add(8);
            p15.permutation.add(10);
            p15.permutation.add(4);
            p15.permutation.add(6);
            p15.permutation.add(12);
            p15.permutation.add(14);
            p15.permutation.add(1);
            p15.permutation.add(3);
            p15.permutation.add(9);
            p15.permutation.add(11);
            p15.permutation.add(5);
            p15.permutation.add(7);
            p15.permutation.add(13);
            p15.permutation.add(15);
            
            p16.inputOrder.add(2);
            p16.inputOrder.add(3);
            p16.inputOrder.add(0);
            p16.inputOrder.add(1);
            
            p16.permutation.add(0);
            p16.permutation.add(4);
            p16.permutation.add(8);
            p16.permutation.add(12);
            p16.permutation.add(1);
            p16.permutation.add(5);
            p16.permutation.add(9);
            p16.permutation.add(13);
            p16.permutation.add(2);
            p16.permutation.add(6);
            p16.permutation.add(10);
            p16.permutation.add(14);
            p16.permutation.add(3);
            p16.permutation.add(7);
            p16.permutation.add(11);
            p16.permutation.add(15);
            
            p17.inputOrder.add(2);
            p17.inputOrder.add(3);
            p17.inputOrder.add(1);
            p17.inputOrder.add(0);
            
            p17.permutation.add(0);
            p17.permutation.add(4);
            p17.permutation.add(8);
            p17.permutation.add(12);
            p17.permutation.add(2);
            p17.permutation.add(6);
            p17.permutation.add(10);
            p17.permutation.add(14);
            p17.permutation.add(1);
            p17.permutation.add(5);
            p17.permutation.add(9);
            p17.permutation.add(13);
            p17.permutation.add(3);
            p17.permutation.add(7);
            p17.permutation.add(11);
            p17.permutation.add(15);
            
            p18.inputOrder.add(3);
            p18.inputOrder.add(0);
            p18.inputOrder.add(1);
            p18.inputOrder.add(2);
            
            p18.permutation.add(0);
            p18.permutation.add(8);
            p18.permutation.add(1);
            p18.permutation.add(9);
            p18.permutation.add(2);
            p18.permutation.add(10);
            p18.permutation.add(3);
            p18.permutation.add(11);
            p18.permutation.add(4);
            p18.permutation.add(12);
            p18.permutation.add(5);
            p18.permutation.add(13);
            p18.permutation.add(6);
            p18.permutation.add(14);
            p18.permutation.add(7);
            p18.permutation.add(15);
            
            p19.inputOrder.add(3);
            p19.inputOrder.add(0);
            p19.inputOrder.add(2);
            p19.inputOrder.add(1);
            
            p19.permutation.add(0);
            p19.permutation.add(8);
            p19.permutation.add(2);
            p19.permutation.add(10);
            p19.permutation.add(1);
            p19.permutation.add(9);
            p19.permutation.add(3);
            p19.permutation.add(11);
            p19.permutation.add(4);
            p19.permutation.add(12);
            p19.permutation.add(6);
            p19.permutation.add(14);
            p19.permutation.add(5);
            p19.permutation.add(13);
            p19.permutation.add(7);
            p19.permutation.add(15);
            
            p20.inputOrder.add(3);
            p20.inputOrder.add(1);
            p20.inputOrder.add(0);
            p20.inputOrder.add(2);
            
            p20.permutation.add(0);
            p20.permutation.add(8);
            p20.permutation.add(1);
            p20.permutation.add(9);
            p20.permutation.add(4);
            p20.permutation.add(12);
            p20.permutation.add(5);
            p20.permutation.add(13);
            p20.permutation.add(2);
            p20.permutation.add(10);
            p20.permutation.add(3);
            p20.permutation.add(11);
            p20.permutation.add(6);
            p20.permutation.add(14);
            p20.permutation.add(7);
            p20.permutation.add(15);
            
            p21.inputOrder.add(3);
            p21.inputOrder.add(1);
            p21.inputOrder.add(2);
            p21.inputOrder.add(0);
            
            p21.permutation.add(0);
            p21.permutation.add(8);
            p21.permutation.add(2);
            p21.permutation.add(10);
            p21.permutation.add(4);
            p21.permutation.add(12);
            p21.permutation.add(6);
            p21.permutation.add(14);
            p21.permutation.add(1);
            p21.permutation.add(9);
            p21.permutation.add(3);
            p21.permutation.add(11);
            p21.permutation.add(5);
            p21.permutation.add(13);
            p21.permutation.add(7);
            p21.permutation.add(15);
            
            p22.inputOrder.add(3);
            p22.inputOrder.add(2);
            p22.inputOrder.add(0);
            p22.inputOrder.add(1);
            
            p22.permutation.add(0);
            p22.permutation.add(8);
            p22.permutation.add(4);
            p22.permutation.add(12);
            p22.permutation.add(1);
            p22.permutation.add(0);
            p22.permutation.add(5);
            p22.permutation.add(13);
            p22.permutation.add(2);
            p22.permutation.add(10);
            p22.permutation.add(6);
            p22.permutation.add(14);
            p22.permutation.add(3);
            p22.permutation.add(11);
            p22.permutation.add(7);
            p22.permutation.add(15);
            
            p23.inputOrder.add(3);
            p23.inputOrder.add(2);
            p23.inputOrder.add(1);
            p23.inputOrder.add(0);
            
            p23.permutation.add(0);
            p23.permutation.add(8);
            p23.permutation.add(4);
            p23.permutation.add(12);
            p23.permutation.add(2);
            p23.permutation.add(10);
            p23.permutation.add(6);
            p23.permutation.add(14);
            p23.permutation.add(1);
            p23.permutation.add(9);
            p23.permutation.add(5);
            p23.permutation.add(13);
            p23.permutation.add(3);
            p23.permutation.add(11);
            p23.permutation.add(7);
            p23.permutation.add(15);
            
            map.put(0, p0);
            map.put(1, p1);
            map.put(2, p2);
            map.put(3, p3);
            map.put(4, p4);
            map.put(5, p5);
            map.put(6, p6);
            map.put(7, p7);
            map.put(8, p8);
            map.put(9, p9);
            map.put(10, p10);
            map.put(11, p11);
            map.put(12, p12);
            map.put(13, p13);
            map.put(14, p14);
            map.put(15, p15);
            map.put(16, p16);
            map.put(17, p17);
            map.put(18, p18);
            map.put(19, p19);
            map.put(20, p20);
            map.put(21, p21);
            map.put(22, p22);
            map.put(23, p23);
            
            //</editor-fold>
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
    
    public static Map<String,String> getInputMapping(List<String> netlist,List<String> subnetlist,List<Integer>mapping){
        Map<String,String> inpMap = new HashMap<String,String>();
        for(int i=0;i<mapping.size();i++){
            inpMap.put(netlist.get(i), subnetlist.get(mapping.get(i)));
        }
            
        return inpMap;
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
