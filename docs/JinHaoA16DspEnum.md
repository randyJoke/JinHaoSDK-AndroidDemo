# JinHaoA16DSPEnum Documentation

## Overview
The `JinHaoA16DspEnum` class provides a comprehensive set of enumerations for managing DSP parameters in JinHao A16 hearing aid devices. All enums follow Java naming conventions and provide utility methods for conversion and display.

## Available Enumerations

### InputMode 
**Purpose:** Defines the input source selection for the DSP.

**Values:**
- `AI1_MIC1` (0) - "AI1 (Mic1)"
- `AI2_DAI_STREAMING` (1) - "AI2 (DAI or Streaming)"
- `AI0_TC` (2) - "AI0 (TC)"
- `AI3_MIC2` (3) - "AI3 (Mic2)"
- `FIXED_CARDIOID` (4) - "Fixed Cardioid (AI1-AI3)"
- `FIXED_SUPER_CARDIOID` (5) - "Fixed SuperCardioid"
- `FIXED_HYPER_CARDIOID` (6) - "Fixed HyperCardioid"
- `ADAPTIVE_CARDIOID` (7) - "Adaptive Cardioid"
- `ADAPTIVE_SUPER_CARDIOID` (8) - "Adaptive SuperCardioid"
- `ADAPTIVE_HYPER_CARDIOID` (9) - "Adaptive HyperCardioid"
- `NOISE_GENERATOR` (10) - "Noise Generator"
- `MIC1_TC` (11) - "MIC1 + TC"
- `MIC1_DAI` (12) - "Mic1 + (DAI or Streaming)"
- `MIC1_NOISE_GENERATOR` (13) - "MIC1 + Noise Generator"
- `FIXED_CARDIOID_STREAMING` (14) - "Fixed Cardioid + Streaming"
- `ADAPTIVE_CARDIOID_STREAMING` (15) - "Adaptive Cardioid + Streaming"

**Methods:**
- `getValue()`: Returns the integer value of the enum constant
- `getDisplayName()`: Returns the human-readable display name
- `getAllValues()`: Static method returning all enum values as a List
- `fromValue(int value)`: Static method to convert integer value to enum constant

### AnalogPreGain 
**Purpose:** Defines analog pre-amplifier gain values.

**Values:** `DB0` (0 dB) through `DB36` (36 dB) in 3 dB increments (13 values total)

**Methods:**
- `getValue()`: Returns the integer value (0-12)
- `getDisplayName()`: Returns formatted string (e.g., "0 dB", "3 dB")
- `getValueInDB()`: Returns the actual dB value (0-36 dB)
- `getAllValues()`: Returns all values as List
- `fromValue(int value)`: Converts integer to enum

### DigitalPreGain (数字前置增益)
**Purpose:** Defines digital pre-amplifier gain values.

**Values:**
- `DB0` (0 dB)
- `DB6` (6 dB)
- `DB12` (12 dB)
- `DB18` (18 dB)

**Methods:**
- `getValue()`: Returns the integer value (0-3)
- `getDisplayName()`: Returns formatted string (e.g., "0 dB", "6 dB")
- `getValueInDB()`: Returns the actual dB value (0, 6, 12, or 18 dB)
- `getAllValues()`: Returns all values as List
- `fromValue(int value)`: Converts integer to enum

### MatrixGain (矩阵增益)
**Purpose:** Defines matrix gain values from 0 dB to -47 dB.

**Values:** `DB0` (0 dB) through `DB_MINUS_47` (-47 dB) in 1 dB steps (48 values total)

**Methods:**
- `getValue()`: Returns the integer value (0-47)
- `getDisplayName()`: Returns formatted string (e.g., "0 dB", "-15 dB")
- `getValueInDB()`: Returns the actual dB value (0 to -47 dB)
- `getAllValues()`: Returns all values as List
- `fromValue(int value)`: Converts integer to enum

