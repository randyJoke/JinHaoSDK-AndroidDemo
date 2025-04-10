# JinHaoGlobalDsp

The `JinHaoGlobalDsp` class is an abstract class that represents global DSP settings for JinHao hearing aids. It allows the creation of specific global DSP instances based on the chip type and provides methods for copying, converting to bytes, and retrieving the number of programs supported by the DSP.

## Methods

| Method Signature                          | Description                                                                 |
|-------------------------------------------|-----------------------------------------------------------------------------|
| `static JinHaoGlobalDsp create(byte[] bytes, JinHaoChip chipType)` | Creates a specific global DSP instance based on the chip type (`A4`, `H01`, `A16`). |
| `abstract JinHaoGlobalDsp copy()`         | Creates a copy of the current global DSP instance.                          |
| `abstract byte[] toBytes()`               | Converts the global DSP settings to a byte array.                           |
| `abstract int numberOfProgram()`          | Returns the number of programs supported by the global DSP.                 |
