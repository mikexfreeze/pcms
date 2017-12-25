package com.pop.pcms.repository;

import com.pop.pcms.domain.PopObserver;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PopObserver entity.
 */
@SuppressWarnings("unused")
public interface PopObserverRepository extends JpaRepository<PopObserver,Long> {

}
