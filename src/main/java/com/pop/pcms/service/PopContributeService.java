package com.pop.pcms.service;

import com.pop.pcms.domain.PopCompetition;
import com.pop.pcms.domain.PopContribute;
import com.pop.pcms.domain.PopPicture;
import com.pop.pcms.repository.PopContributeRepository;
import com.pop.pcms.repository.PopPictureRepository;
import com.pop.pcms.service.dto.PopCompetitionDTO;
import com.pop.pcms.service.dto.PopContributeDTO;
import com.pop.pcms.service.dto.PopPictureDTO;
import com.pop.pcms.service.mapper.PopContributeMapper;
import com.pop.pcms.service.mapper.PopPictureMapper;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * Service Implementation for managing PopContribute.
 */
@Service
@Transactional
public class PopContributeService {

    private final Logger log = LoggerFactory.getLogger(PopContributeService.class);

    private final PopContributeRepository popContributeRepository;

    private final PopContributeMapper popContributeMapper;

    //作品服务
    @Autowired
    private PopPictureRepository popPictureRepository;

    //作品服务映射关系
    @Autowired
    private PopPictureMapper popPictureMapper;

    public PopContributeService(PopContributeRepository popContributeRepository, PopContributeMapper popContributeMapper) {
        this.popContributeRepository = popContributeRepository;
        this.popContributeMapper = popContributeMapper;
        //this.popPictureRepository=popPictureRepository;
    }

    /**
     * Save a popContribute.
     *
     * @param popContributeDTO the entity to save
     * @return the persisted entity
     */
    public PopContributeDTO save(PopContributeDTO popContributeDTO) {
        PopContribute popContribute = popContributeMapper.popContributeDTOToPopContribute(popContributeDTO);
        popContribute = popContributeRepository.save(popContribute);
        //保存作品
        List<PopPictureDTO> pictList = popContributeDTO.getPictList();
        if (pictList != null && pictList.size() > 0) {
            for (PopPictureDTO dto : pictList) {
                PopPicture popPicture = popPictureMapper.popPictureDTOToPopPicture(dto);
                popPicture.setContribute(popContribute);
                popPictureRepository.save(popPicture);
            }
        }
        //保存主题
        PopContributeDTO result = popContributeMapper.popContributeToPopContributeDTO(popContribute);
        return result;
    }

    /**
     * Get all the popContributes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<PopContributeDTO> findAll(Pageable pageable) {
        Page<PopContribute> result = popContributeRepository.findAll(pageable);
        return result.map(popContribute -> popContributeMapper.popContributeToPopContributeDTO(popContribute));
    }

    /**
     * 动态生成where语句
     *
     * @param
     * @return
     */
    private Specification<PopContribute> getWhereClause(final PopContribute popCompetition) {
        return new Specification<PopContribute>() {
            @Override
            public Predicate toPredicate(Root<PopContribute> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicate = new ArrayList<>();
                if (StringUtils.isNotEmpty(String.valueOf(popCompetition.getId()))) {
                    Predicate lineFactoryLike = cb.equal(root.get("id"), popCompetition.getId());
                    predicate.add(lineFactoryLike);
                }
                Predicate[] pre = new Predicate[predicate.size()];
                return query.where(predicate.toArray(pre)).getRestriction();
            }
        };
    }

    /**
     * Get all the popCompetitions.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<PopContributeDTO> findAll(Pageable pageable, PopContribute p) {
        Page<PopContribute> result = popContributeRepository.findAll(getWhereClause(p), pageable);
        return result.map(popCompetition -> popContributeMapper.popContributeToPopContributeDTO(popCompetition));
    }


    /**
     * Get one popContribute by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public PopContributeDTO findOne(Long id) {
        log.debug("Request to get PopContribute : {}", id);
        PopContribute popContribute = popContributeRepository.findOne(id);
        PopContributeDTO popContributeDTO = popContributeMapper.popContributeToPopContributeDTO(popContribute);
        return popContributeDTO;
    }

    /**
     * Delete the  popContribute by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PopContribute : {}", id);
        popContributeRepository.delete(id);
    }

    /**
     * 删除对应的征稿和征稿对应的图片
     *
     * @param dto
     */
    @Transactional
    public void deletePopContributeDTO(PopContributeDTO dto) {
        Long id = dto.getId();
        List<PopPicture> dtoList = popPictureRepository.findByContributeId(id);
        if (dtoList != null && dtoList.size() > 0) {
            for (PopPicture p : dtoList) {
                popPictureRepository.delete(p.getId());
            }
        }
        //先删除
        popContributeRepository.delete(id);
    }
}
