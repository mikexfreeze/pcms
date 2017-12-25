package com.pop.pcms.repository;

import com.pop.pcms.domain.PopContribute;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the PopContribute entity.
 */
@SuppressWarnings("unused")
public interface PopContributeRepository extends JpaRepository<PopContribute,Long>,JpaSpecificationExecutor<PopContribute> {


    @Query("select a from PopContribute a where a.subject.competition = :id")
    Page<PopContribute> findAllByCompetition(Long id, Pageable pageable);
}
