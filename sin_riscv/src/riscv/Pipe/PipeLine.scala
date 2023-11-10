package riscv.Pipe

import chisel3._
import chisel3.util._

import riscv.Pipe.module._

// 流水线模块
class pipeLine[T <: StageBundle](stageBundle: T) extends Module {
  val ioPCtrl     = IO(new pipeRegCtrlBundle())
  val ioPrevStage = IO(Flipped(stageBundle))
  val ioNextStage = IO(stageBundle)

  val stageReg = RegInit(stageBundle, stageBundle.initVal())

  when(ioPCtrl.flush || (ioPCtrl.stallPrev && !ioPCtrl.stallNext)) {
    stageReg := stageBundle.initVal()
  }.elsewhen(!ioPCtrl.stallPrev) {
    stageReg := ioPrevStage
  }
  ioNextStage := stageReg
}
