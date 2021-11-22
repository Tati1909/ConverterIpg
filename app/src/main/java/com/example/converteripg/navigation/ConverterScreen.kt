package com.example.converteripg.navigation

import com.example.converteripg.view.ConverterFragment
import com.github.terrakok.cicerone.Screen
import com.github.terrakok.cicerone.androidx.FragmentScreen

object ConverterScreen {

    //переход на фрагмент со списком пользователей UsersFragment
    fun create(): Screen {
        return FragmentScreen { ConverterFragment.newInstance() }
    }
}