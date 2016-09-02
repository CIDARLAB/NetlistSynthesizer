/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cellocad.BU.dom;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author prash
 */
public class uFGate extends DGate {
    
    public String symbol;
    public String picpath;
    
    List<DWire> outputs  = new ArrayList<DWire>();
    
}
