module m1(finput fi1,fi2,fi3, cinput ci1,ci2, foutput fo1, coutput co1);
  wire w1,w2;
  assign w1=fi1+fi2;
  assign w2=fi3/ci1;
  assign co1=ci1;
  assign fo1=w1+w2;
  
endmodule
