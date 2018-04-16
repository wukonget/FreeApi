package com.peng.freeapi.activitys

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.peng.freeapi.R
import com.peng.freeapi.model.ImageModel
import java.util.*


/**
 *use:      段子模块
 *author:    pengchong
 *time:      2018/4/13  16:19
 */

class JokeActivity : BaseActivity() {

    companion object Factory {
        fun launch(activity: Activity, mImageList: ArrayList<ImageModel>, position: Int) {
            val intent = Intent(activity,JokeActivity::class.java)
            activity.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_joke)
    }

}