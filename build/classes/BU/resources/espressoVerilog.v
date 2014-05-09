module EspToVerilog (
in3, in2, in1,
out1, out2 );
input in3, in2, in1;
output out1, out2;
wire w0, w1;
assign w0 = ~in3 | ~in2 | ~in1;
assign w1 = ~in3 | in2 | in1;
assign out1 =  w1;
assign out2 =  w0 & w1;
endmodule
