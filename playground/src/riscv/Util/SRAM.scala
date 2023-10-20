package riscv.Util

import chisel3._
import chisel3.util._
import riscv.Util.module._
import riscv.DPIC._

class SRAM extends Module {
  val io = IO(new AXILiteSlave)

  val DPICSRAM = Module(new DPICSRAM)
  DPICSRAM.io.clock := clock
  DPICSRAM.io.reset := reset
  io <> DPICSRAM.io.ioAXI
}
