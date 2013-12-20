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
//        3b’100: out = 0;
//        3b’101: out = 1;
//        3b’110: out = 0;
//        3b’111: out = 1;
//        default: out = 0;
//      endcase
//    end
//endmodule

module and3(output out, input in1, in2,in3);
  reg r_out;
  assign out = r_out;
  always@(in1, in2, in3)
    begin
      case({in3,in2,in1})
        0: out = 1;
        1: out = 1;
        2: out = 1;
        3: out = 1;
        4: out = 1;
        5: out = 1;
        6: out = 1;
        7: out = 0;
        
        default: out = 0;
      endcase
    end
endmodule