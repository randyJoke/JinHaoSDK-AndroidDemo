# JinHaoA16DSP 

## Properties

### Basic DSP Settings

| Property | Type | Default Value | Description |
|----------|------|--------------|-------------|
| `inputMode` | `JinHaoA16DspEnum.InputMode` | `AI1_MIC1` | Input source selection for DSP |
| `analogPreGainP0` | `JinHaoA16DspEnum.AnalogPreGain` | `DB0` | Analog gain for path P0 (0-36 dB in 3 dB steps) |
| `analogPreGainP1` | `JinHaoA16DspEnum.AnalogPreGain` | `DB0` | Analog gain for path P1 (0-36 dB in 3 dB steps) |
| `digitalPreGainP1` | `JinHaoA16DspEnum.DigitalPreGain` | `DB0` | Digital gain for path P1 (0, 6, 12, 18 dB) |
| `digitalPreGainP2` | `JinHaoA16DspEnum.DigitalPreGain` | `DB0` | Digital gain for path P2 (0, 6, 12, 18 dB) |
| `matrixGainMantissa` | `int` | `0` | Mantissa component of matrix gain calculation |
| `matrixGainExponent` | `int` | `0` | Exponent component of matrix gain calculation |
| `feedbackCanceler` | `JinHaoA16DspEnum.FeedbackCanceler` | `OFF` | Feedback cancellation setting |
| `noiseReduction` | `JinHaoA16DspEnum.NoiseReduction` | `OFF` | Noise reduction level |
| `lowLevelExpansion` | `JinHaoA16DspEnum.SwitchState` | `OFF` | Low-level expansion switch |
| `windNoiseReduction` | `JinHaoA16DspEnum.SwitchState` | `OFF` | Wind noise reduction switch |

### Equalizer Settings

| Property | Type | Default Value | Description |
|----------|------|--------------|-------------|
| `equalizerArray` | `JinHaoA16DspEnum.EqualizerGain[]` | Array of 16 `DB0` | Equalizer gain values for 16 frequency bands |
| `em1` | `int` | - | Experimental/reserved field 1 |
| `em2` | `int` | - | Experimental/reserved field 2 |

### Compression Settings

| Property | Type | Default Value | Description |
|----------|------|--------------|-------------|
| `compressionRatioArray` | `JinHaoA16DspEnum.CompressionRatio[]` | Array of 16 `RATIO_1_00` | Compression ratio values for 16 frequency bands |
| `compressionConstantArray` | `JinHaoA16DspEnum.CompressionConstant[]` | Array of 16 `CONSTANT0` | Compression time constants for 16 frequency bands |
| `compressionThresholdArray` | `JinHaoA16DspEnum.CompressionThreshold[]` | Array of 16 `DB40` | Compression thresholds for 16 frequency bands |

### Maximum Power Output (MPO) Settings

| Property | Type | Default Value | Description |
|----------|------|--------------|-------------|
| `maximumPowerOutputArray` | `JinHaoA16DspEnum.MaximumPowerOutput[]` | Array of 16 `MUO` | Maximum power output values for 16 frequency bands |
| `mpoAttackTime` | `JinHaoA16DspEnum.MPOAttackTime` | `MS10` | MPO attack time constant (10 ms) |
| `mpoReleaseTime` | `JinHaoA16DspEnum.MPOReleaseTime` | `MS40` | MPO release time constant (40 ms) |
| `remoteMixRatio` | `JinHaoA16DspEnum.RemoteMixRatio` | `DB0` | Remote microphone mix ratio |
| `lowCutInputFilter` | `JinHaoA16DspEnum.LowCutFilter` | `OFF` | Low-cut input filter setting |

### Frequency Reference

| Property | Type | Default Value | Description |
|----------|------|--------------|-------------|
| `FREQUENCIES` | `int[]` (static final) | `[250, 500, ..., 7500]` | Array of 16 frequency values in Hz (constant) |

## Methods

### Matrix Gain Methods

