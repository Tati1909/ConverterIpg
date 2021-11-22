package com.example.converteripg.presentation.converter

import com.example.converteripg.navigation.ConverterScreen
import com.example.converteripg.view.MainView
import com.github.terrakok.cicerone.Router
import moxy.MvpPresenter

class MainPresenter(private val router: Router) :
    MvpPresenter<MainView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        //Можно так router.replaceScreen( UsersScreen.create())
        router.newRootScreen(ConverterScreen.create())
    }

    fun backClicked() {
        router.exit()
    }
}