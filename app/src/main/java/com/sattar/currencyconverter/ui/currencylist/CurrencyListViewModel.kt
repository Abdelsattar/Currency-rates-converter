package com.sattar.currencyconverter.ui.currencylist

import androidx.lifecycle.MutableLiveData
import com.sattar.currencyconverter.data.model.CurrencyRate
import com.sattar.currencyconverter.di.BaseSchedulerProvider
import com.sattar.currencyconverter.ui.base.BaseViewModel
import com.sattar.currencyconverter.util.clearAndAdd
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Project: Currency Converter
 * Created: 12/24/2019.
 * @author : Mohamed Abd EL-Sattar
 */
class CurrencyListViewModel @Inject constructor(
    private val currencyListRepository: CurrencyListRepository,
    private val localCurrencies: ArrayList<CurrencyRate>,
    private val schedulerProvider: BaseSchedulerProvider
) : BaseViewModel() {

    var baseCurrencyCode = "EUR"
    var baseCurrencyRateFactor = 1.0

    fun getLatestCurrencyRates() = MutableLiveData<MutableList<CurrencyRate>>().apply {
        disposable.clearAndAdd(
            currencyListRepository.getLatestCurrencyRates(baseCurrencyCode, baseCurrencyRateFactor)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .repeatWhen { completed -> completed.delay(1, TimeUnit.SECONDS) }
                .subscribe({ response ->
                    this.value = response
                }, { t: Throwable? ->
                    error.value = t?.localizedMessage

                })
        )
    }

}