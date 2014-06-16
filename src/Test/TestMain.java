/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Test;

import BU.ParseVerilog.parseVerilogFile;
import BU.WebSockets.ClothoSocket;
import BU.netsynth.DGate.DGateType;
import BU.netsynth.NetSynth;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author prashantvaidyanathan
 */
public class TestMain {
    public static void main(String[] args)
    {
        
            //TestSynthesis.testNORConversion(DGateType.XNOR);
            //TestSynthesis.testReducedFanin(DGateType.OR,3);
            //parseCaseStatements.parseStructural("");
            // NetSynth.parseStructuralVtoNORNOT("");
            //TestSynthesis.testprintNetsynth();
            //TestSynthesis.testGetTT();
            //TestSynthesis.testespressotoabc();
            //TestSynthesis.testParseVerilogFileFunctions();
            TestSynthesis.testrunNetSynth();
        
        
        /*try {
            ClothoSocket.connect();
        } catch (DeploymentException ex) {
            Logger.getLogger(TestMain.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TestMain.class.getName()).log(Level.SEVERE, null, ex);
        }
        */
    }
}
