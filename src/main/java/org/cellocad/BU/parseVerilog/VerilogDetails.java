/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cellocad.BU.parseVerilog;

import org.cellocad.BU.parseVerilog.VerilogDetails.ioType;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author prashantvaidyanathan
 */
public class VerilogDetails {
    
    public enum ioType{
        wire,
        reg;
    }
    
    public List<input> inputs = new ArrayList<input>();
    public List<output> outputs = new ArrayList<output>();
    
    public void addinput(int isize,String iname, ioType iitype)
    {
        input newinp = new input();
        newinp.itype = iitype;
        newinp.name = iname;
        newinp.size = isize;
        inputs.add(newinp);
    }
    
    public void addoutput(int osize,String oname, ioType oitype)
    {
        output newout = new output();
        newout.otype = oitype;
        newout.name = oname;
        newout.size = osize;
        outputs.add(newout);
    }
    
    public void addinput(int isize,String iname)
    {
        input newinp = new input();
        newinp.name = iname;
        newinp.size = isize;
        newinp.itype = null;
        inputs.add(newinp);
    }
    
    public void addoutput(int osize,String oname)
    {
        output newout = new output();
        newout.name = oname;
        newout.size = osize;
        newout.otype = null;
        outputs.add(newout);
    }
    
    
}
class input
{
    public int size;
    public String name;
    public ioType itype;
}
class output
{
    public int size;
    public String name;
    public ioType otype;
}