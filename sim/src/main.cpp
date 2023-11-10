#include "VTop.h"
#include "verilated.h"
#include "verilated_vcd_c.h"
#include "VTop__Dpi.h"
#include <cstdint>
#include <cstdio>

#define ENABLE_WAVE true

static VTop *VTOP;

#if ENABLE_WAVE
VerilatedContext *contextp = NULL;
VerilatedVcdC *tfp = NULL;
#endif

void sim_init() {
  VTOP = new VTop;
#if ENABLE_WAVE
  contextp = new VerilatedContext;
  tfp = new VerilatedVcdC;
  contextp->traceEverOn(true);
  VTOP->trace(tfp, 3);
  tfp->open("./wave.vcd");
#endif
}

void step_and_dump_wave() {
  VTOP->eval();
#if ENABLE_WAVE
  contextp->timeInc(1);
  tfp->dump(contextp->time());
#endif
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

void sim_exit() {
  step_and_dump_wave();
#if ENABLE_WAVE
  tfp->close();
#endif
}

int main(void) {
  sim_init();

  sim_exit();
  return 0;
}

void pmem_read(int raddr, int* rdata) {}
void pmem_write(int waddr, int wdata, char wstrb) {}
void reg_read(int addr) {}
void reg_write(int addr, int data) {}