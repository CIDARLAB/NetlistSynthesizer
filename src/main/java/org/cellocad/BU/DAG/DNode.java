/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cellocad.BU.DAG;

import java.util.List;
import org.cellocad.BU.netsynth.DGateType;

/**
 *
 * @author prash
 */
public class DNode {
    
    public List<DEdge> inputs;
    public DEdge output;
    public String gatename;
    public DGateType type;
    
    public DNode(){
        
    }
}
