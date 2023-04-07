package io.yoobi.glideexample.glide

import android.content.res.Resources
import com.bumptech.glide.load.model.ModelLoader
import com.bumptech.glide.load.model.ModelLoaderFactory
import com.bumptech.glide.load.model.MultiModelLoaderFactory

/**
 * Glide model loader factory for Cropped Image Model loaders.
 */
class CroppedImageModelLoaderFactory(
    private val resources: Resources
): ModelLoaderFactory<CroppedImage, CroppedImageDecoderInput> {

    override fun build(multiFactory: MultiModelLoaderFactory):
            ModelLoader<CroppedImage, CroppedImageDecoderInput> = CroppedImageModelLoader(resources)

    override fun teardown() { }
}