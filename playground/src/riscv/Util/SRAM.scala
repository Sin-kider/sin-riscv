package riscv.Util

import chisel3._
import chisel3.util._

import riscv.Util.module._
import riscv.DPIC._

class ISRAM extends Module {
  val ioISRAM = IO(new AXILiteSlave())

  val DPICSRAM = Module(new DPICSRAM())
  DPICSRAM.io.clock := clock
  DPICSRAM.io.reset := reset
  ioISRAM <> DPICSRAM.io.ioAXI
}

class DSRAM extends Module {
  val ioDSRAM = IO(new AXILiteSlave())

  val DPICSRAM = Module(new DPICSRAM())
  DPICSRAM.io.clock := clock
  DPICSRAM.io.reset := reset
  ioDSRAM <> DPICSRAM.io.ioAXI
}
