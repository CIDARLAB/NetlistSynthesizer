module and3(output out1,out2, input in1,in2,in3);
  reg r_out;
  assign out = r_out;
  always@(in1, in2, in3)
    begin
      case({in3,in2,in1})
        0: {out1,out2} = 2'b11;
        1: {out1,out2} = 2'b00;
        2: {out1,out2} = 2'b11;
        3: {out1,out2} = 2'b10;
        4: {out1,out2} = 2'b11;
        5: {out1,out2} = 2'b00;
        6: {out1,out2} = 2'b10;
        7: {out1,out2} = 2'b00;
        default: out = 2'b00;
      endcase
    end
endmodule

//module and3(output out, input in1,in2,in3);
//  reg r_out;
//  assign out = r_out;
//  always@(in1, in2, in3)
//    begin
//      case({in3,in2,in1})
//        0: out = 0;
//        1: out = 0;
//        2: out = 1;
//        3: out = 1;
//        4: out = 1;
//        5: out = 0;
//        6: out = 0;
//        7: out = 1;
//        default: out = 0;
//      endcase
//    end
//endmodule