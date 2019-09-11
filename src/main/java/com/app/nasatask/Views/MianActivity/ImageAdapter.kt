package com.app.nasatask.Views.MianActivity

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.nasatask.Models.Apod
import com.app.nasatask.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kotlinx.android.synthetic.main.fullscreen_pod_view.view.*
import kotlinx.android.synthetic.main.main_pod_view.view.imageView
import java.util.regex.Pattern
import javax.inject.Inject


class ImageAdapter @Inject
constructor(internal var context: Context) :
    PagedListAdapter<Apod, ImageAdapter.BaseViewHolder>(DIFF_CALLBACK) {

    var layout : Int =1
        set(value) {
            field = value
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val view :View
        if (viewType==1)
            view=LayoutInflater.from(parent.context).inflate(R.layout.main_pod_view, parent, false)
        else
            view=LayoutInflater.from(parent.context).inflate(R.layout.fullscreen_pod_view, parent, false)

        return getViewHolder(view,viewType)

    }

    fun getViewHolder(view: View,viewType: Int):BaseViewHolder
    {
        if (viewType==1)
            return OnlyImageViewHolder(view)
        else
            return ViewHolder(view)

    }

    override fun getItemViewType(position: Int): Int {
        if (layout==1)
            return 1
        else
            return 2

    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {

        var url=currentList?.get(position)?.url
        if (chkUrl(url))
            url=getYoutubeThumbnailVideoUrl(currentList?.get(position)?.url)

        Glide.with(context)
            .load(url)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(holder.image)

        holder.update(currentList?.get(position))

    }


    inner abstract class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image = itemView.imageView
        abstract fun update(apod: Apod?);
    }


    inner class OnlyImageViewHolder(itemView: View) : BaseViewHolder(itemView) {
        override fun update(apod: Apod?) {}

        init {
            image.setOnClickListener{
                val clickEvent =image.context as ClickEvent
                clickEvent.onClick(adapterPosition)

            }
        }
    }
    inner class ViewHolder(itemView: View) : BaseViewHolder(itemView) {
        val title = itemView.title
        val desc = itemView.explaination

        override fun update(apod: Apod?) {
            desc.setText(apod?.explanation)
            title.setText(apod?.title)
        }


    }



    companion object {

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Apod>() {
            override fun areItemsTheSame(oldItem: Apod, newItem: Apod): Boolean {
                return oldItem.title.equals(newItem.title, ignoreCase = true)
            }

            override fun areContentsTheSame(oldItem: Apod, newItem: Apod): Boolean {
                return oldItem == newItem
            }
        }
    }

    fun getYoutubeThumbnailVideoUrl(url: String?) : String?
    {
        var vId: String? = null
        val pattern = Pattern.compile(
            "^https?://.*(?:youtu.be/|v/|u/\\w/|embed/|watch?v=)([^#&?]*).*$",
            Pattern.CASE_INSENSITIVE
        )
        val matcher = pattern.matcher(url)
        if (matcher.matches()) {
            vId = matcher.group(1)
        }
        else
            return url

        val string=StringBuilder("https://img.youtube.com/vi/")
        string.append(vId)
        string.append("/0.jpg")

        return string.toString()

    }

    fun chkUrl(url: String?) :Boolean
    {
        val youtubePattern = Pattern.compile("^(http(s)?://)?((w){3}.)?youtu(be|.be)?(.com)?/.+")

        return youtubePattern.matcher(url).matches()
    }
    interface ClickEvent{
        fun onClick(position: Int)
    }
}

