package com.app.nasatask.Base


import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import com.app.nasa.DI.DaggerApplicationComponent
import androidx.room.Room
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import com.app.nasatask.DI.database.APODDatabase
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T




class MyApplication : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {


        val component = DaggerApplicationComponent.builder().application(this)
            .build()
        component.inject( this)
         // add database to component builder

        return component
    }


}
