package com.app.nasatask.ViewModel

import android.util.Log

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList

import com.app.nasatask.DI.Network.ApiService
import com.app.nasatask.DI.database.ApodRepo
import com.app.nasatask.DI.database.RoomDBModule
import com.app.nasatask.Models.Apod
import com.app.nasatask.Views.MianActivity.DataRepos.ApodResult
import com.app.nasatask.Views.MianActivity.DataRepos.LocalRepo
import com.app.nasatask.Views.MianActivity.DataRepos.OnlineRepo

import java.util.concurrent.Executors

import javax.inject.Inject

import io.reactivex.Scheduler
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

import android.content.ContentValues.TAG

class MainActivityViewModel @Inject
constructor(private val repo: ApodRepo, private val service: ApiService) : ViewModel() {

    var networkErrors: LiveData<String>? = null
    var apod: LiveData<PagedList<Apod>>? = null


    init {
        getData()
    }

    private fun getData() {
        val localRepo = LocalRepo(repo)

        val onlineRepo = OnlineRepo(localRepo, service)

        val newsResult = onlineRepo.Search()

        apod = newsResult.data

        networkErrors = newsResult.networkErrors

    }
}
