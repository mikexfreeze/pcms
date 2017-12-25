package com.pop.pcms.service.mapper;

import com.pop.pcms.domain.LiveMsg;
import com.pop.pcms.service.dto.LiveMsgDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Mapper for the entity PopAward and its DTO PopAwardDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface LiveMsgMapper {

    @Mapping(source = "competition.id", target = "id")
    LiveMsgDTO LiveMsgToLiveMsgDTO(LiveMsg popAward);

    List<LiveMsgDTO> liveMsgToLiveMsgDTOs(List<LiveMsg> popAwards);

    @Mapping(source = "competition_id", target = "competition.id")
    @Mapping(source = "msgId", target = "msg_id")
    @Mapping(source = "from", target = "fm")
    @Mapping(source = "to", target = "tos")
    LiveMsg LiveMsgDTOToLiveMsg(LiveMsgDTO popAwardDTO);

    List<LiveMsg> LiveMsgDTOsToLiveMsgs(List<LiveMsgDTO> popAwardDTOs);

    default LiveMsgDTO popContributeFromId(Long id) {
        if (id == null) {
            return null;
        }
        LiveMsgDTO popContribute = new LiveMsgDTO();
        popContribute.setId(id);
        return popContribute;
    }
}
