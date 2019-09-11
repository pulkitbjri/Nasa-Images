package com.app.nasatask.Views.MianActivity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.nasa.Base.BaseActivity
import com.app.nasatask.DI.VMFactory.DaggerViewModelFactory
import com.app.nasatask.Models.Apod
import com.app.nasatask.ViewModel.MainActivityViewModel
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject
import android.app.Dialog
import android.graphics.Rect
import android.view.LayoutInflater
import android.view.Window
import android.view.ViewGroup
import androidx.recyclerview.widget.PagerSnapHelper
import kotlinx.android.synthetic.main.dialog.view.*
import com.app.nasatask.R


class MainActivity: BaseActivity<MainActivityViewModel>() ,ImageAdapter.ClickEvent{


    @Inject
    lateinit var adapter:ImageAdapter

    @Inject
    internal lateinit var viewModel: MainActivityViewModel


    override fun layoutRes(): Int {
        return com.app.nasatask.R.layout.activity_main
    }

    @Inject
    lateinit var viewModeFactory: DaggerViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var layoutManager = GridLayoutManager(this,2)
        recycler.layoutManager=layoutManager
        recycler.adapter=adapter

        viewModel.apod?.observe(this,
            Observer<PagedList<Apod>> {
                showEmptyList(it.size == 0)
                adapter.submitList(it)
            })
        viewModel.networkErrors?.observe(this,
            Observer<String> {
                Toast.makeText(applicationContext, it, Toast.LENGTH_SHORT).show()
            })
    }

    override fun getViewModel(): MainActivityViewModel {
        return viewModel
    }


    private fun showEmptyList(show: Boolean) {
        if (show) {
            emptyList.visibility = View.VISIBLE
            recycler.visibility = View.GONE
        } else {
            emptyList.visibility = View.GONE
            recycler.visibility = View.VISIBLE
        }

    }

    override fun onClick(position: Int) {
        openDialog(position)
    }

    private fun openDialog(position: Int) {

        val displayRectangle = Rect()
        val window = this@MainActivity.window
        window.decorView.getWindowVisibleDisplayFrame(displayRectangle)

        val dialog = Dialog(this, android.R.style.Theme_Light)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val viewGroup = findViewById<ViewGroup>(android.R.id.content)
        val v=LayoutInflater.from(this).inflate(R.layout.dialog,viewGroup,false)
        v.setMinimumWidth((displayRectangle.width() * 1f).toInt());
        v.setMinimumHeight((displayRectangle.height() * 1f).toInt());
        dialog.setContentView(v)

        var layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false)
        v.recycler.layoutManager=layoutManager
        val snapHelper=PagerSnapHelper()
        adapter.layout=2
        v.recycler.adapter=adapter
        snapHelper.attachToRecyclerView(v.recycler)

        v.recycler.scrollToPosition(position)
        dialog.setOnDismissListener{
            adapter.layout= 1
        }
        dialog.show()

    }
}
