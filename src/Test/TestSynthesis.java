/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Test;

import BU.netsynth.DGate;
import BU.netsynth.DGate.DGateType;
import BU.netsynth.DWire;
import BU.netsynth.DWire.DWireType;
import BU.netsynth.Global;
import BU.netsynth.NetSynth;
import BU.netsynth.NetlistConversionFunctions;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author prashantvaidyanathan
 */
public class TestSynthesis {
    
    /**
     * 
     * 
     * 
     * 
     * 
     */
    public static void testConversion(DGateType gtype, int inputno)
    {
        DGate inputgate = new DGate();
        inputgate.gtype = gtype;
        for(int i=0;i<inputno;i++)
        {
            String wirename = "InpWire" + Global.wirecount++;
            DWire inpW = new DWire(wirename,DWireType.input);
            inputgate.input.add(inpW);
        }
        inputgate.output = new DWire("OutW",DWireType.output);
        
        System.out.println("----------------------------------\n Input Gate :");
        System.out.println(NetSynth.netlist(inputgate));
        
        System.out.println("-----------------------------------\n Converted to NOR Gate");
        List<DGate> convertednetlist = new ArrayList<DGate>();
        convertednetlist = NetlistConversionFunctions.GatetoNORNOT(inputgate);
        for(int i=0;i<convertednetlist.size();i++)
        {    
            System.out.println(NetSynth.netlist(convertednetlist.get(i)));
        }
        
    }
}
