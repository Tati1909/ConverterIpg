package com.example.converteripg.view

import android.net.Uri
import com.example.converteripg.ScreenView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface ConverterView : ScreenView {

    fun showContent(uri: Uri?)

    fun showLoading()
}