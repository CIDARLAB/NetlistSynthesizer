/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MIT.dnacompiler;

import BU.CelloGraph.DAGW;
import MIT.dnacompiler.Gate.GateType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 *
 * @author prashantvaidyanathan
 */
public class HeuristicSearch {

    
    public static void beginSearch(DAGW dagCirc, double cutoff)
    {
        List<BGateCombo> allcombos = new ArrayList<BGateCombo>();
        allcombos = LoadTables.getAllCombos(cutoff);
        int gates_size = dagCirc.Gates.size();
        List<BGateCombo> notcombos = new ArrayList<BGateCombo>();
        List<BGateCombo> norcombos = new ArrayList<BGateCombo>();
        int notcombosSize = LoadTables.NOTgateCount(allcombos);
        notcombos = LoadTables.dividelist(allcombos, GateType.NOT);
        norcombos = LoadTables.dividelist(allcombos, GateType.NOR);
        
        sortcombos(notcombos);
        sortcombos(norcombos);
        
        HashMap<String,String> Notgates = new HashMap<String,String>();
        HashMap<String,String> Norgates = new HashMap<String,String>();
        HashMap<String,String> inputNotgates = new HashMap<String,String>();
        HashMap<String,String> inputNorgates = new HashMap<String,String>();
        
        notcombos = sortcombosGatewise(notcombos);
        norcombos = sortcombosGatewise(norcombos);
        
        for(BGateCombo bgc:notcombos)
        {
            if(!Notgates.containsKey(bgc.Out))
                Notgates.put(bgc.Out, bgc.Out);
            
            if(bgc.Inp1.contains("inducer"))
            {
                if(!inputNotgates.containsKey(bgc.Inp1))
                    inputNotgates.put(bgc.Inp1, bgc.Inp1);
            }
        }
        for(BGateCombo bgc:norcombos)
        {
            if(!Norgates.containsKey(bgc.Out))
                Norgates.put(bgc.Out, bgc.Out);
            
            if(bgc.Inp1.contains("inducer"))
            {
                if(!inputNorgates.containsKey(bgc.Inp1))
                    inputNorgates.put(bgc.Inp1, bgc.Inp1);
            }
            if(bgc.Inp2.contains("inducer"))
            {
                if(!inputNorgates.containsKey(bgc.Inp2))
                    inputNorgates.put(bgc.Inp2, bgc.Inp2);
            }
        }
        
        /*System.out.println("Number of availabe Not Inputs with cutoff higher than "+ cutoff +": " + inputNotgates.size());
        System.out.println("Number of availabe Nor Inputs with cutoff higher than "+ cutoff +": " + inputNorgates.size());
        System.out.println("Number of availabe Not outputs with cutoff higher than "+ cutoff +": " + Notgates.size());
        System.out.println("Number of availabe Nor outputs with cutoff higher than "+ cutoff +": " + Norgates.size());
        */
        HashMap<Integer,Gate> nodesCirc = new HashMap<Integer,Gate>();
        int indx = gates_size-1;
        
        
        
        
        for (int i = 0; i < dagCirc.Gates.size(); i++) {
	    if (dagCirc.Gates.get(i).Outgoing != null) {                     //Outgoing is a wire
		int index = dagCirc.Gates.get(i).Outgoing.Index;
		for(Wire w: dagCirc.Wires) {
		    if(w.Index == index) { dagCirc.Gates.get(i).Outgoing = w; }
		}
	    }
	}
	for (int i = 0; i < dagCirc.Wires.size(); i++) {
	    if (dagCirc.Wires.get(i).From != null) {                        //From is a gate
		int index = dagCirc.Wires.get(i).From.Index;
		for(Gate g: dagCirc.Gates) {
		    if(g.Index == index) { dagCirc.Wires.get(i).From = g; }
		}
	    }
	    if (dagCirc.Wires.get(i).To != null) {                          //To is a gate
		int index = dagCirc.Wires.get(i).To.Index;
		for(Gate g: dagCirc.Gates) {
		    if(g.Index == index) { dagCirc.Wires.get(i).To = g; }
		}
	    }
	    if (dagCirc.Wires.get(i).Next != null) {                        //Next is a wire
		int index = dagCirc.Wires.get(i).Next.Index;
		for(Wire w: dagCirc.Wires) {
		    if(w.Index == index) { dagCirc.Wires.get(i).Next = w; }
		}
	    }
	}

        
        
        for(int i=(dagCirc.Gates.size()-1);i>=0;i--)
        {
            if(isInput(dagCirc.Gates.get(i)))
            {
               dagCirc.Gates.get(i).stage =0;
            }
            else
            {
                int max = dagCirc.Gates.get(i).stage;
                Wire w = dagCirc.Gates.get(i).Outgoing;
                while(w!=null)
                {
                    int stg = w.To.stage;
                    if(stg >max)
                        max = stg;
                    w = w.Next;
                }
                dagCirc.Gates.get(i).stage = (max +1);
            }
            //System.out.println(dagCirc.Gates.get(i).Name + ":"+dagCirc.Gates.get(i).stage);
        }
        int maxstage = dagCirc.Gates.get(0).stage;
        int xstage =0;
        
        int xindx =0;
        while(xstage <= maxstage)
        {
            for(Gate bgate:dagCirc.Gates)
            {
                if(bgate.stage == xstage)
                {
                    nodesCirc.put(xindx,bgate);
                    xindx++;
                }
            }
            xstage++;
        }
        Iterator it = nodesCirc.entrySet().iterator();
        while(it.hasNext())
        {
            Map.Entry pairs = (Map.Entry)it.next();
            Gate x = (Gate)pairs.getValue();
            System.out.println(x.Name +":"+pairs.getKey());
        }
        
        
        
        
    }
    
    
    public static boolean hasInput(Gate bgate)
    {
        boolean res = false;
        Wire w = bgate.Outgoing;
        
        while(w != null)
        {
            if(w.To.Type.equals(GateType.INPUT.toString()))
                return true;
            w = w.Next;
        }
        return res;
    }
    
