/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ParseVerilog;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author prashantvaidyanathan
 */
public class CircuitDetails {
    public List<String> inputNames ;
    
    public List<String> outputNames;
    public int inputgatetable;
    public CircuitDetails()
    {
        inputNames = new ArrayList<String>();
        outputNames = new ArrayList<String>();
    }
    public CircuitDetails(List<String> inp, List<String> outp, int inpgttt)
    {
        inputNames = new ArrayList<String>();
        outputNames = new ArrayList<String>();
    
        for(String xinp:inp)
        {
            inputNames.add(xinp);
        }
        for(String xoutp:outp)
        {
            outputNames.add(xoutp);
        }
        inputgatetable = inpgttt;
        
        
    }
}
