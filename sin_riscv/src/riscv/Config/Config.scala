package riscv.Config

import chisel3._
import chisel3.util._

object CONFIG {
  object RISCV {
    val RV64 = false
    val RVE  = false
    val RVC  = false
  }
  object ADDR {
    val BASE = 0x8000_0000L
    // val BASE  = 0x7fff_fffcL
    val WIDTH = if (RISCV.RV64) 64 else 32
  }
  object INST {
    val WIDTH   = 32
    val C_WIDTH = 16
  }
  object REG {
    val NUM       = if (RISCV.RVE) 16 else 32
    val WIDTH     = if (RISCV.RV64) 64 else 32
    val NUM_WIDTH = log2Ceil(NUM)
  }
  object DATA {
    val XLEN = if (RISCV.RV64) 64 else 32
    val B    = 8
    val H    = 2 * B
    val W    = 2 * H
    val D    = 2 * W
  }
  object AXI {
    val ADDR_WIDTH = ADDR.WIDTH
    val DATA_WIDTH = DATA.XLEN
  }
}
