# JinHaoRequest 
The JinHaoRequest class encapsulates requests for communication with JinHao hearing aid devices. This class provides an easy way for developers to interact with the device by wrapping different types of operation commands (such as reading, writing, controlling, etc.). Each request includes the operation type, associated data, and a callback mechanism that is triggered after the operation is completed.
## Read Requests
| Method | Parameters | Description |
| ---------------- | ----------- | ----------- |
| `static JinHaoRequest readBat()` | None | Creates a request to read the battery status. |
| `static JinHaoRequest readProgramVolume()` | None | Creates a request to read the program volume. |
| ~~`static JinHaoRequest readVolume(int program)`~~ | ~~`program`: The program number to read the volume for.~~| ~~Creates a request to read the volume for the specified program.~~ |
| `static JinHaoRequest readDsp(int program)` | `program`: The program number to read the DSP for. the range is `0`~ `accessory.numberOfProgram - 1`| Creates a request to read the DSP for the specified program. |
| `static JinHaoRequest readGlobalDsp(JinHaoChip chip)` | `chip`: The chip to read the global DSP for. | Creates a request to read the global DSP for the specified chip. |
| `static JinHaoRequest readNumberOfProgram(JinHaoChip chip)` | `chip`: The chip to read the number of programs for. | Creates a request to read the number of programs for the specified chip. |
| `static JinHaoRequest readProfile(JinHaoProfileType type)` | `type`: The profile type to read. | Creates a request to read the profile of the specified type. |
| `static JinHaoRequest readScenesOfProgram()` | None | Creates a request to read the scenes of a program. |

## Write Requests
| Method | Parameters | Description |
| ---------------- | ----------- | ----------- |
| `static JinHaoRequest writeDsp(JinHaoDsp dsp, int program, boolean withResponse)` | `dsp`: The DSP settings to write.<br> `program`: The program range is 0 to `accessory.numberOfProgram - 1`.<br> `withResponse`: Whether to save the settings persistently. | Writes DSP settings for a program. |
| `static JinHaoRequest writeGlobalDsp(JinHaoGlobalDsp globalDsp, JinHaoChip chip, boolean withResponse)` | `globalDsp`: The global DSP settings.<br> `chip`: The chip to write the global DSP to.<br> `withResponse`: Whether to save the settings persistently. | Creates a request to write the global DSP for the specified chip. |
| `static JinHaoRequest writeProfile(JinHaoProfileType type, byte[] bytes)` | `type`: [The profile type](JinHaoProfileType.md) to write.<br> `bytes`: The profile data in bytes. | Creates a request to write the profile for the specified type with the provided bytes. |
| `static JinHaoRequest writeProtocolData(JinHaoRawData data)` | `data`: The raw protocol data to write. | Creates a request to write protocol data. |

## Control Requests
| Method | Parameters | Description |
| ---------------- | ----------- | ----------- |
| `static JinHaoRequest controlVolume(int volume, int program)` | `volume`: The volume level to set. The `volume` can be set in two ranges: <br>**Range 1:** `volume` from **0 to 10** representing a total of 10 volume levels. <br>**Range 2:** `volume` from **0 to 5**, representing a total of 6 volume levels.<br> `program`: The program number to control the volume for. the range is `0`~ `accessory.numberOfProgram - 1`| Creates a request to control the volume for the specified program. |
| `static JinHaoRequest controlProgram(int program)` | `program`: The program number to control.  the range is `0`~ `accessory.numberOfProgram - 1`| Creates a request to control the program. |
| `static JinHaoRequest controlLockChip()` | None | Creates a request to lock the chip. |
| `static JinHaoRequest controlMute(boolean mute)` | `mute`: Whether to mute or unmute. | Creates a request to control the mute setting. |
| `static JinHaoRequest controlBeep(int beep)` | `beep`: The beep sound setting. | Creates a request to control the beep sound. |
| `static JinHaoRequest controlShip()` | None | Creates a request to control the ship mode. |

## H01 Requests
| Method | Parameters | Description |
| ---------------- | ----------- | ----------- |
| `static JinHaoRequest enterDUT()` | None | Creates a request to enter the DUT mode. |
| `static JinHaoRequest setBrEdr(boolean enable)` | `enable`: Whether to enable or disable BR/EDR mode. | Creates a request to set BR/EDR mode for the device. |
| `static JinHaoRequest setTransparency(int level)` | `level`: The transparency level to set. | Creates a request to set the transparency level. |
| `static JinHaoRequest readTransparency()` | None | Creates a request to read the transparency level. |
| `static JinHaoRequest beginFind()` | None | Creates a request to begin finding the device. |
| `static JinHaoRequest endFind()` | None | Creates a request to end finding the device. |
| `static JinHaoRequest resetFactory()` | None | Creates a request to reset the factory settings. |

## Hearing Test Requests
| Method | Parameters | Description |
| ---------------- | ----------- | ----------- |
| `static JinHaoRequest hearingSupported()` | None | Creates a request to check if hearing is supported. |
| `static JinHaoRequest enterHearingMode(boolean enter)` | `enter`: Whether to enter or exit hearing mode. | Creates a request to enter or exit hearing mode. |
| `static JinHaoRequest setBaseAmplitude(int freq, int amp)` | `freq`: The frequency of the sine wave.<br> `amp`: The amplitude of the sine wave. | Creates a request to set the base amplitude. |
| `static JinHaoRequest playSineAmplitude(int freq, int amp, int timestamp)` | `freq`: The frequency of the sine wave.<br> `amp`: The amplitude of the sine wave.<br> `timestamp`: The timestamp of the request. | Creates a request to play a sine wave with amplitude at the specified frequency. |
| `static JinHaoRequest playSine(int freq, int dB, int timestamp)` | `freq`: The frequency of the sine wave.<br> `dB`: The dB level of the sine wave.<br> `timestamp`: The timestamp of the request. | Creates a request to play a sine wave with a specified frequency and dB level. |
| `static JinHaoRequest stopPlaySine()` | None | Creates a request to stop playing the sine wave. |


## Example
```
//Read
device.excute(JinHaoRequest.readProgramVolume(), Consumer {
    if (it.isError) {
        Log.w(tag, "Failed to read program volume")
    } else {
        Log.w(tag, "Successfully read program volume")
    }
})
```