### FeedbackCanceler (反馈消除)
**Purpose:** Defines feedback cancellation levels.

**Values:**
- `OFF` (0) - "OFF"
- `NORMAL` (1) - "Normal"
- `AGGRESSIVE` (2) - "Aggressive"

**Methods:**
- `getValue()`: Returns the integer value (0-2)
- `getDisplayName()`: Returns the descriptive name
- `getAllValues()`: Returns all values as List
- `fromValue(int value)`: Converts integer to enum

### NoiseReduction
**Purpose:** Defines noise reduction levels.

**Values:**
- `OFF` (0) - "Off"
- `LOW` (1) - "Low"
- `MEDIUM` (2) - "Medium"
- `HIGH` (3) - "High"
- `MAX` (4) - "Max"

**Methods:**
- `getValue()`: Returns the integer value (0-4)
- `getDisplayName()`: Returns the descriptive name
- `getAllValues()`: Returns all values as List
- `fromValue(int value)`: Converts integer to enum

### SwitchState 
**Purpose:** Defines binary switch states.

**Values:**
- `OFF` (0) - "Off"
- `ON` (1) - "On"

**Methods:**
- `getValue()`: Returns the integer value (0 or 1)
- `getDisplayName()`: Returns "Off" or "On"
- `getAllValues()`: Returns all values as List
- `fromValue(int value)`: Converts integer to enum

### LowCutFilter 
**Purpose:** Defines low-cut (high-pass) filter frequencies.

**Values:**
- `OFF` (0) - "Off"
- `HZ250` (1) - "250 Hz"
- `HZ500` (2) - "500 Hz"
- `HZ750` (3) - "750 Hz"
- `HZ1000` (4) - "1000 Hz"
- `HZ1250` (5) - "1250 Hz"
- `HZ1500` (6) - "1500 Hz"
- `HZ2000` (7) - "2000 Hz"
- `HZ2500` (8) - "2500 Hz"
- `HZ3000` (9) - "3000 Hz"

**Methods:**
- `getValue()`: Returns the integer value (0-9)
- `getDisplayName()`: Returns formatted string
- `getFrequency()`: Returns frequency in Hz (null for OFF)
- `getAllValues()`: Returns all values as List
- `fromValue(int value)`: Converts integer to enum

### RemoteMixRatio 
**Purpose:** Defines remote microphone mix ratios.

**Values:**
- `DB0` (0) - "0 dB"
- `DB_MINUS_3` (1) - "-3 dB"
- `DB_MINUS_6` (2) - "-6 dB"
- `DB_MINUS_9` (3) - "-9 dB"
- `DB_MINUS_12` (4) - "-12 dB"

**Methods:**
- `getValue()`: Returns the integer value (0-4)
- `getDisplayName()`: Returns formatted dB string
- `getValueInDB()`: Returns actual dB value (0, -3, -6, -9, or -12 dB)
- `getAllValues()`: Returns all values as List
- `fromValue(int value)`: Converts integer to enum

### MPOAttackTime 
**Purpose:** Defines Maximum Power Output attack times.

**Values:**
- `MS10` (0) - "10 ms"
- `MS20` (1) - "20 ms"
- `MS30` (2) - "30 ms"
- `MS40` (3) - "40 ms"

**Methods:**
- `getValue()`: Returns the integer value (0-3)
- `getDisplayName()`: Returns formatted time string
- `getTimeInMS()`: Returns time in milliseconds
- `getAllValues()`: Returns all values as List
- `fromValue(int value)`: Converts integer to enum

### MPOReleaseTime
**Purpose:** Defines Maximum Power Output release times.

**Values:**
- `MS40` (0) - "40 ms"
- `MS80` (1) - "80 ms"
- `MS150` (2) - "150 ms"
- `MS350` (3) - "350 ms"

