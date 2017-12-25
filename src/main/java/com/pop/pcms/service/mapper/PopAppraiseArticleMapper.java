package com.pop.pcms.service.mapper;

import com.pop.pcms.domain.*;
import com.pop.pcms.service.dto.PopAppraiseArticleDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity PopAppraiseArticle and its DTO PopAppraiseArticleDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PopAppraiseArticleMapper {

    @Mapping(source = "appraise.id", target = "appraiseId")
    @Mapping(source = "contribute.id", target = "contributeId")
    PopAppraiseArticleDTO popAppraiseArticleToPopAppraiseArticleDTO(PopAppraiseArticle popAppraiseArticle);

    List<PopAppraiseArticleDTO> popAppraiseArticlesToPopAppraiseArticleDTOs(List<PopAppraiseArticle> popAppraiseArticles);

    @Mapping(source = "appraiseId", target = "appraise")
    @Mapping(source = "contributeId", target = "contribute")
    PopAppraiseArticle popAppraiseArticleDTOToPopAppraiseArticle(PopAppraiseArticleDTO popAppraiseArticleDTO);

    List<PopAppraiseArticle> popAppraiseArticleDTOsToPopAppraiseArticles(List<PopAppraiseArticleDTO> popAppraiseArticleDTOs);

    default PopAppraise popAppraiseFromId(Long id) {
        if (id == null) {
            return null;
        }
        PopAppraise popAppraise = new PopAppraise();
        popAppraise.setId(id);
        return popAppraise;
    }

    default PopContribute popContributeFromId(Long id) {
        if (id == null) {
            return null;
        }
        PopContribute popContribute = new PopContribute();
        popContribute.setId(id);
        return popContribute;
    }
}
