//module and3(output out, input in1, in2, in3);
//  reg r_out;
//  assign out = r_out;
//  always@(in1, in2, in3)
//    begin
//      case({in3,in2,in1})
//        3b’000: out = 0;
//        3b’001: out = 0;
//        3b’010: out = 0;
//        3b’011: out = 0;
//       3b’100: out = 0;
//        3b’101: out = 1;
//        3b’110: out = 0;
//        3b’111: out = 1;
//        default: out = 0;
//      endcase
//    end
//endmodule

module and3(output out, input in1, in2);
  reg r_out;
  assign out = r_out;
  always@(in1, in2)
    begin
      case({in2,in1})
        2b’00: out = 1;
        2b’01: out = 0;
        2b’10: out = 0;
        2b’11: out = 1;
        default: out = 0;
      endcase
    end
endmodule