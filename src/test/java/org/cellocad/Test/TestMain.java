/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cellocad.Test;


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
            
            //TestSynthesis.testconvertFindORbeforeAND();
            //TestSynthesis.testconvertToNOR3();
            //TestSynthesis.testVerilogrunNetSynth();
            //TestSynthesis.testSpecInputVerilog(3,158);
            //TestSynthesis.testVerilogrunNetSynth();
            //TestSynthesis.testAssignLogic();
        
            //TestSynthesis.testEqualLogic();
            //TestSynthesis.testconvertORbeforeAND();
            //TestSynthesis.testOR3out();
            
            //NetSynthSwitches[] switches = {NetSynthSwitches.abc,NetSynthSwitches.nooutputOR,NetSynthSwitches.precompute};
            //TestSynthesis.testMain("filepath\\filefolder", NetSynthModes.cello, switches);
            TestEqSolver eq = new TestEqSolver();
            //eq.printASTtest();
            
            //eq.eqParserTest("f=(((d+(b'.c))+a));");
            //eq.eqParserTest("f=a!+b;");
            
            //eq.eqParserTest("f=(a'+b)''';");
            
            eq.eqParserTest("f=(e.a'+b)'''+a+b+(c+d);");
            
            
            //TestSynthesis.testANDConversion();
            //TestSynthesis.testVerilogrunNetSynth();
            //TestSynthesis.test3norconversion();
            //TestSynthesis.testSpecInputVerilog(3,25);
            //TestSynthesis.testAllnInputVerilog(3);
        
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
