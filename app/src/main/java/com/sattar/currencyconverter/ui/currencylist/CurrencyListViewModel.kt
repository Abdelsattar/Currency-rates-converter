package com.sattar.currencyconverter.ui.currencylist

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.sattar.currencyconverter.data.model.CurrencyRate
import com.sattar.currencyconverter.data.model.ServerResponse
import com.sattar.currencyconverter.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
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


    fun getLatestCurrencyRates(baseCurrency: String): MutableLiveData<ServerResponse<MutableList<CurrencyRate>>> {
        val currencyRatesLiveData = MutableLiveData<ServerResponse<MutableList<CurrencyRate>>>()

        currencyListRepository.getLatestCurrencyRates(baseCurrency)
//            .delay(5, TimeUnit.SECONDS)
//            .repeat()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                currencyRatesLiveData.value =
                    ServerResponse(getCurrencyListFromMap(response.rates), 200)
                Log.e("Currency", response.toString())

            }, { t: Throwable? ->
                currencyRatesLiveData.value = ServerResponse(t?.localizedMessage!!, 500)

                Log.e("Currency", t?.localizedMessage)

            })

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
                    currencyRate,
                    currencyInfo?.currencyName,
                    currencyInfo?.currencyFlagUrl
                )
            )
        }

        return ratesList
    }


}