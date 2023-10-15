package riscv.IDU.module

import chisel3._
import chisel3.util._
import riscv.Config.CONFIG
import os.truncate

object rs1 {
  val NOP = false.B
  val EN  = true.B
}

object rs2 {
  val NOP = false.B
  val EN  = true.B
}

object rd {
  val NOP = false.B
  val EN  = true.B
}

object op1 {
  val WIDTH = log2Ceil(4)
  val NOP   = 0.U(WIDTH.W)
  val RS1   = 1.U(WIDTH.W)
  val PC    = 2.U(WIDTH.W)
  val ZERO  = 3.U(WIDTH.W) // lui
}

object op2 {
  val WIDTH = log2Ceil(4)
  val NOP   = 0.U(WIDTH.W)
  val RS2   = 1.U(WIDTH.W)
  val IMM   = 2.U(WIDTH.W)
  val FORE  = 3.U(WIDTH.W)
}

object imm {
  val WIDTH = log2Ceil(8)
  val N     = 0.U(WIDTH.W)
  val I     = 1.U(WIDTH.W)
  val S     = 2.U(WIDTH.W)
  val B     = 3.U(WIDTH.W)
  val U     = 4.U(WIDTH.W)
  val J     = 5.U(WIDTH.W)
  val R     = 6.U(WIDTH.W)
  val SHAMT = 7.U(WIDTH.W)
}

object alu {
  val WIDTH = log2Ceil(11)
  val NOP   = 0.U(WIDTH.W)
  val ADD   = 1.U(WIDTH.W)
  val SUB   = 2.U(WIDTH.W)
  val SLL   = 3.U(WIDTH.W)
  val SLT   = 4.U(WIDTH.W)
  val SLTU  = 5.U(WIDTH.W)
  val XOR   = 6.U(WIDTH.W)
  val OR    = 7.U(WIDTH.W)
  val AND   = 8.U(WIDTH.W)
  val SRL   = 9.U(WIDTH.W)
  val SRA   = 10.U(WIDTH.W)
}

object branch {
  val WIDTH = log2Ceil(9)
  val NOP   = 0.U(WIDTH.W)
  val BEQ   = 1.U(WIDTH.W)
  val BNE   = 2.U(WIDTH.W)
  val BLT   = 3.U(WIDTH.W)
  val BGE   = 4.U(WIDTH.W)
  val BLTU  = 5.U(WIDTH.W)
  val BGEU  = 6.U(WIDTH.W)
  val JAL   = 7.U(WIDTH.W)
  val JALR  = 8.U(WIDTH.W)
}

object mem {
  val WIDTH = log2Ceil(12)
  val NOP   = 0.U(WIDTH.W)
  val LB    = 1.U(WIDTH.W)
  val LBU   = 2.U(WIDTH.W)
  val LH    = 3.U(WIDTH.W)
  val LHU   = 4.U(WIDTH.W)
  val LW    = 5.U(WIDTH.W)
  val LWU   = 6.U(WIDTH.W)
  val LD    = 7.U(WIDTH.W)
  val SB    = 8.U(WIDTH.W)
  val SH    = 9.U(WIDTH.W)
  val SW    = 10.U(WIDTH.W)
  val SD    = 11.U(WIDTH.W)
}
