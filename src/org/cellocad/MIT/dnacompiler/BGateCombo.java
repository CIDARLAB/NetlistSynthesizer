package org.cellocad.MIT.dnacompiler;

import org.cellocad.MIT.dnacompiler.Gate.GateType;

public class BGateCombo {
    public GateType Gtype;
    public String Inp1;
    public String Inp2;
    public String Out;
    public Double score;


    public static BGateCombo copyBGateCombo(BGateCombo inp)
    {
        BGateCombo outp = new BGateCombo();
        
        outp.Gtype = inp.Gtype;
        outp.Inp1 = inp.Inp1;
        outp.Inp2 = inp.Inp2;
        outp.Out = inp.Out;
        outp.score = inp.score;
        
        return outp;
    }
}



