# JinHaoA4DSP 

## Properties

### Basic DSP Settings

| Property | Type | Default Value | Description |
|----------|------|--------------|-------------|
| `inputMode` | `JinHaoA4DspEnum.InputMode` | `AI0_TC` | Input source selection for DSP |
| `preGainMic1` | `JinHaoA4DspEnum.PreampGain` | `DB0` | Pre-amplifier gain for microphone 1 |
| `preGainMic2` | `JinHaoA4DspEnum.PreampGain` | `DB0` | Pre-amplifier gain for microphone 2 |
| `matrixGainMantissa` | `int` | `0` | Mantissa component of matrix gain calculation |
| `matrixGainExponent` | `int` | `0` | Exponent component of matrix gain calculation |
| `feedbackCanceler` | `JinHaoA4DspEnum.FeedbackCanceler` | `OFF` | Feedback cancellation setting |
| `compressionRatio1` | `JinHaoA4DspEnum.CompressionRatio` | `RATIO_1_0` | Compression ratio for band 1 |
| `compressionRatio2` | `JinHaoA4DspEnum.CompressionRatio` | `RATIO_1_0` | Compression ratio for band 2 |
| `compressionRatio3` | `JinHaoA4DspEnum.CompressionRatio` | `RATIO_1_0` | Compression ratio for band 3 |
| `compressionRatio4` | `JinHaoA4DspEnum.CompressionRatio` | `RATIO_1_0` | Compression ratio for band 4 |
| `compressionThreshold` | `JinHaoA4DspEnum.CompressionThreshold` | `DB40` | Compression activation threshold |
| `expansion` | `JinHaoA4DspEnum.Expansion` | `OFF` | Expansion (noise gate) setting |
| `lowCutFilter` | `JinHaoA4DspEnum.LowCutFilter` | `HZ200` | Low-cut filter frequency |
| `highCutFilter` | `JinHaoA4DspEnum.HighCutFilter` | `HZ8000` | High-cut filter frequency |
| `maximumPowerOutput` | `JinHaoA4DspEnum.MaximumPowerOutput` | `MUO` | Maximum power output limiting |
| `noiseReduction` | `JinHaoA4DspEnum.NoiseReduction` | `OFF` | Noise reduction level |
| `notCare` | `int` | `0` | Reserved field for internal use |

### Equalizer Settings

| Property | Type | Default Value | Description |
|----------|------|--------------|-------------|
| `equalizerArray` | `JinHaoA4DspEnum.EqualizerGain[]` | Array of 12 `DB0` | Equalizer gain values for 12 frequency bands |

### Frequency Reference

| Property | Type | Default Value | Description |
|----------|------|--------------|-------------|
| `FREQUENCIES` | `int[]` (static final) | `[250, 500, ..., 7000]` | Array of 12 frequency values in Hz (constant) |

## Methods

### Matrix Gain Methods

| Method | Parameters | Return Type | Description |
|--------|------------|-------------|-------------|
| `setMatrixGain(int matrixGain)` | `matrixGain`: int value (-47 to -1) | `void` | Sets matrix gain using single integer value |
| `getMatrixGain()` | None | `int` | Calculates and returns current matrix gain |

### Equalizer Methods

| Method | Parameters | Return Type | Description |
|--------|------------|-------------|-------------|
| `setEqualizerGain(FrequencyBand band, EqualizerGain gain)` | `band`: frequency band enum, `gain`: equalizer gain | `void` | Sets equalizer gain using FrequencyBand enum |
| `getEqualizerGain(FrequencyBand band)` | `band`: frequency band enum | `EqualizerGain` | Returns equalizer gain using FrequencyBand enum |

### Inherited Methods Implementation

| Method | Parameters | Return Type | Description |
|--------|------------|-------------|-------------|
| `getFrequences()` | None | `int[]` | Returns frequency array |
| `setEq(int frequency, int eq)` | `frequency`: Hz value, `eq`: gain value | `void` | Sets equalizer gain for specific frequency |
| `getEq(int frequency)` | `frequency`: Hz value | `int` | Returns equalizer gain for specific frequency |
| `setNoise(NOISE noise)` | `noise`: noise level enum | `void` | Sets noise reduction level |
| `getNoise()` | None | `NOISE` | Returns current noise reduction level |
| `getMinEqValue()` | None | `int` | Returns minimum equalizer gain value (-30) |
| `getMaxEqValue()` | None | `int` | Returns maximum equalizer gain value (0) |
| `setDirection(DIRECTION direction)` | `direction`: direction mode enum | `void` | Sets input direction mode with predefined configurations |
| `getDirection()` | None | `DIRECTION` | Returns current direction mode based on configuration |


## Array Index Mapping

The 12-element arrays use the following frequency band mapping:

| Index | Frequency (Hz) | FrequencyBand Enum |
|-------|---------------|-------------------|
| 0 | 250 | `HZ250` |
| 1 | 500 | `HZ500` |
| 2 | 1000 | `HZ1000` |
| 3 | 1500 | `HZ1500` |
| 4 | 2000 | `HZ2000` |
| 5 | 2500 | `HZ2500` |
| 6 | 3000 | `HZ3000` |
| 7 | 3500 | `HZ3500` |
| 8 | 4000 | `HZ4000` |
| 9 | 5000 | `HZ5000` |
| 10 | 6000 | `HZ6000` |
| 11 | 7000 | `HZ7000` |

## Direction Mode Presets

### NORMAL Mode
- `preGainMic1`: `DB27` (27 dB)
- `preGainMic2`: `DB12` (12 dB)
- `equalizerArray[0]`: `DB_MINUS_28` (-28 dB)
- `equalizerArray[1]`: `DB_MINUS_18` (-18 dB)

### TV Mode
- `preGainMic1`: `DB27` (27 dB)
- `preGainMic2`: `DB12` (12 dB)
- `equalizerArray[0]`: `DB_MINUS_22` (-22 dB)
- `equalizerArray[1]`: `DB_MINUS_12` (-12 dB)

### MEETING Mode
- `preGainMic1`: `DB27` (27 dB)
- `preGainMic2`: `DB24` (24 dB)
- `equalizerArray[0]`: `DB_MINUS_28` (-28 dB)
- `equalizerArray[1]`: `DB_MINUS_14` (-14 dB)

### FACE Mode
- `preGainMic1`: `DB27` (27 dB)
- `preGainMic2`: `DB27` (27 dB)
- `equalizerArray[0]`: `DB_MINUS_28` (-28 dB)
- `equalizerArray[1]`: `DB_MINUS_12` (-12 dB)

