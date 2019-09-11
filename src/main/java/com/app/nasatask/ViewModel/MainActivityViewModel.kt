package com.app.nasatask.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.app.nasatask.DI.Network.ApiService
import com.app.nasatask.DI.database.ApodRepo
import com.app.nasatask.Models.Apod
import com.app.nasatask.Views.MianActivity.DataRepos.LocalRepo
import com.app.nasatask.Views.MianActivity.DataRepos.OnlineRepo
import javax.inject.Inject

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
