package com.pfl.mvp;

import io.reactivex.Flowable;

public class MainModel  implements MainContract.Model {
    @Override
    public Flowable<BaseObjectBean<LoginBean>> login(String username, String password) {
        return RetrofitClient.getInstance().getApi().login(username,password);
    }
}