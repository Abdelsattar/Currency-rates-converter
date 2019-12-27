package com.sattar.currencyconverter.ui.base

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer

/**
 * Project: Currency Converter
 * Created: 12/24/2019.
 * @author : Mohamed Abd EL-Sattar
 */

abstract class BaseActivity : AppCompatActivity() {


    fun showLoading() {
        //todo implement when needed in the activity
    }

    fun hideLoading() {
        //todo implement when needed in the activity
    }

    fun showError(errorMessage: String) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
    }

    override fun onStart() {
        super.onStart()
        getViewModel().error.observe(this, Observer {
            showError(it)
        })
    }

    abstract fun getViewModel(): BaseViewModel


}