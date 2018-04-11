package com.peng.freeapi.activitys

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.util.Log
import android.widget.Toast
import com.peng.freeapi.R
import com.peng.freeapi.adapter.PictureViewAdapter
import com.peng.freeapi.interfaces.GetRequest_Interface
import com.peng.freeapi.model.ImageModel
import com.peng.freeapi.utils.NetUtil
import kotlinx.android.synthetic.main.pictureview.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import android.Manifest.permission
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.support.v4.app.ActivityCompat
import android.content.pm.PackageManager





/**
 * 图片查看
 */

class PictureViewActivity : BaseActivity() {

    var images : ArrayList<ImageModel> = ArrayList<ImageModel>()
    var mPosition = -1

    private val REQUEST_EXTERNAL_STORAGE = 1
    private val PERMISSIONS_STORAGE = arrayOf<String>(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)

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
                mPosition = position
            }

        })
        pictureViewPager.adapter = pictureViewAdapter

        downloadImage.setOnClickListener{
            verifyStoragePermissions(this)
        }

        refreshData()

    }

    private fun refreshData() {

        if(pictureViewPager!==null && images.size>0 && mPosition>-1){
            val adapter = pictureViewPager.adapter as PictureViewAdapter
            adapter.mImages = images
            adapter.notifyDataSetChanged()
            pictureViewPager.currentItem = mPosition
            index.text = "${mPosition+1} / ${images.size}"
        }

    }

    override fun onResume() {
        super.onResume()
        images = intent.getParcelableArrayListExtra<ImageModel>(getString(R.string.pictureView_tag_images))
        mPosition = intent.getIntExtra(getString(R.string.pictureView_tag_index),-1)

        refreshData()
    }

    fun downloadImage(){
        Toast.makeText(this,"开始下载",Toast.LENGTH_SHORT).show()
        val request = NetUtil.getRetrofit()?.create(GetRequest_Interface::class.java)
        val url = images[mPosition].url
        request?.downloadImage(url)?.enqueue(object:Callback<ResponseBody>{
            override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {

            }

            override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                NetUtil.writeResponseToDisk(url.substring(url.lastIndexOf("/")+1), response?.body())
            }

        })
    }

    fun verifyStoragePermissions(activity: Activity) {
        // Check if we have write permission
        val permission = ActivityCompat.checkSelfPermission(activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE)
        }else{
            downloadImage()
        }
    }

}