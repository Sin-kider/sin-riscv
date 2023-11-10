package riscv.IFU.module

import chisel3._
import chisel3.util._

import riscv.Pipe.module._
import riscv.Config.CONFIG

class iMemIFUBundle extends Bundle {
  val valid = Input(Bool())
  val ready = Output(Bool())
  val pc    = Input(UInt(CONFIG.ADDR.WIDTH.W))
}

class iMemIDUBundle extends Bundle {
  val inst = Output(UInt(CONFIG.INST.WIDTH.W))
}

class IFUCtrlBundle extends Bundle {
  val flushPC  = Input(UInt(CONFIG.ADDR.WIDTH.W))
  val stallReq = Output(Bool())
  val pipe     = new pipeCtrlBundle()
}

class IFUStageBundle extends StageBundle {
  val valid = Output(Bool())
  val pc    = Output(UInt(CONFIG.ADDR.WIDTH.W))
}
