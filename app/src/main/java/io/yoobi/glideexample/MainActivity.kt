package io.yoobi.glideexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.DragEvent
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.Target
import com.google.android.material.slider.Slider

private const val SPRITE = "https://raw.githubusercontent.com/rubensousa/PreviewSeekBar/master/sample/src/main/res/raw/thumbnail_sprite.jpg"

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val slider = findViewById<Slider>(R.id.slider)
        val imageView = findViewById<ImageView>(R.id.imageView)

        Glide.with(imageView)
            .load(SPRITE)
            .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .preload()

        slider.addOnChangeListener { view, value, _ ->
            Glide.with(imageView)
                .load(SPRITE)
                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transform(GlideThumbnailTransformation(value.toInt()))
                .into(imageView)
        }
    }
}
