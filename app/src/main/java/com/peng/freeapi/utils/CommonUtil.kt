package com.peng.freeapi.utils

import android.content.Context
import android.util.DisplayMetrics
import android.view.WindowManager
import android.widget.Toast
import com.peng.freeapi.MyApplication


object CommonUtil {

    private var toast : Toast?= null

    fun getScreenWidth(context: Context): Int {
        val wm = context
                .getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val outMetrics = DisplayMetrics()
        wm.defaultDisplay.getMetrics(outMetrics)
        return outMetrics.widthPixels
    }

    fun showToast(message:String){
        if(toast === null) {
            toast = Toast.makeText(MyApplication.instance(), message, Toast.LENGTH_SHORT)
        }else{
            toast!!.setText(message)
        }
        toast!!.show()
    }
}