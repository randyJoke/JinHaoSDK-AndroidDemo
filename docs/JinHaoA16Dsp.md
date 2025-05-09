# JinHaoA16Dsp

This class provides various methods to configure and retrieve DSP parameter data for hearing aids.

## Methods

| Method Name                         | Description                                                     | Parameters                                                                 | Return Value                                                   |
|-------------------------------------|-----------------------------------------------------------------|--------------------------------------------------------------------------|---------------------------------------------------------------|
| `setNoise(NOISE noise)`             | Set the noise reduction level.                                 | `noise`: Noise reduction level (OFF=0, WEAK=1, MEDIUM=3, STRONG=4)       | None                                                          |
| `getNoise()`                        | Get the current noise reduction level.                          | None                                                                     | `OFF` (0), `WEAK` (1), `MEDIUM` (3), `STRONG` (4), `UNKNOWN` (-1) |
| `setDirection(DIRECTION direction)` | Set the directional mode.                                      | `direction`: Directional mode (NORMAL=0, TV=4, METTING=8, FACE=9)         | None                                                          |
| `getDirection()`                    | Get the current directional mode.                               | None                                                                     | `NORMAL` (0), `TV` (4), `METTING` (8), `FACE` (9), `UNKNOWN` (-1) |
| `getFrequences()`                   | Get the supported frequency channels.                           | None                                                                     | Array: `[250, 500, 1000, 1500, 2000, 2500, 3000, 3500, 4000, 4500, 5000, 5500, 6000, 6500, 7000, 7500]` |
| `setEq(int frequence, int eq)`      | Set the EQ value for a specific frequency.                      | `frequence`: Frequency, `eq`: EQ value (0-20)                            | None                                                          |
| `getEq(int frequence)`              | Get the EQ value for a specific frequency.                      | `frequence`: Frequency                                                   | EQ value (0-20) or -1 (if frequency not available)             |
| `getMinEqValue()`                   | Get the minimum EQ value.                                       | None                                                                     | 0                                                             |
| `getMaxEqValue()`                   | Get the maximum EQ value.                                       | None                                                                     | 20                                                            |
| `setCompressThresholdLevel(int frequence, int ct)` | Set the compression threshold level for a specific frequency. | `frequence`: Frequency, `ct`: Compression threshold level (0-31)        | None                                                          |
| `getCompressThresholdLevel(int frequence)` | Get the compression threshold level for a specific frequency.   | `frequence`: Frequency                                                   | Compression threshold level (0-31) or -1 (if frequency not available) |
| `getCompressThresholdValue(int compressThresholdLevel)` | Get the compression threshold value. | `compressThresholdLevel`: Compression threshold level (0-31)             | Compression threshold value (20 + `compressThresholdLevel` * 2) |
| `setCompressRatioLevel(int frequence, int compressRatioLevel)` | Set the compression ratio level for a specific frequency. | `frequence`: Frequency, `compressRatioLevel`: Compression ratio level (0-31) | None                                                          |
| `getCompressRatioLevel(int frequence)` | Get the compression ratio level for a specific frequency.      | `frequence`: Frequency                                                   | Compression ratio level (0-31) or -1 (if frequency not available) |
| `getCompressRatioValue(int compressRatioLevel)` | Get the compression ratio value. | `compressRatioLevel`: Compression ratio level (0-31)                     | Compression ratio value (1.00 to 8.00)                        |
| `setMpoLevel(int frequence, int mpoLevel)` | Set the maximum output level for a specific frequency.       | `frequence`: Frequency, `mpoLevel`: Maximum output level (0-11)          | None                                                          |
| `getMpoLevel(int frequence)`        | Get the maximum output level for a specific frequency.          | `frequence`: Frequency                                                   | Maximum output level (0-11) or -1 (if frequency not available) |
| `getMpoValue(int mpoLevel)`         | Get the maximum output value based on the MPO level.            | `mpoLevel`: Maximum output level (0-11)                                  | Maximum output value (-20 to 0)                               |
| `setAttackTimeLevel(int attackTimeLevel)` | Set the attack time level.                                       | `attackTimeLevel`: Attack time level (0-3)                               | None                                                          |
| `getAttackTimeLevel()`              | Get the attack time level.                                      | None                                                                     | Attack time level (0-3)                                       |
| `getAttackTimeValue(int attackTimeLevel)` | Get the attack time value based on the attack time level.       | `attackTimeLevel`: Attack time level (0-3)                               | Attack time value (2, 5, 10, 20 milliseconds)                      |
| `setReleaseTimeLevel(int releaseTimeLevel)` | Set the release time level.                                      | `releaseTimeLevel`: Release time level (0-3)                             | None                                                          |
| `getReleaseTimeLevel()`             | Get the release time level.                                     | None                                                                     | Release time level (0-3)                                      |
| `getReleaseTimeValue(int releaseTimeLevel)` | Get the release time value based on the release time level.     | `releaseTimeLevel`: Release time level (0-3)                             | Release time value (30, 60, 120, 240 milliseconds)                 |


