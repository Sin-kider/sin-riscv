#include "VTop.h"
#include "dpic.h"
#include "verilated.h"
#include "verilated_vcd_c.h"
#include <cstdio>

static VTop *VTOP;
VerilatedContext *contextp = NULL;
VerilatedVcdC *tfp = NULL;

void sim_init() {
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

void test_IFU(void) {
  static bool first_run = true;
  if (!first_run) {
    VTOP->clock = 1;
    step_and_dump_wave();
  }
  // printf("--- sim ---\n");
  printf("pc: %#010lx ", VTOP->io_pc);
  printf("npc: %#010lx ", VTOP->io_npc);
  if ((VTOP->io_inst & 0x03) == 0x03) {
    printf("inst: 0x%08x\n", VTOP->io_inst);
  } else {
    printf("inst: 0x    %04x\n", VTOP->io_inst & 0x0000FFFF);
  }
  if (!first_run) {
    VTOP->clock = 0;
    step_and_dump_wave();
  }
  first_run = false;
}

void sim_exit() {
  step_and_dump_wave();
  tfp->close();
}

int main(void) {
  sim_init();
  reset();
  while (VTOP->io_pc <= HEX_CODE_LEN + 0x80000000) {
    test_IFU();
  }
  sim_exit();
  return 0;
}