package com.sattar.currencyconverter.ui.currencylist

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sattar.currencyconverter.R
import com.sattar.currencyconverter.data.model.CurrencyRate
import com.sattar.currencyconverter.util.inflate

class CurrencyRatesAdapter() :
    RecyclerView.Adapter<CurrencyRateViewHolder>() {


    var currencyRates: MutableList<CurrencyRate> = ArrayList()
    var originalRates: MutableList<CurrencyRate> = ArrayList()

    private lateinit var rowClick: (item: CurrencyRate) -> Unit

    override fun getItemCount(): Int = currencyRates.size

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CurrencyRateViewHolder {
        val inflatedView = parent.inflate(R.layout.list_item_currency_rate, false)
        return CurrencyRateViewHolder(inflatedView)
    }

    fun addCurrencies(currencyRates: MutableList<CurrencyRate>) {
        this.currencyRates.apply {
            clear()
            addAll(currencyRates)
        }
        this.originalRates.apply {
            clear()
            addAll(currencyRates)
        }

        notifyDataSetChanged()
    }

    override fun onBindViewHolder(viewHolder: CurrencyRateViewHolder, position: Int) {
        val currencyRate = currencyRates[position]
        viewHolder.bindCurrencyRate(currencyRate)
        viewHolder.itemView.setOnClickListener {
            rowClick.invoke(currencyRate)
            currencyRates.apply {
//                clear()
//                addAll(originalRates)
                removeAt(position)
            }
            this.notifyDataSetChanged()

        }
    }

    fun setOnCurrencyRowClick(rowClick: (item: CurrencyRate) -> Unit) {
        this.rowClick = rowClick
    }


}