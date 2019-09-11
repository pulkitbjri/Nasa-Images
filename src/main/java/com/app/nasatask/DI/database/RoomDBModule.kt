package com.app.nasatask.DI.database

import android.content.Context

import androidx.room.Room

import javax.inject.Singleton

import dagger.Module
import dagger.Provides

@Module
class RoomDBModule {

    @Singleton
    @Provides
    fun provideApodDatabase(context: Context): APODDatabase {
        return Room.databaseBuilder(
            context,
            APODDatabase::class.java, APODDatabase.DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }

    @Singleton
    @Provides
    fun provideApodDao(apodDatabase: APODDatabase): ApodRepo {
        return apodDatabase.newsDao()
    }
}