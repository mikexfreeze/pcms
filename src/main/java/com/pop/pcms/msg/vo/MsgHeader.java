package com.pop.pcms.msg.vo;

import java.io.Serializable;

/**
 * Created by zhangjinye on 2017/3/8.
 */
public class MsgHeader<T> implements Serializable{

  private static final long serialVersionUID = 1L;

  //from
  private String from;
  //to
  private String to;
  //消息ID
  private Long msgId;
  //发送时间
  private Long sendtime;
  //消息类型  0 文本类型 1 图片
  private Integer type;
  private T body;

  public String getFrom() {
    return from;
  }

  public void setFrom(String from) {
    this.from = from;
  }

  public String getTo() {
    return to;
  }

  public void setTo(String to) {
    this.to = to;
  }

  public Long getMsgId() {
    return msgId;
  }

  public void setMsgId(Long msgId) {
    this.msgId = msgId;
  }

  public Long getSendtime() {
    return sendtime;
  }

  public void setSendtime(Long sendtime) {
    this.sendtime = sendtime;
  }

  public Integer getType() {
    return type;
  }

  public void setType(Integer type) {
    this.type = type;
  }

  public T getBody() {
    return body;
  }

  public void setBody(T body) {
    this.body = body;
  }
}
