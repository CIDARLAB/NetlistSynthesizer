/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cellocad.ExhaustiveTests;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.cellocad.BU.netsynth.NetSynth;

/**
 *
 * @author prash
 */
public class AnalyzeResults {
    public static void format1(String filepath){
        File file = new File(filepath);
        try {
            FileReader filereader = new FileReader(file);
            BufferedReader reader = new BufferedReader(filereader);
            String line;
            while((line=reader.readLine()) != null){
                if(line.startsWith("That took")){
                    String pieces[] = line.split("::");
                    String time = pieces[1].trim();
                    time = time.substring(0,time.indexOf(" milliseconds"));
                    String nextLine = reader.readLine();
                    String ttpieces[] = nextLine.split("::");
                    String ttval = ttpieces[1].trim();
                    String circsize = ttpieces[2].trim();
                    circsize = circsize.substring(circsize.indexOf("=")+2);
                    System.out.println(ttval +","+circsize+","+time);
                }
                
            }
            reader.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(AnalyzeResults.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AnalyzeResults.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void format2(String filepath){
        File file = new File(filepath);
        try {
            FileReader filereader = new FileReader(file);
            BufferedReader reader = new BufferedReader(filereader);
            String line;
            while((line=reader.readLine()) != null){
                if(line.startsWith("Truth Table")){
                    String ttval = line.substring(line.indexOf(":: ")+3);
                    ttval = ttval.trim();
                    String directline = reader.readLine();
                    String invertline = reader.readLine();
                    String timeline = reader.readLine();
                    String time = timeline.substring(timeline.indexOf("::")+2,timeline.indexOf(" milliseconds"));
                    String circline = reader.readLine();
                    String circsize = circline.substring(circline.indexOf("= ")+2);
                    circsize = circsize.trim();
                    System.out.println(ttval+","+circsize+","+time);
                }
            }
            reader.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(AnalyzeResults.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AnalyzeResults.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public static void format3(String filepath){
        File file = new File(filepath);
        try {
            FileReader filereader = new FileReader(file);
            BufferedReader reader = new BufferedReader(filereader);
            String line;
            while((line=reader.readLine()) != null){
                if(line.startsWith("Truth Table")){
                    String ttval = line.substring(line.indexOf(":: ")+3);
                    ttval = ttval.trim();
                    //String directline = reader.readLine();
                    //String invertline = reader.readLine();
                    String timeline = reader.readLine();
                    String time = timeline.substring(timeline.indexOf("::")+2,timeline.indexOf(" milliseconds"));
                    String circline = reader.readLine();
                    String circsize = circline.substring(circline.indexOf("= ")+2);
                    circsize = circsize.trim();
                    System.out.println(ttval+","+circsize+","+time);
                }
            }
            reader.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(AnalyzeResults.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AnalyzeResults.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public static void main(String[] args) {
        String file1 = NetSynth.getResourcesFilepath();
        file1 += "/netSynthTestResults/all4input1output.txt";
        
        String file2 = NetSynth.getResourcesFilepath();
        file2 += "/netSynthTestResults/all4input1outputABC.txt";
        
        String file3 = NetSynth.getResourcesFilepath();
        file3 += "/netSynthTestResults/all4input1outputABCnoSwap.txt";
        //format1(file1);
        //format2(file2);
        format3(file3);
        //System.out.println("X ::: " + x.substring(x.indexOf("=")+2));
    }
}
