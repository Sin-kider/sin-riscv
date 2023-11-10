package riscv.IFU.module

import chisel3._
import chisel3.util._

import riscv.Config.CONFIG
import riscv.Util._
import riscv.Util.module._
import riscv.Tool.Tools

class iMem extends Module {
  val ioIMemIFU = IO(new iMemIFUBundle())
  val ioIMemIDU = IO(new iMemIDUBundle())
  val ioAXI     = IO(new AXILiteMaster())

  // 忽略AXI-lite写请求和响应
  Tools.ignoreBundle(ioAXI.aw)
  Tools.ignoreBundle(ioAXI.w)
  Tools.ignoreBundle(ioAXI.b)

  // 读取iMem的状态机定义
  val stateIdle :: stateRead :: stateFinish :: Nil = Enum(3)
  val state                                        = RegInit(stateIdle)
  val readEn                                       = WireDefault(false.B)
  val readFinish                                   = WireDefault(false.B)

  readEn     := (state === stateIdle) && ioIMemIFU.valid
  readFinish := (state === stateRead) && ioAXI.r.valid

  switch(state) {
    is(stateIdle) {
      when(ioAXI.ar.ready) {
        state := stateRead
      }
    }
    is(stateRead) {
      when(ioAXI.r.valid) {
        state := stateIdle
      }
    }
  }

  // io
  ioAXI.ar.valid  := readEn
  ioAXI.ar.addr   := ioIMemIFU.pc
  ioIMemIFU.ready := readFinish
  ioIMemIDU.inst  := ioAXI.r.data
  ioAXI.ar.prot   := 0.U
  ioAXI.r.ready   := (state === stateRead)
}
