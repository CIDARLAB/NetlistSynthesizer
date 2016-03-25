/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cellocad.BU.parseVerilog;

import org.cellocad.BU.dom.DGate;
import org.cellocad.BU.dom.DGateType;
import org.cellocad.BU.dom.DWire;
import org.cellocad.BU.dom.DWireType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.cellocad.BU.netsynth.Utilities;
import org.cellocad.BU.parseVerilog.grammar.Verilog2001Lexer;
import org.cellocad.BU.parseVerilog.grammar.Verilog2001Parser;

/**
 *
 * @author prashantvaidyanathan
 */
public class parseVerilogFile {
    
    /*
    public static ParseTree parseVerilog(String verilogCode){
        ANTLRInputStream input = new ANTLRInputStream(verilogCode);
        Verilog2001Lexer lexer = new Verilog2001Lexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        Verilog2001Parser parser = new Verilog2001Parser(tokens);
        ParseTree tree = parser.module_declaration();
        //Verilog2001Parser parser = new Verilog2001Parser();
        
        //System.out.println("TREE :: " + tree.toStringTree(parser));
        traverseTree(tree,0);
        return tree;
    }
    
    public static void traverseTree(ParseTree tree,int level){
//        if(tree.getParent() == null){
//            level = 0;
//            
//        }
        for(int i=0;i<level;i++){
            System.out.print("--");
        }
        System.out.println(tree.getText());
        for(int i=0;i<tree.getChildCount();i++){
            traverseTree(tree.getChild(i),level+1);
        }
    }
    */
    
    public static String verilogFileLinesCode(String verilogCode){
        String pieces[] = verilogCode.split("[\\r\\n]+");
        List<String> lines = new ArrayList<String>();
        for(String line:pieces){
            lines.add(line);
        }
        return verilogFileLinesCode(lines);
        
    }
    
    public static String verilogFileLinesCode(List<String> lines){
        boolean addCodelines = false;
        String alllines = "";

        for (String line : lines) {
            line = line.trim();
            if (!addCodelines) {
                if (line.contains("module ") && (!line.startsWith("//"))) {
                    if (line.indexOf("module ") > 0) {
                        if (((line.charAt((line.indexOf("module ")) - 1)) == ' ')) {
                            addCodelines = true;
                        }

                    } else {
                        addCodelines = true;
                    }
                }
            }
            if ((!line.isEmpty()) && addCodelines && (!line.startsWith("//"))) {
                alllines += (" " + line);
            }
        }

        //System.out.println(alllines);
        alllines = alllines.trim();
        return alllines;
    }
    
    public static String verilogFileLines(String Filepath)
    {
       
        List<String> lines = new ArrayList<String>();
        lines = Utilities.getFileContentAsStringList(Filepath);
        return verilogFileLinesCode(lines);
        
    }
    
    public static List<String> getInputNames(String alllines)
    {
        List<String> inputs = new ArrayList<String>();
        List<String> outputs = new ArrayList<String>();
        List<String> unknownIO = new ArrayList<String>();
        String moduleString = alllines.substring(alllines.indexOf("module "),alllines.indexOf(";"));
        alllines = alllines.substring((alllines.indexOf(moduleString) + moduleString.length()+1), alllines.indexOf(" endmodule"));
        alllines = alllines.trim();
        moduleString = moduleString.trim();
        moduleString = moduleString.substring((moduleString.indexOf("(")+1),moduleString.indexOf(")"));
        
        String[] modulePieces = moduleString.split(",");
        for(int i=0;i<modulePieces.length;i++)
        {
            String IO = modulePieces[i].trim();
            if(IO.contains("input "))
            {
                IO = IO.substring(IO.indexOf("input ")+6);
                IO = IO.trim();
                if(!inputs.contains(IO))
                    inputs.add(IO);
                
                for(int j=i+1;j<modulePieces.length;j++)
                {
                    String IOnext = modulePieces[j].trim();
                    if(IOnext.contains("output "))
                        break;
                    if(!inputs.contains(IOnext))
                        inputs.add(IOnext);
                }
                
            }
            else if(IO.contains("output "))
            {
                
                IO = IO.substring(IO.indexOf("output ")+7);
                IO = IO.trim();
                if(!outputs.contains(IO))
                    outputs.add(IO);
                for(int j=i+1;j<modulePieces.length;j++)
                {
                    String IOnext = modulePieces[j].trim();
                    if(IOnext.contains("input "))
                        break;
                    if(!outputs.contains(IOnext))
                        outputs.add(IOnext);
                }
            }
            else
            {
                unknownIO.add(IO);   
                
            }
        }
        return inputs;
    }
    
