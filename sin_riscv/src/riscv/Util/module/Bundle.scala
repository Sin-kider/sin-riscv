package riscv.Util.module

import chisel3._
import chisel3.util._

import riscv.Util.module._
import riscv.Config.CONFIG

class coreDebBundle extends Bundle {
  val pc        = Output(UInt(CONFIG.ADDR.WIDTH.W))
  val inst      = Output(UInt(CONFIG.INST.WIDTH.W))
  val rs1Addr   = Output(UInt(CONFIG.REG.NUM_WIDTH.W))
  val rs2Addr   = Output(UInt(CONFIG.REG.NUM_WIDTH.W))
  val rs1Data   = Output(UInt(CONFIG.REG.WIDTH.W))
  val rs2Data   = Output(UInt(CONFIG.REG.WIDTH.W))
  val rdAddr    = Output(UInt(CONFIG.REG.NUM_WIDTH.W))
  val ALUresult = Output(UInt(CONFIG.DATA.XLEN.W))
}
