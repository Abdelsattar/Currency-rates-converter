package com.sattar.currencyconverter.data.api

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query


/**
 * Project: Currency Converter
 * Created: 12/24/2019.
 * @author : Mohamed Abd EL-Sattar
 */
interface CurrencyApiService {

    companion object {
        private const val PATH_LATEST = "latest"
        private const val QUERY_BASE = "base"
    }

    @GET(PATH_LATEST)
    fun getLatestCurrencyRates(@Query(QUERY_BASE) baseCurrency: String): Single<String>

}