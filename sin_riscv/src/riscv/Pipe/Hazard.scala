package riscv.Pipe

import chisel3._
import chisel3.util._

import riscv.Pipe.module._
import riscv.Config.CONFIG

class hazard extends Module {
  val ioIDU = IO(new hazardIDUBundle())
  val ioEXU = IO(new hazardEXUBundle())
  val ioLSU = IO(new hazardLSUBundle())
  val ioWBU = IO(new hazardWBUBundle())

  val rs1NeedPass = WireDefault(false.B)
  val rs2NeedPass = WireDefault(false.B)

  val rs1NewData = WireDefault(0.U(CONFIG.REG.WIDTH.W))
  val rs2NewData = WireDefault(0.U(CONFIG.REG.WIDTH.W))

  val rs1NeedStall = WireDefault(false.B)
  val rs2NeedStall = WireDefault(false.B)

  when(ioIDU.ioRS1.en && (ioIDU.ioRS1.addr =/= 0.U)) {
    when(ioEXU.ioRD.en && (ioIDU.ioRS1.addr === ioEXU.ioRD.addr)) {
      rs1NeedPass  := true.B
      rs1NewData   := ioEXU.ioRD.data
      rs1NeedStall := ioEXU.isLoad
    }.elsewhen(ioLSU.ioRD.en && (ioIDU.ioRS1.addr === ioLSU.ioRD.addr)) {
      rs1NeedPass  := true.B
      rs1NewData   := ioLSU.ioRD.data
      rs1NeedStall := ioLSU.isLoad
    }.elsewhen(ioWBU.ioRD.en && (ioIDU.ioRS1.addr === ioWBU.ioRD.addr)) {
      rs1NeedPass  := true.B
      rs1NewData   := ioWBU.ioRD.data
      rs1NeedStall := false.B
    }.otherwise {
      rs1NeedPass  := false.B
      rs1NewData   := 0.U
      rs1NeedStall := false.B
    }
  }

  when(ioIDU.ioRS2.en && (ioIDU.ioRS2.addr =/= 0.U)) {
    when(ioEXU.ioRD.en && (ioIDU.ioRS2.addr === ioEXU.ioRD.addr)) {
      rs2NeedPass  := true.B
      rs2NewData   := ioEXU.ioRD.data
      rs2NeedStall := ioEXU.isLoad
    }.elsewhen(ioLSU.ioRD.en && (ioIDU.ioRS2.addr === ioLSU.ioRD.addr)) {
      rs2NeedPass  := true.B
      rs2NewData   := ioLSU.ioRD.data
      rs2NeedStall := ioLSU.isLoad
    }.elsewhen(ioWBU.ioRD.en && (ioIDU.ioRS2.addr === ioWBU.ioRD.addr)) {
      rs2NeedPass  := true.B
      rs2NewData   := ioWBU.ioRD.data
      rs2NeedStall := false.B
    }.otherwise {
      rs2NeedPass  := false.B
      rs1NewData   := 0.U
      rs2NeedStall := false.B
    }
  }

  ioIDU.ioRS1.needPass := rs1NeedPass
  ioIDU.ioRS2.needPass := rs2NeedPass
  ioIDU.ioRS1.data     := rs1NewData
  ioIDU.ioRS2.data     := rs2NewData
  ioIDU.needStall      := rs1NeedStall || rs2NeedStall
}
