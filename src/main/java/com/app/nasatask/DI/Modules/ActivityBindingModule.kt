package com.app.nasatask.DI.Modules

import com.app.nasatask.Views.MianActivity.MainActivity
import com.app.nasatask.Views.MianActivity.MainActivityModule

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    abstract fun contributesMainActivity(): MainActivity


}