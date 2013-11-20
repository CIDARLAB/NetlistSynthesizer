`timescale 1ns / 1ps

///////////////////////////////////////////////////////////
//
// module name: test inputs
//////////////////////////////////////////////////////////

module XOR (
  output X,
  input A,                                                      
  input B,
  input C
    );

input wire A;
input wire B;
input wire C;
output reg X;

always@(*) begin

	case({A,B,C})
		3'b000: X = 1'b0;
		3'b001: X = 1'b1;
		3'b010: X = 1'b1;
		3'b011: X = 1'b0;
		3'b100: X = 1'b1;
		3'b101: X = 1'b0;
		3'b110: X = 1'b0;
		3'b111: X = 1'b1;

	endcase
end
endmodule
