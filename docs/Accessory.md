# Accessory 

## Overview

The `Accessory` class serves as a base class for BLE (Bluetooth Low Energy) devices. It provides common functionality for establishing a connection, handling Bluetooth GATT operations, and managing the state of a BLE device. This class is designed to be extended by specific devices like `JinHaoAccessory`, which implements the BLE communication protocol for hearing aids.

`JinHaoAccessory` inherits from `Accessory` and adds device-specific functionality for controlling and interacting with hearing aid devices. This includes managing Bluetooth connections, handling characteristic reads and writes, and managing communication with the hearing aid's GATT services.


## Public Methods

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
