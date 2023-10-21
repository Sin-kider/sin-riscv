#include "VTop.h"
#include "dpic.h"
#include "utils.h"
#include "verilated.h"
#include "verilated_vcd_c.h"
#include <cstdint>
#include <cstdio>

static VTop *VTOP;
VerilatedContext *contextp = NULL;
VerilatedVcdC *tfp = NULL;

void sim_init() {
  init_disasm("riscv64-pc-linux-gnu");
  contextp = new VerilatedContext;
  tfp = new VerilatedVcdC;
  VTOP = new VTop;
  contextp->traceEverOn(true);
  VTOP->trace(tfp, 3);
  tfp->open("./wave.vcd");
}

void step_and_dump_wave() {
  VTOP->eval();
  contextp->timeInc(1);
  tfp->dump(contextp->time());
}

void reset(void) {
  VTOP->reset = 1;
  VTOP->eval();
  VTOP->clock = 1;
  VTOP->eval();
  VTOP->reset = 0;
  VTOP->eval();
  VTOP->clock = 0;
  VTOP->eval();
}

const char *regs[] = {"$0", "ra", "sp",  "gp",  "tp", "t0", "t1", "t2",
                      "s0", "s1", "a0",  "a1",  "a2", "a3", "a4", "a5",
                      "a6", "a7", "s2",  "s3",  "s4", "s5", "s6", "s7",
                      "s8", "s9", "s10", "s11", "t3", "t4", "t5", "t6"};

void printInst(uint32_t pc, uint32_t inst) {
  char buf[128] = {};
  disassemble(buf, 128, (uint64_t)pc, (uint8_t *)&inst, 4);
  puts(buf);
}

void test_IDU(void) {
  printf("--- clock ---\n");
  VTOP->clock = 1;
  step_and_dump_wave();
  if (VTOP->io_valid) {
    printf("pc:\t%008x\n", VTOP->io_pc);
    printf("inst:\t%08x ", VTOP->io_inst);
    printInst(VTOP->io_pc, VTOP->io_inst);
    printf("imm\t%d\n", VTOP->io_immData);
    printf("rs1:\t%d(%3s)\n", VTOP->io_rs1Addr, regs[VTOP->io_rs1Addr]);
    printf("rs2:\t%d(%3s)\n", VTOP->io_rs2Addr, regs[VTOP->io_rs2Addr]);
    printf("rd:\t%d(%3s)\n", VTOP->io_rdAddr, regs[VTOP->io_rdAddr]);
  }
  VTOP->clock = 0;
  step_and_dump_wave();
}

void sim_exit() {
  step_and_dump_wave();
  tfp->close();
}
int count = 0;
int main(void) {
  sim_init();
  reset();
  while (count++ < 50) {
    test_IDU();
  }
  sim_exit();
  return 0;
}