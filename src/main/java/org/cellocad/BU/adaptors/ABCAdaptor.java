/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cellocad.BU.adaptors;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.cellocad.BU.dom.DGate;
import org.cellocad.BU.dom.DGateType;
import org.cellocad.BU.dom.DWire;
import org.cellocad.BU.dom.DWireType;
import org.cellocad.BU.netsynth.Global;
import org.cellocad.BU.netsynth.NetSynth;
import org.cellocad.BU.netsynth.Utilities;

/**
 *
 * @author prash
 */
public class ABCAdaptor {

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
     *
     * @param netlist
     * @return
     * *********************************************************************
     */
    public static List<DGate> convertAIGtoNORNOT(List<DGate> netlist) {
        List<DGate> netout = new ArrayList<DGate>();
        List<DGate> notcreated = new ArrayList<DGate>();
        for (int i = 0; i < netlist.size(); i++) {
            if (netlist.get(i).gtype.equals(DGateType.NOT)) {
                netout.add(netlist.get(i));
            }
            if (netlist.get(i).gtype.equals(DGateType.BUF)) {
                netout.add(netlist.get(i));
            }
            if (netlist.get(i).gtype.equals(DGateType.AND)) {
                int flag1 = 0;
                int flag2 = 0;
                DGate newnor = new DGate();
                newnor.gtype = DGateType.NOR;
                newnor.output = netlist.get(i).output;
                for (int j = 0; j < notcreated.size(); j++) {
                    if (netlist.get(i).input.get(0).name.equals(notcreated.get(j).input.get(0).name)) {
                        flag1 = 1;
                        newnor.input.add(notcreated.get(j).output);
                    }
                    if (netlist.get(i).input.get(1).name.equals(notcreated.get(j).input.get(0).name)) {
                        flag2 = 1;
                        newnor.input.add(notcreated.get(j).output);
                    }
                }
                if (flag1 == 0) {
                    String wirename = "0Wire" + Global.wirecount++;
                    DWire notout1 = new DWire(wirename, DWireType.connector);
                    DGate newnot1 = new DGate();
                    newnot1.gtype = DGateType.NOT;
                    newnot1.input.add(netlist.get(i).input.get(0));
                    newnot1.output = notout1;
                    newnor.input.add(newnot1.output);
                    netout.add(newnot1);
                    notcreated.add(newnot1);
                }
                if (flag2 == 0) {
                    String wirename = "0Wire" + Global.wirecount++;
                    DWire notout2 = new DWire(wirename, DWireType.connector);
                    DGate newnot2 = new DGate();
                    newnot2.gtype = DGateType.NOT;
                    newnot2.input.add(netlist.get(i).input.get(1));
                    newnot2.output = notout2;
                    newnor.input.add(newnot2.output);
                    netout.add(newnot2);
                    notcreated.add(newnot2);
                }
                netout.add(newnor);
            }
        }
        return netout;
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
     *
     * @param filename
     * @return
     * @throws java.lang.InterruptedException
     * *********************************************************************
     */
    public static List<DGate> runABC(String filename) throws InterruptedException {
        Utilities.initializeFilepath();
        String x = System.getProperty("os.name");
        StringBuilder commandBuilder = null;
        if (Utilities.isMac(x)) {
            commandBuilder = new StringBuilder(NetSynth.Filepath + "/resources/netsynthResources/abc.mac -c \"read " + NetSynth.Filepath + "/resources/netsynthResources/" + filename + ".blif; strash;  rewrite; refactor; balance; write " + NetSynth.Filepath + "/resources/netsynthResources/abcOutput.bench; quit\"");
        } else if (Utilities.isLinux(x)) {
            commandBuilder = new StringBuilder(NetSynth.Filepath + "/resources/netsynthResources/abc -c \"read " + NetSynth.Filepath + "/resources/netsynthResources/" + filename + ".blif; strash;  rewrite; refactor; balance; write " + NetSynth.Filepath + "/resources/netsynthResources/abcOutput.bench; quit\"");
        } else if (Utilities.isWindows(x)) {
            commandBuilder = new StringBuilder(NetSynth.Filepath + "\\resources\\netsynthResources\\abc -c \"read " + NetSynth.Filepath + "\\resources\\netsynthResources\\" + filename + ".blif; strash;  rewrite; refactor; balance; write " + NetSynth.Filepath + "\\resources\\netsynthResources\\abcOutput.bench; quit\"");
        }
        String command = commandBuilder.toString();
        String filestring = "";
        String clist = "";
        if (Utilities.isWindows(x)) {
            filestring += NetSynth.Filepath + "\\resources\\netsynthResources\\script.cmd";
            clist = NetSynth.Filepath + "\\resources\\netsynthResources\\script.cmd";
        } else {
            filestring += NetSynth.Filepath + "/resources/netsynthResources/script";
            clist = NetSynth.Filepath + "/resources/netsynthResources/script";
        }
        File fespinp = new File(filestring);
        try {
            Writer output = new BufferedWriter(new FileWriter(fespinp));
            output.write(command);
            output.close();
        } catch (IOException ex) {
            Logger.getLogger(NetSynth.class.getName()).log(Level.SEVERE, null, ex);
        }
        Runtime runtime = Runtime.getRuntime();
        Process proc = null;
        List<DGate> finalnetlist = new ArrayList<DGate>();
        try {
            proc = runtime.exec(clist);
            proc.waitFor();
            finalnetlist = convertBenchToAIG();
        } catch (IOException ex) {
            Logger.getLogger(NetSynth.class.getName()).log(Level.SEVERE, null, ex);
        }
        return finalnetlist;
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
     *
     * @return
     * *********************************************************************
     */
    public static List<DGate> convertBenchToAIG() {
        Utilities.initializeFilepath();
        List<String> benchlines = new ArrayList<String>();
        String filestring = "";
        filestring += NetSynth.Filepath + "/resources/netsynthResources/abcOutput.bench";
        benchlines = Utilities.getFileContentAsStringList(filestring);
        
        List<DGate> netlist = new ArrayList<DGate>();
        List<DWire> inputwires = new ArrayList<DWire>();
        List<DWire> outputwires = new ArrayList<DWire>();
        List<DWire> allwires = new ArrayList<DWire>();
        for (int i = 0; i < benchlines.size(); i++) {
            if (benchlines.get(i).contains("INPUT")) {
                String inpname = benchlines.get(i).substring(benchlines.get(i).indexOf("INPUT(") + 6);
                inpname = inpname.substring(0, inpname.indexOf(")"));
                inpname = inpname.trim();
                DWire inpw = new DWire(inpname, DWireType.input);
                inputwires.add(inpw);
                allwires.add(inpw);
            }
            if (benchlines.get(i).contains("OUTPUT")) {
                String outpname = benchlines.get(i).substring(benchlines.get(i).indexOf("OUTPUT(") + 7);
                outpname = outpname.substring(0, outpname.indexOf(")"));
                outpname = outpname.trim();
                DWire outpw = new DWire(outpname, DWireType.output);
                outputwires.add(outpw);
                allwires.add(outpw);
            }
            if (benchlines.get(i).contains("=")) {
                String outpwire = benchlines.get(i).substring(0, benchlines.get(i).indexOf("="));
                outpwire = outpwire.trim();
                int flag = 0;
                for (DWire xwire : allwires) {
                    if (xwire.name.equals(outpwire)) {
                        flag = 1;
                        break;
                    }
                }
                if (flag == 0) {
                    DWire connwire = new DWire(outpwire, DWireType.connector);
                    allwires.add(connwire);
                }
                String gatestring = benchlines.get(i).substring(benchlines.get(i).indexOf("=") + 1);
                gatestring = gatestring.trim();
                if (gatestring.equals("vdd")) {
                    DGate xgate = new DGate();
                    xgate.gtype = DGateType.BUF;
                    xgate.input.add(NetSynth.one);
                    for (DWire xwire : allwires) {
                        if (xwire.name.equals(outpwire)) {
                            xgate.output = xwire;
                        }
                    }
                    netlist.add(xgate);
                }
                if (gatestring.contains("BUFF")) {
                    DGate xgate = new DGate();
                    xgate.gtype = DGateType.BUF;
                    String bufconn = gatestring.substring(gatestring.indexOf("(") + 1, gatestring.indexOf(")"));
                    for (DWire xwire : allwires) {
                        if (xwire.name.equals(bufconn)) {
                            xgate.input.add(xwire);
                        }
                        if (xwire.name.equals(outpwire)) {
                            xgate.output = xwire;
                        }
                    }
                    netlist.add(xgate);
                }
                if (gatestring.contains("NOT")) {
                    DGate xgate = new DGate();
                    xgate.gtype = DGateType.NOT;
                    String notconn = gatestring.substring(gatestring.indexOf("(") + 1, gatestring.indexOf(")"));
                    notconn = notconn.trim();
                    for (DWire xwire : allwires) {
                        if (xwire.name.equals(notconn)) {
                            xgate.input.add(xwire);
                        }
                        if (xwire.name.equals(outpwire)) {
                            xgate.output = xwire;
                        }
                    }
                    netlist.add(xgate);
                }
                if (gatestring.contains("AND")) {
                    DGate xgate = new DGate();
                    xgate.gtype = DGateType.AND;
                    String conn = gatestring.substring(gatestring.indexOf("(") + 1, gatestring.indexOf(")"));
                    conn = conn.trim();
                    String[] connpieces = conn.split(",");
                    String conn1 = connpieces[0].trim();
                    String conn2 = connpieces[1].trim();
                    conn1 = conn1.trim();
                    conn2 = conn2.trim();
                    for (DWire xwire : allwires) {
                        if (xwire.name.equals(conn1)) {
                            xgate.input.add(xwire);
                        }
                        if (xwire.name.equals(conn2)) {
                            xgate.input.add(xwire);
                        }
                        if (xwire.name.equals(outpwire)) {
                            xgate.output = xwire;
                        }
                    }
                    netlist.add(xgate);
                }
            }
        }
        List<DGate> netout = new ArrayList<DGate>();
        netout = convertAIGtoNORNOT(netlist);
        
        File benchFile = new File(filestring);
        benchFile.deleteOnExit();
        
        return netout;
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
     *
     * @param filename
     * @return
     * @throws java.lang.InterruptedException
     * *********************************************************************
     */
    public static List<DGate> runABCverilog_fullFilePath(String filename) throws InterruptedException {
        Utilities.initializeFilepath();
        String x = System.getProperty("os.name");
        StringBuilder commandBuilder = null;
        if (Utilities.isMac(x)) {
            commandBuilder = new StringBuilder(NetSynth.Filepath + "/resources/netsynthResources/abc.mac -c \"read " + filename + "; strash;  rewrite; refactor; balance; write " + NetSynth.Filepath + "/resources/netsynthResources/abcOutput.bench; quit\"");
        } else if (Utilities.isLinux(x)) {
            commandBuilder = new StringBuilder(NetSynth.Filepath + "/resources/netsynthResources/abc -c \"read " + filename + "; strash;  rewrite; refactor; balance; write " + NetSynth.Filepath + "/resources/netsynthResources/abcOutput.bench; quit\"");
        } else if (Utilities.isWindows(x)) {
            commandBuilder = new StringBuilder(NetSynth.Filepath + "\\resources\\netsynthResources\\abc -c \"read " + filename + "; strash;  rewrite; refactor; balance; write " + NetSynth.Filepath + "\\resources\\netsynthResources\\abcOutput.bench; quit\"");
        }
        String command = commandBuilder.toString();
        String filestring = "";
        if (Utilities.isWindows(x)) {
            filestring += NetSynth.Filepath + "\\resources\\netsynthResources\\script.cmd";
        } else {
            filestring += NetSynth.Filepath + "/resources/netsynthResources/script";
        }
        File fespinp = new File(filestring);
        try {
            Writer output = new BufferedWriter(new FileWriter(fespinp));
            output.write(command);
            output.close();
        } catch (IOException ex) {
            Logger.getLogger(NetSynth.class.getName()).log(Level.SEVERE, null, ex);
        }
        String clist = "";
        if (Utilities.isWindows(x)) {
            clist = NetSynth.Filepath + "\\resources\\netsynthResources\\script.cmd";
        } else {
            clist = NetSynth.Filepath + "/resources/netsynthResources/script";
        }
        Runtime runtime = Runtime.getRuntime();
        Process proc = null;
        List<DGate> finalnetlist = new ArrayList<DGate>();
        try {
            proc = runtime.exec(clist);
            proc.waitFor();
            finalnetlist = convertBenchToAIG();
        } catch (IOException ex) {
            Logger.getLogger(NetSynth.class.getName()).log(Level.SEVERE, null, ex);
        }
        return finalnetlist;
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
     *
     * @param filename
     * @return
     * @throws java.lang.InterruptedException
     * *********************************************************************
     */
    public static List<DGate> runABCverilog(String filename) throws InterruptedException {
        Utilities.initializeFilepath();
        String x = System.getProperty("os.name");
        StringBuilder commandBuilder = null;
        if (Utilities.isMac(x)) {
            commandBuilder = new StringBuilder(NetSynth.Filepath + "/resources/netsynthResources/abc.mac -c \"read " + NetSynth.Filepath + "/resources/netsynthResources/" + filename + ".v; strash;  rewrite; refactor; balance; write " + NetSynth.Filepath + "/resources/netsynthResources/abcOutput.bench; quit\"");
        } else if (Utilities.isLinux(x)) {
            commandBuilder = new StringBuilder(NetSynth.Filepath + "/resources/netsynthResources/abc -c \"read " + NetSynth.Filepath + "/resources/netsynthResources/" + filename + ".v; strash;  rewrite; refactor; balance; write " + NetSynth.Filepath + "/resources/netsynthResources/abcOutput.bench; quit\"");
        } else if (Utilities.isWindows(x)) {
            commandBuilder = new StringBuilder(NetSynth.Filepath + "\\resources\\netsynthResources\\abc.exe -c \"read " + NetSynth.Filepath + "\\resources\\netsynthResources\\" + filename + ".v; strash;  rewrite; refactor; balance; write " + NetSynth.Filepath + "\\resources\\netsynthResources\\abcOutput.bench; quit\"");
        }
        String command = commandBuilder.toString();
        String filestring = "";
        if (Utilities.isWindows(x)) {
            filestring += NetSynth.Filepath + "\\resources\\netsynthResources\\script.cmd";
        } else {
            filestring += NetSynth.Filepath + "/resources/netsynthResources/script";
        }
        File fespinp = new File(filestring);
        try {
            Writer output = new BufferedWriter(new FileWriter(fespinp));
            output.write(command);
            output.close();
        } catch (IOException ex) {
            Logger.getLogger(NetSynth.class.getName()).log(Level.SEVERE, null, ex);
        }
        String clist = "";
        if (Utilities.isWindows(x)) {
            clist = NetSynth.Filepath + "\\resources\\netsynthResources\\script.cmd";
        } else {
            clist = NetSynth.Filepath + "/resources/netsynthResources/script";
        }
        Runtime runtime = Runtime.getRuntime();
        Process proc = null;
        List<DGate> finalnetlist = new ArrayList<DGate>();
        try {
            proc = runtime.exec(clist);
            proc.waitFor();
            finalnetlist = convertBenchToAIG();
        } catch (IOException ex) {
            Logger.getLogger(NetSynth.class.getName()).log(Level.SEVERE, null, ex);
        }
        return finalnetlist;
    }
    
}
