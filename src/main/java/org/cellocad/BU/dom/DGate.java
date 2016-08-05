/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cellocad.BU.dom;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;
/**
 *
 * @author prashantvaidyanathan
 */

public class DGate implements Serializable{
    public String gname;
    
    public DGateType gtype;
    public List<DWire> input;
    public DWire output;
    public int gatestage;
    public int gindex;
    
    public String symbol;
    public String picpath;
    
    //shane's additions
    public JSONObject opInfo;   //from UCF, contains all info regarding the microfluidic operation the gate performs
    public String mintName;     //name used for this gate in mint file
    public int inTermInd;       //current index of inTerm JSONArray
    public int outTermInd;      //current index of outTerm JSONArray
    public int inTermVal = 4;       //by default input terminal is 4th orientation (left) EG: *** ----->IN[(Device)]OUT-----> ***
    public int outTermVal = 2;      //by default output terminal is 2nd orientation (right)
    public int bankCount = -1;
    public boolean inTermFlag = false;      //flag that is true if inTerm JSONArray exists (used for gates with nonstandard orientations)
    public boolean outTermFlag = false;     //flag that is true if outTerm JSONArray exists (used for gates with nonstandard orientations)
    public boolean isWritten = false;       //flag that is true if gate has been printed to mint file
    public LayerType layer;
    //end shane's additions
    
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
    
    public DGate(DGate gate)
    {
        gtype = gate.gtype;
        gname = gate.gname;
        gindex = gate.gindex;
        input = new ArrayList<DWire>();
        for(DWire x:gate.input)
        {
            input.add(new DWire(x));
        }
        output = new DWire(gate.output);
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
    
    public boolean isuFGate(){
        if(this.gtype.equals(DGateType.uF)){
            return true;
        }
        return false;
    }
}
