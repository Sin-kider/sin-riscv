package riscv.IFU.module

import chisel3._
import chisel3.util._

import riscv.Config._
import riscv.Util._

class IFUBundle extends Bundle {
  val inst = Output(UInt(CONFIG.INST.WIDTH.W))
  val npc  = Output(UInt(CONFIG.ADDR.WIDTH.W))
  // for test
  val pc = Output(UInt(CONFIG.ADDR.WIDTH.W))
}
