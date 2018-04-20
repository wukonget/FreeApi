package com.peng.freeapi.activitys

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v4.view.ViewPager
import android.view.MotionEvent
import android.view.View
import com.peng.freeapi.R
import com.peng.freeapi.adapter.BannerAdapter
import com.peng.freeapi.anim.ScaleAnimViewPager
import com.peng.freeapi.interfaces.GetRequest_Interface
import com.peng.freeapi.model.BannerModel
import com.peng.freeapi.model.ImageModel
import com.peng.freeapi.model.WanResponse
import com.peng.freeapi.utils.CommonUtil
import com.peng.freeapi.utils.NetUtil
import kotlinx.android.synthetic.main.activity_wanandroid.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


/**
 *use:      玩安卓 api 模块
 *author:    pengchong
 *time:      2018/4/13  16:19
 */

class WanAndroidActivity : BaseActivity() {

    private val NEXT_BANNER = 1

    private var mAdapter : BannerAdapter? = null

    var mHandler = Handler(object:Handler.Callback{
        override fun handleMessage(msg: Message?): Boolean {
            return when(msg?.what){
                NEXT_BANNER -> {
                    mAdapter?.nextBanner()
                    startChange()
                    true
                }
                else -> false
            }
        }

    })

    companion object Factory {
        fun launch(activity: Activity) {
            val intent = Intent(activity,WanAndroidActivity::class.java)
            activity.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wanandroid)
//        actionBar.title = "玩安卓"
        initViews()
        loadData()
    }

    private fun initViews() {

        mAdapter = BannerAdapter(this,banner,dot_container)
        banner.pageMargin=0
        banner.offscreenPageLimit = 3
        banner.setPageTransformer(true,ScaleAnimViewPager())
        CommonUtil.setViewPagerScroller(banner)
        banner.adapter = mAdapter

    }

    private fun loadData() {
        val request = NetUtil.getWanRetrofit()?.create(GetRequest_Interface::class.java)
        request?.getBannerList()?.enqueue(object : Callback<WanResponse<MutableList<BannerModel>>>{
            override fun onFailure(call: Call<WanResponse<MutableList<BannerModel>>>?, t: Throwable?) {

            }

            override fun onResponse(call: Call<WanResponse<MutableList<BannerModel>>>?, response: Response<WanResponse<MutableList<BannerModel>>>?) {
                val resultDatas = response?.body()?.data ?: ArrayList<BannerModel>()
                mAdapter?.setData(resultDatas)
                startChange()
            }

        })

    }

    private fun startChange() {
        mHandler.removeMessages(NEXT_BANNER)
        var msg = mHandler.obtainMessage(NEXT_BANNER)
        mHandler.sendMessageDelayed(msg,3000)
    }

    override fun onStop() {
        super.onStop()
        mHandler.removeCallbacksAndMessages(null)
    }

    override fun onResume() {
        super.onResume()
        startChange()
    }

}