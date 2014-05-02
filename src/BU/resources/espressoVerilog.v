module EspToVerilog (
a, b, c, d,
o1 );
input a, b, c, d;
output o1;
wire w0, w1, w2;
assign w0 = c | d;
assign w1 = ~a | ~b | d;
assign w2 = ~b | c;
assign o1 =  w0 & w1 & w2;
endmodule
