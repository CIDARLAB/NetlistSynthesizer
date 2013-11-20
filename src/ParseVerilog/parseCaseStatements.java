/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ParseVerilog;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author prashantvaidyanathan
 */
public class parseCaseStatements {
    public static int input3case(String Filepath)
    {
        
         Filepath = parseCaseStatements.class.getClassLoader().getResource(".").getPath();
         
         List<String> inputs = new ArrayList<String>();
         List<String> outputs = new ArrayList<String>();
         List<String> unknownIO = new ArrayList<String>();
         
         int x = 0;
        
         
        if(Filepath.contains("build/classes/"))
            Filepath = Filepath.substring(0,Filepath.lastIndexOf("build/classes/")); 
        else if(Filepath.contains("src"))
            Filepath = Filepath.substring(0,Filepath.lastIndexOf("src/"));
       
        Filepath += "src/ParseVerilog/Verilog.v";
        
        File file = new File(Filepath);
        
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
        moduleString = moduleString.trim();
        moduleString = moduleString.substring((moduleString.indexOf("(")+1),moduleString.indexOf(")"));
        
        System.out.println(moduleString);
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
        System.out.println("Inputs :" + inputs.size() + "\nOutputs :"+ outputs.size() + "\nUnknown IOs :" + unknownIO.size());
        
        System.out.println("Inputs :");
        for(String xi:inputs)
        {
            System.out.println(xi);
        }
        
        System.out.println("Outputs :");
        for(String xi:outputs)
        {
            System.out.println(xi);
        }
        
        //</editor-fold>
        
        
                
        
        
        
        
        return x;
    }
    
    
}
