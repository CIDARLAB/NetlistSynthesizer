/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.cellocad.test;

import org.cellocad.BU.dom.DGate;
import static org.cellocad.BU.netsynth.NetSynth.functionOutp;
import static org.cellocad.BU.netsynth.NetSynth.parseEspressoToNORNAND;
import static org.cellocad.BU.netsynth.NetSynth.printGate;
import java.util.ArrayList;
import java.util.List;
import org.cellocad.BU.adaptors.EspressoAdaptor;

/**
 *
 * @author prashantvaidyanathan
 */
public class TestEspresso {
    
    public static void testEspresso()
    {
        
        List<String> espressoOut = new ArrayList<String>();
        espressoOut = EspressoAdaptor.runEspresso("");
        
        List<DGate> SOPgates = new ArrayList<DGate>();
        List<DGate> NORgates = new ArrayList<DGate>();
        
        SOPgates = EspressoAdaptor.parseEspressoOutput(espressoOut);
        
        System.out.println("POS format : ");
        
        if(functionOutp)
        {
                String gateString = printGate(SOPgates.get(0));
                System.out.println(gateString);
            
        }
        else
        {
            for(DGate g:SOPgates)
            {
               
                String gateString = printGate(g);
                System.out.println(gateString);
            }
        }
        
        NORgates = parseEspressoToNORNAND(espressoOut);
     
        System.out.println("\nUniversal Gates : ");
        if(functionOutp)
        {
                String gateString = printGate(NORgates.get(0));
                System.out.println(gateString);
            
        }
        else
        {
            for(DGate g:NORgates)
            {  
                String gateString = printGate(g);
                System.out.println(gateString);
            }
           
        }
        
    }
    
    
    
    
    
}
