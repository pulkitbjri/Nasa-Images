package com.app.nasatask.Views.MianActivity.DataRepos

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList

import com.app.nasatask.DI.Network.ApiService
import com.app.nasatask.Models.Apod

class OnlineRepo(internal var localRepo: LocalRepo, internal var service: ApiService) {

    fun Search(): ApodResult {
        // Get data from the local cache
        val dataSourceFactory = localRepo.apods()

        val boundaryCallback = ApodBoundaryCallback(service, localRepo)

        val networkErrors = boundaryCallback.networkErrors

        val config = PagedList.Config.Builder()
            .setPageSize(1111)
            .setPrefetchDistance(1)
            .setEnablePlaceholders(true)
            .build()
        //returns LiveData
        val data = LivePagedListBuilder(dataSourceFactory!!, 1000)
            .setBoundaryCallback(boundaryCallback)
            .build()

        return ApodResult(data, networkErrors)
    }
}
