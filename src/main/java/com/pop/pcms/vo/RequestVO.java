package com.pop.pcms.vo;

import java.io.Serializable;

/**
 * 请求参数对象
 * Created by zhangjinye on 2017/3/1.
 */
public class RequestVO<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    //应用系统标识
    private String appId;
    //渠道ID
    private String channelId;
    //请求内容
    private T data;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
