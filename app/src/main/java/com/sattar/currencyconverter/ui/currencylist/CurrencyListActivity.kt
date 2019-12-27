package com.sattar.currencyconverter.ui.currencylist

import android.os.Bundle
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.sattar.currencyconverter.R
import com.sattar.currencyconverter.data.model.CurrencyRate
import com.sattar.currencyconverter.ui.base.BaseActivity
import com.sattar.currencyconverter.ui.base.BaseViewModel
import com.sattar.currencyconverter.util.format
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.CropCircleTransformation
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.list_item_currency_rate.*
import javax.inject.Inject

class CurrencyListActivity : BaseActivity() {


    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var currencyListViewModel: CurrencyListViewModel

    private var currencyRateAdapter = CurrencyRatesAdapter()

    override fun getViewModel(): BaseViewModel = currencyListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initScreen()
    }

    private fun initScreen() {

        bindCurrencyRate(CurrencyRate())
        setupCurrencyRatesRecyclerView()

        swipeRefreshCurrencyList.setOnRefreshListener {
            getLatestCurrencies()
            swipeRefreshCurrencyList.isRefreshing = false
        }

        getLatestCurrencies()

    }

    private fun getLatestCurrencies() {

        currencyListViewModel.getLatestCurrencyRates().observe(this,
            Observer { serverResponse ->
                serverResponse.response?.let {
                    currencyRateAdapter.addCurrencies(it)
                }
            })

    }

    private fun setupCurrencyRatesRecyclerView() {
        currencyRateAdapter.setOnCurrencyRowClick { currencyModel ->
            bindCurrencyRate(currencyModel)
            currencyListViewModel.baseCurrencyCode = currencyModel.currencyCode
            getLatestCurrencies()
        }
        rvCurrencyRates.adapter = currencyRateAdapter
        rvCurrencyRates.layoutManager = LinearLayoutManager(this)
    }

    private fun bindCurrencyRate(currencyRate: CurrencyRate) {
        txtCurrencyCode.text = currencyRate.currencyCode
        txtCurrencyName.text = currencyRate.currencyName
        etCurrencyRate.setText(currencyRate.currencyRate.format())

        etCurrencyRate.doOnTextChanged { text, start, count, after ->
            if (text.toString().isNotEmpty()) {
                var inputRate = text.toString().toDoubleOrNull()
                inputRate?.let {
                    currencyListViewModel.baseCurrencyRateFactor = it
                    getLatestCurrencies()
                }

            }
        }

        Picasso.get()
            .load(currencyRate.currencyFlagUrl)
            .resize(48, 48)
            .transform(CropCircleTransformation())
            .placeholder(R.drawable.ic_placeholder_flag)
            .error(R.drawable.ic_placeholder_error)
            .into(imgCurrencyFLag);
    }

}
