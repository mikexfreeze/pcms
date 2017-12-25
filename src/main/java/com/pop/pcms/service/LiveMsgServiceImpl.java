package com.pop.pcms.service;

import com.pop.pcms.domain.LiveMsg;
import com.pop.pcms.repository.LiveMsgRepository;
import com.pop.pcms.service.dto.LiveMsgDTO;
import com.pop.pcms.service.mapper.LiveMsgMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 直播消息保存
 * Created by zhangjinye on 2017/3/10.
 */
@Service
@Transactional
public class LiveMsgServiceImpl implements LiveMsgService {

    private final Logger log = LoggerFactory.getLogger(LiveMsgServiceImpl.class);

    @Autowired
    private LiveMsgRepository liveMsgRepository;

    @Autowired
    private LiveMsgMapper liveMsgMapper;


    /**
     * Save a popAward.
     *
     * @param liveMsgDTO the entity to save
     * @return the persisted entity
     */
    public LiveMsgDTO save(LiveMsgDTO liveMsgDTO) {
        LiveMsg popAward = liveMsgMapper.LiveMsgDTOToLiveMsg(liveMsgDTO);
        popAward = liveMsgRepository.save(popAward);
        LiveMsgDTO result = liveMsgMapper.LiveMsgToLiveMsgDTO(popAward);
        return result;
    }

}
