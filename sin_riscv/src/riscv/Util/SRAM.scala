package riscv.Util

import chisel3._
import chisel3.util._

import riscv.DPIC._
import riscv.Util.module._

class ISRAM extends Module {
  val ioAXI = IO(new AXILiteSlave())

  val DPICSRAM = Module(new DPICSRAM())
  DPICSRAM.io.clock := clock
  DPICSRAM.io.reset := reset
  ioAXI             <> DPICSRAM.io.ioAXI
}

class DSRAM extends Module {
  val ioAXI = IO(new AXILiteSlave())

  val DPICSRAM = Module(new DPICSRAM())
  DPICSRAM.io.clock := clock
  DPICSRAM.io.reset := reset
  ioAXI             <> DPICSRAM.io.ioAXI
}
