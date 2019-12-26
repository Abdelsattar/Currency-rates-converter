package com.sattar.currencyconverter.ui.currencylist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.sattar.currencyconverter.R
import com.sattar.currencyconverter.data.model.CurrencyRate
import kotlinx.android.synthetic.main.list_item_currency_rate.view.*

class CurrencyRatesAdapter(private val currencyRates: ArrayList<CurrencyRate>) :
    RecyclerView.Adapter<CurrencyRatesAdapter.CurrencyRateHolder>() {


    override fun getItemCount(): Int = currencyRates.size

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CurrencyRateHolder {
        val inflatedView = parent.inflate(R.layout.list_item_currency_rate, false)
        return CurrencyRateHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: CurrencyRateHolder, position: Int) {
        val currencyRate = currencyRates[position]
        holder.bindCurrencyRate(currencyRate)
    }

    fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
        return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
    }

    class CurrencyRateHolder(v: View) : RecyclerView.ViewHolder(v) {
        private var view: View = v
        private var currencyRate: CurrencyRate? = null

        fun bindCurrencyRate(currencyRate: CurrencyRate) {
            this.currencyRate = currencyRate
            view.txtCurrencyCode.text = currencyRate.currencyCode
            view.etCurrencyRate.setText(currencyRate.CurrencyRate.toString())
        }

    }
}