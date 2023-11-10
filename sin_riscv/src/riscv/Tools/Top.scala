package riscv.Tools

import chisel3._
import chisel3.util._

import riscv.IFU._
import riscv.IFU.module._
import riscv.IDU._
import riscv.IDU.module._
import riscv.EXU._
import riscv.EXU.module._
import riscv.LSU._
import riscv.LSU.module._
import riscv.WBU._
import riscv.WBU.module._
import riscv.Pipe._
import riscv.Config.CONFIG
import riscv.DPIC._
import riscv.Util._
import riscv.Tools._

class TopBundle extends Bundle {
  val pc        = Output(UInt(CONFIG.ADDR.WIDTH.W))
  val inst      = Output(UInt(CONFIG.INST.WIDTH.W))
  val rs1Addr   = Output(UInt(CONFIG.REG.NUM_WIDTH.W))
  val rs2Addr   = Output(UInt(CONFIG.REG.NUM_WIDTH.W))
  val rs1Data   = Output(UInt(CONFIG.REG.WIDTH.W))
  val rs2Data   = Output(UInt(CONFIG.REG.WIDTH.W))
  val rdAddr    = Output(UInt(CONFIG.REG.NUM_WIDTH.W))
  val ALUresult = Output(UInt(CONFIG.DATA.XLEN.W))
}

class Top extends Module {
  val io = IO(new TopBundle())

  val core = Module(new core())

  val ISRAM = Module(new ISRAM())
  val DSRAM = Module(new DSRAM())

  core.ioIAXI <> ISRAM.ioAXI
  core.ioDAXI <> DSRAM.ioAXI

  // for test
  io.pc        := core.ioDeb.pc
  io.inst      := core.ioDeb.inst
  io.rs1Addr   := core.ioDeb.rs1Addr
  io.rs2Addr   := core.ioDeb.rs2Addr
  io.rs1Data   := core.ioDeb.rs1Data
  io.rs2Data   := core.ioDeb.rs2Data
  io.ALUresult := core.ioDeb.ALUresult
  io.rdAddr    := core.ioDeb.rdAddr
}
