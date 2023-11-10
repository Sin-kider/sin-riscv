module DPICReg # (
  parameter integer REG_NUM_WIDTH = 5,
  parameter integer REG_WIDTH = 32
)(
  input                         clock,
  input                         reset,
  input                         rdEn,
  input  [REG_NUM_WIDTH - 1:0]  rdAddr,
  input  [REG_WIDTH - 1:0]      rdData,
  input                         rs1En,
  input  [REG_NUM_WIDTH - 1:0]  rs1Addr,
  input                         rs2En,
  input  [REG_NUM_WIDTH - 1:0]  rs2Addr
);
  import "DPI-C" function void reg_write(input int addr, input int data);
  import "DPI-C" function void reg_read(input int addr);
  always @(posedge clock) begin
    if (rdEn) begin
      reg_write(rdAddr, rdData);
    end
    if (rs1En) begin
      reg_read(rs1Addr);
    end
    if (rs2En) begin
      reg_read(rs2Addr);
    end
  end
endmodule