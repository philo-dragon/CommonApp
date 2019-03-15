package com.pfl.common.http;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import static com.pfl.common.constant.Constants.TAG;

public class JsonCallbackListener<T> implements CallBackListener {

    private Class<T> reponseClass;
    private IHttpReponseListener httpReponseListener;
    private Handler mHandler = new Handler(Looper.getMainLooper());

    public JsonCallbackListener(Class<T> clazz, IHttpReponseListener httpReponseListener) {
        this.reponseClass = clazz;
        this.httpReponseListener = httpReponseListener;
    }

    @Override
    public void onSuccess(InputStream in) {
        String content = streamToString(in);
        final T reponse = new Gson().fromJson(content, reponseClass);
        mHandler.post(new Runnable() {//把接口切换到主线程
            @Override
            public void run() {
                httpReponseListener.onSuccess(reponse);
            }
        });

    }

    @Override
    public void onFailure() {

    }

    /**
     * 将输入流转换成字符串
     *
     * @param is 从网络获取的输入流
     * @return
     */
    public String streamToString(InputStream is) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = is.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }
            baos.close();
            is.close();
            byte[] byteArray = baos.toByteArray();
            return new String(byteArray);
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            return null;
        }
    }
}
