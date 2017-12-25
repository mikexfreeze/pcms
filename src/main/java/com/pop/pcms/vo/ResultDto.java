package com.pop.pcms.vo;

import java.io.Serializable;

/**
 * WEB端请求返回数据
 * Created by zhangjinye on 2017/2/27.
 */
public class ResultDto<T> implements Serializable {

    protected static final long serialVersionUID = 1L;
    //入参消息
    private String msg;
    //数据内容
    private T data;
    //状态码
    private String code;
    /**
     * 时间戳
     */
    private long timestamp;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
