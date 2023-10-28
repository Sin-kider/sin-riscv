package riscv.DPIC

import chisel3._
import chisel3.util._

import riscv.Util.module._

class DPICSRAM extends BlackBox with HasBlackBoxPath {
  val io = IO(new Bundle {
    val clock = Input(Clock())
    val reset = Input(Reset())
    val ioAXI = new AXILiteSlave
  })
  addPath("playground/test/resources/DPICSRAM-SIM.sv")
}
