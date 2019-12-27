package com.sattar.currencyconverter.data.model

data class CurrencyRate(
    var currencyCode: String, var currencyRate: Double,
    var currencyName: String? = null,
    var currencyFlagUrl: String? = null
)