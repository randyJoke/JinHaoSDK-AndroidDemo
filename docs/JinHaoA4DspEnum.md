# JinHaoA4DSPEnum Documentation 

## Overview
The `JinHaoA4DspEnum` class provides a comprehensive set of enumerations for managing DSP parameters in JinHao A4 hearing aid devices. All enums follow Java naming conventions and provide utility methods for conversion, display, and value calculation.

## Available Enumerations

### InputMode 
**Purpose:** Defines the input source selection for the DSP.

**Values:**
- `AI0_TC` (0) - "AI0 (TC)"
- `AI1_MIC1` (1) - "AI1 (Mic1)"
- `AI2_DAI_AUX` (2) - "AI2 (DAI/AUX)"
- `AI3_MIC2` (3) - "AI3 (Mic2)"
- `FIXED_HYPER_CARDIOID` (4) - "Fixed Hyper Cardioid(Mic1+Mic2)"
- `AI1_AI2` (5) - "AI1+AI2(Mic1+DAI)"
- `AI0_AI3` (6) - "AI0+AI3(TC+MIC2)"

**Methods:**
- `getValue()`: Returns the integer value of the enum constant (0-6)
- `getDisplayName()`: Returns the human-readable display name from parameter labels
- `getAllValues()`: Static method returning all enum values as a List
- `fromValue(int value)`: Static method to convert integer value to enum constant

### PreampGain
**Purpose:** Defines microphone pre-amplifier gain values.

**Values:**
- `DB0` (0) - "0 dB"
- `DB12` (1) - "12 dB"
- `DB15` (2) - "15 dB"
- `DB18` (3) - "18 dB"
- `DB21` (4) - "21 dB"
- `DB24` (5) - "24 dB"
- `DB27` (6) - "27 dB"
- `DB30` (7) - "30 dB"

**Methods:**
- `getValue()`: Returns the integer value (0-7)
- `getDisplayName()`: Returns formatted string from parameter labels
- `getValueInDB()`: Returns the actual dB value (0, 12, 15, 18, 21, 24, 27, or 30 dB)
- `getAllValues()`: Returns all values as List
- `fromValue(int value)`: Converts integer to enum

### FeedbackCanceler
**Purpose:** Defines feedback cancellation state.

**Values:**
- `OFF` (0) - "OFF"
- `ON` (1) - "On"

**Methods:**
- `getValue()`: Returns the integer value (0 or 1)
- `getDisplayName()`: Returns "OFF" or "On"
- `getAllValues()`: Returns all values as List
- `fromValue(int value)`: Converts integer to enum (1 = ON, others = OFF)

### Expansion
**Purpose:** Defines expansion (noise gate) state.

**Values:**
- `OFF` (0) - "OFF"
- `ON` (1) - "On"

**Methods:**
- `getValue()`: Returns the integer value (0 or 1)
- `getDisplayName()`: Returns "OFF" or "On"
- `getAllValues()`: Returns all values as List
- `fromValue(int value)`: Converts integer to enum (1 = ON, others = OFF)

### NoiseReduction 
**Purpose:** Defines noise reduction levels.

**Values:**
- `OFF` (0) - "Off"
- `LOW` (1) - "Low(-7dB)"
- `MEDIUM` (2) - "Medium(-10dB)"
- `HIGH` (3) - "High(-13dB)"

**Methods:**
- `getValue()`: Returns the integer value (0-3)
- `getDisplayName()`: Returns descriptive name with dB values
- `getReductionInDB()`: Returns actual dB reduction (0, -7, -10, or -13 dB)
- `getAllValues()`: Returns all values as List
- `fromValue(int value)`: Converts integer to enum

### LowCutFilter 
**Purpose:** Defines low-cut (high-pass) filter frequencies.

**Values:**
- `HZ200` (0) - "200 Hz"
- `HZ500` (1) - "500 Hz"
- `HZ750` (2) - "750 Hz"
- `HZ1000` (3) - "1000 Hz"
- `HZ1500` (4) - "1500 Hz"
- `HZ2000` (5) - "2000 Hz"
- `HZ3000` (6) - "3000 Hz"

**Methods:**
- `getValue()`: Returns the integer value (0-6)
- `getDisplayName()`: Returns formatted frequency string
- `getFrequency()`: Returns frequency in Hz (200-3000 Hz)
- `getAllValues()`: Returns all values as List
- `fromValue(int value)`: Converts integer to enum

### HighCutFilter 
**Purpose:** Defines high-cut (low-pass) filter frequencies.

**Values:**
- `HZ8000` (0) - "8000(off)"
- `HZ4000` (1) - "4000 Hz"
- `HZ3150` (2) - "3150 Hz"
- `HZ2500` (3) - "2500 Hz"
- `HZ2000` (4) - "2000 Hz"
- `HZ1600` (5) - "1600 Hz"
- `HZ1250` (6) - "1250 Hz"

**Methods:**
- `getValue()`: Returns the integer value (0-6)
- `getDisplayName()`: Returns formatted frequency string ("off" for 8000Hz)
- `getFrequency()`: Returns frequency in Hz (1250-8000 Hz)
- `getAllValues()`: Returns all values as List
- `fromValue(int value)`: Converts integer to enum

### CompressionRatio
**Purpose:** Defines audio compression ratios.

