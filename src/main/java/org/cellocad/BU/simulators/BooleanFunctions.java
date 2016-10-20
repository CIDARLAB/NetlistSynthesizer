/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cellocad.BU.simulators;

import java.util.List;
import org.cellocad.BU.dom.DGate;
import org.cellocad.BU.dom.DGateType;
import org.cellocad.BU.dom.DWire;
import org.cellocad.BU.dom.DWire.DWireValue;

/**
 *
 * @author prashantvaidyanathan
 */
public class BooleanFunctions {
    
    public static DWireValue bcompute(DGateType gateType, List<DWireValue> inputs){
        DWireValue value = DWireValue._x;
        DGate gate = new DGate();
        gate.gtype = gateType;
        
        for(DWireValue val:inputs){
            DWire wire = new DWire();
            wire.wValue = val;
            gate.input.add(wire);
        }
        DWire output = new DWire();
        gate.output = output;
        BooleanSimulator.bfunction(gate);
        return gate.output.wValue;
    }
    
    public static void bAND(DGate and)
    {
        int flag =1;
        for(DWire xw:and.input)
        {
            if(xw.wValue == DWireValue._x)
            {
                and.output.wValue = DWireValue._x;
                return;
            }
            if(xw.wValue == DWireValue._0)
            {
                flag =0;
            }
        }
        if(flag == 1)
        {
            and.output.wValue = DWireValue._1;
        }
        else
        {
            and.output.wValue = DWireValue._0;
        }
    }
    
    public static void bNAND(DGate nand)
    {
        int flag =1;
        for(DWire xw:nand.input)
        {
            if(xw.wValue == DWireValue._x)
            {
                nand.output.wValue = DWireValue._x;
                return;
            }
            if(xw.wValue == DWireValue._0)
            {
                flag =0;
            }
        }
        if(flag == 1)
        {
            nand.output.wValue = DWireValue._0;
        }
        else
        {
            nand.output.wValue = DWireValue._1;
        }
    }
    
    public static void bOR(DGate or)
    {
        int flag =0;
        for(DWire xw:or.input)
        {
            if(xw.wValue == DWireValue._x)
            {
                or.output.wValue = DWireValue._x;
                return;
            }
            if(xw.wValue == DWireValue._1)
            {
                flag =1;
            }
        }
        if(flag == 1)
        {
            or.output.wValue = DWireValue._1;
        }
        else
        {
            or.output.wValue = DWireValue._0;
        }
    }
    
    public static void bNOR(DGate nor)
    {
        int flag =0;
        for(DWire xw:nor.input)
        {
            if(xw.wValue == DWireValue._x)
            {
                nor.output.wValue = DWireValue._x;
                return;
            }
            if(xw.wValue == DWireValue._1)
            {
                flag =1;
            }
        }
        if(flag == 1)
        {
            nor.output.wValue = DWireValue._0;
        }
        else
        {
            nor.output.wValue = DWireValue._1;
        }
    }
    
    public static void bNOT(DGate not)
    {
        for(DWire xw:not.input)
        {
            if(xw.wValue == DWireValue._x)
            {
                not.output.wValue = DWireValue._x;
                return;
            }
            if(xw.wValue == DWireValue._0)
                not.output.wValue = DWireValue._1;
            else
                not.output.wValue = DWireValue._0;
        }
        
    }
    
    public static void bBUF(DGate buf)
    {
        for(DWire xw:buf.input)
        {
            if(xw.wValue == DWireValue._x)
            {
                buf.output.wValue = DWireValue._x;
                return;
            }
            if(xw.wValue == DWireValue._0)
                buf.output.wValue = DWireValue._0;
            else
                buf.output.wValue = DWireValue._1;
        }
        
    }
    
    public static void bXOR(DGate xor)
    {
        int cnt =0;
        for(DWire xw:xor.input)
        {
            if(xw.wValue == DWireValue._x)
            {
                xor.output.wValue = DWireValue._x;
                return;
            }
            if(xw.wValue == DWireValue._1)
            {
                cnt++;
            }
        }
        if(cnt%2 == 1)
        {
                xor.output.wValue = DWireValue._1;
        }
        else
        {
            xor.output.wValue = DWireValue._0;
        }
    }
    
