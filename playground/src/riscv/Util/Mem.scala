package riscv.Util

import chisel3._
import chisel3.util._

import riscv.Util.module._
import riscv.DPIC._

class IMem extends Module {
  val io = IO(new IMemBundle)
  io.state.valid := true.B
  // imem
  val dpicGetInst = Module(new dpicGetInst)
  dpicGetInst.io.clock := clock
  dpicGetInst.io.reset := reset
  dpicGetInst.io.addr  := io.addr
  io.data              := dpicGetInst.io.data
}
