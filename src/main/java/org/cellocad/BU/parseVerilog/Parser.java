/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cellocad.BU.parseVerilog;

import org.cellocad.BU.parseVerilog.VerilogDetails.ioType;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author prashantvaidyanathan
 */
public class Parser {
    
    public static HashMap<String,Integer> keywords = new HashMap<String,Integer>();
    
    public static void addkeywords()
    {
        keywords = new HashMap<String,Integer>();
        
        keywords.put("always", 1);
        keywords.put("and", 1);
        keywords.put("assign", 1);
        keywords.put("automatic", 1);
        
        keywords.put("begin", 1);
        keywords.put("buf", 1);
        keywords.put("bufif0", 1);
        keywords.put("bufif1", 1);
        
        keywords.put("case", 1);
        keywords.put("casex", 1);
        keywords.put("casez", 1);
        keywords.put("cell", 1);
        keywords.put("cmos", 1);
        keywords.put("config", 1);
        
        keywords.put("deassign", 1);
        keywords.put("default", 1);
        keywords.put("defparam", 1);
        keywords.put("design", 1);
        keywords.put("disable", 1);
        
        keywords.put("edge", 1);
        keywords.put("else", 1);
        keywords.put("end", 1);
        keywords.put("endcase", 1);
        keywords.put("endconfig", 1);
        keywords.put("endfunction", 1);
        keywords.put("endgenerate", 1);
        keywords.put("endmodule", 1);
        keywords.put("endprimitive", 1);
        keywords.put("endspecify", 1);
        keywords.put("endtable", 1);
        keywords.put("endtask", 1);
        keywords.put("event", 1);
        
        keywords.put("for", 1);
        keywords.put("force", 1);
        keywords.put("forever", 1);
        keywords.put("fork", 1);
        keywords.put("function", 1);
        
        keywords.put("generate", 1);
        keywords.put("genvar", 1);
        
        keywords.put("highz0", 1);
        keywords.put("highz1", 1);
        
        keywords.put("if", 1);
        keywords.put("ifnone", 1);
        keywords.put("incdir", 1);
        keywords.put("include", 1);
        keywords.put("initial", 1);
        keywords.put("inout", 1);
        keywords.put("input", 1);
        keywords.put("instance", 1);
        keywords.put("integer", 1);
        
        keywords.put("join", 1);
        
        keywords.put("large", 1);
        keywords.put("liblist", 1);
        keywords.put("library", 1);
        keywords.put("localparam", 1);
        
        keywords.put("macromodule", 1);
        keywords.put("medium", 1);
        keywords.put("module", 1);
        
        keywords.put("nand", 1);
        keywords.put("negedge", 1);
        keywords.put("nmos", 1);
        keywords.put("nor", 1);
        keywords.put("noshowcancelled", 1);
        keywords.put("not", 1);
        keywords.put("notif0", 1);
        keywords.put("notif1", 1);
        
        keywords.put("or", 1);
        keywords.put("output", 1);
        
        keywords.put("parameter", 1);
        keywords.put("pmos", 1);
        keywords.put("posedge", 1);
        keywords.put("primitive", 1);
        keywords.put("pull0", 1);
        keywords.put("pull1", 1);
        keywords.put("pulldown", 1);
        keywords.put("pullup", 1);
        keywords.put("pulsestyle_onevent", 1);
        keywords.put("pulsestyle_ondetect", 1);
        
        keywords.put("rcmos", 1);
        keywords.put("real", 1);
        
        keywords.put("realtime", 1);
        keywords.put("reg", 1);
        keywords.put("release", 1);
        keywords.put("repeat", 1);
        keywords.put("rnmos", 1);
        keywords.put("rpmos", 1);
        keywords.put("rtran", 1);
        keywords.put("rtranif0", 1);
        keywords.put("rtranif1", 1);
        
        keywords.put("scalared", 1);
        keywords.put("showcancelled", 1);
        keywords.put("signed", 1);
        keywords.put("small", 1);
        keywords.put("specify", 1);
        keywords.put("specparam", 1);
        keywords.put("strong0", 1);
        keywords.put("strong1", 1);
        keywords.put("supply0", 1);
        keywords.put("supply1", 1);
        
        keywords.put("table", 1);
        keywords.put("task", 1);
        keywords.put("time", 1);
        keywords.put("tran", 1);
        keywords.put("tranif0", 1);
        keywords.put("tranif1", 1);
        keywords.put("tri", 1);
        keywords.put("tri0", 1);
        keywords.put("tri1", 1);
        keywords.put("triand", 1);
        keywords.put("trior", 1);
        keywords.put("trireg", 1);
        
        keywords.put("unsigned", 1);
        keywords.put("use", 1);
        keywords.put("uwire", 1);
        
        keywords.put("vectored", 1);
        
        keywords.put("wait", 1);
        keywords.put("wand", 1);
        keywords.put("weak0", 1);
        keywords.put("weak1", 1);
        keywords.put("while", 1);
        keywords.put("wire", 1);
        
        keywords.put("wor", 1);
        
        keywords.put("xnor", 1);
        keywords.put("xor", 1);
        
        
        
    }
    
