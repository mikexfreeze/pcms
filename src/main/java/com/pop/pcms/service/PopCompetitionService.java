package com.pop.pcms.service;

import com.pop.pcms.domain.PopCompetition;
import com.pop.pcms.repository.PopCompetitionRepository;
import com.pop.pcms.service.dto.PopCompetitionDTO;
import com.pop.pcms.service.mapper.PopCompetitionMapper;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.Predicate;

/**
 * Service Implementation for managing PopCompetition.
 */
@Service
@Transactional
public class PopCompetitionService {

    private final Logger log = LoggerFactory.getLogger(PopCompetitionService.class);

    private final PopCompetitionRepository popCompetitionRepository;

    private final PopCompetitionMapper popCompetitionMapper;

    //活动列表展示列表
    //private PopPictureViewRepository popPictureViewRepository;

    public PopCompetitionService(PopCompetitionRepository popCompetitionRepository, PopCompetitionMapper popCompetitionMapper) {
        this.popCompetitionRepository = popCompetitionRepository;
        this.popCompetitionMapper = popCompetitionMapper;
    }

    /**
     * Save a popCompetition.
     *
     * @param popCompetitionDTO the entity to save
     * @return the persisted entity
     */
    public PopCompetitionDTO save(PopCompetitionDTO popCompetitionDTO) {
        log.debug("Request to save PopCompetition : {}", popCompetitionDTO);
        PopCompetition popCompetition = popCompetitionMapper.popCompetitionDTOToPopCompetition(popCompetitionDTO);
        popCompetition = popCompetitionRepository.save(popCompetition);
        PopCompetitionDTO result = popCompetitionMapper.popCompetitionToPopCompetitionDTO(popCompetition);
        return result;
    }

    /**
     *  Get all the popCompetitions.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<PopCompetitionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PopCompetitions");
        Page<PopCompetition> result = popCompetitionRepository.findAll(pageable);
        return result.map(popCompetition -> popCompetitionMapper.popCompetitionToPopCompetitionDTO(popCompetition));
    }

    /**
     * 动态生成where语句
     * @param
     * @return
     */
    private Specification<PopCompetition> getWhereClause(final PopCompetition popCompetition) {
        return new Specification<PopCompetition>() {
            @Override
            public Predicate toPredicate(Root<PopCompetition> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicate = new ArrayList<>();
                if (StringUtils.isNotEmpty(popCompetition.getTitle())) {
                    Predicate lineFactoryLike = cb.like(root.get("title"), "%" + popCompetition.getTitle() + "%");
                    predicate.add(lineFactoryLike);
                }
                if (popCompetition.getStatus()!=null) {
                    Predicate lineFactoryLike = cb.equal(root.get("status"),   popCompetition.getStatus() );
                    predicate.add(lineFactoryLike);
                }
                Predicate[] pre = new Predicate[predicate.size()];
                return query.where(predicate.toArray(pre)).getRestriction();
            }
        };
    }
    /**
     *  Get all the popCompetitions.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<PopCompetitionDTO> findAllByTitleLikeAndStatus(Pageable pageable,PopCompetition p) {
        Page<PopCompetition> result = popCompetitionRepository.findAll(getWhereClause(p),pageable);
        return result.map(popCompetition -> popCompetitionMapper.popCompetitionToPopCompetitionDTO(popCompetition));
    }

    /**
     *  Get one popCompetition by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public PopCompetitionDTO findOne(Long id) {
        log.debug("Request to get PopCompetition : {}", id);
        PopCompetition popCompetition = popCompetitionRepository.findOne(id);
        PopCompetitionDTO popCompetitionDTO = popCompetitionMapper.popCompetitionToPopCompetitionDTO(popCompetition);
        return popCompetitionDTO;
    }

    /**
     *  Delete the  popCompetition by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PopCompetition : {}", id);
        popCompetitionRepository.delete(id);
    }
}
