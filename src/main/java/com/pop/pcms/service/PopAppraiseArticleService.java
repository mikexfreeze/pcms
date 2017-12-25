package com.pop.pcms.service;

import com.pop.pcms.domain.PopAppraiseArticle;
import com.pop.pcms.repository.PopAppraiseArticleRepository;
import com.pop.pcms.service.dto.PopAppraiseArticleDTO;
import com.pop.pcms.service.mapper.PopAppraiseArticleMapper;
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
 * Service Implementation for managing PopAppraiseArticle.
 */
@Service
@Transactional
public class PopAppraiseArticleService {

    private final Logger log = LoggerFactory.getLogger(PopAppraiseArticleService.class);
    
    private final PopAppraiseArticleRepository popAppraiseArticleRepository;

    private final PopAppraiseArticleMapper popAppraiseArticleMapper;

    public PopAppraiseArticleService(PopAppraiseArticleRepository popAppraiseArticleRepository, PopAppraiseArticleMapper popAppraiseArticleMapper) {
        this.popAppraiseArticleRepository = popAppraiseArticleRepository;
        this.popAppraiseArticleMapper = popAppraiseArticleMapper;
    }

    /**
     * Save a popAppraiseArticle.
     *
     * @param popAppraiseArticleDTO the entity to save
     * @return the persisted entity
     */
    public PopAppraiseArticleDTO save(PopAppraiseArticleDTO popAppraiseArticleDTO) {
        log.debug("Request to save PopAppraiseArticle : {}", popAppraiseArticleDTO);
        PopAppraiseArticle popAppraiseArticle = popAppraiseArticleMapper.popAppraiseArticleDTOToPopAppraiseArticle(popAppraiseArticleDTO);
        popAppraiseArticle = popAppraiseArticleRepository.save(popAppraiseArticle);
        PopAppraiseArticleDTO result = popAppraiseArticleMapper.popAppraiseArticleToPopAppraiseArticleDTO(popAppraiseArticle);
        return result;
    }

    /**
     *  Get all the popAppraiseArticles.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<PopAppraiseArticleDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PopAppraiseArticles");
        Page<PopAppraiseArticle> result = popAppraiseArticleRepository.findAll(pageable);
        return result.map(popAppraiseArticle -> popAppraiseArticleMapper.popAppraiseArticleToPopAppraiseArticleDTO(popAppraiseArticle));
    }

    /**
     *  Get one popAppraiseArticle by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public PopAppraiseArticleDTO findOne(Long id) {
        log.debug("Request to get PopAppraiseArticle : {}", id);
        PopAppraiseArticle popAppraiseArticle = popAppraiseArticleRepository.findOne(id);
        PopAppraiseArticleDTO popAppraiseArticleDTO = popAppraiseArticleMapper.popAppraiseArticleToPopAppraiseArticleDTO(popAppraiseArticle);
        return popAppraiseArticleDTO;
    }

    /**
     *  Delete the  popAppraiseArticle by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PopAppraiseArticle : {}", id);
        popAppraiseArticleRepository.delete(id);
    }
}
