# JinHaoSDK 
The JinHaoSDK provides a set of APIs to communicate with Bluetooth Low Energy (BLE) hearing aids. The SDK allows you to connect to hearing aids, adjust volume, switch hearing modes, and manage sound settings like equalizer (EQ) and noise cancellation.
This guide will walk you through the steps to integrate the SDK into your Android app and demonstrate how to use the SDK’s key functionalities.
## Index
- [Requirements](#requirements)
- [Installation](#installation)
- [Usage](#usage)
    - [Permissions](#permissions)
    - [Initialization](#initialization)
    - [Scan](#scan)
    - [Connect](#connect)
    - [Sending Commands to Hearing Aid](#sending-commands-to-hearing-aid)
        - [Reading Hearing Aid Information](#reading-hearing-aid-information)
        - [Adjust Volume](#adjust-volume)
        - [Switching Programs](#switching-programs)
        - [Frequence](#frequence)
        - [EQ](#eq)
        - [MPO](#mpo)
        - [Noise](#noise)
        - [Direction](#change-direction)
        - [Compression Threshold](#compression-threshold)
        - [Compression Ratio](#compression-ratio)
        - [Attack Time](#attack-time)
        - [Release Time](#release-time)
        - [Data Log](#data-log)
        - [Read Battery](#read-battery)
        - [Notify](#notify)
- [API Document](#api-document)

## Requirements
- **Android 8.0+**
- **Minimum Java version: Java 8**

## Installation

### Step 1: Place the AAR File in the Project
1. Create a `libs` directory in your project’s `app` folder if it doesn’t exist already.
2. Place the `.aar` file in the `libs` directory.

### Step 2: Modify the`settings.gradle.kts` File

1. Add the following code to include JinHaoSDK
```gradle
include(":JinHaoSDK")
```
### Step 3: Modify the`build.gradle.kts` File in your project’s `app` folder
```
dependencies {
    implementation(project(":JinHaoSDK"))
}
```

## Usage

### Permissions
Android requires additional permissions declared in the manifest for an app to run a BLE scan since API 23 (6.0 / Marshmallow) and perform a BLE connection since API 31 (Android 12). 
```
    <uses-permission
        android:name="android.permission.BLUETOOTH"
        android:maxSdkVersion="30" />
    <uses-permission
        android:name="android.permission.BLUETOOTH_ADMIN"
        android:maxSdkVersion="30" />
    <uses-permission
        android:name="android.permission.BLUETOOTH_SCAN"
        android:usesPermissionFlags="neverForLocation" />
    <uses-permission android:name="android.permission.BLUETOOTH_ADVERTISE" />
    <uses-permission android:name="android.permission.BLUETOOTH_CONNECT" />
    <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
```
Before starting a device scan, we must request the necessary Bluetooth permissions by [BluetoothPermissionHelper](docs/BluetoothPermissionHelper.md). 
```kotlin
// Checking and requesting Bluetooth permissions
BluetoothPermissionHelper.checkAndRequestBluetoothPermissions(this@MainActivity);

// Handling permission request result
override fun onRequestPermissionsResult(
    requestCode: Int,
    permissions: Array<out String>,
    grantResults: IntArray
) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults)

    if (requestCode === BluetoothPermissionHelper.REQUEST_CODE_BLUETOOTH) {
        /**
         * Checks whether the user has granted the required Bluetooth permissions.
         * If the permissions are denied, device scanning, connection, and operations
         * will not be possible.
         */
        val permissionsGranted = BluetoothPermissionHelper.handlePermissionsResult(
            requestCode, permissions, grantResults
        )
        if (permissionsGranted) {
            // Permissions granted, Bluetooth operations can now begin.
            Toast.makeText(this, "Bluetooth permissions is ok", Toast.LENGTH_SHORT)
                .show()
        } else {
            // Permissions not granted, please prompt the user.
            Toast.makeText(this, "Bluetooth permissions are required", Toast.LENGTH_SHORT)
                .show()
        }
    }
}
```

### Initialization
First, we need to create an instance of [AccessoryManager](AccessoryManager.md#accessorymanager-class) and assign the appropriate delegates. This will enable the app to manage Bluetooth accessory scanning and track its status.

```swift
// Create an instance of AccessoryManager
val accessoryManager = AccessoryManager(this)

// Set the scanning listener to handle device scanning events
accessoryManager.setScanningListener(this)

// Set the status delegate to monitor the Bluetooth status
accessoryManager.setStatusListener(this)

```
 [AccessoryManagerStatusListener](docs/AccessoryManagerStatusListener.md) will be called when the Bluetooth status changes
 ```
/**
	* Notifies whether the bluetooth power is opened (Bluetooth is enabled and ready to scan)
	*/
override fun accessoryManager(manager: AccessoryManager?, isAvailable: Boolean) {
	if (isAvailable) {

	} else {

	}
}
```
### Scan
The `startScan()` or `startScan(int duration)` method in the [AccessoryManager](docs/AccessoryManager.md) class is used to initiate the scanning process for nearby Bluetooth devices. Once called, it begins searching for Bluetooth accessories within range. This method works in conjunction with the `AccessoryManagerScanningListener` methods to handle the scanning and discovery of devices.

```
 if (BluetoothPermissionHelper.checkAndRequestBluetoothPermissions(this@MainActivity)){
     accessoryManager.clearAccessories()
     accessoryManager.startScan(5)
}
```
[AccessoryManagerScanningListener](docs/AccessoryManagerScanningListener.md) will be called when scanning
```
/**
     * Callback from `AccessoryManagerScanningListener` to monitor changes
     * in the current scanning state.
     */
    override fun accessoryManagerIsScanning(manager: AccessoryManager?, isScanning: Boolean) {
        loadingState.value = isScanning;
    }

    /**
     * Callback from `AccessoryManagerScanningListener` triggered when
     * a new device is discovered during scanning.
     */
    override fun accessoryManagerDidDiscover(
        manager: AccessoryManager?,
        device: Accessory?,
        rssi: Int?
    ) {
        if (device is JinHaoAccessory) {
            Log.w("tag", String.format("name=${device.deviceName}, address=${device.address}"))
            accessorys.add(device)
        }
    }

    /**
     * Callback from `AccessoryManagerScanningListener` triggered
     * when an already discovered device's information is updated.
     */
    override fun accessoryManagerDidUpdate(
        manager: AccessoryManager?,
        device: Accessory?,
        rssi: Int?
    ) {

    }
```

### Connect
- You can use the [JinHaoAccessory](docs/JinHaoAccessory.md) object to call the `connect()` method to establish the connection.
```
val connect = device?.connect(this@DetailActivity)
```
- [JinHaoAccessoryListener](docs/JinHaoAccessoryListener.md) will be called when the Accessory device's connection state changes or when an error occurs during connection attempts.
```
   /**
     * Callback triggered when the device's state changes.
     */
    override fun deviceDidUpdate(device: Accessory?, state: Int) {

    }

    /**
     *  Callback when the device successfully connects
     */
    override fun deviceDidConnect(device: Accessory?) {
        runOnUiThread {
            connectedState.value = true
            Toast.makeText(this, "connected", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     *  Callback when the device disconnects
     */
    override fun deviceDidDisconnect(device: Accessory?) {
        runOnUiThread {
            connectedState.value = false
            Toast.makeText(this, "disconnected", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Callback when the device fails to connect
     */
    override fun deviceDidFailToConnect(device: Accessory?, errorCode: Int) {
        runOnUiThread {
            connectedState.value = false
            Toast.makeText(this, "connection failed", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * This callback is called when the device has discovered its services.
     * At this point, you can retrieve device information such as the current Program, Volume,
     * or DSP data for each Program.
     */
    override fun deviceDidDiscoverServices(device: Accessory?, services: MutableList<AccessoryService>?) {
        if (device is JinHaoAccessory) {
            device.excute(JinHaoRequest.readProgramVolume(), Consumer {
                if (it.isError) {
                    Log.w(tag, "Failed to read program volume")
                } else {
                    Log.w(tag, "Successfully read program volume")
                }
            })

            //We can access the total number of programs in the hearing aid, but no more than four.
            device.excute(JinHaoRequest.readNumberOfProgram(device.hearChip), Consumer {
                if (it.isError) {
                    Log.w(tag, "Failed to read number of program")

                    var numberOfPragram = device.numberOfProgram
                    device.excute(JinHaoRequest.readDsp(0), Consumer {
                        Log.w(tag, "Finished reading program 0 DSP file, current scene is ${device.scenesOfProgram.get(0)}")
                    })
                    device.excute(JinHaoRequest.readDsp(numberOfPragram-1), Consumer {
                        Log.w(tag, "Finished reading program lastest DSP file, current scene is ${device.scenesOfProgram.get(numberOfPragram-1)}")
                    })

                } else {
                    Log.w(tag, "Successfully read number of program is ${device.numberOfProgram}")
                }
            })

            //We can obtain the scene mode corresponding to each program, for example, the scene mode of program 0 is scenesOfProgram[0]
            device.excute(JinHaoRequest.readScenesOfProgram(), Consumer {
                val programs = device.scenesOfProgram    //JinHaoProgram
                Log.w(tag, "Successfully read scenes of program is ${programs.size}, current scene is ${device.scenesOfProgram.get(device.numberOfProgram)}")
            })
        }
    }
```
- you can also call `accessory.disconnect()` to disconnect from the hearing aid. 

### Sending Commands to Hearing Aid
- 1. The command execution must be triggered after the `deviceDidDiscoverServices(device: Accessory?, services: MutableList<AccessoryService>?)` method is called. This is because the services of the device need to be discovered first before any command can be sent to the device.

- 2. To send a command to the hearing aid (e.g., to adjust volume, change program, etc.), you can use the `excute(JinHaoRequest request, int timeout, Consumer<JinHaoResult> complete)` method. This method allows you to issue various requests to the device and receive the result via a completion handler.
  
- 3. The [JinHaoRequest](JinHaoRequest.md) enum contains the supported commands for the JinHaoAccessory, such as volume adjustments, programs changes, etc. 

- 4. The [JinHaoResult](JinHaoResult.md)  provides feedback on whether the command was successful or if there was an error.
  
- 5. The method `deviceDidUpdateValue(device: JinHaoAccessory?)`([JinHaoAccessoryListener](docs/JinHaoAccessoryListener.md)) will be called when send command to Hearing Aid

#### Reading Hearing Aid Information
Before adjusting the volume, switching the program, or executing any other commands, we must first retrieve the relevant information from the hearing aid.

We can send a single command using ``excute(JinHaoRequest request, int timeout, Consumer<JinHaoResult> complete)`` to retrieve information. For more details, Please refer to the related [commands](docs/JinHaoRequest.md) for reading data.


- **get number of program**
```
device.readNumberOfProgram(5000, Consumer {
    if (it.isError) {
        Log.w(tag, "Failed to read number of program")
    } else {
        //range of numberOfProgram  is 0~3, total 4 program(0、1、2、3)
        numberOfProgramState.value = device.numberOfProgram
        Log.w(tag, "Successfully read number of program is ${device.numberOfProgram + 1}")
    }
})
```

- **obtain the scene mode corresponding to each program, for example, the scene mode of program 0 is scenesOfProgram[0]**
```
device.readScenesOfProgram(5000, Consumer {
    val programs = device.scenesOfProgram
    val type0 = programs.get(0)     //default is NORMAL
    val type1 = programs.get(1)     //default is RESTAURANT
    val type2 = programs.get(2)     //default is MUSIC
    val type3 = programs.get(3)     //default is OUTDOOR
    Log.w(tag, "Successfully read scenes of program is ${programs.size}")
})
```

- **Read current programe、volume、dsp**
```
//read current program and volume
device.excute(JinHaoRequest.readProgramVolume(), Consumer {
    if (it.isError) {
        Log.w(tag, "Failed to read program and volume")
    } else {
        Log.w(tag, "Successfully read current program ${device.program}, volume is ${device.volume}")
        device.excute(JinHaoRequest.readDsp(device.program), Consumer {
            Log.w(tag, "current dsp for program ${device.program} is ${device.dsp}")
        })
    }
})
```

- **Send a request to the hearing aid to fetch the SKU code and Pattern code, more info please refer to [JinHaoProfileType](docs/JinHaoProfile.md).**
```
    //read sku for hearing aid
    device.excute(JinHaoRequest.readProfile(JinHaoProfileType.PRODUCT_SKU), Consumer {
        if (it.isSuccess) {
            Log.w(tag, "sku code is ${device.profile.skuCode}")
        }
    })
    //read pattern for hearing aid
    device.excute(JinHaoRequest.readProfile(JinHaoProfileType.PRODUCT_PATTERN), Consumer {
        if (it.isSuccess) {
            Log.w(tag, "pattern code is ${device.profile.patternCode}")
        }
    })
```


#### Adjust Volume 
The volume adjustment range of the hearing aid is either 0–10 or 0–5, depending on the profile(`JinHaoProfile`) of the `JinHaoAccessory`. 
- Before adjusting the volume, we must first call the `JinHaoRequest.readProfile(JinHaoProfileType.PRODUCT_SKU)` to determine the maximum and minimum volume values.
```
device.excute(JinHaoRequest.readProfile(JinHaoProfileType.PRODUCT_SKU), Consumer {
    if (it.isSuccess) {
        minVolumeState.value = device.profile.minVolume.toFloat()
        maxVolumeState.value = device.profile.maxVolume.toFloat()
        Log.w(tag, "success to read sku: ${device.profile.skuCode}, minVolume is: ${device.profile.minVolume}, maxVolume is: ${device.profile.maxVolume}")
    }
})
```
-  Sets the device's volume level.
```
/**
* Once the volume is set, the updated value can be retrieved in the `deviceDidUpdateValue` callback.
*/
device?.excute(JinHaoRequest.controlVolume(it.roundToInt(), programState.value.roundToInt()), Consumer {

})
```

#### Switching Programs
After switching programs, we need to re-fetch the DSP (Digital Signal Processing) data to ensure the hearing aid's data is updated in a timely manner.
```
device?.excute(JinHaoRequest.controlProgram(program.roundToInt()), Consumer { result ->
    if (result.isSuccess && device != null) {
        Log.w(tag, "current program is ${program}, current scene mode is ${device!!.scenesOfProgram.get(device!!.program)}")
    }
})
```

#### Frequence
When modifying MPO, noise, direction, and so on, we should first obtain the frequency modification range supported by the hearing aid.
```
device.excute(JinHaoRequest.readDsp(currentProgram), Consumer {
    Log.w(tag, "Finished reading program ${currentProgram} DSP file")

    val dsp = device?.dsp?.copy()
    val frequences = device.dsp.frequences
})
```
- **JinHaoA4Dsp**
    if dsp is JinHaoA4Dsp, frequences = [250, 500, 1000, 1500, 2000, 2500, 3000, 3500, 4000, 5000, 6000, 7000]
- **JinHaoA16Dsp**
    if dsp is JinHaoA16Dsp, frequences = [250, 500, 1000, 1500, 2000, 2500, 3000, 3500, 4000, 4500, 5000, 5500, 6000, 6500, 7000, 7500]

#### EQ
Before modifying the EQ, we must first obtain the JinHaoDsp for the current program.
```
device.excute(JinHaoRequest.readDsp(currentProgram), Consumer {
    Log.w(tag, "Finished reading program ${currentProgram} DSP file")
})
```
then we can refer to the [JinHaoDsp](docs/JinHaoDsp.md) API to modify the EQ. for example:
```
val dsp = device?.dsp?.copy()
dsp?.setEq(250, 10))
dsp?.let {
    device?.excute(JinHaoRequest.writeDsp(it, currentProgram, true), Consumer {

    })
}
```

#### MPO

Before modifying the MPO, we must first obtain the JinHaoDsp for the current program; if it has already been obtained, there's no need to call it again.
```
device.excute(JinHaoRequest.readDsp(currentProgram), Consumer {
    Log.w(tag, "Finished reading program ${currentProgram} DSP file")
})
```
There are two cases when modifying the MPO, because the JinHaoDsp may be either [JinHaoA4Dsp](docs/JinHaoA4Dsp.md) or [JinHaoA16Dsp](docs/JinHaoA16Dsp.md). Below, we will set the MPO based on each case.

- **JinHaoA4Dsp**
```
val dsp = device?.dsp?.copy()
if (dsp is JinHaoA4Dsp) {
    dsp.mpo = JinHaoDsp.MPO.OFF
    dsp.let {
        device?.excute(JinHaoRequest.writeDsp(it, currentProgram, true), Consumer {
            if (it.isSuccess) {
                
            }
        })
    }
}
```
- **JinHaoA16Dsp**
```
val dsp = device?.dsp?.copy()
if (dsp is JinHaoA16Dsp) {
    dsp.setMpoLevel(250, it.roundToInt())
    dsp.let {
        device?.excute(JinHaoRequest.writeDsp(it, currentProgram, true), Consumer {
            if (it.isSuccess) {
                
            }
        })
    }
}
```

#### Noise
Before modifying the Noise, we must first obtain the JinHaoDsp for the current program; if it has already been obtained, there's no need to call it again.
```
device.excute(JinHaoRequest.readDsp(currentProgram), Consumer {
    Log.w(tag, "Finished reading program ${currentProgram} DSP file")
})
```
then we can refer to the [JinHaoDsp](docs/JinHaoDsp.md) API to modify the [Noise](docs/JinHaoDsp.md#noise). for example:
```
val dsp = device?.dsp?.copy()
dsp?.noise = JinHaoDsp.NOISE.OFF
dsp?.let {
    device?.excute(JinHaoRequest.writeDsp(it, programState.value.roundToInt(), true), Consumer {
        if (it.isSuccess) {
            
        }
    })
}
```

#### Direction
Before modifying the Direction, we must first obtain the JinHaoDsp for the current program; if it has already been obtained, there's no need to call it again.
```
device.excute(JinHaoRequest.readDsp(currentProgram), Consumer {
    Log.w(tag, "Finished reading program ${currentProgram} DSP file")
})
```
then we can refer to the [JinHaoDsp](docs/JinHaoDsp.md) API to modify the [Direction](docs/JinHaoDsp.md#direction). for example:
```
val dsp = device?.dsp?.copy()
dsp?.direction = JinHaoDsp.DIRECTION.NORMAL
dsp?.let {
    device?.excute(JinHaoRequest.writeDsp(it, programState.value.roundToInt(), true), Consumer {
        if (it.isSuccess) {
            
        }
    })
}
```

#### Compression Threshold
The compression threshold applies only to [JinHaoA16Dsp](docs/JinHaoA16Dsp.md). Before modifying the Compression Threshold, we must first obtain the JinHaoDsp for the current program; if it has already been obtained, there's no need to call it again. 
```
device.excute(JinHaoRequest.readDsp(currentProgram), Consumer {
    Log.w(tag, "Finished reading program ${currentProgram} DSP file")
    if (it is JinHaoA16Dsp) {
        Log.w(tag, "compresstion threshold level is ${it.getCompressRatioLevel(250)}  in 250Hz, value is ${it.getCompressThresholdValue(it.getCompressThresholdLevel(250))}")
    }
})
```
then we can refer to the [JinHaoA16Dsp](docs/JinHaoA16Dsp.md) API to modify the [compression threshold](docs/JinHaoA16Dsp.md#compression-threshold). for example:
```
private fun changeCompressionThreshold() {
    val dsp = device?.dsp?.copy()
    if (dsp is JinHaoA16Dsp) {
        //ct is 0 ~ 35
        dsp.setCompressThresholdLevel(250, 1)
        device?.program?.let {
            device!!.excute(JinHaoRequest.writeDsp(dsp, it, true), Consumer {

            })
        }
    }
}
```

#### Compression Ratio
The compression ratio applies only to [JinHaoA16Dsp](docs/JinHaoA16Dsp.md). Before modifying the compression ratio, we must first obtain the JinHaoDsp for the current program; if it has already been obtained, there's no need to call it again. 
```
device.excute(JinHaoRequest.readDsp(currentProgram), Consumer {
    Log.w(tag, "Finished reading program ${currentProgram} DSP file")
    if (it is JinHaoA16Dsp) {
        Log.w(tag, "compresstion ratio level is ${it.getCompressRatioLevel(250)}  in 250Hz, value is ${it.getCompressRatioValue(it.getCompressRatioLevel(250))}")
    }
})
```
then we can refer to the [JinHaoA16Dsp](docs/JinHaoA16Dsp.md) API to modify the [compression ratio](docs/JinHaoA16Dsp.md#compression-ratio). for example:
```
private fun changeCompressionRatio() {
    val dsp = device?.dsp?.copy()
    if (dsp is JinHaoA16Dsp) {
        //compressRatioLevel is 0 ~ 31
        dsp.setCompressRatioLevel(250, 1)
        device?.program?.let {
            device!!.excute(JinHaoRequest.writeDsp(dsp, it, true), Consumer {

            })
        }
    }
}
```

#### Attack Time
The attack time applies only to [JinHaoA16Dsp](docs/JinHaoA16Dsp.md). Before modifying the attack time, we must first obtain the JinHaoDsp for the current program; if it has already been obtained, there's no need to call it again. 
```
device.excute(JinHaoRequest.readDsp(currentProgram), Consumer {
    Log.w(tag, "Finished reading program ${currentProgram} DSP file")
    if (it is JinHaoA16Dsp) {
        Log.w(tag, "attack time level is ${it.attackTimeLevel}, value is ${it.getAttackTimeValue(it.attackTimeLevel)}")
    }
})
```
then we can refer to the [JinHaoA16Dsp](docs/JinHaoA16Dsp.md) API to modify the [attack time](docs/JinHaoA16Dsp.md#attack-time). for example:
```
private fun changeAttackTime() {
    val dsp = device?.dsp?.copy()
    if (dsp is JinHaoA16Dsp) {
        //attackTimeLevel is 0 ~ 3
        dsp.attackTimeLevel = 1
        device?.program?.let {
            device!!.excute(JinHaoRequest.writeDsp(dsp, it, true), Consumer {

            })
        }
    }
}
```

#### Release Time
The release time applies only to [JinHaoA16Dsp](docs/JinHaoA16Dsp.md). Before modifying the release time, we must first obtain the JinHaoDsp for the current program; if it has already been obtained, there's no need to call it again. 
```
device.excute(JinHaoRequest.readDsp(currentProgram), Consumer {
    Log.w(tag, "Finished reading program ${currentProgram} DSP file")
    if (it is JinHaoA16Dsp) {
        Log.w(tag, "release time level is ${it.releaseTimeLevel}, value is ${it.getReleaseTimeValue(it.releaseTimeLevel)}")
    }
})
```
then we can refer to the [JinHaoA16Dsp](docs/JinHaoA16Dsp.md) API to modify the [release time](docs/JinHaoA16Dsp.md#release-time). for example:
```
private fun changeReleaseTime() {
    val dsp = device?.dsp?.copy()
    if (dsp is JinHaoA16Dsp) {
        //releaseTimeLevel is 0 ~ 3
        dsp.releaseTimeLevel = 1
        device?.program?.let {
            device!!.excute(JinHaoRequest.writeDsp(dsp, it, true), Consumer {

            })
        }
    }
}
```

#### Data Log
Reading the DataLog is only applicable to JinHaoA16Accessory. for example:
```
private fun readDataLog() {
    (device as? JinHaoA16Accessory)?.requestSummaryDataLog {
        if (it != null) {
            Log.w(tag, "success to read data log: ${it}")
        }
    }
}
```

#### Read Battery
```
device.excute(JinHaoRequest.readBat(), Consumer {
    if (it.isError) {
        Log.w(tag, "Failed to read battery")
    } else {
        Log.w(tag, "Successfully read battery ${device.bat}")
    }
})
```

#### Notify
When the battery level changes, the hearing aid's volume or program is switched through the button, or a command is sent to the hearing aid via the request method, the relevant methods in the [JinHaoAccessoryListener](docs/JinHaoAccessoryListener.md) will be triggered.
```
/**
    * Callback triggered when the device's battery level changes.
    */
override fun deviceBatteryChanged(device: JinHaoAccessory?, bat: Int) {

}

/**
    * This callback is called when the device's data value is updated,
    * such as when data changes or when data is read from the device.
    * You can access the current device data values in this method.
    * To retrieve the corresponding values from the device, you need to first issue a read command,
    * as shown in the `deviceDidDiscoverServices` method where `device.excute` performs a read operation.
    */
override fun deviceDidUpdateValue(device: JinHaoAccessory?) {
    runOnUiThread {
        if (device is JinHaoAccessory) {
            device.dsp?.let {
                when {
                    it.noise == JinHaoDsp.NOISE.OFF -> {
                        noiseState.value = 0F
                    }
                    it.noise == JinHaoDsp.NOISE.WEAK -> {
                        noiseState.value = 1F
                    }
                    it.noise == JinHaoDsp.NOISE.MEDIUM -> {
                        noiseState.value = 2F
                    }
                    it.noise == JinHaoDsp.NOISE.STRONG -> {
                        noiseState.value = 3F
                    }
                }
                when {
                    it.direction == JinHaoDsp.DIRECTION.NORMAL -> {
                        directionState.value = 0F
                    }
                    it.direction == JinHaoDsp.DIRECTION.TV -> {
                        directionState.value = 1F
                    }
                    it.direction == JinHaoDsp.DIRECTION.METTING -> {
                        directionState.value = 2F
                    }
                    it.direction == JinHaoDsp.DIRECTION.FACE -> {
                        directionState.value = 3F
                    }
                }
                //range of value is dsp.minEqValue to dsp.maxEqValue
                eqState[250]?.value = it.getEq(250).toFloat()
                eqState[500]?.value = it.getEq(500).toFloat()
                eqState[1000]?.value = it.getEq(1000).toFloat()
                eqState[1500]?.value = it.getEq(1500).toFloat()
                eqState[2000]?.value = it.getEq(2000).toFloat()
                eqState[2500]?.value = it.getEq(2500).toFloat()
                eqState[3000]?.value = it.getEq(3000).toFloat()
                eqState[3500]?.value = it.getEq(3500).toFloat()
                eqState[4000]?.value = it.getEq(4000).toFloat()
                eqState[5000]?.value = it.getEq(5000).toFloat()
                eqState[6000]?.value = it.getEq(6000).toFloat()
                eqState[7000]?.value = it.getEq(7000).toFloat()
            }
            device.let {
                volumeState.value = it.volume.toFloat()
            }
            device.let {
                programState.value = it.program.toFloat()
            }
        }
    }
}

/**
    * Changing the program mode on the hearing aid
    */
override fun didChangedProgramByAid(device: JinHaoAccessory?, previous: Int, current: Int) {
    programState.value = current.toFloat()
    device?.excute(JinHaoRequest.readDsp(current), null)
}

/**
    * Changing the volume on the hearing aid
    */
override fun didChangedVolumeByAid(device: JinHaoAccessory?, previous: Int, current: Int) {
    volumeState.value = current.toFloat()
     device?.dsp?.let {
          minEQValueState.value = it.minEqValue.toFloat()
          maxEQValueState.value = it.maxEqValue.toFloat()
     }
}
```

## API Document
- [BluetoothPermissionHelper](docs/BluetoothPermissionHelper.md)
- [Accessory](docs/Accessory.md)
- [AccessoryListener](docs/AccessoryListener.md)
- [AccessoryManager](docs/AccessoryManager.md)
- [AccessoryManagerScanningListener](docs/AccessoryManagerScanningListener.md)
- [AccessoryManagerStatusListener](docs/AccessoryManagerStatusListener.md)
- [JinHaoAccessory](docs/JinHaoAccessory.md)
- [JinHaoAccessoryListener](docs/JinHaoAccessoryListener.md)
- [JinHaoBLEChip](docs/JinHaoBLEChip.md)
- [JinHaoChip](docs/JinHaoChip.md)
- [JinHaoDsp](docs/JinHaoDsp.md)
- [JinHaoA4Dsp](docs/JinHaoA4Dsp.md)
- [JinHaoA16Dsp](docs/JinHaoA16Dsp.md)
- [JinHaoGlobalDsp](docs/JinHaoGlobalDsp.md)
- [JinHaoOrientation](docs/JinHaoOrientation.md)
- [JinHaoProfile](docs/JinHaoProfile.md)
- [JinHaoProfileType](docs/JinHaoProfileType.md)
- [JinHaoProgram](docs/JinHaoProgram.md)
- [JinHaoRequest](docs/JinHaoRequest.md)
- [JinHaoResult](docs/JinHaoResult.md)
- [JinHaoResultError](docs/JinHaoResultError.md)
