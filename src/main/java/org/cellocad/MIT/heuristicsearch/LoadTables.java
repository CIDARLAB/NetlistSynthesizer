package org.cellocad.MIT.heuristicsearch;

import org.cellocad.MIT.dnacompiler.BGateCombo;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.cellocad.MIT.dnacompiler.Gate.GateType;
//import org.cellocad.MIT.dnacompiler.BGateCombo;


public class LoadTables {

    //*****Pigeon variables**********\\
    public static ArrayList<BGateCombo> all_combos = new ArrayList<BGateCombo>();


    public static ArrayList<BGateCombo> getAllCombos(Double score_cutoff)
    {

	String Filepath ="";
	Filepath = LoadTables.class.getClassLoader().getResource(".").getPath();
        if(Filepath.contains("build/classes/"))
            Filepath = Filepath.substring(0,Filepath.lastIndexOf("build/classes/")); 
        else if(Filepath.contains("src"))
            Filepath = Filepath.substring(0,Filepath.lastIndexOf("src/"));
       
        if(Filepath.contains("prash"))
        {
            Filepath += "src/MIT/dnacompiler/";
        }
        else
        {
            Filepath += "MIT/dnacompiler/";
        }

	//String file_NOT = Filepath + "list_NOT_pairs.txt";
	//String file_NOR = Filepath + "list_NOR_trips.txt";
        String file_NOT = Filepath + "synth_NOT_pairs.txt";
	String file_NOR = Filepath + "synth_NOR_trips.txt";

	File fileNOT = new File(file_NOT);
	BufferedReader brNOT;
	FileReader frNOT;
	//ArrayList<BGateType> moclo_lines = new ArrayList<BGateType>();
	try {
	    frNOT = new FileReader(fileNOT);
	    brNOT = new BufferedReader(frNOT);
	    String line;
	    try {
		while((line = brNOT.readLine()) != null ) {
		    //moclo_lines.add(line);
		    //System.out.println(line);

		    //SPLIT LINE
		    String lines[] = line.split(" ");
		    BGateCombo bgc = new BGateCombo();
		    bgc.Gtype = GateType.NOT;
		    bgc.Inp1 = lines[0];
		    bgc.Inp2 = null;
		    bgc.Out = lines[1];
		    bgc.score = Double.parseDouble(lines[2]);

		    if(bgc.score >= score_cutoff)
			all_combos.add(bgc);
		}
	    }
	    catch (IOException ex) {
		System.out.println("IOException when reading input file");
	    }
	} 
	catch (FileNotFoundException ex) {
	    System.out.println("FileNotFoundException when reading input file");
	}




	//parse NOR file
	File fileNOR = new File(file_NOR);
	BufferedReader brNOR;
	FileReader frNOR;
	//ArrayList<BGateType> moclo_lines = new ArrayList<BGateType>();
	try {
	    frNOR = new FileReader(fileNOR);
	    brNOR = new BufferedReader(frNOR);
	    String line;
	    try {
		while((line = brNOR.readLine()) != null ) {
		    //moclo_lines.add(line);
		    //System.out.println("NOR " + line);

		    String lines[] = line.split(" ");
		    BGateCombo bgc = new BGateCombo();
		    bgc.Gtype = GateType.NOR;
		    bgc.Inp1 = lines[0];
		    bgc.Inp2 = lines[1];
		    bgc.Out = lines[2];
		    bgc.score = Double.parseDouble(lines[3]);

		    if(bgc.score >= score_cutoff)
			all_combos.add(bgc);
		}
	    }
	    catch (IOException ex) {
		System.out.println("IOException when reading input file");
	    }
	} 
	catch (FileNotFoundException ex) {
	    System.out.println("FileNotFoundException when reading input file");
	}
        return all_combos;

    }
    
    
    
    public static int NOTgateCount(List<BGateCombo> allcombos)
    {
        int num =0;
        for(BGateCombo bgc:allcombos)
        {
            if(bgc.Gtype == GateType.NOT)
                num++;
        }
        return num;
    }
    public static List<BGateCombo> dividelist(List<BGateCombo> allcombos, GateType nornot)
    {
        List<BGateCombo> combolist = new ArrayList<BGateCombo>();
        
        for(BGateCombo bgc:allcombos)
        {
            if(bgc.Gtype == nornot)
                combolist.add(bgc);
        }
        return combolist;
    }
    
    public static void printallCombos(List<BGateCombo> allcombos)
    {
	
	for(BGateCombo bgc:allcombos)
	{
            if(bgc.Inp2 != null)
                System.out.println(bgc.Gtype + " " + bgc.Inp1 + " + " + bgc.Inp2 +" ==> "+ bgc.Out + " : " + bgc.score);
            else
                System.out.println(bgc.Gtype + " " + bgc.Inp1 + " ==> "+ bgc.Out + " : " + bgc.score);   
	}
        System.out.println("Total number of Bgate combos " + all_combos.size());
        
    }

}
