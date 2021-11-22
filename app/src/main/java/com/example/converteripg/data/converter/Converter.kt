package com.example.converteripg.data.converter

import android.net.Uri
import io.reactivex.rxjava3.core.Single

interface Converter {

    fun convert(uri: Uri): Single<Uri>
}