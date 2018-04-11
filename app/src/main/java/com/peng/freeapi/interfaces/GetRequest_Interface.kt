package com.peng.freeapi.interfaces

import com.peng.freeapi.model.Name
import com.peng.freeapi.model.DataResponse
import com.peng.freeapi.model.ImageModel
import retrofit2.Call
import retrofit2.http.GET

interface GetRequest_Interface {

    @GET("femaleNameApi?page=1")
    fun getNameList() : Call<DataResponse<ArrayList<Name>>>

    @GET("meituApi?page=4")
    fun getImageList() : Call<DataResponse<ArrayList<ImageModel>>>

}