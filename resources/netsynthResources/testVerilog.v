module 65534_4(output out, input inp1,inp2,inp3,inp4);
     reg out;
     always@(inp1,inp2,inp3,inp4) begin
          case({inp1,inp2,inp3,inp4})
               4'b0000: out = 1'b1;
               4'b0001: out = 1'b1;
               4'b0010: out = 1'b1;
               4'b0011: out = 1'b1;
               4'b0100: out = 1'b1;
               4'b0101: out = 1'b1;
               4'b0110: out = 1'b1;
               4'b0111: out = 1'b1;
               4'b1000: out = 1'b1;
               4'b1001: out = 1'b1;
               4'b1010: out = 1'b1;
               4'b1011: out = 1'b1;
               4'b1100: out = 1'b1;
               4'b1101: out = 1'b1;
               4'b1110: out = 1'b1;
               4'b1111: out = 1'b0;
               default: out = 1'b0;
          endcase
     end
endmodule