    public static boolean isInput(Gate bgate)
    {
        boolean res = false;
        if(bgate.Type.equals(GateType.INPUT.toString()))
            return true;
        return res;
    }
    
    public static void sortcombos(List<BGateCombo> combos) 
    {
        BGateCombo temp = new BGateCombo();
        int nsize = combos.size();
        int i, j;
        int pos = nsize-1;
        double min;

        for (i = 0; i < (nsize-1); i++) 
        {
            min = combos.get(i).score;
            pos = i;
            for (j = (i+1); j<nsize; j++) 
            {
                if (combos.get(j).score >= min) 
                {
                    pos = j;
                    min = combos.get(j).score;            
                }
            }
            
            temp.Gtype = combos.get(i).Gtype;
            temp.Inp1 = combos.get(i).Inp1;
            temp.Inp2 = combos.get(i).Inp2;
            temp.Out = combos.get(i).Out;
            temp.score = combos.get(i).score;

            combos.get(i).Gtype = combos.get(pos).Gtype;
            combos.get(i).Inp1 = combos.get(pos).Inp1;
            combos.get(i).Inp2 = combos.get(pos).Inp2;
            combos.get(i).Out = combos.get(pos).Out;
            combos.get(i).score = combos.get(pos).score;

            combos.get(pos).Gtype = temp.Gtype;
            combos.get(pos).Inp1 = temp.Inp1;
            combos.get(pos).Inp2 = temp.Inp2;
            combos.get(pos).Out = temp.Out;
            combos.get(pos).score = temp.score;
            
        }



    }
    
    public static List<BGateCombo> sortcombosGatewise(List<BGateCombo> combos)
    {
        List<BGateCombo> gatewisecombo = new ArrayList<BGateCombo>();
        HashMap<String,String> outputdone = new HashMap<String,String>();
        int i,j;
        
        for(i=0;i<combos.size();i++)
        {
            String bgcOut = combos.get(i).Out;
            if(outputdone.containsKey(bgcOut))
                continue;
            
            outputdone.put(bgcOut, bgcOut);
            for(j=i;j<combos.size();j++)
            {
                if(combos.get(j).Out.equals(bgcOut))
                {
                    gatewisecombo.add(combos.get(j));
                }
            }
        }
        
        
        return gatewisecombo;
    }
}
