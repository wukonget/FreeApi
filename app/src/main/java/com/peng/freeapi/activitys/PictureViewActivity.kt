package com.peng.freeapi.activitys

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.view.ViewPager
import com.peng.freeapi.R
import com.peng.freeapi.adapter.PictureViewAdapter
import com.peng.freeapi.model.ImageModel
import kotlinx.android.synthetic.main.pictureview.*
import java.util.*

/**
 * 图片查看
 */

class PictureViewActivity : BaseActivity() {

    var images : ArrayList<ImageModel> = ArrayList<ImageModel>()
    var position = -1

    companion object Factory {
        fun launch(activity: Activity, mImageList: ArrayList<ImageModel>, position: Int) {

            val intent = Intent(activity,PictureViewActivity::class.java)
            intent.putParcelableArrayListExtra(activity.getString(R.string.pictureView_tag_images),mImageList)
            intent.putExtra(activity.getString(R.string.pictureView_tag_index),position)
            activity.startActivity(intent)
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
        pictureViewPager.addOnPageChangeListener(object:ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                index.text = "${position+1} / ${images.size}"
            }

        })
        pictureViewPager.adapter = pictureViewAdapter

        refreshData()

    }

    private fun refreshData() {

        if(pictureViewPager!==null && images.size>0 && position>-1){
            val adapter = pictureViewPager.adapter as PictureViewAdapter
            adapter.mImages = images
            adapter.notifyDataSetChanged()
            pictureViewPager.currentItem = position
            index.text = "${position+1} / ${images.size}"
        }

    }

    override fun onResume() {
        super.onResume()
        images = intent.getParcelableArrayListExtra<ImageModel>(getString(R.string.pictureView_tag_images))
        position = intent.getIntExtra(getString(R.string.pictureView_tag_index),-1)

        refreshData()
    }

}