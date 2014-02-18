module and3(output out, input in1,in2,in3);
  reg r_out;
  assign out = r_out;
  always@(in1, in2, in3)
    begin
      case({in3,in2,in1})
        0: out = 0;
        1: out = 0;
        2: out = 1;
        3: out = 1;
        4: out = 1;
        5: out = 0;
        6: out = 0;
        7: out = 1;
        default: out = 0;
      endcase
    end
endmodule

//module and3(output out, input in1,in2,in3);
//  reg r_out;
//  assign out = r_out;
//  always@(in1, in2, in3)
//    begin
//      case({in3,in2,in1})
//        0: {out1,out2} = 00;
//        1: {out1,out2} = 00;
//        2: {out1,out2} = 10;
//        3: {out1,out2} = 10;
//        4: {out1,out2} = 10;
//        5: {out1,out2} = 01;
//        6: {out1,out2} = 01;
//        7: {out1,out2} = 10;
//        default: {out1,out2} = 00;
//      endcase
//    end
//endmodule