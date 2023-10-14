# Global Config
TOP_NAME  = Top
TOP_DIR   = $(shell pwd)
BUILD_DIR = $(TOP_DIR)/build

# Chisel Config
CHISEL_BUILD_DIR      = $(BUILD_DIR)/chisel
CHISEL_DIR            = $(TOP_DIR)/playground
CHISEL_BUILD_TOP_VSRC = $(CHISEL_BUILD_DIR)/$(TOP_NAME).sv
CHISEL_BUILD_VSRC     = $(foreach dir,$(CHISEL_BUILD_DIR),$(wildcard $(dir)/*.v)) $(foreach dir,$(CHISEL_BUILD_DIR),$(wildcard $(dir)/*.sv))
CHISEL_SRC_DIR        = $(CHISEL_DIR)/src
CHISEL_TEST_DIR       = $(CHISEL_DIR)/test
CHISEL_SRC_PATH       = $(foreach dir, $(shell find $(CHISEL_SRC_DIR) -maxdepth 3 -type d), $(wildcard $(dir)/*.scala))
CHISEL_TEST_DIR       = $(TOP_DIR)/test_run_dir
CHISEL_TOOL           = riscv.Tool.build
CHISEL_FIRTOOL_PATH   = $(TOP_DIR)/utils

# Verilator Config
VAL               = verilator
SIM_DIR           = $(TOP_DIR)/sim
SIM_BUILD_DIR     = $(BUILD_DIR)/sim
SIM_SRC_DIR       = $(SIM_DIR)/src
SIM_INC_DIR       = $(SIM_DIR)/inc
SIM_SRC_PATH      = $(foreach dir, $(shell find $(SIM_SRC_DIR) -maxdepth 3 -type d), $(wildcard $(dir)/*.cpp))
VAL_FLAGS        += -MMD --build -cc -O3 --x-assign fast --x-initial fast --noassert -Wno-WIDTHEXPAND
VAL_CFLAGS_TEMP   = -Wno-builtin-macro-redefined $(foreach dir, $(SIM_INC_DIR), -I$(dir))
VAL_CFLAGS        = $(foreach temp, $(VAL_CFLAGS_TEMP), -CFLAGS $(temp))
VAL_VSRC_DIR      = $(CHISEL_TEST_DIR)/resources
VAL_VSRC_PATH     = $(foreach dir,$(VAL_VSRC_DIR),$(wildcard $(dir)/*.v)) $(foreach dir,$(VAL_VSRC_DIR),$(wildcard $(dir)/*.sv))
SIM_BINFILE_PATH  = $(SIM_BUILD_DIR)/$(TOP_NAME)
SIM_OBJ_DIR       = $(SIM_BUILD_DIR)/temp
SIM_WAVE_PATH     = $(SIM_BUILD_DIR)/wave.vcd

# VAL_LIBS = 

export PATH := $(PATH):$(CHISEL_FIRTOOL_PATH)

default: $(SIM_BINFILE_PATH)

verilog: $(CHISEL_BUILD_TOP_VSRC)

verilator: $(SIM_BINFILE_PATH)

run: $(SIM_BINFILE_PATH)
	@cd $(SIM_BUILD_DIR) && ./$(TOP_NAME)

show: $(SIM_WAVE_PATH)
	@gtkwave $(SIM_WAVE_PATH)

$(SIM_BINFILE_PATH): $(VAL_VSRC_PATH) $(SIM_SRC_PATH) $(CHISEL_BUILD_TOP_VSRC)
	@echo MKDIR $(SIM_BUILD_DIR)
	@mkdir -p $(SIM_BUILD_DIR)
	@echo GEN $(notdir $(SIM_BINFILE_PATH))
	$(VAL) $(VAL_FLAGS) \
	  --top-module $(TOP_NAME) \
		$(SIM_SRC_PATH) $(CHISEL_BUILD_VSRC) \
		$(VAL_CFLAGS) --Mdir $(SIM_OBJ_DIR) --trace --exe \
		-o $@ $(VAL_LIBS) -j 8

$(CHISEL_BUILD_TOP_VSRC): $(CHISEL_SRC_PATH) $(VAL_VSRC_PATH)
	@echo MKDIR $(CHISEL_BUILD_DIR)
	@mkdir -p $(CHISEL_BUILD_DIR)
	@echo GEN $(notdir $(CHISEL_BUILD_VSRC))
	@mill -i __.runMain $(CHISEL_TOOL) --split-verilog -td $(CHISEL_BUILD_DIR)

fmt:
	mill -i __.reformat

compile:
	mill -i __.compile

help:
	@mill -i __.runMain $(CHISEL_TOOL) --help

clean:
	@rm -rf $(BUILD_DIR)