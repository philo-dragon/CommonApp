package com.pfl.common.http;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class JsonHttpRequest implements IHttpRequest {

    private String mUrl;
    private byte[] data;
    private CallBackListener callBackListener;

    @Override
    public void setUrl(String url) {
        this.mUrl = url;
    }

    @Override
    public void setData(byte[] data) {
        this.data = data;
    }

    @Override
    public void setListener(CallBackListener callBackListener) {
        this.callBackListener = callBackListener;
    }

    @Override
    public void execute() {
        request();
    }

    private void request() {
        HttpURLConnection urlConn = null;
        OutputStream out = null;
        BufferedOutputStream bos = null;
        try {
            // 新建一个URL对象
            URL url = new URL(mUrl);
            // 打开一个HttpURLConnection连接
            urlConn = (HttpURLConnection) url.openConnection();
            // 设置连接主机超时时间
            urlConn.setConnectTimeout(5 * 1000);
            //设置从主机读取数据超时
            urlConn.setReadTimeout(5 * 1000);
            // 设置是否使用缓存  默认是true
            urlConn.setUseCaches(false);
            urlConn.setDoInput(true);
            urlConn.setDoOutput(true);
            // 设置为Post请求
            urlConn.setRequestMethod("GET");
            //urlConn设置请求头信息
            //设置请求中的媒体类型信息。
            urlConn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            //设置客户端与服务连接类型
            //urlConn.addRequestProperty("Connection", "Keep-Alive");
            // 开始连接
            urlConn.connect();

            out = urlConn.getOutputStream();
            bos = new BufferedOutputStream(out);
            bos.write(data);
            bos.flush();

            // 判断请求是否成功
            if (urlConn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                callBackListener.onSuccess(urlConn.getInputStream());
            } else {
                callBackListener.onFailure();
            }
        } catch (Exception e) {
            e.printStackTrace();
            callBackListener.onFailure();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            // 关闭连接
            urlConn.disconnect();
        }
    }

}
