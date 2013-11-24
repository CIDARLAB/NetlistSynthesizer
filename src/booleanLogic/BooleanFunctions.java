/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package booleanLogic;

import netsynth.DGate;
import netsynth.DWire;
import netsynth.DWire.DWireValue;

/**
 *
 * @author prashantvaidyanathan
 */
public class BooleanFunctions {
    
    public static void AND2(DGate and)
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
    
}
