package com.pop.pcms.repository;

import com.pop.pcms.domain.PopSubject;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PopSubject entity.
 */
@SuppressWarnings("unused")
public interface PopSubjectRepository extends JpaRepository<PopSubject,Long> {

    List<PopSubject> findByCompetitionId(Long competitionId);

}
