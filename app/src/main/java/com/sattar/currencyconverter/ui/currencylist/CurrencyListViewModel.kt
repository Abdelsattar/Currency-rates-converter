package com.sattar.currencyconverter.ui.currencylist

import androidx.lifecycle.MutableLiveData
import com.sattar.currencyconverter.data.model.CurrencyRate
import com.sattar.currencyconverter.di.BaseSchedulerProvider
import com.sattar.currencyconverter.ui.base.BaseViewModel
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Named

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

    fun getLatestCurrencyRates(): MutableLiveData<MutableList<CurrencyRate>> {
        val currencyRatesLiveData = MutableLiveData<MutableList<CurrencyRate>>()

        disposable.clear()
        disposable.add(
            currencyListRepository.getLatestCurrencyRates(baseCurrencyCode)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .repeatWhen { completed -> completed.delay(1, TimeUnit.SECONDS) }
                .subscribe({ response ->
                    currencyRatesLiveData.value =
                        getCurrencyListFromMap(response.rates)

                }, { t: Throwable? ->
                    error.value = t?.localizedMessage

                })
        )

        return currencyRatesLiveData
    }

    private fun getCurrencyListFromMap(rates: Map<String, Double>): MutableList<CurrencyRate> {

        val ratesList = ArrayList<CurrencyRate>(rates.size)

        rates.forEach { (currencyCode, currencyRate) ->
            val currencyInfo = localCurrencies.filter {
                it.currencyCode == currencyCode
            }.first()


            ratesList.add(
                CurrencyRate(
                    currencyCode,
                    currencyRate * baseCurrencyRateFactor,
                    currencyInfo.currencyName,
                    currencyInfo.currencyFlagUrl
                )
            )
        }

        return ratesList
    }


}