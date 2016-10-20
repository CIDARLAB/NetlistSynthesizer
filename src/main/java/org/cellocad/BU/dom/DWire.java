/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cellocad.BU.dom;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author prashantvaidyanathan
 */
public class DWire implements Serializable {

    public String name;
    public String logicValue;

    public enum DWireValue {

        _1,
        _0,
        _x;
    }

    public DWireValue wValue;
    public DWireType wtype;
    
    @Getter
    @Setter
    private int wirestage;

    public DWire() {
        name = "";
        wirestage = -1;
        logicValue = "";
        wValue = DWireValue._x;
        wtype = DWireType.connector;
    }

    public DWire(String wirename) {
        
        wirestage = -1;
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
        this.wirestage = xwire.wirestage;
    }

    public static String printDWire(DWire xwire) {
        String wstr = "";
        wstr += ("Wire Name : " + xwire.name);
        wstr += ("::Wire Type " + xwire.wtype.toString());
        return wstr;
    }
}
