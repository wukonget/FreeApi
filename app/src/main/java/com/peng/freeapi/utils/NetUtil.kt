package com.peng.freeapi.utils

import android.content.Intent
import android.net.Uri
import android.os.Environment
import com.peng.freeapi.MyApplication
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.BufferedInputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

object NetUtil {

    var mRetrofit : Retrofit? = null
    var mWanRetrofit : Retrofit? = null

    fun getRetrofit() : Retrofit?{
        if(mRetrofit === null){
            mRetrofit = Retrofit.Builder()
//                    .baseUrl("https://www.apiopen.top/")
                    .baseUrl("http://gank.io/")
                    .addConverterFactory(GsonConverterFactory.create()).build()
        }
        return mRetrofit
    }

    fun getWanRetrofit() : Retrofit?{
        if(mWanRetrofit === null){
            mWanRetrofit = Retrofit.Builder()
                    .baseUrl("http://www.wanandroid.com/")
                    .addConverterFactory(GsonConverterFactory.create()).build()
        }
        return mWanRetrofit
    }

    /**
     * 保存图片到本地
     */
    fun writeResponseToDisk(imageName:String, responseBody: ResponseBody?){
        if(responseBody === null){
            CommonUtil.showToast("图片地址错误")
        }

        try {
            var path = Environment.getExternalStorageDirectory().absolutePath+"/freeApi"
            var inputStream = responseBody?.byteStream()
            var fileDir = File(path)
            if (!fileDir.exists()) {
                fileDir.mkdir()
            }
            var file = File(path, imageName)
            if (file.exists()) {
                file.delete()
                file = File(path, imageName)
            }
            var fos = FileOutputStream(file)
            var bis = BufferedInputStream(inputStream)
            var buffer = ByteArray(1024)
            var length = -1
            while (bis.read(buffer).also { length = it } !== -1) {
                fos.write(buffer, 0, length)
            }
            fos.flush()
            fos.close()
            bis.close()
            inputStream?.close()
            MyApplication.instance().sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file)))
            CommonUtil.showToast("图片保存成功，路径为${file.absolutePath}")
        }catch (e:IOException){
            e.printStackTrace()
            CommonUtil.showToast("读写错误")
        }

    }
}