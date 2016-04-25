/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cellocad.BU.fluigi;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.cellocad.BU.netsynth.NetSynth;

/**
 *
 * @author prash
 */
public class VerilogFluigiGrammar {
    
    public static VerilogFluigiWalker getuFWalker(String line){
        ANTLRInputStream antlrStream = new ANTLRInputStream(line);
        VerilogFluigiLexer lexer = new VerilogFluigiLexer(antlrStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        VerilogFluigiParser parser = new VerilogFluigiParser(tokens);
        ParseTree tree = parser.root();
        
        VerilogFluigiWalker walker = new VerilogFluigiWalker();
        
        ParseTreeWalker.DEFAULT.walk(walker, tree);
        
        
        
        return walker;
    }
}
