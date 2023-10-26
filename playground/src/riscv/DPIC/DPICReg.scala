package riscv.DPIC

import chisel3._
import chisel3.util._
import riscv.Util.module._
import riscv.IDU.module._

class DPICReg extends BlackBox with HasBlackBoxPath {
  val io = IO(new Bundle {
    val clock     = Input(Clock())
    val reset     = Input(Reset())
    val ioRegFile = new regFileBundle()
  })
  addPath("playground/test/resources/DPICReg.sv")
}
