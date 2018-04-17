package com.peng.freeapi.fragments

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chad.library.adapter.base.BaseQuickAdapter
import com.peng.freeapi.R
import com.peng.freeapi.adapter.AndroidAdapter
import com.peng.freeapi.interfaces.GetRequest_Interface
import com.peng.freeapi.model.AndroidData
import com.peng.freeapi.model.DataResponse
import com.peng.freeapi.utils.CommonUtil
import com.peng.freeapi.utils.NetUtil
import kotlinx.android.synthetic.main.androidlistfragment.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AndroidFragment : BaseLazyFragment() {
    lateinit var mListView: RecyclerView
    lateinit var mSwipeRefreshLayout: SwipeRefreshLayout
    var mCurrentPage: Int = 1
    var loadingDialog: Dialog? = null

    override fun initViews(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.androidlistfragment, container, false)

        mListView = view.androidList
        mSwipeRefreshLayout = view.androidRefresh
        mListView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        mSwipeRefreshLayout.setOnRefreshListener {
            loadData(1)
        }

        return view
    }

    override fun initData() {
        loadData(1)
    }

    override fun setDefaultFragmentTitle(title: String?) {

    }

    private fun loadData(page: Int = 1) {
        mCurrentPage = page
        mSwipeRefreshLayout.isRefreshing = true
        val request = NetUtil.getRetrofit()?.create(GetRequest_Interface::class.java)
        request?.getAndroidList(page)?.enqueue(object : Callback<DataResponse<MutableList<AndroidData>>> {
            override fun onFailure(call: Call<DataResponse<MutableList<AndroidData>>>?, t: Throwable?) {
                CommonUtil.showToast("请求失败，请重试")
                if (mListView.adapter == null) {
                    mListView.adapter = AndroidAdapter(R.layout.androidlistitem)
                }
                (mListView.adapter as AndroidAdapter).loadMoreFail()
                mSwipeRefreshLayout.isRefreshing = false
            }

            override fun onResponse(call: Call<DataResponse<MutableList<AndroidData>>>?, response: Response<DataResponse<MutableList<AndroidData>>>?) {
                val adapter: AndroidAdapter
                mSwipeRefreshLayout.isRefreshing = false
                val resultDatas = response?.body()?.results ?: ArrayList<AndroidData>()
                if (mListView.adapter != null) {
                    adapter = mListView.adapter as AndroidAdapter
                    if (page == 1) {
                        adapter.setNewData(resultDatas)
                    } else if (resultDatas.size>0) {
                        adapter.addData(resultDatas)
                        adapter.loadMoreComplete()
                    }else{
                        adapter.loadMoreEnd()
                    }
                } else {
                    adapter = if (response === null || response.body() === null) {
                        AndroidAdapter(R.layout.androidlistitem)
                    } else {
                        AndroidAdapter(R.layout.androidlistitem, resultDatas)
                    }
                    adapter.onItemClickListener = BaseQuickAdapter.OnItemClickListener { adapter, view, position ->
                        val url = (adapter!!.data[position] as AndroidData).url
                        val uri = Uri.parse(url)
                        val intent = Intent(Intent.ACTION_VIEW, uri)
                        startActivity(intent)
                        //                        startActivity(Intent(activity,ActivityWebView::class.java))
                    }
                    adapter.setOnLoadMoreListener(BaseQuickAdapter.RequestLoadMoreListener { loadData(++mCurrentPage) },mListView)
                    adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM)
                    CommonUtil.dismissLoading(loadingDialog)
                    mListView.adapter = adapter
                }

            }

        })

    }


}
