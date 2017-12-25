package com.pop.pcms.service;

import com.pop.pcms.domain.PopAward;
import com.pop.pcms.repository.PopAwardRepository;
import com.pop.pcms.service.dto.PopAwardDTO;
import com.pop.pcms.service.mapper.PopAwardMapper;
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
 * Service Implementation for managing PopAward.
 */
@Service
@Transactional
public class PopAwardService {

    private final Logger log = LoggerFactory.getLogger(PopAwardService.class);
    
    private final PopAwardRepository popAwardRepository;

    private final PopAwardMapper popAwardMapper;

    public PopAwardService(PopAwardRepository popAwardRepository, PopAwardMapper popAwardMapper) {
        this.popAwardRepository = popAwardRepository;
        this.popAwardMapper = popAwardMapper;
    }

    /**
     * Save a popAward.
     *
     * @param popAwardDTO the entity to save
     * @return the persisted entity
     */
    public PopAwardDTO save(PopAwardDTO popAwardDTO) {
        log.debug("Request to save PopAward : {}", popAwardDTO);
        PopAward popAward = popAwardMapper.popAwardDTOToPopAward(popAwardDTO);
        popAward = popAwardRepository.save(popAward);
        PopAwardDTO result = popAwardMapper.popAwardToPopAwardDTO(popAward);
        return result;
    }

    /**
     *  Get all the popAwards.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<PopAwardDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PopAwards");
        Page<PopAward> result = popAwardRepository.findAll(pageable);
        return result.map(popAward -> popAwardMapper.popAwardToPopAwardDTO(popAward));
    }

    /**
     *  Get one popAward by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public PopAwardDTO findOne(Long id) {
        log.debug("Request to get PopAward : {}", id);
        PopAward popAward = popAwardRepository.findOne(id);
        PopAwardDTO popAwardDTO = popAwardMapper.popAwardToPopAwardDTO(popAward);
        return popAwardDTO;
    }

    /**
     *  Delete the  popAward by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PopAward : {}", id);
        popAwardRepository.delete(id);
    }
}
