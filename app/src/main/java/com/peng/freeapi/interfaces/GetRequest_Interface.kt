package com.peng.freeapi.interfaces

import com.peng.freeapi.model.Name
import com.peng.freeapi.model.DataResponse
import com.peng.freeapi.model.ImageModel
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface GetRequest_Interface {

    @GET("femaleNameApi")
    fun getNameList(@Query("page") page:Int) : Call<DataResponse<ArrayList<Name>>>

    @GET("meituApi")
    fun getImageList(@Query("page") page:Int) : Call<DataResponse<ArrayList<ImageModel>>>

    @GET
    fun downloadImage(@Url url:String) : Call<ResponseBody>

}