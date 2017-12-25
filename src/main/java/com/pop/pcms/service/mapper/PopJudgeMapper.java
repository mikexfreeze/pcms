package com.pop.pcms.service.mapper;

import com.pop.pcms.domain.*;
import com.pop.pcms.service.dto.PopJudgeDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity PopJudge and its DTO PopJudgeDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PopJudgeMapper {

    @Mapping(source = "appraise.id", target = "appraiseId")
    PopJudgeDTO popJudgeToPopJudgeDTO(PopJudge popJudge);

    List<PopJudgeDTO> popJudgesToPopJudgeDTOs(List<PopJudge> popJudges);

    @Mapping(source = "appraiseId", target = "appraise")
    PopJudge popJudgeDTOToPopJudge(PopJudgeDTO popJudgeDTO);

    List<PopJudge> popJudgeDTOsToPopJudges(List<PopJudgeDTO> popJudgeDTOs);

    default PopAppraise popAppraiseFromId(Long id) {
        if (id == null) {
            return null;
        }
        PopAppraise popAppraise = new PopAppraise();
        popAppraise.setId(id);
        return popAppraise;
    }
}
