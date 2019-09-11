package com.app.nasatask.DI.database

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.app.nasatask.Models.Apod

@Dao
interface ApodRepo{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(apod: Apod)

    @Query("Select * from apod order by date DESC")
    fun apodByDate(): DataSource.Factory<Int, Apod>?

}
