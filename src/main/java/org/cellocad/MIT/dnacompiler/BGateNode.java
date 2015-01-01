/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cellocad.MIT.dnacompiler;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author prashantvaidyanathan
 */
public class BGateNode {
    
    public String bgname ="";
    public  Gate bgate;
    public  int index;
    public  enum nodecolor{
        WHITE,
        GRAY,
        BLACK;
    }
    public  nodecolor ncolor;
    public BGateNode parent;
    public BGateNode child;
    public BGateNode Next;
    //List<BGateNode> children = new ArrayList<BGateNode>();
    
    public BGateNode()
    {
        this.Next = null;
        this.child = null;
        this.parent = null;
        this.ncolor = nodecolor.WHITE;
        this.bgate = new Gate();
        this.bgname = "";
        this.index = 0;
    }
    public BGateNode(BGateNode xnext, BGateNode xchild, BGateNode xparent, nodecolor xncolor, Gate xgate, String xbgname, int xind)
    {
        this.Next = xnext;
        this.child = xchild;
        this.parent = xparent;
        this.ncolor = xncolor;
        this.bgate = new Gate(xgate);
        this.bgname = xbgname;
        this.index = xind;
    }
}
