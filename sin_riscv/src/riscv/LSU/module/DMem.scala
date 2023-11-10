package riscv.LSU.module

import chisel3._
import chisel3.util._

import riscv.IDU.module._
import riscv.LSU.module._
import riscv.Config.CONFIG
import riscv.Util.module._

class dMem extends Module {
  val ioDMemLSU = IO(new dMemLSUBundle())
  val ioDMemWBU = IO(new dMemWBUBundle())
  val ioAXI     = IO(new AXILiteMaster())

  val stateIdle :: stateRead :: stateWrite :: stateFinish :: Nil = Enum(4)

  val state   = RegInit(stateIdle)
  val dataOut = WireDefault(0.U(CONFIG.DATA.XLEN.W))
  val wstrb   = WireDefault(0.U((log2Ceil(CONFIG.AXI.DATA_WIDTH) - 1).W))
  val ready   = WireDefault(false.B)

  switch(state) {
    is(stateIdle) {
      when(ioAXI.aw.ready && ioAXI.w.ready && ioDMemLSU.writeEn) {
        state := stateWrite
      }
      when(ioAXI.ar.ready && ioDMemLSU.readEn) {
        state := stateRead
      }
    }
    is(stateRead) {
      when(ioAXI.r.valid) {
        state := stateFinish
      }
    }
    is(stateWrite) {
      when(ioAXI.b.valid) {
        state := stateFinish
      }
    }
    is(stateFinish) {
      state := stateIdle
    }
  }

  if (CONFIG.RISCV.RV64) {
    wstrb := MuxCase(
      0.U,
      Seq(
        (ioDMemLSU.dataLen === len.NOP) -> ("b0000_0000".U << ioDMemLSU.addr(2, 0)),
        (ioDMemLSU.dataLen === len.B)   -> ("b0000_0001".U << ioDMemLSU.addr(2, 0)),
        (ioDMemLSU.dataLen === len.H)   -> ("b0000_0011".U << ioDMemLSU.addr(2, 0)),
        (ioDMemLSU.dataLen === len.W)   -> ("b0000_1111".U << ioDMemLSU.addr(2, 0)),
        (ioDMemLSU.dataLen === len.D)   -> ("b1111_1111".U)
      )
    )
  } else {
    wstrb := MuxCase(
      0.U,
      Seq(
        (ioDMemLSU.dataLen === len.NOP) -> ("b0000".U << ioDMemLSU.addr(2, 0)),
        (ioDMemLSU.dataLen === len.B)   -> ("b0001".U << ioDMemLSU.addr(2, 0)),
        (ioDMemLSU.dataLen === len.H)   -> ("b0011".U << ioDMemLSU.addr(2, 0)),
        (ioDMemLSU.dataLen === len.W)   -> ("b1111".U)
      )
    )
  }

  dataOut := ioAXI.r.data
  ready := ((state === stateIdle) && !ioDMemLSU.readEn && !ioDMemLSU.writeEn) ||
    // ((state === stateWrite) && ioAXI.b.valid) ||
    // ((state === stateRead) && ioAXI.r.valid) ||
    (state === stateFinish)

  ioAXI.aw.valid  := (state === stateIdle) && ioDMemLSU.writeEn
  ioAXI.aw.addr   := ioDMemLSU.addr
  ioAXI.aw.prot   := 0.U
  ioAXI.w.valid   := (state === stateIdle) && ioDMemLSU.writeEn
  ioAXI.w.data    := ioDMemLSU.data
  ioAXI.w.strb    := wstrb
  ioAXI.ar.valid  := (state === stateIdle) && ioDMemLSU.readEn
  ioAXI.ar.addr   := ioDMemLSU.addr
  ioAXI.ar.prot   := 0.U
  ioAXI.r.ready   := (state === stateRead)
  ioAXI.b.ready   := (state === stateWrite)
  ioDMemWBU.data  := dataOut
  ioDMemLSU.ready := ready
}
