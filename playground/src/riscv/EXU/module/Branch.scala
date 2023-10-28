package riscv.EXU.module

import chisel3._
import chisel3.util._

import riscv.IDU.module._
import riscv.Config.CONFIG

class Branch extends Module {
  val ioBranch = IO(new BranchBundle())

  val branchResult   = WireDefault(false.B)
  val branchPCResult = WireDefault(0.U(CONFIG.ADDR.WIDTH.W))

  branchResult := MuxCase(
    false.B,
    Seq(
      (ioBranch.branchCtrl === branch.NOP)  -> false.B,
      (ioBranch.branchCtrl === branch.BEQ)  -> (ioBranch.rs1Data === ioBranch.rs2Data).asBool,
      (ioBranch.branchCtrl === branch.BNE)  -> (ioBranch.rs1Data =/= ioBranch.rs2Data).asBool,
      (ioBranch.branchCtrl === branch.BLT)  -> (ioBranch.rs1Data.asSInt < ioBranch.rs2Data.asSInt).asBool,
      (ioBranch.branchCtrl === branch.BGE)  -> (ioBranch.rs1Data.asSInt >= ioBranch.rs2Data.asSInt).asBool,
      (ioBranch.branchCtrl === branch.BLTU) -> (ioBranch.rs1Data.asUInt < ioBranch.rs2Data.asUInt).asBool,
      (ioBranch.branchCtrl === branch.BGEU) -> (ioBranch.rs1Data.asUInt >= ioBranch.rs2Data.asUInt).asBool,
      (ioBranch.branchCtrl === branch.JAL)  -> true.B,
      (ioBranch.branchCtrl === branch.JALR) -> true.B
    )
  )
  branchPCResult := MuxCase(
    0.U(CONFIG.ADDR.WIDTH.W),
    Seq(
      (ioBranch.branchCtrl === branch.NOP)  -> 0.U,
      (ioBranch.branchCtrl === branch.BEQ)  -> (ioBranch.pc + ioBranch.offset),
      (ioBranch.branchCtrl === branch.BNE)  -> (ioBranch.pc + ioBranch.offset),
      (ioBranch.branchCtrl === branch.BLT)  -> (ioBranch.pc + ioBranch.offset),
      (ioBranch.branchCtrl === branch.BGE)  -> (ioBranch.pc + ioBranch.offset),
      (ioBranch.branchCtrl === branch.BLTU) -> (ioBranch.pc + ioBranch.offset),
      (ioBranch.branchCtrl === branch.BGEU) -> (ioBranch.pc + ioBranch.offset),
      (ioBranch.branchCtrl === branch.JAL)  -> (ioBranch.pc + ioBranch.offset),
      (ioBranch.branchCtrl === branch.JALR) -> ((ioBranch.pc + ioBranch.offset) & ~1.U(CONFIG.ADDR.WIDTH.W))
    )
  )

  ioBranch.isBranchSuccess := branchResult
  ioBranch.branchPC        := branchPCResult
}
