package riscv.LSU.module

import chisel3._
import chisel3.util._

class LSUBundle extends Bundle {
  val valid = Output(Bool())

}
