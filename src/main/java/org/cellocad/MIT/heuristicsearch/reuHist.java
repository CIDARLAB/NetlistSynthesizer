/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cellocad.MIT.heuristicsearch;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author prashantvaidyanathan
 */
public class reuHist {
    
    public double titrationpoint;
    public double mean;
    public List<Double> x = new ArrayList<Double>();
    public List<Double> y = new ArrayList<Double>();
    public reuHist()
    {
        titrationpoint =0;
        mean =0;
        x = new ArrayList<Double>();
        y = new ArrayList<Double>();
    }
    
}