## MPO
| Level                         | Value         | Description
|-------------------------------|----------------|-------------------------------------------------|
| 0                         | 0             |  off |
| 1                         | -1            |  MUO |
| 2                         | -2             | None|
| 3                         | -4              | None| 
| 4                         | -6              | None| 
| 5                         | -8              | None| 
| 6                         | -10             | None| 
| 7                         | -12             | None| 
| 8                         | -14             | None| 
| 9                         | -16             | None| 
| 10                         | -18            | None| 
| 11                         | -20            | None| 


## Compression Threshold
| Level                         | Value         | Description
|-------------------------------|----------------|-------------------------------------------------|
| 0                         | 20             |  None |
| 1                         | 22            |  None |
| 2                         | 24             | None|
| 3                         | 26              | None| 
| 4                         | 28              | None| 
| 5                         | 30              | None| 
| 6                         | 32             | None| 
| 7                         | 34             | None| 
| 8                         | 36             | None| 
| 9                         | 38             | None| 
| 10                         | 40            | None| 
| 11                         | 42            | None| 
| 12                         |44            | None|
| 13                         | 46              | None| 
| 14                         | 48             | None| 
| 15                         | 50              | None| 
| 16                         | 52             | None| 
| 17                         | 54             | None| 
| 18                         | 56             | None| 
| 19                         | 58             | None| 
| 20                         | 60            | None| 
| 21                         | 62            |  None |
| 22                         | 64             | None|
| 23                         | 66              | None| 
| 24                         | 68              | None| 
| 25                         | 70              | None| 
| 26                         | 72             | None| 
| 27                         | 74             | None| 
| 28                         | 76             | None| 
| 29                         | 78             | None| 
| 30                         | 80            | None| 
| 31                         | 82            | None| 

## Compression Ratio
| Level                         | Value         | Description
|-------------------------------|----------------|-------------------------------------------------|
| 0                         | 1             |  None |
| 1                         | 1.03            |  None |
| 2                         | 1.05             | None|
| 3                         | 1.08             | None| 
| 4                         | 1.11              | None| 
| 5                         | 1.14              | None| 
| 6                         | 1.18             | None| 
| 7                         | 1.21             | None| 
| 8                         | 1.25             | None| 
| 9                         | 1.29            | None| 
| 10                         | 1.33           | None| 
| 11                         | 1.38            | None| 
| 12                         |1.43            | None|
| 13                         | 1.48              | None| 
| 14                         | 1.54             | None| 
| 15                         | 1.60              | None| 
| 16                         | 1.67             | None| 
| 17                         | 1.74             | None| 
| 18                         | 1.82             | None| 
| 19                         | 1.90             | None| 
| 20                         | 2.00           | None| 
| 21                         | 2.11            |  None |
| 22                         | 2.22             | None|
| 23                         | 2.35              | None| 
| 24                         | 2.50              | None| 
| 25                         | 2.67              | None| 
| 26                         | 2.86             | None| 
| 27                         | 3.08             | None| 
| 28                         | 3.33             | None| 
| 29                         | 3.64             | None| 
| 30                         | 4.00            | None| 
| 31                         | 4.44            | None| 
| 32                         | 5.00             | None| 
| 33                        | 5.71             | None| 
| 34                         | 6.67            | None| 
| 35                        | 8.00            | None| 

## Attack Time
| Level                         | Value         | Description
|-------------------------------|----------------|-------------------------------------------------|
| 0                         | 2             |  ms |
| 1                         |5            |  ms |
| 2                         |10             | ms|
| 3                         | 20              | ms| 

## Release Time
| Level                         | Value         | Description
|-------------------------------|----------------|-------------------------------------------------|
| 0                         | 30             |  ms |
| 1                         | 60            |  ms |
| 2                         | 120             | ms|
| 3                         | 240              | ms| 
