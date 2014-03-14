/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BU.precomputation;

import MIT.dnacompiler.reuHist;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author prashantvaidyanathan
 */
public class HistogramREU {
    public static void calcHist()
    {
        String Filepath;
        Filepath = HistogramREU.class.getClassLoader().getResource(".").getPath();
        if(Filepath.contains("build/classes/"))
            Filepath = Filepath.substring(0,Filepath.lastIndexOf("build/classes/")); 
        else if(Filepath.contains("src"))
            Filepath = Filepath.substring(0,Filepath.lastIndexOf("src/"));
       
        if(Filepath.contains("prashant"))
        {
            Filepath += "src/BU/resources/";
        }
        else
        {
            Filepath += "BU/resources/";
        }
        
        //<editor-fold desc="Black REU">
	String file_black = Filepath + "black.txt";
        reuHist black = new reuHist();
        black.titrationpoint = -0.37468101;
        File fileblack = new File(file_black);
	BufferedReader brblack;
	FileReader frblack;
	try {
	    frblack = new FileReader(fileblack);
	    brblack = new BufferedReader(frblack);
	    String line;
	    try {
		while((line = brblack.readLine()) != null ) {
	
		   String[] values = line.split(" ");
                   black.x.add(Double.parseDouble(values[0]));
		   black.y.add(Double.parseDouble(values[1]));
		}
	    }
	    catch (IOException ex) {
		System.out.println("IOException when reading input file");
	    }
	} 
	catch (FileNotFoundException ex) {
	    System.out.println("FileNotFoundException when reading input file");
	}
        //</editor-fold>
        
        //<editor-fold desc="Gray REU">
	String file_gray = Filepath + "gray.txt";
        reuHist gray = new reuHist();
        gray.titrationpoint = -1.341093805;
        File filegray = new File(file_gray);
	BufferedReader brgray;
	FileReader frgray;
	try {
	    frgray = new FileReader(filegray);
	    brgray = new BufferedReader(frgray);
	    String line;
	    try {
		while((line = brgray.readLine()) != null ) {
	
		   String[] values = line.split(" ");
                   gray.x.add(Double.parseDouble(values[0]));
		   gray.y.add(Double.parseDouble(values[1]));
		}
	    }
	    catch (IOException ex) {
		System.out.println("IOException when reading input file");
	    }
	} 
	catch (FileNotFoundException ex) {
	    System.out.println("FileNotFoundException when reading input file");
	}
        //</editor-fold>
        
        //<editor-fold desc="Blue REU">
	String file_blue = Filepath + "blue.txt";
        reuHist blue = new reuHist();
        blue.titrationpoint = -0.803574133407;
        File fileblue = new File(file_blue);
	BufferedReader brblue;
	FileReader frblue;
	try {
	    frblue = new FileReader(fileblue);
	    brblue = new BufferedReader(frblue);
	    String line;
	    try {
		while((line = brblue.readLine()) != null ) {
	
		   String[] values = line.split(" ");
                   blue.x.add(Double.parseDouble(values[0]));
		   blue.y.add(Double.parseDouble(values[1]));
		}
	    }
	    catch (IOException ex) {
		System.out.println("IOException when reading input file");
	    }
	} 
	catch (FileNotFoundException ex) {
	    System.out.println("FileNotFoundException when reading input file");
	}
        //</editor-fold>
        
        double blackmean =0;
        double graymean =0;
        double bluemean =0;
        
        for(int i=0;i<black.x.size();i++)
        {
            blackmean += (black.x.get(i) * black.y.get(i));
        }
        for(int i=0;i<gray.x.size();i++)
        {
            graymean += (gray.x.get(i) * gray.y.get(i));
        }
        for(int i=0;i<blue.x.size();i++)
        {
            bluemean += (blue.x.get(i) * blue.y.get(i));
        }
        black.mean = blackmean;
        blue.mean = bluemean;
        gray.mean = graymean;
        
        System.out.println("Black Mean :"+black.mean);
        System.out.println("Blue Mean :"+blue.mean);
        System.out.println("Gray Mean :"+gray.mean);
        
        reuHist blackshift = new reuHist();
        reuHist grayshift = new reuHist();
        blackshift.titrationpoint = black.titrationpoint;
        grayshift.titrationpoint = gray.titrationpoint;
        
        double shiftedgraymean =0;
        double shiftedblackmean =0;
        for(int i=0;i<black.x.size();i++)
        {
            blackshift.y.add(black.y.get(i));
            double shiftx = black.x.get(i) - blackmean;
            blackshift.x.add(shiftx);
            shiftedblackmean +=  (shiftx*black.y.get(i));
        }
        for(int i=0;i<gray.x.size();i++)
        {
            grayshift.y.add(gray.y.get(i));
            double shiftx = gray.x.get(i) - graymean;
            grayshift.x.add(shiftx);
            shiftedgraymean +=  (shiftx*gray.y.get(i));
        }
        grayshift.mean = shiftedgraymean;
        
        System.out.println("Shifted Black Mean :"+shiftedblackmean);
        System.out.println("Shifted Gray Mean :"+shiftedgraymean);
        
    }
}
