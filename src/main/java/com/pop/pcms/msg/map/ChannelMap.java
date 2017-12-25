package com.pop.pcms.msg.map;

import io.netty.channel.Channel;
import io.netty.channel.ChannelId;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ChannelId和Channel对应的静态map
 * Created by zhangjinye on 2017/3/8.
 */
public class ChannelMap {

  private ChannelMap() {
  }

  private volatile static ChannelMap _instance = null;

  //返回单例ChannelMap
  public static ChannelMap getInstance() {
    if (_instance == null) {
      synchronized (ChannelMap.class) {
        if (_instance == null) {
          _instance = new ChannelMap();
        }
      }
    }
    return _instance;
  }

  /**
   * 保存所有WebSocket连接对应的map
   */
  private static Map<ChannelId, Channel> channelMap = new ConcurrentHashMap<ChannelId, Channel>();

  //设置ChannelId和Channel
  public static void setChannelMap(ChannelId channelId, Channel channel) {
    channelMap.putIfAbsent(channelId, channel);
  }

  //删除ChannelId和Channel
  public static void removeChannelMap(ChannelId channelId) {
    channelMap.remove(channelId);
  }

  //获取ChannelId和Channel
  public static Map<ChannelId, Channel> channelMap() {
    return channelMap;
  }
}
