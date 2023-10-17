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
  isChange := ioLC.isJump | (ioLC.isBranch & ioLC.isBranchSuccess) | ioLC.isEcall
  stepNum  := Mux(ioLC.isStall, 0.U, 4.U)
  nextPC   := Mux(isChange, ioLC.pcIn, PCReg + stepNum)

  // inst
  ioIMEM.addr := nextPC
  ioIFU.inst  := ioIMEM.data

  // io
  ioIFU.npc := nextPC

  val ifIdle :: ifPcReady :: ifWaitInst :: ifValid :: Nil = Enum(4)
  val state                                               = RegInit(ifIdle)
  switch(state) {
    is(ifIdle) {
      when(!ioLC.isStall) {
        state := ifPcReady
      }
    }
    is(ifPcReady) {
      // TODO
      // when(?) {
      //   state := ifWaitInst
      // }.otherwise {
      //   state := ifPcReady
      // }
      state := ifWaitInst
    }
    is(ifWaitInst) {
      when(ioIMEM.state.valid) {
        state := ifValid
      }.otherwise {
        state := ifWaitInst
      }
    }
    is(ifValid) {
      state := ifIdle
    }
  }
  ioIMEM.state.ready := true.B

  // for test
  ioIFU.pc := PCReg
}
