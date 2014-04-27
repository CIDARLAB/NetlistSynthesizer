/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Test;

import BU.ParseVerilog.parseCaseStatements;
import BU.netsynth.DGate.DGateType;
import BU.netsynth.NetSynth;

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
        TestSynthesis.testprintNetsynth();
    }
}
