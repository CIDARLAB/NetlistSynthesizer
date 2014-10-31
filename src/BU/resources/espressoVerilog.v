module EspToVerilog (
in1, in2, in3, in4,
out );
input in1, in2, in3, in4;
output out;
wire w0, w1, w2, w3;
assign w0 = in2 | in4;
assign w1 = ~in2 | ~in4;
assign w2 = ~in3;
assign w3 = ~in1;
assign out =  w0 & w1 & w2 & w3;
endmodule
