#ifndef __UTILS__
#define __UTILS__

#include <cstdint>
void init_disasm(const char *triple);
void disassemble(char *str, int size, uint64_t pc, uint8_t *code, int nbyte);

#endif