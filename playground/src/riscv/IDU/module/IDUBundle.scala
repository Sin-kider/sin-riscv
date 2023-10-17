package riscv.IDU.module

import chisel3._
import chisel3.util._
import riscv.Config.CONFIG
import riscv.IDU.module._

class regIORead extends Bundle {
  val en   = Input(Bool())
  val addr = Input(UInt(CONFIG.REG.NUM_WIDTH.W))
  val data = Output(UInt(CONFIG.REG.WIDTH.W))
}

class regIOWrite extends Bundle {
  val en   = Input(Bool())
  val addr = Input(UInt(CONFIG.REG.NUM_WIDTH.W))
  val data = Input(UInt(CONFIG.REG.WIDTH.W))
}

class regFileBundle extends Bundle {
  val ioRS1 = new regIORead()
  val ioRS2 = new regIORead()
  val ioRD  = new regIOWrite()
}

class IDUBundle extends Bundle {
  // val rdEn       = Output(Bool())
  // val rs1Data    = Output(UInt(CONFIG.REG.WIDTH.W))
  // val rs2Data    = Output(UInt(CONFIG.REG.WIDTH.W))
  // val op1Type    = Output(UInt(op1.WIDTH.W))
  // val op2Type    = Output(UInt(op2.WIDTH.W))
  // val immType    = Output(UInt(imm.WIDTH.W))
  // val aluCtrl    = Output(UInt(alu.WIDTH.W))
  // val branchCtrl = Output(UInt(branch.WIDTH.W))
  // val memCtrl    = Output(UInt(mem.WIDTH.W))
  // val csrCtrl    = Output(UInt(csr.WIDTH.W))
}
