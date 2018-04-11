package com.peng.freeapi.model

import android.os.Parcel
import android.os.Parcelable

/**
 * 美图模块
 */

data class ImageModel(
        val createdAt: String,
        val publishedAt: String,
        val type: String,
        val url: String
) : Parcelable {
    constructor(source: Parcel) : this(
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(createdAt)
        writeString(publishedAt)
        writeString(type)
        writeString(url)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<ImageModel> = object : Parcelable.Creator<ImageModel> {
            override fun createFromParcel(source: Parcel): ImageModel = ImageModel(source)
            override fun newArray(size: Int): Array<ImageModel?> = arrayOfNulls(size)
        }
    }
}