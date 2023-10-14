package riscv.IDU

import chisel3._
import chisel3.util._

import riscv.IDU.module._
import riscv.IFU.module._
import riscv.Util.module._

class IDU extends Module {
  val ioIDU = IO(new IDUBundle)
  val ioIFU = IO(Flipped(new IFUBundle))
  val ioLC  = IO(Flipped(new logicCtrlIDUBundle))

  // logic ctrl
  val isCompress = WireDefault(false.B)
  isCompress      := ioIFU.inst(1, 0) =/= "b11".U
  ioLC.isCompress := isCompress
}
