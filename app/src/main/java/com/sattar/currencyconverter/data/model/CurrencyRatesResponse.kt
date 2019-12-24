package com.sattar.currencyconverter.data.model

data class CurrencyRatesResponse(
    val base: String,
    val date: String,
    val rates: Rates
)