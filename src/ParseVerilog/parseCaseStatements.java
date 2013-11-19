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
        
        if(Filepath.contains("build/classes/"))
            Filepath = Filepath.substring(0,Filepath.lastIndexOf("build/classes/")); 
        else if(Filepath.contains("src"))
            Filepath = Filepath.substring(0,Filepath.lastIndexOf("src/"));
       
        Filepath += "src/ParseVerilog/Verilog.v";
        
        File file = new File(Filepath);
        
        BufferedReader br;
        FileReader fr;
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
                    if(!line.trim().isEmpty())
                    {
                        filelines.add(line.trim());
                        System.out.println(line.trim());
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
        
        int x = 0;
                
        for(int i=0;i<filelines.size();i++)
        {
            String line = filelines.get(i);
            if(line.contains("input "))
            {
                
            }
        }
        return x;
    }
    
    
}
