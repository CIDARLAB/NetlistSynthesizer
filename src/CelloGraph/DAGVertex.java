/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CelloGraph;

import java.util.ArrayList;
import java.util.List;
import netsynth.DWire;

/**
 *
 * @author prashantvaidyanathan
 */
public class DAGVertex {
    
    public enum VertexType{
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
    public DAGEdge Outgoing;
    
    public DAGVertex()
    {
        this.Index = 0;
        this.Name = "";
        this.Type = "";
        this.Outgoing = null;
        
        this.outW = new DWire();
    }
    public DAGVertex(int ind,String dType)
    {
        this.Index = ind;
        this.Type = dType;
        if(dType.equals(VertexType.NOR.toString()))
        {
            this.Name = "~|";
        }
        else if(dType.equals(VertexType.NOT.toString()))
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
    public DAGVertex(int ind,String dType,DAGEdge de)
    {
        this.Index = ind;
        this.Type = dType;
        if(dType.equals(VertexType.NOR.toString()))
        {
            this.Name = "~|";
        }
        else if(dType.equals(VertexType.NOT.toString()))
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
