package com.pop.pcms.repository;

import com.pop.pcms.domain.PopPicture;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PopPicture entity.
 */
@SuppressWarnings("unused")
public interface PopPictureRepository extends JpaRepository<PopPicture,Long> {

    //根据主题ID找到所有的主题
    List<PopPicture> findByContributeId(Long contributeId);

}
