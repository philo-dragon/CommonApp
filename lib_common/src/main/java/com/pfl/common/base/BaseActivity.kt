package com.pfl.common.base

import androidx.appcompat.app.AppCompatActivity
import com.jojo.design.common_ui.dialog.LoadingDialog

open class BaseActivity : AppCompatActivity() {

    private var mDialog: LoadingDialog? = null

    fun showDialog() {
        if (mDialog == null || !mDialog!!.isShowing()) {
            mDialog = LoadingDialog(this)
            mDialog!!.show()
        }
    }

    fun dismissDialog() {
        if (null != mDialog && mDialog!!.isShowing()) {
            mDialog!!.dismiss()
        }
    }
}
