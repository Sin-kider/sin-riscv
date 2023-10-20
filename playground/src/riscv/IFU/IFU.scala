package riscv.IFU

import chisel3._
import chisel3.util._

import riscv.Config._
import riscv.IFU.module._
import riscv.IDU.module._
import riscv.Util.module._

class IFU extends Module {
  val ioIFU = IO(new IFUBundle)
  val ioIDU = IO(Flipped(new IDUBundle))
  val ioLC  = IO(Flipped(new logicCtrlIFUBundle))
  val ioAXI = IO(new AXILiteMaster)
  // next pc
  val isChange = WireDefault(false.B)
  val stepNum  = WireDefault(0.U(CONFIG.ADDR.WIDTH.W))
  val nextPC   = WireDefault(0.U(CONFIG.ADDR.WIDTH.W))
  val regEn    = WireDefault(false.B)

  val PCReg = RegEnable(nextPC, CONFIG.ADDR.BASE.U, regEn)
  isChange := ioLC.isJump | (ioLC.isBranch & ioLC.isBranchSuccess) | ioLC.isEcall
  stepNum  := Mux(ioLC.isStall, 0.U, 4.U)
  nextPC   := Mux(isChange, ioLC.pcIn, PCReg + stepNum)

  // inst
  val iWritePC :: iReadInst :: Nil = Enum(2)

  val iState = RegInit(iWritePC)
  regEn          := (iState === iReadInst && ioAXI.r.valid)
  ioAXI.ar.valid := true.B
  ioAXI.r.ready  := (iState === iReadInst)
  ioAXI.ar.addr  := nextPC
  ioAXI.ar.prot  := 0.U
  ioAXI.aw.addr  := 0.U
  ioAXI.aw.prot  := 0.U
  ioAXI.aw.valid := false.B
  ioAXI.w.data   := 0.U
  ioAXI.w.strb   := 0.U
  ioAXI.w.valid  := false.B
  ioAXI.b.ready  := false.B
  switch(iState) {
    is(iWritePC) {
      when(ioAXI.ar.ready) {
        iState := iReadInst
      }.otherwise {
        iState := iWritePC
      }
    }
    is(iReadInst) {
      when(ioAXI.r.valid) {
        iState := iWritePC
      }.otherwise {
        iState := iReadInst
      }
    }
  }

  // io
  ioIFU.npc  := nextPC
  ioIFU.inst := ioAXI.r.data

  // for test
  ioIFU.pc := PCReg
}
