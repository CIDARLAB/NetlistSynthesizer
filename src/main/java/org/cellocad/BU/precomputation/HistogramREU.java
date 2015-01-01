/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cellocad.BU.precomputation;

import org.cellocad.MIT.heuristicsearch.reuHist;
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
    public static void calcHist(double titreval)
    {
        String Filepath;
        Filepath = HistogramREU.class.getClassLoader().getResource(".").getPath();
        if(Filepath.contains("build/classes/"))
            Filepath = Filepath.substring(0,Filepath.lastIndexOf("build/classes/")); 
        else if(Filepath.contains("src"))
            Filepath = Filepath.substring(0,Filepath.lastIndexOf("src/"));
       
        if(Filepath.contains("prash"))
        {
            Filepath += "src/org/cellocad/BU/resources/";
        }
        else
        {
            Filepath += "org/cellocad/BU/resources/";
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
        double graymeanavg=0;
        double bluemeanavg=0;
        double blackmeanavg=0;
        for(int i=0;i<black.x.size();i++)
        {
            if(black.x.get(i) >= blackmean)
            {
                blackmeanavg = black.x.get(i);
                break;
            }
        }
        for(int i=0;i<blue.x.size();i++)
        {
            if(blue.x.get(i) >= bluemean)
            {
                bluemeanavg = blue.x.get(i);
                break;
            }
        }
        for(int i=0;i<gray.x.size();i++)
        {
            if(gray.x.get(i) >= graymean)
            {
                graymeanavg = gray.x.get(i);
                break;
            }
        }
        black.mean = blackmeanavg;
        blue.mean = bluemeanavg;
        gray.mean = graymeanavg;
        
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
            double shiftx = black.x.get(i) - blackmeanavg;
            blackshift.x.add(shiftx);
            shiftedblackmean +=  (shiftx*black.y.get(i));
        }
        for(int i=0;i<gray.x.size();i++)
        {
            grayshift.y.add(gray.y.get(i));
            double shiftx = gray.x.get(i) - graymeanavg;
            grayshift.x.add(shiftx);
            shiftedgraymean +=  (shiftx*gray.y.get(i));
        }
        double shiftedgraymeanavg =0;
        double shiftedblackmeanavg =0;
        for(int i=0;i<black.x.size();i++)
        {
            if(blackshift.x.get(i)>= shiftedblackmean)
            {
                shiftedblackmeanavg = blackshift.x.get(i);
                break;
            }
        }
        for(int i=0;i<gray.x.size();i++)
        {
            if(grayshift.x.get(i)>= shiftedgraymean)
            {
                shiftedgraymeanavg = grayshift.x.get(i);
                break;
            }
        }
        
        grayshift.mean = shiftedgraymeanavg;
        blackshift.mean = shiftedblackmeanavg;
        
        grayshift.titrationpoint = gray.titrationpoint;
        blackshift.titrationpoint = black.titrationpoint;
        
        System.out.println("Shifted Black Mean :" + blackshift.mean);
        System.out.println("Shifted Gray Mean :" + grayshift.mean);
        reuHist greenshift = new reuHist();
        greenshift.titrationpoint = titreval;
        reuHist green = new reuHist();
        green.titrationpoint = titreval;
        double greenmean = 0;
        greenmean = gray.mean - (((gray.titrationpoint-green.titrationpoint)/(gray.titrationpoint-black.titrationpoint))*(gray.mean-black.mean));
        System.out.println("Predicted Green Mean :"+greenmean);
        if(grayshift.x.get(grayshift.x.size()-1) > blackshift.x.get(blackshift.x.size()-1))
        {
            for(int i=0;i<blackshift.x.size();i++)
            {
                double y1 = blackshift.y.get(i);
                int j=0;
                for(j=0;j<grayshift.x.size();j++)
                {
                    if(grayshift.x.get(j) >= blackshift.x.get(i))
                        break;
                }
                if(j==500)
                    j--;
                double y2 = grayshift.y.get(j);
                double yval = line_eq(black.titrationpoint,y1,gray.titrationpoint,y2,titreval);
                
                greenshift.x.add(blackshift.x.get(i));
                greenshift.y.add(yval);
            }
            int k=grayshift.x.size()-1;
            for(int j=0;j<grayshift.x.size();j++)
            {
                if(grayshift.x.get(j)>blackshift.x.get(blackshift.x.size()-1))
                {
                    k=j;
                    break;
                }
            }
            for(int j=k;j<grayshift.x.size();j++)
            {
                double y2 = grayshift.y.get(j);
                double y1 =0;
                double yval = line_eq(black.titrationpoint,y1,gray.titrationpoint,y2,titreval);
                greenshift.x.add(grayshift.x.get(j));
                greenshift.y.add(yval);
            }
        }
        else
        {
            for(int i=0;i<grayshift.x.size();i++)
            {
                double y1 = grayshift.y.get(i);
                int j=0;
                for(j=0;j<blackshift.x.size();j++)
                {
                    if(blackshift.x.get(j) >= grayshift.x.get(i))
                        break;
                }
                if(j==500)
                    j--;
                double y2 = blackshift.y.get(j);
                double yval = line_eq(gray.titrationpoint,y1,black.titrationpoint,y2,titreval);
                greenshift.x.add(grayshift.x.get(i));
                greenshift.y.add(yval);
            }
            int k=blackshift.x.size()-1;
            for(int j=0;j<blackshift.x.size();j++)
            {
                if(blackshift.x.get(j)>grayshift.x.get(grayshift.x.size()-1))
                {
                    k=j;
                    break;
                }
            }
            for(int j=k;j<blackshift.x.size();j++)
            {
                double y2 = blackshift.y.get(j);
                double y1 =0;
                double yval = line_eq(gray.titrationpoint,y1,black.titrationpoint,y2,titreval);
                greenshift.x.add(blackshift.x.get(j));
                greenshift.y.add(yval);
            }
        }
        //System.out.println("Shifted Black Mean :"+shiftedblackmean);
        //System.out.println("Shifted Gray Mean :"+shiftedgraymean);
        for(int i=0;i<greenshift.x.size();i++)
        {
            double tempx = greenshift.x.get(i) + greenmean;
            if(tempx > black.x.get(0) && tempx< black.x.get(black.x.size()-1))
            {
                green.x.add(tempx);
                green.y.add(greenshift.y.get(i));
            }
        }
        for(int i=0;i<green.x.size();i++)
        {
            System.out.println(green.x.get(i)+" "+green.y.get(i));
        }
    }
    public static double line_eq(double x1,double y1, double x2, double y2, double xval)
    {
        double yval=0;
        if(x1 == x2)
            return 0;
        yval = y1 + (((y1-y2)*(xval-x1))/(x1-x2));
        return yval;
    }
}

