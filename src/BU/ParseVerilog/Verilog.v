module and3(output out,out1,out2,out3,out4, input in1,in2,in3,in4);

wire w1,w2,w3,w4;
nor (out,in3,in1,in2);
or (out1,in1,in2,in3);
and a1(out2,in3,in2);
xnor (out3,in1,in2,in3);
not (out4,in3);
  
endmodule