package com.pfl.common.http;

public interface IHttpReponseListener<T> {

    void onSuccess(T result);

}
