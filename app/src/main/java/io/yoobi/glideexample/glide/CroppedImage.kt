package io.yoobi.glideexample.glide

import java.io.File

/**
 * A glide model class to initiate custom image loading behaviour.
 */
data class CroppedImage(
        val file: File,
        val horizontalOffset: Int = 0,
        val verticalOffset: Int = 0
)