    public static CircuitDetails findIO(String alllines)
    {
        addkeywords();
        List<String> inputs = new ArrayList<String>();
        List<String> outputs = new ArrayList<String>();
        List<String> unknownIO = new ArrayList<String>();
         
        CircuitDetails circuit = new CircuitDetails();
        
        String moduleString = alllines.substring(alllines.indexOf(" module "),alllines.indexOf(";"));
        alllines = alllines.substring((alllines.indexOf(moduleString) + moduleString.length()+1), alllines.indexOf(" endmodule"));
        alllines = alllines.trim();
        moduleString = moduleString.trim();
        moduleString = moduleString.substring((moduleString.indexOf("(")+1),moduleString.indexOf(")"));
        //System.out.println(alllines);
        
        //System.out.println(moduleString);
        String[] modulePieces = moduleString.split(",");
        for(int i=0;i<modulePieces.length;i++)
        {
            String IO = modulePieces[i].trim();
            if(IO.contains("input "))
            {
                IO = IO.substring(IO.indexOf("input ")+6);
                IO = IO.trim();
                inputs.add(IO);
            }
            else if(IO.contains("output "))
            {
                IO = IO.substring(IO.indexOf("output ")+7);
                IO = IO.trim();
                outputs.add(IO);
            }
            else
            {
                unknownIO.add(IO);                
            }
        }
        //System.out.println(alllines);
        //</editor-fold>
        
        List<String> VerilogLines = new ArrayList<String>();
        String temp="";
        int caseLocation=-1;
        int cnt =0;
        while(alllines.contains(";") || alllines.length()>0)
        {
            
            if(alllines.startsWith("always ") || alllines.startsWith("always@"))
            {
                if(alllines.contains(" begin "))
                {
                    int lastbeginIndx=0;
                    if((alllines.lastIndexOf(" end")+4) == alllines.length())
                    {
                        temp = alllines.substring(0, alllines.lastIndexOf(" end")+4);
                        VerilogLines.add(temp);
                        alllines = alllines.substring(alllines.lastIndexOf(" end")+4);
                    }
                    else
                    {
                        temp = alllines.substring(0, alllines.lastIndexOf(" end ")+5);
                        VerilogLines.add(temp);
                        alllines = alllines.substring(alllines.lastIndexOf(" end ")+5);
                    }
                if(temp.contains(" endcase "))
                    caseLocation = cnt;
                alllines = alllines.trim();
                }
                
            }
            else if(alllines.contains(";"))
            {
                //System.out.println(alllines);
                VerilogLines.add(alllines.substring(0,alllines.indexOf(";")));
                alllines = alllines.substring(alllines.indexOf(";")+1);
                alllines = alllines.trim();
            }    
            else
            {
                VerilogLines.add(alllines);
                alllines = "";
            }
           
            cnt++;
        }
        
        if(caseLocation == -1)
            System.out.println("No case statements");
        
        else
        {
            String tempcase = VerilogLines.get(caseLocation);
            //List<String> caseStatements = new ArrayList<String>();
            String caseblock ="";
            String caseparam = "";
            String[] caseStatements;
            boolean unusualcase = false;
            
            if(tempcase.contains("case("))
            {
                caseblock = tempcase.substring(tempcase.indexOf("case(")+4);
                caseblock = caseblock.trim();
            }
            else if(tempcase.contains("case "))
            {
               
                //System.out.println(tempcase);
                caseblock = tempcase.substring(tempcase.indexOf("case ")+5);
                caseblock = caseblock.trim();
                
            }
            else
            {
                unusualcase = true;
                System.out.println("unusual Case Statement!!\n"+tempcase);
            }
            
            if(!unusualcase)
            {
                caseparam = caseblock.substring(caseblock.indexOf("(")+1,caseblock.indexOf(")"));
                caseblock = caseblock.substring(caseblock.indexOf(")")+1);
                caseblock = caseblock.substring(0,caseblock.indexOf("endcase"));
                caseblock = caseblock.trim();
                caseStatements = caseblock.split(";");
                
                HashMap<Integer,Integer> truthtable = new HashMap<Integer,Integer>();
                caseparam = caseparam.substring(caseparam.indexOf("{")+1, caseparam.indexOf("}"));
                
                String[] caseinp = caseparam.split(",");
                for(int i=0;i<caseinp.length;i++)
                {
                    circuit.inputNames.add(caseinp[i].trim());
                }
                
                String output = caseStatements[0].substring(caseStatements[0].indexOf(":")+1,caseStatements[0].indexOf("="));
                output = output.trim();
                circuit.outputNames.add(output);
                for(int i=0;i<caseStatements.length;i++)
                {
                    String xcase = caseStatements[i].trim();
                
                    String cNum = xcase.substring(0,xcase.indexOf(":")).trim();
                    int caseNumber = Convert.toDec(cNum);
                    String sNum = xcase.substring(xcase.indexOf("=")+1).trim();
                    int swNumber = Convert.toDec(sNum);
                    truthtable.put(caseNumber, swNumber);
                }
                int numTT = (int) Math.pow(2, caseinp.length);
                
                char[] xbits = new char[numTT];
                for(int i=0;i<numTT;i++)
                {
                    
                    if(truthtable.containsKey(i))
                    {
                     
                        xbits[i] = truthtable.get(i).toString().charAt(0);
                    }
                    else
                    {
                        if(truthtable.containsKey(-2))
                        {
                            xbits[i] = truthtable.get(-2).toString().charAt(0);
                        }
                        else
                        {
                            xbits[i] = '0';
                        }
                    }
                }
                //int truthtableval = Convert.bintoDec(new String(xbits)) ;
                String finaltt = new String(xbits);
                circuit.truthTable.add(finaltt);
                //System.out.println(truthtableval);
                
            }
            else
            {
                return null;
            }
                
            
        }
        
        return circuit;
        
    }
    
    
    public static void testfunction(String line)
    {
        addkeywords();
        System.out.println(line);
        List<String> inputs = new ArrayList<String>();
        List<String> outputs = new ArrayList<String>();
        List<String> uIO = new ArrayList<String>();
        boolean iflag=false;
        boolean oflag=false;
        ioType iowire = null;
        int isize =1;
        String temp = line;
        while(temp.contains("input "))
        {
           int sIndx =  temp.indexOf("input ");
           if(sIndx != 0)
           {
               if(!(temp.charAt(sIndx-1) != ' '|| temp.charAt(sIndx-1) != ',' || temp.charAt(sIndx-1) != ';'|| temp.charAt(sIndx-1) != '('))
               {
                   iflag =true;
               }
               else
               {
                   iflag = false;
                   break;
               }
           }
           temp = temp.substring(sIndx+5);
        }
        
        temp = line;
        while(temp.contains("output "))
        {
           int sIndx =  temp.indexOf("output ");
           if(sIndx != 0)
           {
               
               if(!(temp.charAt(sIndx-1) == ' ' || temp.charAt(sIndx-1) == ',' || temp.charAt(sIndx-1) == ';' || temp.charAt(sIndx-1) == '('))
               {
                   oflag =true;
               }
               else
               {
                   oflag = false;
                   break;
               }
           }
           temp = temp.substring(sIndx+6);
        }
    
        if(iflag && oflag)
        {
            System.out.println("No input or output declaration detected..");
        }
        
        if(!iflag)
        {
            
            System.out.println("Inputs exist : " + line.substring(line.indexOf("input ")+5));
            temp = line;
            
            int count =-1;
            
            
                while(temp.contains("input "))
                {
                    count++;
                    int sIndx =  temp.indexOf("input ");
                    String inptemp="";
                    if(sIndx != 0)
                    {
                        if(!(temp.charAt(sIndx-1) != ' '|| temp.charAt(sIndx-1) != ',' || temp.charAt(sIndx-1) != ';'|| temp.charAt(sIndx-1) != '('))
                        {
                            temp = temp.substring(sIndx+5);
                            continue;
                        }
                        else
                        {
                            inptemp = temp.substring(sIndx+5);
                        }
                    }
                    else
                    {
                            inptemp = temp.substring(sIndx+5);
                    }
                    
                    if(inptemp.contains(","))
                    {
                        String[] inp_parts = inptemp.split(",");
                        for(int ii=0;ii<inp_parts.length;ii++)
                        {
                            String tmp = inp_parts[ii].trim();
                            if(tmp.contains(" "))
                            {//Work on this part
                                String[] spc = tmp.split(" ");
                                String firstspace = spc[0].trim();
                                if(keywords.containsKey(firstspace))
                                {
                                    if(firstspace.equals("wire"))
                                    {
                                        inputs.add(spc[1]);
                                    }
                                    else
                                    {
                                        //System.out.println(inptemp);
                                        temp = temp.substring(inptemp.indexOf(firstspace));
                                        //System.out.println(temp);
                                        break;
                                    }   
                                }
                                else
                                {
                                        inputs.add(firstspace);  
                                }
                                
                            }
                            else
                            {
                                if(tmp.contains(")"))
                                {
                                    tmp = tmp.substring(0,tmp.indexOf(")"));
                                    tmp = tmp.trim();
                                }
                                if(tmp.contains(";"))
                                {
                                    tmp = tmp.substring(0,tmp.indexOf(";"));
                                    tmp = tmp.trim();
                                }    
                                inputs.add(tmp);
                                //System.out.println(tmp);
                                
                            }
                        }
                    }
                    else
                    {
                       if(inptemp.contains(")"))
                       {
                           //Input is from Module declaration
                           String tmp = inptemp.substring(0,inptemp.indexOf(")"));
                           tmp = tmp.trim();
                           inputs.add(tmp);
                           //System.out.println(tmp);
                       }
                       else
                       {
                           String tmp = inptemp.substring(0,inptemp.indexOf(";"));
                           tmp = tmp.trim();
                           inputs.add(tmp);
                           //System.out.println(tmp);
                       }
                    }
                    
                    temp = temp.substring(sIndx+5);
                }
                
                for(String s:inputs)
                    System.out.println(s);
                
                
        }
        
        if(!oflag)
        {
            System.out.println(line.substring(line.indexOf("output ")+6));    
        
        }
    }
    
    
    
    
}
