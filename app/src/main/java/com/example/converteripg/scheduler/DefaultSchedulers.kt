package com.example.converteripg.scheduler

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler

class DefaultSchedulers : Schedulers {

    //фоновый поток
    override fun background(): Scheduler =
        io.reactivex.rxjava3.schedulers.Schedulers.newThread()
    //главный поток
    override fun main(): Scheduler = AndroidSchedulers.mainThread()
}