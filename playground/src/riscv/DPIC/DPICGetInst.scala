package riscv.DPIC

import chisel3._
import chisel3.util._
import riscv.Config._

class dpicGetInst extends BlackBox with HasBlackBoxPath {
  val io = IO(new Bundle {
    val clock = Input(Clock())
    val reset = Input(Reset())
    val addr  = Input(UInt(CONFIG.ADDR.WIDTH.W))
    val data  = Output(UInt(CONFIG.INST.WIDTH.W))
  })
  addPath("playground/test/resources/dpicGetInst.sv")
}
