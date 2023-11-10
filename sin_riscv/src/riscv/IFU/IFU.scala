package riscv.IFU

import chisel3._
import chisel3.util._

import riscv.IFU.module._
import riscv.Pipe.module._
import riscv.Config.CONFIG

class IFU extends Module {
  val ioCtrl    = IO(new IFUCtrlBundle())
  val ioIFU     = IO(new IFUStageBundle())
  val ioIMemIFU = IO(Flipped(new iMemIFUBundle()))

  val pc     = WireDefault(0.U(CONFIG.ADDR.WIDTH.W))
  val nextPc = WireDefault(0.U(CONFIG.ADDR.WIDTH.W))
  val valid  = WireDefault(false.B)
  val iMemEn = WireDefault(false.B)
  val pcReg  = RegNext(nextPc, CONFIG.ADDR.BASE.U(CONFIG.ADDR.WIDTH.W))

  nextPc := Mux(ioCtrl.pipe.flush, ioCtrl.flushPC, Mux(ioCtrl.pipe.stall, pc, pcReg + 4.U))
  pc     := pcReg

  ioCtrl.stallReq := !ioIMemIFU.ready
  ioIMemIFU.valid := true.B
  ioIMemIFU.pc    := pc

  ioIFU.valid := ioIMemIFU.ready
  ioIFU.pc    := pc
}
