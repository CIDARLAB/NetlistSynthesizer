module structuralXOR(output out1,out2,out3, input A, B,C);

wire w1,w2,w3,w4;

   not (w1,A);
   not (w2,B);
   nor (w3,w1,w2);
   nor (out1,w3,C);
   not (out2,out1);
   not (out3,out2);
   
endmodule