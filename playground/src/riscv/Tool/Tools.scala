package riscv.Tool

import chisel3._
import chisel3.util._

object Tools {
  def ignoreBundle(bun: Bundle): Unit = {
    require(bun.elements.nonEmpty)
    for ((name, elem) <- bun.elements) {
      elem := DontCare
    }
  }
}
