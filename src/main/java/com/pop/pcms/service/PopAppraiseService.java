package com.pop.pcms.service;

import com.pop.pcms.domain.PopAppraise;
import com.pop.pcms.repository.PopAppraiseRepository;
import com.pop.pcms.service.dto.PopAppraiseDTO;
import com.pop.pcms.service.mapper.PopAppraiseMapper;
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
 * Service Implementation for managing PopAppraise.
 */
@Service
@Transactional
public class PopAppraiseService {

    private final Logger log = LoggerFactory.getLogger(PopAppraiseService.class);

    private final PopAppraiseRepository popAppraiseRepository;

    private final PopAppraiseMapper popAppraiseMapper;

    public PopAppraiseService(PopAppraiseRepository popAppraiseRepository, PopAppraiseMapper popAppraiseMapper) {
        this.popAppraiseRepository = popAppraiseRepository;
        this.popAppraiseMapper = popAppraiseMapper;
    }

    /**
     * 生成评选轮次
     * Save a popAppraise.
     *
     * @param popAppraiseDTO the entity to save
     * @return the persisted entity
     */
    public PopAppraiseDTO save(PopAppraiseDTO popAppraiseDTO) {
        PopAppraise popAppraise = popAppraiseMapper.popAppraiseDTOToPopAppraise(popAppraiseDTO);
        popAppraise = popAppraiseRepository.save(popAppraise);
        PopAppraiseDTO result = popAppraiseMapper.popAppraiseToPopAppraiseDTO(popAppraise);
        return result;
    }

    /**
     *  Get all the popAppraises.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<PopAppraiseDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PopAppraises");
        Page<PopAppraise> result = popAppraiseRepository.findAll(pageable);
        return result.map(popAppraise -> popAppraiseMapper.popAppraiseToPopAppraiseDTO(popAppraise));
    }

    /**
     *  Get one popAppraise by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public PopAppraiseDTO findOne(Long id) {
        log.debug("Request to get PopAppraise : {}", id);
        PopAppraise popAppraise = popAppraiseRepository.findOne(id);
        PopAppraiseDTO popAppraiseDTO = popAppraiseMapper.popAppraiseToPopAppraiseDTO(popAppraise);
        return popAppraiseDTO;
    }

    /**
     *  Delete the  popAppraise by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PopAppraise : {}", id);
        popAppraiseRepository.delete(id);
    }
}
