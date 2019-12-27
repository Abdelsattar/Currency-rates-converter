package com.sattar.currencyconverter.ui.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

/**
 * Project: Currency Converter
 * Created: 12/24/2019.
 * @author : Mohamed Abd EL-Sattar
 */
abstract class BaseViewModel : ViewModel() {

    val error = MutableLiveData<String>()
    val disposable = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()

        disposable.dispose()
        disposable.clear()
    }
}