**Values:**
- `RATIO_1_0` (0) - "1.00 : 1"
- `RATIO_1_14` (1) - "1.14 : 1"
- `RATIO_1_33` (2) - "1.33 : 1"
- `RATIO_1_60` (3) - "1.60 : 1"
- `RATIO_2_0` (4) - "2.00 : 1"
- `RATIO_2_56` (5) - "2.56 : 1"
- `RATIO_4_0` (6) - "4.00 : 1"

**Methods:**
- `getValue()`: Returns the integer value (0-6)
- `getDisplayName()`: Returns formatted ratio string
- `getRatio()`: Returns the ratio as a double value (1.0, 1.14, 1.33, 1.60, 2.0, 2.56, or 4.0)
- `getAllValues()`: Returns all values as List
- `fromValue(int value)`: Converts integer to enum

### CompressionThreshold
**Purpose:** Defines compression activation thresholds.

**Values:**
- `DB40` (0) - "40 dB"
- `DB45` (1) - "45 dB"
- `DB50` (2) - "50 dB"
- `DB55` (3) - "55 dB"
- `DB60` (4) - "60 dB"
- `DB65` (5) - "65 dB"
- `DB70` (6) - "70 dB"

**Methods:**
- `getValue()`: Returns the integer value (0-6)
- `getDisplayName()`: Returns formatted dB string
- `getThresholdInDB()`: Returns actual dB value (40-70 dB in 5 dB increments)
- `getAllValues()`: Returns all values as List
- `fromValue(int value)`: Converts integer to enum

### MaximumPowerOutput 
**Purpose:** Defines maximum power output limiting.

**Values:**
- `MUO` (0) - "Max Undistorted Output (MUO)"
- `DB_MINUS_4` (1) - "-4 dB"
- `DB_MINUS_8` (2) - "-8 dB"
- `DB_MINUS_12` (3) - "-12 dB"
- `DB_MINUS_16` (4) - "-16 dB"
- `DB_MINUS_20` (5) - "-20 dB"
- `DB_MINUS_24` (6) - "-24 dB"

**Methods:**
- `getValue()`: Returns the integer value (0-6)
- `getDisplayName()`: Returns descriptive name
- `getAllValues()`: Returns all values as List
- `fromValue(int value)`: Converts integer to enum

### EqualizerGain 
**Purpose:** Defines equalizer gain values.

**Values:** `DB_MINUS_30` (-30 dB) through `DB0` (0 dB) in 2 dB increments (16 values total)

**Methods:**
- `getValue()`: Returns the integer value (0-15)
- `getDisplayName()`: Returns formatted dB string
- `getValueInDB()`: Returns actual dB value (-30 to 0 dB in 2 dB steps)
- `getAllValues()`: Returns all values as List
- `fromValue(int value)`: Converts integer to enum

### FrequencyBand 
**Purpose:** Defines frequency bands for DSP processing.

**Values:** 12 frequency bands from 250 Hz to 7000 Hz

**Methods:**
- `getValue()`: Returns the integer value (0-11)
- `getFrequency()`: Returns frequency in Hz (250-7000 Hz)
- `getDisplayName()`: Returns formatted frequency string (e.g., "250 Hz")
- `getAllValues()`: Returns all values as List
- `fromValue(int value)`: Converts integer value to enum
- `fromFrequency(int frequency)`: Converts frequency value to enum (static method)

## Common Methods Pattern

All enums follow this consistent pattern:
1. **`getValue()`**: Returns the integer representation
2. **`getDisplayName()`**: Returns human-readable string (fetched from `JinHaoA4DspParameterLabels`)
3. **`getAllValues()`**: Static method returning all enum values as List
4. **`fromValue(int value)`**: Static factory method for integer-to-enum conversion

Specialized methods in specific enums:
- **Value calculation methods**: `getValueInDB()`, `getFrequency()`, `getRatio()`, `getThresholdInDB()`, `getReductionInDB()`
- **Frequency conversion**: `fromFrequency()` in FrequencyBand enum
- **Binary state enums**: `fromValue()` returns ON for value 1, OFF otherwise

## Usage Notes

1. **Parameter Labels Integration**: Display names are retrieved from `JinHaoA4DspParameterLabels` class
2. **Thread Safety**: All methods are thread-safe
3. **Immutable**: All enum instances are immutable
4. **Value Validation**: Factory methods return default values for invalid inputs
5. **Frequency Mapping**: FrequencyBand provides bidirectional conversion between enum and Hz values

## Value Ranges Summary

| Parameter | Min Value | Max Value | Increment | Count |
|-----------|-----------|-----------|-----------|-------|
| Preamp Gain | 0 dB | 30 dB | Variable | 8 |
| Noise Reduction | 0 dB | -13 dB | Variable | 4 |
| Low Cut Filter | 200 Hz | 3000 Hz | Variable | 7 |
| High Cut Filter | 1250 Hz | 8000 Hz | Variable | 7 |
| Compression Ratio | 1.00:1 | 4.00:1 | Variable | 7 |
| Compression Threshold | 40 dB | 70 dB | 5 dB | 7 |
| Equalizer Gain | -30 dB | 0 dB | 2 dB | 16 |
| Frequency Bands | 250 Hz | 7000 Hz | Variable | 12 |