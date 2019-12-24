package com.sattar.currencyconverter.di

import com.sattar.currencyconverter.data.api.CurrencyApiService
import com.sattar.currencyconverter.ui.currencylist.CurrencyListRepository
import dagger.Module
import dagger.Provides

/**
 * Project: Currency Converter
 * Created: 12/24/2019.
 * @author : Mohamed Abd EL-Sattar
 */
@Module
class RepositoryModule {

    @Provides
    fun provideCurrencyListRepository(currencyApiService: CurrencyApiService): CurrencyListRepository {
        return CurrencyListRepository(currencyApiService)
    }

}