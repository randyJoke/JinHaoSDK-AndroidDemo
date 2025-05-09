# JinHaoA4Dsp

This class represents a DSP configuration for the JinHao A4 hearing aid device, including methods to configure and manage DSP settings.

## Enum: `MPO`

Defines the possible values for Maximum Power Output (MPO).

| Value    | Description                                       |
|----------|---------------------------------------------------|
| `OFF`    | Maximum Power Output is turned off               |
| `LOW`    | Low Maximum Power Output                         |
| `MEDIUM` | Medium Maximum Power Output                      |
| `HIGH`   | High Maximum Power Output                        |
| `UNKNOWN`| Unknown MPO setting                              |

## Methods

| Method Name                  | Description                                                      | Parameters                                                                 | Return Type  | Parameter Value Range                                      |
|------------------------------|------------------------------------------------------------------|--------------------------------------------------------------------------|--------------|------------------------------------------------------------|
| `create(byte[] bytes)`        | Creates an instance of `JinHaoA4Dsp` from a byte array.         | `bytes`: Byte array containing DSP data.                                | `JinHaoA4Dsp`| -                                                          |
| `copy()`                      | Returns a copy of the current `JinHaoA4Dsp` instance.           | None                                                                     | `JinHaoA4Dsp`| -                                                          |
| `toBytes()`                   | Converts the current `JinHaoA4Dsp` object to a byte array.       | None                                                                     | `byte[]`     | -                                                          |
| `getFrequences()`             | Returns the list of supported frequencies.                       | None                                                                     | `int[]`      | Array of supported frequencies. [250, 500, 1000, 1500, 2000, 2500, 3000, 3500, 4000, 5000, 6000, 7000] |
| `setEq(int frequence, int eq)`| Sets the equalizer value for a specified frequency.              | `frequence`: Frequency (e.g., 250, 500, 1000, 1500, 2000, 2500, 3000, 3500, 4000, 5000, 6000, 7000).<br>`eq`: Equalizer value (0-15) | None        | `frequence`: Supported frequencies.<br> `eq`: 0 to 15      |
| `getEq(int frequence)`        | Gets the equalizer value for a specified frequency.              | `frequence`: Frequency (e.g., 250, 500, 1000, 1500, 2000, 2500, 3000, 3500, 4000, 5000, 6000, 7000)                           | `int`        | `frequence`: Supported frequencies.<br> Returns `-2` if frequency not found. |
| `setNoise(NOISE noise)`       | Sets the noise reduction level.                                  | `noise`: Noise reduction level (`OFF`, `WEAK`, `MEDIUM`, `STRONG`).      | None         | `noise`: `OFF`, `WEAK`, `MEDIUM`, `STRONG`                |
| `getNoise()`                  | Gets the current noise reduction level.                          | None                                                                     | `NOISE`      | `OFF`, `WEAK`, `MEDIUM`, `STRONG`, `UNKNOWN`               |
| `getMinEqValue()`             | Gets the minimum value for the equalizer.                        | None                                                                     | `int`        | 0                                                          |
| `getMaxEqValue()`             | Gets the maximum value for the equalizer.                        | None                                                                     | `int`        | 15                                                         |
| `setDirection(DIRECTION direction)` | Sets the directional mode for the DSP.                        | `direction`: Directional mode (`NORMAL`, `TV`, `METTING`, `FACE`).       | None         | `direction`: `NORMAL`, `TV`, `METTING`, `FACE`            |
| `getDirection()`              | Gets the current directional mode for the DSP.                   | None                                                                     | `DIRECTION`  | `NORMAL`, `TV`, `METTING`, `FACE`, `UNKNOWN`               |
| `setMpo(MPO mpo)`             | Sets the Maximum Power Output (MPO) level.                      | `mpo`: Maximum Power Output (`OFF`, `LOW`, `MEDIUM`, `HIGH`).           | None         | `mpo`: `OFF`, `LOW`, `MEDIUM`, `HIGH`                     |
| `getMpo()`                    | Gets the current Maximum Power Output (MPO) level.               | None                                                                     | `MPO`        | `OFF`, `LOW`, `MEDIUM`, `HIGH`, `UNKNOWN`                  |

## Constants: `NOISE`, `DIRECTION`

Enumerations representing different levels of noise reduction (`NOISE`) and directional modes (`DIRECTION`).

---

### Example Usage

```kotlin
// Change MPO
val dsp = device?.dsp?.copy()
if (dsp is JinHaoA4Dsp) {
    mpoState.value = it
    when {
        it.roundToInt() == 0 -> {
            dsp.mpo = JinHaoA4Dsp.MPO.OFF
        }
        it.roundToInt() == 1 -> {
            dsp.mpo = JinHaoA4Dsp.MPO.LOW
        }
        it.roundToInt() == 2 -> {
            dsp?.mpo = JinHaoA4Dsp.MPO.MEDIUM
        }
        it.roundToInt() == 3 -> {
            dsp?.mpo = JinHaoA4Dsp.MPO.HIGH
        }
    }
    dsp.let {
        device?.excute(
            JinHaoRequest.writeDsp(
                it,
                programState.value.roundToInt(),
                true
            ), Consumer {

            })
    }
}
```