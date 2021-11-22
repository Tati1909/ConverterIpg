package com.example.converteripg.presentation.converter

import android.net.Uri
import com.example.converteripg.data.converter.Converter
import com.example.converteripg.scheduler.Schedulers
import com.example.converteripg.view.ConverterView
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.plusAssign
import moxy.MvpPresenter

class ConverterPresenter(
    private val converter: Converter,
    private val schedulers: Schedulers,
) : MvpPresenter<ConverterView>() {

    private val disposables = CompositeDisposable()

    fun convert(uri: Uri) {
        //покажи картинку какую мы выбрали
        viewState.showContent(uri)
        //покажи экран загрузки
        viewState.showLoading()

        disposables +=
            converter
                .convert(uri)
                .subscribeOn(schedulers.background())
                .observeOn(schedulers.main())
                .subscribe(
                    viewState::showContent,
                    viewState::showError
                )
    }

    fun cancel() {
        viewState.showContent(null)
        disposables.clear()
    }

    override fun onDestroy() {
        disposables.clear()
    }
}