**Methods:**
- `getValue()`: Returns the integer value (0-3)
- `getDisplayName()`: Returns formatted time string
- `getTimeInMS()`: Returns time in milliseconds
- `getAllValues()`: Returns all values as List
- `fromValue(int value)`: Converts integer to enum

### CompressionRatio 
**Purpose:** Defines audio compression ratios from 1.00:1 to 8.00:1.

**Values:** 36 different compression ratio values with precise increments

**Methods:**
- `getValue()`: Returns the integer value (0-35)
- `getDisplayName()`: Returns formatted ratio string (e.g., "1.00 : 1", "2.00 : 1")
- `getRatioValue()`: Returns the ratio as a double value
- `getAllValues()`: Returns all values as List
- `fromValue(int value)`: Converts integer to enum

### CompressionThreshold 
**Purpose:** Defines compression activation thresholds.

**Values:** `DB20` (20 dB) through `DB82` (82 dB) in 2 dB increments (32 values total)

**Methods:**
- `getValue()`: Returns the integer value (0-31)
- `getDisplayName()`: Returns formatted dB string
- `getThresholdInDB()`: Returns actual dB value (20-82 dB)
- `getAllValues()`: Returns all values as List
- `fromValue(int value)`: Converts integer to enum

### CompressionConstant
**Purpose:** Defines compression time constants.

**Values:** `CONSTANT0` through `CONSTANT8` (9 different time constant configurations)

**Methods:**
- `getValue()`: Returns the integer value (0-8)
- `getDisplayName()`: Returns descriptive time constant string
- `getAllValues()`: Returns all values as List
- `fromValue(int value)`: Converts integer to enum

### MaximumPowerOutput 
**Purpose:** Defines maximum power output limiting.

**Values:**
- `OFF` (0) - "Off"
- `MUO` (1) - "Max Undistorted Output (MUO)"
- `DB_MINUS_2` (2) through `DB_MINUS_28` (15) - Various dB attenuation levels

**Methods:**
- `getValue()`: Returns the integer value (0-15)
- `getDisplayName()`: Returns descriptive name
- `getAllValues()`: Returns all values as List
- `fromValue(int value)`: Converts integer to enum

### EqualizerGain 
**Purpose:** Defines equalizer gain values.

**Values:** `DB_MINUS_40` (-40 dB) through `DB0` (0 dB) in 2 dB increments (21 values total)

**Methods:**
- `getValue()`: Returns the integer value (0-20)
- `getDisplayName()`: Returns formatted dB string
- `getValueInDB()`: Returns actual dB value (-40 to 0 dB)
- `getAllValues()`: Returns all values as List
- `fromValue(int value)`: Converts integer to enum

### FrequencyBand
**Purpose:** Defines frequency bands for DSP processing.

**Values:** 16 frequency bands from 250 Hz to 7500 Hz

**Methods:**
- `getValue()`: Returns the integer value (0-15)
- `getDisplayName()`: Returns formatted frequency string (e.g., "250 Hz")
- `getFrequency()`: Returns frequency in Hz
- `getAllValues()`: Returns all values as List
- `fromValue(int value)`: Converts integer value to enum
- `fromFrequency(int frequency)`: Converts frequency value to enum (static method)

## Common Methods Pattern

All enums follow this consistent pattern:
1. **`getValue()`**: Returns the integer representation
2. **`getDisplayName()`**: Returns human-readable string
3. **`getAllValues()`**: Static method returning all enum values as List
4. **`fromValue(int value)`**: Static factory method for integer-to-enum conversion

Some enums also include:
- **Value calculation methods** (e.g., `getValueInDB()`, `getFrequency()`)
- **Specialized factory methods** (e.g., `fromFrequency()` in FrequencyBand)

## Usage Notes

1. **Thread Safety**: All methods are thread-safe
2. **Immutable**: All enum instances are immutable
3. **Type Safety**: Strong type checking prevents invalid parameter values
4. **Internationalization**: Display names are currently English only
5. **Performance**: All factory methods use efficient lookup mechanisms