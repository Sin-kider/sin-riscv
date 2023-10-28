module DPICReg # (
  parameter integer REG_NUM_WIDTH = 5,
  parameter integer REG_WIDTH = 32
)(
  input                         clock,
  input                         reset,
  input                         ioRegFile_ioRD_en,
  input  [REG_NUM_WIDTH - 1:0]  ioRegFile_ioRD_addr,
  input  [REG_WIDTH - 1:0]      ioRegFile_ioRD_data,
  input                         ioRegFile_ioRS1_en,
  input  [REG_NUM_WIDTH - 1:0]  ioRegFile_ioRS1_addr,
  output [REG_WIDTH - 1:0]      ioRegFile_ioRS1_data,
  input                         ioRegFile_ioRS2_en,
  input  [REG_NUM_WIDTH - 1:0]  ioRegFile_ioRS2_addr,
  output [REG_WIDTH - 1:0]      ioRegFile_ioRS2_data
);
  import "DPI-C" function void reg_write(input int addr, input int data);
  import "DPI-C" function void reg_read(input int addr);
  always @(posedge clock) begin
    if (ioRegFile_ioRD_en) begin
      reg_write(ioRegFile_ioRD_addr, ioRegFile_ioRD_data);
    end
    if (ioRegFile_ioRS1_en) begin
      reg_read(ioRegFile_ioRS1_addr);
    end
    if (ioRegFile_ioRS2_en) begin
      reg_read(ioRegFile_ioRS2_addr);
    end
  end
endmodule