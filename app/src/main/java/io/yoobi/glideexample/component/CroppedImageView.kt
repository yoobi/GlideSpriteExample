package io.yoobi.glideexample.component

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide
import io.yoobi.glideexample.glide.CroppedImage
import java.io.File

/**
 * An Image view which crops and translates the image to fit in the view's dimensions using background thread
 * image loading provided by the Glide library.
 */
class CroppedImageView(context: Context, attrs: AttributeSet) : AppCompatImageView(context, attrs) {

    private var square = 0
    private var file: File? = null
    private var loadRequested = false

    init {
        scaleType = ScaleType.FIT_XY
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        if (file != null) {
            loadCroppedImage()
        }
    }

    fun setImageResource(file: File, square: Int) {
        this.file = file
        this.square = square
        loadRequested = false
        if (height != 0 && width != 0) {
            loadCroppedImage()
        }
    }

    private fun loadCroppedImage() {
        if (file == null || loadRequested) return
        loadRequested = true // Don't trigger multiple loads for the same resource
        val model = CroppedImage(
            file = file!!,
            horizontalOffset = square % 7,
            verticalOffset = square / 7
        )
        Glide.with(context)
            .load(model)
            .into(this)
    }

    fun clear() {
        // Stop any in-progress image loading
        Glide.with(context).clear(this)
    }
}