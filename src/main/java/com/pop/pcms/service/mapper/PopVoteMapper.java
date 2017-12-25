package com.pop.pcms.service.mapper;

import com.pop.pcms.domain.*;
import com.pop.pcms.service.dto.PopVoteDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity PopVote and its DTO PopVoteDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PopVoteMapper {

    @Mapping(source = "judge.id", target = "judgeId")
    @Mapping(source = "contribute.id", target = "contributeId")
    @Mapping(source = "appraise.id", target = "appraiseId")
    PopVoteDTO popVoteToPopVoteDTO(PopVote popVote);

    List<PopVoteDTO> popVotesToPopVoteDTOs(List<PopVote> popVotes);

    @Mapping(source = "judgeId", target = "judge")
    @Mapping(source = "contributeId", target = "contribute")
    @Mapping(source = "appraiseId", target = "appraise")
    PopVote popVoteDTOToPopVote(PopVoteDTO popVoteDTO);

    List<PopVote> popVoteDTOsToPopVotes(List<PopVoteDTO> popVoteDTOs);

    default PopJudge popJudgeFromId(Long id) {
        if (id == null) {
            return null;
        }
        PopJudge popJudge = new PopJudge();
        popJudge.setId(id);
        return popJudge;
    }

    default PopContribute popContributeFromId(Long id) {
        if (id == null) {
            return null;
        }
        PopContribute popContribute = new PopContribute();
        popContribute.setId(id);
        return popContribute;
    }

    default PopAppraise popAppraiseFromId(Long id) {
        if (id == null) {
            return null;
        }
        PopAppraise popAppraise = new PopAppraise();
        popAppraise.setId(id);
        return popAppraise;
    }
}
