package com.peng.freeapi.fragments

import android.app.Dialog
import android.media.Image
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chad.library.adapter.base.BaseQuickAdapter
import com.peng.freeapi.R
import com.peng.freeapi.activitys.PictureViewActivity
import com.peng.freeapi.adapter.ImageListAdapter
import com.peng.freeapi.interfaces.GetRequest_Interface
import com.peng.freeapi.model.DataResponse
import com.peng.freeapi.model.ImageModel
import com.peng.freeapi.model.SimpleDividerGrid
import com.peng.freeapi.utils.CommonUtil
import com.peng.freeapi.utils.NetUtil
import kotlinx.android.synthetic.main.namelistfragment.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ImageListFragment : BaseLazyFragment() {

    lateinit var mListView: RecyclerView
    lateinit var mSwipeRefreshLayout: SwipeRefreshLayout
    var mCurrentPage: Int = 1
    var loadingDialog: Dialog? = null

    override fun initViews(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        val view = inflater.inflate(R.layout.namelistfragment, container, false)

        mListView = view.nameListView
        mSwipeRefreshLayout = view.swipeRefreshLayout
        mListView.addItemDecoration(SimpleDividerGrid(context!!))
        mListView.layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
        mSwipeRefreshLayout.setOnRefreshListener { loadData(1) }

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
        request?.getImageList(page)?.enqueue(object : Callback<DataResponse<ArrayList<ImageModel>>> {
            override fun onFailure(call: Call<DataResponse<ArrayList<ImageModel>>>?, t: Throwable?) {
                if (mListView.adapter == null) {
                    mListView.adapter = ImageListAdapter(R.layout.imagelistitem)
                }
                (mListView.adapter as ImageListAdapter).loadMoreFail()
                mSwipeRefreshLayout.isRefreshing = false
                CommonUtil.showToast("请求失败，请重试")
            }

            override fun onResponse(call: Call<DataResponse<ArrayList<ImageModel>>>?, response: Response<DataResponse<ArrayList<ImageModel>>>?) {
                val adapter: ImageListAdapter
                mSwipeRefreshLayout.isRefreshing = false
                val resultDatas = response?.body()?.results ?: ArrayList<ImageModel>()
                if (mListView.adapter != null) {
                    adapter = mListView.adapter as ImageListAdapter
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
                        ImageListAdapter(R.layout.imagelistitem)
                    } else {
                        ImageListAdapter(R.layout.imagelistitem, resultDatas)
                    }
                    adapter.setOnItemClickListener { adapter, view, position -> PictureViewActivity.launch(activity!!, adapter!!.data as java.util.ArrayList<ImageModel>,position) }
                    adapter.setOnLoadMoreListener { loadData(++mCurrentPage) }
                    adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM)
                    CommonUtil.dismissLoading(loadingDialog)
                    mListView.adapter = adapter
                }
            }

        })

    }


}
