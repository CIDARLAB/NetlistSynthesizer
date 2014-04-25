/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BU.netsynth;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author prashantvaidyanathan
 */

public class DGate implements Serializable{
    String gname;
    public enum DGateType{
           NOT,
           BUF,
           NOR, 
           NAND, 
           AND, 
           OR, 
           XOR,
           XNOR;
    }
    public DGateType gtype;
    public List<DWire> input;
    public DWire output;
    public int gatestage;
    
    
    public DGate()
    {      
         input = new ArrayList<DWire>();
         output = new DWire();
    }
    public DGate(DGateType gType, List<DWire> inputWires, DWire outputWire)
    {
        input = new ArrayList<DWire>();
        this.input.addAll(inputWires);
        gtype = gType;
        output = outputWire;        
        if(!input.isEmpty())
        {
            int maxStage = input.get(0).wirestage;
            for(DWire w:input)
            {
                if(maxStage<w.wirestage)
                    maxStage = w.wirestage;
            }
            maxStage++;
            output.wirestage = maxStage;
            gatestage = maxStage;
        }
        
    }
    public DGate(DGateType gType, String gName, List<DWire> inputWires, DWire outputWire)
    {
        input = new ArrayList<DWire>();
        input.addAll(inputWires);
        gtype = gType;
        output = outputWire;        
        gname = gName;
        if(!input.isEmpty())
        {
            int maxStage = input.get(0).wirestage;
            for(DWire w:input)
            {
             
                if(maxStage<w.wirestage)
                    maxStage = w.wirestage;
            }
            maxStage++;
            output.wirestage = maxStage;
            gatestage = maxStage;
        }
        
    }
    
    public void calculateStage()
    {
        if(!this.input.isEmpty())
        {
            int maxStage = this.input.get(0).wirestage;
            for(DWire w:this.input)
            {
                if(maxStage<w.wirestage)
                    maxStage = w.wirestage;
            }
            maxStage++;
            this.output.wirestage = maxStage;
            this.gatestage = maxStage;
        }
        
    }
}
