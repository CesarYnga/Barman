package com.cesarynga.barman.generator

import com.google.zxing.BarcodeFormat

/**
 * Supported barcode formats
 */
enum class BarmanCodeFormat(val barcodeFormat: BarcodeFormat) {
    /** Aztec 2D barcode format. */
    AZTEC(BarcodeFormat.AZTEC),

    /** CODABAR 1D format. */
    CODABAR(BarcodeFormat.CODABAR),

    /** Code 39 1D format. */
    CODE_39(BarcodeFormat.CODE_39),

    /** Code 93 1D format. */
    CODE_93(BarcodeFormat.CODE_93),

    /** Code 128 1D format. */
    CODE_128(BarcodeFormat.CODE_128),

    /** Data Matrix 2D barcode format. */
    DATA_MATRIX(BarcodeFormat.DATA_MATRIX),

    /** EAN-8 1D format. */
    EAN_8(BarcodeFormat.EAN_8),

    /** EAN-13 1D format. */
    EAN_13(BarcodeFormat.EAN_13),

    /** ITF (Interleaved Two of Five) 1D format. */
    ITF(BarcodeFormat.ITF),

    /** MaxiCode 2D barcode format. */
    MAXICODE(BarcodeFormat.MAXICODE),

    /** PDF417 format. */
    PDF_417(BarcodeFormat.PDF_417),

    /** QR Code 2D barcode format. */
    QR_CODE(BarcodeFormat.QR_CODE),

    /** RSS 14 */
    RSS_14(BarcodeFormat.RSS_14),

    /** RSS EXPANDED */
    RSS_EXPANDED(BarcodeFormat.RSS_EXPANDED),

    /** UPC-A 1D format. */
    UPC_A(BarcodeFormat.UPC_A),

    /** UPC-E 1D format. */
    UPC_E(BarcodeFormat.UPC_E),

    /** UPC/EAN extension format. Not a stand-alone format. */
    UPC_EAN_EXTENSION(BarcodeFormat.UPC_EAN_EXTENSION)
}