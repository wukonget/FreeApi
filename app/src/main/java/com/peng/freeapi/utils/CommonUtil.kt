package com.peng.freeapi.utils

import android.app.AlertDialog
import android.app.Dialog
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

    fun findMax(lastPositions: IntArray): Int {
        var max = lastPositions[0]
        for (value in lastPositions) {
            if (value > max) {
                max = value
            }
        }
        return max
    }

    fun showLoading(context:Context) : Dialog {

       val loadingDialog = AlertDialog.Builder(context)
                .setMessage("加载中")
                .create()
        loadingDialog.show()
        return loadingDialog

    }

    fun dismissLoading(loadingDialog:Dialog){
        if(loadingDialog!==null && loadingDialog.isShowing){
            loadingDialog.dismiss()
        }
    }
}