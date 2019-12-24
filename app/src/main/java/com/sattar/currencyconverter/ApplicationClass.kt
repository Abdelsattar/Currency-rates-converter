package com.sattar.currencyconverter

import android.app.Application
import com.sattar.currencyconverter.di.AppInjector
import com.sattar.currencyconverter.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

/**
 * Project: Currency Converter
 * Created: 12/24/2019.
 * @author : Mohamed Abd EL-Sattar
 */
class ApplicationClass : Application(), HasAndroidInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()

        initDagger()
    }

    private fun initDagger() {
        DaggerAppComponent.builder()
            .application(this)
            .build().inject(this)
    }

    override fun androidInjector(): AndroidInjector<Any> = dispatchingAndroidInjector

}