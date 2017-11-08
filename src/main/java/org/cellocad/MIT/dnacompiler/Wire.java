/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cellocad.MIT.dnacompiler;

import org.cellocad.BU.dom.DWire;

/**
 *
 * @author prashantvaidyanathan
 */
public class Wire {
    public int index;
    public Gate from;
    public Gate to;
    public Wire next;
    public DWire wire;
    
    public Wire()
    {
        this.index =0;
        this.wire = null;
        this.from = null;
        this.to = null;
        this.next = null;
    }
    public Wire(Wire w) {
	this.index = w.index;
        this.wire = w.wire;
        this.from = w.from;
        this.to = w.to;
        this.next = w.next;
    }
    public Wire(int indx,Gate dFrom,Gate dTo)
    {
        this.index = indx;
        this.wire = null;
        this.from = dFrom;
        this.to = dTo;
        this.next = null;
    }
    public Wire(int indx,Gate dFrom,Gate dTo,Wire next)
    {
        this.index = indx;
        this.wire = null;
        this.from = dFrom;
        this.to = dTo;
        this.next = next;
    }
    @Override
    public String toString()
    {
        String x="";
        return x;
    }
}
