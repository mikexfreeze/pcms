package com.pop.pcms.service;

import com.pop.pcms.domain.PopJudge;
import com.pop.pcms.domain.PopSubject;
import com.pop.pcms.repository.PopJudgeRepository;
import com.pop.pcms.service.dto.PopJudgeDTO;
import com.pop.pcms.service.dto.PopSubjectDTO;
import com.pop.pcms.service.mapper.PopJudgeMapper;
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
 * Service Implementation for managing PopJudge.
 */
@Service
@Transactional
public class PopJudgeService {

    private final Logger log = LoggerFactory.getLogger(PopJudgeService.class);

    private final PopJudgeRepository popJudgeRepository;

    private final PopJudgeMapper popJudgeMapper;

    public PopJudgeService(PopJudgeRepository popJudgeRepository, PopJudgeMapper popJudgeMapper) {
        this.popJudgeRepository = popJudgeRepository;
        this.popJudgeMapper = popJudgeMapper;
    }

    /**
     * Save a popJudge.
     *
     * @param popJudgeDTO the entity to save
     * @return the persisted entity
     */
    public PopJudgeDTO save(PopJudgeDTO popJudgeDTO) {
        log.debug("Request to save PopJudge : {}", popJudgeDTO);
        PopJudge popJudge = popJudgeMapper.popJudgeDTOToPopJudge(popJudgeDTO);
        popJudge = popJudgeRepository.save(popJudge);
        PopJudgeDTO result = popJudgeMapper.popJudgeToPopJudgeDTO(popJudge);
        return result;
    }

    /**
     *  Get all the popJudges.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<PopJudgeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PopJudges");
        Page<PopJudge> result = popJudgeRepository.findAll(pageable);
        return result.map(popJudge -> popJudgeMapper.popJudgeToPopJudgeDTO(popJudge));
    }

    /**
     *  Get one popJudge by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public PopJudgeDTO findOne(Long id) {
        log.debug("Request to get PopJudge : {}", id);
        PopJudge popJudge = popJudgeRepository.findOne(id);
        PopJudgeDTO popJudgeDTO = popJudgeMapper.popJudgeToPopJudgeDTO(popJudge);
        return popJudgeDTO;
    }

    /**
     *  Delete the  popJudge by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PopJudge : {}", id);
        popJudgeRepository.delete(id);
    }

    /**
     * 根据投稿找到当前目录下的所有奖项
     *
     * @param competitionId
     * @return
     */
    @Transactional(readOnly = true)
    public List<PopJudgeDTO> findByAppraiseId(Long competitionId) {
        log.debug("Request to get all PopSubjects");
        List<PopJudge> popSubject = popJudgeRepository.findByAppraiseId(competitionId);
        List<PopJudgeDTO> result = popJudgeMapper.popJudgesToPopJudgeDTOs(popSubject);
        return result;
    }
}
