module DPICSRAM # (
  parameter integer ADDR_WIDTH = 32,
  parameter integer DATA_WIDTH = 32
)
(
  // clock
  input                          clock,
  input                          reset,
  // aw
  input                          ioAXI_aw_valid,
  output reg                     ioAXI_aw_ready,
  input [ADDR_WIDTH - 1:0]       ioAXI_aw_addr,
  input [2:0]                    ioAXI_aw_prot,
  // w
  input                          ioAXI_w_valid,
  output reg                     ioAXI_w_ready,
  input [DATA_WIDTH - 1:0]       ioAXI_w_data,
  input [(DATA_WIDTH / 8) - 1:0] ioAXI_w_strb,
  // b
  output reg                     ioAXI_b_valid,
  input                          ioAXI_b_ready,
  output reg [1:0]               ioAXI_b_resp,
  // ar
  input                          ioAXI_ar_valid,
  output reg                     ioAXI_ar_ready,
  input [ADDR_WIDTH - 1:0]       ioAXI_ar_addr,
  input [2:0]                    ioAXI_ar_prot,
  // r
  output reg                     ioAXI_r_valid,
  input                          ioAXI_r_ready,
  output reg [DATA_WIDTH - 1:0]  ioAXI_r_data,
  output reg [1:0]               ioAXI_r_resp
);
  import "DPI-C" function void pmem_write(input int waddr, input int wdata, input byte wstrb);
  import "DPI-C" function void pmem_read(input int raddr, output int rdata);
  
  reg [ADDR_WIDTH - 1:0] aw_addr;
  reg [ADDR_WIDTH - 1:0] ar_addr;
  reg [DATA_WIDTH - 1:0] r_data;
  wire w_en;
  reg aw_en;
  wire r_en;
  // aw
  always @(posedge clock) begin
    if (reset) begin
      ioAXI_aw_ready <= 1'b0;
      aw_en <= 1'b1;
    end else if (ioAXI_aw_valid && ~ioAXI_aw_ready && ioAXI_w_valid && aw_en) begin
      ioAXI_aw_ready <= 1'b1;
      aw_en <= 1'b0;
    end else if (ioAXI_b_valid && ioAXI_b_ready) begin
      ioAXI_aw_ready <= 1'b0;
      aw_en <= 1'b1;
    end else begin
      ioAXI_w_ready <= 1'b0;
    end
  end

  always @(posedge clock) begin
    if (reset) begin
      aw_addr <= 0;
    end else if (ioAXI_aw_valid && ~ioAXI_aw_ready && ioAXI_w_valid && aw_en) begin
      aw_addr <= ioAXI_aw_addr;
    end
  end
  // w
  always @(posedge clock) begin
    if (reset) begin
      ioAXI_w_ready <= 1'b0;
    end else if (ioAXI_w_valid && ~ioAXI_w_ready && ioAXI_aw_valid && aw_en) begin
      ioAXI_w_ready <= 1'b1;
    end else begin
      ioAXI_w_ready <= 1'b0;
    end
  end

  // b
  always @(posedge clock) begin
    if (reset) begin
      ioAXI_b_valid <= 1'b0;
      ioAXI_b_resp <= 2'b0;
    end else if (ioAXI_aw_valid && ioAXI_aw_ready && ~ioAXI_b_valid && ioAXI_w_ready && ioAXI_w_valid) begin
      ioAXI_b_valid <= 1'b1;
      ioAXI_b_resp <= 2'b0;
    end else if (ioAXI_b_valid && ioAXI_b_ready) begin
      ioAXI_b_valid <= 1'b0;
    end
  end

  // write
  assign w_en = ioAXI_w_ready && ioAXI_w_valid && ioAXI_aw_ready && ioAXI_aw_valid;
  always @(*) begin
    if (w_en) begin
      pmem_write(aw_addr, ioAXI_w_data, ioAXI_w_strb);
    end
  end
  
  // ar
  always @(posedge clock) begin
    if (reset) begin
      ioAXI_ar_ready <= 1'b0;
      ar_addr <= 0;
    end else if (ioAXI_ar_valid && ~ioAXI_ar_ready) begin
      ioAXI_ar_ready <= 1'b1;
      ar_addr <= ioAXI_ar_addr;
      pmem_read(ioAXI_ar_addr, ioAXI_r_data);
    end else begin
      ioAXI_ar_ready <= 1'b0;
    end
  end

  // r
  always @(posedge clock) begin
    if (reset) begin
      ioAXI_r_valid <= 1'b0;
      ioAXI_r_resp <= 2'b0;
    end else if (~ioAXI_r_valid && ioAXI_ar_ready && ioAXI_ar_valid) begin
      ioAXI_r_valid <= 1'b1;
      ioAXI_r_resp <= 2'b0;
    end else if (ioAXI_r_valid && ioAXI_r_ready)begin
      ioAXI_r_valid <= 1'b0;
    end
  end
endmodule