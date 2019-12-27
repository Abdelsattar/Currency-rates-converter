package com.sattar.currencyconverter.di

import android.app.Application
import com.google.gson.Gson
import com.sattar.currencyconverter.data.model.CurrencyRate
import com.sattar.currencyconverter.util.fromAssets
import dagger.Module
import dagger.Provides

/**
 * Project: Currency Converter
 * Created: 12/26/2019.
 * @author : Mohamed Abd EL-Sattar
 */

@Module
class UtilModule {

    @Provides
    fun provideLocalCurrencies(context: Application): ArrayList<CurrencyRate> =
        Gson().fromAssets(context = context, fileName = "currencies.json")
}