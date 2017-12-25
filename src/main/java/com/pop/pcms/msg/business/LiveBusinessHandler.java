package com.pop.pcms.msg.business;


import com.pop.pcms.service.LiveMsgService;
import com.pop.pcms.service.dto.LiveMsgDTO;
import com.pop.pcms.vo.ResultDto;
import com.pop.pcms.web.rest.util.BaseValidatorUtil;
import com.pop.pcms.web.rest.util.JsonUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/**
 * {
 * "appId": "string",
 * "channelId": "string",
 * "data": {
 * "msgId": 123456,
 * "from": 123456,
 * "msg": "最近的聊天信息啊",
 * "send_time": "2017-12-12",
 * "create_time": "2017-12-12",
 * "method": "send_msg",
 * "type": "TEXT",
 * "competition_id":"123"
 * }
 * }
 */

/**
 * 业务实现类
 * Created by zhangjinye on 2017/3/9.
 */
@Component
public class LiveBusinessHandler implements BusinessHandler {

    private static final Logger log = LoggerFactory.getLogger(LiveBusinessHandler.class);

    public List<String> methodList = new ArrayList<String>(Arrays.asList("sendMsg", "listMsg"));

    @Autowired
    private LiveMsgService liveMsgService;


    @Override
    public String doBusinessHandler(String msg) {
        try {
            if (StringUtils.isNotEmpty(msg)) {
                LiveMsgDTO dto = JsonUtils.str2obj(msg, LiveMsgDTO.class);
                if (dto == null) {
                    return null;
                }
                //LiveMsgDTO dto = requestVO.getData();
                String method = dto.getMethod();
                //判断是否支持业务方法
                if (!methodList.contains(method)) {
                    log.info("当前消息:{}不支持该方法:{}.", dto.getMsgId(), method);
                    return null;
                }
                String validMsg = BaseValidatorUtil.valConstraint(dto);
                if (StringUtils.isNotEmpty(validMsg)) {
                    log.info("当前消息:{}校验失败:{}.", dto.getMsgId(), validMsg);
                    return null;
                }
                //调用Application实现业务增加
                liveMsgService.save(dto);
                ResultDto<String> resultDto=new ResultDto<String>();
                resultDto.setMsg("成功!");
                resultDto.setCode(SEND_SUCCESS);
                resultDto.setData(dto.getMsg());
                return JsonUtils.obj2str(resultDto);
            }

        } catch (Exception e) {
            log.error("发送系统异常:{}", e);
            ResultDto<String> resultDto=new ResultDto<String>();
            resultDto.setMsg("失败!");
            resultDto.setCode(SEND_SUCCESS);
            return JsonUtils.obj2str(resultDto);
        }
        return null;
    }
}
