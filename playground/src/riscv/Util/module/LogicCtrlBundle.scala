package riscv.Util.module

import chisel3._
import chisel3.util._
import riscv.Config._

class logicCtrlIFUBundle extends Bundle {
  val isCompress   = Output(Bool())
  val isJump       = Output(Bool())
  val isBranch     = Output(Bool())
  val isEcall      = Output(Bool())
  val branchResult = Output(Bool())
  val isStall      = Output(Bool())
  val pcIn         = Output(UInt(CONFIG.ADDR.WIDTH.W))
}
class logicCtrlIDUBundle extends Bundle {
  val isCompress = Input(Bool())
}
