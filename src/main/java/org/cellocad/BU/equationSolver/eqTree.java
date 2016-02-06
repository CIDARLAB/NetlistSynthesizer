/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cellocad.BU.equationSolver;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author prash
 */
public class eqTree {
    public static void generateChildNodeList(eqNode node)
    {
        
        if(node.type.equals(eqNode.eqNodeType.root))
        {
            //<editor-fold desc="Node is of type root" defaultstate="collapsed">
            if (node.value.contains("'") || node.value.contains("!")) {
            //Case 2.1: Ends with a NOT. So does not have any 
            node.value = node.value.replaceAll("!", "'");
            }
            String eqn = node.value;
            String hs[] = eqn.split("=");
            eqNode lhs = new eqNode(node,eqNode.eqNodeType.output,hs[0].trim());
            eqNode eqSign = new eqNode(node,eqNode.eqNodeType.equals,"=");
            hs[1] = hs[1].trim();
            hs[1] = hs[1].substring(0,hs[1].indexOf(";"));
            hs[1] = hs[1].trim();
            
            int bracketCount = 1;
            while(((hs[1].startsWith("(")&&hs[1].endsWith(")")))&&(bracketCount == 1))
            {
                int stack = 1;
                int indx = 1;
                while (stack > 0) {
                    if (hs[1].charAt(indx) == '(') {
                        stack++;
                    } else if (hs[1].charAt(indx) == ')') {
                        stack--;
                    }
                    indx++;
                    if (indx == hs[1].length()) {
                        break;
                    }
                }
                if(indx == hs[1].length())
                {
                    bracketCount =1;
                    hs[1] = hs[1].substring(1,hs[1].lastIndexOf(")"));
                    hs[1] = hs[1].trim();
                }
                else 
                    bracketCount++;
            }
            eqNode rhs = new eqNode(node,eqNode.eqNodeType.equation,hs[1].trim());
            eqNode eol = new eqNode(node,eqNode.eqNodeType.eol,";");
            
           
            
            node.children.add(lhs);
            node.children.add(eqSign);
            node.children.add(rhs);
            node.children.add(eol);
            
            generateChildNodeList(rhs);
            //</editor-fold>
        }
        else if(node.type.equals(eqNode.eqNodeType.equation))
        {
            String eqn = node.value;
            eqn = eqn.trim();
            if(eqn.contains("("))
            {
                
                List<String> pieces = new ArrayList<String>();
                while(eqn.contains("("))
                {
                    if(eqn.startsWith("("))
                    {
                        int stack = 1;
                        int indx = 1;
                        while (stack > 0) {
                            if (eqn.charAt(indx) == '(') {
                                stack++;
                            } else if (eqn.charAt(indx) == ')') {
                                stack--;
                            }
                            indx++;
                            if (indx == eqn.length()) {
                                break;
                            }
                        }
                        if (indx < eqn.length()) {
                            while (eqn.charAt(indx) == '\'') {
                                indx++;
                                if (indx == eqn.length()) {
                                    break;
                                }
                            }
                        }
                        String substring;
                        if(indx == eqn.length())
                        {
                            substring = eqn.trim();
                            pieces.add(substring);
                            eqn = "";
                        }
                        else 
                        {
                            substring = eqn.substring(0,indx);
                            substring = substring.trim();
                            pieces.add(substring);
                            eqn = eqn.substring(indx);
                            eqn = eqn.trim();
                        }
                        
                    }
                    else
                    {
                        String substring = eqn.substring(0,eqn.indexOf("("));
                        substring = substring.trim();
                        pieces.add(substring);
                        eqn = eqn.substring(eqn.indexOf("("));
                        eqn = eqn.trim();
                    }
                }
                if(!("".equals(eqn.trim())))
                    pieces.add(eqn.trim());
                
                if(pieces.size()==1)
                {
                    String notline = pieces.get(0).trim();
                    if(notline.startsWith("(") && notline.endsWith("'"))
                    {
                        
                        notline = notline.substring(0, notline.lastIndexOf("'"));
                        int bracketCount = 1;
                        while (((notline.startsWith("(") && notline.endsWith(")"))) && (bracketCount == 1)) {
                            int stack = 1;
                            int indx = 1;
                            while (stack > 0) {
                                if (notline.charAt(indx) == '(') {
                                    stack++;
                                } else if (notline.charAt(indx) == ')') {
                                    stack--;
                                }
                                indx++;
                                if (indx == notline.length()) {
                                    break;
                                }
                            }
                            if (indx == notline.length()) {
                                bracketCount = 1;
                                notline = notline.substring(1, notline.lastIndexOf(")"));
                                notline = notline.trim();
                            } else {
                                bracketCount++;
                            }
                        }
                        eqNode eqnNode = new eqNode(node, eqNode.eqNodeType.equation, notline);
                        eqNode notNode = new eqNode(node, eqNode.eqNodeType.not, "'");
                        node.children.add(eqnNode);
                        node.children.add(notNode);
                        generateChildNodeList(eqnNode);
                    }
                }
                else
                {
                    List<String> orSeparated = new ArrayList<String>();
                    for(String xpiece:pieces)
                    {
                        if(xpiece.startsWith("("))
                            orSeparated.add(xpiece.trim());
                        else
                        {
                            if(xpiece.contains("+"))
                            {
                                boolean lastOR = false;
                                if(xpiece.length()==1)
                                    orSeparated.add(xpiece.trim());
                                else
                                {
                                    if(xpiece.endsWith("+"))
                                    {
                                        lastOR = true;
                                        xpiece = xpiece.substring(0,xpiece.lastIndexOf("+"));
                                    }
                                    String orPieces[] = xpiece.split("\\+");
                                    for(int i=0;i<(orPieces.length-1);i++)
                                    {
                                        //System.out.println(i+":"+orPieces[i]);
                                        if(("".equals(orPieces[i].trim())))
                                            orSeparated.add("+");
                                        else
                                        {
                                            orSeparated.add(orPieces[i].trim());
                                            orSeparated.add("+");
                                        }
                                    }
                                    if (("".equals(orPieces[orPieces.length-1].trim()))) 
                                    {
                                    
                                    } 
                                    else 
                                    {
                                        orSeparated.add(orPieces[orPieces.length-1].trim());
                                    }
                                    if(lastOR)
                                        orSeparated.add("+");
                                }
                            }
                            else
                                orSeparated.add(xpiece.trim());
                        }
                    }
                    
                    pieces = new ArrayList<String>();
                    for(String xpiece:orSeparated)
                    {
                        if(xpiece.startsWith("("))
                            pieces.add(xpiece.trim());
                        else
                        {
                            if(xpiece.contains("."))
                            {
                                boolean lastAND = false;
                                if(xpiece.length()==1)
                                    pieces.add(xpiece.trim());
                                else
                                {
                                    if(xpiece.endsWith("."))
                                    {
                                        lastAND = true;
                                        xpiece = xpiece.substring(0, xpiece.lastIndexOf("."));
                                    }
                                    String orPieces[] = xpiece.split("\\.");
                                    //System.out.println("And split");
                                    for(int i=0;i<orPieces.length-1;i++)
                                    {
                                        
                                        if(("".equals(orPieces[i].trim())))
                                            pieces.add(".");
                                        else
                                        {
                                            pieces.add(orPieces[i].trim());
                                            pieces.add(".");
                                        }
                                        
                                    }
                                    if (("".equals(orPieces[orPieces.length-1].trim()))) 
                                    {
                                    
                                    } 
                                    else 
                                    {
                                        pieces.add(orPieces[orPieces.length-1].trim());
                                    }
                                    if(lastAND)
                                        pieces.add(".");
                                }
                            }
                            else
                                pieces.add(xpiece.trim());
                        }
                    }
                    for(String xstring:pieces)
                    {
                        String val = xstring.trim();
                        List<eqNode> siblingNodes = new ArrayList<eqNode>();
                        if(val.length()==1)
                        {
                            if(val.equals("+"))
                            {
                                eqNode orNode = new eqNode(node,eqNode.eqNodeType.or,"+");
                                siblingNodes.add(orNode);
                            }
                            else if(val.equals("."))
                            {
                                eqNode andNode = new eqNode(node,eqNode.eqNodeType.and,".");
                                siblingNodes.add(andNode);
                            }
                            else
                            {
                                eqNode termNode = new eqNode(node,eqNode.eqNodeType.term,val);
                                siblingNodes.add(termNode);
                            }
                        }
                        else
                        {
                            if(val.startsWith("("))
                            {
                                int bracketCount = 1;
                                while (((val.startsWith("(") && val.endsWith(")"))) && (bracketCount == 1)) {
                                    int stack = 1;
                                    int indx = 1;
                                    while (stack > 0) {
                                        if (val.charAt(indx) == '(') {
                                            stack++;
                                        } else if (val.charAt(indx) == ')') {
                                            stack--;
                                        }
                                        indx++;
                                        if (indx == val.length()) {
                                            break;
                                        }
                                    }
                                    if (indx == val.length()) {
                                        bracketCount = 1;
                                        val = val.substring(1, val.lastIndexOf(")"));
                                        val = val.trim();
                                    } else {
                                        bracketCount++;
                                    }
                                }
                                
                                eqNode eqnNode = new eqNode(node,eqNode.eqNodeType.equation,val);
                                generateChildNodeList(eqnNode);
                                siblingNodes.add(eqnNode);
                                
                            }
                            else 
                            {
                                eqNode termNode = new eqNode(node,eqNode.eqNodeType.term,val);
                                siblingNodes.add(termNode);
                            }
                        }
                        node.children.addAll(siblingNodes);
                        
                        /*if(siblingNodes.size() >1)
                        {
                            for (int i = 0; i < siblingNodes.size() - 1; i++) 
                            {
                                siblingNodes.get(i).sibling = siblingNodes.get(i+1);
                            }
                        }*/
                        
                    }
                    
                    
                }
                
                
               
            }
            else
            {
                //<editor-fold defaultstate="collapsed" desc="Equation has No parenthesis">
                //Case 2: Equation has no parenthesis
                if(!(eqn.contains("+")||eqn.contains(".")))
                {
                    //Case 2.1: Does not contain any OR or AND gates
                    if(eqn.endsWith("'")||eqn.endsWith("!"))
                    {
                        //Case 2.1.1: Possibly single or multiple NOT terms
                        eqn = eqn.substring(0, eqn.lastIndexOf("'"));
                        eqn = eqn.trim();
                        eqNode not = new eqNode(node, eqNode.eqNodeType.not, "'");
                        if (eqn.contains("'")) 
                        {
                            //Case 2.1.1.1: Multiple Not terms
                            eqNode childeqn = new eqNode(node, eqNode.eqNodeType.equation, eqn);
                            //childeqn.sibling = not;
                            node.children.add(childeqn);
                            node.children.add(not);
                            generateChildNodeList(childeqn);
                        } 
                        else 
                        {
                            //Case 2.1.1.2: Single Not term
                            eqNode childeqn = new eqNode(node, eqNode.eqNodeType.term, eqn);
                            //childeqn.sibling = not;
                            node.children.add(childeqn);
                            node.children.add(not);
                        }
                    }
                    else
                    {
                        //Case 2.1.2: Eq is a term.
                        eqNode childeqn = new eqNode(node, eqNode.eqNodeType.term, eqn);
                        node.children.add(childeqn);
                    }
                }
                else
                {
                    //Case 2.2: Contains OR or AND gates
                    if(eqn.contains("+"))
                    {
                        //Case 2.2.1: Can be expressed as a SOP
                        List<eqNode> prods = new ArrayList<eqNode>();
                        while(eqn.contains("+"))
                        {
                            //Generate all product Terms
                            String prod = eqn.substring(0,eqn.indexOf("+"));
                            eqn = eqn.substring(eqn.indexOf("+")+1);
                            prod = prod.trim();
                            eqn = eqn.trim();
                            eqNode prodNode;
                            if(prod.contains("'")||prod.contains("."))
                            {
                                prodNode = new eqNode(node,eqNode.eqNodeType.equation,prod);
                                generateChildNodeList(prodNode);
                            }
                            else
                            {
                                prodNode = new eqNode(node,eqNode.eqNodeType.term,prod);
                            }
                            prods.add(prodNode);
                            eqNode orNode = new eqNode(node,eqNode.eqNodeType.or,"+");
                            prods.add(orNode);
                        }
                        // Last product term
                        eqNode prodNode;
                        if(eqn.contains("'")||eqn.contains("."))
                        {
                            //System.out.println(eqn);
                            prodNode = new eqNode(node,eqNode.eqNodeType.equation,eqn);
                            generateChildNodeList(prodNode);
                        }
                        else
                        {
                            prodNode = new eqNode(node,eqNode.eqNodeType.term,eqn);
                        }
                        prods.add(prodNode);
                        for(eqNode xnode:prods)
                        {
                            node.children.add(xnode);
                        }
                    }
                    else 
                    {
                        //Case 2.2.2: Can be expressed as a MaxTerm
                        List<eqNode> terms = new ArrayList<eqNode>();
                        while(eqn.contains("."))
                        {
                            //Generate all product Terms
                            String term = eqn.substring(0,eqn.indexOf("."));
                            eqn = eqn.substring(eqn.indexOf(".")+1);
                            term = term.trim();
                            eqn = eqn.trim();
                            eqNode termNode;
                            if(term.contains("'"))
                            {
                                termNode = new eqNode(node,eqNode.eqNodeType.equation,term);
                                generateChildNodeList(termNode);
                            }
                            else
                            {
                                termNode = new eqNode(node,eqNode.eqNodeType.term,term);
                            }
                            terms.add(termNode);
                            eqNode andNode = new eqNode(node,eqNode.eqNodeType.and,".");
                            terms.add(andNode);
                        }
                        //Last product term
                        eqNode termNode;
                        if(eqn.contains("'"))
                        {
                            //System.out.println(eqn);
                            termNode = new eqNode(node,eqNode.eqNodeType.equation,eqn);
                            generateChildNodeList(termNode);
                        }
                        else
                        {
                            termNode = new eqNode(node,eqNode.eqNodeType.term,eqn);
                        }
                        terms.add(termNode);
                        for(eqNode xnode:terms)
                        {
                            node.children.add(xnode);
                        }
                    }
                }
                //</editor-fold>
            }
            
        }
    }
    
