module and3(output out1,out2, input in1,in2,in3);
  reg r_out;
  assign out = r_out;
  always@(in1, in2, in3)
    begin
      case({in3,in2,in1})
        0: {out1,out2} = 2'b10;
        1: {out1,out2} = 2'b11;
        2: {out1,out2} = 2'b11;
        3: {out1,out2} = 2'bx1;
        4: {out1,out2} = 2'b11;
        5: {out1,out2} = 2'b11;
        6: {out1,out2} = 2'b11;
        7: {out1,out2} = 2'b11;
        default: out = 2'b00;
      endcase
    end
endmodule