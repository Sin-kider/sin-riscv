package riscv.IDU.module

import chisel3._
import chisel3.util._

import riscv.Config.CONFIG
import riscv.Pipe.module._

class IDUCtrlBundle extends Bundle {
  val stallReq = Output(Bool())
  val pipe     = new pipeCtrlBundle()
}

class IDUStageBundle extends StageBundle {
  // IF
  val pc = Output(UInt(CONFIG.ADDR.WIDTH.W))
  // ID
  val rdEn       = Output(Bool())
  val rdAddr     = Output(UInt(CONFIG.REG.NUM_WIDTH.W))
  val op1Type    = Output(UInt(op1.WIDTH.W))
  val op2Type    = Output(UInt(op2.WIDTH.W))
  val immData    = Output(UInt(CONFIG.DATA.XLEN.W))
  val aluCtrl    = Output(UInt(alu.WIDTH.W))
  val branchCtrl = Output(UInt(branch.WIDTH.W))
  val memCtrl    = Output(UInt(mem.WIDTH.W))
  val csrCtrl    = Output(UInt(csr.WIDTH.W))
  val rs1Data    = Output(UInt(CONFIG.REG.WIDTH.W))
  val rs2Data    = Output(UInt(CONFIG.REG.WIDTH.W))
  // for test
  val rs1Addr = Output(UInt(CONFIG.REG.NUM_WIDTH.W))
  val rs2Addr = Output(UInt(CONFIG.REG.NUM_WIDTH.W))
  val inst    = Output(UInt(CONFIG.INST.WIDTH.W))
}

class regIDUBundle extends Bundle {
  val ioRS1 = new Bundle {
    val en   = Input(Bool())
    val addr = Input(UInt(CONFIG.REG.NUM_WIDTH.W))
    val data = Output(UInt(CONFIG.REG.WIDTH.W))
  }
  val ioRS2 = new Bundle {
    val en   = Input(Bool())
    val addr = Input(UInt(CONFIG.REG.NUM_WIDTH.W))
    val data = Output(UInt(CONFIG.REG.WIDTH.W))
  }
}

class regWBUBundle extends Bundle {
  val ioRD = new Bundle {
    val en   = Input(Bool())
    val addr = Input(UInt(CONFIG.REG.NUM_WIDTH.W))
    val data = Input(UInt(CONFIG.REG.WIDTH.W))
  }
}
