package riscv.Util.module

import chisel3._
import chisel3.util._
import riscv.Config._

class logicCtrlIFUBundle extends Bundle {
  val isJump          = Output(Bool())
  val isBranch        = Output(Bool())
  val isEcall         = Output(Bool())
  val isBranchSuccess = Output(Bool())
  val isStall         = Output(Bool())
  val pcIn            = Output(UInt(CONFIG.ADDR.WIDTH.W))
}
class logicCtrlIDUBundle extends Bundle {
  val isStall = Output(Bool())
}
class logicCtrlDebBundle extends Bundle {
  val isStall = Input(Bool())
}
