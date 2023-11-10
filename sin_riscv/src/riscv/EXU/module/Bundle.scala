package riscv.EXU.module

import chisel3._
import chisel3.util._

import riscv.IDU.module._
import riscv.Pipe.module._
import riscv.Config.CONFIG

class ALUBundle extends Bundle {
  val aluCtrl = Input(UInt(alu.WIDTH.W))
  val op1     = Input(UInt(CONFIG.DATA.XLEN.W))
  val op2     = Input(UInt(CONFIG.DATA.XLEN.W))
  val result  = Output(UInt(CONFIG.DATA.XLEN.W))
}

class BranchBundle extends Bundle {
  val branchCtrl      = Input(UInt(branch.WIDTH.W))
  val rs1Data         = Input(UInt(CONFIG.DATA.XLEN.W))
  val rs2Data         = Input(UInt(CONFIG.DATA.XLEN.W))
  val pc              = Input(UInt(CONFIG.ADDR.WIDTH.W))
  val offset          = Input(UInt(CONFIG.DATA.XLEN.W))
  val isBranchSuccess = Output(Bool())
  val branchPC        = Output(UInt(CONFIG.ADDR.WIDTH.W))
}

class EXUCtrlBundle extends Bundle {
  val flushReq = Output(Bool())
  val flushPC  = Output(UInt(CONFIG.ADDR.WIDTH.W))
  val pipe     = new pipeCtrlBundle()
}

class EXUStageBundle extends StageBundle {
  // IF
  val pc = Output(UInt(CONFIG.ADDR.WIDTH.W))
  // ID
  val rdEn    = Output(Bool())
  val rdAddr  = Output(UInt(CONFIG.REG.NUM_WIDTH.W))
  val memCtrl = Output(UInt(mem.WIDTH.W))
  val csrCtrl = Output(UInt(csr.WIDTH.W))
  // EX
  val rs1Data   = Output(UInt(CONFIG.REG.WIDTH.W))
  val rs2Data   = Output(UInt(CONFIG.REG.WIDTH.W))
  val ALUResult = Output(UInt(CONFIG.DATA.XLEN.W))
  // for test
  val rs1Addr = Output(UInt(CONFIG.REG.NUM_WIDTH.W))
  val rs2Addr = Output(UInt(CONFIG.REG.NUM_WIDTH.W))
  val inst    = Output(UInt(CONFIG.INST.WIDTH.W))
}
