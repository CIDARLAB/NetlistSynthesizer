module and3(output out, input in1,in2,in3);

wire w1,w2,w3,w4;
nor (w1,in1,in2);
or (w2,in1,in2,in3);
and a1(w3,in3,w1);
xor (w4,w1,w2,w3);
not (out,w4);
  
endmodule