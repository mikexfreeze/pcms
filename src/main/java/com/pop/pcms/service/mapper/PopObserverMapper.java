package com.pop.pcms.service.mapper;

import com.pop.pcms.domain.*;
import com.pop.pcms.service.dto.PopObserverDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity PopObserver and its DTO PopObserverDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PopObserverMapper {

    @Mapping(source = "appraise.id", target = "appraiseId")
    PopObserverDTO popObserverToPopObserverDTO(PopObserver popObserver);

    List<PopObserverDTO> popObserversToPopObserverDTOs(List<PopObserver> popObservers);

    @Mapping(source = "appraiseId", target = "appraise")
    PopObserver popObserverDTOToPopObserver(PopObserverDTO popObserverDTO);

    List<PopObserver> popObserverDTOsToPopObservers(List<PopObserverDTO> popObserverDTOs);

    default PopAppraise popAppraiseFromId(Long id) {
        if (id == null) {
            return null;
        }
        PopAppraise popAppraise = new PopAppraise();
        popAppraise.setId(id);
        return popAppraise;
    }
}
