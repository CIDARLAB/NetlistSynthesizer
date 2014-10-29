module structuralXOR(output out, input inA, inB, inC, inD);

wire w7,w6,w5,w4,w3,w2,w1;
   

not (w1, inB);
not (w3, inC);
nor (w2, w1, inC);
nor (w4, inB, w3);
nor (w5, w4, inD);
nor (w6, w2, w5);
not (w7, w6);
or (out, w7, inA);

endmodule