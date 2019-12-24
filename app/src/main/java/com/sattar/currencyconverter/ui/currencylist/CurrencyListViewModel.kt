package com.sattar.currencyconverter.ui.currencylist

import android.util.Log
import com.sattar.currencyconverter.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Project: Currency Converter
 * Created: 12/24/2019.
 * @author : Mohamed Abd EL-Sattar
 */
class CurrencyListViewModel @Inject constructor(val currencyListRepository: CurrencyListRepository) :
    BaseViewModel() {


    fun getLatestCurrencyRates(baseCurrency: String) {

        currencyListRepository.getLatestCurrencyRates(baseCurrency)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->

                Log.e("Currency", response.toString())

            }, { t: Throwable? ->
                Log.e("Currency", t?.localizedMessage)

            })

    }
}