/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cellocad.BU.precomputation.equationSolver;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author prash
 */
public class eqParser {
    
    public static void generateChildNodeList(eqNode node)
    {
        
        if(node.type.equals(eqNodeType.root))
        {
            if (node.value.contains("'") || node.value.contains("!")) {
            //Case 2.1: Ends with a NOT. So does not have any 
            node.value = node.value.replaceAll("!", "'");
            }
            String eqn = node.value;
            String hs[] = eqn.split("=");
            eqNode lhs = new eqNode(node,eqNodeType.output,hs[0].trim());
            eqNode eqSign = new eqNode(node,eqNodeType.equals,"=");
            hs[1] = hs[1].trim();
            hs[1] = hs[1].substring(0,hs[1].indexOf(";"));
            hs[1] = hs[1].trim();
            while((hs[1].startsWith("(")&&hs[1].endsWith(")")))
            {
                hs[1] = hs[1].substring(1,hs[1].lastIndexOf(")"));
                hs[1] = hs[1].trim();
            }
            eqNode rhs = new eqNode(node,eqNodeType.equation,hs[1].trim());
            eqNode eol = new eqNode(node,eqNodeType.eol,";");
            
            lhs.sibling = eqSign;
            eqSign.sibling = rhs;
            rhs.sibling = eol;
            
            node.children.add(lhs);
            node.children.add(eqSign);
            node.children.add(rhs);
            node.children.add(eol);
            
            generateChildNodeList(rhs);
            
        }
        else if(node.type.equals(eqNodeType.equation))
        {
            String eqn = node.value;
            eqn = eqn.trim();
            if(eqn.contains("("))
            {
                //Case 1: Equation has a parenthesis
                //Isolate paranthesis with Not?
                if(eqn.startsWith("("))
                {
                    //Case 1.1: Equation starts with a parenthesis
                    
                    
                }
                else
                {
                    //Case 1.2: Equation does not start with a parenthesis
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
                        eqNode not = new eqNode(node, eqNodeType.not, "'");
                        if (eqn.contains("'")) 
                        {
                            //Case 2.1.1.1: Multiple Not terms
                            eqNode childeqn = new eqNode(node, eqNodeType.equation, eqn);
                            childeqn.sibling = not;
                            node.children.add(childeqn);
                            node.children.add(not);
                            generateChildNodeList(childeqn);
                        } 
                        else 
                        {
                            //Case 2.1.1.2: Single Not term
                            eqNode childeqn = new eqNode(node, eqNodeType.term, eqn);
                            childeqn.sibling = not;
                            node.children.add(childeqn);
                            node.children.add(not);
                        }
                    }
                    else
                    {
                        //Case 2.1.2: Eq is a term.
                        eqNode childeqn = new eqNode(node, eqNodeType.term, eqn);
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
                                prodNode = new eqNode(node,eqNodeType.equation,prod);
                                generateChildNodeList(prodNode);
                            }
                            else
                            {
                                prodNode = new eqNode(node,eqNodeType.term,prod);
                            }
                            prods.add(prodNode);
                            eqNode orNode = new eqNode(node,eqNodeType.or,"+");
                            prods.add(orNode);
                        }
                        // Last product term
                        eqNode prodNode;
                        if(eqn.contains("'")||eqn.contains("."))
                        {
                            //System.out.println(eqn);
                            prodNode = new eqNode(node,eqNodeType.equation,eqn);
                            generateChildNodeList(prodNode);
                        }
                        else
                        {
                            prodNode = new eqNode(node,eqNodeType.term,eqn);
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
                                termNode = new eqNode(node,eqNodeType.equation,term);
                                generateChildNodeList(termNode);
                            }
                            else
                            {
                                termNode = new eqNode(node,eqNodeType.term,term);
                            }
                            terms.add(termNode);
                            eqNode andNode = new eqNode(node,eqNodeType.and,".");
                            terms.add(andNode);
                        }
                        //Last product term
                        eqNode termNode;
                        if(eqn.contains("'"))
                        {
                            //System.out.println(eqn);
                            termNode = new eqNode(node,eqNodeType.equation,eqn);
                            generateChildNodeList(termNode);
                        }
                        else
                        {
                            termNode = new eqNode(node,eqNodeType.term,eqn);
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
}
