/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package netsynth;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author prashantvaidyanathan
 */
public class DagGraph_PV {

   
    public enum DAGType
    {
        NOT, 
        OR,
        NOR, 
        AND, 
        NAND,
        XOR,
        XNOR,
        INPUT,
        OUTPUT,
        OUTPUT_OR;
    }
    class Vertex
    {
        String name;
        DAGType vertexD;
    }
    public static class Child
    {
        String name;
        DAGType childD;
        Child()
        {
            name = "";
        }
        Child(String xname,DAGType xDAGType)
        {
            this.name = xname;
            this.childD = xDAGType;
        }
    }
    int Index;
    Vertex V = new Vertex();
    List<Child> C = new ArrayList<Child>();
}
