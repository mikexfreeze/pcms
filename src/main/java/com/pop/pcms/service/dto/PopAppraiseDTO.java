package com.pop.pcms.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import com.pop.pcms.domain.enumeration.AppraiseStatus;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;

/**
 * A DTO for the PopAppraise entity.
 */
public class PopAppraiseDTO implements Serializable {

    @NotNull(message = "[id]不能为空!",profiles = "pf6")
    @NotEmpty(message = "[id]不能为空!",profiles = "pf6")
    private Long id;

    @NotNull(message = "[round]不能为空!",profiles = "pf1")
    @NotEmpty(message = "[round]不能为空!",profiles = "pf1")
    private Long round;

    private String appraiseType;

    @NotNull(message = "[pollNum]不能为空!",profiles = "pf2")
    @NotEmpty(message = "[pollNum]不能为空!",profiles = "pf2")
    private String pollNum;

    private String remark;

    @NotNull(message = "[awardName]不能为空!",profiles = "pf5")
    @NotEmpty(message = "[awardName]不能为空!",profiles = "pf5")
    private String awardName;

    private AppraiseStatus status;

    @NotNull(message = "[awardConfigId]不能为空!",profiles = "pf4")
    @NotEmpty(message = "[awardConfigId]不能为空!",profiles = "pf4")
    private String awardConfigId;

    private Long parentAppraise;

    @NotNull(message = "[subjectId]不能为空!",profiles = "pf3")
    @NotEmpty(message = "[subjectId]不能为空!",profiles = "pf3")
    private Long subjectId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Long getRound() {
        return round;
    }

    public void setRound(Long round) {
        this.round = round;
    }
    public String getAppraiseType() {
        return appraiseType;
    }

    public void setAppraiseType(String appraiseType) {
        this.appraiseType = appraiseType;
    }
    public String getPollNum() {
        return pollNum;
    }

    public void setPollNum(String pollNum) {
        this.pollNum = pollNum;
    }
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
    public String getAwardName() {
        return awardName;
    }

    public void setAwardName(String awardName) {
        this.awardName = awardName;
    }
    public AppraiseStatus getStatus() {
        return status;
    }

    public void setStatus(AppraiseStatus status) {
        this.status = status;
    }
    public String getAwardConfigId() {
        return awardConfigId;
    }

    public void setAwardConfigId(String awardConfigId) {
        this.awardConfigId = awardConfigId;
    }
    public Long getParentAppraise() {
        return parentAppraise;
    }

    public void setParentAppraise(Long parentAppraise) {
        this.parentAppraise = parentAppraise;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long popSubjectId) {
        this.subjectId = popSubjectId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PopAppraiseDTO popAppraiseDTO = (PopAppraiseDTO) o;

        if ( ! Objects.equals(id, popAppraiseDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PopAppraiseDTO{" +
            "id=" + id +
            ", round='" + round + "'" +
            ", appraiseType='" + appraiseType + "'" +
            ", pollNum='" + pollNum + "'" +
            ", remark='" + remark + "'" +
            ", awardName='" + awardName + "'" +
            ", status='" + status + "'" +
            ", awardConfigId='" + awardConfigId + "'" +
            ", parentAppraise='" + parentAppraise + "'" +
            '}';
    }
}
