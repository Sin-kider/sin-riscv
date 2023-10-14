package riscv.Config

import chisel3._
import chisel3.util._

object CONFIG {
  object RISCV {
    val RV64 = true
    val RVE  = false
    val RVC  = true
  }
  object ADDR {
    val BASE  = 0x7fff_fffcL
    val WIDTH = if (RISCV.RV64) 64 else 32
  }
  object INST {
    val WIDTH   = 32
    val C_WIDTH = if (RISCV.RVC) 16 else 32
  }
  object REG {
    val NUM       = if (RISCV.RVE) 16 else 32
    val NUM_WIDTH = log2Ceil(NUM)
  }
}
