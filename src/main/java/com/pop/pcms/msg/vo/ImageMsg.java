package com.pop.pcms.msg.vo;

import java.io.Serializable;

/**
 * 文本消息
 * Created by zhangjinye on 2017/3/8.
 */
public class ImageMsg implements Serializable {
  private static final long serialVersionUID = 1L;

  //图片name
  private String name;
  //md5
  private String md5;
  //生成URL
  private String url;
  //宽
  private Long w;
  //高
  private Long h;
  //图片大小
  private Long size;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getMd5() {
    return md5;
  }

  public void setMd5(String md5) {
    this.md5 = md5;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public Long getW() {
    return w;
  }

  public void setW(Long w) {
    this.w = w;
  }

  public Long getH() {
    return h;
  }

  public void setH(Long h) {
    this.h = h;
  }

  public Long getSize() {
    return size;
  }

  public void setSize(Long size) {
    this.size = size;
  }
}
