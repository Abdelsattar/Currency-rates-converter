package com.sattar.currencyconverter.ui.currencylist

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.sattar.currencyconverter.R
import com.sattar.currencyconverter.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class CurrencyListActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var currencyListViewModel: CurrencyListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getLatestCurrencies(txt)
    }

    fun getLatestCurrencies(view: View) {
        currencyListViewModel.getLatestCurrencyRates("EUR")

    }

}
