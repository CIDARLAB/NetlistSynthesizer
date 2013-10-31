/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package netsynth;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import netsynth.Gate.GateType;
import netsynth.Wire.WireType;


/**
 *
 * @author prashantvaidyanathan
 */
public class NetSynth {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Global.wirecount = 0;
        Global.espinp =0;
        Global.espout =0;
        //testnetlistmodule();
        runEspresso();
    }
    
    public static void runEspresso() {
        try {
            Runtime rt = Runtime.getRuntime();
            Process proc = rt.exec("./espresso.exe A0.txt>javafile.txt");
            //Process p = Runtime.getRuntime().exec("/espresso.exe A0.txt>javafile.txt");
            int waitFor = proc.waitFor();

        } catch (IOException ex) {
            //Validate the case the file can't be accesed (not enought permissions)
        } catch (InterruptedException ex) {
            //Validate the case the process is being stopped by some external situation     
        }
    }
    
    
    public static void testnetlistmodule()
    {
        Gate and = new Gate();
        Wire w1 = new Wire("A",WireType.input);
        Wire w2 = new Wire("B",WireType.input);
        Wire outp = new Wire("outP",WireType.output);
        List<Wire> inputWires = new ArrayList<Wire>();
        inputWires.add(w1);
        inputWires.add(w2);
        
        GateType testgateType;
        testgateType = GateType.XOR2;
        Gate gtest = new Gate(testgateType,inputWires,outp);
        
        
        
        List<Gate> test = new ArrayList<Gate>();
        test = GatetoNORNOT(gtest);

        for(Gate gout:test)
        {
            String netbuilder = "";
            netbuilder = netlist(gout);
            System.out.println(netbuilder);
        
        }
         
        
    }
    
    
    public static String netlist(Gate g)
    {
        String netbuilder="";
        netbuilder += g.gtype;
            netbuilder += "(";
            netbuilder += g.output.name;
            
            for(Wire x:g.input)
            {
                netbuilder += ",";
                netbuilder += x.name;
            }
            netbuilder += ")";
            //netbuilder += "  Stage:";
            //netbuilder += g.gatestage;
        return netbuilder;
    }
    
    
    public static List<Gate> GatetoNOR(Gate g)
    {
       List<Gate> nor_eq = new ArrayList<Gate>();
       
       if(g.gtype == GateType.NOT)
       {
           Gate nor1 = new Gate();
           nor1.gtype = GateType.NOR2;
           
           nor1.input.add(g.input.get(0));
           nor1.input.add(g.input.get(0));
           
           nor1.output = g.output;
           nor1.calculateStage();
           nor_eq.add(nor1);
           
       }
       
       else if(g.gtype == GateType.AND2)
       {
           Gate nor1 = new Gate();
           nor1.gtype = GateType.NOR2;
           nor1.input.add(g.input.get(0));
           nor1.input.add(g.input.get(0));
           Wire outp1 = new Wire();
           outp1.name = "Wire" + Global.wirecount++;
           nor1.output = outp1;
           
           Gate nor2 = new Gate();
           nor2.gtype = GateType.NOR2;
           nor2.input.add(g.input.get(1));
           nor2.input.add(g.input.get(1));
           Wire outp2 = new Wire();
           outp2.name = "Wire" + Global.wirecount++;
           nor2.output = outp2;
           
           Gate nor3 = new Gate();
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
           Gate nor1 = new Gate();
           nor1.gtype = GateType.NOR2;
           nor1.input.add(g.input.get(0));
           nor1.input.add(g.input.get(0));
           Wire outp1 = new Wire();
           outp1.name = "Wire" + Global.wirecount++;
           nor1.output = outp1;
           
           Gate nor2 = new Gate();
           nor2.gtype = GateType.NOR2;
           nor2.input.add(g.input.get(1));
           nor2.input.add(g.input.get(1));
           Wire outp2 = new Wire();
           outp2.name = "Wire" + Global.wirecount++;
           nor2.output = outp2;
           
           Gate nor3 = new Gate();
           nor3.gtype = GateType.NOR2;
           nor3.input.add(outp1);
           nor3.input.add(outp2);
           Wire outp3 = new Wire();
           outp3.name = "Wire" + Global.wirecount++;
           nor3.output = outp3;
           
           Gate nor4 = new Gate();
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
           Gate nor1  = new Gate();
           nor1.gtype = GateType.NOR2;
           nor1.input.add(g.input.get(0));
           nor1.input.add(g.input.get(1));
           Wire outp1 = new Wire();
           outp1.name = "Wire" + Global.wirecount++;
           nor1.output = outp1;
           
           Gate nor2 = new Gate();
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
           Gate nor1 = new Gate();
           nor1.gtype = GateType.NOR2;
           nor1.input.add(g.input.get(0));
           nor1.input.add(g.input.get(0));
           Wire outp1 = new Wire();
           outp1.name = "Wire" + Global.wirecount++;
           nor1.output = outp1;
           
           Gate nor2 = new Gate();
           nor2.gtype = GateType.NOR2;
           nor2.input.add(g.input.get(1));
           nor2.input.add(g.input.get(1));
           Wire outp2 = new Wire();
           outp2.name = "Wire" + Global.wirecount++;
           nor2.output = outp2;
           
           Gate nor3 = new Gate();
           nor3.gtype = GateType.NOR2;
           nor3.input.add(g.input.get(0));
           nor3.input.add(g.input.get(1));
           Wire outp3 = new Wire();
           outp3.name = "Wire" + Global.wirecount++;
           nor3.output = outp3;
           
           Gate nor4 = new Gate();
           nor4.gtype = GateType.NOR2;
           nor4.input.add(outp1);
           nor4.input.add(outp2);
           Wire outp4 = new Wire();
           outp4.name = "Wire" + Global.wirecount++;
           nor4.output = outp4;
           
           Gate nor5 = new Gate();
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
           Gate nor1 = new Gate();
           nor1.gtype = GateType.NOR2;
           nor1.input.add(g.input.get(0));
           nor1.input.add(g.input.get(0));
           Wire outp1 = new Wire();
           outp1.name = "Wire" + Global.wirecount++;
           nor1.output = outp1;
           
           Gate nor2 = new Gate();
           nor2.gtype = GateType.NOR2;
           nor2.input.add(g.input.get(1));
           nor2.input.add(g.input.get(1));
           Wire outp2 = new Wire();
           outp2.name = "Wire" + Global.wirecount++;
           nor2.output = outp2;
           
           Gate nor3 = new Gate();
           nor3.gtype = GateType.NOR2;
           nor3.input.add(outp1);
           nor3.input.add(g.input.get(1));
           Wire outp3 = new Wire();
           outp3.name = "Wire" + Global.wirecount++;
           nor3.output = outp3;
           
           Gate nor4 = new Gate();
           nor4.gtype = GateType.NOR2;
           nor4.input.add(g.input.get(0));
           nor4.input.add(outp2);
           Wire outp4 = new Wire();
           outp4.name = "Wire" + Global.wirecount++;
           nor4.output = outp4;
           
           Gate nor5 = new Gate();
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
     
    public static List<Gate> GatetoNORNOT(Gate g)
    {
       List<Gate> nor_eq = new ArrayList<Gate>();
       
       if(g.gtype == GateType.NOT)
       {
           g.calculateStage();
           nor_eq.add(g);
       }
       
       else if(g.gtype == GateType.AND2)
       {
           Gate not1 = new Gate();
           not1.gtype = GateType.NOT;
           not1.input.add(g.input.get(0));
           Wire outp1 = new Wire();
           outp1.name = "Wire" + Global.wirecount++;
           not1.output = outp1;
           
           Gate not2 = new Gate();
           not2.gtype = GateType.NOT;
           not2.input.add(g.input.get(1));
           Wire outp2 = new Wire();
           outp2.name = "Wire" + Global.wirecount++;
           not2.output = outp2;
           
           Gate nor1 = new Gate();
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
           Gate not1 = new Gate();
           not1.gtype = GateType.NOT;
           not1.input.add(g.input.get(0));
           Wire outp1 = new Wire();
           outp1.name = "Wire" + Global.wirecount++;
           not1.output = outp1;
           
           Gate not2 = new Gate();
           not2.gtype = GateType.NOT;
           not2.input.add(g.input.get(1));
           Wire outp2 = new Wire();
           outp2.name = "Wire" + Global.wirecount++;
           not2.output = outp2;
           
           Gate nor1 = new Gate();
           nor1.gtype = GateType.NOR2;
           nor1.input.add(outp1);
           nor1.input.add(outp2);
           Wire outp3 = new Wire();
           outp3.name = "Wire" + Global.wirecount++;
           nor1.output = outp3;
           
           Gate not3 = new Gate();
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
           Gate nor1  = new Gate();
           nor1.gtype = GateType.NOR2;
           nor1.input.add(g.input.get(0));
           nor1.input.add(g.input.get(1));
           Wire outp1 = new Wire();
           outp1.name = "Wire" + Global.wirecount++;
           nor1.output = outp1;
           
           Gate not1 = new Gate();
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
           Gate not1 = new Gate();
           not1.gtype = GateType.NOT;
           not1.input.add(g.input.get(0));
           Wire outp1 = new Wire();
           outp1.name = "Wire" + Global.wirecount++;
           not1.output = outp1;
           
           Gate not2 = new Gate();
           not2.gtype = GateType.NOT;
           not2.input.add(g.input.get(1));
           Wire outp2 = new Wire();
           outp2.name = "Wire" + Global.wirecount++;
           not2.output = outp2;
           
           Gate nor1 = new Gate();
           nor1.gtype = GateType.NOR2;
           nor1.input.add(g.input.get(0));
           nor1.input.add(g.input.get(1));
           Wire outp3 = new Wire();
           outp3.name = "Wire" + Global.wirecount++;
           nor1.output = outp3;
           
           Gate nor2 = new Gate();
           nor2.gtype = GateType.NOR2;
           nor2.input.add(outp1);
           nor2.input.add(outp2);
           Wire outp4 = new Wire();
           outp4.name = "Wire" + Global.wirecount++;
           nor2.output = outp4;
           
           Gate nor3 = new Gate();
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
           Gate not1 = new Gate();
           not1.gtype = GateType.NOT;
           not1.input.add(g.input.get(0));
           Wire outp1 = new Wire();
           outp1.name = "Wire" + Global.wirecount++;
           not1.output = outp1;
           
           Gate not2 = new Gate();
           not2.gtype = GateType.NOT;
           not2.input.add(g.input.get(1));
           Wire outp2 = new Wire();
           outp2.name = "Wire" + Global.wirecount++;
           not2.output = outp2;
           
           Gate nor1 = new Gate();
           nor1.gtype = GateType.NOR2;
           nor1.input.add(outp1);
           nor1.input.add(g.input.get(1));
           Wire outp3 = new Wire();
           outp3.name = "Wire" + Global.wirecount++;
           nor1.output = outp3;
           
           Gate nor2 = new Gate();
           nor2.gtype = GateType.NOR2;
           nor2.input.add(g.input.get(0));
           nor2.input.add(outp2);
           Wire outp4 = new Wire();
           outp4.name = "Wire" + Global.wirecount++;
           nor2.output = outp4;
           
           Gate nor3 = new Gate();
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
    
    
    
}