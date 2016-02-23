/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cellocad.BU.precomputation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.cellocad.BU.dom.DGate;
import org.cellocad.BU.dom.DGateType;
import org.cellocad.BU.netsynth.NetSynth;
import org.cellocad.BU.dom.DWire;
import org.cellocad.BU.dom.DWireType;
import org.cellocad.BU.netsynth.Global;
import org.cellocad.BU.netsynth.Utilities;
import org.cellocad.BU.subcircuit.SubcircuitLibrary;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author prashantvaidyanathan
 */
public class PreCompute {
    
    public static List<SubcircuitLibrary> getCircuitLibrary(JSONArray jsonLib){
        List<String> inputOrder = new ArrayList<String>();
        inputOrder.add("a");
        inputOrder.add("b");
        inputOrder.add("c");
        
       
        List<SubcircuitLibrary> library = new ArrayList<SubcircuitLibrary>();
        List<List<String>> string_netlists = new ArrayList<List<String>>();
        
        try {
            for(int i=0;i<jsonLib.length();i++){
                JSONObject obj;
                obj = (JSONObject) jsonLib.get(i);
                String output = "";
                List<String> inputs = new ArrayList<String>();
                if(obj.has("outputs")){
                    output = (new JSONArray(obj.get("outputs").toString()).get(0)).toString().trim();
                }
                if(obj.has("inputs")){
                    JSONArray inputList = new JSONArray(obj.get("inputs").toString());
                    for(int j=0;j<inputList.length();j++){
                        inputs.add(inputList.get(j).toString().trim());
                    }
                }
                List<DGate> netlist = new ArrayList<DGate>();
                if(obj.has("netlist")){
                    JSONArray netlistJSON = new JSONArray(obj.get("netlist").toString());
                    for(int j=0;j<netlistJSON.length();j++){
                        netlist.add(stringToDGate(netlistJSON.get(j).toString(),inputs,output));
                    }
                }
                NetSynth.rewireNetlist(netlist);
                
                SubcircuitLibrary subcirc = new SubcircuitLibrary(netlist,inputs,output);
                
                //System.out.println("SubCirc:\n"+ subcirc.printSubcircuit());
                library.add(subcirc);
            }
            //System.out.println("JSON Array " + jsonLib);
        } catch (JSONException ex) {
            Logger.getLogger(PreCompute.class.getName()).log(Level.SEVERE, null, ex);
            //return null;
        }
        
        return library;
    }
    
    public static List<SubcircuitLibrary> getCircuitLibrary(String ucfFileContent){
        List<String> inputOrder = new ArrayList<String>();
        inputOrder.add("a");
        inputOrder.add("b");
        inputOrder.add("c");
        
       
        List<SubcircuitLibrary> library = new ArrayList<SubcircuitLibrary>();
        List<List<String>> string_netlists = new ArrayList<List<String>>();
        
        // <editor-fold defaultstate="collapsed" desc="Read from Input File"> 
        JSONArray jsonLib;
        try {
            jsonLib = new JSONArray(ucfFileContent);
            for(int i=0;i<jsonLib.length();i++){
                JSONObject obj;
                obj = (JSONObject) jsonLib.get(i);
                String output = "";
                List<String> inputs = new ArrayList<String>();
                if(obj.has("outputs")){
                    output = (new JSONArray(obj.get("outputs").toString()).get(0)).toString().trim();
                }
                if(obj.has("inputs")){
                    JSONArray inputList = new JSONArray(obj.get("inputs").toString());
                    for(int j=0;j<inputList.length();j++){
                        inputs.add(inputList.get(j).toString().trim());
                    }
                }
                List<DGate> netlist = new ArrayList<DGate>();
                if(obj.has("netlist")){
                    JSONArray netlistJSON = new JSONArray(obj.get("netlist").toString());
                    for(int j=0;j<netlistJSON.length();j++){
                        netlist.add(stringToDGate(netlistJSON.get(j).toString(),inputs,output));
                    }
                }
                NetSynth.rewireNetlist(netlist);
                
                SubcircuitLibrary subcirc = new SubcircuitLibrary(netlist,inputs,output);
                
                //System.out.println("SubCirc:\n"+ subcirc.printSubcircuit());
                library.add(subcirc);
            }
            //System.out.println("JSON Array " + jsonLib);
        } catch (JSONException ex) {
            Logger.getLogger(PreCompute.class.getName()).log(Level.SEVERE, null, ex);
            //return null;
        }
        
        return library;
    }
      
    public static DGate stringToDGate(String dgate,List<String> inputorder, String output){
        DGate gate = new DGate();
        String temp = dgate;
        if(dgate.startsWith(DGateType.AND.toString())){
            gate.gtype = DGateType.AND;
        }
        else if(dgate.startsWith(DGateType.BUF.toString())){
            gate.gtype = DGateType.BUF;
        }
        else if(dgate.startsWith(DGateType.NAND.toString())){
            gate.gtype = DGateType.NAND;    
        }
        else if(dgate.startsWith(DGateType.NOR.toString())){
            gate.gtype = DGateType.NOR;
        }
        else if(dgate.startsWith(DGateType.NOT.toString())){
            gate.gtype = DGateType.NOT;
        }
        else if(dgate.startsWith(DGateType.OUTPUT_OR.toString())){
            gate.gtype = DGateType.OUTPUT_OR;
        }
        else if(dgate.startsWith(DGateType.OR.toString())){
            gate.gtype = DGateType.OR;
        }
        else if(dgate.startsWith(DGateType.XNOR.toString())){
            gate.gtype = DGateType.XNOR;
        }
        else if(dgate.startsWith(DGateType.XOR.toString())){
            gate.gtype = DGateType.XOR;
        }
        else{
            return gate;
        }
        if(dgate.contains("(") && dgate.contains(")"))
        {
            String ioports = dgate.substring(dgate.indexOf("(")+1, dgate.lastIndexOf(")"));
            String[] io = ioports.trim().split(",");
            if(output.equals(io[0].trim())){
                gate.output = new DWire(io[0].trim(),DWireType.output);
            }
            else{
                gate.output = new DWire(io[0].trim(),DWireType.connector);
            }
            for(int j=1;j<io.length;j++){
                if(inputorder.contains(io[j].trim())){
                    gate.input.add(new DWire(io[j].trim(),DWireType.input));
                }
                else{
                    gate.input.add(new DWire(io[j].trim(),DWireType.connector));
                }
            }
                 
        }
        else
        {
            return gate;
        }
        
        return gate;
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
        Global.one = new DWire("_one", DWireType.Source);
        Global.zero = new DWire("_zero", DWireType.GND);
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
           
           parts[i] = parts[i].trim();
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
                outw = Global.zero;
               else if(xwtype == DWireType.Source)
                outw = Global.one;
               else
                outw = new DWire(parts[i].trim(),xwtype);
           }
           else
           {
               if(xwtype == DWireType.GND)
                   inp.add(Global.zero);
               else if(xwtype == DWireType.Source)
                   inp.add(Global.one);
               else
                   inp.add(new DWire(parts[i].trim(),xwtype));
           }
       }
       xgate = new DGate(gtype,inp,outw);
       return xgate;
       }
       
    }
    
    
   
   
}
