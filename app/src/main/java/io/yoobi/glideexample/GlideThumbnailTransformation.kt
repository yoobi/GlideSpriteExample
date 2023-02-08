package io.yoobi.glideexample

import android.graphics.Bitmap
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import java.nio.ByteBuffer
import java.security.MessageDigest

private const val MAX_LINES = 7
private const val MAX_COLUMNS = 7

class GlideThumbnailTransformation(square: Int) : BitmapTransformation() {

    private val x: Int = square % MAX_COLUMNS
    private val y: Int = square / MAX_COLUMNS

    override fun transform(pool: BitmapPool, toTransform: Bitmap, outWidth: Int, outHeight: Int): Bitmap {
        val width = toTransform.width / MAX_COLUMNS
        val height = toTransform.height / MAX_LINES

        return Bitmap.createBitmap(toTransform, x * width, y * height, width, height)
    }

    override fun updateDiskCacheKey(messageDigest: MessageDigest) {
        val data: ByteArray = ByteBuffer.allocate(8).putInt(x).putInt(y).array()
        messageDigest.update(data)
    }

    override fun hashCode(): Int {
        return (x.toString() + y.toString()).hashCode()
    }

    override fun equals(other: Any?): Boolean {
        if (other !is GlideThumbnailTransformation) return false
        return other.x == x && other.y == y
    }
}