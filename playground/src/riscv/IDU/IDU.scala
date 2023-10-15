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

  // rd imm
  val rd   = ioIFU.inst(11, 7)
  val rs1  = ioIFU.inst(19, 15)
  val rs2  = ioIFU.inst(24, 20)
  val immI = Cat(Fill(52, ioIFU.inst(31)), ioIFU.inst(31, 20))
  val immS = Cat(Fill(52, ioIFU.inst(31)), ioIFU.inst(31, 25), ioIFU.inst(11, 7))
  val immB = Cat(Fill(52, ioIFU.inst(31)), ioIFU.inst(7), ioIFU.inst(30, 25), ioIFU.inst(11, 8), 0.U(1.W))
  val immU = Cat(Cat(Fill(32, ioIFU.inst(31)), ioIFU.inst(31, 12)), Fill(12, 0.U))
  val immJ = Cat(Fill(44, ioIFU.inst(31)), ioIFU.inst(31), ioIFU.inst(19, 12), ioIFU.inst(20), ioIFU.inst(30, 21), Fill(1, 0.U))

}
