package riscv.IFU.module

import chisel3._
import chisel3.util._

import riscv.Util._
import riscv.Config.CONFIG

class iMemBundle extends Bundle {
  val valid = Input(Bool())
  val ready = Output(Bool())
  val addr  = Input(UInt(CONFIG.ADDR.WIDTH.W))
  val data  = Output(UInt(CONFIG.INST.WIDTH.W))
}

class IFUBundle extends Bundle {
  val ready = Input(Bool())
  val valid = Output(Bool())
  val inst  = Output(UInt(CONFIG.INST.WIDTH.W))
  val pc    = Output(UInt(CONFIG.ADDR.WIDTH.W))
  // // for test
  // val npc = Output(UInt(CONFIG.ADDR.WIDTH.W))
}
