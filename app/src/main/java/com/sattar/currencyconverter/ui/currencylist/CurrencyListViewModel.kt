package com.sattar.currencyconverter.ui.currencylist

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.sattar.currencyconverter.data.model.CurrencyRatesResponse
import com.sattar.currencyconverter.data.model.ServerResponse
import com.sattar.currencyconverter.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Project: Currency Converter
 * Created: 12/24/2019.
 * @author : Mohamed Abd EL-Sattar
 */
class CurrencyListViewModel @Inject constructor(val currencyListRepository: CurrencyListRepository) :
    BaseViewModel() {



    fun getLatestCurrencyRates(baseCurrency: String): MutableLiveData<ServerResponse<CurrencyRatesResponse>> {
        var currencyRatesLiveData = MutableLiveData<ServerResponse<CurrencyRatesResponse>>()

        currencyListRepository.getLatestCurrencyRates(baseCurrency)
            .delay(5, TimeUnit.SECONDS)
            .repeat()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                currencyRatesLiveData.value = ServerResponse(response, 200)
                Log.e("Currency", response.toString())

            }, { t: Throwable? ->
                currencyRatesLiveData.value = ServerResponse(t?.localizedMessage!!, 500)

                Log.e("Currency", t?.localizedMessage)

            })

        return currencyRatesLiveData

    }
}