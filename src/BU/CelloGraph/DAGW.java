/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BU.CelloGraph;

import MIT.dnacompiler.Gate;
import MIT.dnacompiler.Gate.GateType;
import MIT.dnacompiler.Wire;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author prashantvaidyanathan
 */
public class DAGW {
    public ArrayList<Wire> Wires;
    public ArrayList<Gate> Gates;
    public List<String> truthtable;
    public DAGW()
    {
        Wires = new ArrayList<Wire>();
        Gates = new ArrayList<Gate>();
        truthtable = new ArrayList<String>();
    }
    
    public DAGW(List<Gate> dagwGates,List<Wire> dagwWires)
    {
        Wires = new ArrayList<Wire>();
        Gates = new ArrayList<Gate>();
        truthtable = new ArrayList<String>();
      
        if (dagwWires != null) 
        {
            Wires = new ArrayList<Wire>();
         
            for (int i=0;i < dagwWires.size();i++) 
            {
                Wire y = new Wire(dagwWires.get(i));
                Wires.add(y);
            }
        }
        if (dagwGates != null) 
        {
            Gates = new ArrayList<Gate>();
            for(int i=0;i<dagwGates.size();i++) 
            {
                Gate gy = new Gate(dagwGates.get(i));
                Gates.add(gy);
            }
        }
    }
    
    public DAGW(DAGW obj)
    {
        Wires = new ArrayList<Wire>();
        Gates = new ArrayList<Gate>();
        truthtable = obj.truthtable;
        if(obj.truthtable!= null)
        {
            truthtable = new ArrayList<String>();
            for(int i=0;i < obj.truthtable.size();i++)
            {
                truthtable.add(obj.truthtable.get(i));
            }
        }
        if (obj.Wires != null) 
        {
            Wires = new ArrayList<Wire>();
 
            for (int i=0;i < obj.Wires.size();i++) 
            {
                Wire y = new Wire(obj.Wires.get(i));
                Wires.add(y);
            }
        }
        if (obj.Gates != null) 
        {
            Gates = new ArrayList<Gate>();
 
            for(int i=0;i<obj.Gates.size();i++) 
            {
                Gate gy = new Gate(obj.Gates.get(i));
                Gates.add(gy);
            }
        }
    }
    
    public String printGraph() 
    {
        String s = "";

        for (int i = 0; i < Gates.size(); ++i) 
        {
            Gate gi = Gates.get(i);
            //s += String.format("Vertex: Name = %-18s Type = %-9s  Index = %-3d", gi.Name, gi.Type, gi.Index);


            s += String.format("%-12s", gi.Type);
            s += String.format("%-18s", gi.Name);
            s += String.format("%-3d", gi.Index);


            //System.out.println(s);

            String child_indx = "";
            if (gi.Type.equals("NOT") || gi.Type.equals("OUTPUT")) 
            {
                //System.out.println(gi.Type);
                child_indx += "(" + gi.Outgoing.To.Index + ")";
            }
            if (gi.Type.equals("NOR") || gi.Type.equals("OUTPUT_OR")) 
            {
                child_indx += "(" + gi.Outgoing.To.Index + ",";
                child_indx += gi.Outgoing.Next.To.Index + ")";
            }
            s += String.format("%-12s", child_indx);

            s += "\n";
        }
        s += "\n";

        return s;
    }
    
    
    public static DAGW addDanglingInputs(DAGW obj, List<String> inputnames)
    {
        DAGW reorderedDAGW = new DAGW();
        List<Gate> inpGates = new ArrayList<Gate>();
        int indx = -1;
        for(int i=0;i<inputnames.size();i++)
        {
            int flag =0;
            for(int j=0;j<obj.Gates.size();j++)
            {
                if(obj.Gates.get(j).Type.equals(GateType.INPUT.toString()))
                {
                    if(obj.Gates.get(j).Name.trim().equals(inputnames.get(i).trim()))
                    {
                        flag =1;
                    }
                }
            }
            if(flag == 0)
            {
                Gate dangGate = new Gate();
                dangGate.Index = indx;
                dangGate.Name = inputnames.get(i).trim();
                dangGate.Type = GateType.INPUT.toString();
                indx--;
                inpGates.add(dangGate);
            }
        }
        
        
        for(Gate xgate:obj.Gates)
            reorderedDAGW.Gates.add(xgate);
        
        for(Gate xgate:inpGates)
            reorderedDAGW.Gates.add(xgate);
        
        
        for(Wire xwire:obj.Wires)
            reorderedDAGW.Wires.add(xwire);
        for(String xtt:obj.truthtable)
            reorderedDAGW.truthtable.add(xtt);
        
        return reorderedDAGW;
    }
    
    public static DAGW reorderinputs(DAGW obj, List<String> inputnames)
    {
        DAGW reorderedDAGW = new DAGW();
        int firstindex =0;
        int tempfirstindx =0;
        
        HashMap<Integer, Gate> inpGates = new HashMap<Integer, Gate>(); 
        for(int i=0;i<obj.Gates.size();i++)
        {
            if(obj.Gates.get(i).Type.equals(GateType.INPUT.toString()))
            {
                firstindex = i;
                break;
            }
        }
        tempfirstindx = firstindex;
        for(int i=0;i<inputnames.size();i++)
        {
            for(int j=0;j<obj.Gates.size();j++)
            {
                if(inputnames.get(i).trim().equals(obj.Gates.get(j).Name.trim()))
                {
                    inpGates.put(firstindex, obj.Gates.get(j));
                    firstindex++;
                }
            }
         }
        
        for(int i=0;i<tempfirstindx;i++)
        {
            reorderedDAGW.Gates.add(obj.Gates.get(i));
        }
        
        for(int i=tempfirstindx;i<obj.Gates.size();i++)
        {
            reorderedDAGW.Gates.add(inpGates.get(i));
        }
        
        
        
        for(Wire xwire:obj.Wires)
            reorderedDAGW.Wires.add(xwire);
        for(String xtt:obj.truthtable)
            reorderedDAGW.truthtable.add(xtt);
        
        return reorderedDAGW;
    }
    
    public static String printDAGW()
    {
        return "";
    }
    
    public static int FindWire(int de){
        return 0;
    }
    public static int FindGate(int dv){
        return 0;
    }
}
