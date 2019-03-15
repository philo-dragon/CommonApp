package com.pfl.common.netstate;

import android.content.IntentFilter;
import com.pfl.common.constant.Constants;
import com.pfl.common.utils.Util;

/**
 * 网络管理
 */
public class NetworkManager {

    private static NetworkManager instance;

    private NetworkStateReceiver receiver;

    private NetworkManager() {
        if (null != instance) {
            throw new RuntimeException(this.getClass().getSimpleName() + " already existed");
        }

        receiver = new NetworkStateReceiver();
    }

    public static NetworkManager getInstance() {

        if (null == instance) {
            synchronized (NetworkManager.class) {
                if (null == instance) {
                    instance = new NetworkManager();
                }
            }
        }

        return instance;
    }

    public void init() {

        //广播注册
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constants.ANDROID_NET_ACTION);
        Util.getInstance().getApplication().registerReceiver(receiver, filter);

        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            NetworkCallbackImpl networkCallback = new NetworkCallbackImpl();
            NetworkRequest.Builder builder = new NetworkRequest.Builder();
            NetworkRequest request = builder.build();
            ConnectivityManager connmagr = (ConnectivityManager) Util.getInstance()
                    .getApplication().getSystemService(Context.CONNECTIVITY_SERVICE);
            if (null != connmagr) {
                connmagr.registerNetworkCallback(request, networkCallback);
            }
        }*/

    }

    public void registerObserver(Object object) {
        receiver.registerObserver(object);
    }

    public void unRegisterObserver(Object object) {
        receiver.unRegisterObserver(object);
    }

    public void unRegisterAllObserver() {
        receiver.unRegisterAllObserver();
    }

}
