package com.example.converteripg.data.converter

import android.content.Context
import android.net.Uri
import io.reactivex.rxjava3.core.Single

class ConverterImpl(private val context: Context): Converter {

    override fun convert(uri: Uri): Single<Uri> =
        ConverterSingle(context, uri)
}