package com.peng.freeapi.model


/**
 *use:
 *author:    pengchong
 *time:      2018/4/17  13:58
 */

data class BannerModel(
        val desc:String,
        val id:Long,
        val imagePath:String,
        val isVisible: Int,
        val order: Int,
        val title: String,
        val type: Int,
        val url: String
)