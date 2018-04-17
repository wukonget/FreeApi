package com.peng.freeapi.model

data class WanResponse<T>(
    val errorCode : Int,
    val errorMsg : String,
    val data : T
)