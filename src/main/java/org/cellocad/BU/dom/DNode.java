/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cellocad.BU.dom;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author prash
 */
public class DNode {
    
    public List<DEdge> inputs;
    public DEdge output;
    public String gatename;
    public DGateType type;
    public Set<Set<String>> subcircuitLeaves;
    public DNode(){
        subcircuitLeaves = new HashSet<Set<String>>();
        inputs = new ArrayList<DEdge>();
        //output = new DEdge();
        
    }
}
