### JinHaoRunTimeHistory

| Field Name        | Type   | Description                                   | Value Range      |
|-------------------|--------|-----------------------------------------------|------------------|
| lessHalfAnHour    | int | Num occurrences of runs less than 0.5 hour                   | 0 ~ 32767   |
| between05And2     | int | Num occurrences of runs between 0.5 and 2 hours              | 0 ~ 32767   |
| between2And4      | int | Num occurrences of runs between 2 and 4 hours                | 0 ~ 32767   |
| between4And8      | int | Num occurrences of runs between 4 and 8 hours                | 0 ~ 32767   |
| between8And12     | int | Num occurrences of runs between 8 and 12 hours               | 0 ~ 32767   |
| between12And16    | int | Num occurrences of runs between 12 and 16 hours              | 0 ~ 32767   |
| between16And20    | int | Num occurrences of runs between 16 and 20 hours              | 0 ~ 32767   |
| greaterThan20     | int | Num occurrences of runs greater than 20 hours                | 0 ~ 32767   |

### JinHaoProgramRunDataLog

| Field Name       | Type   | Description                                   | Value Range      |
|------------------|--------|-----------------------------------------------|------------------|
| totalRunTime     | int | Total run time of the program (in minutes)               | 0 ~ 32767   |
| vcIndex          | int | cumlutive VC index  		                      | 0 ~ 32767   |
| lessThan50       | int | Num Occurrences @ <50 dB				              | 0 ~ 32767   |
| between50And60   | int | Num Occurrences 50db<X>60db					          | 0 ~ 32767   |
| between60And70   | int | Num Occurrences 60db<X>70db                   | 0 ~ 32767   |
| between70And80   | int | Num Occurrences 70db<X>80db                   | 0 ~ 32767   |
| greaterThan80    | int | Num Occurrences > 80db                        | 0 ~ 32767   |

### JinHaoSummaryDataLog

| Field Name            | Type                          | Description                                           | Value Range      |
|-----------------------|-------------------------------|-------------------------------------------------------|------------------|
| totalRunTime          | int                        | Total run time since reset (in minutes)		                           | 0 ~ 32767   |
| runTimeSincePowerUp   | int                        | Run Time since power up				                           | 0 ~ 32767   |
| numberOfBatteryChanges| int                        | Number of battery changes                               | 0 ~ 32767   |
| batteryChangeHours    | int                        | Hours accumulated before battery changes              | 0 ~ 32767   |
| lowBatteryFlag        | int                        | Whether a low battery was detected (0 or 1 expected)  | 0 ~ 32767   |
| program               | [[JinHaoProgramRunDataLog](#jinhaoprogramrundatalog)]   | Array of 6 program run data logs                      | -                |
| runTimeHistory        | [JinHaoRunTimeHistory](#jinhaoruntimehistory)          | Runtime distribution history                          | -                |
| unUsedWord            | int                        | Reserved                                              | 0 ~ 32767   |
| CRC                   | int                        | CRC checksum                                          | 0 ~ 32767   |
