/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cellocad.BU.fluigi;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author prash
 */
public class FluigiCircuitDetails {
    
    public String modulename;
    
    public List<String> inputs;
    public List<String> outputs;
    public List<String> wires;
    
    public FluigiCircuitDetails(){
        modulename = "";
        inputs = new ArrayList<>();
        outputs = new ArrayList<>();
        wires = new ArrayList<>();
    }
    
}