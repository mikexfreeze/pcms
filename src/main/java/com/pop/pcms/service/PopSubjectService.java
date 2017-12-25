package com.pop.pcms.service;

import com.pop.pcms.domain.PopSubject;
import com.pop.pcms.repository.PopSubjectRepository;
import com.pop.pcms.service.dto.PopSubjectDTO;
import com.pop.pcms.service.mapper.PopSubjectMapper;
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
 * Service Implementation for managing PopSubject.
 */
@Service
@Transactional
public class PopSubjectService {

    private final Logger log = LoggerFactory.getLogger(PopSubjectService.class);

    private final PopSubjectRepository popSubjectRepository;

    private final PopSubjectMapper popSubjectMapper;

    public PopSubjectService(PopSubjectRepository popSubjectRepository, PopSubjectMapper popSubjectMapper) {
        this.popSubjectRepository = popSubjectRepository;
        this.popSubjectMapper = popSubjectMapper;
    }

    /**
     * Save a popSubject.
     *
     * @param popSubjectDTO the entity to save
     * @return the persisted entity
     */
    public PopSubjectDTO save(PopSubjectDTO popSubjectDTO) {
        log.debug("Request to save PopSubject : {}", popSubjectDTO);
        PopSubject popSubject = popSubjectMapper.popSubjectDTOToPopSubject(popSubjectDTO);
        popSubject = popSubjectRepository.save(popSubject);
        PopSubjectDTO result = popSubjectMapper.popSubjectToPopSubjectDTO(popSubject);
        return result;
    }

    /**
     * Get all the popSubjects.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<PopSubjectDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PopSubjects");
        Page<PopSubject> result = popSubjectRepository.findAll(pageable);
        return result.map(popSubject -> popSubjectMapper.popSubjectToPopSubjectDTO(popSubject));
    }


    /**
     * 根据活动ID找到当前目录下的所有主题
     *
     * @param competitionId
     * @return
     */
    @Transactional(readOnly = true)
    public List<PopSubjectDTO> findByCompetitionId(Long competitionId) {
        log.debug("Request to get all PopSubjects");
        List<PopSubject> popSubject = popSubjectRepository.findByCompetitionId(competitionId);
        List<PopSubjectDTO> result = popSubjectMapper.popSubjectsToPopSubjectDTOs(popSubject);
        return result;
    }

    /**
     * Get one popSubject by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public PopSubjectDTO findOne(Long id) {
        log.debug("Request to get PopSubject : {}", id);
        PopSubject popSubject = popSubjectRepository.findOne(id);
        PopSubjectDTO popSubjectDTO = popSubjectMapper.popSubjectToPopSubjectDTO(popSubject);
        return popSubjectDTO;
    }

    /**
     * Delete the  popSubject by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PopSubject : {}", id);
        popSubjectRepository.delete(id);
    }
}
