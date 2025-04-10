## JinHaoAccessoryListener Interface

The `JinHaoAccessoryListener` interface extends the `AccessoryListener` interface and is specifically designed for the `JinHaoAccessory` class, which represents a hearing aid device. This interface provides callback methods to notify the application about various events related to the hearing aid's state, including battery changes, value updates, and changes in program or volume.

### Methods

| Method | Description |
|--------|-------------|
| `deviceBatteryChanged(JinHaoAccessory device, int bat)` | Notifies the application when the hearing aid's battery level changes. The `bat` parameter represents the new battery level. |
| `deviceDidUpdateValue(JinHaoAccessory device)` | Notifies the application when the hearing aid's values are updated (e.g., volume, program, or other settings). |
| `didChangedProgramByAid(JinHaoAccessory device, int previous, int current)` | Notifies the application when the program setting on the hearing aid changes. The `previous` parameter is the previous program, and `current` is the new program. |
| `didChangedVolumeByAid(JinHaoAccessory device, int previous, int current)` | Notifies the application when the volume setting on the hearing aid changes. The `previous` parameter is the previous volume level, and `current` is the new volume level. |