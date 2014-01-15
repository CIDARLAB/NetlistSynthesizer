/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MIT.dnacompiler;

import BU.CelloGraph.DAGW;
import MIT.dnacompiler.BGateNode.nodecolor;
import MIT.dnacompiler.Gate.GateType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
        //LoadTables.printallCombos(norcombos);
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
        /*for(Gate xg:dagCirc.Gates)
        {
            System.out.println(xg.Name + ":" + xg.Index);
        }*/
        
        
        List<HashMap<Integer,BGateNode>> combinations = new ArrayList<HashMap<Integer,BGateNode>>();
        
        //List<Pair<Integer,Wire>> nodequeue = new ArrayList<Pair<Integer,Wire>>();
        
        //Start Heuristic Search Algo
        while (curr != null) 
        {
            
            if (curr.ncolor == nodecolor.WHITE) 
            {

                curr.ncolor = nodecolor.GRAY;
                if (curr.index == 0) 
                {
                    curr.ncolor = nodecolor.BLACK;
                    BGateNode runner = curr;
                    HashMap<Integer, BGateNode> assign = new HashMap<Integer, BGateNode>();
                    while (runner != null) 
                    {
                        assign.put(runner.index, runner);
                        System.out.println(runner.index + ":" +runner.bgname);
                        runner = runner.parent;
                    }
                    combinations.add(assign);
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
                    if(runner.bgate.Type.equals(GateType.OUTPUT.toString()) || runner.bgate.Type.equals(GateType.OUTPUT_OR.toString()))
                    {
                        if(nodesCirc.get(next_indx).Type.equals(GateType.NOR.toString()))
                        {
                            int inpno=0;
                            Wire nextw = nodesCirc.get(next_indx).Outgoing;
                            while(nextw!=null)
                            {
                                if(nextw.To.Type.equals(GateType.INPUT.toString()))
                                    inpno++;
                                nextw = nextw.Next;
                            }
                            for (BGateCombo xbgc : norcombos) 
                            {
                                int comboflag =0;
                                if(inpno == 1)
                                {
                                    if(xbgc.Inp1.contains("inducer")||xbgc.Inp2.contains("inducer"))
                                    {
                                        comboflag =1;
                                    }
                                }
                                else if(inpno ==2)
                                {
                                    if(xbgc.Inp1.contains("inducer")&&xbgc.Inp2.contains("inducer"))
                                    {
                                        comboflag =1;
                                    }
                                }
                                else
                                {
                                    if((!xbgc.Inp1.contains("inducer")) && (!xbgc.Inp2.contains("inducer")))
                                    {
                                        comboflag =1;
                                    }
                                }
                                BGateNode subrunner = curr;
                                if (comboflag == 1) 
                                {
                                    while (subrunner != null) 
                                    {
                                        if (subrunner.bgname.equals(xbgc.Inp1) || subrunner.bgname.equals(xbgc.Inp2) || subrunner.bgname.equals(xbgc.Out)) 
                                        {
                                            comboflag = 0;
                                            break;
                                        }
                                        subrunner = subrunner.parent;
                                    }
                                }
                                if(comboflag ==1)
                                {
                                    childnodeassign.add(xbgc.Out);
                                }
                            }
                            
                            
                        }
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
                            for (BGateCombo xbgc : norcombos) 
                            {
                                int comboflag =0;
                                if(inpno == 1)
                                {
                                    if(xbgc.Inp1.contains("inducer"))
                                    {
                                        comboflag =1;
                                    }
                                }
                                else
                                {
                                    if((!xbgc.Inp1.contains("inducer")))
                                    {
                                        comboflag =1;
                                    }
                                }
                                BGateNode subrunner = curr;
                                if (comboflag == 1) 
                                {
                                    while (subrunner != null) 
                                    {
                                        if (subrunner.bgname.equals(xbgc.Inp1) || subrunner.bgname.equals(xbgc.Out)) 
                                        {
                                            comboflag = 0;
                                            break;
                                        }
                                        subrunner = subrunner.parent;
                                    }
                                }
                                if(comboflag ==1)
                                {
                                    childnodeassign.add(xbgc.Out);
                                }
                            }
                        }
                    }
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
                                                    if (bgc.Inp1.contains("inducer")) 
                                                    {

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
                                                    }
                                                }
                                            }
                                            
                                            if(isInp == 2) // nodes to be added have gate of type nor
                                            {
                                                int deepinpno =0;
                                                Wire deepw = ginp.Outgoing;
                                                while(deepw!=null)
                                                {
                                                    if(deepw.To.Type.equals(GateType.INPUT.toString()))
                                                        deepinpno++;
                                                    deepw = deepw.Next;
                                                }
                                                String xtempn = tempnodesassign;
                                                
                                                    for(BGateCombo nbcg:norcombos)
                                                    {
                                                        if(xtempn.equals(nbcg.Out))
                                                        {
                                                            int deepflag =0;
                                                            if(deepinpno==1)
                                                            {
                                                                if(nbcg.Inp1.contains("inducer") || nbcg.Inp2.contains("inducer"))
                                                                    deepflag =1;
                                                            }
                                                            else if(deepinpno == 2)
                                                            {
                                                                if(nbcg.Inp1.contains("inducer") && nbcg.Inp2.contains("inducer"))
                                                                    deepflag =1;
                                                                
                                                            }
                                                            else 
                                                            {
                                                                if((!nbcg.Inp1.contains("inducer")) && (!nbcg.Inp2.contains("inducer")))
                                                                    deepflag =1;
                                                            }
                                                            if(deepflag == 1)
                                                            {
                                                                BGateNode deeprunner = curr;
                                                                while(deeprunner!=null)
                                                                {
                                                                    if(deeprunner.bgname.equals(nbcg.Out) ||deeprunner.bgname.equals(nbcg.Inp1)||deeprunner.bgname.equals(nbcg.Inp2))
                                                                    {
                                                                        deepflag =0;
                                                                        break;
                                                                    }
                                                                    deeprunner = deeprunner.parent;
                                                                }
                                                            }
                                                        }
                                                        if(!childnodeassign.contains(xtempn))
                                                            childnodeassign.add(xtempn);
                                                    }
                                                
                                            }
                                            else if(isInp == 3) //nodes to be added have the gate type not
                                            {
                                                int deepinpno =0;
                                                Wire deepw = ginp.Outgoing;
                                                while(deepw!=null)
                                                {
                                                    if(deepw.To.Type.equals(GateType.INPUT.toString()))
                                                        deepinpno++;
                                                    deepw = deepw.Next;
                                                }
                                                String xtempn = tempnodesassign;

                                                for (BGateCombo nbcg : notcombos) 
                                                {
                                                    if (xtempn.equals(nbcg.Out)) 
                                                    {
                                                        int deepflag = 0;
                                                        if (deepinpno == 1) 
                                                        {
                                                            if (nbcg.Inp1.contains("inducer")) 
                                                            {
                                                                deepflag = 1;
                                                            }
                                                        } 
                                                        else 
                                                        {
                                                            if ((!nbcg.Inp1.contains("inducer"))) 
                                                            {
                                                                deepflag = 1;
                                                            }
                                                        }
                                                        if (deepflag == 1) 
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
                                                        }
                                                    }
                                                    if (!childnodeassign.contains(xtempn)) 
                                                    {
                                                        childnodeassign.add(xtempn);
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
                                                if(bgc.Inp1.contains("inducer")||bgc.Inp2.contains("inducer")) 
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
                                                    for(BGateCombo deepbgc: norcombos)
                                                    {
                                                        if(deepbgc.Out.equals(bgc.Inp1))
                                                        {
                                                            if(deepinpno ==1)
                                                            {
                                                                if(deepbgc.Inp1.contains("inducer") || deepbgc.Inp2.contains("inducer"))
                                                                {
                                                                    flaginp1=1;
                                                                }
                                                            }
                                                            else if(deepinpno==2)
                                                            {
                                                                if(deepbgc.Inp1.contains("inducer") && deepbgc.Inp2.contains("inducer"))
                                                                {
                                                                    flaginp1=1;
                                                                }
                                                            }
                                                            else
                                                            {
                                                                if((!deepbgc.Inp1.contains("inducer")) && (!deepbgc.Inp2.contains("inducer")))
                                                                {
                                                                    flaginp1=1;
                                                                }
                                                            }
                                                        }
                                                        if(deepbgc.Out.equals(bgc.Inp2))
                                                        {
                                                            if(deepinpno ==1)
                                                            {
                                                                if(deepbgc.Inp1.contains("inducer") || deepbgc.Inp2.contains("inducer"))
                                                                {
                                                                    flaginp2=1;
                                                                }
                                                            }
                                                            else if(deepinpno==2)
                                                            {
                                                                if(deepbgc.Inp1.contains("inducer") && deepbgc.Inp2.contains("inducer"))
                                                                {
                                                                    flaginp2=1;
                                                                }
                                                            }
                                                            else
                                                            {
                                                                if((!deepbgc.Inp1.contains("inducer")) && (!deepbgc.Inp2.contains("inducer")))
                                                                {
                                                                    flaginp2=1;
                                                                }
                                                            }
                                                        }
                                                        if(flaginp1 ==1)
                                                        {
                                                            BGateNode finalrunner = curr;
                                                            while(finalrunner!=null)
                                                            {
                                                                if(finalrunner.bgname.equals(deepbgc.Out) || finalrunner.bgname.equals(deepbgc.Inp1) ||finalrunner.bgname.equals(deepbgc.Inp2))
                                                                {
                                                                    flaginp1=0;
                                                                    break;
                                                                }
                                                            }
                                                            if(flaginp1==1)
                                                            {
                                                                if(!childnodeassign.contains(bgc.Inp1))
                                                                    childnodeassign.add(bgc.Inp1);
                                                            }
                                                        }
                                                        if(flaginp2 ==1)
                                                        {
                                                            BGateNode finalrunner = curr;
                                                            while(finalrunner!=null)
                                                            {
                                                                if(finalrunner.bgname.equals(deepbgc.Out) || finalrunner.bgname.equals(deepbgc.Inp1) ||finalrunner.bgname.equals(deepbgc.Inp2))
                                                                {
                                                                    flaginp2=0;
                                                                    break;
                                                                }
                                                            }
                                                            if(flaginp2==1)
                                                            {
                                                                if(!childnodeassign.contains(bgc.Inp2))
                                                                    childnodeassign.add(bgc.Inp2);
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
                                                    for(BGateCombo deepbgc: notcombos)
                                                    {
                                                        if(deepbgc.Out.equals(bgc.Inp1))
                                                        {
                                                            if(deepinpno ==1)
                                                            {
                                                                if(deepbgc.Inp1.contains("inducer"))
                                                                {
                                                                    flaginp1=1;
                                                                }
                                                            }
                                                            else
                                                            {
                                                                if((!deepbgc.Inp1.contains("inducer")))
                                                                {
                                                                    flaginp1=1;
                                                                }
                                                            }
                                                        }
                                                        if(deepbgc.Out.equals(bgc.Inp2))
                                                        {
                                                            if(deepinpno ==1)
                                                            {
                                                                if(deepbgc.Inp1.contains("inducer"))
                                                                {
                                                                    flaginp2=1;
                                                                }
                                                            }
                                                            else
                                                            {
                                                                if((!deepbgc.Inp1.contains("inducer")))
                                                                {
                                                                    flaginp2=1;
                                                                }
                                                            }
                                                        }
                                                        if(flaginp1 ==1)
                                                        {
                                                            BGateNode finalrunner = curr;
                                                            while(finalrunner!=null)
                                                            {
                                                                if(finalrunner.bgname.equals(deepbgc.Out) || finalrunner.bgname.equals(deepbgc.Inp1))
                                                                {
                                                                    flaginp1=0;
                                                                    break;
                                                                }
                                                            }
                                                            if(flaginp1==1)
                                                            {
                                                                if(!childnodeassign.contains(bgc.Inp1))
                                                                    childnodeassign.add(bgc.Inp1);
                                                            }
                                                        }
                                                        if(flaginp2 ==1)
                                                        {
                                                            BGateNode finalrunner = curr;
                                                            while(finalrunner!=null)
                                                            {
                                                                if(finalrunner.bgname.equals(deepbgc.Out) || finalrunner.bgname.equals(deepbgc.Inp1))
                                                                {
                                                                    flaginp2=0;
                                                                    break;
                                                                }
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
                                                    for(BGateCombo deepbgc: norcombos)
                                                    {
                                                        if(deepbgc.Out.equals(bgc.Inp1))
                                                        {
                                                            if(deepinpno ==1)
                                                            {
                                                                if(deepbgc.Inp1.contains("inducer") || deepbgc.Inp2.contains("inducer"))
                                                                {
                                                                    flaginp1=1;
                                                                }
                                                            }
                                                            else if(deepinpno==2)
                                                            {
                                                                if(deepbgc.Inp1.contains("inducer") && deepbgc.Inp2.contains("inducer"))
                                                                {
                                                                    flaginp1=1;
                                                                }
                                                            }
                                                            else
                                                            {
                                                                if((!deepbgc.Inp1.contains("inducer")) && (!deepbgc.Inp2.contains("inducer")))
                                                                {
                                                                    flaginp1=1;
                                                                }
                                                            }
                                                        }
                                                        if(flaginp1 ==1)
                                                        {
                                                            BGateNode finalrunner = curr;
                                                            while(finalrunner!=null)
                                                            {
                                                                if(finalrunner.bgname.equals(deepbgc.Out) || finalrunner.bgname.equals(deepbgc.Inp1) ||finalrunner.bgname.equals(deepbgc.Inp2))
                                                                {
                                                                    flaginp1=0;
                                                                    break;
                                                                }
                                                            }
                                                            if(flaginp1==1)
                                                            {
                                                                if(!childnodeassign.contains(bgc.Inp1))
                                                                    childnodeassign.add(bgc.Inp1);
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
                                                    for(BGateCombo deepbgc: notcombos)
                                                    {
                                                        if(deepbgc.Out.equals(bgc.Inp1))
                                                        {
                                                            if(deepinpno ==1)
                                                            {
                                                                if(deepbgc.Inp1.contains("inducer"))
                                                                {
                                                                    flaginp1=1;
                                                                }
                                                            }
                                                            else
                                                            {
                                                                if((!deepbgc.Inp1.contains("inducer")))
                                                                {
                                                                    flaginp1=1;
                                                                }
                                                            }
                                                        }
                                                        if(flaginp1 ==1)
                                                        {
                                                            BGateNode finalrunner = curr;
                                                            while(finalrunner!=null)
                                                            {
                                                                if(finalrunner.bgname.equals(deepbgc.Out) || finalrunner.bgname.equals(deepbgc.Inp1))
                                                                {
                                                                    flaginp1=0;
                                                                    break;
                                                                }
                                                            }
                                                            if(flaginp1==1)
                                                            {
                                                                if(!childnodeassign.contains(bgc.Inp1))
                                                                    childnodeassign.add(bgc.Inp1);
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
                        if (curr.child != null) 
                        {
                            curr = curr.child;
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
            }
            else if(curr.ncolor == nodecolor.GRAY)
            {
                curr.ncolor = nodecolor.BLACK;
                if(curr.Next != null)
                    curr = curr.Next;
                else
                    curr = curr.parent;
            }
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
