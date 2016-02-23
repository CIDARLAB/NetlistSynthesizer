/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cellocad.BU.netsynth;

import lombok.Getter;
import org.cellocad.BU.dom.DWire;
import org.cellocad.BU.dom.DWireType;

/**
 *
 * @author prash
 */
public class Global {
    
    
    public static DWire zero = new DWire("_zero", DWireType.GND);
    
    public static DWire one = new DWire("_one", DWireType.Source);  
}
