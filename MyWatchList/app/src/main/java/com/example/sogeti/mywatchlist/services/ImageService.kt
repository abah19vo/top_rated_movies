package com.example.sogeti.mywatchlist.services

import android.content.Context
import android.graphics.Bitmap
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder

enum class ImageSizes(val value: String) {
    Small("200"),
    Large("500")
}

object ImageService {
    private const val baseUrl = "https://image.tmdb.org/t/p/w"

    fun loadImage(path: String, size: ImageSizes, context: Context): RequestBuilder<Bitmap> {

        return Glide.with(context).asBitmap().load("$baseUrl${size.value}/$path")
    }
}


