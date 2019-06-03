package com.pfl.common.aop;

import android.view.View;

public class AopTest {

    private void initView(View view) {
        view.setOnClickListener(new View.OnClickListener() {
            // 如果需要自定义点击时间间隔，自行传入毫秒值即可
            // @SingleClick(2000)
            @SingleClick
            @Override
            public void onClick(View v) {
                // do something
            }
        });
    }
}
