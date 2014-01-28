/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MIT.dnacompiler;

import BU.CelloGraph.DAGW;
import BU.CelloGraph.DAGW_assignment;
import MIT.dnacompiler.BGateNode.nodecolor;
import MIT.dnacompiler.Gate.GateType;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author prashantvaidyanathan
 */
public class HeuristicSearch {

    
    public static DAGW_assignment beginSearch(DAGW dagCirc, double cutoff,double maxscore, long maxassign, long outputshift, int fixedinputs)
    {
        DAGW_assignment assignmentresult = new DAGW_assignment();
        List<BGateCombo> allcombos = new ArrayList<BGateCombo>();
        allcombos = LoadTables.getAllCombos(cutoff);
        int gates_size = dagCirc.Gates.size();
        List<BGateCombo> notcombos = new ArrayList<BGateCombo>();
        List<BGateCombo> norcombos = new ArrayList<BGateCombo>(); 
        
        
        List<BGateCombo> norcombo_0_inputs = new ArrayList<BGateCombo>();
        List<BGateCombo> norcombo_1_input = new ArrayList<BGateCombo>();
        List<BGateCombo> norcombo_2_inputs = new ArrayList<BGateCombo>();
        
        List<BGateCombo> notcombo_0_inputs = new ArrayList<BGateCombo>();
        List<BGateCombo> notcombo_1_input = new ArrayList<BGateCombo>();
        
        
        
        int notcombosSize = LoadTables.NOTgateCount(allcombos);
        notcombos = LoadTables.dividelist(allcombos, GateType.NOT);
        norcombos = LoadTables.dividelist(allcombos, GateType.NOR);
        long outputassigns =0;
        sortcombos(notcombos);
        sortcombos(norcombos);
        long totassign=0;
        
        HashMap<String,String> Notgates = new HashMap<String,String>();
        HashMap<String,String> Norgates = new HashMap<String,String>();
        HashMap<String,String> inputNotgates = new HashMap<String,String>();
        HashMap<String,String> inputNorgates = new HashMap<String,String>();
        
        notcombos = sortcombosGatewise(notcombos);
        norcombos = sortcombosGatewise(norcombos);
        //LoadTables.printallCombos(norcombos);
        for(BGateCombo bgc:notcombos)
        {
            if(!Notgates.containsKey(bgc.Out))
                Notgates.put(bgc.Out, bgc.Out);
            
            if(bgc.Inp1.contains("inducer"))
            {
                if(!inputNotgates.containsKey(bgc.Inp1))
                    inputNotgates.put(bgc.Inp1, bgc.Inp1);
                
                notcombo_1_input.add(bgc);
            }
            else
            {
                notcombo_0_inputs.add(bgc);
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
            
            if((!bgc.Inp1.contains("inducer")) && (!bgc.Inp2.contains("inducer")))
            {
                norcombo_0_inputs.add(bgc);
            }
            else if((bgc.Inp1.contains("inducer")) && (bgc.Inp2.contains("inducer")))
            {
                norcombo_2_inputs.add(bgc);
            }
            else
            {
                BGateCombo entry = new BGateCombo();
                if(bgc.Inp1.contains("inducer"))
                {
                    entry.Inp1 = bgc.Inp1;
                    entry.Inp2 = bgc.Inp2;
                }
                else 
                {
                    entry.Inp1 = bgc.Inp2;
                    entry.Inp2 = bgc.Inp1;                    
                }
                entry.Out = bgc.Out;
                entry.Gtype = bgc.Gtype;
                norcombo_1_input.add(entry);
            }
        }
        
        /*System.out.println("Number of availabe Not Inputs with cutoff higher than "+ cutoff +": " + inputNotgates.size());
        System.out.println("Number of availabe Nor Inputs with cutoff higher than "+ cutoff +": " + inputNorgates.size());
        System.out.println("Number of availabe Not outputs with cutoff higher than "+ cutoff +": " + Notgates.size());
        System.out.println("Number of availabe Nor outputs with cutoff higher than "+ cutoff +": " + Norgates.size());
        */
        HashMap<Integer,Gate> nodesCirc = new HashMap<Integer,Gate>();
        
        int indx = dagCirc.Gates.size()-1;
        int no_of_inputs =0;
        //<editor-fold desc="Reconnecting DAGW in memory">
        for (int i = 0; i < dagCirc.Gates.size(); i++) {
	    if (dagCirc.Gates.get(i).Outgoing != null) {                     //Outgoing is a wire
		int index = dagCirc.Gates.get(i).Outgoing.Index;
		for(Wire w: dagCirc.Wires) {
		    if(w.Index == index) { dagCirc.Gates.get(i).Outgoing = w; }
		}
	    }
	if(dagCirc.Gates.get(i).Type.equals(GateType.INPUT.toString()))
            no_of_inputs++;
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
        //</editor-fold>
        
        //<editor-fold desc="Assiging Gate Stages"> 
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
        //</editor-fold>
        
        int maxstage = dagCirc.Gates.get(0).stage;
        
        List<String> fixedinputsList = new ArrayList<String>();
         
        fixedinputsList.add("inducer_pTac");
        fixedinputsList.add("inducer_pBAD");
        fixedinputsList.add("inducer_pTetStar");
        
        int xstage =0;
        int xindx =0;
        
        //<editor-fold desc="Reindexing Gates">
        while(xstage <= maxstage)
        {
            for(Gate bgate:dagCirc.Gates)
            {
                if(bgate.stage == xstage)
                {
                    bgate.Index = xindx;
                    nodesCirc.put(xindx,bgate);
                    xindx++;
                }
            }
            xstage++;
        }
        //</editor-fold>
        
        
        xindx--;
        System.out.println(xindx);
        BGateNode root = new BGateNode();
        
        root.bgate = (Gate)nodesCirc.get(xindx);
        
        
        //root.bgate = (Gate)nodesCirc.get(xindx);
        root.index = xindx;
        root.bgname = "reporter_YFP";
        root.parent = null;
        root.Next = null;
        root.ncolor = nodecolor.WHITE;
        BGateNode curr = root;
        //curr = root;
        //HashMap<HashMap<Integer,BGateNode>,Integer> combinations = new HashMap<HashMap<Integer,BGateNode>,Integer>();
        
        Iterator it = nodesCirc.entrySet().iterator();
        while(it.hasNext())
        {
            Map.Entry pairs = (Map.Entry)it.next();
            Gate x = (Gate)pairs.getValue();
            x.Index = (Integer)pairs.getKey();
            System.out.println(x.Name +":"+pairs.getKey());
        }
       
        List<HashMap<Integer,BGateNode>> combinations = new ArrayList<HashMap<Integer,BGateNode>>();
        
        //List<Pair<Integer,Wire>> nodequeue = new ArrayList<Pair<Integer,Wire>>();
        
        //Start Heuristic Search Algo
        List<Integer> indices = new ArrayList<Integer>();
        assignmentresult.dagobject = dagCirc;
        
        int assigncounter =0;
        //System.out.println("\n\n\nStart Heuristic Algorithm!!\n");
        while (curr != null) 
        {
            int term=0;
            //<editor-fold desc="Node is white">
            if (curr.ncolor == nodecolor.WHITE) 
            {
                curr.ncolor = nodecolor.GRAY;
                //System.out.println(curr.index);
                //indices.add(curr.index);
                if(fixedinputs == 1)
                {
                    term = no_of_inputs;
                }
                if (curr.index == term) 
                {
                    
                    curr.ncolor = nodecolor.BLACK;
                    BGateNode runner = curr;
                    HashMap<Integer, String> assignGate = new HashMap<Integer, String>();
                    //System.out.println("\n\nAssignment!! ==>");
                    while (runner != null) 
                    {
                        assignGate.put(runner.index, runner.bgname);
                        //System.out.println(runner.index + ":" +runner.bgname);
                        runner = runner.parent;
                    }
                    if(fixedinputs ==1)
                    {
                        for(int fi=0;fi<no_of_inputs;fi++)
                            assignGate.put(fi,fixedinputsList.get(fi));
                    }
                    assignmentresult.assignment.add(assignGate);
                    totassign++;
                    assigncounter++;
                    
                    if(assigncounter == outputshift)
                    {
                        assigncounter =0;
                        while(curr.index!=(xindx-1))
                        {
                            curr = curr.parent;
                        }
                        if(curr.Next != null)
                            curr = curr.Next;
                        else
                          curr = curr.parent.child;   
                        
                    }
                    
                    if(totassign == maxassign)
                        break;
                    /*if(score >= maxscore)
                     * break;
                     */
                    if (curr.Next != null) 
                    {
                        curr = curr.Next;
                    } 
                    else 
                    {
                        curr = curr.parent;
                    }
                } 
                
                else 
                {
                    if(curr.index == (xindx-1))
                        assigncounter =0;
                   
                    List<String> childnodeassign = new ArrayList<String>();
                    //HashMap<String,String> hashchildnodeassign = new HashMap<String,String>();
                    int next_indx = curr.index-1;
                    BGateNode runner = curr;
                    int flag =0;
                    while(runner!= null)
                    {
                        Wire outw = runner.bgate.Outgoing;
                        while(outw!=null)
                        {
                            if(outw.To.Index == next_indx)
                            {
                                flag =1;
                                break;
                            }
                            outw = outw.Next;
                        }
                        if(flag==1)
                            break;
                        runner = runner.parent;
                    }
                    
                        
                    childnodeassign = new ArrayList<String>();
                    
                    if(runner == null)
                        childnodeassign.add("Inducer_Dummy");
                    else
                    {
                  
                    //<editor-fold desc="Curr node is an input to the OUPUT/OUTPUT_OR Gate " defaultstate="collapsed">
                    if(runner.bgate.Type.equals(GateType.OUTPUT.toString()) || runner.bgate.Type.equals(GateType.OUTPUT_OR.toString()))
                    {
                        //Child nodes to be added are of Gatetype NOR
                        
                        //<editor-fold desc="Child nodes to be added are of GateType NOR" defaultstate="collapsed">
                        if (nodesCirc.get(next_indx).Type.equals(GateType.NOR.toString())) 
                        {
                            int inpno = 0;
                            Wire nextw = nodesCirc.get(next_indx).Outgoing;
                            while (nextw != null) 
                            {
                                if (nextw.To.Type.equals(GateType.INPUT.toString())) 
                                {
                                    inpno++;
                                }
                                nextw = nextw.Next;
                            }
                          
                            int comboflag = 0;
                            if (inpno == 1) 
                            {
                                for (BGateCombo xbgc : norcombo_1_input) 
                                {
                                    comboflag = 1;
                                    if(childnodeassign.contains(xbgc.Out))
                                        continue;
                                    
                                    BGateNode subrunner = curr;
                                    while (subrunner != null) 
                                    {
                                        if (subrunner.bgname.equals(xbgc.Inp1) || subrunner.bgname.equals(xbgc.Inp2) || subrunner.bgname.equals(xbgc.Out)) 
                                        {
                                            comboflag = 0;
                                            break;
                                        }
                                        subrunner = subrunner.parent;
                                    }
                                    if (comboflag == 1) 
                                    {
                                        childnodeassign.add(xbgc.Out);
                                    }
                                }
                            } 
                            else if (inpno == 2) 
                            {
                                for (BGateCombo xbgc : norcombo_2_inputs) 
                                {
                                    comboflag = 1;
                                    if(childnodeassign.contains(xbgc.Out))
                                        continue;
                                    BGateNode subrunner = curr;
                                    while (subrunner != null) 
                                    {
                                        if (subrunner.bgname.equals(xbgc.Inp1) || subrunner.bgname.equals(xbgc.Inp2) || subrunner.bgname.equals(xbgc.Out)) 
                                        {
                                            comboflag = 0;
                                            break;
                                        }
                                        subrunner = subrunner.parent;
                                    }
                                    if (comboflag == 1) 
                                    {
                                        childnodeassign.add(xbgc.Out);
                                    }
                                }
                            } 
                            else 
                            {
                                for (BGateCombo xbgc : norcombo_0_inputs) 
                                {
                                    comboflag = 1;
                                    if(childnodeassign.contains(xbgc.Out))
                                        continue;
                                    BGateNode subrunner = curr;
                                    while (subrunner != null) 
                                    {
                                        if (subrunner.bgname.equals(xbgc.Inp1) || subrunner.bgname.equals(xbgc.Inp2) || subrunner.bgname.equals(xbgc.Out)) 
                                        {
                                            comboflag = 0;
                                            break;
                                        }
                                        subrunner = subrunner.parent;
                                    }
                                    if (comboflag == 1) 
                                    {
                                        childnodeassign.add(xbgc.Out);
                                    }
                                }
                            }
                        }
                        
                        //</editor-fold>
                        
                        //<editor-fold desc="Child nodes to be added are of GateType NOT" defaultstate="collapsed">
                        else if(nodesCirc.get(next_indx).Type.equals(GateType.NOT.toString()))
                        {
                            int inpno=0;
                            Wire nextw = nodesCirc.get(next_indx).Outgoing;
                            while(nextw!=null)
                            {
                                if(nextw.To.Type.equals(GateType.INPUT.toString()))
                                    inpno++;
                                nextw = nextw.Next;
                            }
                            int comboflag = 0;
                            if (inpno == 1) 
                            {
                                for (BGateCombo xbgc : notcombo_1_input) 
                                {
                                    comboflag = 1;
                                    if (childnodeassign.contains(xbgc.Out)) // skip certain values in combos list
                                    {
                                        continue;
                                    }
                                    BGateNode subrunner = curr;
                                    
                                        while (subrunner != null) 
                                        {
                                            if (subrunner.bgname.equals(xbgc.Inp1) || subrunner.bgname.equals(xbgc.Out)) 
                                            {
                                                comboflag = 0;
                                                break;
                                            }
                                            subrunner = subrunner.parent;
                                        }
                                    
                                    if (comboflag == 1) 
                                    {
                                        childnodeassign.add(xbgc.Out);
                                    }
                                }
                            } 
                            else 
                            {
                                for(BGateCombo xbgc : notcombo_0_inputs) 
                                {
                                    comboflag = 1;
                                    if (childnodeassign.contains(xbgc.Out)) 
                                    {
                                        continue;
                                    }
                                    BGateNode subrunner = curr;
                                        while (subrunner != null) {
                                            if (subrunner.bgname.equals(xbgc.Inp1) || subrunner.bgname.equals(xbgc.Out)) {
                                                comboflag = 0;
                                                break;
                                            }
                                            subrunner = subrunner.parent;
                                        }
                                    
                                    if (comboflag == 1) 
                                    {
                                        childnodeassign.add(xbgc.Out);
                                    }
                                }
                            }
                        }                        
                        //</editor-fold>
                        
                        //<editor-fold desc="Child nodes to be added are of GateType INPUT" defaultstate="collapsed">
                        else if (nodesCirc.get(next_indx).Type.equals(GateType.INPUT.toString())) 
                        {
                            Iterator itinput = inputNotgates.entrySet().iterator();
                            while (itinput.hasNext()) 
                            {
                                Map.Entry pairs = (Map.Entry) itinput.next();
                                String tempinp = (String) pairs.getKey();
                                if (!childnodeassign.contains(tempinp)) 
                                {
                                    int inpflag = 0;
                                    BGateNode inpbgc = curr;
                                    while (inpbgc != null) 
                                    {
                                        if (inpbgc.bgname.equals(tempinp)) 
                                        {
                                            inpflag = 1;
                                            break;
                                        }
                                        inpbgc = inpbgc.parent;
                                    }
                                    if (inpflag == 0) 
                                    {
                                        childnodeassign.add(tempinp);
                                    }
                                }
                            }
                            itinput = inputNorgates.entrySet().iterator();
                            while (itinput.hasNext()) 
                            {
                                Map.Entry pairs = (Map.Entry) itinput.next();
                                String tempinp = (String) pairs.getKey();
                                if (!childnodeassign.contains(tempinp)) 
                                {
                                    int inpflag = 0;
                                    BGateNode inpbgc = curr;
                                    while (inpbgc != null) 
                                    {
                                        if (inpbgc.bgname.equals(tempinp)) 
                                        {
                                            inpflag = 1;
                                            break;
                                        }
                                        inpbgc = inpbgc.parent;
                                    }
                                    if (inpflag == 0) 
                                    {
                                        childnodeassign.add(tempinp);
                                    }
                                }
                            }
                        }
                        //</editor-fold>
                        
                    }
                    //</editor-fold>
                    
                    else
                    {
                        int isInp=0;
                        Gate ginp = nodesCirc.get(next_indx);
                        if(ginp.Type.equals(GateType.INPUT.toString()))
                        {
                            isInp =1;
                        }
                        else if(ginp.Type.equals(GateType.NOR.toString()))
                        {
                            isInp =2;
                           
                        }
                        else if(ginp.Type.equals(GateType.NOT.toString()))
                        {
                            isInp =3;
                        }
                        if(runner.bgate.Type.equals(GateType.NOR.toString()))
                        {
                            
                            //<editor-fold desc="Current node is an input to a NOR Gate">
                            String outrun = runner.bgname;
                            //System.out.println(outrun);
                            String inp1run = "";
                            int found=0;
                            if(runner.bgate.Outgoing.To.Index == next_indx)
                            {
                                BGateNode subrunner = curr;
                                found =0;
                                while(subrunner!= null)
                                {
                                    if(subrunner.index == runner.bgate.Outgoing.Next.To.Index)
                                    {
                                        found =1;
                                        break;
                                    }
                                    subrunner = subrunner.parent;
                                }
                                if(found ==1)
                                {
                                    inp1run = subrunner.bgname; 
                                }
                            } 
                            else
                            {
                                BGateNode subrunner = curr;
                                found =0;
                                while(subrunner!= null)
                                {
                                    if(subrunner.index == runner.bgate.Outgoing.To.Index)
                                    {
                                        found =1;
                                        break;
                                    }
                                    subrunner = subrunner.parent;
                                }
                                if(found ==1)
                                {
                                    inp1run = subrunner.bgname; 
                                }
                            }
                            
                                Wire wcheck = runner.bgate.Outgoing;
                                int inpno=0;
                                while(wcheck!=null)
                                {
                                    if(wcheck.To.Type.equals(GateType.INPUT.toString()))
                                        inpno++; 
                                    wcheck = wcheck.Next;
                                }
                                
                                for(BGateCombo bgc:norcombos)
                                {
                                    
                                    //<editor-fold desc="one of the inputs has been assigned already">
                                    if(found ==1) // one of the inputs has been assigned already
                                    {
                                        if(childnodeassign.contains(bgc.Inp1) && childnodeassign.contains(bgc.Inp2))
                                            continue;
                                        
                                        //<editor-fold desc="intended node is an input node"> 
                                        if(isInp == 1) //intended node is an input
                                        {
                                            if(bgc.Out.equals(outrun))
                                            {
                                                if(bgc.Inp1.equals(inp1run))
                                                {
                                                    if(bgc.Inp2.contains("inducer"))
                                                    {
                                                        if(!childnodeassign.contains(bgc.Inp2))
                                                        {
                                                            int inpflag =0;
                                                            BGateNode inprunner = curr;
                                                            while(inprunner!=null)
                                                            {
                                                                if(inprunner.bgname.equals(bgc.Inp2))
                                                                {
                                                                    inpflag =1;
                                                                    break;
                                                                }
                                                                inprunner = inprunner.parent;
                                                            }
                                                            if(inpflag ==0)
                                                            {
                                                                childnodeassign.add(bgc.Inp2);
                                                            }
                                                        }
                                                    }
                                                }
                                                else if(bgc.Inp2.equals(inp1run))
                                                {
                                                    if(bgc.Inp1.contains("inducer"))
                                                    {
                                                        if(!childnodeassign.contains(bgc.Inp1))
                                                        {
                                                            int inpflag =0;
                                                            BGateNode inprunner = curr;
                                                            while(inprunner!=null)
                                                            {
                                                                if(inprunner.bgname.equals(bgc.Inp1))
                                                                {
                                                                    inpflag =1;
                                                                    break;
                                                                }
                                                                inprunner = inprunner.parent;
                                                            }
                                                            if(inpflag ==0)
                                                            {
                                                                //System.out.println(bgc.Inp1);
                                                                childnodeassign.add(bgc.Inp1);
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                        //</editor-fold>
                                        //<editor-fold desc="intended node is not an output">
                                        else // concerned gate is not an input gate (be careful here)
                                        {
                                            String tempnodesassign = "";
                                            if(bgc.Out.equals(outrun))
                                            {
                                                if(bgc.Inp1.equals(inp1run))
                                                {
                                                   int inpflag =0;
                                                   BGateNode inprunner = curr;
                                                   while(inprunner!=null)
                                                   {
                                                       if(inprunner.bgname.equals(bgc.Inp2))
                                                       {
                                                           inpflag =1;
                                                           break;
                                                       }
                                                       inprunner = inprunner.parent;
                                                   }
                                                   if(inpflag ==0)
                                                   {
                                                       tempnodesassign = bgc.Inp2;
                                                   }
                                                }
                                                else if (bgc.Inp2.equals(inp1run)) 
                                                {
                                                    //if (bgc.Inp1.contains("inducer")) 
                                                    //{

                                                        int inpflag = 0;
                                                        BGateNode inprunner = curr;
                                                        while (inprunner != null) 
                                                        {
                                                            if (inprunner.bgname.equals(bgc.Inp1)) 
                                                            {
                                                                inpflag = 1;
                                                                break;
                                                            }
                                                            inprunner = inprunner.parent;
                                                        }
                                                        if (inpflag == 0) 
                                                        {
                                                            tempnodesassign = bgc.Inp1;
                                                        }
                                                    //}
                                                }
                                            }
                                            if(childnodeassign.contains(tempnodesassign))
                                                    continue;
                                            
                                            if(isInp == 2) // nodes to be added have gate of type nor
                                            {
                                                int deepinpno = 0;
                                                Wire deepw = ginp.Outgoing;
                                                while (deepw != null) {
                                                    if (deepw.To.Type.equals(GateType.INPUT.toString())) {
                                                        deepinpno++;
                                                    }
                                                    deepw = deepw.Next;
                                                }
                                                String xtempn = tempnodesassign;


                                                int deepflag = 0;
                                                if (deepinpno == 1) // 1 inducer
                                                {
                                                    for (BGateCombo nbcg : norcombo_1_input) //move this!!
                                                    {
                                                        if (childnodeassign.contains(xtempn)) 
                                                        {
                                                            continue;
                                                        }
                                                        if (xtempn.equals(nbcg.Out)) 
                                                        {
                                                            deepflag = 1;
                                                            BGateNode deeprunner = curr;
                                                            while (deeprunner != null) 
                                                            {
                                                                if (deeprunner.bgname.equals(nbcg.Out) || deeprunner.bgname.equals(nbcg.Inp1) || deeprunner.bgname.equals(nbcg.Inp2)) 
                                                                {
                                                                    deepflag = 0;
                                                                    break;
                                                                }
                                                                deeprunner = deeprunner.parent;
                                                            }
                                                            if (deepflag == 1) 
                                                            {
                                                                childnodeassign.add(xtempn);
                                                            }
                                                        }
                                                    }
                                                }
                                                else if (deepinpno == 2) // 2 inducers
                                                {
                                                    for (BGateCombo nbcg : norcombo_2_inputs) //move this!!
                                                    {
                                                        if (childnodeassign.contains(xtempn)) 
                                                        {
                                                            continue;
                                                        }
                                                        if (xtempn.equals(nbcg.Out)) 
                                                        {
                                                            deepflag = 1;
                                                            BGateNode deeprunner = curr;
                                                            while (deeprunner != null) 
                                                            {
                                                                if (deeprunner.bgname.equals(nbcg.Out) || deeprunner.bgname.equals(nbcg.Inp1) || deeprunner.bgname.equals(nbcg.Inp2)) 
                                                                {
                                                                    deepflag = 0;
                                                                    break;
                                                                }
                                                                deeprunner = deeprunner.parent;
                                                            }
                                                            if (deepflag == 1) 
                                                            {
                                                                childnodeassign.add(xtempn);
                                                            }
                                                        }
                                                    }
                                                    
                                                } 
                                                else // 0 inducers
                                                {
                                                    for (BGateCombo nbcg : norcombo_0_inputs) //move this!!
                                                    {
                                                        if (childnodeassign.contains(xtempn)) 
                                                        {
                                                            continue;
                                                        }
                                                        if (xtempn.equals(nbcg.Out)) 
                                                        {
                                                            deepflag = 1;
                                                            BGateNode deeprunner = curr;
                                                            while (deeprunner != null) 
                                                            {
                                                                if (deeprunner.bgname.equals(nbcg.Out) || deeprunner.bgname.equals(nbcg.Inp1) || deeprunner.bgname.equals(nbcg.Inp2)) 
                                                                {
                                                                    deepflag = 0;
                                                                    break;
                                                                }
                                                                deeprunner = deeprunner.parent;
                                                            }

                                                            if (deepflag == 1) 
                                                            {
                                                                childnodeassign.add(xtempn);
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                            else if(isInp == 3) //nodes to be added have the gate type not
                                            {
                                                int deepinpno = 0;
                                                Wire deepw = ginp.Outgoing;
                                                while (deepw != null) 
                                                {
                                                    if (deepw.To.Type.equals(GateType.INPUT.toString())) 
                                                    {
                                                        deepinpno++;
                                                    }
                                                    deepw = deepw.Next;
                                                }
                                                String xtempn = tempnodesassign;

                                                int deepflag = 0;
                                                if (deepinpno == 1) // 1 inducer
                                                {
                                                    for (BGateCombo nbcg : notcombo_1_input) //move this
                                                    {
                                                        deepflag = 1;
                                                        if (childnodeassign.contains(xtempn)) 
                                                        {
                                                            continue;
                                                        }
                                                        if (xtempn.equals(nbcg.Out)) //move this
                                                        {
                                                            BGateNode deeprunner = curr;
                                                            while (deeprunner != null) 
                                                            {
                                                                if (deeprunner.bgname.equals(nbcg.Out) || deeprunner.bgname.equals(nbcg.Inp1)) 
                                                                {
                                                                    deepflag = 0;
                                                                    break;
                                                                }
                                                                deeprunner = deeprunner.parent;
                                                            }
                                                            if (deepflag == 1) 
                                                            {
                                                                childnodeassign.add(xtempn);
                                                            }
                                                        }
                                                    }
                                                } 
                                                else //0 inducers
                                                {
                                                    for (BGateCombo nbcg : notcombo_0_inputs) //move this
                                                    {
                                                        deepflag = 1;
                                                        if (childnodeassign.contains(xtempn)) 
                                                        {
                                                            continue;
                                                        }
                                                        if (xtempn.equals(nbcg.Out)) //move this
                                                        {
                                                            BGateNode deeprunner = curr;
                                                            while (deeprunner != null) 
                                                            {
                                                                if (deeprunner.bgname.equals(nbcg.Out) || deeprunner.bgname.equals(nbcg.Inp1)) 
                                                                {
                                                                    deepflag = 0;
                                                                    break;
                                                                }
                                                                deeprunner = deeprunner.parent;
                                                            }
                                                            if (deepflag == 1) 
                                                            {
                                                                childnodeassign.add(xtempn);
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                        //</editor-fold>
                                    }
                                    //</editor-fold>
                                    
                                    //<editor-fold desc="none of the inputs have been assigned">
                                    else // none of the inputs have been assigned
                                    {
                                        int niflag =0;
                                        if(bgc.Out.equals(outrun))
                                        {
                                            if(inpno ==1)
                                            {
                                                if((bgc.Inp1.contains("inducer") && (!bgc.Inp2.contains("inducer"))) ||  ((!bgc.Inp1.contains("inducer")) && (bgc.Inp2.contains("inducer")))) 
                                                {
                                                    niflag =1;
                                                }
                                            }
                                            else if(inpno == 2)
                                            {
                                                if(bgc.Inp1.contains("inducer")&&bgc.Inp2.contains("inducer")) 
                                                {
                                                    niflag =1;
                                                }
                                            }
                                            else
                                            {
                                                if(!(bgc.Inp1.contains("inducer"))&&(!bgc.Inp2.contains("inducer"))) 
                                                {
                                                    niflag =1;
                                                }
                                            }
                                            if(niflag == 1)
                                            {
                                                
                                                int flaginp1=0;
                                                int flaginp2=0;
                                                if(isInp == 1)
                                                {
                                                    String xinp1;
                                                    String xinp2;
                                                    xinp1 = bgc.Inp1;
                                                    xinp2 = bgc.Inp2;
                                                    
                                                    if(xinp1.contains("inducer"))
                                                    {
                                                        if(!childnodeassign.contains(xinp1))
                                                        {
                                                            childnodeassign.add(xinp1);
                                                        }
                                                    }
                                                    if(xinp2.contains("inducer"))
                                                    {
                                                        if(!childnodeassign.contains(xinp2))
                                                        {
                                                            childnodeassign.add(xinp2);
                                                        }
                                                    }
                                                }
                                                
                                                if(isInp == 2) // Nor gate
                                                {
                                                    int deepinpno=0;
                                                    Wire deepw = ginp.Outgoing;
                                                    while(deepw!=null)
                                                    {
                                                        if(deepw.To.Type.equals(GateType.INPUT.toString()))
                                                            deepinpno++;
                                                        deepw = deepw.Next;
                                                    }
                                                            if(deepinpno ==1)
                                                            { // 1 inducer
                                                                for(BGateCombo deepbgc: norcombo_1_input)
                                                                {
                                                                    flaginp1 =1;
                                                                    flaginp2 =1;
                                                                    if(deepbgc.Out.equals(bgc.Inp1))
                                                                    {
                                                                        BGateNode finalrunner = curr;
                                                                        while(finalrunner!=null)
                                                                        {
                                                                            if(finalrunner.bgname.equals(deepbgc.Out) || finalrunner.bgname.equals(deepbgc.Inp1) ||finalrunner.bgname.equals(deepbgc.Inp2))
                                                                            {
                                                                                flaginp1=0;
                                                                                break;
                                                                            }
                                                                            finalrunner = finalrunner.parent;
                                                                        }
                                                                        if(flaginp1==1)
                                                                        {
                                                                            if(!childnodeassign.contains(bgc.Inp1))
                                                                                childnodeassign.add(bgc.Inp1);
                                                                        }
                                                                    }
                                                                    if(deepbgc.Out.equals(bgc.Inp2))
                                                                    {
                                                                        BGateNode finalrunner = curr;
                                                                        while(finalrunner!=null)
                                                                        {
                                                                            if(finalrunner.bgname.equals(deepbgc.Out) || finalrunner.bgname.equals(deepbgc.Inp1) ||finalrunner.bgname.equals(deepbgc.Inp2))
                                                                            {
                                                                                flaginp2=0;
                                                                                break;
                                                                            }
                                                                            finalrunner = finalrunner.parent;
                                                                        }
                                                                        if(flaginp2==1)
                                                                        {
                                                                            if(!childnodeassign.contains(bgc.Inp2))
                                                                                childnodeassign.add(bgc.Inp2);
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                            else if(deepinpno==2)
                                                            { // 2 inducers
                                                                for(BGateCombo deepbgc: norcombo_2_inputs)
                                                                {
                                                                    flaginp1 =1;
                                                                    flaginp2 =1;
                                                                    if(deepbgc.Out.equals(bgc.Inp1))
                                                                    {
                                                                        BGateNode finalrunner = curr;
                                                                        while(finalrunner!=null)
                                                                        {
                                                                            if(finalrunner.bgname.equals(deepbgc.Out) || finalrunner.bgname.equals(deepbgc.Inp1) ||finalrunner.bgname.equals(deepbgc.Inp2))
                                                                            {
                                                                                flaginp1=0;
                                                                                break;
                                                                            }
                                                                            finalrunner = finalrunner.parent;
                                                                        }
                                                                        if(flaginp1==1)
                                                                        {
                                                                            if(!childnodeassign.contains(bgc.Inp1))
                                                                                childnodeassign.add(bgc.Inp1);
                                                                        }
                                                                    }
                                                                    if(deepbgc.Out.equals(bgc.Inp2))
                                                                    {
                                                                        BGateNode finalrunner = curr;
                                                                        while(finalrunner!=null)
                                                                        {
                                                                            if(finalrunner.bgname.equals(deepbgc.Out) || finalrunner.bgname.equals(deepbgc.Inp1) ||finalrunner.bgname.equals(deepbgc.Inp2))
                                                                            {
                                                                                flaginp2=0;
                                                                                break;
                                                                            }
                                                                            finalrunner = finalrunner.parent;
                                                                        }
                                                                        if(flaginp2==1)
                                                                        {
                                                                            if(!childnodeassign.contains(bgc.Inp2))
                                                                                childnodeassign.add(bgc.Inp2);
                                                                        }
                                                                    }
                                                                }
                                                                
                                                            }
                                                            else
                                                            { // 0 inducer
                                                                for(BGateCombo deepbgc: norcombo_0_inputs)
                                                                {
                                                                    flaginp1 =1;
                                                                    flaginp2 =1;
                                                                    if(deepbgc.Out.equals(bgc.Inp1))
                                                                    {
                                                                        BGateNode finalrunner = curr;
                                                                        while(finalrunner!=null)
                                                                        {
                                                                            if(finalrunner.bgname.equals(deepbgc.Out) || finalrunner.bgname.equals(deepbgc.Inp1) ||finalrunner.bgname.equals(deepbgc.Inp2))
                                                                            {
                                                                                flaginp1=0;
                                                                                break;
                                                                            }
                                                                            finalrunner = finalrunner.parent;
                                                                        }
                                                                        if(flaginp1==1)
                                                                        {
                                                                            if(!childnodeassign.contains(bgc.Inp1))
                                                                                childnodeassign.add(bgc.Inp1);
                                                                        }
                                                                    }
                                                                    if(deepbgc.Out.equals(bgc.Inp2))
                                                                    {
                                                                        BGateNode finalrunner = curr;
                                                                        while(finalrunner!=null)
                                                                        {
                                                                            if(finalrunner.bgname.equals(deepbgc.Out) || finalrunner.bgname.equals(deepbgc.Inp1) ||finalrunner.bgname.equals(deepbgc.Inp2))
                                                                            {
                                                                                flaginp2=0;
                                                                                break;
                                                                            }
                                                                            finalrunner = finalrunner.parent;
                                                                        }
                                                                        if(flaginp2==1)
                                                                        {
                                                                            if(!childnodeassign.contains(bgc.Inp2))
                                                                                childnodeassign.add(bgc.Inp2);
                                                                        }
                                                                    }
                                                                }
                                                            }
                                               }
                                                else if(isInp == 3) //Not gate
                                                {
                                                    int deepinpno=0;
                                                    Wire deepw = ginp.Outgoing;
                                                    while(deepw!=null)
                                                    {
                                                        if(deepw.To.Type.equals(GateType.INPUT.toString()))
                                                            deepinpno++;
                                                        deepw = deepw.Next;
                                                    }
                                                    
                                                    if (deepinpno == 1) 
                                                    { // 1 inducer
                                                        for (BGateCombo deepbgc : notcombo_1_input) 
                                                        {
                                                            flaginp1 =1;
                                                            flaginp2 =1;
                                                            if (deepbgc.Out.equals(bgc.Inp1)) 
                                                            {
                                                                BGateNode finalrunner = curr;
                                                                while(finalrunner!=null)
                                                                {
                                                                    if(finalrunner.bgname.equals(deepbgc.Out) || finalrunner.bgname.equals(deepbgc.Inp1))
                                                                    {
                                                                        flaginp1=0;
                                                                        break;
                                                                    }
                                                                    finalrunner = finalrunner.parent;
                                                                }
                                                                if(flaginp1==1)
                                                                {
                                                                    if(!childnodeassign.contains(bgc.Inp1))
                                                                        childnodeassign.add(bgc.Inp1);
                                                                }
                                                            }
                                                            if (deepbgc.Out.equals(bgc.Inp2)) 
                                                            {
                                                                BGateNode finalrunner = curr;
                                                                while(finalrunner!=null)
                                                                {
                                                                    if(finalrunner.bgname.equals(deepbgc.Out) || finalrunner.bgname.equals(deepbgc.Inp2)) //this line was changed
                                                                    {
                                                                        flaginp2=0;
                                                                        break;
                                                                    }
                                                                    finalrunner = finalrunner.parent;
                                                                }
                                                                if(flaginp2==1)
                                                                {
                                                                    if(!childnodeassign.contains(bgc.Inp2))
                                                                        childnodeassign.add(bgc.Inp2);
                                                                }
                                                            }
                                                        }
                                                    } 
                                                    else 
                                                    { // 0 inducers
                                                        for (BGateCombo deepbgc : notcombo_0_inputs) 
                                                        {
                                                            flaginp1 =1;
                                                            flaginp2 =1;
                                                            if (deepbgc.Out.equals(bgc.Inp1)) 
                                                            {
                                                                BGateNode finalrunner = curr;
                                                                while(finalrunner!=null)
                                                                {
                                                                    if(finalrunner.bgname.equals(deepbgc.Out) || finalrunner.bgname.equals(deepbgc.Inp1))
                                                                    {
                                                                        flaginp1=0;
                                                                        break;
                                                                    }
                                                                    finalrunner = finalrunner.parent;
                                                                }
                                                                if(flaginp1==1)
                                                                {
                                                                    if(!childnodeassign.contains(bgc.Inp1))
                                                                        childnodeassign.add(bgc.Inp1);
                                                                }
                                                            }
                                                            if (deepbgc.Out.equals(bgc.Inp2)) 
                                                            {
                                                                BGateNode finalrunner = curr;
                                                                while(finalrunner!=null)
                                                                {
                                                                    if(finalrunner.bgname.equals(deepbgc.Out) || finalrunner.bgname.equals(deepbgc.Inp2)) //this line was changed
                                                                    {
                                                                        flaginp2=0;
                                                                        break;
                                                                    }
                                                                    finalrunner = finalrunner.parent;
                                                                }
                                                                if(flaginp2==1)
                                                                {
                                                                    if(!childnodeassign.contains(bgc.Inp2))
                                                                        childnodeassign.add(bgc.Inp2);
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    //</editor-fold>
                                }
                        //</editor-fold>    
                        }
                        else if(runner.bgate.Type.equals(GateType.NOT.toString()))
                        {
                            
                            //<editor-fold desc="Current node is an input to a NOT Gate">
                            
                            String outrun = runner.bgname;
                            //String inp1run = "";
                            int found=0;
                            
                            
                                Wire wcheck = runner.bgate.Outgoing;
                                int inpno=0;
                                while(wcheck!=null)
                                {
                                    if(wcheck.To.Type.equals(GateType.INPUT.toString()))
                                        inpno++; 
                                    wcheck = wcheck.Next;
                                }
                                
                                for(BGateCombo bgc:notcombos)
                                {
                                    //<editor-fold desc="none of the inputs have been assigned">
                                       
                                        int niflag =0;
                                        if(bgc.Out.equals(outrun))
                                        {
                                            if(inpno ==1)
                                            {
                                                if(bgc.Inp1.contains("inducer")) 
                                                {
                                                    niflag =1;
                                                    //System.out.println("Valid Combo!");
                                                }
                                            }
                                            else
                                            {
                                                if(!(bgc.Inp1.contains("inducer"))) 
                                                {
                                                    niflag =1;
                                                }
                                            }
                                            if(niflag == 1)
                                            {
                                                int flaginp1=0;
                                                
                                                if(isInp == 1)
                                                {
                                                    BGateNode finalinprunner = curr;
                                                    int finalinpflag =0;
                                                    if(!childnodeassign.contains(bgc.Inp1))
                                                    {
                                                        while(finalinprunner!=null)
                                                        {
                                                            if(finalinprunner.bgname.equals(bgc.Inp1))
                                                            {
                                                                finalinpflag =1;
                                                                break;
                                                            }
                                                            finalinprunner = finalinprunner.parent;
                                                        }
                                                        if(finalinpflag ==0)
                                                            childnodeassign.add(bgc.Inp1);
                                                    }
                                                }
                                                
                                                if(isInp == 2) // Nor gate
                                                {
                                                    int deepinpno=0;
                                                    Wire deepw = ginp.Outgoing;
                                                    while(deepw!=null)
                                                    {
                                                        if(deepw.To.Type.equals(GateType.INPUT.toString()))
                                                            deepinpno++;
                                                        deepw = deepw.Next;
                                                    }
                                                    
                                                            if(deepinpno ==1)
                                                            { // 1 inducer
                                                                for(BGateCombo deepbgc: norcombo_1_input)
                                                                {
                                                                    flaginp1 =1;
                                                                    if(childnodeassign.contains(bgc.Inp1))
                                                                    {
                                                                        continue;
                                                                    }
                                                                    if(deepbgc.Out.equals(bgc.Inp1))
                                                                    {
                                                                        BGateNode finalrunner = curr;
                                                                        while(finalrunner!=null)
                                                                        {
                                                                            if(finalrunner.bgname.equals(deepbgc.Out) || finalrunner.bgname.equals(deepbgc.Inp1) ||finalrunner.bgname.equals(deepbgc.Inp2))
                                                                            {
                                                                                flaginp1=0;
                                                                                break;
                                                                            }
                                                                            finalrunner = finalrunner.parent;
                                                                        }
                                                                        if(flaginp1==1)
                                                                        {
                                                                            childnodeassign.add(bgc.Inp1);
                                                                        }
                                                                    }
                                                                }   
                                                            }
                                                            else if(deepinpno==2)
                                                            { // 2 inducers
                                                                for(BGateCombo deepbgc: norcombo_2_inputs)
                                                                {
                                                                    flaginp1 =1;
                                                                    if(childnodeassign.contains(bgc.Inp1))
                                                                    {
                                                                        continue;
                                                                    }
                                                                    if(deepbgc.Out.equals(bgc.Inp1))
                                                                    {
                                                                        BGateNode finalrunner = curr;
                                                                        while(finalrunner!=null)
                                                                        {
                                                                            if(finalrunner.bgname.equals(deepbgc.Out) || finalrunner.bgname.equals(deepbgc.Inp1) ||finalrunner.bgname.equals(deepbgc.Inp2))
                                                                            {
                                                                                flaginp1=0;
                                                                                break;
                                                                            }
                                                                            finalrunner = finalrunner.parent;
                                                                        }
                                                                        if(flaginp1==1)
                                                                        {
                                                                            childnodeassign.add(bgc.Inp1);
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                            else
                                                            { // 0 inducers
                                                                for(BGateCombo deepbgc: norcombo_0_inputs)
                                                                {
                                                                    flaginp1 =1;
                                                                    if(childnodeassign.contains(bgc.Inp1))
                                                                    {
                                                                        continue;
                                                                    }
                                                                    if(deepbgc.Out.equals(bgc.Inp1))
                                                                    {
                                                                        BGateNode finalrunner = curr;
                                                                        while(finalrunner!=null)
                                                                        {
                                                                            if(finalrunner.bgname.equals(deepbgc.Out) || finalrunner.bgname.equals(deepbgc.Inp1) ||finalrunner.bgname.equals(deepbgc.Inp2))
                                                                            {
                                                                                flaginp1=0;
                                                                                break;
                                                                            }
                                                                            finalrunner = finalrunner.parent;
                                                                        }
                                                                        if(flaginp1==1)
                                                                        {
                                                                            childnodeassign.add(bgc.Inp1);
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                }
                                                else if(isInp == 3) //Not gate
                                                {
                                                    int deepinpno=0;
                                                    Wire deepw = ginp.Outgoing;
                                                    while(deepw!=null)
                                                    {
                                                        if(deepw.To.Type.equals(GateType.INPUT.toString()))
                                                            deepinpno++;
                                                        deepw = deepw.Next;
                                                    }
                                                    
                                                            if(deepinpno ==1)
                                                            { // 1 inducer
                                                                
                                                                for(BGateCombo deepbgc: notcombo_1_input)
                                                                {
                                                                    if(childnodeassign.contains(bgc.Inp1))
                                                                        continue;
                                                                    flaginp1 =1;
                                                                    if(deepbgc.Out.equals(bgc.Inp1))
                                                                    {
                                                                        BGateNode finalrunner = curr;
                                                                        while(finalrunner!=null)
                                                                        {
                                                                            if(finalrunner.bgname.equals(deepbgc.Out) || finalrunner.bgname.equals(deepbgc.Inp1))
                                                                            {
                                                                                flaginp1=0;
                                                                                break;
                                                                            }
                                                                            finalrunner = finalrunner.parent;
                                                                        }
                                                                        if(flaginp1==1)
                                                                        {
                                                                            childnodeassign.add(bgc.Inp1);
                                                                        }
                                                                    }
                                                                }    
                                                            }
                                                            else
                                                            { // 0 inducers
                                                                for(BGateCombo deepbgc: notcombo_0_inputs)
                                                                {
                                                                    if(childnodeassign.contains(bgc.Inp1))
                                                                        continue;
                                                                    flaginp1 =1;
                                                                    if(deepbgc.Out.equals(bgc.Inp1))
                                                                    {
                                                                        BGateNode finalrunner = curr;
                                                                        while(finalrunner!=null)
                                                                        {
                                                                            if(finalrunner.bgname.equals(deepbgc.Out) || finalrunner.bgname.equals(deepbgc.Inp1))
                                                                            {
                                                                                flaginp1=0;
                                                                                break;
                                                                            }
                                                                            finalrunner = finalrunner.parent;
                                                                        }
                                                                        if(flaginp1==1)
                                                                        {
                                                                            childnodeassign.add(bgc.Inp1);
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        
                                                        
                                                            
                                                        
                                                    }
                                                
                                            }
                                        }
                                    //</editor-fold>
                                }
                        //</editor-fold>    
                        }
                    }
                    }
                    if (childnodeassign.isEmpty()) 
                    {
                        curr.ncolor = nodecolor.BLACK;
                        if (curr.Next != null) 
                        {
                            curr = curr.Next;
                        } 
                        else 
                        {
                            curr = curr.parent;
                        }
                    } 
                    else 
                    {
                        List<BGateNode> nodeschildren = new ArrayList<BGateNode>();
                        for (int i = 0; i < childnodeassign.size(); i++) 
                        {
                            BGateNode childcurr = new BGateNode(null, null, curr, nodecolor.WHITE, nodesCirc.get(curr.index - 1), childnodeassign.get(i), (curr.index - 1));
                            nodeschildren.add(childcurr);
                        }
                        for (int i = 0; i < nodeschildren.size() - 1; i++) 
                        {
                            nodeschildren.get(i).Next = nodeschildren.get(i + 1);
                        }
                        curr.child = nodeschildren.get(0);
                        
                        /*BGateNode childcheck = curr.child;
                        while(childcheck!=null)
                        {
                            System.out.println(childcheck.index + "" + childcheck.bgname);
                            childcheck = childcheck.Next;
                        }*/
                        
                        if (curr.child != null) 
                        {
                            curr = curr.child;
                            //System.out.println("Curr " + curr.index);
                        } 
                        else if (curr.Next != null) 
                        {
                            curr = curr.Next;
                        } 
                        else 
                        {
                            curr = curr.parent;
                        }
                        //System.out.println("reached here!!");
                    }
                }
                //Comment this out
                //break;
            }
            //</editor-fold>
            
            else if(curr.ncolor == nodecolor.GRAY)
            {
                
                    BGateNode subrunnercolor = curr.child;
                    int colorflag =0;
                    while(subrunnercolor!=null)
                    {
                        if(subrunnercolor.ncolor != nodecolor.BLACK)
                        {
                            colorflag =1;
                            break;
                        }
                        subrunnercolor = subrunnercolor.Next;
                    }
                    if(colorflag ==1)
                        curr = curr.child;
                    else
                    {
                        curr.ncolor = nodecolor.BLACK;
                        if(curr.Next != null)
                            curr = curr.Next;
                        else
                            curr = curr.parent;
                    }
                
           }
            else if(curr.ncolor == nodecolor.BLACK)
            {
                if(curr.Next == null && curr.index == (xindx-1))
                {
                    int colorflag =0;
                    BGateNode runnercolor = curr.parent.child;
                    while(runnercolor.Next!=null)
                    {
                        if(runnercolor.ncolor != nodecolor.BLACK)
                        {
                            colorflag =1;
                            break;
                        }
                        runnercolor = runnercolor.Next;
                    }
                    if(colorflag == 0)
                    {
                        curr.ncolor = nodecolor.BLACK;
                        curr = curr.parent;
                    }
                    else 
                        curr = runnercolor;
                }
                else
                {
                if(curr.Next != null)
                    curr = curr.Next;
                else
                    curr = curr.parent;
            
                }
            }     
            
        }
        //System.out.println(combinations.size());
        System.out.println(assignmentresult.assignment.size());
        //genindexGraph(indices);
        
        
    return assignmentresult;  
    }
    
    
    public static double ScoreCircuit(DAGW dagCirc, HashMap<Integer,String> gateassign, HashMap<String,TransferFunction> gatefunc, HashMap<String,TransferFunction> inpfunc)
    {
        double score=0;
        List<Double> pminList = new ArrayList<Double>();
        List<Double> pmaxList = new ArrayList<Double>();
        
        for(int i=0;i<dagCirc.Gates.size();i++)
        {
            pminList.add(0.0);
            pmaxList.add(0.0);
            
        }
        for(int i=0;i<dagCirc.Gates.size();i++)
        {
            int gind = dagCirc.Gates.get(i).Index;
            if(dagCirc.Gates.get(i).Type.equals(GateType.INPUT.toString()))
            {
                pminList.set(gind, inpfunc.get(gatefunc.get(gind)).pmin);
                pmaxList.set(gind, inpfunc.get(gatefunc.get(gind)).pmax);
            }
            else if(dagCirc.Gates.get(i).Type.equals(GateType.OUTPUT.toString()) || dagCirc.Gates.get(i).Type.equals(GateType.OUTPUT_OR.toString()))
            {
                if(dagCirc.Gates.get(i).Type.equals(GateType.OUTPUT.toString()))
                {
                    int prevind = dagCirc.Gates.get(i).Outgoing.To.Index;
                    score = 1 - (pminList.get(prevind)/pmaxList.get(prevind));
                    
                }
                else
                {
                    int prevind1 = dagCirc.Gates.get(i).Outgoing.To.Index;
                    int prevind2 = dagCirc.Gates.get(i).Outgoing.Next.To.Index;
                    score = 1 - ((pminList.get(prevind1) + pminList.get(prevind2)) /  (pmaxList.get(prevind1) + pmaxList.get(prevind2))  );
                    
                }
            }
            else
            {
                if(dagCirc.Gates.get(i).Type.equals(GateType.NOT.toString()))
                {
                    int prevind = dagCirc.Gates.get(i).Outgoing.To.Index;
                    TransferFunction tempnot = new TransferFunction();
                    tempnot = gatefunc.get(gateassign.get(gind));
                    
                    double score0 = ScoreGate(tempnot.pmin, tempnot.pmax, tempnot.kd, tempnot.n, pminList.get(prevind));
                    double score1 = ScoreGate(tempnot.pmin, tempnot.pmax, tempnot.kd, tempnot.n, pmaxList.get(prevind));
                    pminList.set(gind, score1);
                    pmaxList.set(gind, score0);
                    
                    
                }
                else if(dagCirc.Gates.get(i).Type.equals(GateType.NOR.toString()))
                {
                    int prevind1 = dagCirc.Gates.get(i).Outgoing.To.Index;
                    int prevind2 = dagCirc.Gates.get(i).Outgoing.Next.To.Index;
                    TransferFunction tempnot = new TransferFunction();
                    tempnot = gatefunc.get(gateassign.get(gind));
                    
                    double score00 = ScoreGate(tempnot.pmin, tempnot.pmax, tempnot.kd, tempnot.n, (pminList.get(prevind1) + pminList.get(prevind2)));
                    double score01 = ScoreGate(tempnot.pmin, tempnot.pmax, tempnot.kd, tempnot.n, (pminList.get(prevind1) + pmaxList.get(prevind2)));
                    double score10 = ScoreGate(tempnot.pmin, tempnot.pmax, tempnot.kd, tempnot.n, (pmaxList.get(prevind1) + pminList.get(prevind2)));
                    double score11 = ScoreGate(tempnot.pmin, tempnot.pmax, tempnot.kd, tempnot.n, (pmaxList.get(prevind1) + pmaxList.get(prevind2)));
                    
                    double maxscore = score01;
                    if(score10 > maxscore)
                        maxscore = score10;
                    if(score11 > maxscore)
                        maxscore = score11;
                    
                    pminList.set(gind, maxscore);
                    pmaxList.set(gind, score00);
                }
            }
        }
        return score;
    }
    
    public static double ScoreGate(double pmin, double pmax, double kd, double n, double x)
    {
        double score=0;
        double term1 = x/kd;
        double term2 = Math.pow(term1, n);
        double term3 = 1 + term2;
        double term4 = pmax/term3;
        score = pmin + term4;
    
        return score;
    }
    
    
    public static DAGW getFinalDAG(DAGW dagCirc)
    {
        int indx = dagCirc.Gates.size()-1;
        
        //<editor-fold desc="Reconnecting DAGW in memory">
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
        //</editor-fold>
        
        //<editor-fold desc="Assiging Gate Stages"> 
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
        //</editor-fold>
        
        int maxstage = dagCirc.Gates.get(0).stage;
        
        
        int xstage =0;
        int xindx =0;
        
        //<editor-fold desc="Reindexing Gates">
        while(xstage <= maxstage)
        {
            for(Gate bgate:dagCirc.Gates)
            {
                if(bgate.stage == xstage)
                {
                    //nodesCirc.put(xindx,bgate);
                    bgate.Index = xindx;
                    xindx++;
                }
            }
            xstage++;
        }
        //</editor-fold>
        
        
        return dagCirc;
    }
    
    
    public static void genindexGraph(List<Integer> indexlist)
    { 
        
        String Filepath;
        Filepath = HeuristicSearch.class.getClassLoader().getResource(".").getPath();
        if(Filepath.contains("build/classes/"))
            Filepath = Filepath.substring(0,Filepath.lastIndexOf("build/classes/")); 
        else if(Filepath.contains("src"))
            Filepath = Filepath.substring(0,Filepath.lastIndexOf("src/"));
        
          String filestring ="";
          if(Filepath.contains("prashant"))
          {
              filestring += Filepath+ "src/BU/resources/IndexGraph";
          }
          else
          {
              filestring += Filepath+ "BU/resources/IndexGraph";
          }
        
          
            //filestring += Global.espout++ ;
            filestring += ".csv";
            File fespinp = new File(filestring);
        try 
        {
            Writer output = new BufferedWriter(new FileWriter(fespinp));
            String Line = "Index,NodeIndex\n";
            output.write(Line);
            
            for(int i=0;i<indexlist.size();i++)
                output.write(i +","+indexlist.get(i)+"\n");
            //for(int i=0;i<10000;i++)
            //    output.write(i +","+indexlist.get(i)+"\n");
            
            output.close();
        
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(HeuristicSearch.class.getName()).log(Level.SEVERE, null, ex);
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
    
    
    
    
    public static HashMap<String,TransferFunction> getGateFunction()
    {

	String Filepath ="";
        HashMap<String,TransferFunction> gatefunctions = new HashMap<String,TransferFunction>();
        
	Filepath = HeuristicSearch.class.getClassLoader().getResource(".").getPath();
        if(Filepath.contains("build/classes/"))
            Filepath = Filepath.substring(0,Filepath.lastIndexOf("build/classes/")); 
        else if(Filepath.contains("src"))
            Filepath = Filepath.substring(0,Filepath.lastIndexOf("src/"));
       
        if(Filepath.contains("prashant"))
        {
            Filepath += "src/MIT/dnacompiler/";
        }
        else
        {
            Filepath += "MIT/dnacompiler/";
        }

	String file_gates = Filepath + "TransferFunc.txt";
	
	File filegates = new File(file_gates);
	BufferedReader brgate;
	FileReader frgate;
	
	try {
	    frgate = new FileReader(filegates);
	    brgate = new BufferedReader(frgate);
	    String line;
	    try 
            {
		while((line = brgate.readLine()) != null ) 
                {
	            String lines[] = line.split(" ");
                    TransferFunction entry = new TransferFunction();
                    entry.bgname = lines[0];
                    entry.pmax = Double.parseDouble(lines[1]);
                    entry.pmin = Double.parseDouble(lines[2]);
                    entry.kd = Double.parseDouble(lines[3]);
                    entry.n = Double.parseDouble(lines[4]);
                    gatefunctions.put(entry.bgname, entry);
                }
	    }
	    catch (IOException ex) {
		System.out.println("IOException when reading input file");
	    }
	} 
	catch (FileNotFoundException ex) {
	    System.out.println("FileNotFoundException when reading input file");
	}

        
        return gatefunctions;

    }
    public static HashMap<String,TransferFunction> getInpFunction()
    {

	String Filepath ="";
        HashMap<String,TransferFunction> inpfunctions = new HashMap<String,TransferFunction>();
        
	Filepath = HeuristicSearch.class.getClassLoader().getResource(".").getPath();
        if(Filepath.contains("build/classes/"))
            Filepath = Filepath.substring(0,Filepath.lastIndexOf("build/classes/")); 
        else if(Filepath.contains("src"))
            Filepath = Filepath.substring(0,Filepath.lastIndexOf("src/"));
       
        if(Filepath.contains("prashant"))
        {
            Filepath += "src/MIT/dnacompiler/";
        }
        else
        {
            Filepath += "MIT/dnacompiler/";
        }

	String file_inputs = Filepath + "InpFunc.txt";

	
        
        File fileinp = new File(file_inputs);
	BufferedReader brinp;
	FileReader frinp;
	try {
	    frinp = new FileReader(fileinp);
	    brinp = new BufferedReader(frinp);
	    String line;
	    try {
		while((line = brinp.readLine()) != null ) {
	
		    String lines[] = line.split(" ");
                    TransferFunction entry = new TransferFunction();
                    entry.bgname = lines[0];
                    entry.pmax = Double.parseDouble(lines[1]);
                    entry.pmin = Double.parseDouble(lines[2]);
                    inpfunctions.put(entry.bgname, entry);
		}
	    }
	    catch (IOException ex) {
		System.out.println("IOException when reading input file");
	    }
	} 
	catch (FileNotFoundException ex) {
	    System.out.println("FileNotFoundException when reading input file");
	}
        return inpfunctions;

    }
    
    
    
}
