/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package netsynth;

import java.util.ArrayList;
import java.util.List;
import netsynth.DGate.GateType;

/**
 *
 * @author prashantvaidyanathan
 */
public class NetlistConversionFunctions {
    
    
    
    public static List<DGate> GatetoNORNOT(DGate g)
    {
       List<DGate> nor_eq = new ArrayList<DGate>();
       
       if(g.gtype == GateType.NOT)
       {
           g.calculateStage();
           nor_eq.add(g);
       }
       
       else if(g.gtype == GateType.AND2)
       {
           DGate not1 = new DGate();
           not1.gtype = GateType.NOT;
           not1.input.add(g.input.get(0));
           DWire outp1 = new DWire();
           outp1.name = "Wire" + Global.wirecount++;
           not1.output = outp1;
           
           DGate not2 = new DGate();
           not2.gtype = GateType.NOT;
           not2.input.add(g.input.get(1));
           DWire outp2 = new DWire();
           outp2.name = "Wire" + Global.wirecount++;
           not2.output = outp2;
           
           DGate nor1 = new DGate();
           nor1.gtype = GateType.NOR2;
           nor1.input.add(outp1);
           nor1.input.add(outp2);
           nor1.output = g.output;
           
           not1.calculateStage();
           not2.calculateStage();
           nor1.calculateStage();
           
           nor_eq.add(not1);
           nor_eq.add(not2);
           nor_eq.add(nor1);
       }
       
       
       else if(g.gtype == GateType.NAND2)
       {
           DGate not1 = new DGate();
           not1.gtype = GateType.NOT;
           not1.input.add(g.input.get(0));
           DWire outp1 = new DWire();
           outp1.name = "Wire" + Global.wirecount++;
           not1.output = outp1;
           
           DGate not2 = new DGate();
           not2.gtype = GateType.NOT;
           not2.input.add(g.input.get(1));
           DWire outp2 = new DWire();
           outp2.name = "Wire" + Global.wirecount++;
           not2.output = outp2;
           
           DGate nor1 = new DGate();
           nor1.gtype = GateType.NOR2;
           nor1.input.add(outp1);
           nor1.input.add(outp2);
           DWire outp3 = new DWire();
           outp3.name = "Wire" + Global.wirecount++;
           nor1.output = outp3;
           
           DGate not3 = new DGate();
           not3.gtype = GateType.NOT;
           not3.input.add(outp3);
           not3.output = g.output;
           
           not1.calculateStage();
           not2.calculateStage();
           nor1.calculateStage();
           not3.calculateStage();
           
           nor_eq.add(not1);
           nor_eq.add(not2);
           nor_eq.add(nor1);
           nor_eq.add(not3);
           
       }
       
       
       else if(g.gtype == GateType.OR2)
       {
           DGate nor1  = new DGate();
           nor1.gtype = GateType.NOR2;
           nor1.input.add(g.input.get(0));
           nor1.input.add(g.input.get(1));
           DWire outp1 = new DWire();
           outp1.name = "Wire" + Global.wirecount++;
           nor1.output = outp1;
           
           DGate not1 = new DGate();
           not1.gtype = GateType.NOT;
           not1.input.add(outp1);
           not1.output = g.output;
          
           nor1.calculateStage();
           not1.calculateStage();
           
           nor_eq.add(nor1);
           nor_eq.add(not1);
           
       }
       
       else if(g.gtype == GateType.NOR2)
       {
           g.calculateStage();
           nor_eq.add(g);
       }
       
       else if(g.gtype == GateType.XOR2)
       {
           DGate not1 = new DGate();
           not1.gtype = GateType.NOT;
           not1.input.add(g.input.get(0));
           DWire outp1 = new DWire();
           outp1.name = "Wire" + Global.wirecount++;
           not1.output = outp1;
           
           DGate not2 = new DGate();
           not2.gtype = GateType.NOT;
           not2.input.add(g.input.get(1));
           DWire outp2 = new DWire();
           outp2.name = "Wire" + Global.wirecount++;
           not2.output = outp2;
           
           DGate nor1 = new DGate();
           nor1.gtype = GateType.NOR2;
           nor1.input.add(g.input.get(0));
           nor1.input.add(g.input.get(1));
           DWire outp3 = new DWire();
           outp3.name = "Wire" + Global.wirecount++;
           nor1.output = outp3;
           
           DGate nor2 = new DGate();
           nor2.gtype = GateType.NOR2;
           nor2.input.add(outp1);
           nor2.input.add(outp2);
           DWire outp4 = new DWire();
           outp4.name = "Wire" + Global.wirecount++;
           nor2.output = outp4;
           
           DGate nor3 = new DGate();
           nor3.gtype = GateType.NOR2;
           nor3.input.add(outp3);
           nor3.input.add(outp4);
           nor3.output = g.output;
           
           not1.calculateStage();
           not2.calculateStage();
           nor1.calculateStage();
           nor2.calculateStage();
           nor3.calculateStage();
           
           nor_eq.add(not1);
           nor_eq.add(not2);
           nor_eq.add(nor1);
           nor_eq.add(nor2);
           nor_eq.add(nor3);
                                                       
       }
       else if(g.gtype == GateType.XNOR2)
       {
           DGate not1 = new DGate();
           not1.gtype = GateType.NOT;
           not1.input.add(g.input.get(0));
           DWire outp1 = new DWire();
           outp1.name = "Wire" + Global.wirecount++;
           not1.output = outp1;
           
           DGate not2 = new DGate();
           not2.gtype = GateType.NOT;
           not2.input.add(g.input.get(1));
           DWire outp2 = new DWire();
           outp2.name = "Wire" + Global.wirecount++;
           not2.output = outp2;
           
           DGate nor1 = new DGate();
           nor1.gtype = GateType.NOR2;
           nor1.input.add(outp1);
           nor1.input.add(g.input.get(1));
           DWire outp3 = new DWire();
           outp3.name = "Wire" + Global.wirecount++;
           nor1.output = outp3;
           
           DGate nor2 = new DGate();
           nor2.gtype = GateType.NOR2;
           nor2.input.add(g.input.get(0));
           nor2.input.add(outp2);
           DWire outp4 = new DWire();
           outp4.name = "Wire" + Global.wirecount++;
           nor2.output = outp4;
           
           DGate nor3 = new DGate();
           nor3.gtype = GateType.NOR2;
           nor3.input.add(outp3);
           nor3.input.add(outp4);
           nor3.output = g.output;

           not1.calculateStage();
           not2.calculateStage();
           nor1.calculateStage();
           nor2.calculateStage();
           nor3.calculateStage();

           
           nor_eq.add(not1);
           nor_eq.add(not2);
           nor_eq.add(nor1);
           nor_eq.add(nor2);
           nor_eq.add(nor3);
       }
       return nor_eq;
    }  
    
    
    public static List<DGate> GatetoNOR(DGate g)
    {
       List<DGate> nor_eq = new ArrayList<DGate>();
       
       if(g.gtype == GateType.NOT)
       {
           DGate nor1 = new DGate();
           nor1.gtype = GateType.NOR2;
           
           nor1.input.add(g.input.get(0));
           nor1.input.add(g.input.get(0));
           
           nor1.output = g.output;
           nor1.calculateStage();
           nor_eq.add(nor1);
           
       }
       
       else if(g.gtype == GateType.AND2)
       {
           DGate nor1 = new DGate();
           nor1.gtype = GateType.NOR2;
           nor1.input.add(g.input.get(0));
           nor1.input.add(g.input.get(0));
           DWire outp1 = new DWire();
           outp1.name = "Wire" + Global.wirecount++;
           nor1.output = outp1;
           
           DGate nor2 = new DGate();
           nor2.gtype = GateType.NOR2;
           nor2.input.add(g.input.get(1));
           nor2.input.add(g.input.get(1));
           DWire outp2 = new DWire();
           outp2.name = "Wire" + Global.wirecount++;
           nor2.output = outp2;
           
           DGate nor3 = new DGate();
           nor3.gtype = GateType.NOR2;
           nor3.input.add(outp1);
           nor3.input.add(outp2);
           nor3.output = g.output;
           
           nor1.calculateStage();
           nor2.calculateStage();
           nor3.calculateStage();
           
           nor_eq.add(nor1);
           nor_eq.add(nor2);
           nor_eq.add(nor3);
       
       }
       
       
       else if(g.gtype == GateType.NAND2)
       {
           DGate nor1 = new DGate();
           nor1.gtype = GateType.NOR2;
           nor1.input.add(g.input.get(0));
           nor1.input.add(g.input.get(0));
           DWire outp1 = new DWire();
           outp1.name = "Wire" + Global.wirecount++;
           nor1.output = outp1;
           
           DGate nor2 = new DGate();
           nor2.gtype = GateType.NOR2;
           nor2.input.add(g.input.get(1));
           nor2.input.add(g.input.get(1));
           DWire outp2 = new DWire();
           outp2.name = "Wire" + Global.wirecount++;
           nor2.output = outp2;
           
           DGate nor3 = new DGate();
           nor3.gtype = GateType.NOR2;
           nor3.input.add(outp1);
           nor3.input.add(outp2);
           DWire outp3 = new DWire();
           outp3.name = "Wire" + Global.wirecount++;
           nor3.output = outp3;
           
           DGate nor4 = new DGate();
           nor4.gtype = GateType.NOR2;
           nor4.input.add(outp3);
           nor4.input.add(outp3);
           nor4.output = g.output;
           
           nor1.calculateStage();
           nor2.calculateStage();
           nor3.calculateStage();
           nor4.calculateStage();
           
           nor_eq.add(nor1);
           nor_eq.add(nor2);
           nor_eq.add(nor3);
           nor_eq.add(nor4);
           
       }
       
       
       else if(g.gtype == GateType.OR2)
       {
           DGate nor1  = new DGate();
           nor1.gtype = GateType.NOR2;
           nor1.input.add(g.input.get(0));
           nor1.input.add(g.input.get(1));
           DWire outp1 = new DWire();
           outp1.name = "Wire" + Global.wirecount++;
           nor1.output = outp1;
           
           DGate nor2 = new DGate();
           nor2.gtype = GateType.NOR2;
           nor2.input.add(outp1);
           nor2.input.add(outp1);
           nor2.output = g.output;
          
           nor1.calculateStage();
           nor2.calculateStage();
           
           nor_eq.add(nor1);
           nor_eq.add(nor2);
           
       }
       
       else if(g.gtype == GateType.NOR2)
       {
           g.calculateStage();
           nor_eq.add(g);
       }
       
       else if(g.gtype == GateType.XOR2)
       {
           DGate nor1 = new DGate();
           nor1.gtype = GateType.NOR2;
           nor1.input.add(g.input.get(0));
           nor1.input.add(g.input.get(0));
           DWire outp1 = new DWire();
           outp1.name = "Wire" + Global.wirecount++;
           nor1.output = outp1;
           
           DGate nor2 = new DGate();
           nor2.gtype = GateType.NOR2;
           nor2.input.add(g.input.get(1));
           nor2.input.add(g.input.get(1));
           DWire outp2 = new DWire();
           outp2.name = "Wire" + Global.wirecount++;
           nor2.output = outp2;
           
           DGate nor3 = new DGate();
           nor3.gtype = GateType.NOR2;
           nor3.input.add(g.input.get(0));
           nor3.input.add(g.input.get(1));
           DWire outp3 = new DWire();
           outp3.name = "Wire" + Global.wirecount++;
           nor3.output = outp3;
           
           DGate nor4 = new DGate();
           nor4.gtype = GateType.NOR2;
           nor4.input.add(outp1);
           nor4.input.add(outp2);
           DWire outp4 = new DWire();
           outp4.name = "Wire" + Global.wirecount++;
           nor4.output = outp4;
           
           DGate nor5 = new DGate();
           nor5.gtype = GateType.NOR2;
           nor5.input.add(outp3);
           nor5.input.add(outp4);
           nor5.output = g.output;
           
           nor1.calculateStage();
           nor2.calculateStage();
           nor3.calculateStage();
           nor4.calculateStage();
           nor5.calculateStage();
           
           nor_eq.add(nor1);
           nor_eq.add(nor2);
           nor_eq.add(nor3);
           nor_eq.add(nor4);
           nor_eq.add(nor5);
                                                       
       }
       else if(g.gtype == GateType.XNOR2)
       {
           DGate nor1 = new DGate();
           nor1.gtype = GateType.NOR2;
           nor1.input.add(g.input.get(0));
           nor1.input.add(g.input.get(0));
           DWire outp1 = new DWire();
           outp1.name = "Wire" + Global.wirecount++;
           nor1.output = outp1;
           
           DGate nor2 = new DGate();
           nor2.gtype = GateType.NOR2;
           nor2.input.add(g.input.get(1));
           nor2.input.add(g.input.get(1));
           DWire outp2 = new DWire();
           outp2.name = "Wire" + Global.wirecount++;
           nor2.output = outp2;
           
           DGate nor3 = new DGate();
           nor3.gtype = GateType.NOR2;
           nor3.input.add(outp1);
           nor3.input.add(g.input.get(1));
           DWire outp3 = new DWire();
           outp3.name = "Wire" + Global.wirecount++;
           nor3.output = outp3;
           
           DGate nor4 = new DGate();
           nor4.gtype = GateType.NOR2;
           nor4.input.add(g.input.get(0));
           nor4.input.add(outp2);
           DWire outp4 = new DWire();
           outp4.name = "Wire" + Global.wirecount++;
           nor4.output = outp4;
           
           DGate nor5 = new DGate();
           nor5.gtype = GateType.NOR2;
           nor5.input.add(outp3);
           nor5.input.add(outp4);
           nor5.output = g.output;

           nor1.calculateStage();
           nor2.calculateStage();
           nor3.calculateStage();
           nor4.calculateStage();
           nor5.calculateStage();
           
           nor_eq.add(nor1);
           nor_eq.add(nor2);
           nor_eq.add(nor3);
           nor_eq.add(nor4);
           nor_eq.add(nor5);
       }
       
       return nor_eq;
    }  
     
    
}
