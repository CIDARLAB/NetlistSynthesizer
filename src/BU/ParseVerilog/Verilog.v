module and3(output out, input in1,in2,in3);
  reg r_out;
  assign out = r_out;
  always@(in1, in2, in3)
    begin
      case({in3,in2,in1})
        0: out = 0;
        1: out = 1;
        2: out = 1;
        3: out = 0;
        4: out = 0;
        5: out = 0;
        6: out = 0;
        7: out = 1;
        default: out = 0;
      endcase
    end
endmodule