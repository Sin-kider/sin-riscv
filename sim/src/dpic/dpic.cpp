#include "dpic.h"
#include "VTop__Dpi.h"
#include <cstdint>
#include <cstdio>

uint8_t hex_code[HEX_CODE_LEN] = {
    0x13, 0x04, 0x00, 0x00, 0x17, 0x91, 0x00, 0x00, 0x13, 0x01, 0xC1, 0xFF,
    0xEF, 0x00, 0xC0, 0x11, 0x63, 0x04, 0x05, 0x00, 0x67, 0x80, 0x00, 0x00,
    0x13, 0x01, 0x41, 0xFF, 0x13, 0x05, 0x10, 0x00, 0x23, 0x24, 0x11, 0x00,
    0xEF, 0x00, 0x00, 0x10, 0x13, 0x01, 0x41, 0xFF, 0x23, 0x22, 0x81, 0x00,
    0x17, 0x14, 0x00, 0x00, 0x13, 0x04, 0xC4, 0xB8, 0x83, 0x25, 0x84, 0x00,
    0x03, 0x25, 0x04, 0x00, 0x23, 0x24, 0x11, 0x00, 0xEF, 0x00, 0xC0, 0x1F,
    0x13, 0x35, 0x15, 0x00, 0xEF, 0xF0, 0x5F, 0xFC, 0x83, 0x25, 0x44, 0x00,
    0x03, 0x25, 0x04, 0x00, 0xEF, 0x00, 0x80, 0x1E, 0x13, 0x55, 0xF5, 0x01,
    0xEF, 0xF0, 0x1F, 0xFB, 0x83, 0x25, 0x44, 0x00, 0x03, 0x25, 0x04, 0x00,
    0x93, 0x85, 0x15, 0x00, 0x13, 0x05, 0x15, 0x00, 0xEF, 0x00, 0xC0, 0x1C,
    0x13, 0x55, 0xF5, 0x01, 0xEF, 0xF0, 0x5F, 0xF9, 0x83, 0x25, 0x44, 0x00,
    0x03, 0x25, 0x04, 0x00, 0x93, 0x85, 0x25, 0x00, 0x13, 0x05, 0x25, 0x00,
    0xEF, 0x00, 0x00, 0x1B, 0x13, 0x55, 0xF5, 0x01, 0xEF, 0xF0, 0x9F, 0xF7,
    0x83, 0x25, 0x44, 0x00, 0x03, 0x25, 0x04, 0x00, 0x93, 0x85, 0x35, 0x00,
    0x13, 0x05, 0x35, 0x00, 0xEF, 0x00, 0x40, 0x19, 0x13, 0x55, 0xF5, 0x01,
    0xEF, 0xF0, 0xDF, 0xF5, 0x97, 0x15, 0x00, 0x00, 0x93, 0x85, 0xC5, 0xAF,
    0x17, 0x15, 0x00, 0x00, 0x13, 0x05, 0x45, 0xB1, 0xEF, 0x00, 0xC0, 0x0C,
    0x83, 0x25, 0xC4, 0x00, 0xEF, 0x00, 0x00, 0x11, 0x83, 0x25, 0x04, 0x01,
    0xEF, 0x00, 0x80, 0x16, 0x13, 0x35, 0x15, 0x00, 0xEF, 0xF0, 0x1F, 0xF3,
    0x13, 0x06, 0x50, 0x00, 0x93, 0x05, 0x30, 0x02, 0x17, 0x15, 0x00, 0x00,
    0x13, 0x05, 0x85, 0xAE, 0xEF, 0x00, 0xC0, 0x1C, 0x83, 0x25, 0x44, 0x01,
    0x13, 0x06, 0x50, 0x00, 0xEF, 0x00, 0xC0, 0x20, 0x13, 0x35, 0x15, 0x00,
    0xEF, 0xF0, 0x9F, 0xF0, 0x83, 0x20, 0x81, 0x00, 0x03, 0x24, 0x41, 0x00,
    0x13, 0x05, 0x00, 0x00, 0x13, 0x01, 0xC1, 0x00, 0x67, 0x80, 0x00, 0x00,
    0x67, 0x80, 0x00, 0x00, 0x6F, 0x00, 0x00, 0x00, 0x13, 0x01, 0x41, 0xFF,
    0x17, 0x15, 0x00, 0x00, 0x13, 0x05, 0x45, 0xA8, 0x23, 0x24, 0x11, 0x00,
    0xEF, 0xF0, 0x1F, 0xEF, 0x6F, 0x00, 0x00, 0x00, 0x63, 0x06, 0x05, 0x02,
    0x83, 0x47, 0x05, 0x00, 0x13, 0x07, 0x05, 0x00, 0x13, 0x05, 0x00, 0x00,
    0x63, 0x8C, 0x07, 0x00, 0x13, 0x05, 0x15, 0x00, 0xB3, 0x07, 0xA7, 0x00,
    0x83, 0xC7, 0x07, 0x00, 0xE3, 0x9A, 0x07, 0xFE, 0x67, 0x80, 0x00, 0x00,
    0x67, 0x80, 0x00, 0x00, 0x13, 0x01, 0x41, 0xFF, 0x13, 0x06, 0x90, 0x00,
    0x97, 0x15, 0x00, 0x00, 0x93, 0x85, 0x05, 0x97, 0x17, 0x15, 0x00, 0x00,
    0x13, 0x05, 0x05, 0x9B, 0x23, 0x24, 0x11, 0x00, 0xEF, 0x00, 0x40, 0x73,
    0x13, 0x05, 0x10, 0x00, 0xEF, 0xF0, 0x5F, 0xF9, 0x63, 0x80, 0x05, 0x02,
    0x93, 0x07, 0x05, 0x00, 0x03, 0xC7, 0x05, 0x00, 0x93, 0x87, 0x17, 0x00,
    0x93, 0x85, 0x15, 0x00, 0xA3, 0x8F, 0xE7, 0xFE, 0xE3, 0x18, 0x07, 0xFE,
    0x67, 0x80, 0x00, 0x00, 0xE3, 0x12, 0x05, 0xFE, 0x13, 0x01, 0x41, 0xFF,
    0x13, 0x06, 0x10, 0x01, 0x97, 0x15, 0x00, 0x00, 0x93, 0x85, 0x45, 0x92,
    0x17, 0x15, 0x00, 0x00, 0x13, 0x05, 0x45, 0x96, 0x23, 0x24, 0x11, 0x00,
    0xEF, 0x00, 0x80, 0x6E, 0x13, 0x05, 0x10, 0x00, 0xEF, 0xF0, 0x9F, 0xF4,
    0x63, 0x8A, 0x05, 0x02, 0x03, 0x47, 0x05, 0x00, 0x93, 0x07, 0x05, 0x00,
    0x63, 0x08, 0x07, 0x00, 0x03, 0xC7, 0x17, 0x00, 0x93, 0x87, 0x17, 0x00,
    0xE3, 0x1C, 0x07, 0xFE, 0x03, 0xC7, 0x05, 0x00, 0x93, 0x87, 0x17, 0x00,
    0x93, 0x85, 0x15, 0x00, 0xA3, 0x8F, 0xE7, 0xFE, 0xE3, 0x18, 0x07, 0xFE,
    0x67, 0x80, 0x00, 0x00, 0xE3, 0x18, 0x05, 0xFC, 0x13, 0x01, 0x41, 0xFF,
    0x13, 0x06, 0x20, 0x02, 0x97, 0x15, 0x00, 0x00, 0x93, 0x85, 0x45, 0x8C,
    0x17, 0x15, 0x00, 0x00, 0x13, 0x05, 0x45, 0x90, 0x23, 0x24, 0x11, 0x00,
    0xEF, 0x00, 0x80, 0x68, 0x13, 0x05, 0x10, 0x00, 0xEF, 0xF0, 0x9F, 0xEE,
    0x63, 0x06, 0x05, 0x04, 0x83, 0xC7, 0x05, 0x00, 0x83, 0x46, 0x05, 0x00,
    0x13, 0x07, 0x05, 0x00, 0x33, 0x85, 0xF6, 0x40, 0x13, 0x06, 0x05, 0x00,
    0x63, 0x90, 0xF6, 0x02, 0x63, 0x86, 0x07, 0x02, 0x83, 0xC7, 0x15, 0x00,
    0x83, 0x46, 0x17, 0x00, 0x93, 0x85, 0x15, 0x00, 0x13, 0x07, 0x17, 0x00,
    0x33, 0x86, 0xF6, 0x40, 0xE3, 0x84, 0xF6, 0xFE, 0x13, 0x26, 0x16, 0x00,
    0x33, 0x06, 0xC0, 0x40, 0x13, 0x65, 0x16, 0x00, 0x67, 0x80, 0x00, 0x00,
    0x67, 0x80, 0x00, 0x00, 0x63, 0x96, 0x05, 0x02, 0x13, 0x01, 0x41, 0xFF,
    0x13, 0x06, 0xB0, 0x02, 0x97, 0x15, 0x00, 0x00, 0x93, 0x85, 0xC5, 0x84,
    0x17, 0x15, 0x00, 0x00, 0x13, 0x05, 0xC5, 0x88, 0x23, 0x24, 0x11, 0x00,
    0xEF, 0x00, 0x00, 0x61, 0x13, 0x05, 0x10, 0x00, 0xEF, 0xF0, 0x1F, 0xE7,
    0x83, 0x47, 0x00, 0x00, 0x73, 0x00, 0x10, 0x00, 0x63, 0x02, 0x05, 0x02,
    0x93, 0xF5, 0xF5, 0x0F, 0x63, 0x0C, 0x06, 0x00, 0x33, 0x06, 0xC5, 0x00,
    0x93, 0x07, 0x05, 0x00, 0x23, 0x80, 0xB7, 0x00, 0x93, 0x87, 0x17, 0x00,
    0xE3, 0x9C, 0xC7, 0xFE, 0x67, 0x80, 0x00, 0x00, 0x13, 0x01, 0x41, 0xFF,
    0x13, 0x06, 0xD0, 0x03, 0x97, 0x05, 0x00, 0x00, 0x93, 0x85, 0x85, 0x7F,
    0x17, 0x15, 0x00, 0x00, 0x13, 0x05, 0x85, 0x83, 0x23, 0x24, 0x11, 0x00,
    0xEF, 0x00, 0xC0, 0x5B, 0x13, 0x05, 0x10, 0x00, 0xEF, 0xF0, 0xDF, 0xE1,
    0x63, 0x0C, 0x05, 0x02, 0xB3, 0x86, 0xC5, 0x00, 0x63, 0x1A, 0x06, 0x00,
    0x6F, 0x00, 0x40, 0x02, 0x93, 0x85, 0x15, 0x00, 0x13, 0x05, 0x15, 0x00,
    0x63, 0x8C, 0xD5, 0x00, 0x83, 0x47, 0x05, 0x00, 0x03, 0xC7, 0x05, 0x00,
    0xE3, 0x86, 0xE7, 0xFE, 0x33, 0x85, 0xE7, 0x40, 0x67, 0x80, 0x00, 0x00,
    0x13, 0x05, 0x00, 0x00, 0x67, 0x80, 0x00, 0x00, 0xE3, 0x96, 0x05, 0xFC,
    0x13, 0x01, 0x41, 0xFF, 0x13, 0x06, 0x60, 0x06, 0x97, 0x05, 0x00, 0x00,
    0x93, 0x85, 0x45, 0x79, 0x17, 0x05, 0x00, 0x00, 0x13, 0x05, 0x45, 0x7D,
    0x23, 0x24, 0x11, 0x00, 0xEF, 0x00, 0x80, 0x55, 0x13, 0x05, 0x10, 0x00,
    0xEF, 0xF0, 0x9F, 0xDB, 0x13, 0x01, 0x01, 0xFE, 0x93, 0x87, 0x05, 0x00,
    0x23, 0x2C, 0x81, 0x00, 0x23, 0x22, 0xB1, 0x00, 0x13, 0x04, 0x05, 0x00,
    0x93, 0x05, 0x06, 0x00, 0x13, 0x85, 0x07, 0x00, 0x23, 0x2A, 0x91, 0x00,
    0x23, 0x2E, 0x11, 0x00, 0x93, 0x04, 0x06, 0x00, 0xEF, 0x00, 0x40, 0x62,
    0x17, 0x17, 0x00, 0x00, 0x13, 0x07, 0x47, 0x80, 0x23, 0x24, 0xA1, 0x00,
    0x23, 0x20, 0xE1, 0x00, 0x63, 0x1C, 0x05, 0x02, 0x03, 0x25, 0x41, 0x00,
    0x93, 0x85, 0x04, 0x00, 0xEF, 0x00, 0x80, 0x68, 0x83, 0x27, 0x01, 0x00,
    0x33, 0x85, 0xA7, 0x00, 0x83, 0x47, 0x05, 0x00, 0x13, 0x05, 0x14, 0x00,
    0x23, 0x00, 0xF4, 0x00, 0x83, 0x20, 0xC1, 0x01, 0x03, 0x24, 0x81, 0x01,
    0x83, 0x24, 0x41, 0x01, 0x13, 0x01, 0x01, 0x02, 0x67, 0x80, 0x00, 0x00,
    0x93, 0x85, 0x04, 0x00, 0xEF, 0x00, 0x40, 0x5D, 0x23, 0x26, 0xA1, 0x00,
    0x63, 0x0E, 0x05, 0x02, 0x93, 0x85, 0x04, 0x00, 0xEF, 0x00, 0x40, 0x5C,
    0x23, 0x28, 0xA1, 0x00, 0x83, 0x27, 0xC1, 0x00, 0x63, 0x18, 0x05, 0x04,
    0x13, 0x85, 0x07, 0x00, 0x93, 0x85, 0x04, 0x00, 0xEF, 0x00, 0x00, 0x63,
    0x83, 0x27, 0x01, 0x00, 0x33, 0x85, 0xA7, 0x00, 0x03, 0x47, 0x05, 0x00,
    0x93, 0x07, 0x14, 0x00, 0x23, 0x00, 0xE4, 0x00, 0x13, 0x84, 0x07, 0x00,
    0x03, 0x25, 0x81, 0x00, 0x93, 0x85, 0x04, 0x00, 0xEF, 0x00, 0xC0, 0x60,
    0x83, 0x27, 0x01, 0x00, 0x33, 0x85, 0xA7, 0x00, 0x03, 0x47, 0x05, 0x00,
    0x93, 0x07, 0x14, 0x00, 0x23, 0x00, 0xE4, 0x00, 0x13, 0x84, 0x07, 0x00,
    0x6F, 0xF0, 0x1F, 0xF6, 0x93, 0x85, 0x04, 0x00, 0x23, 0x20, 0xF1, 0x00,
    0xEF, 0x00, 0x00, 0x56, 0x83, 0x27, 0x01, 0x00, 0x03, 0x27, 0x01, 0x01,
    0x93, 0x05, 0x05, 0x00, 0x63, 0x00, 0x05, 0x02, 0x13, 0x05, 0x04, 0x00,
    0x13, 0x86, 0x04, 0x00, 0x23, 0x26, 0xE1, 0x00, 0xEF, 0xF0, 0x5F, 0xEF,
    0x03, 0x27, 0xC1, 0x00, 0x83, 0x27, 0x01, 0x00, 0x13, 0x04, 0x05, 0x00,
    0x13, 0x05, 0x07, 0x00, 0x93, 0x85, 0x04, 0x00, 0x23, 0x26, 0xF1, 0x00,
    0xEF, 0x00, 0x80, 0x5A, 0x97, 0x07, 0x00, 0x00, 0x93, 0x87, 0x47, 0x70,
    0x33, 0x85, 0xA7, 0x00, 0x83, 0x46, 0x05, 0x00, 0x13, 0x07, 0x14, 0x00,
    0x23, 0x20, 0xF1, 0x00, 0x23, 0x00, 0xD4, 0x00, 0x83, 0x27, 0xC1, 0x00,
    0x13, 0x04, 0x07, 0x00, 0x6F, 0xF0, 0x9F, 0xF4, 0x13, 0x01, 0xC1, 0xFD,
    0x23, 0x20, 0x11, 0x02, 0x23, 0x2E, 0x81, 0x00, 0x23, 0x2C, 0x91, 0x00,
    0x93, 0x07, 0x06, 0x00, 0x03, 0x46, 0x06, 0x00, 0x13, 0x03, 0x05, 0x00,
    0x63, 0x08, 0x06, 0x06, 0x93, 0x82, 0x05, 0x00, 0x93, 0x84, 0x06, 0x00,
    0x13, 0x04, 0x05, 0x00, 0x97, 0x03, 0x00, 0x00, 0x93, 0x83, 0x83, 0x65,
    0x63, 0x92, 0x05, 0x02, 0x6F, 0x00, 0x00, 0x2D, 0x23, 0x00, 0xC4, 0x00,
    0x03, 0xC6, 0x17, 0x00, 0x13, 0x04, 0x14, 0x00, 0x93, 0x07, 0x07, 0x00,
    0x33, 0x05, 0x64, 0x40, 0x63, 0x02, 0x06, 0x04, 0x63, 0x0A, 0x55, 0x2A,
    0x93, 0x06, 0x50, 0x02, 0x13, 0x87, 0x17, 0x00, 0xE3, 0x1E, 0xD6, 0xFC,
    0x03, 0xC6, 0x17, 0x00, 0x93, 0x06, 0x50, 0x01, 0x93, 0x05, 0xD6, 0xF9,
    0x93, 0xF5, 0xF5, 0x0F, 0xE3, 0xEA, 0xB6, 0xFC, 0x93, 0x95, 0x25, 0x00,
    0xB3, 0x85, 0x75, 0x00, 0x83, 0xA6, 0x05, 0x00, 0xB3, 0x86, 0x76, 0x00,
    0x67, 0x80, 0x06, 0x00, 0x13, 0x04, 0x05, 0x00, 0x13, 0x05, 0x00, 0x00,
    0x23, 0x00, 0x04, 0x00, 0x83, 0x20, 0x01, 0x02, 0x03, 0x24, 0xC1, 0x01,
    0x83, 0x24, 0x81, 0x01, 0x13, 0x01, 0x41, 0x02, 0x67, 0x80, 0x00, 0x00,
    0x83, 0xA6, 0x04, 0x00, 0x93, 0x84, 0x44, 0x00, 0x63, 0xC2, 0x06, 0x28,
    0x63, 0x8A, 0x06, 0x06, 0x13, 0x05, 0x04, 0x00, 0x93, 0xD5, 0x46, 0x40,
    0x63, 0x8E, 0x05, 0x02, 0x13, 0x06, 0x00, 0x01, 0x23, 0x28, 0xE1, 0x00,
    0x23, 0x26, 0xF1, 0x00, 0x23, 0x24, 0x51, 0x00, 0x23, 0x22, 0x61, 0x00,
    0x23, 0x20, 0xD1, 0x00, 0xEF, 0xF0, 0xDF, 0xDC, 0x03, 0x27, 0x01, 0x01,
    0x83, 0x27, 0xC1, 0x00, 0x83, 0x22, 0x81, 0x00, 0x03, 0x23, 0x41, 0x00,
    0x83, 0x26, 0x01, 0x00, 0x97, 0x03, 0x00, 0x00, 0x93, 0x83, 0xC3, 0x58,
    0x93, 0xF6, 0xF6, 0x00, 0x17, 0x06, 0x00, 0x00, 0x13, 0x06, 0x86, 0x5D,
    0xB3, 0x06, 0xD6, 0x00, 0x83, 0xC6, 0x06, 0x00, 0x13, 0x04, 0x15, 0x00,
    0x23, 0x00, 0xD5, 0x00, 0x6F, 0x00, 0xC0, 0x01, 0x83, 0xA6, 0x04, 0x00,
    0x93, 0x84, 0x44, 0x00, 0x63, 0x92, 0x06, 0x22, 0x93, 0x06, 0x00, 0x03,
    0x23, 0x00, 0xD4, 0x00, 0x13, 0x04, 0x14, 0x00, 0x03, 0xC6, 0x27, 0x00,
    0x13, 0x07, 0x17, 0x00, 0x6F, 0xF0, 0x9F, 0xF0, 0x83, 0xA6, 0x04, 0x00,
    0x23, 0x26, 0xE1, 0x00, 0x23, 0x24, 0xF1, 0x00, 0x13, 0x85, 0x06, 0x00,
    0x23, 0x22, 0x51, 0x00, 0x23, 0x20, 0x61, 0x00, 0x23, 0x28, 0xD1, 0x00,
    0xEF, 0xF0, 0xDF, 0xB1, 0x03, 0x23, 0x01, 0x00, 0x83, 0x22, 0x41, 0x00,
    0x83, 0x27, 0x81, 0x00, 0x03, 0x27, 0xC1, 0x00, 0x93, 0x84, 0x44, 0x00,
    0x97, 0x03, 0x00, 0x00, 0x93, 0x83, 0xC3, 0x50, 0x63, 0x58, 0xA0, 0x26,
    0x83, 0x26, 0x01, 0x01, 0xB3, 0x05, 0xA4, 0x00, 0x03, 0xC6, 0x06, 0x00,
    0x13, 0x04, 0x14, 0x00, 0x93, 0x86, 0x16, 0x00, 0xA3, 0x0F, 0xC4, 0xFE,
    0xE3, 0x98, 0x85, 0xFE, 0x03, 0xC6, 0x27, 0x00, 0x13, 0x84, 0x05, 0x00,
    0x13, 0x07, 0x17, 0x00, 0x6F, 0xF0, 0xDF, 0xE9, 0x83, 0xA6, 0x04, 0x00,
    0x93, 0x84, 0x44, 0x00, 0xE3, 0x8A, 0x06, 0xF6, 0x93, 0xD5, 0x46, 0x00,
    0x63, 0x82, 0x05, 0x04, 0x13, 0x05, 0x04, 0x00, 0x13, 0x06, 0x00, 0x01,
    0x23, 0x28, 0xE1, 0x00, 0x23, 0x26, 0xF1, 0x00, 0x23, 0x24, 0x51, 0x00,
    0x23, 0x22, 0x61, 0x00, 0x23, 0x20, 0xD1, 0x00, 0xEF, 0xF0, 0xDF, 0xCC,
    0x03, 0x27, 0x01, 0x01, 0x83, 0x27, 0xC1, 0x00, 0x83, 0x22, 0x81, 0x00,
    0x03, 0x23, 0x41, 0x00, 0x83, 0x26, 0x01, 0x00, 0x13, 0x04, 0x05, 0x00,
    0x97, 0x03, 0x00, 0x00, 0x93, 0x83, 0x83, 0x48, 0x93, 0xF6, 0xF6, 0x00,
    0x17, 0x06, 0x00, 0x00, 0x13, 0x06, 0x46, 0x4D, 0xB3, 0x06, 0xD6, 0x00,
    0x83, 0xC6, 0x06, 0x00, 0x13, 0x04, 0x14, 0x00, 0xA3, 0x0F, 0xD4, 0xFE,
    0x6F, 0xF0, 0x9F, 0xF1, 0x83, 0xA6, 0x04, 0x00, 0x93, 0x84, 0x44, 0x00,
    0x63, 0xCA, 0x06, 0x0E, 0xE3, 0x8E, 0x06, 0xEE, 0x23, 0x20, 0x81, 0x00,
    0x93, 0x05, 0xA0, 0x00, 0x13, 0x85, 0x06, 0x00, 0x23, 0x2A, 0xE1, 0x00,
    0x23, 0x28, 0xF1, 0x00, 0x23, 0x26, 0x51, 0x00, 0x23, 0x24, 0x61, 0x00,
    0x23, 0x22, 0xD1, 0x00, 0xEF, 0x00, 0x40, 0x2A, 0x83, 0x26, 0x41, 0x00,
    0x03, 0x23, 0x81, 0x00, 0x83, 0x22, 0xC1, 0x00, 0x83, 0x27, 0x01, 0x01,
    0x03, 0x27, 0x41, 0x01, 0x93, 0x05, 0x05, 0x00, 0x63, 0x04, 0x05, 0x02,
    0x03, 0x25, 0x01, 0x00, 0x13, 0x06, 0xA0, 0x00, 0xEF, 0xF0, 0x1F, 0xC3,
    0x03, 0x27, 0x41, 0x01, 0x83, 0x27, 0x01, 0x01, 0x83, 0x22, 0xC1, 0x00,
    0x03, 0x23, 0x81, 0x00, 0x83, 0x26, 0x41, 0x00, 0x23, 0x20, 0xA1, 0x00,
    0x13, 0x85, 0x06, 0x00, 0x93, 0x05, 0xA0, 0x00, 0x23, 0x28, 0xE1, 0x00,
    0x23, 0x26, 0xF1, 0x00, 0x23, 0x24, 0x51, 0x00, 0x23, 0x22, 0x61, 0x00,
    0xEF, 0x00, 0xC0, 0x2C, 0x97, 0x06, 0x00, 0x00, 0x93, 0x86, 0x86, 0x42,
    0xB3, 0x86, 0xA6, 0x00, 0x83, 0x27, 0x01, 0x00, 0x83, 0xC6, 0x06, 0x00,
    0x03, 0x27, 0x01, 0x01, 0x13, 0x84, 0x17, 0x00, 0x23, 0x80, 0xD7, 0x00,
    0x83, 0x22, 0x81, 0x00, 0x83, 0x27, 0xC1, 0x00, 0x03, 0x23, 0x41, 0x00,
    0x97, 0x03, 0x00, 0x00, 0x93, 0x83, 0x43, 0x3A, 0x6F, 0xF0, 0x1F, 0xE5,
    0x83, 0xA6, 0x04, 0x00, 0x13, 0x04, 0x14, 0x00, 0x93, 0x84, 0x44, 0x00,
    0xA3, 0x0F, 0xD4, 0xFE, 0x03, 0xC6, 0x27, 0x00, 0x13, 0x07, 0x17, 0x00,
    0x6F, 0xF0, 0x5F, 0xD4, 0x83, 0x20, 0x01, 0x02, 0x03, 0x24, 0xC1, 0x01,
    0x83, 0x24, 0x81, 0x01, 0x13, 0x85, 0x02, 0x00, 0x13, 0x01, 0x41, 0x02,
    0x67, 0x80, 0x00, 0x00, 0x13, 0x06, 0x14, 0x00, 0x23, 0x20, 0xC1, 0x00,
    0x13, 0x06, 0xD0, 0x02, 0x23, 0x00, 0xC4, 0x00, 0xB3, 0x06, 0xD0, 0x40,
    0x6F, 0xF0, 0x5F, 0xF0, 0x13, 0x06, 0xD0, 0x02, 0x13, 0x05, 0x14, 0x00,
    0x23, 0x00, 0xC4, 0x00, 0xB3, 0x06, 0xD0, 0x40, 0x6F, 0xF0, 0x9F, 0xD7,
    0x13, 0x06, 0x90, 0x00, 0x63, 0x76, 0xD6, 0x04, 0x13, 0x85, 0x06, 0x00,
    0x93, 0x05, 0xA0, 0x00, 0x23, 0x28, 0xE1, 0x00, 0x23, 0x26, 0xF1, 0x00,
    0x23, 0x24, 0x51, 0x00, 0x23, 0x22, 0x61, 0x00, 0x23, 0x20, 0xD1, 0x00,
    0xEF, 0x00, 0x00, 0x19, 0x93, 0x05, 0x05, 0x00, 0x13, 0x06, 0xA0, 0x00,
    0x13, 0x05, 0x04, 0x00, 0xEF, 0xF0, 0xDF, 0xB2, 0x03, 0x27, 0x01, 0x01,
    0x83, 0x27, 0xC1, 0x00, 0x83, 0x22, 0x81, 0x00, 0x03, 0x23, 0x41, 0x00,
    0x83, 0x26, 0x01, 0x00, 0x13, 0x04, 0x05, 0x00, 0x13, 0x85, 0x06, 0x00,
    0x93, 0x05, 0xA0, 0x00, 0x23, 0x26, 0xE1, 0x00, 0x23, 0x24, 0xF1, 0x00,
    0x23, 0x22, 0x51, 0x00, 0x23, 0x20, 0x61, 0x00, 0xEF, 0x00, 0x40, 0x19,
    0x97, 0x06, 0x00, 0x00, 0x93, 0x86, 0x46, 0x32, 0xB3, 0x86, 0xA6, 0x00,
    0x03, 0xC6, 0x06, 0x00, 0x93, 0x06, 0x14, 0x00, 0x03, 0x23, 0x01, 0x00,
    0x23, 0x00, 0xC4, 0x00, 0x83, 0x22, 0x41, 0x00, 0x83, 0x27, 0x81, 0x00,
    0x03, 0x27, 0xC1, 0x00, 0x13, 0x84, 0x06, 0x00, 0x97, 0x03, 0x00, 0x00,
    0x93, 0x83, 0x03, 0x2A, 0x6F, 0xF0, 0xDF, 0xD4, 0x93, 0x05, 0x04, 0x00,
    0x6F, 0xF0, 0xDF, 0xDA, 0x13, 0x01, 0x81, 0xFD, 0xB7, 0xF2, 0xFF, 0xFF,
    0x23, 0x28, 0x11, 0x00, 0x23, 0x26, 0x81, 0x00, 0x23, 0x24, 0x91, 0x00,
    0x33, 0x01, 0x51, 0x00, 0xB7, 0x12, 0x00, 0x00, 0x37, 0xF3, 0xFF, 0xFF,
    0x93, 0x83, 0x42, 0x00, 0xB3, 0x83, 0x63, 0x00, 0x13, 0x03, 0x41, 0x00,
    0x33, 0x83, 0x63, 0x00, 0x23, 0x20, 0x61, 0x00, 0x13, 0x04, 0x05, 0x00,
    0x13, 0x83, 0x02, 0x00, 0x13, 0x85, 0x02, 0x01, 0x93, 0x02, 0x41, 0x00,
    0x33, 0x05, 0x55, 0x00, 0x23, 0x20, 0xB5, 0x00, 0x93, 0x05, 0x43, 0x01,
    0x93, 0x04, 0x03, 0x00, 0xB3, 0x85, 0x55, 0x00, 0x13, 0x03, 0x83, 0x01,
    0x23, 0xA0, 0xC5, 0x00, 0x33, 0x03, 0x53, 0x00, 0x03, 0x25, 0x01, 0x00,
    0x23, 0x20, 0xD3, 0x00, 0x93, 0x86, 0xC4, 0x01, 0xB3, 0x86, 0x56, 0x00,
    0x23, 0xA0, 0xE6, 0x00, 0x13, 0x87, 0x04, 0x02, 0x33, 0x07, 0x57, 0x00,
    0x23, 0x20, 0xF7, 0x00, 0x83, 0x27, 0x01, 0x00, 0x37, 0x16, 0x00, 0x00,
    0x93, 0x05, 0x00, 0x00, 0x13, 0x06, 0xC6, 0xFF, 0x13, 0x05, 0x45, 0x00,
    0x23, 0xA0, 0x07, 0x00, 0xEF, 0xF0, 0x9F, 0x96, 0x03, 0x25, 0x01, 0x00,
    0x93, 0x87, 0x44, 0x01, 0xB3, 0x86, 0x27, 0x00, 0x13, 0x06, 0x04, 0x00,
    0x93, 0x05, 0xF0, 0xFF, 0x23, 0x2E, 0xD5, 0xFE, 0xEF, 0xF0, 0x1F, 0xB5,
    0x83, 0x27, 0x01, 0x00, 0x93, 0x04, 0x05, 0x00, 0x03, 0xC5, 0x07, 0x00,
    0x63, 0x0C, 0x05, 0x00, 0x13, 0x84, 0x07, 0x00, 0x13, 0x04, 0x14, 0x00,
    0xEF, 0xF0, 0x0F, 0xF9, 0x03, 0x45, 0x04, 0x00, 0xE3, 0x1A, 0x05, 0xFE,
    0xB7, 0x12, 0x00, 0x00, 0x33, 0x01, 0x51, 0x00, 0x83, 0x20, 0x01, 0x01,
    0x13, 0x85, 0x04, 0x00, 0x03, 0x24, 0xC1, 0x00, 0x83, 0x24, 0x81, 0x00,
    0x13, 0x01, 0x81, 0x02, 0x67, 0x80, 0x00, 0x00, 0x63, 0x40, 0x05, 0x06,
    0x63, 0xC6, 0x05, 0x06, 0x13, 0x86, 0x05, 0x00, 0x93, 0x05, 0x05, 0x00,
    0x13, 0x05, 0xF0, 0xFF, 0x63, 0x0C, 0x06, 0x02, 0x93, 0x06, 0x10, 0x00,
    0x63, 0x7A, 0xB6, 0x00, 0x63, 0x58, 0xC0, 0x00, 0x13, 0x16, 0x16, 0x00,
    0x93, 0x96, 0x16, 0x00, 0xE3, 0x6A, 0xB6, 0xFE, 0x13, 0x05, 0x00, 0x00,
    0x63, 0xE6, 0xC5, 0x00, 0xB3, 0x85, 0xC5, 0x40, 0x33, 0x65, 0xD5, 0x00,
    0x93, 0xD6, 0x16, 0x00, 0x13, 0x56, 0x16, 0x00, 0xE3, 0x96, 0x06, 0xFE,
    0x67, 0x80, 0x00, 0x00, 0x93, 0x82, 0x00, 0x00, 0xEF, 0xF0, 0x5F, 0xFB,
    0x13, 0x85, 0x05, 0x00, 0x67, 0x80, 0x02, 0x00, 0x33, 0x05, 0xA0, 0x40,
    0x63, 0x48, 0xB0, 0x00, 0xB3, 0x05, 0xB0, 0x40, 0x6F, 0xF0, 0xDF, 0xF9,
    0xB3, 0x05, 0xB0, 0x40, 0x93, 0x82, 0x00, 0x00, 0xEF, 0xF0, 0x1F, 0xF9,
    0x33, 0x05, 0xA0, 0x40, 0x67, 0x80, 0x02, 0x00, 0x93, 0x82, 0x00, 0x00,
    0x63, 0xCA, 0x05, 0x00, 0x63, 0x4C, 0x05, 0x00, 0xEF, 0xF0, 0x9F, 0xF7,
    0x13, 0x85, 0x05, 0x00, 0x67, 0x80, 0x02, 0x00, 0xB3, 0x05, 0xB0, 0x40,
    0xE3, 0x58, 0x05, 0xFE, 0x33, 0x05, 0xA0, 0x40, 0xEF, 0xF0, 0x1F, 0xF6,
    0x33, 0x05, 0xB0, 0x40, 0x67, 0x80, 0x02, 0x00, 0x61, 0x61, 0x61, 0x61,
    0x61, 0x61, 0x61, 0x61, 0x61, 0x61, 0x61, 0x61, 0x61, 0x61, 0x61, 0x61,
    0x61, 0x61, 0x61, 0x61, 0x61, 0x61, 0x61, 0x61, 0x61, 0x61, 0x61, 0x61,
    0x61, 0x61, 0x61, 0x61, 0x61, 0x61, 0x61, 0x61, 0x61, 0x61, 0x00, 0x00,
    0x61, 0x61, 0x61, 0x61, 0x61, 0x61, 0x61, 0x61, 0x61, 0x61, 0x61, 0x61,
    0x61, 0x61, 0x61, 0x61, 0x61, 0x61, 0x61, 0x61, 0x61, 0x61, 0x61, 0x61,
    0x61, 0x61, 0x61, 0x61, 0x61, 0x61, 0x61, 0x61, 0x61, 0x61, 0x61, 0x61,
    0x61, 0x62, 0x00, 0x00, 0x2C, 0x20, 0x57, 0x6F, 0x72, 0x6C, 0x64, 0x21,
    0x0A, 0x00, 0x00, 0x00, 0x48, 0x65, 0x6C, 0x6C, 0x6F, 0x2C, 0x20, 0x57,
    0x6F, 0x72, 0x6C, 0x64, 0x21, 0x0A, 0x00, 0x00, 0x23, 0x23, 0x23, 0x23,
    0x23, 0x00, 0x00, 0x00, 0x2F, 0x68, 0x6F, 0x6D, 0x65, 0x2F, 0x73, 0x69,
    0x6E, 0x2F, 0x70, 0x72, 0x6F, 0x6A, 0x65, 0x63, 0x74, 0x2F, 0x79, 0x73,
    0x79, 0x78, 0x2D, 0x77, 0x6F, 0x72, 0x6B, 0x62, 0x65, 0x6E, 0x63, 0x68,
    0x2D, 0x6E, 0x65, 0x77, 0x2F, 0x61, 0x62, 0x73, 0x74, 0x72, 0x61, 0x63,
    0x74, 0x2D, 0x6D, 0x61, 0x63, 0x68, 0x69, 0x6E, 0x65, 0x2F, 0x6B, 0x6C,
    0x69, 0x62, 0x2F, 0x73, 0x72, 0x63, 0x2F, 0x73, 0x74, 0x72, 0x69, 0x6E,
    0x67, 0x2E, 0x63, 0x00, 0x41, 0x73, 0x73, 0x65, 0x72, 0x74, 0x69, 0x6F,
    0x6E, 0x20, 0x66, 0x61, 0x69, 0x6C, 0x20, 0x61, 0x74, 0x20, 0x25, 0x73,
    0x3A, 0x25, 0x64, 0x0A, 0x00, 0x00, 0x00, 0x00, 0x68, 0xFC, 0xFF, 0xFF,
    0xA0, 0xFB, 0xFF, 0xFF, 0xC4, 0xF9, 0xFF, 0xFF, 0xC4, 0xF9, 0xFF, 0xFF,
    0xC4, 0xF9, 0xFF, 0xFF, 0xC4, 0xF9, 0xFF, 0xFF, 0xC4, 0xF9, 0xFF, 0xFF,
    0xC4, 0xF9, 0xFF, 0xFF, 0xC4, 0xF9, 0xFF, 0xFF, 0xC4, 0xF9, 0xFF, 0xFF,
    0xC4, 0xF9, 0xFF, 0xFF, 0xC4, 0xF9, 0xFF, 0xFF, 0xC4, 0xF9, 0xFF, 0xFF,
    0x2C, 0xFB, 0xFF, 0xFF, 0xC4, 0xF9, 0xFF, 0xFF, 0xC4, 0xF9, 0xFF, 0xFF,
    0xC0, 0xFA, 0xFF, 0xFF, 0xC4, 0xF9, 0xFF, 0xFF, 0x9C, 0xFA, 0xFF, 0xFF,
    0xC4, 0xF9, 0xFF, 0xFF, 0xC4, 0xF9, 0xFF, 0xFF, 0x28, 0xFA, 0xFF, 0xFF,
    0x30, 0x31, 0x32, 0x33, 0x34, 0x35, 0x36, 0x37, 0x38, 0x39, 0x41, 0x42,
    0x43, 0x44, 0x45, 0x46, 0x00, 0x00, 0x00, 0x00, 0x48, 0x65, 0x6C, 0x6C,
    0x6F, 0x00, 0x00, 0x00, 0x70, 0x0A, 0x00, 0x80, 0x98, 0x0A, 0x00, 0x80,
    0x70, 0x0A, 0x00, 0x80, 0xC0, 0x0A, 0x00, 0x80, 0xCC, 0x0A, 0x00, 0x80,
    0xDC, 0x0A, 0x00, 0x80, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
    0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};

int dpicGetInst(long long addr) {
  if (addr >= 0x80000000) {
    return *(uint32_t *)&hex_code[addr - 0x80000000];
  } else {
    return 0;
  }
}