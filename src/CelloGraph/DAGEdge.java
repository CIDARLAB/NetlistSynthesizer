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
public class DAGEdge {
    
    public int Index;
    public DAGVertex From;
    public DAGVertex To;
    public DAGEdge Next;
    public DWire wire;
    
    public DAGEdge()
    {
        this.Index =0;
        this.wire = null;
        this.From = null;
        this.To = null;
        this.Next = null;
    }
    public DAGEdge(int indx,DAGVertex dFrom,DAGVertex dTo)
    {
        this.Index = indx;
        this.wire = null;
        this.From = dFrom;
        this.To = dTo;
        this.Next = null;
    }
    public DAGEdge(int indx,DAGVertex dFrom,DAGVertex dTo,DAGEdge next)
    {
        this.Index = indx;
        this.wire = null;
        this.From = dFrom;
        this.To = dTo;
        this.Next = next;
    }
    @Override
    public String toString()
    {
        String x="";
        return x;
    }
    
    
}
