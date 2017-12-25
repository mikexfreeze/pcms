package com.pop.pcms.repository;

import com.pop.pcms.domain.PopJudge;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PopJudge entity.
 */
@SuppressWarnings("unused")
public interface PopJudgeRepository extends JpaRepository<PopJudge,Long> {

    List<PopJudge> findByAppraiseId(Long appraiseId);

}
