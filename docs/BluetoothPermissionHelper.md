# BluetoothPermissionHelper Class

The `BluetoothPermissionHelper` class manages Bluetooth-related permissions in an Android application. It ensures that the app has the necessary permissions to perform Bluetooth operations by checking and requesting permissions based on the Android version.

## Key Features

1. **Permission Checking and Requesting**:
   - Checks the required Bluetooth permissions based on the Android version, including:
     - `BLUETOOTH`, `BLUETOOTH_ADMIN`, `BLUETOOTH_SCAN`, `BLUETOOTH_CONNECT`, and `ACCESS_FINE_LOCATION`.
   - Handles permissions separately for different Android versions (Android 10 and above).
   - Requests missing permissions from the user if needed.

2. **Handling Permission Request Results**:
   - After requesting permissions, it processes the result and checks if all required permissions were granted.
   - Returns `true` if all permissions are granted, and `false` if any permission is denied.

## Methods

### `checkAndRequestBluetoothPermissions(Activity activity)`

Checks for required Bluetooth permissions based on the Android version and requests any missing permissions.

**Parameters**:
- `activity`: The `Activity` context from which permissions are requested.

**Returns**:
- `true` if all required permissions are granted.
- `false` if any permissions are missing and requests are made.

### `handlePermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)`

Processes the result of the permission request and returns whether all required permissions were granted.

**Parameters**:
- `requestCode`: The request code passed to `requestPermissions()`.
- `permissions`: An array of permissions requested.
- `grantResults`: An array of grant results for the corresponding permissions.

**Returns**:
- `true` if all permissions are granted.
- `false` if any permission is denied.

## Usage Example

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