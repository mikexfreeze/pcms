package com.pop.pcms.repository;

import com.pop.pcms.domain.PopVote;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PopVote entity.
 */
@SuppressWarnings("unused")
public interface PopVoteRepository extends JpaRepository<PopVote,Long> {

}
