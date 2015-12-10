/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cellocad.ExhaustiveTests;

import java.util.List;
import org.cellocad.BU.ParseVerilog.Convert;
import org.cellocad.BU.booleanLogic.BooleanSimulator;
import org.cellocad.BU.netsynth.DGate;
import org.cellocad.BU.netsynth.DGateType;
import org.cellocad.BU.precomputation.PreCompute;
import org.cellocad.BU.subcircuit.SubcircuitLibrary;
import org.junit.Test;

/**
 *
 * @author prash
 */
public class AllNetlistsTest {
    
    @Test
    public void testPrecomputedLibrary(){
        List<SubcircuitLibrary> library = PreCompute.getCircuitLibrary();
        for(SubcircuitLibrary subcirc:library){
            System.out.println("Truth Table :: " + Convert.bintoDec(BooleanSimulator.getTruthTable(subcirc.netlist, subcirc.inputs).get(0)));
            System.out.println(getRepressorCount(subcirc.netlist) + "\n");
        }
    }
    
    private static int getRepressorCount(List<DGate> netlist){
        if(containsOUTPUT_OR(netlist)){
            return (netlist.size()-2);
        }
        else{
            return netlist.size();
        }
//        return 0;
    }
    
    private static boolean containsOUTPUT_OR(List<DGate> netlist){
        if(netlist.size() >1){
            if(netlist.get(netlist.size()-1).gtype.equals(DGateType.NOT)){
                if (netlist.get(netlist.size() - 2).gtype.equals(DGateType.NOR)) {
                    return true;
                }
            }
        }
        
        return false;
    }

    
}
