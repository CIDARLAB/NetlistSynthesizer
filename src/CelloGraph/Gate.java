/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CelloGraph;

import netsynth.DWire;

/**
 *
 * @author prashantvaidyanathan
 */
public class Gate {
    public enum GateType{
        INPUT,
        OUTPUT,
        OUTPUT_OR,
        NOR,
        NOT;
    }
    
    public DWire outW = new DWire();
    public int Index;
    public String Name;
    public String Type;
    public Wire Outgoing;
    
    public Gate()
    {
        this.Index = 0;
        this.Name = "";
        this.Type = "";
        this.Outgoing = null;
        
        this.outW = new DWire();
    }
    public Gate(int ind,String dType)
    {
        this.Index = ind;
        this.Type = dType;
        if(dType.equals(GateType.NOR.toString()))
        {
            this.Name = "~|";
        }
        else if(dType.equals(GateType.NOT.toString()))
        {
            this.Name = "~";
        }
        else
        { 
            this.Name = "";
        }
       
        this.outW = new DWire();
        this.Outgoing = null;
    }
    public Gate(int ind,String dType,Wire de)
    {
        this.Index = ind;
        this.Type = dType;
        if(dType.equals(GateType.NOR.toString()))
        {
            this.Name = "~|";
        }
        else if(dType.equals(GateType.NOT.toString()))
        {
            this.Name = "~";
        }
        else
        { 
            this.Name = "";
        }
        this.Outgoing = de;
        
        this.outW = new DWire();
    
    }
    
    
    
    @Override
    public String toString()
    {
        String x = "";
        return x;
    }
}
