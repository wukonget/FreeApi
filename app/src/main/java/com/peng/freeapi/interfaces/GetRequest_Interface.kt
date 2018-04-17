package com.peng.freeapi.interfaces

import com.peng.freeapi.model.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface GetRequest_Interface {

//    @GET("femaleNameApi")
//    fun getNameList(@Query("page") page:Int) : Call<DataResponse<ArrayList<Name>>>
//
//    @GET("meituApi")
//    fun getImageList(@Query("page") page:Int) : Call<DataResponse<ArrayList<ImageModel>>>

    @GET("api/data/福利/20/{page}")
    fun getNameList(@Path("page") page:Int) : Call<DataResponse<ArrayList<Name>>>

    @GET("api/data/福利/20/{page}")
    fun getImageList(@Path("page") page:Int) : Call<DataResponse<ArrayList<ImageModel>>>

    @GET("api/data/Android/20/{page}")
    fun getAndroidList(@Path("page") page:Int) : Call<DataResponse<MutableList<AndroidData>>>

    @GET
    fun downloadImage(@Url url:String) : Call<ResponseBody>


    @GET("banner/json")
    fun getBannerList():Call<WanResponse<MutableList<BannerModel>>>

}