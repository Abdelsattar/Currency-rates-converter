package com.sattar.currencyconverter.data.model

/**
 * Project: Currency Converter
 * Created: 12/25/2019.
 * @author : Mohamed Abd EL-Sattar
 */
class ServerResponse<T> {

    val response: T?
    val error: String?
    val status: Int

    constructor(response: T?, status: Int) {
        this.response = response
        this.status = status
        error = null
    }

    constructor(error: String, status: Int) {
        this.error = error
        this.status = status
        response = null
    }

}