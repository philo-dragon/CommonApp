package com.pfl.common.base

import androidx.fragment.app.Fragment
import com.jojo.design.common_ui.dialog.LoadingDialog

open class BaseFragment : Fragment() {

    private var mDialog: LoadingDialog? = null

    fun showDialog() {
        if (mDialog == null || !mDialog!!.isShowing()) {
            mDialog = LoadingDialog(activity!!)
            mDialog!!.show()
        }
    }

    fun dismissDialog() {
        if (null != mDialog && mDialog!!.isShowing()) {
            mDialog!!.dismiss()
        }
    }
}
