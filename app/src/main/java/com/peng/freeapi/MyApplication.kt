package com.peng.freeapi

import android.app.Application

class MyApplication : Application() {

    companion object {
        private var instance : Application? = null
        fun instance() = instance!!
    }


    override fun onCreate() {
        super.onCreate()
        instance = this
    }

}
