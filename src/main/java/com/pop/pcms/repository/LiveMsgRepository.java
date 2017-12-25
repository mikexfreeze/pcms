package com.pop.pcms.repository;

import com.pop.pcms.domain.LiveMsg;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the PopPicture entity.
 */
@SuppressWarnings("unused")
public interface LiveMsgRepository extends JpaRepository<LiveMsg,Long> {


}
