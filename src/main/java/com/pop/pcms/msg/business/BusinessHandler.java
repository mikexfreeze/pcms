package com.pop.pcms.msg.business;


/**
 * 业务抽象类
 * Created by zhangjinye on 2017/3/9.
 */
public   interface BusinessHandler {

    public final static String  SEND_SUCCESS="1000";

    public final static String  SEND_ERROR="1000";
  /**
   * 业务抽象类
   * @param msg
   * @return
   */
  public   String doBusinessHandler(String msg);
}
