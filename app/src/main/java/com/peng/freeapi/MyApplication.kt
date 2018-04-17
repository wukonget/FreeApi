package com.peng.freeapi

import android.app.Application
import com.vondear.rxtools.RxTool

class MyApplication : Application() {

    companion object {
        private var instance : Application? = null
        fun instance() = instance!!
    }


    override fun onCreate() {
        super.onCreate()
        instance = this
        RxTool.init(this)
    }

}
