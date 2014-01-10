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
    
    public static String gname;
    public static int index;
    public static enum nodecolor{
        WHITE,
        GRAY,
        BLACK;
    }
    public static nodecolor ncolor;
    BGateNode parent = new BGateNode();
    List<BGateNode> children = new ArrayList<BGateNode>();
    
}
