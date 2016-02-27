/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cellocad.BU.netsynth;

import com.fasterxml.uuid.EthernetAddress;
import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedGenerator;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author prash
 */
public class Utilities {
    
    
    //<editor-fold desc="OS Check">
    public static boolean isSolaris() {
        String os = System.getProperty("os.name");
        return isSolaris(os);
    }

    public static boolean isSolaris(String os) {
        if (os.toLowerCase().indexOf("sunos") >= 0) {
            return true;
        }
        return false;
    }

    public static boolean isWindows() {
        String os = System.getProperty("os.name");
        return isWindows(os);
    }

    public static boolean isWindows(String os) {
        if (os.toLowerCase().indexOf("win") >= 0) {
            return true;
        }
        return false;
    }

    public static boolean isLinux() {
        String os = System.getProperty("os.name");
        return isLinux(os);
    }

    public static boolean isLinux(String os) {
        if ((os.toLowerCase().indexOf("nix") >= 0) || (os.indexOf("nux") >= 0) || (os.indexOf("aix") > 0)) {
            return true;
        }
        return false;
    }
    
    public static boolean isMac() {
        String os = System.getProperty("os.name");
        return isMac(os);
    }

    public static boolean isMac(String os) {
        if (os.toLowerCase().indexOf("mac") >= 0) {
            return true;
        }
        return false;
    }
    //</editor-fold>  
    
    public static String getUUID(){
        TimeBasedGenerator gen = Generators.timeBasedGenerator(EthernetAddress.fromInterface());
        UUID uuid = gen.generate();
        return uuid.toString();
    }
    
    public static boolean makeDirectory(String filepath){
        File file = new File(filepath);
        return file.mkdir();
    }
    
    public static boolean validFilepath(String filepath){
        File file = new File(filepath);
        return file.exists();
    }
    
    public static boolean isDirectory(String filepath){
        File file = new File(filepath);
        return file.isDirectory();
    }
    
    /**
     * Function ************************************************************
     * <br>
     * Synopsis []
     * <br>
     * Description []
     * <br>
     * SideEffects []
     * <br>
     * SeeAlso []
     * *********************************************************************
     */
//    public static void initializeFilepath(NetSynth netsynth) {
//        netsynth.Filepath = NetSynth.class.getClassLoader().getResource(".").getPath();
//        if (netsynth.Filepath.contains("/target/")) {
//            netsynth.Filepath = netsynth.Filepath.substring(0, netsynth.Filepath.lastIndexOf("/target/"));
//        } else if (netsynth.Filepath.contains("/src/")) {
//            netsynth.Filepath = netsynth.Filepath.substring(0, netsynth.Filepath.lastIndexOf("/src/"));
//        } else if (netsynth.Filepath.contains("/build/classes/")) {
//            netsynth.Filepath = netsynth.Filepath.substring(0, netsynth.Filepath.lastIndexOf("/build/classes/"));
//        }
//        if (Utilities.isWindows()) {
//            try {
//                netsynth.Filepath = URLDecoder.decode(netsynth.Filepath, "utf-8");
//                netsynth.Filepath = new File(netsynth.Filepath).getPath();
//                if (netsynth.Filepath.contains("\\target\\")) {
//                    netsynth.Filepath = netsynth.Filepath.substring(0, netsynth.Filepath.lastIndexOf("\\target\\"));
//                } else if (netsynth.Filepath.contains("\\src\\")) {
//                    netsynth.Filepath = netsynth.Filepath.substring(0, netsynth.Filepath.lastIndexOf("\\src\\"));
//                } else if (netsynth.Filepath.contains("\\build\\classes\\")) {
//                    netsynth.Filepath = netsynth.Filepath.substring(0, netsynth.Filepath.lastIndexOf("\\build\\classes\\"));
//                }
//            } catch (UnsupportedEncodingException ex) {
//                Logger.getLogger(NetSynth.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        } else {
//            if (netsynth.Filepath.contains("/target/")) {
//                netsynth.Filepath = netsynth.Filepath.substring(0, netsynth.Filepath.lastIndexOf("/target/"));
//            } else if (netsynth.Filepath.contains("/src/")) {
//                netsynth.Filepath = netsynth.Filepath.substring(0, netsynth.Filepath.lastIndexOf("/src/"));
//            } else if (netsynth.Filepath.contains("/build/classes/")) {
//                netsynth.Filepath = netsynth.Filepath.substring(0, netsynth.Filepath.lastIndexOf("/build/classes/"));
//            }
//        }
//    }
    
    
    
