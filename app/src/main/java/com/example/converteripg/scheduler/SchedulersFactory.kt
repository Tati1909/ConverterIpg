package com.example.converteripg.scheduler

import android.content.Context
import com.example.converteripg.data.converter.Converter
import com.example.converteripg.data.converter.ConverterImpl

object SchedulersFactory {

    fun create(): Schedulers = DefaultSchedulers()

}