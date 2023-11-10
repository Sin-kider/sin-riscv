package riscv.DPIC

import chisel3._
import chisel3.util._

import riscv.Config.CONFIG
import riscv.Util.module._

class DPICRegBundle extends Bundle {
  val clock   = Input(Clock())
  val reset   = Input(Reset())
  val rdEn    = Input(Bool())
  val rdAddr  = Input(UInt(CONFIG.REG.NUM_WIDTH.W))
  val rdData  = Input(UInt(CONFIG.REG.WIDTH.W))
  val rs1En   = Input(Bool())
  val rs1Addr = Input(UInt(CONFIG.REG.NUM_WIDTH.W))
  val rs2En   = Input(Bool())
  val rs2Addr = Input(UInt(CONFIG.REG.NUM_WIDTH.W))
}

class DPICReg extends BlackBox with HasBlackBoxPath {
  val io = IO(new DPICRegBundle())
  addPath("sin_riscv/test/resources/DPICReg.sv")
}
