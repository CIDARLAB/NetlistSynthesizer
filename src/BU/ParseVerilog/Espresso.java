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
public class Espresso {
    public static List<String> createFile(CircuitDetails circ)
    {
        List<String> esfile = new ArrayList<String>();
         int pw = (int) Math.pow(2, circ.inputNames.size());
        String truthfunc = Convert.dectoBin(circ.inputgatetable, pw);
        //System.out.println(truthfunc);
        String line = "";
        line += (".i " + circ.inputNames.size());
        esfile.add(line);
        line = "";
        line += (".o " + circ.outputNames.size());
        esfile.add(line);
        line = "";
        line += ".ilb";
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
            String tempinp = Convert.dectoBin(i,circ.inputNames.size());
            line += tempinp;
            line += " ";
            line += truthfunc.charAt(i);
            esfile.add(line);
        }
        line ="";
        line += ".e";
        esfile.add(line);
        
      
        
        return esfile;
    }
    
  
}
