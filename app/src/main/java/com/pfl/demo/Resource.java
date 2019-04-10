package com.pfl.demo;

public class Resource<T> {
    public static final int ERROR = 0;
    public static final int LOADING = 1;
    public static final int REFRESH = 2;
    public static final int LOADMORE = 3;

    private int status;
    private T data;
    private String message;

    public Resource(int status, T data, String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
