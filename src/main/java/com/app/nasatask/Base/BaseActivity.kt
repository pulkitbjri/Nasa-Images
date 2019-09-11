package com.app.nasa.Base

import android.os.Bundle

import androidx.annotation.LayoutRes
import androidx.lifecycle.ViewModel

import butterknife.ButterKnife
import dagger.android.support.DaggerAppCompatActivity
import io.reactivex.annotations.Nullable
import javax.inject.Inject

abstract class BaseActivity<T : ViewModel>  : DaggerAppCompatActivity() {

    private var viewModel: T? = null


    @LayoutRes
    protected abstract fun layoutRes(): Int

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutRes())
        this.viewModel = viewModel ?: getViewModel()
        ButterKnife.bind(this)

    }

    abstract fun getViewModel(): T

}