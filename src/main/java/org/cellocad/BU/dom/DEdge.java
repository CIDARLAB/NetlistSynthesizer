/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cellocad.BU.dom;

import java.util.ArrayList;
import java.util.List;
import org.cellocad.BU.dom.DWire.DWireValue;

/**
 *
 * @author prash
 */
public class DEdge {
    
    public List<DNode> to;
    public DNode from;
    public String wirename;
    public DWireType type;
    public DWireValue value;
    public DEdge(){
        to = new ArrayList<DNode>();
        value = DWireValue._x;
        //from = new DNode();
    }
    
}
