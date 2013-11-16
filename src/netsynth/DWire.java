/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package netsynth;

import java.io.Serializable;

/**
 *
 * @author prashantvaidyanathan
 */
public class DWire implements Serializable {
   
    
     String name; 
     public enum DWireType{
         input,
         output,
         GND,
         Source,
         connector;
     }
     public DWireType wtype;
     int wirestage;
    
     public DWire()
     {
         name = "";
         wtype = DWireType.connector;  
     }
     public DWire(String wirename)
     {
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

     }
     
     
}
