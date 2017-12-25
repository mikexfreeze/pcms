package com.pop.pcms.service;

import com.pop.pcms.domain.PopObserver;
import com.pop.pcms.repository.PopObserverRepository;
import com.pop.pcms.service.dto.PopObserverDTO;
import com.pop.pcms.service.mapper.PopObserverMapper;
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
 * Service Implementation for managing PopObserver.
 */
@Service
@Transactional
public class PopObserverService {

    private final Logger log = LoggerFactory.getLogger(PopObserverService.class);
    
    private final PopObserverRepository popObserverRepository;

    private final PopObserverMapper popObserverMapper;

    public PopObserverService(PopObserverRepository popObserverRepository, PopObserverMapper popObserverMapper) {
        this.popObserverRepository = popObserverRepository;
        this.popObserverMapper = popObserverMapper;
    }

    /**
     * Save a popObserver.
     *
     * @param popObserverDTO the entity to save
     * @return the persisted entity
     */
    public PopObserverDTO save(PopObserverDTO popObserverDTO) {
        log.debug("Request to save PopObserver : {}", popObserverDTO);
        PopObserver popObserver = popObserverMapper.popObserverDTOToPopObserver(popObserverDTO);
        popObserver = popObserverRepository.save(popObserver);
        PopObserverDTO result = popObserverMapper.popObserverToPopObserverDTO(popObserver);
        return result;
    }

    /**
     *  Get all the popObservers.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<PopObserverDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PopObservers");
        Page<PopObserver> result = popObserverRepository.findAll(pageable);
        return result.map(popObserver -> popObserverMapper.popObserverToPopObserverDTO(popObserver));
    }

    /**
     *  Get one popObserver by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public PopObserverDTO findOne(Long id) {
        log.debug("Request to get PopObserver : {}", id);
        PopObserver popObserver = popObserverRepository.findOne(id);
        PopObserverDTO popObserverDTO = popObserverMapper.popObserverToPopObserverDTO(popObserver);
        return popObserverDTO;
    }

    /**
     *  Delete the  popObserver by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PopObserver : {}", id);
        popObserverRepository.delete(id);
    }
}
