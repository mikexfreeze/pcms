package com.pop.pcms.repository;

import com.pop.pcms.domain.PopAwardConfig;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PopAwardConfig entity.
 */
@SuppressWarnings("unused")
public interface PopAwardConfigRepository extends JpaRepository<PopAwardConfig,Long> {

    //根据主题ID找到当前主题下的所有的奖项
    List<PopAwardConfig> findBySubjectId(Long subjectId);

}
