/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cellocad.exhaustiveTests;

import java.util.List;
import org.cellocad.BU.parseVerilog.Convert;
import org.cellocad.BU.simulators.BooleanSimulator;
import org.cellocad.BU.dom.DGate;
import org.cellocad.BU.dom.DGateType;
import org.cellocad.BU.precomputation.PreCompute;
import org.cellocad.BU.subcircuit.SubcircuitLibrary;
import org.junit.Test;

/**
 *
 * @author prash
 */
public class AllNetlistsTest {
    
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
