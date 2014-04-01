/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BU.booleanLogic;

import BU.ParseVerilog.Convert;
import BU.ParseVerilog.Espresso;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import BU.netsynth.DGate;
import BU.netsynth.DWire;
import BU.netsynth.DWire.DWireType;
import BU.netsynth.DWire.DWireValue;
import BU.netsynth.NetSynth;

/**
 *
 * @author prashantvaidyanathan
 */
public class BooleanSimulator {
    
    public static void bfunction(DGate gate)
    {
        switch(gate.gtype)
        {
            case AND2 : BooleanFunctions.bAND(gate);
                        break;
            case OR2 : BooleanFunctions.bOR(gate);
                        break;
            case NOR2 : BooleanFunctions.bNOR(gate);
                        break;
            case NAND2 : BooleanFunctions.bNAND(gate);
                        break;
            case NOT : BooleanFunctions.bNOT(gate);
                        break;
            case XOR2 : BooleanFunctions.bXOR(gate);
                        break;
            case XNOR2 : BooleanFunctions.bXNOR(gate);
                        break;
        }
    }
    
    public static String bpermute(List<DGate> netlist)
    {
        //DWireValue outvalue = DWireValue._x;
        String output = "";
        HashMap <String,DWire> inputsW = new HashMap<String,DWire>();
        List<DWire> inputL = new ArrayList<DWire>();
        
        for(DGate dg:netlist)
        {
            for(DWire dw:dg.input)
            {
                if(dw.wtype == DWireType.input)
                {
                    if(!inputsW.containsKey(dw.name.trim()))
                    {   
                        inputL.add(dw);
                       
                        inputsW.put(dw.name.trim(),dw);}
                    }
            }
        }
        
        HashMap <String,Integer> inputWires = new HashMap<String,Integer>();
        int inpcnt =0;
        for(DWire dwl:inputL)
        {
            inputWires.put(dwl.name.trim(), inpcnt);
            inpcnt++;
        }
        
        /*Iterator it = inputsW.entrySet().iterator();
        while(it.hasNext())
        {
            Map.Entry pair = (Map.Entry)it.next();
            String val = (String) pair.getKey();
            System.out.println(val);
            inputWires.put(val, inpcnt);
            inpcnt++;
            //DWire temp = (DWire)pair.getValue();
        }*/
        
        int inpsize = inputWires.size();
        int inppow = (int) Math.pow(2, inpsize); 
        
        for(int i=0;i<inppow;i++)
        {
            String xi = Convert.dectoBin(i, inpsize);
            for(DGate ng:netlist)
            {
                for(int j=0;j<ng.input.size();j++)
                {
                    DWire dw = ng.input.get(j);
                    if(dw.wtype == DWireType.input)
                    {
                        int indx = inputWires.get(dw.name.trim());
                        if(xi.charAt(indx) == '0')
                            ng.input.get(j).wValue = DWireValue._0;
                        else if(xi.charAt(indx) == '1')
                            ng.input.get(j).wValue = DWireValue._1;
                    }
                    else if(dw.wtype == DWireType.Source)
                    {
                        ng.input.get(j).wValue = DWireValue._1;
                    }
                    else if(dw.wtype == DWireType.GND)
                    {
                        ng.input.get(j).wValue = DWireValue._0;
                    }
                    
                
                }
                bfunction(ng);
                
            }
            
            //int 
            
            DWireValue finaldw = netlist.get(netlist.size()-1).output.wValue; 
            if(finaldw == DWireValue._0)
                output += "0";
            else if(finaldw == DWireValue._1)
                output += "1";
            else if(finaldw == DWireValue._x)
                output += "-";
            
        }
        
        return output;
    }
    
    
    public static String bpermuteEsynth(List<DGate> netlist)
    {
        //DWireValue outvalue = DWireValue._x;
        String output = "";
        HashMap <String,DWire> inputsW = new HashMap<String,DWire>();
        List<DWire> inputL = new ArrayList<DWire>();
        
        for(DGate dg:netlist)
        {
            for(DWire dw:dg.input)
            {
                if(dw.wtype == DWireType.input)
                {
                    if(!inputsW.containsKey(dw.name.trim()))
                    {   
                        inputL.add(dw);
                       
                        inputsW.put(dw.name.trim(),dw);}
                    }
            }
        }
        
        HashMap <String,Integer> inputWires = new HashMap<String,Integer>();
        int inpcnt =0;
        for(DWire dwl:inputL)
        {
            inputWires.put(dwl.name.trim(), inpcnt);
            inpcnt++;
        }
        
        /*Iterator it = inputsW.entrySet().iterator();
        while(it.hasNext())
        {
            Map.Entry pair = (Map.Entry)it.next();
            String val = (String) pair.getKey();
            System.out.println(val);
            inputWires.put(val, inpcnt);
            inpcnt++;
            //DWire temp = (DWire)pair.getValue();
        }*/
        
        int inpsize = inputWires.size();
        int inppow = (int) Math.pow(2, inpsize); 
        
        for(int i=0;i<inppow;i++)
        {
            String xi = Convert.dectoBin(i, inpsize);
            for(DGate ng:netlist)
            {
                for(int j=0;j<ng.input.size();j++)
                {
                    DWire dw = ng.input.get(j);
                    if(dw.wtype == DWireType.input)
                    {
                        int indx=0;
                        if(dw.name.trim().equals("in3"))
                        {
                            indx = 0;
                        }
                        else if(dw.name.trim().equals("in2"))
                        {
                            indx = 1;
                        }
                        else if(dw.name.trim().equals("in1"))
                        {
                            indx = 2;
                        }
                        if(xi.charAt(indx) == '0')
                            ng.input.get(j).wValue = DWireValue._0;
                        else if(xi.charAt(indx) == '1')
                            ng.input.get(j).wValue = DWireValue._1;
                    }
                    else if(dw.wtype == DWireType.Source)
                    {
                        ng.input.get(j).wValue = DWireValue._1;
                    }
                    else if(dw.wtype == DWireType.GND)
                    {
                        ng.input.get(j).wValue = DWireValue._0;
                    }
                    
                
                }
                bfunction(ng);
                
            }
            
            //int 
            
            DWireValue finaldw = netlist.get(netlist.size()-1).output.wValue; 
            if(finaldw == DWireValue._0)
                output += "0";
            else if(finaldw == DWireValue._1)
                output += "1";
            else if(finaldw == DWireValue._x)
                output += "-";
            
        }
        
        return output;
    }
    
    
    public static String bpermuteTest(List<DGate> netlist)
    {
        //DWireValue outvalue = DWireValue._x;
        String output = "";
        HashMap <String,DWire> inputsW = new HashMap<String,DWire>();
        List<DWire> inputL = new ArrayList<DWire>();
        
        for(DGate dg:netlist)
        {
            for(DWire dw:dg.input)
            {
                if(dw.wtype == DWireType.input)
                {
                    if(!inputsW.containsKey(dw.name.trim()))
                    {   
                        inputL.add(dw);
                       
                        inputsW.put(dw.name.trim(),dw);}
                    }
            }
        }
        
        HashMap <String,Integer> inputWires = new HashMap<String,Integer>();
        int inpcnt =0;
        for(DWire dwl:inputL)
        {
            inputWires.put(dwl.name.trim(), inpcnt);
            inpcnt++;
        }
        
        /*Iterator it = inputsW.entrySet().iterator();
        while(it.hasNext())
        {
            Map.Entry pair = (Map.Entry)it.next();
            String val = (String) pair.getKey();
            System.out.println(val);
            inputWires.put(val, inpcnt);
            inpcnt++;
            //DWire temp = (DWire)pair.getValue();
        }*/
        
        int inpsize = inputWires.size();
        int inppow = (int) Math.pow(2, inpsize); 
        
        for(int i=0;i<inppow;i++)
        {
            String xi = Convert.dectoBin(i, inpsize);
            for(DGate ng:netlist)
            {
                for(int j=0;j<ng.input.size();j++)
                {
                    DWire dw = ng.input.get(j);
                    if(dw.wtype == DWireType.input)
                    {
                        int indx=0;
                        if(dw.name.trim().equals("in1"))
                        {
                            indx = 0;
                        }
                        else if(dw.name.trim().equals("in2"))
                        {
                            indx = 1;
                        }
                        else if(dw.name.trim().equals("in3"))
                        {
                            indx = 2;
                        }
                        if(xi.charAt(indx) == '0')
                            ng.input.get(j).wValue = DWireValue._0;
                        else if(xi.charAt(indx) == '1')
                            ng.input.get(j).wValue = DWireValue._1;
                    }
                    else if(dw.wtype == DWireType.Source)
                    {
                        ng.input.get(j).wValue = DWireValue._1;
                    }
                    else if(dw.wtype == DWireType.GND)
                    {
                        ng.input.get(j).wValue = DWireValue._0;
                    }
                    
                
                }
                bfunction(ng);
                
            }
            
            //int 
            
            DWireValue finaldw = netlist.get(netlist.size()-1).output.wValue; 
            if(finaldw == DWireValue._0)
                output += "0";
            else if(finaldw == DWireValue._1)
                output += "1";
            else if(finaldw == DWireValue._x)
                output += "-";
            
        }
        
        return output;
    }
    
    
    
