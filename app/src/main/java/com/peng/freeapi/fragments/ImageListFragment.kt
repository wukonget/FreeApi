package com.peng.freeapi.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.peng.freeapi.R
import com.peng.freeapi.adapter.ImageListAdapter
import com.peng.freeapi.adapter.NameListAdapter
import com.peng.freeapi.interfaces.GetRequest_Interface
import com.peng.freeapi.model.DataResponse
import com.peng.freeapi.model.ImageModel
import com.peng.freeapi.model.Name
import com.peng.freeapi.utils.NetUtil
import kotlinx.android.synthetic.main.namelistfragment.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ImageListFragment : Fragment() {

    lateinit var mListView: RecyclerView
    lateinit var mSwipeRefreshLayout: SwipeRefreshLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.namelistfragment, container, false)

        mListView = view.nameListView
        mListView.layoutManager = GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)

        mSwipeRefreshLayout = view.swipeRefreshLayout
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorAccent)
        mSwipeRefreshLayout.setOnRefreshListener {
            loadData()
        }

        loadData()

        return view
    }

    private fun loadData() {
        if (!mSwipeRefreshLayout.isRefreshing) {
            mSwipeRefreshLayout.isRefreshing = true
        }
        val request = NetUtil.getRetrofit()?.create(GetRequest_Interface::class.java)
        request?.getImageList()?.enqueue(object : Callback<DataResponse<ArrayList<ImageModel>>> {
            override fun onFailure(call: Call<DataResponse<ArrayList<ImageModel>>>?, t: Throwable?) {
                mSwipeRefreshLayout.isRefreshing = false
                Toast.makeText(context, "请求失败，请重试", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<DataResponse<ArrayList<ImageModel>>>?, response: Response<DataResponse<ArrayList<ImageModel>>>?) {
                mSwipeRefreshLayout.isRefreshing = false
                Toast.makeText(context, "请求成功，共${response?.body()?.data?.size}条数据", Toast.LENGTH_SHORT).show()
                var adapter: ImageListAdapter
                if (mListView.adapter != null) {
                    adapter = mListView.adapter as ImageListAdapter
                    adapter.setData(response?.body()?.data)
                } else {
                    adapter = if (response === null || response.body() === null) {
                        ImageListAdapter(context!!)
                    } else {
                        ImageListAdapter(context!!, response.body()!!.data)
                    }
                }
                mListView.adapter = adapter
//                mListView.adapter.notifyDataSetChanged()
            }

        })

    }


}
