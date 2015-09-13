module A(output out1, out2, out3, out4,  input in1, in2, in3);
  always@(in1,in2,in3)
    begin
      case({in1,in2,in3})
        3'b000: {out1,out2,out3,out4} = 4'b0000;
        3'b001: {out1,out2,out3,out4} = 4'b1001;
        3'b010: {out1,out2,out3,out4} = 4'b0000;
        3'b011: {out1,out2,out3,out4} = 4'b0110;
        3'b100: {out1,out2,out3,out4} = 4'b1100;
        3'b101: {out1,out2,out3,out4} = 4'b1001;
        3'b110: {out1,out2,out3,out4} = 4'b1100;
        3'b111: {out1,out2,out3,out4} = 4'b0110;
      endcase
    end
endmodule