/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cellocad.BU.parseVerilog;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author prashantvaidyanathan
 */
public class CircuitDetails {
    public List<String> inputNames ;
    
    public List<String> outputNames;
    public List<String> truthTable;
    //public int inputgatetable;
    public CircuitDetails()
    {
        inputNames = new ArrayList<String>();
        outputNames = new ArrayList<String>();
        truthTable = new ArrayList<String>();
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
    
    public CircuitDetails(List<String> inp, List<String> outp, List<String> inpgttt)
    {
        inputNames = new ArrayList<String>();
        outputNames = new ArrayList<String>();
        truthTable = new ArrayList<String>();
        for(String xinp:inp)
        {
            inputNames.add(xinp);
        }
        for(String xoutp:outp)
        {
            outputNames.add(xoutp);
        }
        for(String xtt:inpgttt)
        {
            truthTable.add(xtt);
        }
    }
    
}
