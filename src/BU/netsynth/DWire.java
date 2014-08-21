/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BU.netsynth;

import java.io.Serializable;

/**
 *
 * @author prashantvaidyanathan
 */
public class DWire implements Serializable {
   
    
     public String name; 
     
     public enum DWireValue{
         _1,
         _0,
         _x;
     }
     
     public DWireValue wValue;
     public DWireType wtype;
     int wirestage;
    
     public DWire()
     {
         name = "";
         wValue = DWireValue._x;
         wtype = DWireType.connector;  
     }
     public DWire(String wirename)
     {
        wtype = DWireType.connector;
        wValue = DWireValue._x;
        this.name = wirename;
     }
     public DWire(String wirename,DWireType wType)
     {
         if(wType == DWireType.input)
         {
            this.wirestage = 0;
          
         }
         this.name = wirename;
         this.wtype = wType;
         wValue = DWireValue._x;
     }
     public DWire(DWire xwire)
     {
        this.wtype = xwire.wtype;
        this.wValue = xwire.wValue;
        this.name = xwire.name;
     }
}
