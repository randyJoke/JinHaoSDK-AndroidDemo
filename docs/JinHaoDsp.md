# JinHaoDsp 

The `JinHaoDsp` class is an abstract class that defines the Digital Signal Processing (DSP) parameters for JinHao hearing aids. It provides methods for setting and getting various DSP settings such as MPO (Maximum Power Output), noise reduction levels, directionality, and equalizer settings across different frequency bands. The class also provides methods for creating a specific DSP instance based on the hearing aid's chip type and copying DSP settings.

## Enums

### MPO (Maximum Power Output)

| Constant  | Description                   |
|-----------|-------------------------------|
| `OFF`     | MPO is turned off.             |
| `LOW`     | Low MPO setting.               |
| `MEDIUM`  | Medium MPO setting.            |
| `HIGH`    | High MPO setting.              |
| `UNKNOWN` | MPO setting is unknown.        |

### NOISE (Noise Reduction)

| Constant  | Description                   |
|-----------|-------------------------------|
| `OFF`     | Noise reduction is off.        |
| `WEAK`    | Weak noise reduction.          |
| `MEDIUM`  | Medium noise reduction.        |
| `STRONG`  | Strong noise reduction.        |
| `UNKNOWN` | Noise setting is unknown.      |

### DIRECTION (Directional Microphone Settings)

| Constant  | Description                   |
|-----------|-------------------------------|
| `NORMAL`  | Normal microphone direction.   |
| `TV`      | TV mode.                       |
| `METTING` | Meeting mode.                  |
| `FACE`    | Face-to-face mode.             |
| `UNKNOWN` | Direction setting is unknown.  |

## Methods

| Method Signature                          | Description                                                                 |
|-------------------------------------------|-----------------------------------------------------------------------------|
| `static JinHaoDsp create(byte[] bytes, JinHaoChip chip)` | Creates a specific DSP instance based on the provided chip type.           |
| `abstract JinHaoDsp copy()`               | Creates a copy of the current DSP instance.                                 |
| `abstract byte[] toBytes()`               | Converts the DSP settings to a byte array.                                  |
| `abstract void setMpo(MPO mpo)`           | Sets the MPO (Maximum Power Output) setting.                               |
| `abstract MPO getMpo()`                   | Gets the current MPO setting.                                               |
| `abstract void setDirection(DIRECTION direction)` | Sets the directionality of the microphones.                                 |
| `abstract DIRECTION getDirection()`       | Gets the current microphone directionality setting.                         |
| `abstract void setNoise(NOISE noise)`     | Sets the noise reduction level.                                            |
| `abstract NOISE getNoise()`               | Gets the current noise reduction setting.                                  |
| `abstract void setEq250(byte eq250)`      | Sets the equalizer value for 250 Hz.                                        |
| `abstract void setEq500(byte eq500)`      | Sets the equalizer value for 500 Hz.                                        |
| `abstract void setEq1000(byte eq1000)`    | Sets the equalizer value for 1000 Hz.                                       |
| `abstract void setEq1500(byte eq1500)`    | Sets the equalizer value for 1500 Hz.                                       |
| `abstract void setEq2000(byte eq2000)`    | Sets the equalizer value for 2000 Hz.                                       |
| `abstract void setEq2500(byte eq2500)`    | Sets the equalizer value for 2500 Hz.                                       |
| `abstract void setEq3000(byte eq3000)`    | Sets the equalizer value for 3000 Hz.                                       |
| `abstract void setEq3500(byte eq3500)`    | Sets the equalizer value for 3500 Hz.                                       |
| `abstract void setEq4000(byte eq4000)`    | Sets the equalizer value for 4000 Hz.                                       |
| `abstract void setEq5000(byte eq5000)`    | Sets the equalizer value for 5000 Hz.                                       |
| `abstract void setEq6000(byte eq6000)`    | Sets the equalizer value for 6000 Hz.                                       |
| `abstract void setEq7000(byte eq7000)`    | Sets the equalizer value for 7000 Hz.                                       |
| `abstract byte getEq250()`                | Gets the equalizer value for 250 Hz.                                        |
| `abstract byte getEq500()`                | Gets the equalizer value for 500 Hz.                                        |
| `abstract byte getEq1000()`               | Gets the equalizer value for 1000 Hz.                                       |
| `abstract byte getEq1500()`               | Gets the equalizer value for 1500 Hz.                                       |
| `abstract byte getEq2000()`               | Gets the equalizer value for 2000 Hz.                                       |
| `abstract byte getEq2500()`               | Gets the equalizer value for 2500 Hz.                                       |
| `abstract byte getEq3000()`               | Gets the equalizer value for 3000 Hz.                                       |
| `abstract byte getEq3500()`               | Gets the equalizer value for 3500 Hz.                                       |
| `abstract byte getEq4000()`               | Gets the equalizer value for 4000 Hz.                                       |
| `abstract byte getEq5000()`               | Gets the equalizer value for 5000 Hz.                                       |
| `abstract byte getEq6000()`               | Gets the equalizer value for 6000 Hz.                                       |
| `abstract byte getEq7000()`               | Gets the equalizer value for 7000 Hz.                                       |
| `abstract byte getMinEqValue()`           | Gets the minimum equalizer value across all frequency bands.               |
| `abstract byte getMaxEqValue()`           | Gets the maximum equalizer value across all frequency bands.               |

