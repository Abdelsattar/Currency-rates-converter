package com.sattar.currencyconverter.di;

import com.sattar.currencyconverter.ui.currencylist.CurrencyListActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityModule {
    @ContributesAndroidInjector
    abstract CurrencyListActivity contributeCurrencyListActivity();

}