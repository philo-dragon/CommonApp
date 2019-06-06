package com.pfl.common.utils

import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import com.pfl.common.R

/**
 *  desc : Toast工具类
 */
class ToastUtils {

    companion object {
        private var toast: Toast? = null
        /**
         * 显示toast
         *
         * @param context
         * @param text
         * @param isLongToast
         */
        @JvmStatic
        fun makeEventToast( text: String?,isLongToast: Boolean) {

            val v = LayoutInflater.from(Util.getInstance().application).inflate(R.layout.toast_view, null)
            val textView = v.findViewById<TextView>(R.id.text)
            textView.text = text

            //每次创建Toast时先做一下判断
            //如果前面已经有Toast在显示，则只是更新toast内容，而不再创建，提升用户体验
            if (toast != null) {
                textView.text = text
            } else {
                toast = Toast.makeText(Util.getInstance().application, text, if (isLongToast) Toast.LENGTH_LONG else Toast.LENGTH_SHORT)
            }

            toast!!.view = v
            toast!!.show()
        }

        /**
         * 显示toast
         *
         * @param text
         */
        @JvmStatic
        fun makeShortToast(text: String?) {
            if (null == text)
                return

            val v = LayoutInflater.from(Util.getInstance().application).inflate(R.layout.toast_view, null)
            val textView = v.findViewById<TextView>(R.id.text)
            textView.text = text

            //每次创建Toast时先做一下判断
            //如果前面已经有Toast在显示，则只是更新toast内容，而不再创建，提升用户体验
            if (toast != null) {
                textView.text = text
            } else {
                toast = Toast.makeText(Util.getInstance().application, text, Toast.LENGTH_SHORT)
            }
            toast!!.view = v
            toast!!.show()
        }
    }

}