/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BU.precomputation;

import BU.ParseVerilog.Convert;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author prashantvaidyanathan
 */
public class genVerilogFile {
    
    public static List<String> createVerilogFile(int inputs,String hex)
    {
        
        List<String> verout = new ArrayList<String>();
        int dec = Convert.HextoInt(hex);
        int combinations = (int)Math.pow(2, inputs);
        int maxdec = (int)Math.pow(2, combinations);
        maxdec--;
        if(dec > maxdec)
        {
            dec = maxdec;
        }
        
        List<String> inputvars = new ArrayList<String>();
        int incnt =1;
        for(int i=0;i<inputs;i++)
        {
            String inpstr = "inp" + incnt;
            inputvars.add(inpstr);
            incnt++;
        }
        
        String modulename = "";
        modulename += hex + "_" + inputs;
        String modulestring = "module " +  modulename +"(outout out, input ";
        for(int i=0;i<inputs;i++)
        {
            if(i == (inputs-1))
            {
                modulestring += inputvars.get(i);
            }
            else
            {
                modulestring += (inputvars.get(i) + ","); 
            }
        }
        modulestring += ");";
        int ttpow;
        ttpow = (int) Math.pow(2, inputs);
        String bin = Convert.dectoBin(dec, ttpow);
        String line = "";
        verout.add(modulestring);
        verout.add("     reg out;");
        //line = "";
        line += "     always@(";
        for(int i=0;i<inputs;i++)
        {
            if(i == (inputs-1))
            {
                line += inputvars.get(i);
            }
            else
            {
                line += (inputvars.get(i) + ","); 
            }
        }    
        line += ") begin";
        verout.add(line);
        line = "";
        line+= "          case({";
        for(int i=(inputs-1);i>=0;i--)
        {
            if(i == 0)
            {
                line += inputvars.get(i);
            }
            else
            {
                line += (inputvars.get(i) + ","); 
            }
        }
        line+= "})";
        verout.add(line);
        int ttlength = bin.length();
        
        for(int i=0;i<ttlength;i++)
        {
            line ="               ";
            line += (inputs+ "b'");
            String bini = Convert.dectoBin(i, inputs);
            line +=bini;
            line += (": out = ");
            line += bin.charAt(i);
            line += ";";
            verout.add(line);
        }
        verout.add("               default: out = 0;");
        verout.add("          endcase");
        verout.add("     end");
        verout.add("endmodule");
        printverilogfile(verout);
        return verout;
    }
    
    public static void printverilogfile(List<String> verilogfile)
    {
        for(String verline:verilogfile)
        {
            System.out.println(verline);
        }
    }
    
}
