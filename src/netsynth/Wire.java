/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package netsynth;

/**
 *
 * @author prashantvaidyanathan
 */
public class Wire {
   
    
     String name; 
     public enum WireType{
         input,
         output, 
         connector;
     }
     WireType wtype;
     int wirestage;
     Wire()
     {
         name = "";
         wtype = WireType.connector;  
     }
     Wire(String wirename)
     {
        this.name = wirename;
     }
     Wire(String wirename,WireType wType)
     {
         if(wType == WireType.input)
         {
            this.wirestage = 0;
         }
         this.name = wirename;
         this.wtype = wType;

     }
     
     
}
