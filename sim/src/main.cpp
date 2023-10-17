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

void test_IFU(void) {
  static bool first_run = true;
  if (!first_run) {
    VTOP->clock = 1;
    step_and_dump_wave();
  }
  char buf[128] = {};
  char *p = buf;
  p += sprintf(p, "pc: 0x%08lx", VTOP->io_pc);
  memset(p, ' ', 1);
  p += 1;
  uint32_t instTemp = (VTOP->io_inst & 0x03) == 0x03
                          ? VTOP->io_inst
                          : VTOP->io_inst & 0x0000FFFF;
  if ((VTOP->io_inst & 0x03) == 0x03) {
    p += sprintf(p, "inst: 0x%08x ", VTOP->io_inst);
  } else {
    p += sprintf(p, "inst: 0x    %04x ", VTOP->io_inst & 0x0000FFFF);
  }
  if (!first_run) {
    disassemble(p, 64, VTOP->io_pc, (uint8_t *)&instTemp, 4);
    VTOP->clock = 0;
    step_and_dump_wave();
  }
  puts(buf);
  first_run = false;
}

void sim_exit() {
  step_and_dump_wave();
  tfp->close();
}
int count = 0;
int main(void) {
  sim_init();
  reset();
  while (VTOP->io_pc <= CODE_LEN + 0x80000000) {
    test_IFU();
    // if (VTOP->io_pc == 0x80000010) {
    //   VTOP->io_isStall = 1;
    //   count++;
    //   if (count > 20) {
    //     break;
    //   }
    // }
  }
  sim_exit();
  return 0;
}