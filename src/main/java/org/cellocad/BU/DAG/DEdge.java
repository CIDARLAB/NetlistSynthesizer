/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cellocad.BU.DAG;

import java.util.List;
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
    
    public DEdge(){
        
    }
    
}
