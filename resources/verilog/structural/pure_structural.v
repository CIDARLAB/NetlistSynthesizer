module Structural(output out1, input in1,in2);

wire w1,w2;
not (w1, in1);
not (w2, in2);
nor (out1, w2, w1);   
   
endmodule

