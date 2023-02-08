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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File

private const val SPRITE = "https://raw.githubusercontent.com/rubensousa/PreviewSeekBar/master/sample/src/main/res/raw/thumbnail_sprite.jpg"

class MainActivity : AppCompatActivity() {
    private var spriteFile: File? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val slider = findViewById<Slider>(R.id.slider)
        val imageView = findViewById<ImageView>(R.id.imageView)

        GlobalScope.launch(Dispatchers.IO) {
            spriteFile = Glide.with(imageView)
                .downloadOnly()
                .load(SPRITE)
                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .submit().get()
        }

        slider.addOnChangeListener { view, value, _ ->
            spriteFile?.let {
                Glide.with(imageView)
                    .load(it)
                    .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .transform(GlideThumbnailTransformation(value.toInt()))
                    .into(imageView)
            }
        }
    }
}
