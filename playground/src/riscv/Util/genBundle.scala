package riscv.Util

import chisel3._
import chisel3.util._

class stateBundle extends Bundle {
  val valid = Output(Bool()) // Data validity
  val ready = Input(Bool()) // Ready to receive data
}
