package com.peng.freeapi.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.peng.freeapi.R
import com.peng.freeapi.model.Name
import kotlinx.android.synthetic.main.namelistitem.view.*

class NameListAdapter(context: Context, nameList: ArrayList<Name> = ArrayList<Name>()) : RecyclerView.Adapter<NameListAdapter.NameListHolder>() {

    private var mContext = context
    private var mNameList = nameList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NameListHolder {
        return NameListHolder(LayoutInflater.from(mContext).inflate(R.layout.namelistitem,parent,false))

    }

    override fun getItemCount(): Int {
        return mNameList.size
    }

    override fun onBindViewHolder(holder: NameListHolder, position: Int) {
        holder.name.text = mNameList[position].desc
        holder.index.text = "${position+1}、"
    }

    fun setData(namelist: ArrayList<Name>?) {
        if(namelist === null){
            mNameList = ArrayList<Name>()
        }else {
            mNameList = namelist!!
        }
        notifyDataSetChanged()
    }

    fun addData(data: ArrayList<Name>) {
        mNameList.addAll(data)
        notifyDataSetChanged()
    }


    class NameListHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name = itemView.name
        var index = itemView.index
    }
}