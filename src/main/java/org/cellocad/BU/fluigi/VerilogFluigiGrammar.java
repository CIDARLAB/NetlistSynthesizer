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

/**
 *
 * @author prash
 */
public class VerilogFluigiGrammar {
    public static ParseTree getParseTree(String line){
        ANTLRInputStream antlrStream = new ANTLRInputStream(line);
        VerilogFluigiLexer lexer = new VerilogFluigiLexer(antlrStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        VerilogFluigiParser parser = new VerilogFluigiParser(tokens);
        ParseTree tree = parser.root();
        
        VerilogFluigiWalker walker = new VerilogFluigiWalker();
        
        ParseTreeWalker.DEFAULT.walk(walker, tree);
        
        System.out.println("Module Name :: " + walker.details.modulename);
        System.out.println("Inputs :: " + walker.details.inputs);
        System.out.println("Outputs :: " + walker.details.outputs);
        System.out.println("Wires :: " + walker.details.wires);
        
        //System.out.println("TREE :: \n" + tree.toStringTree(parser));
        
        return tree;
    }
}
