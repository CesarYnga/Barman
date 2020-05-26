# Barman

Android Barcode library based on ZXing written in Kotlin.

## Usage

### Sync
Perform barcode encoding in the current thread.

**Kotlin**
```kotlin
val barman = Barman()
val bitmap = barman.generateBitmap(
    "Barcode Sample content", 
    BarmanCodeFormat.CODE_QR, 
    400, 
    400
)
imageView.setImageBitmap(bitmap)
```

**KTX**
```kotlin
imageView.loadBarcode(
    "Barcode Sample content", 
    BarmanCodeFormat.CODE_QR
)
```

### Async
Perform barcode encoding in a new worker thread using coroutines. A `Job` is returned for cancelation.

**Kotlin**
```kotlin
val job = barman.generateBitmapAsync(
    "Barcode Sample content",
    BarmanCodeFormat.CODE_QA,
    400,
    400
) {
    imageView.setImageBitmap(it)
}

```

If you want to handle your own coroutine, use `generateBitmapSuspended` function.
```kotlin
coroutineScope.launch {
    val bitmap = generateBitmapSuspended(
        "Barcode Sample content",
        BarmanCodeFormat.CODE_QA,
        400,
        400
    )
    imageView.setImageBitmap(bitmap)
}
```

**KTX**
```kotlin
val job = imageView.loadBarcodeAsync(
    "Barcode Sample content", 
    BarmanCodeFormat.CODE_QR
)
```

License
-------

    Copyright 2020 César Ynga

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.