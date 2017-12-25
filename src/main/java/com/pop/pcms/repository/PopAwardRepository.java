package com.pop.pcms.repository;

import com.pop.pcms.domain.PopAward;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PopAward entity.
 */
@SuppressWarnings("unused")
public interface PopAwardRepository extends JpaRepository<PopAward,Long> {

}
