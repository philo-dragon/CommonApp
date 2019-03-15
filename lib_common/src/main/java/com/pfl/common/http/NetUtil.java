package com.pfl.common.http;

public class NetUtil {
    public static <T,M> void request(T requestData,String url,Class<M> clazz , IHttpReponseListener listener){

        IHttpRequest httpRequest = new JsonHttpRequest();
        CallBackListener callBackListener = new JsonCallbackListener<>(clazz,listener);
        HttpTask task = new HttpTask(url,requestData,httpRequest,callBackListener);
        ThreadPoolManager.getInstance().addTask(task);

    }
}
