package com.app.nasatask.Views.MianActivity.DataRepos

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList

import com.app.nasa.Constants
import com.app.nasatask.DI.Network.ApiService
import com.app.nasatask.Models.Apod
import com.app.nasatask.interfaces.InsertFinished

import java.text.SimpleDateFormat

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import java.util.*

class ApodBoundaryCallback(
    private val apodService: ApiService,
    private val newsRepositoryLocal: LocalRepo) : PagedList.BoundaryCallback<Apod>() {

    internal var simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
    val networkErrors: MutableLiveData<String>
    private var isRequestInProgress = false

    init {
        networkErrors = MutableLiveData()
    }

    override fun onZeroItemsLoaded() {
        requestAndSaveData(false,null)
    }

    override fun onItemAtEndLoaded(itemAtEnd: Apod) {
        requestAndSaveData(true,itemAtEnd)
    }

    private fun requestAndSaveData(b: Boolean, apod: Apod?) {
        if (isRequestInProgress)
            return

        isRequestInProgress = true

        val dateString: String?
        if (b)
            dateString = getdate(apod?.date)
        else
            dateString= getTodaysdate()

        Log.i("asdas", "asjbfjhads asdf hja $dateString")
        val callback = apodService.apod(Constants.APIKEY, dateString!!)

        callback
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableSingleObserver<Apod>() {
                override fun onSuccess(apod: Apod) {
                    newsRepositoryLocal.insert(apod, object : InsertFinished{
                        override fun insertFinished() {
                            isRequestInProgress = false
                        }
                    })
                }

                override fun onError(e: Throwable) {
                    networkErrors.postValue(e.message)
                    isRequestInProgress = false
                }
            })
    }

    fun getdate(date: String?): String? {

        var dateold=simpleDateFormat.parse(date)
        val cal = Calendar.getInstance()
        cal.time=dateold
        cal.add(Calendar.DATE, -1)
        return simpleDateFormat.format(cal.time)
    }
    fun getTodaysdate(): String? {

        val cal = Calendar.getInstance()
        cal.add(Calendar.DATE, -1)
        return simpleDateFormat.format(cal.time)
    }
}
