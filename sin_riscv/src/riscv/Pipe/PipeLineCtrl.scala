package riscv.Pipe

import chisel3._
import chisel3.util._

import riscv.IFU.module._
import riscv.IDU.module._
import riscv.EXU.module._
import riscv.LSU.module._
import riscv.WBU.module._
import riscv.Pipe.module._
import riscv.Tool.Tools

class pipeLineCtrl extends Module {
  val ioIFUCtrl  = IO(Flipped(new IFUCtrlBundle()))
  val ioIFUPCtrl = IO(Flipped(new pipeRegCtrlBundle()))
  val ioIDUCtrl  = IO(Flipped(new IDUCtrlBundle()))
  val ioIDUPCtrl = IO(Flipped(new pipeRegCtrlBundle()))
  val ioEXUCtrl  = IO(Flipped(new EXUCtrlBundle()))
  val ioEXUPCtrl = IO(Flipped(new pipeRegCtrlBundle()))
  val ioLSUCtrl  = IO(Flipped(new LSUCtrlBundle()))
  val ioLSUPCtrl = IO(Flipped(new pipeRegCtrlBundle()))
  val ioWBUCtrl  = IO(Flipped(new WBUCtrlBundle()))
  val ioWBUPCtrl = IO(Flipped(new pipeRegCtrlBundle()))

  val stallCode = WireDefault("b00000".U(5.W))
  val flushCode = WireDefault("b00000".U(5.W))

  stallCode := MuxCase(
    "b00000".U(5.W),
    Seq(
      ioLSUCtrl.stallReq -> "b11110".U(5.W),
      ioIDUCtrl.stallReq -> "b11000".U(5.W),
      ioIFUCtrl.stallReq -> "b10000".U(5.W)
    )
  )

  flushCode := MuxCase(
    "b00000".U(5.W),
    Seq(
      ioEXUCtrl.flushReq -> "b11000".U(5.W)
    )
  )

  // io
  ioIFUCtrl.flushPC := ioEXUCtrl.flushPC

  ioIFUPCtrl.flush     := flushCode(4)
  ioIFUCtrl.pipe.flush := flushCode(4)
  ioIDUPCtrl.flush     := flushCode(3)
  ioIDUCtrl.pipe.flush := flushCode(3)
  ioEXUPCtrl.flush     := flushCode(2)
  ioEXUCtrl.pipe.flush := flushCode(2)
  ioLSUPCtrl.flush     := flushCode(1)
  ioLSUCtrl.pipe.flush := flushCode(1)
  ioWBUPCtrl.flush     := flushCode(0)
  ioWBUCtrl.pipe.flush := flushCode(0)

  ioIFUPCtrl.stallPrev := stallCode(4)
  ioIFUCtrl.pipe.stall := stallCode(4)
  ioIFUPCtrl.stallNext := stallCode(3)
  ioIDUPCtrl.stallPrev := stallCode(3)
  ioIDUCtrl.pipe.stall := stallCode(3)
  ioIDUPCtrl.stallNext := stallCode(2)
  ioEXUPCtrl.stallPrev := stallCode(2)
  ioEXUCtrl.pipe.stall := stallCode(2)
  ioEXUPCtrl.stallNext := stallCode(1)
  ioLSUPCtrl.stallPrev := stallCode(1)
  ioLSUCtrl.pipe.stall := stallCode(1)
  ioLSUPCtrl.stallNext := stallCode(0)
  ioWBUPCtrl.stallPrev := stallCode(0)
  ioWBUCtrl.pipe.stall := stallCode(0)
  ioWBUPCtrl.stallNext := DontCare
}
