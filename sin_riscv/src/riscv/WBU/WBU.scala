package riscv.WBU

import chisel3._
import chisel3.util._

import riscv.IDU.module._
import riscv.LSU.module._
import riscv.WBU.module._
import riscv.Pipe.module._

class WBU extends Module {
  val ioCtrl      = IO(new WBUCtrlBundle())
  val ioWBU       = IO(new WBUStageBundle())
  val ioLSU       = IO(Flipped(new LSUStageBundle()))
  val ioDMemWBU   = IO(Flipped(new dMemWBUBundle()))
  val ioRegWBU    = IO(Flipped(new regWBUBundle()))
  val ioHazardWBU = IO(Flipped(new hazardWBUBundle()))

  ioRegWBU.ioRD.en   := ioLSU.rdEn && ioLSU.valid
  ioRegWBU.ioRD.addr := ioLSU.rdAddr
  ioRegWBU.ioRD.data := Mux(ioLSU.isLoad, ioDMemWBU.data, ioLSU.ALUresult)

  ioHazardWBU.ioRD.en   := ioLSU.rdEn && ioLSU.valid
  ioHazardWBU.ioRD.addr := ioLSU.rdAddr
  ioHazardWBU.ioRD.data := Mux(ioLSU.isLoad, ioDMemWBU.data, ioLSU.ALUresult)

  ioWBU.pc        := ioLSU.pc
  ioWBU.rs1Data   := ioLSU.rs1Data
  ioWBU.rs1Addr   := ioLSU.rs1Addr
  ioWBU.rs2Data   := ioLSU.rs2Data
  ioWBU.rs2Addr   := ioLSU.rs2Addr
  ioWBU.rdAddr    := ioLSU.rdAddr
  ioWBU.ALUresult := ioLSU.ALUresult
  ioWBU.inst      := ioLSU.inst
}
