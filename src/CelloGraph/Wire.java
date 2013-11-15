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
public class Wire {
    public int Index;
    public Gate From;
    public Gate To;
    public Wire Next;
    public DWire wire;
    
    public Wire()
    {
        this.Index =0;
        this.wire = null;
        this.From = null;
        this.To = null;
        this.Next = null;
    }
    public Wire(int indx,Gate dFrom,Gate dTo)
    {
        this.Index = indx;
        this.wire = null;
        this.From = dFrom;
        this.To = dTo;
        this.Next = null;
    }
    public Wire(int indx,Gate dFrom,Gate dTo,Wire next)
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
