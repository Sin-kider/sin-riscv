package riscv.EXU

import chisel3._
import chisel3.util._
import riscv.EXU.module._
import riscv.IDU.module._

class EXU extends Module {
  val ioEXU = IO(new EXUBundle())
  val ioIDU = IO(Flipped(new IDUBundle))

}