    public static String getFilepath() {
        String _filepath = NetSynth.class.getClassLoader().getResource(".").getPath();
        if (_filepath.contains("/target/")) {
            _filepath = _filepath.substring(0, _filepath.lastIndexOf("/target/"));
        } else if (_filepath.contains("/src/")) {
            _filepath = _filepath.substring(0, _filepath.lastIndexOf("/src/"));
        } else if (_filepath.contains("/build/classes/")) {
            _filepath = _filepath.substring(0, _filepath.lastIndexOf("/build/classes/"));
        }
        if (Utilities.isWindows()) {
            try {
                _filepath = URLDecoder.decode(_filepath, "utf-8");
                _filepath = new File(_filepath).getPath();
                if (_filepath.contains("\\target\\")) {
                    _filepath = _filepath.substring(0, _filepath.lastIndexOf("\\target\\"));
                } else if (_filepath.contains("\\src\\")) {
                    _filepath = _filepath.substring(0, _filepath.lastIndexOf("\\src\\"));
                } else if (_filepath.contains("\\build\\classes\\")) {
                    _filepath = _filepath.substring(0, _filepath.lastIndexOf("\\build\\classes\\"));
                }
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(NetSynth.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            if (_filepath.contains("/target/")) {
                _filepath = _filepath.substring(0, _filepath.lastIndexOf("/target/"));
            } else if (_filepath.contains("/src/")) {
                _filepath = _filepath.substring(0, _filepath.lastIndexOf("/src/"));
            } else if (_filepath.contains("/build/classes/")) {
                _filepath = _filepath.substring(0, _filepath.lastIndexOf("/build/classes/"));
            }
        }
       
        return _filepath;
    }
    
    public static String getNetSynthResourcesFilepath() {
        String _filepath = getFilepath();
        if (Utilities.isWindows()) {
            _filepath += "\\resources\\netsynthResources\\";
        } else {
            _filepath += "/resources/netsynthResources/";
        }
        return _filepath;
    }
    
    public static String getResourcesFilepath() {
        String _filepath = getFilepath();
        if (Utilities.isWindows()) {
            _filepath += "\\resources\\";
        } else {
            _filepath += "/resources/";
        }
        return _filepath;
    }
    
    public static String getFileContentAsString(String filepath){
        String filecontent = "";
        
        File file = new File(filepath);
        try { 
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line= "";
            while((line=reader.readLine()) != null){
                filecontent += (line+"\n");
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Utilities.class.getName()).log(Level.SEVERE, null, ex);
            Logger.getLogger(Utilities.class.getName()).log(Level.SEVERE, null, "File at " + filepath + " not found.");
        } catch (IOException ex) {
            Logger.getLogger(Utilities.class.getName()).log(Level.SEVERE, null, ex);
        }
        return filecontent;
    }
    
    public static List<String> getFileContentAsStringList(String filepath){
        List<String> filecontent = null;
        
        File file = new File(filepath);
        try { 
            BufferedReader reader = new BufferedReader(new FileReader(file));
            filecontent = new ArrayList<String>();
            String line= "";
            while((line=reader.readLine()) != null){
                filecontent.add(line);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Utilities.class.getName()).log(Level.SEVERE, null, ex);
            Logger.getLogger(Utilities.class.getName()).log(Level.SEVERE, null, "File at " + filepath + " not found.");
        } catch (IOException ex) {
            Logger.getLogger(Utilities.class.getName()).log(Level.SEVERE, null, ex);
        }
        return filecontent;
    }
    
}
