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
import netsynth.DGate;
import netsynth.DGate.DGateType;
import netsynth.NetSynth;
import netsynth.DWire;
import netsynth.DWire.DWireType;

/**
 *
 * @author prashantvaidyanathan
 */
public class PreCompute {
    
    
    public static List<List<DGate>> parseNetlistFile()
    {
       
        List<List<DGate>> all_netlists = new ArrayList<List<DGate>>();
        List<List<String>> string_netlists = new ArrayList<List<String>>();
        
        // <editor-fold defaultstate="collapsed" desc="Read from Input File"> 
        
        String Filepath="";
        Filepath = PreCompute.class.getClassLoader().getResource(".").getPath();
        
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
                Logger.getLogger(PreCompute.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PreCompute.class.getName()).log(Level.SEVERE, null, ex);
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
        int cnt=1;
        for(int i=0;i<string_netlists.size();i++)
        {
            List<DGate> set = new ArrayList<DGate>();
            for(String xs:string_netlists.get(i))
            {
                DGate xg1 = parseNorGate(xs);
                set.add(xg1);
            }
            if(!set.isEmpty())
            {
                set.get(set.size()-1).output.wtype = DWireType.output;
                //System.out.println("Found" + cnt++);
            }
            
            all_netlists.add(set);
        }
        
        /*for(int i=0;i<all_netlists.size()-1;i++)
        {
            System.out.println("Netlist : "+ cnt++ + "\n");
            for(Gate gg:all_netlists.get(i))
            {
                String ggs = NetSynth.netlist(gg);
                System.out.println(ggs);
            }
            System.out.println("\n\n");
        }*/
        
        return all_netlists;
    }
    
    
    
    
    public static DGate parseNorGate(String xg)
    {
       DGate xgate;
       DWire outw = null;
       List<DWire> inp = new ArrayList<DWire>();
       DGateType gtype = DGateType.NOR2;
       String sub = xg.substring(xg.indexOf("(")+1);
       sub = sub.substring(0,sub.indexOf(")"));
       String[] parts = sub.split(",");
       DWireType xwtype = DWireType.connector;
       for(int i=0;i<parts.length;i++)
       {
           
           parts[i].trim();
           
           if(parts[i].contains("Wire"))
               xwtype = DWireType.connector;
            else if(parts[i].contains("_0"))
               xwtype = DWireType.GND;
            else if(parts[i].contains("_1"))
               xwtype = DWireType.Source;
           else 
               xwtype = DWireType.input;
           
           
           if(i==0)
           {
               if(xwtype == DWireType.GND)
                outw = NetSynth.zero;
               else if(xwtype == DWireType.Source)
                outw = NetSynth.one;
               else
                outw = new DWire(parts[i],xwtype);
           }
           else
           {
               if(xwtype == DWireType.GND)
                   inp.add(NetSynth.zero);
               else if(xwtype == DWireType.Source)
                   inp.add(NetSynth.one);
               else
                   inp.add(new DWire(parts[i],xwtype));
           }
       }
       xgate = new DGate(gtype,inp,outw);
       return xgate;
    }
    
    
    public static String binaryConv(int num)
    {
        String outbin ="";
        return outbin;
    }
   
}
