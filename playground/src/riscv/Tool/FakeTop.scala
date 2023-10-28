package riscv.Tool

// This module is only used for testing.

import chisel3._
import chisel3.util._

import riscv.IFU._
import riscv.IFU.module._
import riscv.IDU._
import riscv.IDU.module._
import riscv.EXU._
import riscv.EXU.module._
import riscv.Util._
import riscv.DPIC._
import riscv.Config.CONFIG

// class Top extends SRAM {}

class TopBundle extends EXUBundle {}

class Top extends Module {
  val io   = IO(new TopBundle)
  val ioRD = IO(new regIOWrite())

  val IFU       = Module(new IFU())
  val IDU       = Module(new IDU())
  val EXU       = Module(new EXU())
  val logicCtrl = Module(new logicCtrl())
  val iMem      = Module(new iMem())
  val ISRAM     = Module(new ISRAM())
  // val DSRAM     = Module(new DSRAM)
  val regFile = Module(new regFile())

  IFU.ioIFU <> IDU.ioIFU
  IDU.ioIDU <> EXU.ioIDU
  ISRAM.ioISRAM <> iMem.ioAXI
  iMem.ioIMem <> IFU.ioIMem
  logicCtrl.ioIFU <> IFU.ioLC
  logicCtrl.ioIDU <> IDU.ioLC
  logicCtrl.ioEXU <> EXU.ioLC
  regFile.ioRegFile.ioRS1 <> IDU.ioRS1
  regFile.ioRegFile.ioRS2 <> IDU.ioRS2
  io <> EXU.ioEXU
  // for test
  val DPICReg = Module(new DPICReg)
  DPICReg.io.clock := clock
  DPICReg.io.reset := reset
  DPICReg.io.ioRegFile.ioRS1 <> IDU.ioRS1
  DPICReg.io.ioRegFile.ioRS2 <> IDU.ioRS2
  Tools.ignoreBundle(DPICReg.io.ioRegFile.ioRD)
  regFile.ioRegFile.ioRD <> ioRD
}
