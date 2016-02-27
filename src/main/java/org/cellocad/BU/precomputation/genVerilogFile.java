/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cellocad.BU.precomputation;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import org.cellocad.BU.parseVerilog.Convert;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.cellocad.BU.parseVerilog.parseVerilogFile;
import org.cellocad.BU.equationSolver.eqNode;
import org.cellocad.BU.equationSolver.eqSolver;
import org.cellocad.BU.equationSolver.eqTree;
import org.cellocad.BU.netsynth.Utilities;

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
    
    
    public static List<String> createVerilogFromEq(List<String> eqs) 
    {
        List<String> veriloglines = new ArrayList<>();
        List<String> outputs = new ArrayList<>();
        List<List<String>> inputSets = new ArrayList<>();
        List<String> truthTables = new ArrayList<>();
        List<String> allInputs = new ArrayList<>();
        
        for(String eq:eqs)
        {
            eqNode node = eqTree.createEqTree(eq);
            outputs.add(node.children.get(0).value);
            inputSets.add(eqTree.getAllTerms(node));
            for(String xstring:eqTree.getAllTerms(node))
            {
                if(!allInputs.contains(xstring))
                {
                    allInputs.add(xstring);
                }
            }
            truthTables.add(eqSolver.solveEquation(node));
            //System.out.println(eqSolver.solveEquation(node));
        }
        
        return getVerilogFromEq(allInputs, inputSets, outputs,truthTables);
    }
    
    
    public static List<String> getVerilogFromEq(List<String> inputs, List<List<String>> inputSet, List<String> outputs, List<String> tt)
    {
        List<String> veriloglines = new ArrayList<>();
        String moduleLine = "module verilogEqn(output ";
        String inputsgrp = "";
        String outputsgrp = "";
        
        for(int i=0;i<(outputs.size()-1);i++)
             outputsgrp += ((outputs.get(i)) + ",") ;
        outputsgrp += outputs.get(outputs.size()-1);
        
        moduleLine += (outputsgrp +", input ");
        
        for(int i=0;i<(inputs.size()-1);i++)
            inputsgrp += ((inputs.get(i)) + ",") ;
        inputsgrp += inputs.get(inputs.size()-1);
        
        moduleLine += (inputsgrp+ ");");
        
        veriloglines.add(moduleLine); //Module Line
        veriloglines.add("  always@("+inputsgrp+")"); // Always line
        veriloglines.add("    begin"); // Begin line
        veriloglines.add("      case({"+inputsgrp+"})");
        
        //Insert Logic to print TruthTable here!
        int pow = (int)Math.pow(2, inputs.size());
        for(int i=0;i<pow;i++)
        {
            String rowLine = "        ";
            rowLine += i+":{"+outputsgrp+"}="+outputs.size()+"'b";
            String rowbool = Convert.dectoBin(i, inputs.size());
            Map<String,Character> inpMap = new HashMap<>();
            for(int j=0;j<rowbool.length();j++)
            {
                inpMap.put(inputs.get(j), rowbool.charAt(j));
            }
            for(int j=0;j<outputs.size();j++)
            {
                String outTT = tt.get(j);
                String specificRow = "";
                for(int k=0;k<inputSet.get(j).size();k++)
                {
                    specificRow += inpMap.get(inputSet.get(j).get(k));
                }
                int indxVal = Convert.bintoDec(specificRow);
                rowLine += outTT.charAt(indxVal);
                
            }
            rowLine += ";";
            veriloglines.add(rowLine);
        }
        String rowLine = "        ";
        rowLine += "default"+":{"+outputsgrp+"}="+outputs.size()+"'b";
        for(String x:outputs)
            rowLine += "0";
        rowLine += ";";
        veriloglines.add(rowLine);
        veriloglines.add("      endcase");
        veriloglines.add("    end");
        veriloglines.add("endmodule");
        
        
        return veriloglines;
    }
    
    
    public static void printverilogfile(List<String> verilogfile)
    {
        for(String verline:verilogfile)
        {
            System.out.println(verline);
        }
    }
    
    
    public static String modifyAssignVerilog(String filepath)
    {
        String newFilePath = "";
        String alllines = parseVerilogFile.verilogFileLines(filepath);
        List<String> inputnames = parseVerilogFile.getInputNames(alllines);
        List<String> outputnames = parseVerilogFile.getOutputNames(alllines);
        String moduleSubstring = alllines.substring(alllines.indexOf("module "),alllines.indexOf(";")+1);
        String modifiedModule = "module newAssignVerilog(";
        String inputSeq="";
        String outputSeq="";
        for(int i=0;i<inputnames.size()-1;i++)
            inputSeq += (inputnames.get(i) + ",");
        inputSeq+= inputnames.get(inputnames.size()-1);
        for(int i=0;i<outputnames.size()-1;i++)
            outputSeq += (outputnames.get(i) + ",");
        outputSeq+= outputnames.get(outputnames.size()-1);
        modifiedModule+= (inputSeq + "," + outputSeq);
        modifiedModule+= "); input " + inputSeq + "; output " + outputSeq + "; " ;
        
        alllines = alllines.replace(moduleSubstring, modifiedModule) + "\n";

        System.out.println(alllines);   
        
        newFilePath = Utilities.getNetSynthResourcesFilepath() + "modifiedForABC.v";
        File newFile = new File(newFilePath);
        try {
            Writer output = new BufferedWriter(new FileWriter(newFile));
            output.write(alllines);
            output.close();
        } catch (IOException ex) {
            Logger.getLogger(genVerilogFile.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println(newFilePath);
        
        return newFilePath;
    }
    
    
    public static String modifyAssignVerilogCode(String alllines)
    {
        String newFilePath = "";
        List<String> inputnames = parseVerilogFile.getInputNames(alllines);
        List<String> outputnames = parseVerilogFile.getOutputNames(alllines);
        String moduleSubstring = alllines.substring(alllines.indexOf("module "),alllines.indexOf(";")+1);
        String modifiedModule = "module newAssignVerilog(";
        String inputSeq="";
        String outputSeq="";
        for(int i=0;i<inputnames.size()-1;i++)
            inputSeq += (inputnames.get(i) + ",");
        inputSeq+= inputnames.get(inputnames.size()-1);
        for(int i=0;i<outputnames.size()-1;i++)
            outputSeq += (outputnames.get(i) + ",");
        outputSeq+= outputnames.get(outputnames.size()-1);
        modifiedModule+= (inputSeq + "," + outputSeq);
        modifiedModule+= "); input " + inputSeq + "; output " + outputSeq + "; " ;
        
        alllines = alllines.replace(moduleSubstring, modifiedModule) + "\n";

        System.out.println(alllines);   
        
        newFilePath = Utilities.getNetSynthResourcesFilepath() + "modifiedForABC.v";
        File newFile = new File(newFilePath);
        try {
            Writer output = new BufferedWriter(new FileWriter(newFile));
            output.write(alllines);
            output.close();
        } catch (IOException ex) {
            Logger.getLogger(genVerilogFile.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println(newFilePath);
        
        return newFilePath;
    }
    
}
