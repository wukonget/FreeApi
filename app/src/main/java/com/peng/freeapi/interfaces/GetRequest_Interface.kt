package com.peng.freeapi.interfaces

import com.peng.freeapi.model.Name
import com.peng.freeapi.model.DataResponse
import com.peng.freeapi.model.ImageModel
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface GetRequest_Interface {

    @GET("femaleNameApi?page=1")
    fun getNameList() : Call<DataResponse<ArrayList<Name>>>

    @GET("meituApi?page=4")
    fun getImageList() : Call<DataResponse<ArrayList<ImageModel>>>

    @GET
    fun downloadImage(@Url url:String) : Call<ResponseBody>

}