package riscv.IDU.module

import chisel3._
import chisel3.util._
import riscv.Config.CONFIG

class regFile extends Module {
  val ioRegFile = IO(new regFileBundle())

  // reg init
  val reg = RegInit(VecInit(Seq.fill(CONFIG.REG.NUM) { 0.U(CONFIG.REG.WIDTH.W) }))
  // read
  ioRegFile.ioRS1.data := Mux(ioRegFile.ioRS1.en, reg(ioRegFile.ioRS1.addr), 0.U)
  ioRegFile.ioRS2.data := Mux(ioRegFile.ioRS2.en, reg(ioRegFile.ioRS2.addr), 0.U)
  // write
  when(ioRegFile.ioRD.en && ioRegFile.ioRD.addr =/= 0.U) {
    reg(ioRegFile.ioRD.addr) := ioRegFile.ioRD.data
  }
}
