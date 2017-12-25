package com.pop.pcms.msg.vo;

import java.io.Serializable;

/**
 * 文本消息
 * Created by zhangjinye on 2017/3/8.
 */
public class TextMsg implements Serializable {
  private static final long serialVersionUID = 1L;

  //文本消息内容
  private String msg;

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }
}
