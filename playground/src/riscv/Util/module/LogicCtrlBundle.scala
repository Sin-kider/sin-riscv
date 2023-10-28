package riscv.Util.module

import chisel3._
import chisel3.util._

import riscv.Config.CONFIG

class logicCtrlIFUBundle extends Bundle {
  val isEcall         = Output(Bool())
  val isBranchSuccess = Output(Bool())
  val isStall         = Output(Bool())
  val branchPC        = Output(UInt(CONFIG.ADDR.WIDTH.W))
}

class logicCtrlIDUBundle extends Bundle {
  val isStall = Output(Bool())
}

class logicCtrlEXUBundle extends Bundle {
  val isStall         = Output(Bool())
  val isBranchSuccess = Input(Bool())
  val branchPC        = Input(UInt(CONFIG.ADDR.WIDTH.W))
}

class logicCtrlDebBundle extends Bundle {
  // val isStall = Input(Bool())
}
