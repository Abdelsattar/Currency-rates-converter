package com.sattar.currencyconverter.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sattar.currencyconverter.ui.currencylist.CurrencyListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Project: Currency Converter
 * Created: 12/24/2019.
 * @author : Mohamed Abd EL-Sattar
 */

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(CurrencyListViewModel::class)
    abstract fun bindCurrencyListViewModel(editPlaceViewModel: CurrencyListViewModel): ViewModel

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

}