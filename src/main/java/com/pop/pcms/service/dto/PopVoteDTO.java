package com.pop.pcms.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import com.pop.pcms.domain.enumeration.VoteStatus;
import com.pop.pcms.domain.enumeration.VoteType;

/**
 * A DTO for the PopVote entity.
 */
public class PopVoteDTO implements Serializable {

    private Long id;

    private VoteStatus status;

    private VoteType voteType;

    private Long judgeId;

    private Long contributeId;

    private Long appraiseId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public VoteStatus getStatus() {
        return status;
    }

    public void setStatus(VoteStatus status) {
        this.status = status;
    }
    public VoteType getVoteType() {
        return voteType;
    }

    public void setVoteType(VoteType voteType) {
        this.voteType = voteType;
    }

    public Long getJudgeId() {
        return judgeId;
    }

    public void setJudgeId(Long popJudgeId) {
        this.judgeId = popJudgeId;
    }

    public Long getContributeId() {
        return contributeId;
    }

    public void setContributeId(Long popContributeId) {
        this.contributeId = popContributeId;
    }

    public Long getAppraiseId() {
        return appraiseId;
    }

    public void setAppraiseId(Long popAppraiseId) {
        this.appraiseId = popAppraiseId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PopVoteDTO popVoteDTO = (PopVoteDTO) o;

        if ( ! Objects.equals(id, popVoteDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PopVoteDTO{" +
            "id=" + id +
            ", status='" + status + "'" +
            ", voteType='" + voteType + "'" +
            '}';
    }
}
