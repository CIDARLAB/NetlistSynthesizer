/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cellocad.BU.DAG;

import java.util.ArrayList;
import java.util.List;
import org.cellocad.BU.netsynth.DWire.DWireValue;
import org.cellocad.BU.netsynth.DWireType;

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
