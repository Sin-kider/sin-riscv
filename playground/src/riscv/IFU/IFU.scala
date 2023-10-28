package riscv.IFU

import chisel3._
import chisel3.util._

import riscv.IFU.module._
import riscv.IDU.module._
import riscv.Util.module._
import riscv.Config.CONFIG

class IFU extends Module {
  // io
  val ioIFU  = IO(new IFUBundle())
  val ioLC   = IO(Flipped(new logicCtrlIFUBundle()))
  val ioIMem = IO(Flipped(new iMemBundle()))

  // wire & reg
  val regEn    = WireDefault(false.B)
  val isChange = WireDefault(false.B)
  val iMemEn   = WireDefault(false.B)
  val stepNum  = WireDefault(0.U(CONFIG.ADDR.WIDTH.W))
  val nextPC   = WireDefault(0.U(CONFIG.ADDR.WIDTH.W))
  val PCReg    = RegEnable(nextPC, CONFIG.ADDR.BASE.U, regEn)

  // next pc
  isChange := ioLC.isBranchSuccess | ioLC.isEcall
  stepNum  := Mux(ioLC.isStall, 0.U, 4.U)
  nextPC   := Mux(isChange, ioLC.branchPC, PCReg + stepNum)

  // inst
  regEn  := ioIFU.ready & ioIMem.ready
  iMemEn := !ioLC.isStall

  // io
  ioIFU.pc     := PCReg
  ioIFU.inst   := ioIMem.data
  ioIFU.valid  := regEn
  ioIMem.valid := iMemEn
  ioIMem.addr  := PCReg
}
