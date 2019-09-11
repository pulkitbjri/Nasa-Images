package com.app.nasatask.Views.MianActivity.DataRepos

import androidx.lifecycle.LiveData
import androidx.paging.PagedList

import com.app.nasatask.Models.Apod

class ApodResult(val data: LiveData<PagedList<Apod>>, val networkErrors: LiveData<String>)