| Method | Parameters | Return Type | Description |
|--------|------------|-------------|-------------|
| `setMatrixGain(int matrixGain)` | `matrixGain`: int value (-47 to -1) | `void` | Sets matrix gain using single integer value |
| `getMatrixGain()` | None | `int` | Calculates and returns current matrix gain |

### Inherited Methods Implementation

| Method | Parameters | Return Type | Description |
|--------|------------|-------------|-------------|
| `setNoise(NOISE noise)` | `noise`: noise level enum | `void` | Sets noise reduction level |
| `getNoise()` | None | `NOISE` | Returns current noise reduction level |
| `setDirection(DIRECTION direction)` | `direction`: direction mode enum | `void` | Sets input direction mode |
| `getDirection()` | None | `DIRECTION` | Returns current direction mode |
| `getFrequences()` | None | `int[]` | Returns frequency array |
| `setEq(int frequency, int eq)` | `frequency`: Hz value, `eq`: gain value | `void` | Sets equalizer gain for specific frequency |
| `getEq(int frequency)` | `frequency`: Hz value | `int` | Returns equalizer gain for specific frequency |
| `getMinEqValue()` | None | `int` | Returns minimum equalizer gain value |
| `getMaxEqValue()` | None | `int` | Returns maximum equalizer gain value |

### Enhanced Methods (FrequencyBand-based)

| Method | Parameters | Return Type | Description |
|--------|------------|-------------|-------------|
| `setEq(FrequencyBand band, EqualizerGain gain)` | `band`: frequency band enum, `gain`: equalizer gain | `void` | Sets equalizer gain using FrequencyBand |
| `getEq(FrequencyBand band)` | `band`: frequency band enum | `EqualizerGain` | Returns equalizer gain using FrequencyBand |
| `setEqualizerGain(FrequencyBand band, EqualizerGain gain)` | `band`: frequency band enum, `gain`: equalizer gain | `void` | Sets equalizer gain (convenience method) |
| `getEqualizerGain(FrequencyBand band)` | `band`: frequency band enum | `EqualizerGain` | Returns equalizer gain (convenience method) |
| `setCompressThreshold(FrequencyBand band, CompressionThreshold threshold)` | `band`: frequency band enum, `threshold`: compression threshold | `void` | Sets compression threshold using FrequencyBand |
| `getCompressThreshold(FrequencyBand band)` | `band`: frequency band enum | `CompressionThreshold` | Returns compression threshold |
| `setCompressRatio(FrequencyBand band, CompressionRatio ratio)` | `band`: frequency band enum, `ratio`: compression ratio | `void` | Sets compression ratio using FrequencyBand |
| `getCompressRatio(FrequencyBand band)` | `band`: frequency band enum | `CompressionRatio` | Returns compression ratio |
| `setMpo(FrequencyBand band, MaximumPowerOutput mpo)` | `band`: frequency band enum, `mpo`: maximum power output | `void` | Sets maximum power output using FrequencyBand |
| `getMpo(FrequencyBand band)` | `band`: frequency band enum | `MaximumPowerOutput` | Returns maximum power output |
| `setCompressionConstant(FrequencyBand band, CompressionConstant constant)` | `band`: frequency band enum, `constant`: compression constant | `void` | Sets compression constant using FrequencyBand |
| `getCompressionConstant(FrequencyBand band)` | `band`: frequency band enum | `CompressionConstant` | Returns compression constant |

### Utility Methods

| Method | Parameters | Return Type | Description |
|--------|------------|-------------|-------------|
| `indexOf(int[] array, int element)` | `array`: int array, `element`: value to find | `int` | Returns index of element in array, or -1 if not found (private) |

## Array Index Mapping

The 16-element arrays use the following frequency band mapping:

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
| 9 | 4500 | `HZ4500` |
| 10 | 5000 | `HZ5000` |
| 11 | 5500 | `HZ5500` |
| 12 | 6000 | `HZ6000` |
| 13 | 6500 | `HZ6500` |
| 14 | 7000 | `HZ7000` |
| 15 | 7500 | `HZ7500` |