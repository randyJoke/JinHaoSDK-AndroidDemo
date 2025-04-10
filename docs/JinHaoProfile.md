# JinHaoProfile
The `JinHaoProfile` class represents the profile of a hearing aid device, containing various configuration details including product series, software versions, and the programs available for the device.

## Methods

| Method                          | Description                                                                                     |
|-------------------------------------------|-------------------------------------------------------------------------------------------------|
| `String getPSeriesCode()`          | Returns the product series code.                                                                |
| `void setPSeriesCode(String)`      | Sets the product series code.                                                                   |
| `String getSkuCode()`              | Returns the SKU code.                                                                           |
| `void setSkuCode(String)`          | Sets the SKU code.                                                                              |
| `String getPatternCode()`          | Returns the product pattern code.                                                               |
| `void setPatternCode(String)`      | Sets the product pattern code.                                                                  |
| `String getHwVersion()`            | Returns the hardware version number of the product.                                             |
| `void setHwVersion(String)`        | Sets the hardware version number of the product.                                               |
| `String getSwVersion()`            | Returns the software version number of the product.                                             |
| `void setSwVersion(String)`        | Sets the software version number of the product.                                               |
| `String getBhwPattern()`           | Returns the Bluetooth hardware model number.                                                   |
| `void setBhwPattern(String)`       | Sets the Bluetooth hardware model number.                                                      |
| `String getBhwVersion()`           | Returns the Bluetooth hardware version number.                                                 |
| `void setBhwVersion(String)`       | Sets the Bluetooth hardware version number.                                                   |
| `String getBswVersion()`           | Returns the Bluetooth software version number.                                                 |
| `void setBswVersion(String)`       | Sets the Bluetooth software version number.                                                   |
| `String getVersion()`              | Returns the product version number.                                                             |
| `void setVersion(String)`          | Sets the product version number.                                                                |
| `String getOtaVersion()`           | Returns the OTA version used for firmware upgrade verification.                                |
| `void setOtaVersion(String)`       | Sets the OTA version for firmware upgrade verification.                                        |
| `String getSeriesCode()`           | Returns the unique identifier for the hearing aid.                                             |
| `void setSeriesCode(String)`       | Sets the unique identifier for the hearing aid.                                                |
| `String getAdName()`               | Returns the Bluetooth advertisement name.                                                      |
| `void setAdName(String)`           | Sets the Bluetooth advertisement name.                                                         |

## Example
```
device.excute(JinHaoRequest.readProfile(JinHaoProfileType.PRODUCT_SKU), Consumer {
    if (it.isSuccess) {
        Log.w(tag, "sku code is ${device.profile.skuCode}")
    }
})
```