package com.pop.pcms.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import com.pop.pcms.domain.enumeration.JudgeStatus;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;

/**
 * A DTO for the PopJudge entity.
 */
public class PopJudgeDTO implements Serializable {

    @NotNull(message = "[id]不能为空!",profiles = "pf1")
    @NotEmpty(message = "[id]不能为空!",profiles = "pf1")
    private Long id;

    @NotNull(message = "[userId]不能为空!",profiles = "pf2")
    @NotEmpty(message = "[userId]不能为空!",profiles = "pf2")
    private Long userId;

    @NotNull(message = "[colorFlag]不能为空!",profiles = "pf3")
    @NotEmpty(message = "[colorFlag]不能为空!",profiles = "pf3")
    private String colorFlag;

    private JudgeStatus voteStatus;


    private String userName;

    @NotNull(message = "[appraiseId]不能为空!",profiles = "pf4")
    @NotEmpty(message = "[appraiseId]不能为空!",profiles = "pf4")
    private Long appraiseId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public String getColorFlag() {
        return colorFlag;
    }

    public void setColorFlag(String colorFlag) {
        this.colorFlag = colorFlag;
    }
    public JudgeStatus getVoteStatus() {
        return voteStatus;
    }

    public void setVoteStatus(JudgeStatus voteStatus) {
        this.voteStatus = voteStatus;
    }
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

        PopJudgeDTO popJudgeDTO = (PopJudgeDTO) o;

        if ( ! Objects.equals(id, popJudgeDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PopJudgeDTO{" +
            "id=" + id +
            ", userId='" + userId + "'" +
            ", colorFlag='" + colorFlag + "'" +
            ", voteStatus='" + voteStatus + "'" +
            ", userName='" + userName + "'" +
            '}';
    }
}
