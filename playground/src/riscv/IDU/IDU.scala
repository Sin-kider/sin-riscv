package riscv.IDU

import chisel3._
import chisel3.util._

import riscv.IDU.module._
import riscv.IFU.module._
import riscv.Util.module._
import riscv.Config.CONFIG

class IDU extends Module {
  val ioIDU = IO(new IDUBundle)
  val ioIFU = IO(Flipped(new IFUBundle))
  val ioLC  = IO(Flipped(new logicCtrlIDUBundle))

  // rd imm
  // val rdAddr  = ioIFU.inst(11, 7)
  // val rs1Addr = ioIFU.inst(19, 15)
  // val rs2Addr = ioIFU.inst(24, 20)
  // val immI    = Cat(Fill(CONFIG.DATA.XLEN - 12, ioIFU.inst(31)), ioIFU.inst(31, 20))
  // val immS    = Cat(Fill(CONFIG.DATA.XLEN - 12, ioIFU.inst(31)), ioIFU.inst(31, 25), ioIFU.inst(11, 7))
  // val immB    = Cat(Fill(CONFIG.DATA.XLEN - 12, ioIFU.inst(31)), ioIFU.inst(7), ioIFU.inst(30, 25), ioIFU.inst(11, 8), 0.U(1.W))
  // val immU    = Cat(Cat(Fill(CONFIG.DATA.XLEN - 32, ioIFU.inst(31)), ioIFU.inst(31, 12)), Fill(12, 0.U))
  // val immJ    = Cat(Fill(CONFIG.DATA.XLEN - 20, ioIFU.inst(31)), ioIFU.inst(31), ioIFU.inst(19, 12), ioIFU.inst(20), ioIFU.inst(30, 21), Fill(1, 0.U))
  // val immSAMT = Cat(Fill(CONFIG.DATA.XLEN - 6, 0.U), ioIFU.inst(25, 20))

  // // decode
  // val rs1En :: rs2En :: rdEn :: op1Type :: op2Type :: immType :: aluCtrl :: branchCtrl :: memCtrl :: csrCtrl :: Nil =
  //   ListLookup(ioIFU.inst, ALL.defaultList, ALL.instList)

  // // reg
  // val rs1Data = WireDefault(0.U(CONFIG.REG.WIDTH.W))
  // val rs2Data = WireDefault(0.U(CONFIG.REG.WIDTH.W))
  // val rdData  = WireDefault(0.U(CONFIG.REG.WIDTH.W))

  // val regFile = Module(new regFile())
  // regFile.ioRegFile.ioRS1.en   := rs1En
  // regFile.ioRegFile.ioRS1.addr := rs1Addr
  // regFile.ioRegFile.ioRS2.en   := rs2En
  // regFile.ioRegFile.ioRS2.addr := rs2Addr
  // rs1Data                      := regFile.ioRegFile.ioRS1.data
  // rs2Data                      := regFile.ioRegFile.ioRS2.data

  // // io
  // ioIDU.rs1Data := rs1Data
  // ioIDU.rs2Data := rs2Data

}
