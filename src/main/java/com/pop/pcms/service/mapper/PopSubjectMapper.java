package com.pop.pcms.service.mapper;

import com.pop.pcms.domain.*;
import com.pop.pcms.service.dto.PopSubjectDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity PopSubject and its DTO PopSubjectDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PopSubjectMapper {

    @Mapping(source = "competition.id", target = "competitionId")
    PopSubjectDTO popSubjectToPopSubjectDTO(PopSubject popSubject);

    List<PopSubjectDTO> popSubjectsToPopSubjectDTOs(List<PopSubject> popSubjects);

    @Mapping(source = "competitionId", target = "competition")
    PopSubject popSubjectDTOToPopSubject(PopSubjectDTO popSubjectDTO);

    List<PopSubject> popSubjectDTOsToPopSubjects(List<PopSubjectDTO> popSubjectDTOs);

    default PopCompetition popCompetitionFromId(Long id) {
        if (id == null) {
            return null;
        }
        PopCompetition popCompetition = new PopCompetition();
        popCompetition.setId(id);
        return popCompetition;
    }
}
