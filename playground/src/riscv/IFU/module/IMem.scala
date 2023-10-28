package riscv.IFU.module

import chisel3._
import chisel3.util._

import riscv.Util._
import riscv.Util.module._
import riscv.Config.CONFIG
import riscv.Tool.Tools

class iMem extends Module {
  val ioIMem = IO(new iMemBundle())
  val ioAXI  = IO(new AXILiteMaster())

  // 忽略AXI-lite写请求和响应
  Tools.ignoreBundle(ioAXI.aw)
  Tools.ignoreBundle(ioAXI.w)
  Tools.ignoreBundle(ioAXI.b)

  // 读取iMem的状态机定义
  val stateIdle :: stateRead :: Nil = Enum(2)
  val state                         = RegInit(stateIdle)
  val readEn                        = WireDefault(false.B)
  val readFinish                    = WireDefault(false.B)

  readEn     := (state === stateIdle) & ioIMem.valid
  readFinish := (state === stateRead) & ioAXI.r.valid

  when(state === stateIdle) {
    when(ioAXI.ar.ready) {
      state := stateRead
    }
  }
  when(state === stateRead) {
    when(ioAXI.r.valid) {
      state := stateIdle
    }
  }

  // io
  ioAXI.ar.valid := readEn
  ioAXI.ar.addr  := ioIMem.addr
  ioIMem.ready   := readFinish
  ioIMem.data    := ioAXI.r.data
  ioAXI.ar.prot  := 0.U
  ioAXI.r.ready  := (state === stateRead)
}
