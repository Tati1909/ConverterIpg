package com.example.converteripg.data.converter

import android.content.Context

object ConverterFactory {

    fun create(requireContext: Context): Converter = ConverterImpl(requireContext)
}