    public static List<String> getOutputNames(String alllines)
    {
        List<String> inputs = new ArrayList<String>();
        List<String> outputs = new ArrayList<String>();
        List<String> unknownIO = new ArrayList<String>();
        String moduleString = alllines.substring(alllines.indexOf("module "),alllines.indexOf(";"));
        alllines = alllines.substring((alllines.indexOf(moduleString) + moduleString.length()+1), alllines.indexOf(" endmodule"));
        alllines = alllines.trim();
        moduleString = moduleString.trim();
        moduleString = moduleString.substring((moduleString.indexOf("(")+1),moduleString.indexOf(")"));
        
        String[] modulePieces = moduleString.split(",");
        for(int i=0;i<modulePieces.length;i++)
        {
            String IO = modulePieces[i].trim();
            if(IO.contains("input "))
            {
                IO = IO.substring(IO.indexOf("input ")+6);
                IO = IO.trim();
                if(!inputs.contains(IO))
                    inputs.add(IO);
                
                for(int j=i+1;j<modulePieces.length;j++)
                {
                    String IOnext = modulePieces[j].trim();
                    if(IOnext.contains("output "))
                        break;
                    if(!inputs.contains(IOnext))
                        inputs.add(IOnext);
                }
                
            }
            else if(IO.contains("output "))
            {
                
                IO = IO.substring(IO.indexOf("output ")+7);
                IO = IO.trim();
                if(!outputs.contains(IO))
                    outputs.add(IO);
                for(int j=i+1;j<modulePieces.length;j++)
                {
                    String IOnext = modulePieces[j].trim();
                    if(IOnext.contains("input "))
                        break;
                    if(!outputs.contains(IOnext))
                        outputs.add(IOnext);
                }
            }
            else
            {
                unknownIO.add(IO);   
                
            }
        }
        return outputs;
    }
    
    
    public static List<String> getInputNamesABCVerilog(String alllines)
    {
        List<String> inputs = new ArrayList<String>();
        List<String> outputs = new ArrayList<String>();
        List<String> unknownIO = new ArrayList<String>();
        String moduleString = alllines.substring(alllines.indexOf("module "),alllines.indexOf(";"));
        alllines = alllines.substring((alllines.indexOf(moduleString) + moduleString.length()+1), alllines.indexOf(" endmodule"));
        alllines = alllines.trim();
        
        String[] vlines = alllines.split(";");
        for(int i=0;i<vlines.length;i++)
        {
            String line = vlines[i].trim();
            if(line.startsWith("input "))
            {
                moduleString = line;
                break;
            }
        }
        //System.out.println(moduleString);
        moduleString = moduleString.trim();
        
        String[] modulePieces = moduleString.split(",");
        for(int i=0;i<modulePieces.length;i++)
        {
            String IO = modulePieces[i].trim();
            if(IO.contains("input "))
            {
                IO = IO.substring(IO.indexOf("input ")+6);
                IO = IO.trim();
                if(!inputs.contains(IO))
                    inputs.add(IO);
                
                for(int j=i+1;j<modulePieces.length;j++)
                {
                    String IOnext = modulePieces[j].trim();
                    if(IOnext.contains("output "))
                        break;
                    if(!inputs.contains(IOnext))
                        inputs.add(IOnext);
                }
                
            }
            else if(IO.contains("output "))
            {
                
                IO = IO.substring(IO.indexOf("output ")+7);
                IO = IO.trim();
                if(!outputs.contains(IO))
                    outputs.add(IO);
                for(int j=i+1;j<modulePieces.length;j++)
                {
                    String IOnext = modulePieces[j].trim();
                    if(IOnext.contains("input "))
                        break;
                    if(!outputs.contains(IOnext))
                        outputs.add(IOnext);
                }
            }
            else
            {
                unknownIO.add(IO);   
                
            }
        }
        return inputs;
    }
    
