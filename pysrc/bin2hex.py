import sys

def read_binary_file(file_path):
  try:
    with open(file_path, 'rb') as file:
      binary_data = list(file.read())
    return binary_data
  except FileNotFoundError:
    print(f"File not found: {file_path}")
    return None
  except Exception as e:
    print(f"An error occurred: {str(e)}")
    return None

# 例子用法:
file_path = sys.argv[1]
binary_data = read_binary_file(file_path)
if binary_data:
  hex_data = [str(hex(byte))[2:].zfill(2) for byte in binary_data]
  output = f"#define HEX_CODE_LEN {len(hex_data)}\n"
  output += "uint8_t hex_code[HEX_CODE_LEN] = {\n"
  for i in range(0, len(hex_data), 4):
    chunk = hex_data[i:i + 4]
    formatted_chunk = [int(s, 16) for s in chunk]
    output += "  " + ", ".join("0x{:02X}".format(value) for value in formatted_chunk) + ",\n"
  output = output.rstrip(",\n") + "\n};"
  print(output)