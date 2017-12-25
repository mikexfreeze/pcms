package com.pop.pcms.repository;

import com.pop.pcms.domain.PopCompetition;

import com.pop.pcms.domain.enumeration.CompetitionStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the PopCompetition entity.
 */
@SuppressWarnings("unused")
public interface PopCompetitionRepository extends JpaRepository<PopCompetition, Long>,JpaSpecificationExecutor<PopCompetition> {

    //select id,title,status,start_date,stop_date,result_url,content_url,asset_dir,article_type,competition_type,remark from pop_competition where 1=1 and title like :title and   status =:status
    Page<PopCompetition> findAllByTitleLikeAndStatus(String title, CompetitionStatus status, Pageable var1);
}
