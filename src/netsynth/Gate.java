/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package netsynth;

import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author prashantvaidyanathan
 */

public class Gate {
    String gname;
    public enum GateType{
           NOT,
           NOR2, 
           NOR3,
           NAND2, 
           NAND3,
           AND2, 
           AND3, 
           OR2, 
           OR3, 
           XOR2,
           XNOR2;
    }
    GateType gtype;
    public List<Wire> input;
    public Wire output;
    public int gatestage;
    
    Gate()
    {      
         input = new ArrayList<Wire>();
         output = new Wire();
    }
    Gate(GateType gType, List<Wire> inputWires, Wire outputWire)
    {
        input = new ArrayList<Wire>();
        input.addAll(inputWires);
        gtype = gType;
        output = outputWire;        
        if(!input.isEmpty())
        {
            int maxStage = input.get(0).wirestage;
            for(Wire w:input)
            {
                if(maxStage<w.wirestage)
                    maxStage = w.wirestage;
            }
            maxStage++;
            output.wirestage = maxStage;
            gatestage = maxStage;
        }
        
    }
    Gate(GateType gType, String gName, List<Wire> inputWires, Wire outputWire)
    {
        input = new ArrayList<Wire>();
        input.addAll(inputWires);
        gtype = gType;
        output = outputWire;        
        gname = gName;
        if(!input.isEmpty())
        {
            int maxStage = input.get(0).wirestage;
            for(Wire w:input)
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
            for(Wire w:this.input)
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