    public static List<String> getOutputNamesABCVerilog(String alllines)
    {
        List<String> inputs = new ArrayList<String>();
        List<String> outputs = new ArrayList<String>();
        List<String> unknownIO = new ArrayList<String>();
        String moduleString = alllines.substring(alllines.indexOf("module "),alllines.indexOf(";"));
        alllines = alllines.substring((alllines.indexOf(moduleString) + moduleString.length()+1), alllines.indexOf(" endmodule"));
        alllines = alllines.trim();
        //moduleString = moduleString.trim();
        //moduleString = moduleString.substring((moduleString.indexOf("(")+1),moduleString.indexOf(")"));
        
        String[] vlines = alllines.split(";");
        for(int i=0;i<vlines.length;i++)
        {
            String line = vlines[i].trim();
            if(line.startsWith("output "))
            {
                moduleString = line;
                break;
            }
        }
        moduleString = moduleString.trim();
        String[] modulePieces = moduleString.split(",");
        for(int i=0;i<modulePieces.length;i++)
        {
            String IO = modulePieces[i].trim();
            if(IO.contains("input "))
            {
                IO = IO.substring(IO.indexOf("input ")+6);
                IO = IO.trim();
                if(!inputs.contains(IO))
                    inputs.add(IO);
                
                for(int j=i+1;j<modulePieces.length;j++)
                {
                    String IOnext = modulePieces[j].trim();
                    if(IOnext.contains("output "))
                        break;
                    if(!inputs.contains(IOnext))
                        inputs.add(IOnext);
                }
                
            }
            else if(IO.contains("output "))
            {
                
                IO = IO.substring(IO.indexOf("output ")+7);
                IO = IO.trim();
                if(!outputs.contains(IO))
                    outputs.add(IO);
                for(int j=i+1;j<modulePieces.length;j++)
                {
                    String IOnext = modulePieces[j].trim();
                    if(IOnext.contains("input "))
                        break;
                    if(!outputs.contains(IOnext))
                        outputs.add(IOnext);
                }
            }
            else
            {
                unknownIO.add(IO);   
                
            }
        }
        return outputs;
    }
    
    
    public static CircuitDetails parseCaseStatements(String alllines)
    {
        
         
         List<String> inputs = new ArrayList<String>();
         List<String> outputs = new ArrayList<String>();
         List<String> unknownIO = new ArrayList<String>();
         
         int x = 0;
        
        CircuitDetails circuit = new CircuitDetails();
        
        //System.out.println(alllines);
        
        //<editor-fold desc="Extract Module IO Contraints">
        String moduleString = alllines.substring(alllines.indexOf("module "),alllines.indexOf(";"));
        alllines = alllines.substring((alllines.indexOf(moduleString) + moduleString.length()+1), alllines.indexOf(" endmodule"));
        alllines = alllines.trim();
        moduleString = moduleString.trim();
        moduleString = moduleString.substring((moduleString.indexOf("(")+1),moduleString.indexOf(")"));
        //System.out.println(alllines);
        
        //System.out.println(moduleString);
        String[] modulePieces = moduleString.split(",");
        for(int i=0;i<modulePieces.length;i++)
        {
            String IO = modulePieces[i].trim();
            if(IO.contains("input "))
            {
                IO = IO.substring(IO.indexOf("input ")+6);
                IO = IO.trim();
                inputs.add(IO);
                
            }
            else if(IO.contains("output "))
            {
                
                IO = IO.substring(IO.indexOf("output ")+7);
                IO = IO.trim();
                outputs.add(IO);
                
            }
            else
            {
                unknownIO.add(IO);   
                
            }
        }
        //System.out.println(alllines);
        //</editor-fold>
        
        List<String> VerilogLines = new ArrayList<String>();
        String temp="";
        int caseLocation=-1;
        int cnt =0;
        while(alllines.contains(";") || alllines.length()>0)
        {
            
            if(alllines.startsWith("always ") || alllines.startsWith("always@"))
            {
                if(alllines.contains(" begin "))
                {
                    int lastbeginIndx=0;
                    if((alllines.lastIndexOf(" end")+4) == alllines.length())
                    {
                        temp = alllines.substring(0, alllines.lastIndexOf(" end")+4);
                        VerilogLines.add(temp);
                        alllines = alllines.substring(alllines.lastIndexOf(" end")+4);
                    }
                    else
                    {
                        temp = alllines.substring(0, alllines.lastIndexOf(" end ")+5);
                        VerilogLines.add(temp);
                        alllines = alllines.substring(alllines.lastIndexOf(" end ")+5);
                    }
                if(temp.contains(" endcase "))
                    caseLocation = cnt;
                alllines = alllines.trim();
                }
                
            }
            else if(alllines.contains(";"))
            {
                //System.out.println(alllines);
                VerilogLines.add(alllines.substring(0,alllines.indexOf(";")));
                alllines = alllines.substring(alllines.indexOf(";")+1);
                alllines = alllines.trim();
            }    
            else
            {
                VerilogLines.add(alllines);
                alllines = "";
            }
           
            cnt++;
        }
        
        if(caseLocation == -1)
            System.out.println("No case statements");
        
        else
        {
            String tempcase = VerilogLines.get(caseLocation);
            //List<String> caseStatements = new ArrayList<String>();
            String caseblock ="";
            String caseparam = "";
            String[] caseStatements;
            boolean unusualcase = false;
            
            if(tempcase.contains("case("))
            {
                caseblock = tempcase.substring(tempcase.indexOf("case(")+4);
                caseblock = caseblock.trim();
            }
            else if(tempcase.contains("case "))
            {
               
                //System.out.println(tempcase);
                caseblock = tempcase.substring(tempcase.indexOf("case ")+5);
                caseblock = caseblock.trim();
                
            }
            else
            {
                unusualcase = true;
                System.out.println("unusual Case Statement!!\n"+tempcase);
            }
            
            if(!unusualcase)
            {
                caseparam = caseblock.substring(caseblock.indexOf("(")+1,caseblock.indexOf(")"));
                caseblock = caseblock.substring(caseblock.indexOf(")")+1);
                caseblock = caseblock.substring(0,caseblock.indexOf("endcase"));
                caseblock = caseblock.trim();
                caseStatements = caseblock.split(";");
                
                List<HashMap<Integer,Integer>> truthtable = new ArrayList<HashMap<Integer,Integer>>();
                List<HashMap<Integer,Character>> charTruthTable = new ArrayList<HashMap<Integer,Character>>();
                caseparam = caseparam.substring(caseparam.indexOf("{")+1, caseparam.indexOf("}"));
                
                String[] caseinp = caseparam.split(",");
                for(int i=0;i<caseinp.length;i++)
                {
                    circuit.inputNames.add(caseinp[i].trim());
                }
                
                String output = caseStatements[0].substring(caseStatements[0].indexOf(":")+1,caseStatements[0].indexOf("="));
                output = output.trim();
                if(output.contains("{"))
                {
                    if(!output.contains("}"))
                        System.out.println("Wrong Case Statement!!");
                    else
                    {
                        output = output.substring(output.indexOf("{")+1, output.lastIndexOf("}"));
                        String outputpieces[] = output.split(",");
                        for(int i=0;i<outputpieces.length;i++)
                        {
                            circuit.outputNames.add(outputpieces[i].trim());
                            //System.out.println("These are the outputs!!"+outputpieces[i].trim());
                        }
                    }
                }
                else
                {
                    circuit.outputNames.add(output);
                    //System.out.println("OUTPUT : " + output);
                }
                
                //System.out.println(output);
                 HashMap<Integer,Character> tt = new HashMap<Integer,Character>();
                for (int j = 0; j < circuit.outputNames.size(); j++) 
                {
                    tt = new HashMap<Integer,Character>();
                    for (int i = 0; i < caseStatements.length; i++) 
                    {
                        String xcase = caseStatements[i].trim();
                        String cNum = xcase.substring(0, xcase.indexOf(":")).trim();
                        int caseNumber = Convert.toDec(cNum);
                        //System.out.println(caseNumber);
                        String sNum = xcase.substring(xcase.indexOf("=") + 1).trim();
                        //System.out.println("xcase : "+xcase);
                        //System.out.println("cNum : "+cNum);
                        //System.out.println("sNum : "+sNum);
                        
                        if (sNum.contains("'")) 
                        {
                            if (sNum.contains("b")) 
                            {
                                String bintt = sNum.substring(sNum.indexOf("b")+1);
                                char ttnum = bintt.charAt(j);
                                //if(bintt.charAt(j) =='1')
                                //    ttnum = '1';
                                tt.put(caseNumber, ttnum);
                            } 
                            else if (sNum.contains("d")) 
                            {
                                System.out.println("Feature not yet supported");
                            }
                        }
                        else if (sNum.contains("’")) 
                        {
                            if (sNum.contains("b")) 
                            {
                                String bintt = sNum.substring(sNum.indexOf("b")+1);
                                char ttnum = bintt.charAt(j);
                                //if(bintt.charAt(j) =='1')
                                //    ttnum = '1';
                                tt.put(caseNumber, ttnum);
                            } 
                            else if (sNum.contains("d")) 
                            {
                                System.out.println("Feature not yet supported");
                            }
                        }
                        else 
                        {
                            System.out.println("Feature not yet supported");
                            //String bintt = Convert.dectoBin(Integer.parseInt(sNum), circuit.outputNames.size());
                            //int ttnum=0;
                            //if(bintt.charAt(j) =='1')
                            //    ttnum = 1;
                            //tt.put(caseNumber, ttnum);
                        }
                    }
                    //truthtable.add(tt);
                    charTruthTable.add(tt);
                }
                
                //<editor-fold desc="Commented out code that could handle all types of assigments in case statements">
                /*for (int j = 0; j < circuit.outputNames.size(); j++) 
                {
                    tt = new HashMap<Integer,Integer>();
                    for (int i = 0; i < caseStatements.length; i++) 
                    {
                        String xcase = caseStatements[i].trim();
                        String cNum = xcase.substring(0, xcase.indexOf(":")).trim();
                        int caseNumber = Convert.toDec(cNum);
                        //System.out.println(caseNumber);
                        String sNum = xcase.substring(xcase.indexOf("=") + 1).trim();
                        System.out.println("xcase : "+xcase);
                        System.out.println("cNum : "+cNum);
                        System.out.println("sNum : "+sNum);
                        
                        if (sNum.contains("'")) 
                        {
                            if (sNum.contains("b")) 
                            {
                                String bintt = sNum.substring(sNum.indexOf("b")+1);
                                int ttnum=0;
                                if(bintt.charAt(j) =='1')
                                    ttnum = 1;
                                tt.put(caseNumber, ttnum);
                            } 
                            else if (sNum.contains("d")) 
                            {
                                int bitnum = Integer.parseInt(sNum.substring(0, sNum.indexOf("'")));
                                String bintt = Convert.dectoBin(Integer.parseInt(sNum.substring(sNum.indexOf("d")+1)), circuit.outputNames.size()); 
                                int ttnum=0;
                                if(bintt.charAt(j) =='1')
                                    ttnum = 1;
                                tt.put(caseNumber, ttnum);
                            }
                        }
                        else if (sNum.contains("’")) 
                        {
                            if (sNum.contains("b")) 
                            {
                                String bintt = sNum.substring(sNum.indexOf("b")+1);
                                int ttnum=0;
                                if(bintt.charAt(j) =='1')
                                    ttnum = 1;
                                tt.put(caseNumber, ttnum);
                            } 
                            else if (sNum.contains("d")) 
                            {
                                int bitnum = Integer.parseInt(sNum.substring(0, sNum.indexOf("’")));
                                String bintt = Convert.dectoBin(Integer.parseInt(sNum.substring(sNum.indexOf("d")+1)), circuit.outputNames.size()); 
                                int ttnum=0;
                                if(bintt.charAt(j) =='1')
                                    ttnum = 1;
                                tt.put(caseNumber, ttnum);
                            }
                        }
                        else 
                        {
                            String bintt = Convert.dectoBin(Integer.parseInt(sNum), circuit.outputNames.size());
                            int ttnum=0;
                            if(bintt.charAt(j) =='1')
                                ttnum = 1;
                            tt.put(caseNumber, ttnum);
                        }
                    }
                    truthtable.add(tt);
                }*/
                //</editor-fold>
                
                int numTT = (int) Math.pow(2, caseinp.length);
                
                char[] xbits = new char[numTT];
                
                for (int j = 0; j < circuit.outputNames.size(); j++) 
                {
                    xbits = new char[numTT];
                    for (int i = 0; i < numTT; i++) 
                    {

                        if (charTruthTable.get(j).containsKey(i)) 
                        {

                            xbits[i] = charTruthTable.get(j).get(i).toString().charAt(0);
                        } 
                        else
                        {
                            if (charTruthTable.get(j).containsKey(-2)) 
                            {
                                xbits[i] = charTruthTable.get(j).get(-2).toString().charAt(0);
                            } 
                            else 
                            {
                                xbits[i] = '0';
                            }
                        }
                    }
                    //int truthtableval = Convert.bintoDec(new String(xbits));
                    String truthtableval = new String(xbits);
                    //System.out.println(truthtableval);
                    circuit.truthTable.add(truthtableval);
                }
            }
            else
            {
                return null;
            }
            
        }
        
        return circuit;
    }
    
