/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cellocad.Test;

import org.cellocad.BU.precomputation.equationSolver.eqNode;
import org.cellocad.BU.precomputation.equationSolver.eqNodeType;
import org.cellocad.BU.precomputation.equationSolver.eqParser;

/**
 *
 * @author prash
 */
public class TestEqSolver {
    
    
    public void printASTtest()
    {
        eqNode root = new eqNode();
        root.type = eqNodeType.equation;
        root.stage = 0;
        root.value = "f=a';";
        eqNode ch1 = new eqNode();
        eqNode ch2 = new eqNode();
        eqNode ch3 = new eqNode();
        eqNode ch4 = new eqNode();
        ch1.stage = root.stage +1;
        ch2.stage = root.stage +1;
        ch3.stage = root.stage +1;
        ch4.stage = root.stage +1;
        
        ch1.type = eqNodeType.term;
        ch2.type = eqNodeType.equals;
        ch3.type = eqNodeType.equation;
        ch4.type = eqNodeType.eol;
        
        ch1.value = "f";
        ch2.value = "=";
        ch3.value = "a'";
        ch4.value = ";";
        
        ch1.sibling = ch2;
        ch2.sibling = ch3;
        ch3.sibling = ch4;
        
        root.children.add(ch1);
        root.children.add(ch2);
        root.children.add(ch3);
        root.children.add(ch4);
        
       
        
        eqNode l1 = new eqNode();
        eqNode l2 = new eqNode();
        
        l1.stage = ch3.stage+1;
        l2.stage = ch3.stage+1;
        
        l1.type = eqNodeType.term;
        l2.type = eqNodeType.not;
        
        l1.value = "a";
        l2.value = "'";
        
        l1.sibling = l2;
        
        ch3.children.add(l1);
        ch3.children.add(l2);
        
        
        System.out.println(root.printAST());
        
    }
    
    public void eqParserTest(String eqn)
    {
        eqNode root = new eqNode(eqNodeType.root,eqn,0);
        eqParser.generateChildNodeList(root);
        System.out.println(root.printAST());
        
        
        System.out.println(eqParser.getAllTerms(root));
        
        
    }
    
}
