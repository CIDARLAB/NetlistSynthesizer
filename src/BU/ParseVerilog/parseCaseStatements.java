/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BU.ParseVerilog;

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
public class parseCaseStatements {
    
    public static CircuitDetails input3case(String Filepath)
    {
        
         
         List<String> inputs = new ArrayList<String>();
         List<String> outputs = new ArrayList<String>();
         List<String> unknownIO = new ArrayList<String>();
         
         int x = 0;
        
        CircuitDetails circuit = new CircuitDetails();
        
        String path = Filepath;
        
        Filepath = parseCaseStatements.class.getClassLoader().getResource(".").getPath();
         
        if(Filepath.contains("prashant"))
        {
            if(Filepath.contains("build/classes/"))
                Filepath = Filepath.substring(0,Filepath.lastIndexOf("build/classes/")); 
            else if(Filepath.contains("src"))
                Filepath = Filepath.substring(0,Filepath.lastIndexOf("src/"));
            Filepath += "src/BU/ParseVerilog/Verilog.v";
            path = Filepath;
        }
                
        
        
        
        File file = new File(path);
        
        BufferedReader br;
        FileReader fr;
        boolean addCodelines =false;
        String alllines="";
        List<String> filelines = new ArrayList<String>();
        try
        {
            fr = new FileReader(file);
            br = new BufferedReader(fr);
            String line;
            try 
            {
                while((line = br.readLine()) != null )
                {
                    line = line.trim();
                    if(!addCodelines)
                    {
                        if(line.contains("module ") && (!line.startsWith("//")))
                        {
                            if(line.indexOf("module ")>0)
                            {
                                if(((line.charAt((line.indexOf("module "))-1)) == ' '))
                                {
                                    addCodelines = true;
                                }     
                                    
                            }
                            else
                                addCodelines = true;
                        }
                    }
                    if((!line.isEmpty()) && addCodelines && (!line.startsWith("//")))
                    {
                        alllines+= (" " + line); 
                        
                        filelines.add(line);
                        //System.out.println(line.trim());
                    }
                }
            } 
            catch (IOException ex) 
            {
                Logger.getLogger(parseCaseStatements.class.getName()).log(Level.SEVERE, null, ex);
            }
        } 
        catch (FileNotFoundException ex) 
        {
            Logger.getLogger(parseCaseStatements.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //System.out.println(alllines);
        
        //<editor-fold desc="Extract Module IO Contraints">
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
                    //System.out.println(caseNumber);
                    String sNum = xcase.substring(xcase.indexOf("=")+1).trim();
                    int swNumber = Convert.toDec(sNum);
                    //System.out.println(swNumber);
                    truthtable.put(caseNumber, swNumber);
                }
                int numTT = (int) Math.pow(2, caseinp.length);
                
                char[] xbits = new char[numTT];
                for(int i=0;i<numTT;i++)
                {
                    
                    if(truthtable.containsKey(i))
                    {
                        
                        xbits[i] = truthtable.get(i).toString().charAt(0);
                        //System.out.println(i+ " " +xbits[i]);
                        
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
                
                int truthtableval = Convert.bintoDec(new String(xbits)) ;
                circuit.inputgatetable.add(truthtableval);
                
            }
            else
            {
                return null;
            }
            
        }
        
        return circuit;
    }
   
}