    public static List<DGate> parseStructural(String alllines)
    {
        
         List<DGate> structnetlist = new ArrayList<DGate>();
         List<String> inputs = new ArrayList<String>();
         List<String> outputs = new ArrayList<String>();
         List<String> wirenames = new ArrayList<String>();
         List<String> unknownIO = new ArrayList<String>();
         
         int x = 0;
        
        CircuitDetails circuit = new CircuitDetails();
        //System.out.println(alllines);
        //System.out.println(alllines);
        //<editor-fold desc="Extract Module IO Contraints">
        String moduleString = alllines.substring(alllines.indexOf("module "),alllines.indexOf(";"));
        alllines = alllines.substring((alllines.indexOf(moduleString) + moduleString.length()+1), alllines.indexOf(" endmodule"));
        alllines = alllines.trim();
        moduleString = moduleString.trim();
        moduleString = moduleString.substring((moduleString.indexOf("(")+1),moduleString.indexOf(")"));
        //System.out.println(alllines);
        
        //System.out.println(moduleString);
        String[] modulePieces = moduleString.split(",");
        for(int i=0;i<modulePieces.length;i++)
        {
            String IO = modulePieces[i].trim();
            if(IO.contains("input "))
            {
                IO = IO.substring(IO.indexOf("input ")+6);
                IO = IO.trim();
                if(!inputs.contains(IO))
                    inputs.add(IO);
                
                for(int j=i+1;j<modulePieces.length;j++)
                {
                    String IOnext = modulePieces[j].trim();
                    if(IOnext.contains("output "))
                        break;
                    if(!inputs.contains(IOnext))
                        inputs.add(IOnext);
                }
                
            }
            else if(IO.contains("output "))
            {
                
                IO = IO.substring(IO.indexOf("output ")+7);
                IO = IO.trim();
                if(!outputs.contains(IO))
                    outputs.add(IO);
                for(int j=i+1;j<modulePieces.length;j++)
                {
                    String IOnext = modulePieces[j].trim();
                    if(IOnext.contains("input "))
                        break;
                    if(!outputs.contains(IOnext))
                        outputs.add(IOnext);
                }
            }
            else
            {
                unknownIO.add(IO);   
                
            }
        }
        //System.out.println(alllines);
        //</editor-fold>
        
        List<String> VerilogLines = new ArrayList<String>();
        String temp="";
        int caseLocation=-1;
        int cnt =0;
        //System.out.println(alllines);
        while(alllines.contains(";") || alllines.length()>0)
        {
            
            
            if(alllines.contains(";"))
            {
                //System.out.println(alllines);
                VerilogLines.add(alllines.substring(0,alllines.indexOf(";")));
                alllines = alllines.substring(alllines.indexOf(";")+1);
                alllines = alllines.trim();
            }    
            else
            {
                VerilogLines.add(alllines);
                alllines = "";
            }
           
            cnt++;
        }
        String wirekw = VerilogKeywords.WIRE.toString().toLowerCase();
        
        for(String xline:VerilogLines)
        {
            xline = xline.trim();
            if(xline.contains(wirekw+" "))
            {
                
                if(xline.substring(0,wirekw.length()+1).equals(wirekw+" "))
                {
                    xline = xline.substring(wirekw.length());
                    String wNames[] = xline.split(",");
                    for(int i=0;i<wNames.length;i++)
                    {
                        if(!wirenames.contains(wNames[i].trim()))
                            wirenames.add(wNames[i].trim());
                    }
                }
            }
        }
        List<DWire> inputWires = new ArrayList<DWire>();
        List<DWire> outputWires = new ArrayList<DWire>();
        List<DWire> connWires = new ArrayList<DWire>();
        
        for(int i=0;i<inputs.size();i++)
        {
            DWire inpW = new DWire(inputs.get(i),DWireType.input);
            inputWires.add(inpW);
        }
        for(int i=0;i<outputs.size();i++)
        {
            DWire outpW = new DWire(outputs.get(i),DWireType.output);
            outputWires.add(outpW);
        }
        for(int i=0;i<wirenames.size();i++)
        {
            DWire connW = new DWire(wirenames.get(i),DWireType.connector);
            connWires.add(connW);
        }
        
        String xnorkw = VerilogKeywords.XNOR.toString().toLowerCase();
        String xorkw = VerilogKeywords.XOR.toString().toLowerCase();
        String orkw = VerilogKeywords.OR.toString().toLowerCase();
        String norkw = VerilogKeywords.NOR.toString().toLowerCase();
        String andkw = VerilogKeywords.AND.toString().toLowerCase();
        String nandkw = VerilogKeywords.NAND.toString().toLowerCase();
        String notkw = VerilogKeywords.NOT.toString().toLowerCase();
        String bufkw = VerilogKeywords.BUF.toString().toLowerCase();
        
        for(String xline:VerilogLines)
        {
            xline = xline.trim();
            if(xline.contains(xnorkw+" "))
            {
               if(xline.substring(0,xnorkw.length()+1).equals(xnorkw+" "))
               {
                   String templ = xline;
                   templ = templ.substring(xnorkw.length());
                   templ = templ.trim();
                   String gateparam = templ.substring(templ.indexOf("(")+1);
                   gateparam = gateparam.substring(0,gateparam.indexOf(")"));
                   //System.out.println(gateparam);
                   structnetlist.add(parseLineToGate(gateparam,xnorkw,inputWires,outputWires,connWires));
               }
            }
            if(xline.contains(xorkw+" "))
            {
               if(xline.substring(0,xorkw.length()+1).equals(xorkw+" "))
               {
                   String templ = xline;
                   templ = templ.substring(xorkw.length());
                   templ = templ.trim();
                   String gateparam = templ.substring(templ.indexOf("(")+1);
                   gateparam = gateparam.substring(0,gateparam.indexOf(")"));
                   //System.out.println(gateparam);
                   structnetlist.add(parseLineToGate(gateparam,xorkw,inputWires,outputWires,connWires));
               }
            }
            if(xline.contains(orkw+" "))
            {
               if(xline.substring(0,orkw.length()+1).equals(orkw+" "))
               {
                   String templ = xline;
                   templ = templ.substring(orkw.length());
                   templ = templ.trim();
                   String gateparam = templ.substring(templ.indexOf("(")+1);
                   gateparam = gateparam.substring(0,gateparam.indexOf(")"));
                   //System.out.println(gateparam);
                   structnetlist.add(parseLineToGate(gateparam,orkw,inputWires,outputWires,connWires));
               }
            }
            if(xline.contains(norkw+" "))
            {
               if(xline.substring(0,norkw.length()+1).equals(norkw+" "))
               {
                   String templ = xline;
                   templ = templ.substring(norkw.length());
                   templ = templ.trim();
                   String gateparam = templ.substring(templ.indexOf("(")+1);
                   gateparam = gateparam.substring(0,gateparam.indexOf(")"));
                   //System.out.println(gateparam);
                   structnetlist.add(parseLineToGate(gateparam,norkw,inputWires,outputWires,connWires));
               }
            }
            
            if(xline.contains(nandkw+" "))
            {
               if(xline.substring(0,nandkw.length()+1).equals(nandkw+" "))
               {
                   String templ = xline;
                   templ = templ.substring(nandkw.length());
                   templ = templ.trim();
                   String gateparam = templ.substring(templ.indexOf("(")+1);
                   gateparam = gateparam.substring(0,gateparam.indexOf(")"));
                   //System.out.println(gateparam);
                   structnetlist.add(parseLineToGate(gateparam,nandkw,inputWires,outputWires,connWires));
               }
            }
            if(xline.contains(andkw+" "))
            {
               if(xline.substring(0,andkw.length()+1).equals(andkw+" "))
               {
                   String templ = xline;
                   templ = templ.substring(andkw.length());
                   templ = templ.trim();
                   String gateparam = templ.substring(templ.indexOf("(")+1);
                   gateparam = gateparam.substring(0,gateparam.indexOf(")"));
                   //System.out.println(gateparam);
                   structnetlist.add(parseLineToGate(gateparam,andkw,inputWires,outputWires,connWires));
               }
            }
            if(xline.contains(notkw+" "))
            {
               if(xline.substring(0,notkw.length()+1).equals(notkw+" "))
               {
                   String templ = xline;
                   templ = templ.substring(notkw.length());
                   templ = templ.trim();
                   String gateparam = templ.substring(templ.indexOf("(")+1);
                   gateparam = gateparam.substring(0,gateparam.indexOf(")"));
                   //System.out.println(gateparam);
                   structnetlist.add(parseLineToGate(gateparam,notkw,inputWires,outputWires,connWires));
               }
            }
            if(xline.contains(bufkw+" "))
            {
               if(xline.substring(0,bufkw.length()+1).equals(bufkw+" "))
               {
                   String templ = xline;
                   templ = templ.substring(bufkw.length());
                   templ = templ.trim();
                   String gateparam = templ.substring(templ.indexOf("(")+1);
                   gateparam = gateparam.substring(0,gateparam.indexOf(")"));
                   //System.out.println(gateparam);
                   structnetlist.add(parseLineToGate(gateparam,bufkw,inputWires,outputWires,connWires));
               }
            }
        }
        //for(int i=0;i<structnetlist.size();i++)
        //    System.out.println(NetSynth.netlist(structnetlist.get(i)));
        return structnetlist;
    }
    
    
    public static boolean hasDontCares(List<String> TruthTableVals)
    {
        boolean hasdontcares = false;
        
        for(int i=0;i<TruthTableVals.size();i++)
        {
            if(TruthTableVals.get(i).contains("x"))
            {
                hasdontcares = true;
                return hasdontcares;
                //return true;
            }
        }
        
        return hasdontcares;
    }
    
    
    public static boolean isStructural(String alllines)
    {
        boolean noAlwaysBlock = true;
        while(alllines.contains(";") || alllines.length()>0)
        {
            if(alllines.startsWith("always ") || alllines.startsWith("always@") || alllines.startsWith("assign "))
            {
                noAlwaysBlock = false;
                break;
            }
            else if(alllines.contains(";"))
            {
                //System.out.println(alllines);
                alllines = alllines.substring(alllines.indexOf(";")+1);
                alllines = alllines.trim();
            }    
            else
            {
                alllines = "";
            }
        }
        return noAlwaysBlock;
    }
    
