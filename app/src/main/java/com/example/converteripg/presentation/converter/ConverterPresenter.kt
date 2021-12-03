package com.example.converteripg.presentation.converter

import android.net.Uri
import com.example.converteripg.data.converter.ConverterJpgToPng
import com.example.converteripg.scheduler.Schedulers
import com.example.converteripg.view.ConverterView
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.plusAssign
import moxy.MvpPresenter

class ConverterPresenter(
    private val converter: ConverterJpgToPng,
    private val schedulers: Schedulers,
) : MvpPresenter<ConverterView>() {

    private val disposables = CompositeDisposable()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
    }

    fun startConvertingPressed(uri: Uri) {
        viewState.showProgressBar()
        viewState.signWaitingShow()
        viewState.signGetStartedHide()
        viewState.btnAbortConvertEnabled()

        disposables +=
            converter
                .convert(uri)
                .subscribeOn(schedulers.background())
                .observeOn(schedulers.main())
                .subscribe(
                    { t: Uri? ->
                        if (t != null) {
                            onConvertingSuccess(t)
                        }
                    },
                    { e: Throwable? ->
                        onConvertingError(e)
                    })
    }

    /**
     * Обработка успеха от источника
     */
    private fun onConvertingSuccess(uri: Uri) {
        viewState.showConvertedImage(uri)
        viewState.hideProgressBar()
        viewState.btnAbortConvertDisabled()
        viewState.signAbortConvertHide()
        viewState.signWaitingHide()
    }

    /**
     * Обработка ошибки от источника
     */
    private fun onConvertingError(e: Throwable?) {
        viewState.showProgressBar()
        viewState.hideProgressBar()
        viewState.btnAbortConvertDisabled()
        viewState.signWaitingHide()
    }

    /**
     * Прерывание процесса конвертации изображения
     * фактически отписывается от источника, и выводит зарезервированное изображение на экран
     * попутно скрывает прогресс бар и деактивирует кнопку прерывания процесса конвертации
     */
    fun abortConvertImagePressed() {
        viewState.hideProgressBar()
        viewState.signWaitingHide()
        viewState.btnAbortConvertDisabled()
        viewState.signAbortConvertShow()
        disposables.clear()
    }

    /**
     * Обрабатываемое изображение выбрано
     * @param imageUri - Uri конвертируемого изображения (оригинала)
     * отдает команду вывести выбранное изображение в виджет оригинала
     * попутно активирует кнопку старта процесса конвертации изображения
     * скрывает ненужные сигнумы и отображает картинку заглушку ожидания результата конвертации
     */
    fun originalImageSelected(imageUri: Uri) {
        viewState.showOriginImage(imageUri)
        viewState.btnStartConvertEnable()
        viewState.signAbortConvertHide()
        viewState.signGetStartedHide()
        viewState.signWaitingShow()
    }

    override fun onDestroy() {
        disposables.clear()
    }
}