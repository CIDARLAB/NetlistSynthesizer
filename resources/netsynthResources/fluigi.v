module m1(input in1,in2,in3,in4,in5, output out1,out2);
	wire w1,w2,w3;     
	assign w1 = in1 # in2 # in3;
     	assign w2 = in4 * in5;
	assign w3 = in2 ^ in3;
	assign out1 = w1 * w2 * w3;
	assign out2 = w1 @ in5;
endmodule
