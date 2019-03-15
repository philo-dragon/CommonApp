package com.pfl.common.http;

import java.io.InputStream;

public interface CallBackListener {

    void onSuccess(InputStream in);

    void onFailure();
}
