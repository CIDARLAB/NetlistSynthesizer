/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cellocad.BU.equationSolver;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.cellocad.BU.parseVerilog.Convert;
import org.cellocad.BU.equationSolver.eqNode.eqNodeBooleanVal;
import org.cellocad.BU.equationSolver.eqNode.eqNodeColor;
import org.cellocad.BU.equationSolver.eqNode.eqNodeType;

/**
 *
 * @author prash
 */
public class eqSolver {
    public static String solveEquation(eqNode root)
    {
        String booleanString = "";
        eqNode eqNode =  root.children.get(2);
        List<String> inputs = eqTree.getAllTerms(root);
        if(inputs.size() < 1 )
            return "";
        else
        {
            Map<String,eqNodeBooleanVal> termValues;
            int pow = (int)Math.pow(2, inputs.size());
            for(int i=0;i<pow;i++)
            {
                String bin = Convert.dectoBin(i, inputs.size());
                termValues = new HashMap<>();
                resetTree(root);
                for(int j=0;j<bin.length();j++)
                {
                    eqNodeBooleanVal val ;
                    if(bin.charAt(j)=='0')
                        val = eqNodeBooleanVal._0;
                    else 
                        val = eqNodeBooleanVal._1;
                    termValues.put(inputs.get(j),val);
                }
                eqNodeBooleanVal rowVal = getRowValue(eqNode,termValues);
                if(rowVal == null)
                {
                    System.out.println("Dont care?");
                }
                else 
                {
                    if(rowVal == eqNodeBooleanVal._0)
                        booleanString+= "0";
                    else if(rowVal == eqNodeBooleanVal._1)
                        booleanString+= "1";
                }
            }
        }
        
        return booleanString;
    }
    
    public static eqNodeBooleanVal getRowValue(eqNode node,Map<String,eqNodeBooleanVal> map)
    {
        //System.out.println();
        //System.out.println(map);
        eqNode curr = node;
        eqNodeBooleanVal rowVal = null;
        while(curr != null)
        {
            //System.out.println("Node Type : "+ curr.type + "Node Value "+ curr.value);
            if(curr.color == eqNodeColor.black)
            {
               if(curr.type == eqNodeType.root)
               {
                   rowVal = curr.boolVal;
                   //System.out.println(rowVal);
               }
               curr = curr.parent;
            }
            else if(curr.color == eqNodeColor.gray)
            {
                curr.color = eqNodeColor.black;
                
                if(curr.type == eqNodeType.root)
                {
                    curr = curr.parent;
                }
                
                else if(curr.parent.type.equals(eqNodeType.root))
                {
                    curr.parent.boolVal = curr.boolVal;
                    curr = curr.parent;
                    curr.color = eqNodeColor.black;
                }
                else
                {  
                if(curr.leftSibling == null)
                {
                    if(curr.rightSibling == null)
                    {
                        //System.out.println("Stage" + curr.stage +" : "+ curr.value + curr.type);
                        curr.parent.boolVal = curr.boolVal;
                        curr = curr.parent;
                    }
                    else 
                    {
                        curr = curr.rightSibling;
                    }
                }
                else
                {
                    eqNodeBooleanVal newVal = getBooleanOprVal(curr.leftSibling, curr.boolVal);
                    curr.boolVal = newVal;
                    if (curr.rightSibling == null) 
                    {
                        curr.parent.boolVal = curr.boolVal;
                        curr = curr.parent;
                    } 
                    else 
                    {
                        curr = curr.rightSibling;
                    }
                }
            }
            }
            else if(curr.color == eqNodeColor.white)
            {
                
                curr.color = eqNodeColor.gray;
                if(curr.type == eqNodeType.term)
                {
                    curr.color = eqNodeColor.black;
                    if(curr.leftSibling == null)
                    {
                        if(curr.rightSibling == null)
                        {
                            curr.parent.boolVal = map.get(curr.value);
                            curr = curr.parent;
                        }
                        else
                        {
                            curr = curr.rightSibling;
                        }
                    }
                    else
                    {
                        eqNodeBooleanVal newVal = getBooleanOprVal(curr.leftSibling,map.get(curr.value));
                        curr.boolVal = newVal;
                        if(curr.rightSibling == null)
                        {
                            curr.parent.boolVal = curr.boolVal;
                            curr = curr.parent;
                        }
                        else
                        {
                            curr = curr.rightSibling;
                        }
                    }
                }
                else if(curr.type == eqNodeType.not)
                {
                    curr.color = eqNodeColor.black;
                    eqNodeBooleanVal leftVal ;
                    if(curr.leftSibling.type == eqNodeType.term)
                        leftVal = map.get(curr.leftSibling.value);
                    else 
                        leftVal = curr.leftSibling.boolVal;
                    eqNodeBooleanVal newVal = eqNOT(leftVal);
                    curr.boolVal = newVal;
                    curr.parent.boolVal = curr.boolVal;
                    curr = curr.parent;
                }
                else if(curr.type == eqNodeType.and || curr.type == eqNodeType.or)
                {
                    curr.color = eqNodeColor.black;
                    if(curr.leftSibling.type== eqNodeType.term)
                        curr.boolVal = map.get(curr.leftSibling.value);
                    else
                        curr.boolVal = curr.leftSibling.boolVal;
                    curr = curr.rightSibling;
                }
                else 
                {
                    curr = curr.children.get(0);
                }
            }
        }
        return rowVal;
    }
    
    public static eqNodeBooleanVal getBooleanOprVal(eqNode prevNode, eqNodeBooleanVal boolval)
    {
        eqNodeBooleanVal val = null ;
        if(prevNode.type == eqNodeType.and)
        {
            val = eqAND(prevNode.boolVal,boolval);
        }
        else if(prevNode.type == eqNodeType.or)
        {
            val = eqOR(prevNode.boolVal,boolval);
        }
        return val;
    }
    
    public static eqNodeBooleanVal eqAND(eqNodeBooleanVal op1,eqNodeBooleanVal op2)
    {
        if(op1.equals(eqNodeBooleanVal._1) && op2.equals(eqNodeBooleanVal._1))
            return eqNodeBooleanVal._1;
        else 
            return eqNodeBooleanVal._0;
    }
    
    public static eqNodeBooleanVal eqOR(eqNodeBooleanVal op1,eqNodeBooleanVal op2)
    {
        if(op1.equals(eqNodeBooleanVal._1) || op2.equals(eqNodeBooleanVal._1))
            return eqNodeBooleanVal._1;
        else 
            return eqNodeBooleanVal._0;
    }
    
    public static eqNodeBooleanVal eqNOT(eqNodeBooleanVal op1)
    {
        if(op1.equals(eqNodeBooleanVal._0))
            return eqNodeBooleanVal._1;
        else 
            return eqNodeBooleanVal._0;
    }
    
    public static void resetTree(eqNode node)
    {
        node.boolVal = eqNodeBooleanVal._x;
        node.color = eqNodeColor.white;
        for(eqNode child:node.children)
        {
            resetTree(child);
        }
    }
    
}
