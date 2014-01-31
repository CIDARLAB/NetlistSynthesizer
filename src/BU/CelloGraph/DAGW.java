/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BU.CelloGraph;

import MIT.dnacompiler.Gate;
import MIT.dnacompiler.Wire;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author prashantvaidyanathan
 */
public class DAGW {
    public ArrayList<Wire> Wires;
    public ArrayList<Gate> Gates;
    public String truthtable;
    public DAGW()
    {
        Wires = new ArrayList<Wire>();
        Gates = new ArrayList<Gate>();
        truthtable = "";
    }
    
    public DAGW(List<Gate> dagwGates,List<Wire> dagwWires)
    {
        Wires = new ArrayList<Wire>();
        Gates = new ArrayList<Gate>();
        truthtable = "";
      
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
