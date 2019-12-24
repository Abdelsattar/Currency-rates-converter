package com.sattar.currencyconverter.di


import androidx.lifecycle.ViewModel
import dagger.MapKey
import java.lang.annotation.*
import java.lang.annotation.Retention
import java.lang.annotation.Target
import kotlin.reflect.KClass

/**
 * Project: Currency Converter
 * Created: 12/24/2019.
 * @author : Mohamed Abd EL-Sattar
 */

@Suppress("DEPRECATED_JAVA_ANNOTATION")
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@MapKey
internal annotation class ViewModelKey(val value: KClass<out ViewModel>)