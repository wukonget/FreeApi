package com.peng.freeapi.utils

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetUtil {

    var mRetrofit : Retrofit? = null

    fun getRetrofit() : Retrofit?{
        if(mRetrofit === null){
            mRetrofit = Retrofit.Builder()
                    .baseUrl("https://www.apiopen.top/")
                    .addConverterFactory(GsonConverterFactory.create()).build()
        }
        return mRetrofit
    }
}