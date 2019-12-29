package com.sattar.currencyconverter.util

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.io.InputStream
import java.text.DecimalFormat

/**
 * Project: Currency Converter
 * Created: 12/26/2019.
 * @author : Mohamed Abd EL-Sattar
 */


inline fun <reified T> Gson.fromAssets(context: Context, fileName: String): T {
    val inputStream: InputStream = context.assets.open(fileName)
    val size: Int = inputStream.available()
    val buffer = ByteArray(size)
    inputStream.read(buffer)
    inputStream.close()
    val json = String(buffer)
    return this.fromJson<T>(json, object : TypeToken<T>() {}.type)
}


fun Double.format(): String {
    val dec = DecimalFormat("#.#####")
    return dec.format(this)
}

fun CompositeDisposable.clearAndAdd(disposable: Disposable) {
    this.apply {
        clear()
        add(disposable)
    }
}

