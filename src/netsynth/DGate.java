/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package netsynth;

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
    DGateType gtype;
    public List<DWire> input;
    public DWire output;
    public int gatestage;
    public float dagstage;
    
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
            float totInpWires=0;
            int tot=0;
            int maxStage = input.get(0).wirestage;
            for(DWire w:input)
            {
                totInpWires++;
                tot+= w.dagstage;    
                if(maxStage<w.wirestage)
                    maxStage = w.wirestage;
            }
            this.dagstage = (tot/totInpWires);
            this.output.dagstage = (tot/totInpWires);
            maxStage++;
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
            float totInpWires=0;
            int tot=0;
            int maxStage = input.get(0).wirestage;
            for(DWire w:input)
            {
                totInpWires++;
                tot+= w.dagstage;
                if(maxStage<w.wirestage)
                    maxStage = w.wirestage;
            }
            this.dagstage = (tot/totInpWires);
            this.output.dagstage = (tot/totInpWires);
            maxStage++;
            maxStage++;
            output.wirestage = maxStage;
            gatestage = maxStage;
        }
        
    }
    
    public void calculateStage()
    {
        if(!this.input.isEmpty())
        {
            float totInpWires=0;
            int tot=0;
            int maxStage = this.input.get(0).wirestage;
            for(DWire w:this.input)
            {
                totInpWires++;
                tot+= w.dagstage;
                if(maxStage<w.wirestage)
                    maxStage = w.wirestage;
            }
            this.dagstage = (tot/totInpWires);
            this.output.dagstage = (tot/totInpWires);
            maxStage++;
            this.output.wirestage = maxStage;
            this.gatestage = maxStage;
        }
        
    }
}
