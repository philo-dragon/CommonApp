package com.pfl.mvp;

import java.lang.ref.WeakReference;

public class BasePresenter<V extends BaseView> {

    private WeakReference<V> weakReference;

    /**
     * 绑定view，一般在初始化中调用该方法
     *
     * @param view view
     */
    public void attachView(V view) {
        weakReference = new WeakReference<>(view);
    }

    /**
     * 解除绑定view，一般在onDestroy中调用
     */

    public void detachView() {
        if (isViewAttached()) {
            weakReference.clear();
            weakReference = null;
        }
    }

    /**
     * View是否绑定
     *
     * @return
     */
    public boolean isViewAttached() {
        return weakReference != null && weakReference.get() != null;
    }

    protected V getView() {
        return weakReference.get();
    }


}