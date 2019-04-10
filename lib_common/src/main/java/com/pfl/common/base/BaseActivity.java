package com.pfl.common.base;

import androidx.appcompat.app.AppCompatActivity;
import com.jojo.design.common_ui.dialog.LoadingDialog;

public class BaseActivity extends AppCompatActivity {

    private LoadingDialog mDialog;

    public void showDialog() {
        if (mDialog == null || !mDialog.isShowing()) {
            mDialog = new LoadingDialog(this);
            mDialog.show();
        }
    }

    public void dismissDialog() {
        if (null != mDialog && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }
}
