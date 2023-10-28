package riscv.EXU

import chisel3._
import chisel3.util._

import riscv.EXU.module._
import riscv.IDU.module._
import riscv.Util.module._
import riscv.Config.CONFIG

class EXU extends Module {
  val ioEXU = IO(new EXUBundle())
  val ioIDU = IO(Flipped(new IDUBundle()))
  val ioLC  = IO(Flipped(new logicCtrlEXUBundle()))

  // Enable
  val regEn = WireDefault(false.B)
  regEn := ioIDU.valid & ioEXU.ready
  // op1 & op2
  val operator1 = WireDefault(0.U(CONFIG.DATA.XLEN.W))
  val operator2 = WireDefault(0.U(CONFIG.DATA.XLEN.W))
  operator1 := MuxCase(
    0.U,
    Seq(
      (ioIDU.op1Type === op1.NOP)  -> 0.U,
      (ioIDU.op1Type === op1.RS1)  -> ioIDU.rs1Data,
      (ioIDU.op1Type === op1.PC)   -> ioIDU.pc,
      (ioIDU.op1Type === op1.ZERO) -> 0.U
    )
  )
  operator2 := MuxCase(
    0.U,
    Seq(
      (ioIDU.op2Type === op2.NOP)  -> 0.U,
      (ioIDU.op2Type === op2.RS2)  -> ioIDU.rs2Data,
      (ioIDU.op2Type === op2.IMM)  -> ioIDU.immData,
      (ioIDU.op2Type === op2.FORE) -> 4.U
    )
  )

  // alu
  val ALUresult = WireDefault(0.U(CONFIG.DATA.XLEN.W))
  val ALU       = Module(new ALU())
  ALU.ioALU.op1     := operator1
  ALU.ioALU.op2     := operator2
  ALU.ioALU.aluCtrl := ioIDU.aluCtrl
  ALUresult         := ALU.ioALU.result

  // branch
  val branchPC        = WireDefault(0.U(CONFIG.ADDR.WIDTH.W))
  val isBranchSuccess = WireDefault(false.B)
  val Branch          = Module(new Branch())
  Branch.ioBranch.branchCtrl := ioIDU.branchCtrl
  Branch.ioBranch.rs1Data    := ioIDU.rs1Data
  Branch.ioBranch.rs2Data    := ioIDU.rs2Data
  Branch.ioBranch.pc         := ioIDU.pc
  Branch.ioBranch.offset     := ioIDU.immData
  branchPC                   := Branch.ioBranch.branchPC
  isBranchSuccess            := Branch.ioBranch.isBranchSuccess

  // io
  ioLC.branchPC        := branchPC
  ioLC.isBranchSuccess := isBranchSuccess
  ioIDU.ready          := ioEXU.ready
  ioEXU.valid          := RegNext(regEn, false.B)
  ioEXU.ALUresult      := ALUresult
  ioEXU.rs1Data        := RegEnable(ioIDU.rs1Data, 0.U, regEn)
  ioEXU.rs2Data        := RegEnable(ioIDU.rs2Data, 0.U, regEn)
  ioEXU.memCtrl        := RegEnable(ioIDU.memCtrl, mem.NOP, regEn)
  ioEXU.csrCtrl        := RegEnable(ioIDU.csrCtrl, csr.NOP, regEn)
  ioEXU.rdEn           := RegEnable(ioIDU.rdEn, rd.NOP, regEn)
  ioEXU.memCtrl        := RegEnable(ioIDU.memCtrl, mem.NOP, regEn)
  ioEXU.csrCtrl        := RegEnable(ioIDU.csrCtrl, csr.NOP, regEn)
  ioEXU.inst           := RegEnable(ioIDU.inst, 0.U, regEn)
  ioEXU.pc             := RegEnable(ioIDU.pc, CONFIG.ADDR.BASE.U, regEn)
  // for test
  ioEXU.rs1Addr := RegEnable(ioIDU.rs1Addr, 0.U, regEn)
  ioEXU.rs2Addr := RegEnable(ioIDU.rs2Addr, 0.U, regEn)
  ioEXU.rdAddr  := RegEnable(ioIDU.rdAddr, 0.U, regEn)
  ioEXU.immData := RegEnable(ioIDU.immData, 0.U, regEn)
}
