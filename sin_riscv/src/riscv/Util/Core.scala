package riscv.Util

import chisel3._
import chisel3.util._

import riscv.IFU._
import riscv.IFU.module._
import riscv.IDU._
import riscv.IDU.module._
import riscv.EXU._
import riscv.EXU.module._
import riscv.LSU._
import riscv.LSU.module._
import riscv.WBU._
import riscv.WBU.module._
import riscv.Pipe._
import riscv.Config.CONFIG
import riscv.Util.module._

class core extends Module {
  val ioDeb  = IO(new coreDebBundle())
  val ioIAXI = IO(new AXILiteMaster())
  val ioDAXI = IO(new AXILiteMaster())

  val IFU    = Module(new IFU())
  val IFID   = Module(new pipeLine(new IFUStageBundle()))
  val IDU    = Module(new IDU())
  val IDEX   = Module(new pipeLine(new IDUStageBundle()))
  val EXU    = Module(new EXU())
  val EXLS   = Module(new pipeLine(new EXUStageBundle()))
  val LSU    = Module(new LSU())
  val LSWB   = Module(new pipeLine(new LSUStageBundle()))
  val WBU    = Module(new WBU())
  val WBDPIC = Module(new pipeLine(new WBUStageBundle()))

  val pipeLineCtrl = Module(new pipeLineCtrl())
  val hazard       = Module(new hazard())

  val iMem = Module(new iMem())
  val dMem = Module(new dMem())

  val regFile = Module(new regFile())

  IFU.ioIFU        <> IFID.ioPrevStage
  IFID.ioNextStage <> IDU.ioIFU
  IDU.ioIDU        <> IDEX.ioPrevStage
  IDEX.ioNextStage <> EXU.ioIDU
  EXU.ioEXU        <> EXLS.ioPrevStage
  EXLS.ioNextStage <> LSU.ioEXU
  LSU.ioLSU        <> LSWB.ioPrevStage
  LSWB.ioNextStage <> WBU.ioLSU
  WBU.ioWBU        <> WBDPIC.ioPrevStage

  pipeLineCtrl.ioIFUCtrl  <> IFU.ioCtrl
  pipeLineCtrl.ioIFUPCtrl <> IFID.ioPCtrl
  pipeLineCtrl.ioIDUCtrl  <> IDU.ioCtrl
  pipeLineCtrl.ioIDUPCtrl <> IDEX.ioPCtrl
  pipeLineCtrl.ioEXUCtrl  <> EXU.ioCtrl
  pipeLineCtrl.ioEXUPCtrl <> EXLS.ioPCtrl
  pipeLineCtrl.ioLSUCtrl  <> LSU.ioCtrl
  pipeLineCtrl.ioLSUPCtrl <> LSWB.ioPCtrl
  pipeLineCtrl.ioWBUCtrl  <> WBU.ioCtrl
  pipeLineCtrl.ioWBUPCtrl <> WBDPIC.ioPCtrl

  hazard.ioIDU <> IDU.ioHazardIDU
  hazard.ioEXU <> EXU.ioHazardEXU
  hazard.ioLSU <> LSU.ioHazardLSU
  hazard.ioWBU <> WBU.ioHazardWBU

  IDU.ioRegIDU <> regFile.ioRegIDU
  WBU.ioRegWBU <> regFile.ioRegWBU

  IFU.ioIMemIFU <> iMem.ioIMemIFU
  IDU.ioIMemIDU <> iMem.ioIMemIDU
  iMem.ioAXI    <> ioIAXI

  LSU.ioDMemLSU <> dMem.ioDMemLSU
  WBU.ioDMemWBU <> dMem.ioDMemWBU
  dMem.ioAXI    <> ioDAXI

  // for test
  ioDeb.pc        := WBDPIC.ioNextStage.pc
  ioDeb.inst      := WBDPIC.ioNextStage.inst
  ioDeb.rs1Addr   := WBDPIC.ioNextStage.rs1Addr
  ioDeb.rs2Addr   := WBDPIC.ioNextStage.rs2Addr
  ioDeb.rs1Data   := WBDPIC.ioNextStage.rs1Data
  ioDeb.rs2Data   := WBDPIC.ioNextStage.rs2Data
  ioDeb.ALUresult := WBDPIC.ioNextStage.ALUresult
  ioDeb.rdAddr    := WBDPIC.ioNextStage.rdAddr
}
