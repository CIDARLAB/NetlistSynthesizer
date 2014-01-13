/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MIT.dnacompiler;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author prashantvaidyanathan
 */
public class BGateNode {
    
    public static String bgname;
    public static Gate bgate;
    public static int index;
    public static enum nodecolor{
        WHITE,
        GRAY,
        BLACK;
    }
    public static nodecolor ncolor;
    BGateNode parent;
    BGateNode child;
    BGateNode Next;
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
}
