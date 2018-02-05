/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cellocad.BU.dom;

import org.cellocad.MIT.dnacompiler.Gate;
import org.cellocad.MIT.dnacompiler.Gate.GateType;
import org.cellocad.MIT.dnacompiler.Wire;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author prashantvaidyanathan
 */
public class DAGW {
    public ArrayList<Wire> Wires;
    public ArrayList<Gate> Gates;
    public List<String> truthtable;
    private String waveform;
	private Object BooleanLogic;
    public DAGW()
    {
        Wires = new ArrayList<Wire>();
        Gates = new ArrayList<Gate>();
        truthtable = new ArrayList<String>();
    }
    
    public DAGW(List<Gate> dagwGates,List<Wire> dagwWires)
    {
    	waveform = null;
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
    	waveform = null;
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
    
    public String printGraph(){
        String s = "";
        //String s = String.format("\n----- Logic Circuit #%d -----\n", index);
        for (int i = 0; i < Gates.size(); ++i) {
            Gate gi = Gates.get(i);

            s += String.format("%-12s", gi.type);
            //s += String.format("%-18s", BooleanLogic.logicString(gi.get_logics()));
            s += String.format("%-18s", gi.name);
            s += String.format("%-3d", gi.index);

            String child_indx = "(";
            for(Gate child: gi.getChildren()) {
                child_indx += child.index +",";
            }
            child_indx = child_indx.substring(0,child_indx.length()-1);
            if(!gi.type.equals("INPUT"))
                child_indx += ")";

            s += String.format("%-12s", child_indx);
            s += String.format("%-12s", gi.type);
            //s += String.format("%-18s", BooleanLogic.logicString(gi.get_logics()));
            s += String.format("%-18s", gi.name);
            s += String.format("%-3d", gi.index);
            //if(gi.getScores().get_score() != -1.0000) {
            //    s += String.format("%-5.4f", gi.getScores().get_score()); //onoff_ratio or noise_margin
            //}

            //if(gi.get_cellgrowth().size() > 0) {
            //    s += "  tox:" + String.format("%-3.2f", CellGrowth.mostToxicRow(gi));
            //}

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
                if(obj.Gates.get(j).type.equals(GateType.INPUT))
                {
                    if(obj.Gates.get(j).name.trim().equals(inputnames.get(i).trim()))
                    {
                        flag =1;
                    }
                }
            }
            if(flag == 0)
            {
                Gate dangGate = new Gate();
                dangGate.index = indx;
                dangGate.name = inputnames.get(i).trim();
                dangGate.type = GateType.INPUT;
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
            if(obj.Gates.get(i).type.equals(GateType.INPUT))
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
                if(inputnames.get(i).trim().equals(obj.Gates.get(j).name.trim()))
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
    
    public String getWaveform() {
		return waveform;
	}

	public void setWaveform(String waveform) {
		this.waveform = waveform;
	}
}
