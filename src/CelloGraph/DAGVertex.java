/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CelloGraph;

import java.util.ArrayList;
import java.util.List;
import netsynth.Wire;

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
    
    public Wire outW = new Wire();
    public int Index;
    public String Name;
    public String Type;
    public DAGEdge outgoing;
    
    public DAGVertex()
    {
        this.Index = 0;
        this.Name = "";
        this.Type = "";
        this.outgoing = null;
        
        this.outW = new Wire();
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
       
        this.outW = new Wire();
        this.outgoing = null;
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
        this.outgoing = de;
        
        this.outW = new Wire();
    
    }
    
    
    
    @Override
    public String toString()
    {
        String x = "";
        return x;
    }
    
}
