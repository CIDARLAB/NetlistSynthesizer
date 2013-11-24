/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package booleanLogic;

import ParseVerilog.Espresso;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import netsynth.DGate;
import netsynth.DWire;
import netsynth.DWire.DWireType;
import netsynth.DWire.DWireValue;

/**
 *
 * @author prashantvaidyanathan
 */
public class BooleanSimulator {
    
    public static void bfunction(DGate gate)
    {
        switch(gate.gtype)
        {
            case AND2 : BooleanFunctions.bAND(gate);
                        break;
            case OR2 : BooleanFunctions.bOR(gate);
                        break;
            case NOR2 : BooleanFunctions.bNOR(gate);
                        break;
            case NAND2 : BooleanFunctions.bNAND(gate);
                        break;
            case NOT : BooleanFunctions.bNOT(gate);
                        break;
            case XOR2 : BooleanFunctions.bXOR(gate);
                        break;
            case XNOR2 : BooleanFunctions.bXNOR(gate);
                        break;
        }
    }
    
    public static String bpermute(List<DGate> netlist)
    {
        //DWireValue outvalue = DWireValue._x;
        String output = "";
        HashMap <String,DWire> inputsW = new HashMap<String,DWire>();
        
        for(DGate dg:netlist)
        {
            for(DWire dw:dg.input)
            {
                if(dw.wtype == DWireType.input)
                {
                   
                    inputsW.put(dw.name.trim(),dw);
                    
                }
            }
        }
        
        HashMap <String,Integer> inputWires = new HashMap<String,Integer>();
        int inpcnt =0;
        
        Iterator it = inputsW.entrySet().iterator();
        while(it.hasNext())
        {
            Map.Entry pair = (Map.Entry)it.next();
            String val = (String) pair.getKey();
            inputWires.put(val, inpcnt);
            inpcnt++;
            //DWire temp = (DWire)pair.getValue();
        }
        
        int inpsize = inputWires.size();
        int inppow = (int) Math.pow(2, inpsize); 
        for(int i=0;i<inppow;i++)
        {
            String xi = Espresso.dectoBin(i, inpsize);
            for(DGate ng:netlist)
            {
                for(DWire dw:ng.input)
                {
                    if(dw.wtype == DWireType.input)
                    {
                        int indx = inputWires.get(dw.name.trim());
                        if(xi.charAt(indx) == '0')
                            dw.wValue = DWireValue._0;
                        else if(xi.charAt(indx) == '1')
                            dw.wValue = DWireValue._1;
                    }
                }
                bfunction(ng);
            }
            DWireValue finaldw = netlist.get(netlist.size()-1).output.wValue; 
            if(finaldw == DWireValue._0)
                output += "0";
            else if(finaldw == DWireValue._1)
                output += "1";
            else if(finaldw == DWireValue._x)
                output += "-";
            
        }
        
        return output;
    }
    
    
}
