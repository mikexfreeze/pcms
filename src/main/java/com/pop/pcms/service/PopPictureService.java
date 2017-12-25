package com.pop.pcms.service;

import com.pop.pcms.domain.PopPicture;
import com.pop.pcms.domain.PopViewPicture;
import com.pop.pcms.repository.PopPictureRepository;
import com.pop.pcms.service.dto.PopPictureDTO;
import com.pop.pcms.service.mapper.PopPictureMapper;
import com.pop.pcms.web.rest.util.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

/**
 * Service Implementation for managing PopPicture.
 */
@Service
@Transactional
public class PopPictureService {

    private final Logger log = LoggerFactory.getLogger(PopPictureService.class);

    private final PopPictureRepository popPictureRepository;

    private final PopPictureMapper popPictureMapper;

    @PersistenceUnit
    private EntityManagerFactory emf;

    public PopPictureService(PopPictureRepository popPictureRepository, PopPictureMapper popPictureMapper) {
        this.popPictureRepository = popPictureRepository;
        this.popPictureMapper = popPictureMapper;
    }

    /**
     * Save a popPicture.
     *
     * @param popPictureDTO the entity to save
     * @return the persisted entity
     */
    public PopPictureDTO save(PopPictureDTO popPictureDTO) {
        log.debug("Request to save PopPicture : {}", popPictureDTO);
        PopPicture popPicture = popPictureMapper.popPictureDTOToPopPicture(popPictureDTO);
        popPicture = popPictureRepository.save(popPicture);
        PopPictureDTO result = popPictureMapper.popPictureToPopPictureDTO(popPicture);
        return result;
    }

    /**
     * Get all the popPictures.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<PopPictureDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PopPictures");
        Page<PopPicture> result = popPictureRepository.findAll(pageable);
        return result.map(popPicture -> popPictureMapper.popPictureToPopPictureDTO(popPicture));
    }

    /**
     * Get one popPicture by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public PopPictureDTO findOne(Long id) {
        log.debug("Request to get PopPicture : {}", id);
        PopPicture popPicture = popPictureRepository.findOne(id);
        PopPictureDTO popPictureDTO = popPictureMapper.popPictureToPopPictureDTO(popPicture);
        return popPictureDTO;
    }

    /**
     * Delete the  popPicture by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        popPictureRepository.delete(id);
    }

    /**
     * 根据主题ID找到对应的作品
     *
     * @param contributeId
     * @return
     */
    @Transactional(readOnly = true)
    public List<PopViewPicture> findByContributeId(Long contributeId) {
        String sql = "select t.title,b.name,d.pic_path,d.remark,d.shoot_address,d.shoot_date,d.contribute_id,d.id from pop_competition t,pop_subject b,pop_contribute c, pop_picture d" +
            " where t.id=b.competition_id \n" +
            " and b.id=c.subject_id\n" +
            " and c.id=d.contribute_id\n" +
            " and t.id=" + contributeId;
        EntityManager em = emf.createEntityManager();
        Query query = em.createNativeQuery(sql);
        List<?> objecArraytList = query.getResultList();
        List<PopViewPicture> result = new ArrayList<PopViewPicture>();
        if (objecArraytList != null && objecArraytList.size() > 0) {
            for (int i = 0; i < objecArraytList.size(); i++) {
                Object[] obj = (Object[]) objecArraytList.get(i);
                //使用obj[0],obj[1],obj[2]...取出属性
                try {
                    PopViewPicture p = new PopViewPicture();
                    p.setTitle(String.valueOf(obj[0]));//title
                    p.setName(String.valueOf(obj[1]));//name
                    p.setPicPath(String.valueOf(obj[2]));
                    p.setRemark(String.valueOf(obj[3]));
                    p.setShootAddress(String.valueOf(obj[4]));
                    p.setShootDate(DateUtils.asLocalDate((java.sql.Date) obj[5]));
                    p.setContributeId(Long.parseLong(String.valueOf(obj[6])));
                    p.setId(Long.parseLong(String.valueOf(obj[7])));
                    result.add(p);
                }catch (Exception e){
                    log.error("保存数据异常:{}",e);
                }

            }
        }
        // List<PopPicture> result = popPictureRepository.findByContributeId(contributeId);
        //List<PopPictureDTO> dtoList=popPictureMapper.popPicturesToPopPictureDTOs(result);
        return result;
    }

}
