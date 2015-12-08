module 254_3(output out, input inp1,inp2);
     reg out;
     always@(inp1,inp2) begin
          case({inp1,inp2})
               2'b00: out = 1'b0;
               2'b01: out = 1'b1;
               2'b10: out = 1'b1;
               2'b11: out = 1'b0;
               default: out = 1'b0;
          endcase
     end
endmodule
