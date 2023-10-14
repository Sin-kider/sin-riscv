package riscv.IFU

import chisel3._
import chisel3.util._

import riscv.Config._
import riscv.IFU.module._
import riscv.IDU.module._
import riscv.Util.module._

class IFU extends Module {
  val ioIFU  = IO(new IFUBundle)
  val ioIDU  = IO(Flipped(new IDUBundle))
  val ioLC   = IO(Flipped(new logicCtrlIFUBundle))
  val ioIMEM = IO(Flipped(new IMemBundle))
  // next pc
  val isChange = WireDefault(false.B)
  val stepNum  = WireDefault(0.U(CONFIG.ADDR.WIDTH.W))
  val nextPC   = WireDefault(0.U(CONFIG.ADDR.WIDTH.W))

  val PCReg = RegNext(nextPC, CONFIG.ADDR.BASE.U)
  isChange := ioLC.isJump | (ioLC.isBranch & ioLC.branchResult) | ioLC.isEcall
  stepNum  := Mux(ioLC.isCompress & PCReg =/= CONFIG.ADDR.BASE.U, 2.U, 4.U)
  nextPC   := Mux(isChange, ioLC.pcIn, PCReg + stepNum)

  // inst
  ioIMEM.addr := nextPC
  ioIFU.inst  := ioIMEM.data

  // io
  ioIFU.npc := nextPC

  // for test
  ioIFU.pc := PCReg
}
