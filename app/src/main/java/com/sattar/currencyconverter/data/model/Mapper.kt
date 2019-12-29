package com.sattar.currencyconverter.data.model

/**
 * Project: Currency Converter
 * Created: 12/29/2019.
 * @author : Mohamed Abd EL-Sattar
 */
object Mapper {

    fun getCurrencyListFromMap(
        rates: Map<String, Double>,
        baseCurrencyRateFactor: Double,
        localCurrenciesData: ArrayList<CurrencyRate>
    ): MutableList<CurrencyRate> {

        val ratesList = ArrayList<CurrencyRate>(rates.size)

        rates.forEach { (currencyCode, currencyRate) ->
            val currencyInfo = localCurrenciesData.filter {
                it.currencyCode == currencyCode
            }.first()


            ratesList.add(
                CurrencyRate(
                    currencyCode,
                    currencyRate * baseCurrencyRateFactor,
                    currencyInfo.currencyName,
                    currencyInfo.currencyFlagUrl
                )
            )
        }

        return ratesList
    }

}