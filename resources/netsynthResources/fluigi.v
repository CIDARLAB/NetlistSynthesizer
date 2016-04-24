module m1(input in1,in2,in3,in4,in5, output out1,out2);
     assign out1 = in1 # in2 # in3;
     assign out2 = in4 * in5;
endmodule
