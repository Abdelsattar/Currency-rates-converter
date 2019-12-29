package com.sattar.currencyconverter.ui.currencylist

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.core.widget.NestedScrollView
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.sattar.currencyconverter.data.model.CurrencyRate
import com.sattar.currencyconverter.ui.base.BaseActivity
import com.sattar.currencyconverter.ui.base.BaseViewModel
import com.sattar.currencyconverter.util.format
import com.sattar.currencyconverter.util.handleVisibleOrGone
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
        setContentView(com.sattar.currencyconverter.R.layout.activity_main)

        initScreen()
    }

    private fun initScreen() {

        bindCurrencyRate(CurrencyRate())
        setupCurrencyRatesRecyclerView()

        swipeRefreshCurrencyList.setOnRefreshListener {
            getLatestCurrencies()
            showLoading()

            itemBase.handleVisibleOrGone(false)
            txtError.handleVisibleOrGone(false)

            currencyRateAdapter.clearData()
            swipeRefreshCurrencyList.isRefreshing = false
        }

        getLatestCurrencies()

        currencyListViewModel.error.observe(this, Observer {
            hideLoading()
            if (currencyRateAdapter.isDataEmpty()) {
                txtError.handleVisibleOrGone(true)
                txtError.setText(it)
            }

        })
    }

    private fun getLatestCurrencies() {

        currencyListViewModel.getLatestCurrencyRates().observe(this,
            Observer { currenciesResponse ->
                if (pbCurrencyList.isVisible)
                    hideLoading()

                if (!itemBase.isVisible)
                    itemBase.handleVisibleOrGone(true)

                if (txtError.isVisible)
                    itemBase.handleVisibleOrGone(false)

                currenciesResponse?.let {
                    currencyRateAdapter.addCurrencies(it)
                }
            })
    }

    private fun setupCurrencyRatesRecyclerView() {
        currencyRateAdapter.setOnCurrencyRowClick { currencyModel ->
            bindCurrencyRate(currencyModel)
            scrollToTop()
            currencyListViewModel.baseCurrencyCode = currencyModel.currencyCode
            currencyListViewModel.baseCurrencyRateFactor = currencyModel.currencyRate

            getLatestCurrencies()
        }
        rvCurrencyRates.adapter = currencyRateAdapter
        rvCurrencyRates.layoutManager = LinearLayoutManager(this)
    }

    private fun scrollToTop() {
        nestedScrollView.post {
            nestedScrollView.fling(0);  // Sets mLastScrollerY for next command
            nestedScrollView.fullScroll(NestedScrollView.FOCUS_UP)
        }
    }

    private fun bindCurrencyRate(currencyRate: CurrencyRate) {
        txtCurrencyCode.text = currencyRate.currencyCode
        txtCurrencyName.text = currencyRate.currencyName
        etCurrencyRate.setText(currencyRate.currencyRate.format())

        etCurrencyRate.doOnTextChanged { text, start, count, after ->
            if (text.toString().isNotEmpty()) {
                val inputRate = text.toString().toDoubleOrNull()
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
            .placeholder(com.sattar.currencyconverter.R.drawable.ic_placeholder_flag)
            .error(com.sattar.currencyconverter.R.drawable.ic_placeholder_error)
            .into(imgCurrencyFLag);
    }

    private fun hideLoading() {
        pbCurrencyList.handleVisibleOrGone(false)
    }

    private fun showLoading() {
        pbCurrencyList.handleVisibleOrGone(true)
    }

}
