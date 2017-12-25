package com.pop.pcms.repository;

import com.pop.pcms.domain.PopAppraiseArticle;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PopAppraiseArticle entity.
 */
@SuppressWarnings("unused")
public interface PopAppraiseArticleRepository extends JpaRepository<PopAppraiseArticle,Long> {

}
