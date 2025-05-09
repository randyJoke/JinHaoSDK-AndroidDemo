### JinHaoRunTimeHistory

| Field Name        | Type   | Description                                   | Value Range      |
|-------------------|--------|-----------------------------------------------|------------------|
| lessHalfAnHour    | UInt32 | Run time less than 0.5 hour                   | 0 ~ 0xFFFFFFFF   |
| between05And2     | UInt32 | Run time between 0.5 and 2 hours              | 0 ~ 0xFFFFFFFF   |
| between2And4      | UInt32 | Run time between 2 and 4 hours                | 0 ~ 0xFFFFFFFF   |
| between4And8      | UInt32 | Run time between 4 and 8 hours                | 0 ~ 0xFFFFFFFF   |
| between8And12     | UInt32 | Run time between 8 and 12 hours               | 0 ~ 0xFFFFFFFF   |
| between12And16    | UInt32 | Run time between 12 and 16 hours              | 0 ~ 0xFFFFFFFF   |
| between16And20    | UInt32 | Run time between 16 and 20 hours              | 0 ~ 0xFFFFFFFF   |
| greaterThan20     | UInt32 | Run time greater than 20 hours                | 0 ~ 0xFFFFFFFF   |

### JinHaoProgramRunDataLog

| Field Name       | Type   | Description                                   | Value Range      |
|------------------|--------|-----------------------------------------------|------------------|
| totalRunTime     | UInt32 | Total run time for this program               | 0 ~ 0xFFFFFFFF   |
| vcIndex          | UInt32 | Index of volume control                       | 0 ~ 0xFFFFFFFF   |
| lessThan50       | UInt32 | Percentage usage < 50dB                        | 0 ~ 0xFFFFFFFF   |
| between50And60   | UInt32 | Percentage usage between 50dB and 60dB          | 0 ~ 0xFFFFFFFF   |
| between60And70   | UInt32 | Percentage usage between 60dB and 70dB          | 0 ~ 0xFFFFFFFF   |
| between70And80   | UInt32 | Percentage usage between 70dB and 80dB         | 0 ~ 0xFFFFFFFF   |
| greaterThan80    | UInt32 | Percentage usage > 80dB                       | 0 ~ 0xFFFFFFFF   |

### JinHaoSummaryDataLog

| Field Name            | Type                          | Description                                           | Value Range      |
|-----------------------|-------------------------------|-------------------------------------------------------|------------------|
| totalRunTime          | UInt32                        | Total Run Time (since reset)				                           | 0 ~ 0xFFFFFFFF   |
| runTimeSincePowerUp   | UInt32                        | Run Time since power up				                           | 0 ~ 0xFFFFFFFF   |
| numberOfBatteryChanges| UInt32                        | Number of battery changes                               | 0 ~ 0xFFFFFFFF   |
| batteryChangeHours    | UInt32                        | Hours accumulated before battery changes              | 0 ~ 0xFFFFFFFF   |
| lowBatteryFlag        | UInt32                        | Whether a low battery was detected (0 or 1 expected)  | 0 ~ 0xFFFFFFFF   |
| program               | [[JinHaoProgramRunDataLog](#jinhaoprogramrundatalog)]   | Array of 6 program run data logs                      | -                |
| runTimeHistory        | [JinHaoRunTimeHistory](#jinhaoruntimehistory)          | Runtime distribution history                          | -                |
| unUsedWord            | UInt32                        | Reserved                                              | 0 ~ 0xFFFFFFFF   |
| CRC                   | UInt32                        | CRC checksum                                          | 0 ~ 0xFFFFFFFF   |