    public static boolean hasCaseStatements(String alllines)
    {
        boolean hascasestatements = true;
        String temp="";
        int caseLocation=-1;
        int cnt =0;
        while(alllines.contains(";") || alllines.length()>0)
        {
            
            if(alllines.startsWith("always ") || alllines.startsWith("always@"))
            {
                if(alllines.contains(" begin "))
                {
                    int lastbeginIndx=0;
                    if((alllines.lastIndexOf(" end")+4) == alllines.length())
                    {
                        temp = alllines.substring(0, alllines.lastIndexOf(" end")+4);
                        //VerilogLines.add(temp);
                        alllines = alllines.substring(alllines.lastIndexOf(" end")+4);
                    }
                    else
                    {
                        temp = alllines.substring(0, alllines.lastIndexOf(" end ")+5);
                        //VerilogLines.add(temp);
                        alllines = alllines.substring(alllines.lastIndexOf(" end ")+5);
                    }
                if(temp.contains(" endcase "))
                    caseLocation = cnt;
                alllines = alllines.trim();
                }
                
            }
            else if(alllines.contains(";"))
            {
                //System.out.println(alllines);
                alllines = alllines.substring(alllines.indexOf(";")+1);
                alllines = alllines.trim();
            }    
            else
            {
                alllines = "";
            }
           
            cnt++;
        }
        
        if(caseLocation == -1)
        {
            hascasestatements = false;
        }
        return hascasestatements;
    }
    
