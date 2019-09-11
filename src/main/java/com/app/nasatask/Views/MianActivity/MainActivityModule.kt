package com.app.nasatask.Views.MianActivity

import android.content.Context

import dagger.Module
import dagger.Provides

@Module
class MainActivityModule {
    @Provides
    internal fun providePagerAdapter(context: Context): ImageAdapter {
        return ImageAdapter(context)
    }


}
