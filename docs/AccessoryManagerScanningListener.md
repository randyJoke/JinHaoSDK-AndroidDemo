# AccessoryManagerScanningListener Interface

The `AccessoryManagerScanningListener` interface provides methods to monitor and handle accessory scanning events in the `AccessoryManager`.

## Methods

| Method Name | Description |
|-------------|-------------|
| `accessoryManagerIsScanning(AccessoryManager manager, boolean isScanning)` | Notifies whether the [accessory manager](AccessoryManager.md) is currently scanning. |
| `accessoryManagerDidDiscover(AccessoryManager manager, Accessory device, Integer rssi)` | Called when a new [accessory](Accessory.md) is discovered during scanning. |
| `accessoryManagerDidUpdate(AccessoryManager manager, Accessory device, Integer rssi)` | Called when an [accessory](Accessory.md) is updated during scanning. |
