package com.sattar.currencyconverter.ui.currencylist

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.sattar.currencyconverter.R
import com.sattar.currencyconverter.data.model.CurrencyRate
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.CropCircleTransformation
import kotlinx.android.synthetic.main.list_item_currency_rate.view.*

/**
 * Project: Currency Converter
 * Created: 12/27/2019.
 * @author : Mohamed Abd EL-Sattar
 */
class CurrencyRateViewHolder(v: View) : RecyclerView.ViewHolder(v) {
    private var view: View = v
    private var currencyRate: CurrencyRate? = null

    fun bindCurrencyRate(currencyRate: CurrencyRate) {
        this.currencyRate = currencyRate
        view.txtCurrencyCode.text = currencyRate.currencyCode
        view.txtCurrencyName.text = currencyRate.currencyName
        view.etCurrencyRate.setText(currencyRate.currencyRate.toString())

        Picasso.get()
            .load(currencyRate.currencyFlagUrl)
            .resize(48, 48)
            .transform(CropCircleTransformation())
            .placeholder(R.drawable.ic_placeholder_flag)
            .error(R.drawable.ic_placeholder_error)
            .into(view.imgCurrencyFLag);
    }

}