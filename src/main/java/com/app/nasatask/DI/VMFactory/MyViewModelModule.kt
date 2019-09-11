package com.app.nasatask.DI.VMFactory

import androidx.lifecycle.ViewModel
import com.app.nasatask.ViewModel.MainActivityViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class MyViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(MainActivityViewModel::class)
    abstract fun bindMyViewModel(myViewModel: MainActivityViewModel): ViewModel



}