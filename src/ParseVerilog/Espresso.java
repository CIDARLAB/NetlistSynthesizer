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
public class Espresso {
    public static List<String> createFile(CircuitDetails circ)
    {
        List<String> esfile = new ArrayList<String>();
        
        String truthfunc = dectoBin(circ.inputgatetable, circ.inputNames.size());
        return esfile;
    }
    
    public static String dectoBin(int dec,int numofInputs)
    {
        String bin ="";
        
        for(int i=0;i<numofInputs;i++)
        {
            bin += "0";
        }
        if(dec ==0)
            return bin;
        
        StringBuilder xbin = new StringBuilder(bin);
        int indx =0;
        while(dec>0)
        {
            int x = dec%2;
            
            char bins =  Character.forDigit(x,10);
            xbin.setCharAt(indx, bins);
            dec = dec/2;
            indx++;
        }
        bin = xbin.reverse().toString();
        return bin;
    }
}
