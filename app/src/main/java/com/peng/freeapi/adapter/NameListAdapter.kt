package com.peng.freeapi.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.peng.freeapi.R
import com.peng.freeapi.model.Name

class NameListAdapter(layoutResId: Int, data: MutableList<Name>? = ArrayList<Name>()) : BaseQuickAdapter<Name, BaseViewHolder>(layoutResId, data) {


    override fun convert(helper: BaseViewHolder?, item: Name?) {
        helper?.setText(R.id.name,item?.desc)
        helper?.setText(R.id.index,"${helper.adapterPosition+1}、")

    }


}