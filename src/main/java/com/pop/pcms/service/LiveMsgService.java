package com.pop.pcms.service;

import com.pop.pcms.service.dto.LiveMsgDTO;

/**
 * 消息聊天
 * Service Implementation for managing PopAward.
 */
public interface LiveMsgService {


    /**
     * Save a popAward.
     *
     * @param liveMsgDTO the entity to save
     * @return the persisted entity
     */
    public LiveMsgDTO save(LiveMsgDTO liveMsgDTO);

}
