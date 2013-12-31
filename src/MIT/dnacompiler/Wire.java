/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MIT.dnacompiler;

import BU.netsynth.DWire;

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
    public Wire(Wire w) {
	this.Index = w.Index;
        this.wire = w.wire;
        this.From = w.From;
        this.To = w.To;
        this.Next = w.Next;
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
