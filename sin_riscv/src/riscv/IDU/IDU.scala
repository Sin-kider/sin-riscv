package riscv.IDU

import chisel3._
import chisel3.util._

import riscv.IFU.module._
import riscv.IDU.module._
import riscv.Pipe.module._
import riscv.Config.CONFIG

class IDU extends Module {
  val ioCtrl      = IO(new IDUCtrlBundle())
  val ioIDU       = IO(new IDUStageBundle())
  val ioIFU       = IO(Flipped(new IFUStageBundle))
  val ioIMemIDU   = IO(Flipped(new iMemIDUBundle()))
  val ioRegIDU    = IO(Flipped(new regIDUBundle()))
  val ioHazardIDU = IO(Flipped(new hazardIDUBundle()))

  val valid      = WireDefault(false.B)
  val inst       = WireDefault(0.U(CONFIG.INST.WIDTH.W))
  val rs1Data    = WireDefault(0.U(CONFIG.REG.WIDTH.W))
  val rs2Data    = WireDefault(0.U(CONFIG.REG.WIDTH.W))
  val rdAddr     = inst(11, 7)
  val rs1Addr    = inst(19, 15)
  val rs2Addr    = inst(24, 20)
  val immData    = WireDefault(0.U(CONFIG.DATA.XLEN.W))
  val immR       = 0.U(CONFIG.DATA.XLEN.W)
  val immN       = 0.U(CONFIG.DATA.XLEN.W)
  val immI       = Cat(Fill(CONFIG.DATA.XLEN - 12, inst(31)), inst(31, 20))
  val immS       = Cat(Fill(CONFIG.DATA.XLEN - 12, inst(31)), inst(31, 25), inst(11, 7))
  val immB       = Cat(Fill(CONFIG.DATA.XLEN - 12, inst(31)), inst(7), inst(30, 25), inst(11, 8), 0.U(1.W))
  val immU       = if (CONFIG.RISCV.RV64) Cat(Fill(32, inst(31)), Cat(inst(31, 12), Fill(12, 0.U))) else Cat(inst(31, 12), Fill(12, 0.U))
  val immJ       = Cat(Fill(CONFIG.DATA.XLEN - 20, inst(31)), inst(31), inst(19, 12), inst(20), inst(30, 21), Fill(1, 0.U))
  val immSAMT    = Cat(Fill(CONFIG.DATA.XLEN - 6, 0.U), inst(25, 20))
  val stallDelay = RegNext(ioCtrl.pipe.stall)
  val lastInst   = RegInit(0.U(CONFIG.INST.WIDTH.W))

  lastInst := Mux(!stallDelay, ioIMemIDU.inst, lastInst)
  inst     := Mux(!ioIFU.valid, 0.U, Mux(stallDelay, lastInst, ioIMemIDU.inst))

  val rs1En :: rs2En :: rdEn :: op1Type :: op2Type :: aluCtrl :: immType :: branchCtrl :: memCtrl :: csrCtrl :: Nil =
    ListLookup(inst, ALL.defaultList, ALL.instList)

  immData := MuxCase(
    0.U(CONFIG.DATA.XLEN.W),
    Seq(
      (immType === imm.I) -> immI,
      (immType === imm.B) -> immB,
      (immType === imm.J) -> immJ,
      (immType === imm.U) -> immU,
      (immType === imm.S) -> immS,
      (immType === imm.R) -> immR,
      (immType === imm.S) -> immS,
      (immType === imm.N) -> immN
    )
  )
  rs1Data := Mux(ioHazardIDU.ioRS1.needPass, ioHazardIDU.ioRS1.data, ioRegIDU.ioRS1.data)
  rs2Data := Mux(ioHazardIDU.ioRS2.needPass, ioHazardIDU.ioRS2.data, ioRegIDU.ioRS2.data)

  // io
  ioRegIDU.ioRS1.en   := rs1En
  ioRegIDU.ioRS2.en   := rs2En
  ioRegIDU.ioRS1.addr := rs1Addr
  ioRegIDU.ioRS2.addr := rs2Addr

  ioHazardIDU.ioRS1.en   := rs1En
  ioHazardIDU.ioRS2.en   := rs2En
  ioHazardIDU.ioRS1.addr := rs1Addr
  ioHazardIDU.ioRS2.addr := rs2Addr

  ioCtrl.stallReq := ioHazardIDU.needStall

  ioIDU.aluCtrl    := aluCtrl
  ioIDU.branchCtrl := branchCtrl
  ioIDU.csrCtrl    := csrCtrl
  ioIDU.immData    := immData
  ioIDU.memCtrl    := memCtrl
  ioIDU.op1Type    := op1Type
  ioIDU.op2Type    := op2Type
  ioIDU.pc         := ioIFU.pc
  ioIDU.rdAddr     := rdAddr
  ioIDU.rdEn       := rdEn
  ioIDU.rs1Data    := rs1Data
  ioIDU.rs2Data    := rs2Data
  // for npc
  ioIDU.rs1Addr := rs1Addr
  ioIDU.rs2Addr := rs2Addr
  ioIDU.inst    := inst
}
