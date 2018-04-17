package com.peng.freeapi.adapter

import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.peng.freeapi.R
import com.peng.freeapi.model.ImageModel

class ImageListAdapter(layoutResId: Int, data: MutableList<ImageModel>? = ArrayList<ImageModel>()) : BaseQuickAdapter<ImageModel, BaseViewHolder>(layoutResId, data) {


    override fun convert(helper: BaseViewHolder?, item: ImageModel?) {
        Glide.with(mContext)
                .load(item!!.url)
                .into(helper!!.getView(R.id.pictureViewPager))

    }
}