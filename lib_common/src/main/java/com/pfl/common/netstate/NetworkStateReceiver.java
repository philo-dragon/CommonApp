package com.pfl.common.netstate;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.pfl.common.constant.Constants;
import com.pfl.common.utils.NetworkUtils;
import com.pfl.common.utils.Util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.*;

/**
 * 网络状态改变监听
 */
public class NetworkStateReceiver extends BroadcastReceiver {

    private Map<Object, List<MethodBean>> networkList;//保存Object对象中相关方法
    private NetType netType;

    public NetworkStateReceiver() {
        netType = NetType.AUTO;
        networkList = new HashMap<>();
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent == null || intent.getAction() == null) {
            Log.e(Constants.TAG, "intent or intent.getAction() is null");
            return;
        }

        if (intent.getAction().equals(Constants.ANDROID_NET_ACTION)) {
            netType = NetworkUtils.getNetType();
            post(netType);
        }
    }

    private void post(NetType netType) {
        Set<Object> set = networkList.keySet();
        for (Object object : set) {
            List<MethodBean> methodBeans = networkList.get(object);
            if (null != methodBeans) {
                for (MethodBean method : methodBeans) {

                    if (method.getType().isAssignableFrom(netType.getClass())) {
                        switch (method.getNetType()) {
                            case AUTO:
                                invoke(method, object, netType);
                                break;
                            case WIFI:
                                if (netType == NetType.WIFI || netType == NetType.NONE) {
                                    invoke(method, object, netType);
                                }
                                break;
                            case CMWAP:
                                if (netType == NetType.CMWAP || netType == NetType.NONE) {
                                    invoke(method, object, netType);
                                }
                                break;
                            case CMNET:
                                if (netType == NetType.CMNET || netType == NetType.NONE) {
                                    invoke(method, object, netType);
                                }
                                break;
                            case NONE:
                                break;
                            default:
                                break;
                        }
                    }

                }
            }
        }
    }

    private void invoke(MethodBean method, Object object, NetType netType) {
        Method mc = method.getMethod();
        try {
            mc.invoke(object, netType);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public void registerObserver(Object object) {

        List<MethodBean> methodist = networkList.get(object);
        if (null == methodist) {
            methodist = findAnnotation(object);
            networkList.put(object, methodist);
        }
    }

    private List<MethodBean> findAnnotation(Object regist) {//通过反射获取信息
        List<MethodBean> methodist = new ArrayList<>();
        Class<?> aClass = regist.getClass();
        Method[] methods = aClass.getMethods();//获取class对象所有方法
        for (Method method : methods) {
            NetWork annotation = method.getAnnotation(NetWork.class);//获取NetWork类型的注解
            if (null == annotation) {
                continue;
            }

            Type returnType = method.getGenericReturnType();//获取方法返回值类型
            if (!"void".equals(returnType.toString())) {//返回值不是void 类型
                throw new RuntimeException(method.getName() + "Method return must be void");
            }

            Class<?>[] parameterTypes = method.getParameterTypes();//获取方法所有参数
            if (1 != parameterTypes.length) {//有且只能有一个参数
                throw new RuntimeException(method.getName() + "Method can only have one parameter");
            }

            MethodBean methodBean = new MethodBean(parameterTypes[0], annotation.netType(), method);
            methodist.add(methodBean);

        }
        return methodist;
    }

    public void unRegisterObserver(Object object) {
        if (!networkList.isEmpty() && networkList.containsKey(object)) {
            networkList.remove(object);
        }
    }

    public void unRegisterAllObserver() {
        networkList.clear();
        Util.getInstance().getApplication().unregisterReceiver(this);
        networkList = null;
    }
}
