package riscv.Pipe.module

import chisel3._
import chisel3.util._

import riscv.Config.CONFIG

// 流水线控制信号IO
class pipeRegCtrlBundle extends Bundle {
  val flush     = Input(Bool())
  val stallPrev = Input(Bool())
  val stallNext = Input(Bool())
}

// 各级流水信号IO父类
class StageBundle extends Bundle {
  def initVal() = 0.U.asTypeOf(this)
}

class pipeCtrlBundle extends Bundle {
  val flush = Input(Bool())
  val stall = Input(Bool())
}

class hazardIDUBundle extends Bundle {
  val ioRS1 = new Bundle {
    val en       = Input(Bool())
    val addr     = Input(UInt(CONFIG.REG.NUM_WIDTH.W))
    val needPass = Output(Bool())
    val data     = Output(UInt(CONFIG.REG.WIDTH.W))
  }
  val ioRS2 = new Bundle {
    val en       = Input(Bool())
    val addr     = Input(UInt(CONFIG.REG.NUM_WIDTH.W))
    val needPass = Output(Bool())
    val data     = Output(UInt(CONFIG.REG.WIDTH.W))
  }
  val needStall = Output(Bool())
}

class hazardEXUBundle extends Bundle {
  val isLoad = Input(Bool())
  val ioRD = new Bundle {
    val en   = Input(Bool())
    val addr = Input(UInt(CONFIG.REG.NUM_WIDTH.W))
    val data = Input(UInt(CONFIG.REG.WIDTH.W))
  }
}
class hazardLSUBundle extends Bundle {
  val isLoad = Input(Bool())
  val ioRD = new Bundle {
    val en   = Input(Bool())
    val addr = Input(UInt(CONFIG.REG.NUM_WIDTH.W))
    val data = Input(UInt(CONFIG.REG.WIDTH.W))
  }
}
class hazardWBUBundle extends Bundle {
  val ioRD = new Bundle {
    val en   = Input(Bool())
    val addr = Input(UInt(CONFIG.REG.NUM_WIDTH.W))
    val data = Input(UInt(CONFIG.REG.WIDTH.W))
  }
}
