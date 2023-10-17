package riscv.Util.module

import chisel3._
import chisel3.util._

import riscv.Config._
import riscv.Util._

class IMemBundle extends Bundle {
  val state = new stateBundle
  val addr  = Input(UInt(CONFIG.ADDR.WIDTH.W))
  val data  = Output(UInt(CONFIG.INST.WIDTH.W))
}
