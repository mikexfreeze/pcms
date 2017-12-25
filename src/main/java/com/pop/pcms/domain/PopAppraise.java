package com.pop.pcms.domain;

import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

import com.pop.pcms.domain.enumeration.AppraiseStatus;

/**
 * 评选轮次
 */
@ApiModel(description = "评选轮次")
@Entity
@Table(name = "pop_appraise")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PopAppraise implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "round")
    private Long round;

    @Column(name = "appraise_type")
    private String appraiseType;

    @Column(name = "poll_num")
    private String pollNum;

    @Column(name = "remark")
    private String remark;

    @Column(name = "award_name")
    private String awardName;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private AppraiseStatus status;

    @Column(name = "award_config_id")
    private String awardConfigId;

    @Column(name = "parent_appraise")
    private Long parentAppraise;

    @ManyToOne
    private PopSubject subject;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRound() {
        return round;
    }

    public PopAppraise round(Long round) {
        this.round = round;
        return this;
    }

    public void setRound(Long round) {
        this.round = round;
    }

    public String getAppraiseType() {
        return appraiseType;
    }

    public PopAppraise appraiseType(String appraiseType) {
        this.appraiseType = appraiseType;
        return this;
    }

    public void setAppraiseType(String appraiseType) {
        this.appraiseType = appraiseType;
    }

    public String getPollNum() {
        return pollNum;
    }

    public PopAppraise pollNum(String pollNum) {
        this.pollNum = pollNum;
        return this;
    }

    public void setPollNum(String pollNum) {
        this.pollNum = pollNum;
    }

    public String getRemark() {
        return remark;
    }

    public PopAppraise remark(String remark) {
        this.remark = remark;
        return this;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getAwardName() {
        return awardName;
    }

    public PopAppraise awardName(String awardName) {
        this.awardName = awardName;
        return this;
    }

    public void setAwardName(String awardName) {
        this.awardName = awardName;
    }

    public AppraiseStatus getStatus() {
        return status;
    }

    public PopAppraise status(AppraiseStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(AppraiseStatus status) {
        this.status = status;
    }

    public String getAwardConfigId() {
        return awardConfigId;
    }

    public PopAppraise awardConfigId(String awardConfigId) {
        this.awardConfigId = awardConfigId;
        return this;
    }

    public void setAwardConfigId(String awardConfigId) {
        this.awardConfigId = awardConfigId;
    }

    public Long getParentAppraise() {
        return parentAppraise;
    }

    public PopAppraise parentAppraise(Long parentAppraise) {
        this.parentAppraise = parentAppraise;
        return this;
    }

    public void setParentAppraise(Long parentAppraise) {
        this.parentAppraise = parentAppraise;
    }

    public PopSubject getSubject() {
        return subject;
    }

    public PopAppraise subject(PopSubject popSubject) {
        this.subject = popSubject;
        return this;
    }

    public void setSubject(PopSubject popSubject) {
        this.subject = popSubject;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PopAppraise popAppraise = (PopAppraise) o;
        if (popAppraise.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, popAppraise.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PopAppraise{" +
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
