package com.pfl.common.base;

import androidx.fragment.app.Fragment;
import com.jojo.design.common_ui.dialog.LoadingDialog;

public class BaseFragment extends Fragment {

    private LoadingDialog mDialog;

    public void showDialog() {
        if(mDialog == null || !mDialog.isShowing()) {
            mDialog = new LoadingDialog(getActivity());
            mDialog.show();
        }
    }

    public void dismissDialog() {
        if (null != mDialog && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }
}
