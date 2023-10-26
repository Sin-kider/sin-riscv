package riscv.Util

import chisel3._
import chisel3.util._
import riscv.Util.module._
import riscv.Tool.Tools

class logicCtrl extends Module {
  val ioIFU = IO(new logicCtrlIFUBundle)
  val ioIDU = IO(new logicCtrlIDUBundle)
  val ioEXU = IO(new logicCtrlEXUBundle)
  // for test
  val ioDeb = IO(new logicCtrlDebBundle)
  Tools.ignoreBundle(ioIFU)
  Tools.ignoreBundle(ioIDU)
  Tools.ignoreBundle(ioEXU)

  // for test
}
