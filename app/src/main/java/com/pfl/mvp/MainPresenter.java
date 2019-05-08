package com.pfl.mvp;

import io.reactivex.functions.Consumer;

public class MainPresenter extends BasePresenter<MainContract.View> implements MainContract.Presenter {

    private MainContract.Model model;

    public MainPresenter() {
        model = new MainModel();
    }

    @Override
    public void login(String username, String password) {
        //View是否绑定 如果没有绑定，就不执行网络请求
        if (!isViewAttached()) {
            return;
        }
        getView().showLoading();
        model.login(username, password)
                .compose(RxScheduler.Flo_io_main())
                .as(getView().<BaseObjectBean<LoginBean>>bindAutoDispose())
                .subscribe(bean -> {
                    getView().onSuccess(bean);
                    getView().hideLoading();
                }, throwable -> {
                    getView().onError(throwable);
                    getView().hideLoading();
                });
    }
}