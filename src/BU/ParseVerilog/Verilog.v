module structuralXOR(output out, input inA, inB, inC, inD);

wire w8,w7,w6,w5,w4,w3,w2,w1;
   
not (w1, inB);
not (w3, inC);
not (w4, inD);
nor (w5, w1, w4);
nor (w2, w1, inC);
nor (w6, w3, w5);
nor (w7, w2, w6);
not (w8, w7);
or (out, w8, inA);
   
endmodule