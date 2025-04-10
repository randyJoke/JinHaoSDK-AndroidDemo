# `JinHaoResult` Enum Documentation

## Overview

The `JinHaoResult` enum represents the result of an operation performed with a [JinHaoAccessory](JinHaoAccessory.md). It encapsulates both success and error outcomes, providing a clear structure for handling results and errors. This enum can hold either the data from a successful operation or the error information from a failed operation.

## Methods

| Method Name          | Description                                                                 | Return Type                      |
|----------------------|-----------------------------------------------------------------------------|-----------------------------------|
| `success(ByteBuffer data)` | Returns a `JinHaoResult` indicating success, with the provided `ByteBuffer` data. | `JinHaoResult`                   |
| `error(JinHaoResultError error)` | Returns a `JinHaoResult` indicating an error, with the provided `JinHaoResultError` instance. | `JinHaoResult`                   |
| `getData()`          | Returns the data from a successful operation. Throws an exception if the result is an error. | `ByteBuffer`                     |
| `getError()`         | Returns the error from a failed operation. Throws an exception if the result is a success. | `JinHaoResultError`              |
| `isSuccess()`        | Checks if the result is a success.                                          | `boolean`                        |
| `isError()`          | Checks if the result is an error.                                           | `boolean`                        |
