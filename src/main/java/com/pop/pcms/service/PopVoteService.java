package com.pop.pcms.service;

import com.pop.pcms.domain.PopVote;
import com.pop.pcms.repository.PopVoteRepository;
import com.pop.pcms.service.dto.PopVoteDTO;
import com.pop.pcms.service.mapper.PopVoteMapper;
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
 * Service Implementation for managing PopVote.
 */
@Service
@Transactional
public class PopVoteService {

    private final Logger log = LoggerFactory.getLogger(PopVoteService.class);
    
    private final PopVoteRepository popVoteRepository;

    private final PopVoteMapper popVoteMapper;

    public PopVoteService(PopVoteRepository popVoteRepository, PopVoteMapper popVoteMapper) {
        this.popVoteRepository = popVoteRepository;
        this.popVoteMapper = popVoteMapper;
    }

    /**
     * Save a popVote.
     *
     * @param popVoteDTO the entity to save
     * @return the persisted entity
     */
    public PopVoteDTO save(PopVoteDTO popVoteDTO) {
        log.debug("Request to save PopVote : {}", popVoteDTO);
        PopVote popVote = popVoteMapper.popVoteDTOToPopVote(popVoteDTO);
        popVote = popVoteRepository.save(popVote);
        PopVoteDTO result = popVoteMapper.popVoteToPopVoteDTO(popVote);
        return result;
    }

    /**
     *  Get all the popVotes.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<PopVoteDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PopVotes");
        Page<PopVote> result = popVoteRepository.findAll(pageable);
        return result.map(popVote -> popVoteMapper.popVoteToPopVoteDTO(popVote));
    }

    /**
     *  Get one popVote by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public PopVoteDTO findOne(Long id) {
        log.debug("Request to get PopVote : {}", id);
        PopVote popVote = popVoteRepository.findOne(id);
        PopVoteDTO popVoteDTO = popVoteMapper.popVoteToPopVoteDTO(popVote);
        return popVoteDTO;
    }

    /**
     *  Delete the  popVote by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PopVote : {}", id);
        popVoteRepository.delete(id);
    }
}
