/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package netsynth;

import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author prashantvaidyanathan
 */

public class Gate {
    String type;
    public enum GateType{
           NOT,
           NOR2, 
           NOR3,
           NAND2, 
           NAND3,
           AND2, 
           AND3, 
           OR2, 
           OR3, 
           XOR2,
           XNOR2;
    }
    GateType gtype;
    public List<Wire> input;
    public Wire output;
    Gate()
    {      
           input = new ArrayList<Wire>();
           output = new Wire();
    }
    
}