     public static String bpermutePreComp(List<DGate> netlist)
    {
        //DWireValue outvalue = DWireValue._x;
        String output = "";
        HashMap <String,DWire> inputsW = new HashMap<String,DWire>();
        List<DWire> inputL = new ArrayList<DWire>();
        
        for(DGate dg:netlist)
        {
            for(DWire dw:dg.input)
            {
                if(dw.wtype == DWireType.input)
                {
                    if(!inputsW.containsKey(dw.name.trim()))
                    {   
                        inputL.add(dw);
                       
                        inputsW.put(dw.name.trim(),dw);}
                    }
            }
        }
        
        HashMap <String,Integer> inputWires = new HashMap<String,Integer>();
        
        for(DWire dwl:inputL)
        {
            if("a".equals(dwl.name.trim()))
                inputWires.put(dwl.name.trim(), 0);
            else if("b".equals(dwl.name.trim()))
                inputWires.put(dwl.name.trim(), 1);
            //else if("c".equals(dwl.name.trim()))
            else
                inputWires.put(dwl.name.trim(), 2);
            
        }
        /*Iterator it = inputsW.entrySet().iterator();
        while(it.hasNext())
        {
            Map.Entry pair = (Map.Entry)it.next();
            String val = (String) pair.getKey();
            System.out.println(val);
            inputWires.put(val, inpcnt);
            inpcnt++;
            //DWire temp = (DWire)pair.getValue();
        }*/
        
        int inpsize = 3;
        int inppow = (int) Math.pow(2, inpsize); 
        for(int i=0;i<inppow;i++)
        {
            String xi = Convert.dectoBin(i, inpsize);
            for(DGate ng:netlist)
            {
                for(int j=0;j<ng.input.size();j++)
                {
                    DWire dw = ng.input.get(j);
                    if(dw.wtype == DWireType.input)
                    {
                        int indx = inputWires.get(dw.name.trim());
                        if(xi.charAt(indx) == '0')
                            ng.input.get(j).wValue = DWireValue._0;
                        else if(xi.charAt(indx) == '1')
                            ng.input.get(j).wValue = DWireValue._1;
                    }
                    else if(dw.wtype == DWireType.Source)
                    {
                        ng.input.get(j).wValue = DWireValue._1;
                    }
                    else if(dw.wtype == DWireType.GND)
                    {
                        ng.input.get(j).wValue = DWireValue._0;
                    }
                    
                
                }
                bfunction(ng);
                
            }
            
            //int 
            
            DWireValue finaldw = netlist.get(netlist.size()-1).output.wValue; 
            if(finaldw == DWireValue._0)
                output += "0";
            else if(finaldw == DWireValue._1)
                output += "1";
            else if(finaldw == DWireValue._x)
                output += "-";
            
        }
        
        return output;
    }
    
    
    
}
