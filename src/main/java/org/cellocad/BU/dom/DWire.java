/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cellocad.BU.dom;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author prashantvaidyanathan
 */
public class DWire implements Serializable {

    public String name;
    public String logicValue;

    //shane additions start
    public DGate fromGate;
    public List<DGate> toGate;      //for splitting the wire to multiple gates
    public boolean isWritten = false;
    public DWire dupChannel;
    public LayerType layer;   
    //shane additions end
    
    public enum DWireValue {
        _1,
        _0,
        _x;
    }

    public DWireValue wValue;
    public DWireType wtype;
    int wirestage;



    public DWire() {                    //constructing unnamed connector wire
        name = "";
        logicValue = "";
        wValue = DWireValue._x;
        wtype = DWireType.connector;
    }

    public DWire(String wirename) {     //constructing connector wire with name
        wtype = DWireType.connector;
        wValue = DWireValue._x;
        logicValue = "";
        this.name = wirename;
    }

    public DWire(String wirename, DWireType wType) {
        if (wType == DWireType.input) {
            this.wirestage = 0;
        }
        this.name = wirename;
        this.wtype = wType;
        logicValue = "";
        wValue = DWireValue._x;
    }

    public DWire(DWire xwire) {
        this.wtype = xwire.wtype;
        this.wValue = xwire.wValue;
        this.name = xwire.name;
        this.logicValue = xwire.logicValue;
    }

    public static String printDWire(DWire xwire) {
        String wstr = "";
        wstr += ("Wire Name : " + xwire.name);
        wstr += ("::Wire Type " + xwire.wtype.toString());
        return wstr;
    }    
}
