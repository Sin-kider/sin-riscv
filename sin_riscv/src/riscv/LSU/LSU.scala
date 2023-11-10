package riscv.LSU

import chisel3._
import chisel3.util._

import riscv.IDU.module._
import riscv.EXU.module._
import riscv.LSU.module._
import riscv.Pipe.module._
import riscv.Config.CONFIG

class LSU extends Module {
  val ioCtrl      = IO(new LSUCtrlBundle())
  val ioLSU       = IO(new LSUStageBundle())
  val ioEXU       = IO(Flipped(new EXUStageBundle()))
  val ioDMemLSU   = IO(Flipped(new dMemLSUBundle()))
  val ioHazardLSU = IO(Flipped(new hazardLSUBundle()))

  val readEn   = WireDefault(false.B)
  val writeEn  = WireDefault(false.B)
  val isSigned = WireDefault(false.B)
  val dataLen  = WireDefault(len.NOP)

  readEn := (ioEXU.memCtrl === mem.LB) ||
    (ioEXU.memCtrl === mem.LBU) ||
    (ioEXU.memCtrl === mem.LH) ||
    (ioEXU.memCtrl === mem.LHU) ||
    (ioEXU.memCtrl === mem.LW) ||
    (ioEXU.memCtrl === mem.LWU) ||
    (ioEXU.memCtrl === mem.LD)

  writeEn := (ioEXU.memCtrl === mem.SB) ||
    (ioEXU.memCtrl === mem.SH) ||
    (ioEXU.memCtrl === mem.SW) ||
    (ioEXU.memCtrl === mem.SD)

  if (CONFIG.RISCV.RV64) {
    isSigned := (ioEXU.memCtrl === mem.LB) ||
      (ioEXU.memCtrl === mem.LH) ||
      (ioEXU.memCtrl === mem.LW)
  } else {
    isSigned := (ioEXU.memCtrl === mem.LB) ||
      (ioEXU.memCtrl === mem.LH)
  }

  dataLen := MuxCase(
    len.NOP,
    Seq(
      (ioEXU.memCtrl === mem.LB || ioEXU.memCtrl === mem.LBU || ioEXU.memCtrl === mem.SB) -> len.B,
      (ioEXU.memCtrl === mem.LH || ioEXU.memCtrl === mem.LHU || ioEXU.memCtrl === mem.SH) -> len.H,
      (ioEXU.memCtrl === mem.LW || ioEXU.memCtrl === mem.LWU || ioEXU.memCtrl === mem.SW) -> len.W,
      (ioEXU.memCtrl === mem.LD || ioEXU.memCtrl === mem.SD)                              -> len.D,
      (ioEXU.memCtrl === mem.NOP)                                                         -> len.NOP
    )
  )
  // io
  ioDMemLSU.readEn   := readEn
  ioDMemLSU.writeEn  := writeEn
  ioDMemLSU.isSigned := isSigned
  ioDMemLSU.dataLen  := dataLen
  ioDMemLSU.data     := ioEXU.rs2Data
  ioDMemLSU.addr     := ioEXU.ALUResult

  ioCtrl.stallReq := (readEn || writeEn) && !ioDMemLSU.ready

  ioLSU.valid  := (!readEn && !writeEn) || ioDMemLSU.ready
  ioLSU.rdEn   := ioEXU.rdEn
  ioLSU.rdAddr := ioEXU.rdAddr

  ioHazardLSU.ioRD.en   := ioEXU.rdEn
  ioHazardLSU.ioRD.addr := ioEXU.rdAddr
  ioHazardLSU.ioRD.data := ioEXU.ALUResult
  ioHazardLSU.isLoad    := readEn
  // for npc
  ioLSU.pc        := ioEXU.pc
  ioLSU.rs1Data   := ioEXU.rs1Data
  ioLSU.rs1Addr   := ioEXU.rs1Addr
  ioLSU.rs2Data   := ioEXU.rs2Data
  ioLSU.rs2Addr   := ioEXU.rs2Addr
  ioLSU.ALUresult := ioEXU.ALUResult
  ioLSU.inst      := ioEXU.inst
  ioLSU.isLoad    := readEn
}
