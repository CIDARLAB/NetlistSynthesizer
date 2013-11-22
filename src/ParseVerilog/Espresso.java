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
         int pw = (int) Math.pow(2, circ.inputNames.size());
        String truthfunc = dectoBin(circ.inputgatetable, pw);
        System.out.println(truthfunc);
        String line = "";
        line += (".i " + circ.inputNames.size());
        esfile.add(line);
        line = "";
        line += (".e " + circ.outputNames.size());
        esfile.add(line);
        line = "";
        line += ".lb";
        for(String xinp:circ.inputNames)
        {
            line += " "+xinp;
        }
        esfile.add(line);
        line = "";
        line += ".ob";
        for(String xout:circ.outputNames)
        {
            line += " " + xout;
        }
        esfile.add(line);
       
        for(int i=0;i<pw; i++)
        {
            line ="";
            String tempinp = dectoBin(i,circ.inputNames.size());
            line += tempinp;
            line += " ";
            line += truthfunc.charAt(i);
            esfile.add(line);
        }
        line ="";
        line += ".e";
        esfile.add(line);
        
        
        for(String nline:esfile)
        {
            System.out.println(nline);
        }
        
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
