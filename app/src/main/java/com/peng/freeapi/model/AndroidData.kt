package com.peng.freeapi.model


/**
 *use:       Android页面数据
 *author:    pengchong
 *time:      2018/4/16  14:32
 */

data class AndroidData(
        val _id: String,
        val createdAt: String,
        val desc: String,
        val publishedAt: String,
        val source: String,
        val type: String,
        val url: String,
        val used: Boolean,
        val who: String,
        val images: MutableList<String>
)