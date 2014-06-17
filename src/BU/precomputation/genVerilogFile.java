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
    
    public static List<String> createSingleOutpVerilogFile(int inputs,String hex)
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
        String modulestring = "module " +  modulename +"(output out, input ";
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
        line+= "})";
        verout.add(line);
        int ttlength = bin.length();
        
        for(int i=0;i<ttlength;i++)
        {
            line ="               ";
            line += (inputs+ "'b");
            String bini = Convert.dectoBin(i, inputs);
            line +=bini;
            line += (": out = 1'b");
            line += bin.charAt(i);
            line += ";";
            verout.add(line);
        }
        verout.add("               default: out = 1'b0;");
        verout.add("          endcase");
        verout.add("     end");
        verout.add("endmodule");
        //printverilogfile(verout);
        return verout;
    }
    
    
    public static List<String> createSingleOutpVerilogFile(int inputs,int intval)
    {
        
        List<String> verout = new ArrayList<String>();
        int dec = intval;
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
        modulename += intval + "_" + inputs;
        String modulestring = "module " +  modulename +"(output out, input ";
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
        line+= "})";
        verout.add(line);
        int ttlength = bin.length();
        
        for(int i=0;i<ttlength;i++)
        {
            line ="               ";
            line += (inputs+ "'b");
            String bini = Convert.dectoBin(i, inputs);
            line +=bini;
            line += (": out = 1'b");
            line += bin.charAt(i);
            line += ";";
            verout.add(line);
        }
        verout.add("               default: out = 1'b0;");
        verout.add("          endcase");
        verout.add("     end");
        verout.add("endmodule");
        //printverilogfile(verout);
        return verout;
    }
    
    
    public static List<String> createINTVerilogFile(int inputs,List<Integer> intlist)
    {
        
        List<String> verout = new ArrayList<String>();
        //int dec = Convert.HextoInt(hex);
        int combinations = (int)Math.pow(2, inputs);
        int maxdec = (int)Math.pow(2, combinations);
        maxdec--;
        
        for(int intval: intlist)
        {
            if(intval > maxdec)
                intval = maxdec;
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
        modulename +=  "CellVerilog_" + inputs;
        String modulestring = "module " +  modulename +"(output out, input ";
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
        List<String> binTT = new ArrayList<String>();
        for(int intval: intlist)
        {
            binTT.add(Convert.dectoBin(intval, ttpow));
        }
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
        line+= "})";
        verout.add(line);
        int ttlength = binTT.get(0).length();
        
        for(int i=0;i<ttlength;i++)
        {
            line ="               ";
            line += (inputs+ "'b");
            String bini = Convert.dectoBin(i, inputs);
            line +=bini;
            line += (": out = ");
            line += intlist.size() + "'b";
            for(int j=0;j<intlist.size();j++)
            {
                line += binTT.get(j).charAt(i);
            }
            line += ";";
            verout.add(line);
        }
        verout.add("               default: out = 1'b0;");
        verout.add("          endcase");
        verout.add("     end");
        verout.add("endmodule");
        //printverilogfile(verout);
        return verout;
    }
    
    
  
    public static List<String> createHEXVerilogFile(int inputs,List<String> hex)
    {
        
        List<String> verout = new ArrayList<String>();
        //int dec = Convert.HextoInt(hex);
        int combinations = (int)Math.pow(2, inputs);
        int maxdec = (int)Math.pow(2, combinations);
        maxdec--;
        List<Integer> intlist = new ArrayList<Integer>();
        
        for(String hexval:hex)
        {
            intlist.add(Convert.HextoInt(hexval));
        }
        
        for(int intval: intlist)
        {
            if(intval > maxdec)
                intval = maxdec;
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
        modulename +=  "CellVerilog_" + inputs;
        String modulestring = "module " +  modulename +"(output out, input ";
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
        List<String> binTT = new ArrayList<String>();
        for(int intval: intlist)
        {
            binTT.add(Convert.dectoBin(intval, ttpow));
        }
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
        line+= "})";
        verout.add(line);
        int ttlength = binTT.get(0).length();
        
        for(int i=0;i<ttlength;i++)
        {
            line ="               ";
            line += (inputs+ "'b");
            String bini = Convert.dectoBin(i, inputs);
            line +=bini;
            line += (": out = ");
            line += intlist.size() + "'b";
            for(int j=0;j<intlist.size();j++)
            {
                line += binTT.get(j).charAt(i);
            }
            line += ";";
            verout.add(line);
        }
        verout.add("               default: out = 1'b0;");
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