    public static void bXNOR(DGate xor)
    {
        int cnt =0;
        for(DWire xw:xor.input)
        {
            if(xw.wValue == DWireValue._x)
            {
                xor.output.wValue = DWireValue._x;
                return;
            }
            if(xw.wValue == DWireValue._1)
            {
                cnt++;
            }
        }
        if(cnt%2 == 1)
        {
                xor.output.wValue = DWireValue._0;
        }
        else
        {
            xor.output.wValue = DWireValue._1;
        }
    }
    
    
    public static DWireValue bANDWireValue(DGate and)
    {
        int flag =1;
        for(DWire xw:and.input)
        {
            if(xw.wValue == DWireValue._x)
            {
                return DWireValue._x;
            }
            if(xw.wValue == DWireValue._0)
            {
                flag =0;
            }
        }
        if(flag == 1)
        {
            return DWireValue._1;
        }
        else
        {
            return DWireValue._0;
        }
    }
    
    public static DWireValue bNANDWireValue(DGate nand)
    {
        int flag =1;
        for(DWire xw:nand.input)
        {
            if(xw.wValue == DWireValue._x)
            {
                return DWireValue._x;
            }
            if(xw.wValue == DWireValue._0)
            {
                flag =0;
            }
        }
        if(flag == 1)
        {
            return DWireValue._0;
        }
        else
        {
            return DWireValue._1;
        }
    }
    
    public static DWireValue bORWireValue(DGate or)
    {
        int flag =0;
        for(DWire xw:or.input)
        {
            if(xw.wValue == DWireValue._x)
            {
                return DWireValue._x;
            }
            if(xw.wValue == DWireValue._1)
            {
                flag =1;
            }
        }
        if(flag == 1)
        {
            return DWireValue._1;
        }
        else
        {
            return DWireValue._0;
        }
    }
    
    public static DWireValue bNORWireValue(DGate nor)
    {
        int flag =0;
        for(DWire xw:nor.input)
        {
            if(xw.wValue == DWireValue._x)
            {
                return DWireValue._x;
            }
            if(xw.wValue == DWireValue._1)
            {
                flag =1;
            }
        }
        if(flag == 1)
        {
            return DWireValue._0;
        }
        else
        {
            return DWireValue._1;
        }
    }
    
    public static DWireValue bNOTWireValue(DGate not)
    {
        for(DWire xw:not.input)
        {
            if(xw.wValue == DWireValue._x)
            {
                return DWireValue._x;
            }
            if(xw.wValue == DWireValue._0)
                return DWireValue._1;
            else
                return DWireValue._0;
        }
        return null;
        
    }
    
    public static DWireValue bBUFWireValue(DGate buf)
    {
        for(DWire xw:buf.input)
        {
            if (xw.wValue == DWireValue._x) {
                return DWireValue._x;

            }
            if (xw.wValue == DWireValue._0) {
                return DWireValue._0;
            } else {
                return DWireValue._1;
            }
        }
        return null;
        
    }
    
    public static DWireValue bXORWireValue(DGate xor)
    {
        int cnt =0;
        for(DWire xw:xor.input)
        {
            if(xw.wValue == DWireValue._x)
            {
                return DWireValue._x;
            }
            if(xw.wValue == DWireValue._1)
            {
                cnt++;
            }
        }
        if(cnt%2 == 1)
        {
            return DWireValue._1;
        }
        else
        {
            return DWireValue._0;
        }
    }
    
    public static DWireValue bXNORWireValue(DGate xor)
    {
        int cnt =0;
        for(DWire xw:xor.input)
        {
            if(xw.wValue == DWireValue._x)
            {
                return DWireValue._x;
            }
            if(xw.wValue == DWireValue._1)
            {
                cnt++;
            }
        }
        if(cnt%2 == 1)
        {
            return DWireValue._0;
        }
        else
        {
            return DWireValue._1;
        }
    }
    
    
}
