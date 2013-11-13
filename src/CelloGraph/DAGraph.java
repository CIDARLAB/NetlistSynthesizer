/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CelloGraph;

import java.util.ArrayList;

/**
 *
 * @author prashantvaidyanathan
 */
public class DAGraph {
    
    public ArrayList<DAGVertex> Vertices;
    public ArrayList<DAGEdge> Edges;
    
    public DAGraph()
    {
        Vertices = new ArrayList<DAGVertex>();
        Edges = new ArrayList<DAGEdge>();
    }
    
    public static void printGraph(){}
    public static DAGEdge FindEdge(){return null;}
    public static DAGVertex FindVertex(){return null;}
    
}
