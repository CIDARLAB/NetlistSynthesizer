/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cellocad.BU.netsynth;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author prash
 */
public class Utilities {

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
        NetSynth.Filepath = _filepath;
        return _filepath;
    }

    public static String getResourcesFilepath() {
        String _filepath = getFilepath();
        if (Utilities.isWindows()) {
            _filepath += "\\resources\\netsynthResources\\";
        } else {
            _filepath += "/resources/netsynthResources/";
        }
        return _filepath;
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
    public static void initializeFilepath() {
        NetSynth.Filepath = NetSynth.class.getClassLoader().getResource(".").getPath();
        if (NetSynth.Filepath.contains("/target/")) {
            NetSynth.Filepath = NetSynth.Filepath.substring(0, NetSynth.Filepath.lastIndexOf("/target/"));
        } else if (NetSynth.Filepath.contains("/src/")) {
            NetSynth.Filepath = NetSynth.Filepath.substring(0, NetSynth.Filepath.lastIndexOf("/src/"));
        } else if (NetSynth.Filepath.contains("/build/classes/")) {
            NetSynth.Filepath = NetSynth.Filepath.substring(0, NetSynth.Filepath.lastIndexOf("/build/classes/"));
        }
        if (Utilities.isWindows()) {
            try {
                NetSynth.Filepath = URLDecoder.decode(NetSynth.Filepath, "utf-8");
                NetSynth.Filepath = new File(NetSynth.Filepath).getPath();
                if (NetSynth.Filepath.contains("\\target\\")) {
                    NetSynth.Filepath = NetSynth.Filepath.substring(0, NetSynth.Filepath.lastIndexOf("\\target\\"));
                } else if (NetSynth.Filepath.contains("\\src\\")) {
                    NetSynth.Filepath = NetSynth.Filepath.substring(0, NetSynth.Filepath.lastIndexOf("\\src\\"));
                } else if (NetSynth.Filepath.contains("\\build\\classes\\")) {
                    NetSynth.Filepath = NetSynth.Filepath.substring(0, NetSynth.Filepath.lastIndexOf("\\build\\classes\\"));
                }
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(NetSynth.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            if (NetSynth.Filepath.contains("/target/")) {
                NetSynth.Filepath = NetSynth.Filepath.substring(0, NetSynth.Filepath.lastIndexOf("/target/"));
            } else if (NetSynth.Filepath.contains("/src/")) {
                NetSynth.Filepath = NetSynth.Filepath.substring(0, NetSynth.Filepath.lastIndexOf("/src/"));
            } else if (NetSynth.Filepath.contains("/build/classes/")) {
                NetSynth.Filepath = NetSynth.Filepath.substring(0, NetSynth.Filepath.lastIndexOf("/build/classes/"));
            }
        }
    }
    
}
