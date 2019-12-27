package com.sattar.currencyconverter.ui.currencylist

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sattar.currencyconverter.R
import com.sattar.currencyconverter.data.model.CurrencyRate
import com.sattar.currencyconverter.util.inflate

class CurrencyRatesAdapter(private val currencyRates: MutableList<CurrencyRate>) :
    RecyclerView.Adapter<CurrencyRateViewHolder>() {


    override fun getItemCount(): Int = currencyRates.size

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CurrencyRateViewHolder {
        val inflatedView = parent.inflate(R.layout.list_item_currency_rate, false)
        return CurrencyRateViewHolder(inflatedView)
    }

    override fun onBindViewHolder(viewHolder: CurrencyRateViewHolder, position: Int) {
        val currencyRate = currencyRates[position]
        viewHolder.bindCurrencyRate(currencyRate)
    }


}