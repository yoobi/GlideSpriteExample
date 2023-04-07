package io.yoobi.glideexample

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.android.material.slider.Slider
import io.yoobi.glideexample.component.CroppedImageView
import io.yoobi.glideexample.glide.CroppedImage
import io.yoobi.glideexample.glide.GlideApp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File
import kotlin.math.max
import kotlin.math.min

// Width: 840
// Height: 469
private const val SPRITE = "https://raw.githubusercontent.com/rubensousa/PreviewSeekBar/master/sample/src/main/res/raw/thumbnail_sprite.jpg"

class MainActivity : AppCompatActivity() {
    private var spriteFile: MutableLiveData<File> = MutableLiveData(null)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val slider = findViewById<Slider>(R.id.slider)
        val imageView = findViewById<CroppedImageView>(R.id.imageView)
        val minus = findViewById<Button>(R.id.btn_minus)
        val plus = findViewById<Button>(R.id.btn_plus)

        GlideApp.get(this@MainActivity).clearMemory()
        GlobalScope.launch(Dispatchers.IO) {
            GlideApp.get(this@MainActivity).clearDiskCache()
            spriteFile.postValue(Glide.with(imageView)
                .downloadOnly()
                .load(SPRITE)
                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                .submit().get()
            )
        }

        spriteFile.observe(this) {
            it ?: return@observe
            for(i in 0 until 49) {
                Log.e("MainActivity", "i: $i")
                imageView.setImageResource(it, i)
            }
        }

        slider.addOnChangeListener { view, value, _ ->
            spriteFile.value?.let {
                imageView.setImageResource(it, value.toInt())
            }
        }

        minus.setOnClickListener {
            slider.value = max(slider.valueFrom, slider.value - 1f)
        }

        plus.setOnClickListener {
            slider.value = min(slider.valueTo, slider.value + 1f)
        }
    }

}
