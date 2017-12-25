package com.pop.pcms.service.mapper;

import com.pop.pcms.domain.*;
import com.pop.pcms.service.dto.PopCompetitionDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity PopCompetition and its DTO PopCompetitionDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PopCompetitionMapper {

    PopCompetitionDTO popCompetitionToPopCompetitionDTO(PopCompetition popCompetition);

    List<PopCompetitionDTO> popCompetitionsToPopCompetitionDTOs(List<PopCompetition> popCompetitions);

    PopCompetition popCompetitionDTOToPopCompetition(PopCompetitionDTO popCompetitionDTO);

    List<PopCompetition> popCompetitionDTOsToPopCompetitions(List<PopCompetitionDTO> popCompetitionDTOs);
}
