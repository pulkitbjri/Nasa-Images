package com.app.nasatask.Views.MianActivity.DataRepos

import androidx.paging.DataSource
import com.app.nasatask.DI.database.ApodRepo
import com.app.nasatask.Models.Apod
import com.app.nasatask.interfaces.InsertFinished
import java.util.concurrent.Executors

class LocalRepo(internal var repo: ApodRepo) {

    internal fun apods (): DataSource.Factory<Int, Apod>?
    {
        return repo.apodByDate()
    }

    internal fun insert(repos: Apod, finished: InsertFinished) {
        Executors.newSingleThreadExecutor().execute {
            repo.insert(repos)
            finished.insertFinished()
        }
    }

}