    public static DGate parseLineToGate(String codeline,String gatetype,List<DWire> inputWires, List<DWire> outputWires, List<DWire> connWires)
    {
        DGate pgate = new DGate();
        String params[] = codeline.split(",");
        String gout = params[0].trim();
        for(DWire xoutW:outputWires)
        {
            if(xoutW.name.equals(gout))
            {
                pgate.output = xoutW;
            }
        }
        for(DWire xconnW:connWires)
        {
            if(xconnW.name.equals(gout))
            {
                pgate.output = xconnW;
            }
        }
        for(int i=1;i<params.length;i++)
        {
            String gin = params[i].trim();
            for(DWire xinW:inputWires)
            {
                if(xinW.name.equals(gin))
                {
                    pgate.input.add(xinW);
                }
            }
            for(DWire xconnW:connWires)
            {
                if(xconnW.name.equals(gin))
                {
                    pgate.input.add(xconnW);
                }
            }
            for(DWire xconnW:outputWires)
            {
                if(xconnW.name.equals(gin))
                {
                    pgate.input.add(xconnW);
                }
            }
        }
        if(gatetype.equals("xnor"))
            pgate.gtype = DGateType.XNOR;
        if(gatetype.equals("xor"))
            pgate.gtype = DGateType.XOR;
        if(gatetype.equals("nor"))
            pgate.gtype = DGateType.NOR;
        if(gatetype.equals("or"))
            pgate.gtype = DGateType.OR;
        if(gatetype.equals("not"))
            pgate.gtype = DGateType.NOT;
        if(gatetype.equals("buf"))
            pgate.gtype = DGateType.BUF;
        if(gatetype.equals("and"))
            pgate.gtype = DGateType.AND;
        if(gatetype.equals("nand"))
            pgate.gtype = DGateType.NAND;
        return pgate;
    }
}
