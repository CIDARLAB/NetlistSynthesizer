/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cellocad.BU.DAG;

import java.util.List;
import org.cellocad.BU.netsynth.DGateType;
import org.cellocad.BU.netsynth.DWire;

/**
 *
 * @author prash
 */

public class DGNode {
    
    public DGateType gateType;
    
    public List<DGNode> to;
    public List<DGNode> from;
    public List<DGNode> sibling;
    
    public DWire outgoing;
    
}
