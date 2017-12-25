package com.pop.pcms.service.mapper;

import com.pop.pcms.domain.*;
import com.pop.pcms.service.dto.PopAwardConfigDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity PopAwardConfig and its DTO PopAwardConfigDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PopAwardConfigMapper {

    @Mapping(source = "subject.id", target = "subjectId")
    PopAwardConfigDTO popAwardConfigToPopAwardConfigDTO(PopAwardConfig popAwardConfig);

    List<PopAwardConfigDTO> popAwardConfigsToPopAwardConfigDTOs(List<PopAwardConfig> popAwardConfigs);

    @Mapping(source = "subjectId", target = "subject")
    PopAwardConfig popAwardConfigDTOToPopAwardConfig(PopAwardConfigDTO popAwardConfigDTO);

    List<PopAwardConfig> popAwardConfigDTOsToPopAwardConfigs(List<PopAwardConfigDTO> popAwardConfigDTOs);

    default PopSubject popSubjectFromId(Long id) {
        if (id == null) {
            return null;
        }
        PopSubject popSubject = new PopSubject();
        popSubject.setId(id);
        return popSubject;
    }
}
