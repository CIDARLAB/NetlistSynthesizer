/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BU.precomputation;

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
import BU.netsynth.DGate;
import BU.netsynth.DGateType;
import BU.netsynth.NetSynth;
import BU.netsynth.DWire;
import BU.netsynth.DWireType;

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
       
        if(Filepath.contains("prashant"))
        {
            Filepath += "src/BU/precomputation/allnetlists.txt";
        }
        else
        {
            Filepath += "BU/precomputation/allnetlists.txt";
        }
        
        
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
                String expression = filelines.get(i).substring(filelines.get(i).indexOf(":")+2);
                
                k=i+2;
                
                snetlist = new ArrayList<String>();
                while(!filelines.get(k).equals(""))
                {
                    nline = filelines.get(k).trim();
                    snetlist.add(nline);
                    k++; 
                }
                if(snetlist.isEmpty() )    
                {
                    snetlist.add(expression);
                }
                
                    string_netlists.add(snetlist);
                
            }
        }
        int cnt=1;
        for(int i=0;i<string_netlists.size();i++)
        {
            List<DGate> set = new ArrayList<DGate>();
            List<DGate> stset = new ArrayList<DGate>();
            
            for(String xs:string_netlists.get(i))
            {
                DGate xg1 = parseNorGate(xs);
                set.add(xg1);
            }
            if(!set.isEmpty())
            {
                stset = stitchNetlist(set); 
                stset.get(stset.size()-1).output.wtype = DWireType.output;
                //System.out.println("Found" + cnt++);
                stset = doubleinputnortonot(stset);
                all_netlists.add(stset);
            }
            else  
                all_netlists.add(set);
        }
        

        
        return all_netlists;
    }
    
    public static List<DGate> doubleinputnortonot(List<DGate> netlistinp)
    {
        for(int i=0;i<netlistinp.size();i++)
        {
            if(netlistinp.get(i).gtype.equals(DGateType.NOR))
            {
                int norflag =0;
                int pos =0;
                for(int j=0;j<netlistinp.get(i).input.size();j++)
                {
                    if(netlistinp.get(i).input.get(j).wtype.equals(DWireType.GND))
                    {
                        pos = j;
                        norflag = 1;
                        break;
                    }
                }
                if(norflag ==1)
                {
                    netlistinp.get(i).input.remove(pos);
                    netlistinp.get(i).gtype = DGateType.NOT;
                }
            }
        }
        return netlistinp;
    }
    
    public static List<DGate> stitchNetlist(List<DGate> set)
    {
        List<DGate> stitchedset = new ArrayList<DGate>();
        HashMap<String,DWire> input = new HashMap<String,DWire>();
        HashMap<String,DWire> connectors = new HashMap<String,DWire>();
        
        HashMap<String,DWire> allwires = new HashMap<String,DWire>();
        List<DWire> wires = new ArrayList<DWire>();
        
        
        for(DGate dg:set)
        {
            List<DWire> ninp = new ArrayList<DWire>();
            DWire noup = new DWire();
            for(DWire dw:dg.input)
            {
                //if(dw.wtype == DWireType.connector)
                //{
                    int flag =0;
                    for(DWire sw:wires)
                    {
                        if(sw.name.trim().equals(dw.name.trim()))
                        {
                            ninp.add(sw);
                            dw = sw;
                            flag =1;
                            break;
                        }
                    }
                    if(flag ==0)
                    {
                        ninp.add(dw);
                        wires.add(dw);
                    }
                //}
            }
            DWire dw = dg.output;
            //if(dw.wtype == DWireType.connector)
            //{
                int flag =0;
                for(DWire sw:wires)
                {
                    if(sw.name.trim().equals(dw.name.trim()))
                    {
                        noup = sw;
                        dw = sw;
                        flag =1;
                        break;
                    }
                }
                if(flag ==0)
                {
                    noup = dw;
                    wires.add(dw);
                }
            //}
            dg.input = ninp;
            dg.output = noup;
            stitchedset.add(dg);
        }
        
        /*for(DGate dg:set)
        {
            for(DWire dw:dg.input)
            {
                if(dw.wtype == DWireType.Source ||dw.wtype == DWireType.GND || dw.wtype == DWireType.output )
                {
                }
                else if(dw.wtype == DWireType.input)
                {
                    DWire x = input.get(dw.name.trim());
                    if(x==null) 
                        input.put(dw.name.trim(), dw);
                    else
                        dw = input.get(dw.name.trim());
                }
                else
                {
                    DWire x = connectors.get(dw.name.trim());
                    if(x==null)
                        connectors.put(dw.name.trim(), dw);
                    else
                        dw = connectors.get(dw.name.trim());
                }
            }
            
            DWire dw = dg.output;
            if(dw.wtype == DWireType.Source ||dw.wtype == DWireType.GND || dw.wtype == DWireType.output )
            {
            }
            else if(dw.wtype == DWireType.input)
            {
                    DWire x = input.get(dw.name.trim());
                    if(x==null)
                        input.put(dw.name.trim(), dw);
                    else
                        dw = input.get(dw.name.trim());
             }
             else
             {
                DWire x = connectors.get(dw.name.trim());
                if(x==null)
                    connectors.put(dw.name.trim(), dw);
                else
                    dw = connectors.get(dw.name.trim());
             }
            stitchedset.add(dg);
        }*/
        
     
        return stitchedset;
    }
    
    public static DGate parseNorGate(String xg)
    {
       if(!xg.contains("NOR"))
       {
           DGate bufgate = new DGate();
           bufgate.gtype = DGateType.BUF;
           bufgate.input.add(new DWire(xg,DWireType.input));
           DWire bufout = new DWire("X",DWireType.output);
           bufgate.output = bufout;
           return bufgate;
       }
       else
       {
           DGate xgate;
       DWire outw = null;
       List<DWire> inp = new ArrayList<DWire>();
       DGateType gtype = DGateType.NOR;
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
                outw = new DWire(parts[i].trim(),xwtype);
           }
           else
           {
               if(xwtype == DWireType.GND)
                   inp.add(NetSynth.zero);
               else if(xwtype == DWireType.Source)
                   inp.add(NetSynth.one);
               else
                   inp.add(new DWire(parts[i].trim(),xwtype));
           }
       }
       xgate = new DGate(gtype,inp,outw);
       return xgate;
       }
       
    }
    
    
   
   
}