    public static void assignSiblingNodes(eqNode node)
    {
        //Assign Right nodes
        for(int i=0;i<node.children.size()-1;i++)
        {
            //System.out.println("Right sibling of " + node.children.get(i).value + " is :" +node.children.get(i+1).value);
            node.children.get(i).rightSibling = node.children.get(i+1);
        }
        //Assign Left nodes
        for(int i=node.children.size()-1;i>=1;i--)
        {
            node.children.get(i).leftSibling = node.children.get(i-1);
        }
        //Assign sibling nodes for all Child Nodes
        for(eqNode child:node.children)
        {
            assignSiblingNodes(child);
        }
    }
    
    public static eqNode createEqTree(String equation)
    {
        eqNode root = new eqNode(eqNode.eqNodeType.root,equation,0);
        generateChildNodeList(root);
        assignSiblingNodes(root);
        return root;
    }
    
    public static List<String> getAllTerms(eqNode node)
    {
        List<String> terms = new ArrayList<String>();
        if(node.type.equals(eqNode.eqNodeType.term))
        {
            List<String> term = new ArrayList<String>();
            term.add(node.value);
            return term;
        }
        else
        {
            for(eqNode child:node.children)
            {
                for(String term:getAllTerms(child))
                {
                    if(!terms.contains(term))
                    {
                        terms.add(term);
                    }
                            
                }
            }
        }
        return terms;
    }
    
}
