package com.peng.freeapi.fragments

import android.app.Dialog
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jcodecraeer.xrecyclerview.ProgressStyle
import com.jcodecraeer.xrecyclerview.XRecyclerView
import com.peng.freeapi.R
import com.peng.freeapi.adapter.NameListAdapter
import com.peng.freeapi.interfaces.GetRequest_Interface
import com.peng.freeapi.model.DataResponse
import com.peng.freeapi.model.Name
import com.peng.freeapi.model.SimpleDividerLinear
import com.peng.freeapi.utils.CommonUtil
import com.peng.freeapi.utils.NetUtil
import kotlinx.android.synthetic.main.namelistfragment.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NameListFragment : BaseLazyFragment() {
    lateinit var mListView: XRecyclerView
    var mCurrentPage: Int = 1
    var loadingDialog: Dialog? = null

    override fun initViews(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.namelistfragment, container, false)

        mListView = view.nameListView
//        mListView.setLimitNumberToCallLoadMore(2)
        mListView.setRefreshProgressStyle(ProgressStyle.BallClipRotateMultiple)
        mListView.addItemDecoration(SimpleDividerLinear(context!!))
        mListView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        mListView.setLoadingListener(object : XRecyclerView.LoadingListener {
            override fun onLoadMore() {
                loadData(++mCurrentPage)
            }

            override fun onRefresh() {
                loadData(1)
            }

        })

        return view
    }

    override fun initData() {
        loadData(1)
    }

    override fun setDefaultFragmentTitle(title: String?) {

    }

    private fun loadData(page: Int = 1) {
        mCurrentPage = page

        val request = NetUtil.getRetrofit()?.create(GetRequest_Interface::class.java)
        request?.getNameList(page)?.enqueue(object : Callback<DataResponse<ArrayList<Name>>> {
            override fun onFailure(call: Call<DataResponse<ArrayList<Name>>>?, t: Throwable?) {
                CommonUtil.showToast("请求失败，请重试")
            }

            override fun onResponse(call: Call<DataResponse<ArrayList<Name>>>?, response: Response<DataResponse<ArrayList<Name>>>?) {
                val adapter: NameListAdapter
                if (mListView.adapter != null) {
                    adapter = mListView.adapter as NameListAdapter
                    if (page == 1) {
                        adapter.setData(response?.body()?.results)
                    } else if (response?.body()?.results !== null) {
                        adapter.addData(response.body()?.results!!)
                    }
                    mListView.refreshComplete()
                } else {
                    adapter = if (response === null || response.body() === null) {
                        NameListAdapter(context!!)
                    } else {
                        NameListAdapter(context!!, response.body()!!.results)
                    }
                    CommonUtil.dismissLoading(loadingDialog)
                    mListView.adapter = adapter
                }

            }

        })

    }


}
