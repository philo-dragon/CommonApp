package com.pfl.update;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.*;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import com.pfl.app.R;

public class UpdateDialogFragment extends DialogFragment {

    public static UpdateDialogFragment newInstance() {

        Bundle args = new Bundle();

        UpdateDialogFragment fragment = new UpdateDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.UpdateAppDialog);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_update_dialog, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        //点击window外的区域 是否消失
        getDialog().setCanceledOnTouchOutside(false);
        getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    //禁用
                    /*if (mUpdateApp != null && mUpdateApp.isConstraint()) {
                        //返回桌面
                        startActivity(new Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_HOME));
                        return true;
                    } else {
                        return false;
                    }*/
                }
                return false;
            }
        });

        Window dialogWindow = getDialog().getWindow();
        dialogWindow.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        lp.height = (int) (displayMetrics.heightPixels * 0.8f);
        dialogWindow.setAttributes(lp);
    }
}
