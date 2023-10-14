package riscv.Tool

import circt.stage._
import chisel3._
import chisel3.util._

object build extends App {
  def top       = new Top()
  val generator = Seq(chisel3.stage.ChiselGeneratorAnnotation(() => top))
  (new ChiselStage).execute(args, generator :+ CIRCTTargetAnnotation(CIRCTTarget.Verilog))
}
