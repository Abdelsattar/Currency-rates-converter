package com.sattar.currencyconverter.ui.currencylist

import android.os.Bundle
import android.provider.Telephony
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.sattar.currencyconverter.R
import com.sattar.currencyconverter.data.model.CurrencyRate
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

        initScreen()
    }

    fun initScreen() {
        getLatestCurrencies()
    }

    fun getLatestCurrencies() {

        currencyListViewModel.getLatestCurrencyRates("EUR").observe(this,
            Observer { serverResponse ->
                if (serverResponse.status == 200) {
                    Log.e("UI response ", serverResponse?.response.toString())
                    setupCurrencyRatesRecyclerView(serverResponse?.response)
                } else {
                    Log.e("UI response ", serverResponse?.error)

                }
            })

    }

    private lateinit var adapter: CurrencyRatesAdapter

    fun setupCurrencyRatesRecyclerView(rates: MutableList<CurrencyRate>?) {
        adapter = CurrencyRatesAdapter(rates!!)
        rvCurrencyRates.adapter = adapter
        rvCurrencyRates.layoutManager = LinearLayoutManager(this)
    }

}
