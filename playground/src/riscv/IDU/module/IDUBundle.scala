package riscv.IDU.module

import chisel3._
import chisel3.util._

import riscv.IDU.module._
import riscv.Config.CONFIG

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
  val ready      = Input(Bool())
  val valid      = Output(Bool())
  val rdEn       = Output(Bool())
  val rdAddr     = Output(UInt(CONFIG.REG.NUM_WIDTH.W))
  val rs1Data    = Output(UInt(CONFIG.REG.WIDTH.W))
  val rs2Data    = Output(UInt(CONFIG.REG.WIDTH.W))
  val op1Type    = Output(UInt(op1.WIDTH.W))
  val op2Type    = Output(UInt(op2.WIDTH.W))
  val immData    = Output(UInt(CONFIG.DATA.XLEN.W))
  val aluCtrl    = Output(UInt(alu.WIDTH.W))
  val branchCtrl = Output(UInt(branch.WIDTH.W))
  val memCtrl    = Output(UInt(mem.WIDTH.W))
  val csrCtrl    = Output(UInt(csr.WIDTH.W))
  val pc         = Output(UInt(CONFIG.ADDR.WIDTH.W))
  val inst       = Output(UInt(CONFIG.INST.WIDTH.W))
  // for test
  val rs1Addr = Output(UInt(CONFIG.REG.NUM_WIDTH.W))
  val rs2Addr = Output(UInt(CONFIG.REG.NUM_WIDTH.W))
}
