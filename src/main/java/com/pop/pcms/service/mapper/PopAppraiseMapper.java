package com.pop.pcms.service.mapper;

import com.pop.pcms.domain.*;
import com.pop.pcms.service.dto.PopAppraiseDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity PopAppraise and its DTO PopAppraiseDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PopAppraiseMapper {

    @Mapping(source = "subject.id", target = "subjectId")
    PopAppraiseDTO popAppraiseToPopAppraiseDTO(PopAppraise popAppraise);

    List<PopAppraiseDTO> popAppraisesToPopAppraiseDTOs(List<PopAppraise> popAppraises);

    @Mapping(source = "subjectId", target = "subject")
    PopAppraise popAppraiseDTOToPopAppraise(PopAppraiseDTO popAppraiseDTO);

    List<PopAppraise> popAppraiseDTOsToPopAppraises(List<PopAppraiseDTO> popAppraiseDTOs);

    default PopSubject popSubjectFromId(Long id) {
        if (id == null) {
            return null;
        }
        PopSubject popSubject = new PopSubject();
        popSubject.setId(id);
        return popSubject;
    }
}
