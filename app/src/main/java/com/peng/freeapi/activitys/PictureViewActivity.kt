package com.peng.freeapi.activitys

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import com.peng.freeapi.R
import com.peng.freeapi.adapter.PictureViewAdapter
import com.peng.freeapi.model.ImageModel
import kotlinx.android.synthetic.main.pictureview.*
import java.util.ArrayList

/**
 * 图片查看
 */

class PictureViewActivity : BaseActivity() {

    var images : ArrayList<ImageModel> = ArrayList<ImageModel>()
    var position = -1

    companion object Factory {
        fun launch(mActivity: Activity, mImageList: ArrayList<ImageModel>, position: Int) {
            val intent = Intent(mActivity,PictureViewActivity::class.java)
            intent.putParcelableArrayListExtra(mActivity.getString(R.string.pictureView_tag_images),mImageList)
            intent.putExtra(mActivity.getString(R.string.pictureView_tag_index),position)
            mActivity.startActivity(intent)
        }
    }

    lateinit var pictureViewAdapter : PictureViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pictureview)
        initViews()
    }

    private fun initViews() {

        pictureViewAdapter = PictureViewAdapter(this@PictureViewActivity)
        pictureViewPager.adapter = pictureViewAdapter

        refreshData()

    }

    private fun refreshData() {

        if(pictureViewPager!==null && images.size>0 && position>-1){
            val adapter = pictureViewPager.adapter as PictureViewAdapter
            adapter.mImages = images
            adapter.notifyDataSetChanged()
            pictureViewPager.currentItem = position
        }

    }

    override fun onResume() {
        super.onResume()
        images = intent.getParcelableArrayListExtra<ImageModel>(getString(R.string.pictureView_tag_images))
        position = intent.getIntExtra(getString(R.string.pictureView_tag_index),-1)

        refreshData()
    }

}