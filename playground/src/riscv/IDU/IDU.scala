package riscv.IDU

import chisel3._
import chisel3.util._

import riscv.IDU.module._
import riscv.IFU.module._
import riscv.Util.module._
import riscv.Config.CONFIG

class IDU extends Module {
  val ioIDU = IO(new IDUBundle())
  val ioIFU = IO(Flipped(new IFUBundle()))
  val ioLC  = IO(Flipped(new logicCtrlIDUBundle()))
  val ioRS1 = IO(Flipped(new regIORead()))
  val ioRS2 = IO(Flipped(new regIORead()))

  // rd imm
  val rdAddr  = ioIFU.inst(6 + CONFIG.REG.NUM_WIDTH, 7)
  val rs1Addr = ioIFU.inst(14 + CONFIG.REG.NUM_WIDTH, 15)
  val rs2Addr = ioIFU.inst(19 + CONFIG.REG.NUM_WIDTH, 20)
  val immR    = 0.U(CONFIG.DATA.XLEN.W)
  val immN    = 0.U(CONFIG.DATA.XLEN.W)
  val immI    = Cat(Fill(CONFIG.DATA.XLEN - 12, ioIFU.inst(31)), ioIFU.inst(31, 20))
  val immS    = Cat(Fill(CONFIG.DATA.XLEN - 12, ioIFU.inst(31)), ioIFU.inst(31, 25), ioIFU.inst(11, 7))
  val immB    = Cat(Fill(CONFIG.DATA.XLEN - 12, ioIFU.inst(31)), ioIFU.inst(7), ioIFU.inst(30, 25), ioIFU.inst(11, 8), 0.U(1.W))
  val immU    = if (CONFIG.RISCV.RV64) Cat(Fill(32, ioIFU.inst(31)), Cat(ioIFU.inst(31, 12), Fill(12, 0.U))) else Cat(ioIFU.inst(31, 12), Fill(12, 0.U))
  val immJ    = Cat(Fill(CONFIG.DATA.XLEN - 20, ioIFU.inst(31)), ioIFU.inst(31), ioIFU.inst(19, 12), ioIFU.inst(20), ioIFU.inst(30, 21), Fill(1, 0.U))
  val immSAMT = Cat(Fill(CONFIG.DATA.XLEN - 6, 0.U), ioIFU.inst(25, 20))
  val regEn   = ioIFU.valid

  // decode
  val rs1En :: rs2En :: rdEn :: op1Type :: op2Type :: aluCtrl :: immType :: branchCtrl :: memCtrl :: csrCtrl :: Nil =
    ListLookup(ioIFU.inst, ALL.defaultList, ALL.instList)

  // reg
  val rs1Data = WireDefault(0.U(CONFIG.REG.WIDTH.W))
  val rs2Data = WireDefault(0.U(CONFIG.REG.WIDTH.W))
  val rdData  = WireDefault(0.U(CONFIG.REG.WIDTH.W))

  ioRS1.en   := rs1En
  ioRS1.addr := rs1Addr
  ioRS2.en   := rs2En
  ioRS2.addr := rs2Addr
  rs1Data    := ioRS1.data
  rs2Data    := ioRS2.data

  // imm
  val immData = WireDefault(0.U(CONFIG.DATA.XLEN.W))
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
  // io
  ioIDU.rs1Data    := rs1Data
  ioIDU.rs2Data    := rs2Data
  ioIDU.rdEn       := RegEnable(rdEn, rd.NOP, regEn)
  ioIDU.op1Type    := RegEnable(op1Type, op1.NOP, regEn)
  ioIDU.op2Type    := RegEnable(op2Type, op2.NOP, regEn)
  ioIDU.immData    := RegEnable(immData, 0.U, regEn)
  ioIDU.aluCtrl    := RegEnable(aluCtrl, alu.NOP, regEn)
  ioIDU.branchCtrl := RegEnable(branchCtrl, branch.NOP, regEn)
  ioIDU.memCtrl    := RegEnable(memCtrl, mem.NOP, regEn)
  ioIDU.csrCtrl    := RegEnable(csrCtrl, csr.NOP, regEn)
  ioIDU.valid      := RegNext(regEn, false.B)
  ioIDU.inst       := RegEnable(ioIFU.inst, 0.U, regEn)
  ioIDU.pc         := RegEnable(ioIFU.pc, CONFIG.ADDR.BASE.U, regEn)

  // for test
  ioIDU.rs1Addr := rs1Addr
  ioIDU.rs2Addr := rs2Addr
  ioIDU.rdAddr  := rdAddr
}
