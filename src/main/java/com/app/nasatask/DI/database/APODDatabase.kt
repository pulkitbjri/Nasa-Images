package com.app.nasatask.DI.database


import android.content.Context

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

import com.app.nasatask.Models.Apod

@Database(entities = [Apod::class], version = 1,exportSchema = false)
abstract class APODDatabase : RoomDatabase() {

    abstract fun newsDao(): ApodRepo

    companion object {
        val DATABASE_NAME = "apod.db"
    }


}
