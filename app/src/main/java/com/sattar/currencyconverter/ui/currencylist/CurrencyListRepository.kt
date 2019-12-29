package com.sattar.currencyconverter.ui.currencylist

import com.sattar.currencyconverter.data.api.CurrencyApiService
import com.sattar.currencyconverter.ui.base.BaseRepository
import javax.inject.Inject

/**
 * Project: Currency Converter
 * Created: 12/24/2019.
 * @author : Mohamed Abd EL-Sattar
 */
class CurrencyListRepository @Inject constructor(val currencyApiService: CurrencyApiService) : BaseRepository() {

    fun getLatestCurrencyRates(baseCurrency: String) =
        currencyApiService.getLatestCurrencyRates(baseCurrency)


}