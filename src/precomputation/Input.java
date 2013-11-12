/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package precomputation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import netsynth.Gate;
import netsynth.Gate.GateType;
import netsynth.Wire;

/**
 *
 * @author prashantvaidyanathan
 */
public class Input {
    
    
    public static List<List<Gate>> parseNetlistFile()
    {
       
        List<List<Gate>> all_netlists = new ArrayList<List<Gate>>();
        List<List<String>> string_netlists = new ArrayList<List<String>>();
        
        // <editor-fold defaultstate="collapsed" desc="Read from Input File"> 
        
        String Filepath="";
        Filepath = Input.class.getClassLoader().getResource(".").getPath();
        
        if(Filepath.contains("build/classes/"))
            Filepath = Filepath.substring(0,Filepath.lastIndexOf("build/classes/")); 
        else if(Filepath.contains("src"))
            Filepath = Filepath.substring(0,Filepath.lastIndexOf("src/"));
       
        Filepath += "src/precomputation/allnetlists.txt";
       
        File file = new File(Filepath);
        
         BufferedReader br;
         FileReader fr;
         List<String> filelines = new ArrayList<String>();
         try {
            
            fr = new FileReader(file);           
            br = new BufferedReader(fr);
            
            String line;
            try {
                while((line = br.readLine()) != null )
                {
                    filelines.add(line);
                }
            } catch (IOException ex) {
                Logger.getLogger(Input.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Input.class.getName()).log(Level.SEVERE, null, ex);
        }
         
        // </editor-fold> 
         List<String> snetlist;
        int count =0;
        String nline = "";
        for(int i=0;i<filelines.size();i++)
        {
           int k;
            if(filelines.get(i).contains("Expression for "))
            {
                k=i+2;
                snetlist = new ArrayList<String>();
                while(!filelines.get(k).equals(""))
                {
                    nline = filelines.get(k).trim();
                    snetlist.add(nline);
                    k++; 
                }
                string_netlists.add(snetlist);
            }
        }
         
        for(String xs:string_netlists.get(1))
        {
            System.out.println(xs);
        }
         
        return all_netlists;
    }
    
    public static Gate parseNorGate(String x)
    {
       Gate xgate = null ;
        Wire outw = null;
        List<Wire> inp = new ArrayList<Wire>();
       //xgate = new Gate(GateType.NOR2, inp, outw);
        return xgate;
    }
    
    public static String binaryConv(int num)
    {
        String outbin ="";
        return outbin;
    }
   
}
