package riscv.Tool

// This module is only used for testing.

import chisel3._
import chisel3.util._

import riscv.Config._
import riscv.IFU._
import riscv.IDU._
import riscv.Util._

class TopBundle extends Bundle {
  val inst = Output(UInt(CONFIG.INST.WIDTH.W))
  val npc  = Output(UInt(CONFIG.ADDR.WIDTH.W))
  // for test
  val pc = Output(UInt(CONFIG.ADDR.WIDTH.W))
}

class Top extends Module {
  val io = IO(new TopBundle)

  val IFU       = Module(new IFU)
  val IDU       = Module(new IDU)
  val IMem      = Module(new IMem)
  val logicCtrl = Module(new logicCtrl)

  IFU.ioIFU <> IDU.ioIFU
  IFU.ioIMEM <> IMem.io
  logicCtrl.ioIFU <> IFU.ioLC
  logicCtrl.ioIDU <> IDU.ioLC

  io.inst := IFU.ioIFU.inst
  io.npc  := IFU.ioIFU.npc
  io.pc   := IFU.ioIFU.pc
}
