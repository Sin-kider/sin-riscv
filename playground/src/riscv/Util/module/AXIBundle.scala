package riscv.Util.module

import chisel3._
import chisel3.util._

import riscv.Config._
import riscv.Util._

class AXILite_AW extends Bundle {
  val valid = Output(Bool())
  val ready = Input(Bool())
  val addr  = Output(UInt(CONFIG.AXI.ADDR_WIDTH.W))
  val prot  = Output(UInt(3.W))
}

class AXILite_W extends Bundle {
  val valid = Output(Bool())
  val ready = Input(Bool())
  val data  = Output(UInt(CONFIG.AXI.DATA_WIDTH.W))
  val strb  = Output(UInt((log2Ceil(CONFIG.AXI.DATA_WIDTH) - 1).W))
}

class AXILite_AR extends Bundle {
  val valid = Output(Bool())
  val ready = Input(Bool())
  val addr  = Output(UInt(CONFIG.AXI.ADDR_WIDTH.W))
  val prot  = Output(UInt(3.W))
}

class AXILite_R extends Bundle {
  val valid = Output(Bool())
  val ready = Input(Bool())
  val data  = Output(UInt(CONFIG.AXI.DATA_WIDTH.W))
  val resp  = Output(UInt(2.W))
}

class AXILite_B extends Bundle {
  val valid = Output(Bool())
  val ready = Input(Bool())
  val resp  = Output(UInt(2.W))
}

class AXILiteSlave extends Bundle {
  val aw = Flipped(new AXILite_AW())
  val w  = Flipped(new AXILite_W())
  val ar = Flipped(new AXILite_AR())
  val r  = new AXILite_R()
  val b  = new AXILite_B()
}

class AXILiteMaster extends Bundle {
  val aw = new AXILite_AW()
  val w  = new AXILite_W()
  val ar = new AXILite_AR()
  val r  = Flipped(new AXILite_R())
  val b  = Flipped(new AXILite_B())
}
