# JinHaoAccessory

The `JinHaoAccessory` class inherits from the [Accessory](Accessory.md) class and is used to manage and control the connection, data transfer, and configuration of JinHao hearing aid devices. 


## Override Methods

| Method | Description |
|--------|-------------|
| **setAlphaGattCallback(BluetoothGattCallback alphaGattCallback)** | Sets a callback for GATT operations. |
| **getAlphaGattCallback() : BluetoothGattCallback** | Gets the current GATT callback. |
| **setAccessoryState(int accessoryState)** | Sets the current state of the accessory and notifies the listener. |
| **getAccessoryState() : int** | Returns the current state of the accessory. |
| **getBluetoothDevice() : BluetoothDevice** | Returns the Bluetooth device associated with the accessory. |
| **getGatt() : BluetoothGatt** | Returns the Bluetooth GATT object associated with the accessory. |
| **getAddress() : String** | Returns the address of the Bluetooth device. |
| **getDeviceName() : String** | Returns the name of the Bluetooth device. |
| **isConnected() : boolean** | Returns `true` if the accessory is connected, otherwise `false`. |
| **update(BluetoothDevice bluetoothDevice, ScanRecord scanRecord)** | Updates the accessory's Bluetooth device and scan record. |
| **connect(AccessoryListener listener) : boolean** | Starts the connection process and notifies the listener upon connection. |
| **connect(int duration, AccessoryListener listener) : boolean** | Starts the connection process with a timeout duration. |
| **disconnect(boolean force) : boolean** | Disconnects the accessory. If `force` is `true`, it closes the GATT connection immediately. |
| **setListener(AccessoryListener mListener)** | Sets the listener for accessory state changes and notifications. |
| **getListener() : AccessoryListener** | Returns the current listener associated with the accessory. |

### Setter

| Method Name                  | Parameters                                       | Description                              |
|------------------------------|--------------------------------------------------|------------------------------------------|
| ~~`setMute~~`~~                     | ~~`boolean mute`~~                                   | ~~Sets the mute state of the device~~         |
| `setGlobalDsp`                | `JinHaoGlobalDsp globalDsp`                      | Sets [the global DSP](JinHaoGlobalDsp.md) settings              |
| `setProgram`                  | `int program`                                    | Sets the current program mode          |
| `setVolume`                   | `int program, int volume`                        | Sets the volume for a specific program mode |
| `setDsp`                      | `int program, JinHaoDsp dsp`                     | Sets [the DSP](JinHaoDsp.md) settings for a specific program mode |
| ~~`setPairCode`~~                 | ~~`String pairCode`~~                                | ~~Sets the pairing~~ code                    |

### Getter

| Method Name                  | Parameters                                       | Description                              |
|------------------------------|--------------------------------------------------|------------------------------------------|
| `getOrientation`              | None                                             | Retrieves the device orientation (left ear, right ear, or undefined) |
| `getHearChip`                 | None                                             | Retrieves the hearing chip model of the device |
| `getBleChip`                  | None                                             | Retrieves the Bluetooth chip model of the device |
| `getBat`                      | None                                             | Retrieves the current battery level      |
| `getProgram`                  | None                                             | Retrieves the current program mode       |
| `getVolume`                   | None                                             | Retrieves the volume for the current program mode |
| `getDsp`                      | None                                             | Retrieves the DSP settings for the current program mode |
| `getGlobalDsp`                | None                                             | Retrieves the global DSP settings        |
| `getProfile`                  | None                                             | Retrieves the device configuration profile |
| `getNumberOfProgram`    | `Int?`                             | The total number of available programs, including global programs.           |
| `getScenesOfProgram`    | [[JinHaoProgram](JinHaoProgram.md)]                  | An array of programs in the accessory's profile.                             |

### Data Execution

| Method Name                  | Parameters                                       | Description                              |
|------------------------------|--------------------------------------------------|------------------------------------------|
| `excute`                      | `JinHaoRequest request, int timeout, Consumer<JinHaoResult> complete` | Executes a [request](JinHaoRequest.md) with a timeout         |
| `excute`                      | `JinHaoRequest request, Consumer<JinHaoResult> complete` | Executes a [request](JinHaoRequest.md) with default timeout of 5000ms |
