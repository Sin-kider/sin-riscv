package riscv.IDU.module

import chisel3._
import chisel3.util._

import riscv.Config.CONFIG
import riscv.DPIC._

class regFile extends Module {
  val ioRegIDU = IO(new regIDUBundle())
  val ioRegWBU = IO(new regWBUBundle())

  // reg init
  val reg = RegInit(VecInit(Seq.fill(CONFIG.REG.NUM) { 0.U(CONFIG.REG.WIDTH.W) }))
  // read
  ioRegIDU.ioRS1.data := Mux(ioRegIDU.ioRS1.en, reg(ioRegIDU.ioRS1.addr), 0.U)
  ioRegIDU.ioRS2.data := Mux(ioRegIDU.ioRS2.en, reg(ioRegIDU.ioRS2.addr), 0.U)
  // write
  when(ioRegWBU.ioRD.en && (ioRegWBU.ioRD.addr =/= 0.U)) {
    reg(ioRegWBU.ioRD.addr) := ioRegWBU.ioRD.data
  }

  // for npc
  val DPICReg = Module(new DPICReg)
  DPICReg.io.clock   := clock
  DPICReg.io.reset   := reset
  DPICReg.io.rdEn    := ioRegWBU.ioRD.en
  DPICReg.io.rdAddr  := ioRegWBU.ioRD.addr
  DPICReg.io.rdData  := ioRegWBU.ioRD.data
  DPICReg.io.rs1En   := ioRegIDU.ioRS1.en
  DPICReg.io.rs1Addr := ioRegIDU.ioRS1.addr
  DPICReg.io.rs2En   := ioRegIDU.ioRS2.en
  DPICReg.io.rs2Addr := ioRegIDU.ioRS2.addr
}
