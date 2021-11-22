package com.example.converteripg

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.converteripg.databinding.ActivityMainBinding
import com.example.converteripg.presentation.converter.MainPresenter
import com.example.converteripg.view.MainView
import com.github.terrakok.cicerone.androidx.AppNavigator
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter

class MainActivity : MvpAppCompatActivity(), MainView {

    private val navigator = AppNavigator(this@MainActivity, R.id.container)

    //объявляем Presenter и делегируем его создание и хранение
    //через делегат moxyPresenter.
    //moxyPresenter создает новый экземпляр MoxyKtxDelegate.
    //Делегат подключается к жизненному циклу активити
    private val presenter by moxyPresenter {
        MainPresenter(App.instance.router)
    }

    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

    }

    //устанавливаем навигатор
    override fun onResumeFragments() {
        super.onResumeFragments()
        App.instance.navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        App.instance.navigatorHolder.removeNavigator()
        super.onPause()
    }
}