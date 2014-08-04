module bestdepth(output o0, o1, o2, input ina, inb, inc);

   wire w0, w1, w2, w3, w4, w5, w6, w7, w8, w9;
   nor (w0, ina, inb);
   not (w1, inc);
   not (w2, w0);
   or (o0, w2, w1);
   nor (w3, ina, inc);
   not (w4, inb);
   not (w5,w3);
   or (o1, w4, w5);
   nor (w6, inb, inc);
   not (w7, ina);
   nor (w8, w0, w7);
   not (w9, w6);
   or (o2, w8, w9);

endmodule
