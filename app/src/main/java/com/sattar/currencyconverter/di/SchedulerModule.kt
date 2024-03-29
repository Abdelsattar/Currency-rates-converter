package com.sattar.currencyconverter.di

import dagger.Module
import dagger.Provides

/**
 * Project: Currency Converter
 * Created: 12/28/2019.
 * @author : Mohamed Abd EL-Sattar
 */

@Module
class SchedulerModule {

    @Provides
    fun provideScheduler(): BaseSchedulerProvider = SchedulerProvider()

}