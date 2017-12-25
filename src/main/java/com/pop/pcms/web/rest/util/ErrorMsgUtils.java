package com.pop.pcms.web.rest.util;


import com.pop.pcms.vo.ResultDto;

/**
 * 请求返回错误封装工具类
 * Created by ZHAN565 on 2017/2/27.
 */
public class ErrorMsgUtils {

//  /**
//   * 返回错误信息
//   *
//   * @param msg
//   * @param code
//   * @return
//   */
//  public static String errorMsg(String msg, String code) {
//    ResultDto<String> dto = new ResultDto<String>();
//    dto.setMsg(msg);
//    dto.setCode(code);
//    //String errorMsg = JsonUtils.obj2str(dto);
//    return "";
//  }

    /**
     * 返回成功信息
     *
     * @param
     * @param code 当前输入的成功信息对象
     * @return
     */
    public static <T> ResultDto successMsg(T data, String code, String msg) {
        ResultDto<T> dto = new ResultDto<T>();
        dto.setMsg(msg);//msg
        dto.setCode(code);//code
        dto.setData(data);//数据节点data
        dto.setTimestamp(System.currentTimeMillis());
        return dto;
    }
}
