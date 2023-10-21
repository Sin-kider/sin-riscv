package riscv.Tool

// This module is only used for testing.

import chisel3._
import chisel3.util._

import riscv.Config._
import riscv.IFU._
import riscv.IDU._
import riscv.Util._
import riscv.Util._
import riscv.IDU.module._

// class Top extends SRAM {}

class TopBundle extends IDUBundle {}

class Top extends Module {
  val io   = IO(new TopBundle)
  val ioRD = IO(new regIOWrite())

  val IFU       = Module(new IFU)
  val IDU       = Module(new IDU)
  val logicCtrl = Module(new logicCtrl)
  val SRAM      = Module(new SRAM)
  val regFile   = Module(new regFile())

  IFU.ioIFU <> IDU.ioIFU
  SRAM.io <> IFU.ioAXI
  logicCtrl.ioIFU <> IFU.ioLC
  logicCtrl.ioIDU <> IDU.ioLC
  regFile.ioRegFile.ioRS1 <> IDU.ioRS1
  regFile.ioRegFile.ioRS2 <> IDU.ioRS2
  io <> IDU.ioIDU
  // for test
  regFile.ioRegFile.ioRD <> ioRD
}
