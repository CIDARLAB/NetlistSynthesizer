/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cellocad.BU.adaptors;

import java.util.ArrayList;
import java.util.List;
import org.cellocad.BU.parseVerilog.CircuitDetails;
import org.cellocad.BU.parseVerilog.Convert;

/**
 *
 * @author prash
 */
public class EspressoAdaptor {

    public static List<String> createFile(CircuitDetails circ) {
        List<String> esfile = new ArrayList<String>();
        int pw = (int) Math.pow(2, circ.inputNames.size());
        List<String> truthfunc = new ArrayList<String>();
        for (String xtt : circ.truthTable) {
            truthfunc.add(xtt);
        }
        String line = "";
        line += (".i " + circ.inputNames.size());
        esfile.add(line);
        line = "";
        line += (".o " + circ.outputNames.size());
        esfile.add(line);
        line = "";
        line += ".ilb";
        for (String xinp : circ.inputNames) {
            line += " " + xinp;
        }
        esfile.add(line);
        line = "";
        line += ".ob";
        for (String xout : circ.outputNames) {
            line += " " + xout;
        }
        esfile.add(line);
        for (int i = 0; i < pw; i++) {
            line = "";
            String tempinp = Convert.dectoBin(i, circ.inputNames.size());
            line += tempinp;
            line += " ";
            for (int j = 0; j < circ.outputNames.size(); j++) {
                line += truthfunc.get(j).charAt(i);
            }
            esfile.add(line);
        }
        line = "";
        line += ".e";
        esfile.add(line);
        return esfile;
    }
    
}
