package com.sattar.currencyconverter.ui.currencylist

import com.sattar.currencyconverter.data.api.CurrencyApiService
import com.sattar.currencyconverter.data.model.CurrencyRate
import com.sattar.currencyconverter.data.model.Mapper
import com.sattar.currencyconverter.ui.base.BaseRepository
import javax.inject.Inject

/**
 * Project: Currency Converter
 * Created: 12/24/2019.
 * @author : Mohamed Abd EL-Sattar
 */
class CurrencyListRepository @Inject constructor(private val currencyApiService: CurrencyApiService,
                                                 private val localCurrenciesData: ArrayList<CurrencyRate>) : BaseRepository() {

    fun getLatestCurrencyRates(baseCurrency: String,baseRateFactor: Double) =
        currencyApiService.getLatestCurrencyRates(baseCurrency).map { currencyRatesResponse ->
            Mapper.getCurrencyListFromMap(currencyRatesResponse.rates,baseRateFactor,localCurrenciesData )
        }

}