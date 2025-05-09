# JinHaoDsp

The `JinHaoDsp` abstract class defines the core DSP (Digital Signal Processing) configuration interface for JinHao hearing aids. This includes control over noise reduction, microphone directionality, equalizer settings for different frequencies, and chip-specific data serialization.

---

## Enums

### NOISE

| Constant  | Description                    |
|-----------|--------------------------------|
| `OFF`     | Noise reduction is turned off. |
| `WEAK`    | Weak noise reduction level.    |
| `MEDIUM`  | Medium noise reduction level.  |
| `STRONG`  | Strong noise reduction level.  |
| `UNKNOWN` | Noise setting is unknown.      |

### DIRECTION 

| Constant  | Description                        |
|-----------|------------------------------------|
| `NORMAL`  | Default microphone mode.           |
| `TV`      | Optimized for TV listening.        |
| `METTING` | Optimized for meetings.            |
| `FACE`    | Optimized for face-to-face speech. |
| `UNKNOWN` | Direction setting is unknown.      |

---

## Methods

| Method Signature                                               | Description                                                                 |
|----------------------------------------------------------------|-----------------------------------------------------------------------------|
| `static JinHaoDsp create(byte[] bytes, JinHaoChip chip)`       | Creates a specific DSP instance based on the chip model.                    |
| `abstract JinHaoDsp copy()`                                    | Returns a deep copy of the DSP instance.                                    |
| `abstract byte[] toBytes()`                                    | Converts the current DSP settings into a byte array for storage/transmission. |
| `abstract void setNoise(NOISE noise)`                          | Sets the noise reduction level.                                             |
| `abstract NOISE getNoise()`                                    | Retrieves the current noise reduction setting.                              |
| `abstract void setDirection(DIRECTION direction)`              | Sets the microphone directionality mode.                                    |
| `abstract DIRECTION getDirection()`                            | Retrieves the current microphone directionality setting.                    |
| `abstract int[] getFrequences()`                               | Returns an array of supported equalizer frequencies.                        |
| `abstract void setEq(int frequence, int eq)`                   | Sets the EQ (equalizer) gain/attenuation value for a specific frequency.    |
| `abstract int getEq(int frequence)`                            | Gets the EQ value for a specific frequency.                                 |
| `abstract int getMinEqValue()`                                 | Gets the minimum EQ value allowed.                                          |
| `abstract int getMaxEqValue()`                                 | Gets the maximum EQ value allowed.                                          |

---

## Notes

- This class must be extended for each supported chip type (e.g., `JinHaoA4Dsp`, `JinHaoA16Dsp`).
- The static `create(...)` method automatically instantiates the correct subclass based on the `JinHaoChip` value.
- The EQ-related methods use integer frequency values rather than hardcoded bands, providing flexibility for different chip models.
