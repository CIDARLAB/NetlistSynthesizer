/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cellocad.BU.adaptors;

import java.util.ArrayList;
import java.util.List;
import org.cellocad.BU.parseVerilog.CircuitDetails;
import org.cellocad.BU.parseVerilog.Convert;

/**
 *
 * @author prashantvaidyanathan
 */
public class BlifAdaptor {
    public static List<String> createFile(CircuitDetails circ)
    {
        List<String> esfile = new ArrayList<String>();
        int pw = (int) Math.pow(2, circ.inputNames.size());
        List<String> truthfunc = new ArrayList<String>();
        for(String xtt:circ.truthTable)
        {
            //String tempTT= Convert.dectoBin(xtt, pw);
            truthfunc.add(xtt);
        }
        //System.out.println(truthfunc);
        String line = "";
        line = ".model blif_file";
        esfile.add(line);
        
        
        line = "";
        String line2 = "";
        line2 += ".names";
        line += ".inputs";
        for(String xinp:circ.inputNames)
        {
            line += " "+xinp;
            line2 += " "+xinp;
            
        }
        esfile.add(line);
        line = "";
        line += ".outputs";
        for(String xout:circ.outputNames)
        {
            line += " " + xout;
            //line2 += " " + xout;
            
        }
        esfile.add(line);
        //esfile.add(line2);;
        
        String line3 ="";
        for(int i=0;i<circ.outputNames.size();i++)
        {
            if(Convert.bintoDec(circ.truthTable.get(i))==0)
            {
                line3 = ".names "+ circ.outputNames.get(i);
            }   
            else
            {
                line3 = line2 + " " +circ.outputNames.get(i);
            }
            esfile.add(line3);
            //System.out.println(truthfunc.get(i));
            for(int j=0;j<pw; j++)
            {
                if(truthfunc.get(i).charAt(j) == '1')
                {
                    line ="";
                    String tempinp = Convert.dectoBin(j,circ.inputNames.size());
                    line += tempinp;
                    line += " 1";
                    esfile.add(line);
                }
            }
        }
        
        
        
        //add .end line
        line ="";
        line += ".end";
        esfile.add(line);
        
        //for(int i=0;i<esfile.size();i++)
        //    System.out.println(esfile.get(i));
        
        return esfile;
    }
    
}
