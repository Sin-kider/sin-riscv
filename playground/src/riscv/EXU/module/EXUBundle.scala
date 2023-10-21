package riscv.EXU.module

import chisel3._
import chisel3.util._

class EXUBundle extends Bundle {
  val valid = Output(Bool())
}
