package com.peng.freeapi.adapter

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.peng.freeapi.R
import com.peng.freeapi.model.AndroidData


/**
 *use:
 *author:    pengchong
 *time:      2018/4/16  14:31
 */

class AndroidAdapter(layoutResId: Int, data: MutableList<AndroidData>? = ArrayList<AndroidData>()) : BaseQuickAdapter<AndroidData, BaseViewHolder>(layoutResId, data) {


    override fun convert(helper: BaseViewHolder?, item: AndroidData?) {
        helper?.setText(R.id.who,item?.who)
        helper?.setText(R.id.desc,item?.desc)

        var time = item?.publishedAt?.replace("T"," ")
        time = time?.substring(0,time.indexOf("."))

        helper?.setText(R.id.publishedAt,time)
        var imageView = helper!!.getView(R.id.image) as ImageView
        if(item?.images!=null && item.images.size>0 && helper!=null){
            imageView.visibility = View.VISIBLE
            Glide.with(mContext).load(item!!.images!![0]).into(imageView)
        }else{
            imageView.visibility = View.GONE
        }
    }


}