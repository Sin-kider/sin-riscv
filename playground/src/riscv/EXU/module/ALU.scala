package riscv.EXU.module

import chisel3._
import chisel3.util._
import riscv.Config._
import riscv.IDU.module.alu

class ALU extends Module {
  val ioALU = IO(new ALUBundle())
  // result
  val result = WireDefault(0.U(CONFIG.DATA.XLEN.W))
  result := MuxCase(
    0.U,
    Seq(
      (ioALU.aluCtrl === alu.NOP)  -> 0.U,
      (ioALU.aluCtrl === alu.ADD)  -> (ioALU.op1 + ioALU.op2),
      (ioALU.aluCtrl === alu.SUB)  -> (ioALU.op1 - ioALU.op2),
      (ioALU.aluCtrl === alu.SLT)  -> (ioALU.op1.asSInt < ioALU.op2.asSInt).asUInt,
      (ioALU.aluCtrl === alu.SLTU) -> (ioALU.op1.asUInt < ioALU.op2.asUInt).asUInt,
      (ioALU.aluCtrl === alu.XOR)  -> (ioALU.op1 ^ ioALU.op2),
      (ioALU.aluCtrl === alu.OR)   -> (ioALU.op1 | ioALU.op2),
      (ioALU.aluCtrl === alu.AND)  -> (ioALU.op1 & ioALU.op2),
      (ioALU.aluCtrl === alu.SLL)  -> (ioALU.op1 << ioALU.op2(log2Ceil(CONFIG.DATA.XLEN) - 1, 0)),
      (ioALU.aluCtrl === alu.SRL)  -> (ioALU.op1 >> ioALU.op2(log2Ceil(CONFIG.DATA.XLEN) - 1, 0)),
      (ioALU.aluCtrl === alu.SRA)  -> (ioALU.op1.asSInt >> ioALU.op2).asUInt
    )
  )
  // io
  ioALU.result := result
}
