module and3(output out, input in1,in2,in3);
 reg r_out;
  assign out = r_out;
  always@(in1, in2, in3)
    begin
      case({in3,in2,in1})
        0: out = 1;
        1: out = 0;
        2: out = 1;
        3: out = 0;
        4: out = 1;
        5: out = 0;
        6: out = 1;
        7: out = 0;
        default: out = 0;
      endcase
    end
endmodule