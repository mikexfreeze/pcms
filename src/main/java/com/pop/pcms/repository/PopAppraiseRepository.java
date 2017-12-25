package com.pop.pcms.repository;

import com.pop.pcms.domain.PopAppraise;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PopAppraise entity.
 */
@SuppressWarnings("unused")
public interface PopAppraiseRepository extends JpaRepository<PopAppraise,Long> {

}
