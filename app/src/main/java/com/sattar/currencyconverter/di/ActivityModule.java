package com.sattar.currencyconverter.di;

import com.sattar.currencyconverter.ui.currencylist.CurrencyListActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Project: Currency Converter
 * Created: 12/24/2019.
 * @author : Mohamed Abd EL-Sattar
 */

@Module
public abstract class ActivityModule {
    @ContributesAndroidInjector
    abstract CurrencyListActivity contributeCurrencyListActivity();

}