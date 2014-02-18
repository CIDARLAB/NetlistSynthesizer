/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BU.ParseVerilog;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author prashantvaidyanathan
 */
public class CircuitDetails {
    public List<String> inputNames ;
    
    public List<String> outputNames;
    public List<Integer> inputgatetable;
    //public int inputgatetable;
    public CircuitDetails()
    {
        inputNames = new ArrayList<String>();
        outputNames = new ArrayList<String>();
        inputgatetable = new ArrayList<Integer>();
    }
    /*public CircuitDetails(List<String> inp, List<String> outp, int inpgttt)
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
    }*/
    
    public CircuitDetails(List<String> inp, List<String> outp, List<Integer> inpgttt)
    {
        inputNames = new ArrayList<String>();
        outputNames = new ArrayList<String>();
        inputgatetable = new ArrayList<Integer>();
        for(String xinp:inp)
        {
            inputNames.add(xinp);
        }
        for(String xoutp:outp)
        {
            outputNames.add(xoutp);
        }
        for(int xtt:inpgttt)
        {
            inputgatetable.add(xtt);
        }
    }
    
}
