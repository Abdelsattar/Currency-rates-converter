package com.sattar.currencyconverter.ui.currencylist

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.sattar.currencyconverter.data.model.CurrencyRate
import com.sattar.currencyconverter.data.model.ServerResponse
import com.sattar.currencyconverter.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.text.DecimalFormat
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Project: Currency Converter
 * Created: 12/24/2019.
 * @author : Mohamed Abd EL-Sattar
 */
class CurrencyListViewModel @Inject constructor(
    private val currencyListRepository: CurrencyListRepository,
    private val localCurrencies: ArrayList<CurrencyRate>
) : BaseViewModel() {

    var baseCurrencyCode = "EUR"
    var baseCurrencyRateFactor = 1.0

    fun getLatestCurrencyRates(): MutableLiveData<ServerResponse<MutableList<CurrencyRate>>> {
        val currencyRatesLiveData = MutableLiveData<ServerResponse<MutableList<CurrencyRate>>>()

        disposable.clear()
        disposable.add(
            currencyListRepository.getLatestCurrencyRates(baseCurrencyCode)
//                .delay(1, TimeUnit.SECONDS)
//                .repeat()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .repeatWhen { completed -> completed.delay(1, TimeUnit.SECONDS) }

                .subscribe({ response ->
                    currencyRatesLiveData.value =
                        ServerResponse(getCurrencyListFromMap(response.rates), 200)
                    Log.e("Currency", response.toString())

                }, { t: Throwable? ->

                    Log.e("Currency", t?.localizedMessage)
                    error.value = t?.localizedMessage

                })
        )

        return currencyRatesLiveData
    }

    private fun getCurrencyListFromMap(rates: Map<String, Double>): MutableList<CurrencyRate> {
        Log.e("Local Currency", localCurrencies.size.toString())

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