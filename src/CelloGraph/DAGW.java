/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CelloGraph;

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
    public DAGW()
    {
        Wires = new ArrayList<Wire>();
        Gates = new ArrayList<Gate>();
    }
    
    public DAGW(List<Gate> dagwGates,List<Wire> dagwWires)
    {
        Wires = new ArrayList<Wire>();
        Gates = new ArrayList<Gate>();
 
      
        if (dagwWires != null) 
        {
            Wires = new ArrayList<Wire>();
            /*Iterator<Wire> itwire = dagwWires.iterator();
            while(itwire.hasNext())
            {
                Wire rmw = itwire.next();
                Wire y = new Wire(rmw);
                dagwWires.remove(rmw);
                Wires.add(y);
            }*/
            
            //Collections.copy(Wires,dagwWires);
            for (int i=0;i < dagwWires.size();i++) 
            {
                Wire y = new Wire(dagwWires.get(i));
                Wires.add(y);
            }
        }
        if (dagwGates != null) 
        {
            Gates = new ArrayList<Gate>();
            /*Iterator<Gate> itgate = dagwGates.iterator();
            while(itgate.hasNext())
            {
                Gate rmg = itgate.next();
                Gate y = new Gate(rmg);
                dagwGates.remove(rmg);
                Gates.add(y);
            }*/
            
            //Collections.copy(Gates, dagwGates);
            
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
