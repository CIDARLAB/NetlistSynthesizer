/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cellocad.BU.subcircuit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.cellocad.BU.dom.DGate;

/**
 *
 * @author prash
 */
public class SwapMap {
    public Map<String,String> inputmap;
    public List<DGate> subcircuit;
  
    public SwapMap(){
        inputmap = new HashMap<String,String> ();
        subcircuit = new ArrayList<DGate>();
    }
}
