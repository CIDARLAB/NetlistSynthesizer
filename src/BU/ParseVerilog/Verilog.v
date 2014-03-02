module and3(output out1,out2,out3 input in1,in2,in3);
  reg r_out;
  assign out = r_out;
  always@(in1, in2, in3)
    begin
      case({in3,in2,in1})
        0: {out1,out2,out3} = 3'b000;
        1: {out1,out2,out3} = 3'b001;
        2: {out1,out2,out3} = 3'b011;
        3: {out1,out2,out3} = 3'b010;
        4: {out1,out2,out3} = 3'b110;
        5: {out1,out2,out3} = 3'b111;
        6: {out1,out2,out3} = 3'b101;
        7: {out1,out2,out3} = 3'b100;
        default: out = 3'b000;
      endcase
    end
endmodule