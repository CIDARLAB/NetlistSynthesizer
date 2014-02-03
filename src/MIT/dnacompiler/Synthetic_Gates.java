/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MIT.dnacompiler;

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
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author prashantvaidyanathan
 */
public class Synthetic_Gates {
    
    
    public static void genrandomGateInpFiles(int numofgates, int numofinp)
    {
        List<Double> pmin = new ArrayList<Double>();
        List<Double> pmax = new ArrayList<Double>();
        List<Double> kd = new ArrayList<Double>();
        List<Double> n = new ArrayList<Double>();
        
        List<Double> indpmin = new ArrayList<Double>();
        List<Double> indpmax = new ArrayList<Double>();
        
        String Filepath ="";
        Filepath = Synthetic_Gates.class.getClassLoader().getResource(".").getPath();
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
	
	File gate_file = new File(file_gates);
	BufferedReader brgate;
	FileReader frgate;
	
	try {
	    frgate = new FileReader(gate_file);
	    brgate = new BufferedReader(frgate);
	    String line;
	    try 
            {
		while((line = brgate.readLine()) != null ) 
                {
	            String lines[] = line.split(" ");
                    //TransferFunction entry = new TransferFunction();
                    //entry.bgname = lines[0];
                    pmax.add(Double.parseDouble(lines[1]));
                    pmin.add(Double.parseDouble(lines[2]));
                      kd.add(Double.parseDouble(lines[3]));
                       n.add(Double.parseDouble(lines[4]));
                }
	    }
	    catch (IOException ex) {
		System.out.println("IOException when reading input file");
	    }
	} 
	catch (FileNotFoundException ex) {
	    System.out.println("FileNotFoundException when reading input file");
	}
        
        String inp_file = Filepath + "InpFunc.txt";
	
	File fileinp = new File(inp_file);
	BufferedReader brinp;
	FileReader frinp;
	
	try {
	    frinp = new FileReader(fileinp);
	    brinp = new BufferedReader(frinp);
	    String line;
	    try 
            {
		while((line = brinp.readLine()) != null ) 
                {
	            String lines[] = line.split(" ");
                    //TransferFunction entry = new TransferFunction();
                    //entry.bgname = lines[0];
                    indpmax.add(Double.parseDouble(lines[1]));
                    indpmin.add(Double.parseDouble(lines[2]));
                }
	    }
	    catch (IOException ex) {
		System.out.println("IOException when reading input file");
	    }
	} 
	catch (FileNotFoundException ex) {
	    System.out.println("FileNotFoundException when reading input file");
	}
    
        String file_gate = Filepath + "synth_Gates.txt";
        String file_inp = Filepath + "synth_inducer.txt";
        
        File fespgate = new File(file_gate);
        File fespinp = new File(file_inp);
        try 
        {
            
            Writer output = new BufferedWriter(new FileWriter(fespgate));
            Writer outputinp = new BufferedWriter(new FileWriter(fespinp));
            
            String line ="";
            Random randg = new Random();
            Random randinp = new Random();
                
            for(int i=0;i<numofgates;i++)
            {
                int randpmax = randg.nextInt(pmin.size());
                int randpmin = randg.nextInt(pmin.size());
                int randkd = randg.nextInt(pmin.size());
                int randn = randg.nextInt(pmin.size());
                
                
                line = "NOTsynth_" + i + " " + pmax.get(randpmax) + " "+pmin.get(randpmin) + " " + kd.get(randkd) + " "+ n.get(randn) +"\n"; 
                output.write(line);
            }
            output.close();
            for(int i=0;i<numofinp;i++)
            {
                int randpmax = randinp.nextInt(indpmin.size());
                int randpmin = randinp.nextInt(indpmin.size());
                line = "inducerSynth_" + i + " " + indpmax.get(randpmax) + " "+indpmin.get(randpmin) +"\n"; 
                outputinp.write(line);
            }
            outputinp.close();
            
        
        } catch (IOException ex) {
            Logger.getLogger(Synthetic_Gates.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    
    
    
    }
    
    public static void genidealGateInpFiles(int numofgates, int numofinp)
    {
        String Filepath;
        Filepath = Synthetic_Gates.class.getClassLoader().getResource(".").getPath();
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
        String file_gates = Filepath + "synth_Gates.txt";
        
        String file_inp = Filepath + "synth_inducer.txt";
        
        File fespgate = new File(file_gates);
        File fespinp = new File(file_inp);
        try 
        {
            
            Writer output = new BufferedWriter(new FileWriter(fespgate));
            Writer outputinp = new BufferedWriter(new FileWriter(fespinp));
            
            String line ="";
            for(int i=0;i<numofgates;i++)
            {
                line = "NOTsynth_" + i + " 50.000000 0.025000 0.800000 18\n"; 
                output.write(line);
            }
            output.close();
            for(int i=0;i<numofinp;i++)
            {
                line = "inducerSynth_" + i + " 50.000000 0.025000\n"; 
                outputinp.write(line);
            }
            outputinp.close();
            
        
        } catch (IOException ex) {
            Logger.getLogger(Synthetic_Gates.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void genExistingGateInpFiles()
    {
        String Filepath;
        Filepath = Synthetic_Gates.class.getClassLoader().getResource(".").getPath();
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
        String file_gates = Filepath + "synth_Gates.txt";
        String original_gates = Filepath + "TransferFunc.txt";
        String file_inp = Filepath + "synth_inducer.txt";
        String original_inp = Filepath + "InpFunc.txt";
        
        BufferedReader brgate;
	FileReader frgate;
        BufferedReader brinp;
	FileReader frinp;
        int cnt=1;
        File fespgate = new File(file_gates);
        File fespinp = new File(file_inp);
        try 
        {
            frgate = new FileReader(original_gates);
	    brgate = new BufferedReader(frgate);
            frinp = new FileReader(original_inp);
	    brinp = new BufferedReader(frinp);
            Writer output = new BufferedWriter(new FileWriter(fespgate));
            Writer outputinp = new BufferedWriter(new FileWriter(fespinp));
            
            String line ="";
            while((line = brgate.readLine()) != null ) 
            {
                line +="\n";
                output.write(line);
            }
            output.close();
            while((line = brinp.readLine()) != null ) 
            {
                line +="\n";
                outputinp.write(line);
            }
            outputinp.close();
            
        
        } catch (IOException ex) {
            Logger.getLogger(Synthetic_Gates.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void genNotpairs()
    {
        HashMap<String,TransferFunction> gatefunctions = new HashMap<String,TransferFunction>();
        HashMap<String,TransferFunction> inpfunctions = new HashMap<String,TransferFunction>();
        
        
        HashMap<Integer,TransferFunction> allgates = new HashMap<Integer,TransferFunction>();
        List<String> gatesF = new ArrayList<String>();
        List<String> complgatesF = new ArrayList<String>();
        
        gatefunctions = getGateFunction();
        inpfunctions = getInpFunction();
        
        int cnt=0;
        for(String xname: gatefunctions.keySet())
        {
            gatesF.add(xname);
            complgatesF.add(xname);
        }
        for(String xname: inpfunctions.keySet())
        {
            complgatesF.add(xname);
        }
        for(int i=0;i<complgatesF.size();i++)
        {
            String tempg = complgatesF.get(i);
            if(inpfunctions.containsKey(tempg))
            {
                allgates.put(cnt,inpfunctions.get(tempg));
                cnt++;
            }
            else if(gatefunctions.containsKey(tempg))
            {
                allgates.put(cnt,gatefunctions.get(tempg));
                cnt++;
            }
            
        }
        String Filepath;
        Filepath = Synthetic_Gates.class.getClassLoader().getResource(".").getPath();
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
        String file_gates = Filepath + "synth_NOT_pairs.txt";
        
        File fespinp = new File(file_gates);
        try 
        {
            
            Writer output = new BufferedWriter(new FileWriter(fespinp));
            String Line ="";
            for(int i=0;i<gatesF.size();i++)
            {
                String outname = gatesF.get(i);
                TransferFunction out = new TransferFunction();
                out = gatefunctions.get(outname);
                for(int j=0;j<cnt;j++)
                {
                    TransferFunction inp1 = new TransferFunction();
                    inp1 = allgates.get(j);
                    if(inp1.bgname.equals(out.bgname))
                        continue;
                    double score0 = HeuristicSearch.ScoreGate(out.pmin, out.pmax, out.kd, out.n, (inp1.pmin));
                    double score1 = HeuristicSearch.ScoreGate(out.pmin, out.pmax, out.kd, out.n, (inp1.pmax));
                    double finalscore = (1 - (score1/score0));
                    Line = inp1.bgname + " " + out.bgname + " " + finalscore + "\n"; 
                    output.write(Line);
                }
            }
            
            
            
            output.close();
            
            
        } catch (IOException ex) {
            Logger.getLogger(Synthetic_Gates.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    
    }
    
    public static void genNortrips()
    {
        HashMap<String,TransferFunction> gatefunctions = new HashMap<String,TransferFunction>();
        HashMap<String,TransferFunction> inpfunctions = new HashMap<String,TransferFunction>();
        
        
        HashMap<Integer,TransferFunction> allgates = new HashMap<Integer,TransferFunction>();
        List<String> gatesF = new ArrayList<String>();
        List<String> complgatesF = new ArrayList<String>();
        
        gatefunctions = getGateFunction();
        inpfunctions = getInpFunction();
        
        int cnt=0;
        for(String xname: gatefunctions.keySet())
        {
            gatesF.add(xname);
            complgatesF.add(xname);
        }
        for(String xname: inpfunctions.keySet())
        {
            complgatesF.add(xname);
        }
        for(int i=0;i<complgatesF.size();i++)
        {
            String tempg = complgatesF.get(i);
            if(inpfunctions.containsKey(tempg))
            {
                allgates.put(cnt,inpfunctions.get(tempg));
                cnt++;
            }
            else if(gatefunctions.containsKey(tempg))
            {
                allgates.put(cnt,gatefunctions.get(tempg));
                cnt++;
            }
            
        }
        String Filepath;
        Filepath = Synthetic_Gates.class.getClassLoader().getResource(".").getPath();
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
        String file_gates = Filepath + "synth_NOR_trips.txt";
        
        File fespinp = new File(file_gates);
        try 
        {
            
            Writer output = new BufferedWriter(new FileWriter(fespinp));
            String Line ="";
            for(int i=0;i<gatesF.size();i++)
            {
                String outname = gatesF.get(i);
                TransferFunction out = new TransferFunction();
                out = gatefunctions.get(outname);
                for(int j=0;j<cnt;j++)
                {
                    TransferFunction inp1 = new TransferFunction();
                    inp1 = allgates.get(j);
                    if(inp1.bgname.equals(out.bgname))
                        continue;
                    for(int k=j;k<cnt;k++)
                    {
                        if(k==j)
                            continue;
                        TransferFunction inp2 = new TransferFunction();
                        inp2 = allgates.get(k);
                        if(inp2.bgname.equals(inp1.bgname) ||  inp2.bgname.equals(out.bgname))
                            continue;
                        
                        double score00 = HeuristicSearch.ScoreGate(out.pmin, out.pmax, out.kd, out.n, (inp1.pmin + inp2.pmin));
                        double score01 = HeuristicSearch.ScoreGate(out.pmin, out.pmax, out.kd, out.n, (inp1.pmin + inp2.pmax));
                        double score10 = HeuristicSearch.ScoreGate(out.pmin, out.pmax, out.kd, out.n, (inp1.pmax + inp2.pmin));
                        double score11 = HeuristicSearch.ScoreGate(out.pmin, out.pmax, out.kd, out.n, (inp1.pmax + inp2.pmax));
                        
                        double maxscore = score01;
                        if(score10>maxscore)
                            maxscore = score10;
                        if(score11>maxscore)
                            maxscore = score11;
                        
                        double finalscore = (1 - (maxscore/score00));
                        Line = inp1.bgname + " " + inp2.bgname + " " + out.bgname + " " + finalscore + "\n"; 
                        output.write(Line);
                    }
                }
            }
            
            
            
            output.close();
            
            
        } catch (IOException ex) {
            Logger.getLogger(Synthetic_Gates.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    
    }
    
     
    public static HashMap<String,TransferFunction> getGateFunction()
    {

	String Filepath ="";
        HashMap<String,TransferFunction> gatefunctions = new HashMap<String,TransferFunction>();
        
	Filepath = Synthetic_Gates.class.getClassLoader().getResource(".").getPath();
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

	String file_gates = Filepath + "synth_Gates.txt";
	
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
        
	Filepath = Synthetic_Gates.class.getClassLoader().getResource(".").getPath();
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

	String file_inputs = Filepath + "synth_inducer.txt";

	
        
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
