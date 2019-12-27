package com.sattar.currencyconverter.util

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
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


fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}

fun Double.format(): String {
    val dec = DecimalFormat("#.#####")
    return dec.format(this)
}