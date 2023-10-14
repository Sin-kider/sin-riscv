package riscv.Tool

import chisel3._
import chisel3.util._

object Tools {
  def ignoreBundle(bun: Bundle): Unit = {
    require(bun.elements.toList.length > 0)
    for (i <- 0 until bun.elements.toList.length) {
      bun.getElements(i) := DontCare
    }
  }
}
