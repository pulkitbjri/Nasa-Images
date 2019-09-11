package com.app.nasatask.Models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity(tableName = "apod")
data class Apod(
    @SerializedName("copyright")
    var copyright: String ?=null,
    @PrimaryKey
    @SerializedName("date")
    var date: String,
    @SerializedName("explanation")
    var explanation: String?=null,
    @SerializedName("hdurl")
    var hdurl: String?=null,
    @SerializedName("media_type")
    var mediaType: String?=null,
    @SerializedName("title")
    var title: String?=null,
    @SerializedName("url")
    var url: String?=null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Apod) return false

        if (copyright != other.copyright) return false
        if (date != other.date) return false
        if (explanation != other.explanation) return false
        if (hdurl != other.hdurl) return false
        if (mediaType != other.mediaType) return false
        if (title != other.title) return false
        if (url != other.url) return false

        return true
    }

    override fun hashCode(): Int {
        var result = copyright?.hashCode() ?: 0
        result = 31 * result + date.hashCode()
        result = 31 * result + (explanation?.hashCode() ?: 0)
        result = 31 * result + (hdurl?.hashCode() ?: 0)
        result = 31 * result + (mediaType?.hashCode() ?: 0)
        result = 31 * result + (title?.hashCode() ?: 0)
        result = 31 * result + (url?.hashCode() ?: 0)
        return result
    }
}