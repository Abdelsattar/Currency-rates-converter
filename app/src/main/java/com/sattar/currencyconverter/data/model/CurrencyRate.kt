package com.sattar.currencyconverter.data.model

data class CurrencyRate(
    var currencyCode: String = "EUR",
    var currencyRate: Double = 1.0,
    var currencyName: String? = "Euro",
    var currencyFlagUrl: String? = "https://myflag.com.au/wp-content/uploads/european-union-flag.jpg"
)