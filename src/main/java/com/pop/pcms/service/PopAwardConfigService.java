package com.pop.pcms.service;

import com.pop.pcms.domain.PopAwardConfig;
import com.pop.pcms.domain.PopSubject;
import com.pop.pcms.repository.PopAwardConfigRepository;
import com.pop.pcms.service.dto.PopAwardConfigDTO;
import com.pop.pcms.service.dto.PopSubjectDTO;
import com.pop.pcms.service.mapper.PopAwardConfigMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing PopAwardConfig.
 */
@Service
@Transactional
public class PopAwardConfigService {

    private final Logger log = LoggerFactory.getLogger(PopAwardConfigService.class);

    private final PopAwardConfigRepository popAwardConfigRepository;

    private final PopAwardConfigMapper popAwardConfigMapper;

    public PopAwardConfigService(PopAwardConfigRepository popAwardConfigRepository, PopAwardConfigMapper popAwardConfigMapper) {
        this.popAwardConfigRepository = popAwardConfigRepository;
        this.popAwardConfigMapper = popAwardConfigMapper;
    }

    /**
     * Save a popAwardConfig.
     *
     * @param popAwardConfigDTO the entity to save
     * @return the persisted entity
     */
    public PopAwardConfigDTO save(PopAwardConfigDTO popAwardConfigDTO) {
        log.debug("Request to save PopAwardConfig : {}", popAwardConfigDTO);
        PopAwardConfig popAwardConfig = popAwardConfigMapper.popAwardConfigDTOToPopAwardConfig(popAwardConfigDTO);
        popAwardConfig = popAwardConfigRepository.save(popAwardConfig);
        PopAwardConfigDTO result = popAwardConfigMapper.popAwardConfigToPopAwardConfigDTO(popAwardConfig);
        return result;
    }

    /**
     *  Get all the popAwardConfigs.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<PopAwardConfigDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PopAwardConfigs");
        Page<PopAwardConfig> result = popAwardConfigRepository.findAll(pageable);
        return result.map(popAwardConfig -> popAwardConfigMapper.popAwardConfigToPopAwardConfigDTO(popAwardConfig));
    }

    /**
     *  Get one popAwardConfig by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public PopAwardConfigDTO findOne(Long id) {
        log.debug("Request to get PopAwardConfig : {}", id);
        PopAwardConfig popAwardConfig = popAwardConfigRepository.findOne(id);
        PopAwardConfigDTO popAwardConfigDTO = popAwardConfigMapper.popAwardConfigToPopAwardConfigDTO(popAwardConfig);
        return popAwardConfigDTO;
    }

    /**
     *  Delete the  popAwardConfig by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PopAwardConfig : {}", id);
        popAwardConfigRepository.delete(id);
    }

    /**
     * 根据主题ID找到当前目录下的所有奖项
     *
     * @param 当前主题ID
     * @return
     */
    @Transactional(readOnly = true)
    public List<PopAwardConfigDTO> findBySubjectId(Long subjectId) {
        List<PopAwardConfig> popSubject = popAwardConfigRepository.findBySubjectId(subjectId);
        List<PopAwardConfigDTO> result = popAwardConfigMapper.popAwardConfigsToPopAwardConfigDTOs(popSubject);
        return result;
    }
}
