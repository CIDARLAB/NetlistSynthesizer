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
                    filelines.add(line.trim());
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
