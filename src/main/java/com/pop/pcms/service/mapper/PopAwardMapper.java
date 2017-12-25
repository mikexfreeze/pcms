package com.pop.pcms.service.mapper;

import com.pop.pcms.domain.*;
import com.pop.pcms.service.dto.PopAwardDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity PopAward and its DTO PopAwardDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PopAwardMapper {

    @Mapping(source = "contribute.id", target = "contributeId")
    PopAwardDTO popAwardToPopAwardDTO(PopAward popAward);

    List<PopAwardDTO> popAwardsToPopAwardDTOs(List<PopAward> popAwards);

    @Mapping(source = "contributeId", target = "contribute")
    PopAward popAwardDTOToPopAward(PopAwardDTO popAwardDTO);

    List<PopAward> popAwardDTOsToPopAwards(List<PopAwardDTO> popAwardDTOs);

    default PopContribute popContributeFromId(Long id) {
        if (id == null) {
            return null;
        }
        PopContribute popContribute = new PopContribute();
        popContribute.setId(id);
        return popContribute;
    }
}
