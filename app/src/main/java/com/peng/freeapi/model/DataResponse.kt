package com.peng.freeapi.model

data class DataResponse<T>(
    val code : Int,
    val message : String,
    val data : T
)