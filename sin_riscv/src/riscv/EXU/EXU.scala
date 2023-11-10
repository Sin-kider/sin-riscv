package riscv.EXU

import chisel3._
import chisel3.util._

import riscv.IDU.module._
import riscv.EXU.module._
import riscv.Pipe.module._
import riscv.Config.CONFIG

class EXU extends Module {
  val ioCtrl      = IO(new EXUCtrlBundle())
  val ioEXU       = IO(new EXUStageBundle())
  val ioIDU       = IO(Flipped(new IDUStageBundle()))
  val ioHazardEXU = IO(Flipped(new hazardEXUBundle()))

  val operator1 = WireDefault(0.U(CONFIG.DATA.XLEN.W))
  val operator2 = WireDefault(0.U(CONFIG.DATA.XLEN.W))

  val flushReq   = WireDefault(false.B)
  val flushPC    = WireDefault(0.U(CONFIG.ADDR.WIDTH.W))
  val stallDelay = RegNext(ioCtrl.pipe.stall)

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

  val ALU       = Module(new ALU())
  val ALUresult = WireDefault(0.U(CONFIG.DATA.XLEN.W))
  ALU.ioALU.op1     := operator1
  ALU.ioALU.op2     := operator2
  ALU.ioALU.aluCtrl := ioIDU.aluCtrl
  ALUresult         := ALU.ioALU.result

  val Branch          = Module(new Branch())
  val isBranchSuccess = WireDefault(false.B)
  Branch.ioBranch.branchCtrl := ioIDU.branchCtrl
  Branch.ioBranch.rs1Data    := ioEXU.rs1Data
  Branch.ioBranch.rs2Data    := ioEXU.rs2Data
  Branch.ioBranch.pc         := ioIDU.pc
  Branch.ioBranch.offset     := ioIDU.immData
  isBranchSuccess            := Branch.ioBranch.isBranchSuccess

  flushPC  := Branch.ioBranch.branchPC
  flushReq := isBranchSuccess

  // io
  ioCtrl.flushPC  := flushPC
  ioCtrl.flushReq := flushReq

  ioHazardEXU.isLoad := (ioIDU.memCtrl === mem.LB) ||
    (ioIDU.memCtrl === mem.LBU) ||
    (ioIDU.memCtrl === mem.LH) ||
    (ioIDU.memCtrl === mem.LHU) ||
    (ioIDU.memCtrl === mem.LW) ||
    (ioIDU.memCtrl === mem.LWU) ||
    (ioIDU.memCtrl === mem.LD)
  ioHazardEXU.ioRD.en   := ioIDU.rdEn
  ioHazardEXU.ioRD.addr := ioIDU.rdAddr
  ioHazardEXU.ioRD.data := ALUresult

  ioEXU.pc        := ioIDU.pc
  ioEXU.rdEn      := ioIDU.rdEn
  ioEXU.rdAddr    := ioIDU.rdAddr
  ioEXU.memCtrl   := ioIDU.memCtrl
  ioEXU.csrCtrl   := ioIDU.csrCtrl
  ioEXU.rs1Data   := ioIDU.rs1Data
  ioEXU.rs2Data   := ioIDU.rs2Data
  ioEXU.ALUResult := ALUresult
  // for test
  ioEXU.rs1Addr := ioIDU.rs1Addr
  ioEXU.rs2Addr := ioIDU.rs2Addr
  ioEXU.inst    := ioIDU.inst
}
