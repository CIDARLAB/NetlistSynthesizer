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
public class eqNode {
    
    public int stage;
    public eqNode parent;
    public List<eqNode> children;
    public eqNode leftSibling;
    public eqNode rightSibling;
    
    public enum eqNodeType {
        eol,
        output,
        equals,
        or,
        and,
        not,
        bracket,
        equation,
        term,
        root
    }
    
    public enum eqNodeColor{
        white,
        gray,
        black
    }
    public enum eqNodeBooleanVal{
        _1,
        _0,
        _x
    }
    public eqNodeColor color;
    public eqNodeBooleanVal boolVal;
    public eqNodeType type;
    public String value; 
    
    
    public eqNode()
    {
        this.children = new ArrayList<eqNode>();
    } 
    
    public eqNode(eqNode parent,eqNodeType _type, String _value)
    {
        this.parent = parent;
        this.stage = parent.stage+1;
        this.type = _type;
        this.value = _value;
        this.children = new ArrayList<eqNode>();
    }
    
    public eqNode(eqNodeType _type,String _value)
    {
        this.type =  _type;
        this.value = _value; 
        this.children = new ArrayList<eqNode>();
    }
    
    public eqNode(eqNodeType _type,String _value, int _stage)
    {
        this.type = _type;
        this.value = _value;
        this.stage = _stage;
        this.children = new ArrayList<eqNode>();
    }
    
    public List<eqNode> getChildren(eqNode node)
    {
        List<eqNode> _children = new ArrayList<eqNode>();
        for (eqNode child : node.children) {
            _children.add(child);
        }
        
        return _children;
    }
    
    public String printAST()
    {
        String val ="";
        val = "Type : "+type + " ||Stage : " + stage + " ||Value :" + value;
        for(eqNode node:children)
        {
            val += "\n";
            for(int i=0;i<(node.stage);i++)
            {
                val+=" ";
            }
            val += node.printAST();
        }
        return val;
    }
    
    
}
