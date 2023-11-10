package riscv.LSU.module

import chisel3._
import chisel3.util._

import riscv.Pipe.module._
import riscv.Config.CONFIG

class LSUCtrlBundle extends Bundle {
  val stallReq = Output(Bool())
  val pipe     = new pipeCtrlBundle()
}

class LSUStageBundle extends StageBundle {
  val valid = Output(Bool())
  // ID
  val rdEn   = Output(Bool())
  val rdAddr = Output(UInt(CONFIG.REG.NUM_WIDTH.W))
  // EX
  val ALUresult = Output(UInt(CONFIG.DATA.XLEN.W))
  // LS
  val isLoad = Output(Bool())
  // for npc
  val pc      = Output(UInt(CONFIG.ADDR.WIDTH.W))
  val rs1Data = Output(UInt(CONFIG.REG.WIDTH.W))
  val rs2Data = Output(UInt(CONFIG.REG.WIDTH.W))
  val rs1Addr = Output(UInt(CONFIG.REG.NUM_WIDTH.W))
  val rs2Addr = Output(UInt(CONFIG.REG.NUM_WIDTH.W))
  val inst    = Output(UInt(CONFIG.INST.WIDTH.W))
}

object len {
  val WIDTH = log2Ceil(5)
  val NOP   = 0.U(WIDTH.W)
  val B     = 1.U(WIDTH.W)
  val H     = 2.U(WIDTH.W)
  val W     = 3.U(WIDTH.W)
  val D     = 4.U(WIDTH.W)
}

class dMemLSUBundle extends Bundle {
  val ready    = Output(Bool())
  val writeEn  = Input(Bool())
  val readEn   = Input(Bool())
  val isSigned = Input(Bool())
  val dataLen  = Input(UInt(len.WIDTH.W))
  val addr     = Input(UInt(CONFIG.ADDR.WIDTH.W))
  val data     = Input(UInt(CONFIG.DATA.XLEN.W))
}

class dMemWBUBundle extends Bundle {
  val data = Output(UInt(CONFIG.INST.WIDTH.W))
}
