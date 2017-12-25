package com.pop.pcms.service.mapper;

import com.pop.pcms.domain.*;
import com.pop.pcms.service.dto.PopContributeDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity PopContribute and its DTO PopContributeDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PopContributeMapper {

    @Mapping(source = "subject.id", target = "subjectId")
    PopContributeDTO popContributeToPopContributeDTO(PopContribute popContribute);

    List<PopContributeDTO> popContributesToPopContributeDTOs(List<PopContribute> popContributes);

    @Mapping(source = "subjectId", target = "subject")
    PopContribute popContributeDTOToPopContribute(PopContributeDTO popContributeDTO);

    List<PopContribute> popContributeDTOsToPopContributes(List<PopContributeDTO> popContributeDTOs);

    default PopSubject popSubjectFromId(Long id) {
        if (id == null) {
            return null;
        }
        PopSubject popSubject = new PopSubject();
        popSubject.setId(id);
        return popSubject;
    }
}
