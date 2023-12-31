package riscv.IDU.module

import chisel3._
import chisel3.util._

object RV32I {
  object INST {
    // R & R
    val ADD  = BitPat("b0000000_?????_?????_000_?????_01100_11")
    val SUB  = BitPat("b0100000_?????_?????_000_?????_01100_11")
    val SLL  = BitPat("b0000000_?????_?????_001_?????_01100_11")
    val SLT  = BitPat("b0000000_?????_?????_010_?????_01100_11")
    val SLTU = BitPat("b0000000_?????_?????_011_?????_01100_11")
    val XOR  = BitPat("b0000000_?????_?????_100_?????_01100_11")
    val SRL  = BitPat("b0000000_?????_?????_101_?????_01100_11")
    val SRA  = BitPat("b0100000_?????_?????_101_?????_01100_11")
    val OR   = BitPat("b0000000_?????_?????_110_?????_01100_11")
    val AND  = BitPat("b0000000_?????_?????_111_?????_01100_11")
    // R & I
    val ADDI  = BitPat("b???????_?????_?????_000_?????_00100_11")
    val SLTI  = BitPat("b???????_?????_?????_010_?????_00100_11")
    val SLTIU = BitPat("b???????_?????_?????_011_?????_00100_11")
    val XORI  = BitPat("b???????_?????_?????_100_?????_00100_11")
    val ORI   = BitPat("b???????_?????_?????_110_?????_00100_11")
    val ANDI  = BitPat("b???????_?????_?????_111_?????_00100_11")
    val SLLI  = BitPat("b000000?_?????_?????_001_?????_00100_11")
    val SRLI  = BitPat("b000000?_?????_?????_101_?????_00100_11")
    val SRAI  = BitPat("b010000?_?????_?????_101_?????_00100_11")
    // Branch
    val BEQ  = BitPat("b???????_?????_?????_???_?????_11000_11")
    val BNE  = BitPat("b???????_?????_?????_001_?????_11000_11")
    val BLT  = BitPat("b???????_?????_?????_100_?????_11000_11")
    val BGE  = BitPat("b???????_?????_?????_101_?????_11000_11")
    val BLTU = BitPat("b???????_?????_?????_110_?????_11000_11")
    val BGEU = BitPat("b???????_?????_?????_111_?????_11000_11")
    // Load & Store
    val LB  = BitPat("b???????_?????_?????_000_?????_00000_11")
    val LH  = BitPat("b???????_?????_?????_001_?????_00000_11")
    val LW  = BitPat("b???????_?????_?????_010_?????_00000_11")
    val LBU = BitPat("b???????_?????_?????_100_?????_00000_11")
    val LHU = BitPat("b???????_?????_?????_101_?????_00000_11")
    val SB  = BitPat("b???????_?????_?????_000_?????_01000_11")
    val SH  = BitPat("b???????_?????_?????_001_?????_01000_11")
    val SW  = BitPat("b???????_?????_?????_010_?????_01000_11")
    // Upper
    val LUI   = BitPat("b???????_?????_?????_???_?????_01101_11")
    val AUIPC = BitPat("b???????_?????_?????_???_?????_00101_11")
    // Jump
    val JAL  = BitPat("b???????_?????_?????_???_?????_11011_11")
    val JALR = BitPat("b???????_?????_?????_000_?????_11001_11")
    // Env
    val ECALL  = BitPat("b0000000_00000_00000_000_00000_11100_11")
    val EBREAK = BitPat("b0000000_00001_00000_000_00000_11100_11")
  }
  val table = Array(
    // (ALU) R & R (rd = rs1 ? rs2) (no imm)
    INST.ADD  -> List(rs1.EN, rs2.EN, rd.EN, op1.RS1, op2.RS2, alu.ADD, imm.R, branch.NOP, mem.NOP, csr.NOP),
    INST.SUB  -> List(rs1.EN, rs2.EN, rd.EN, op1.RS1, op2.RS2, alu.SUB, imm.R, branch.NOP, mem.NOP, csr.NOP),
    INST.SLL  -> List(rs1.EN, rs2.EN, rd.EN, op1.RS1, op2.RS2, alu.SLL, imm.R, branch.NOP, mem.NOP, csr.NOP),
    INST.SLT  -> List(rs1.EN, rs2.EN, rd.EN, op1.RS1, op2.RS2, alu.SLT, imm.R, branch.NOP, mem.NOP, csr.NOP),
    INST.SLTU -> List(rs1.EN, rs2.EN, rd.EN, op1.RS1, op2.RS2, alu.SLTU, imm.R, branch.NOP, mem.NOP, csr.NOP),
    INST.XOR  -> List(rs1.EN, rs2.EN, rd.EN, op1.RS1, op2.RS2, alu.XOR, imm.R, branch.NOP, mem.NOP, csr.NOP),
    INST.SRL  -> List(rs1.EN, rs2.EN, rd.EN, op1.RS1, op2.RS2, alu.SRL, imm.R, branch.NOP, mem.NOP, csr.NOP),
    INST.SRA  -> List(rs1.EN, rs2.EN, rd.EN, op1.RS1, op2.RS2, alu.SRA, imm.R, branch.NOP, mem.NOP, csr.NOP),
    INST.OR   -> List(rs1.EN, rs2.EN, rd.EN, op1.RS1, op2.RS2, alu.OR, imm.R, branch.NOP, mem.NOP, csr.NOP),
    INST.AND  -> List(rs1.EN, rs2.EN, rd.EN, op1.RS1, op2.RS2, alu.AND, imm.R, branch.NOP, mem.NOP, csr.NOP),
    // (ALU) R & I (rd = rs1 ? immI)
    INST.ADDI  -> List(rs1.EN, rs2.NOP, rd.EN, op1.RS1, op2.IMM, alu.ADD, imm.I, branch.NOP, mem.NOP, csr.NOP),
    INST.SLLI  -> List(rs1.EN, rs2.NOP, rd.EN, op1.RS1, op2.IMM, alu.SLL, imm.I, branch.NOP, mem.NOP, csr.NOP),
    INST.SLTI  -> List(rs1.EN, rs2.NOP, rd.EN, op1.RS1, op2.IMM, alu.SLT, imm.I, branch.NOP, mem.NOP, csr.NOP),
    INST.SLTIU -> List(rs1.EN, rs2.NOP, rd.EN, op1.RS1, op2.IMM, alu.SLTU, imm.I, branch.NOP, mem.NOP, csr.NOP),
    INST.XORI  -> List(rs1.EN, rs2.NOP, rd.EN, op1.RS1, op2.IMM, alu.XOR, imm.I, branch.NOP, mem.NOP, csr.NOP),
    INST.SRLI  -> List(rs1.EN, rs2.NOP, rd.EN, op1.RS1, op2.IMM, alu.SRL, imm.I, branch.NOP, mem.NOP, csr.NOP),
    INST.SRAI  -> List(rs1.EN, rs2.NOP, rd.EN, op1.RS1, op2.IMM, alu.SRA, imm.I, branch.NOP, mem.NOP, csr.NOP),
    INST.ORI   -> List(rs1.EN, rs2.NOP, rd.EN, op1.RS1, op2.IMM, alu.OR, imm.I, branch.NOP, mem.NOP, csr.NOP),
    INST.ANDI  -> List(rs1.EN, rs2.NOP, rd.EN, op1.RS1, op2.IMM, alu.AND, imm.I, branch.NOP, mem.NOP, csr.NOP),
    // (BRANCH) Branch (if (rs1 ? rs2) pc += immB)
    INST.BEQ  -> List(rs1.EN, rs2.EN, rd.NOP, op1.PC, op2.IMM, alu.ADD, imm.B, branch.BEQ, mem.NOP, csr.NOP),
    INST.BNE  -> List(rs1.EN, rs2.EN, rd.NOP, op1.PC, op2.IMM, alu.ADD, imm.B, branch.BNE, mem.NOP, csr.NOP),
    INST.BLT  -> List(rs1.EN, rs2.EN, rd.NOP, op1.PC, op2.IMM, alu.ADD, imm.B, branch.BLT, mem.NOP, csr.NOP),
    INST.BGE  -> List(rs1.EN, rs2.EN, rd.NOP, op1.PC, op2.IMM, alu.ADD, imm.B, branch.BGE, mem.NOP, csr.NOP),
    INST.BLTU -> List(rs1.EN, rs2.EN, rd.NOP, op1.PC, op2.IMM, alu.ADD, imm.B, branch.BLTU, mem.NOP, csr.NOP),
    INST.BGEU -> List(rs1.EN, rs2.EN, rd.NOP, op1.PC, op2.IMM, alu.ADD, imm.B, branch.BGEU, mem.NOP, csr.NOP),
    // (Mem) Load & Store (rd = M[rs1 + immI]) (M[rs1 + immS] = rs2)
    INST.LB  -> List(rs1.EN, rs2.NOP, rd.EN, op1.RS1, op2.IMM, alu.NOP, imm.I, branch.NOP, mem.LB, csr.NOP),
    INST.LH  -> List(rs1.EN, rs2.NOP, rd.EN, op1.RS1, op2.IMM, alu.NOP, imm.I, branch.NOP, mem.LH, csr.NOP),
    INST.LW  -> List(rs1.EN, rs2.NOP, rd.EN, op1.RS1, op2.IMM, alu.NOP, imm.I, branch.NOP, mem.LW, csr.NOP),
    INST.LBU -> List(rs1.EN, rs2.NOP, rd.EN, op1.RS1, op2.IMM, alu.NOP, imm.I, branch.NOP, mem.LBU, csr.NOP),
    INST.LHU -> List(rs1.EN, rs2.NOP, rd.EN, op1.RS1, op2.IMM, alu.NOP, imm.I, branch.NOP, mem.LHU, csr.NOP),
    INST.SB  -> List(rs1.EN, rs2.EN, rd.NOP, op1.RS1, op2.IMM, alu.NOP, imm.S, branch.NOP, mem.SB, csr.NOP),
    INST.SH  -> List(rs1.EN, rs2.EN, rd.NOP, op1.RS1, op2.IMM, alu.NOP, imm.S, branch.NOP, mem.SH, csr.NOP),
    INST.SW  -> List(rs1.EN, rs2.EN, rd.NOP, op1.RS1, op2.IMM, alu.NOP, imm.S, branch.NOP, mem.SW, csr.NOP),
    // (ALU) PC / ZREO & U (rd = 0 / pc + immU)
    INST.LUI   -> List(rs1.NOP, rs2.NOP, rd.EN, op1.ZERO, op2.IMM, alu.ADD, imm.U, branch.NOP, mem.NOP, csr.NOP),
    INST.AUIPC -> List(rs1.NOP, rs2.NOP, rd.EN, op1.PC, op2.IMM, alu.ADD, imm.U, branch.NOP, mem.NOP, csr.NOP),
    // (BRANCH) (rd = (pc + 4), pc += immJ) (pc = ~1 & (rs1 + immI), rd = pc + 4)
    INST.JAL  -> List(rs1.NOP, rs2.NOP, rd.EN, op1.PC, op2.IMM, alu.ADD, imm.J, branch.JAL, mem.NOP, csr.NOP),
    INST.JALR -> List(rs1.EN, rs2.NOP, rd.EN, op1.PC, op2.FORE, alu.ADD, imm.I, branch.JALR, mem.NOP, csr.NOP),
    // (Env)
    INST.EBREAK -> List(rs1.NOP, rs2.NOP, rd.NOP, op1.NOP, op2.NOP, alu.NOP, imm.N, branch.NOP, mem.NOP, csr.NOP),
    INST.ECALL  -> List(rs1.NOP, rs2.NOP, rd.NOP, op1.NOP, op2.NOP, alu.NOP, imm.N, branch.NOP, mem.NOP, csr.NOP)
  )
}
