module dpicGetInst (
  input clock,
  input reset,
  input [63:0] addr,
  output reg [31:0] data
);
  import "DPI-C" function int dpicGetInst(input longint addr);
  always @(posedge clock) begin
      data <= dpicGetInst(addr);
  end
endmodule