module A(output out1, input a, b, c);

//output out1;
//input a, b;

 wire w0;
 assign w0 = a & b;
 assign out1 = w0 | (a & c);

endmodule
