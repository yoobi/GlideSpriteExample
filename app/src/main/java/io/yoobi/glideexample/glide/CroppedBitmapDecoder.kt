package io.yoobi.glideexample.glide

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.BitmapRegionDecoder
import android.graphics.Rect
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import com.bumptech.glide.load.Options
import com.bumptech.glide.load.ResourceDecoder
import com.bumptech.glide.load.engine.Resource
import com.bumptech.glide.load.resource.SimpleResource

/**
 * Glide bitmap decoder that creates a smaller bitmap based on the view's dimensions, instead of using an image
 * transformation. This bypasses the creation of two bitmaps and reduces memory usage.
 */
class CroppedBitmapDecoder(val resources: Resources): ResourceDecoder<CroppedImageDecoderInput, BitmapDrawable> {

    override fun handles(source: CroppedImageDecoderInput, options: Options): Boolean {
        return true
    }

    override fun decode(source: CroppedImageDecoderInput, width: Int, height: Int, options: Options): Resource<BitmapDrawable> {
        val bitmap: Bitmap
        var decoder: BitmapRegionDecoder? = null

        val bitmapFactoryOptions = BitmapFactory.Options().apply {
            // Decode image dimensions only, not content
            inJustDecodeBounds = true
        }

        // You may want to consider using a bitmap cache if your Use Case requires it -
        // see Glide's Downsampler.java for implementation details.

        // Determine the image's height and width
        BitmapFactory.decodeFile(source.file.absolutePath, bitmapFactoryOptions)
        val imageHeight = bitmapFactoryOptions.outHeight
        val imageWidth = bitmapFactoryOptions.outWidth

        try {
            decoder = if(Build.VERSION.SDK_INT < Build.VERSION_CODES.S) {
                BitmapRegionDecoder.newInstance(source.file.absolutePath, false)
            } else {
                BitmapRegionDecoder.newInstance(source.file.absolutePath)
            }
            // Ensure the cropping and translation region doesn't exceed the image dimensions
            val newWidth = imageWidth / 7
            val newHeight = imageHeight / 7
            val region = Rect(
                source.horizontalOffset*newWidth,
                source.verticalOffset*newHeight,
                newWidth + (source.horizontalOffset*newWidth),
                newHeight + (source.verticalOffset*newHeight),
            )

            // Decode image content within the cropping region
            bitmapFactoryOptions.inJustDecodeBounds = false
            bitmap = decoder.decodeRegion(region, bitmapFactoryOptions)
        } finally {
            decoder?.recycle()
        }

        val drawable = BitmapDrawable(resources, bitmap)
        return SimpleResource<BitmapDrawable>(drawable)
    }
}