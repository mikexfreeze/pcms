package com.pop.pcms.domain;

import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

import com.pop.pcms.domain.enumeration.JudgeStatus;

/**
 * 评委
 */
@ApiModel(description = "评委")
@Entity
@Table(name = "pop_judge")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PopJudge implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "color_flag")
    private String colorFlag;

    @Enumerated(EnumType.STRING)
    @Column(name = "vote_status")
    private JudgeStatus voteStatus;

    @Column(name = "user_name")
    private String userName;

    @ManyToOne
    private PopAppraise appraise;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public PopJudge userId(Long userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getColorFlag() {
        return colorFlag;
    }

    public PopJudge colorFlag(String colorFlag) {
        this.colorFlag = colorFlag;
        return this;
    }

    public void setColorFlag(String colorFlag) {
        this.colorFlag = colorFlag;
    }

    public JudgeStatus getVoteStatus() {
        return voteStatus;
    }

    public PopJudge voteStatus(JudgeStatus voteStatus) {
        this.voteStatus = voteStatus;
        return this;
    }

    public void setVoteStatus(JudgeStatus voteStatus) {
        this.voteStatus = voteStatus;
    }

    public String getUserName() {
        return userName;
    }

    public PopJudge userName(String userName) {
        this.userName = userName;
        return this;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public PopAppraise getAppraise() {
        return appraise;
    }

    public PopJudge appraise(PopAppraise popAppraise) {
        this.appraise = popAppraise;
        return this;
    }

    public void setAppraise(PopAppraise popAppraise) {
        this.appraise = popAppraise;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PopJudge popJudge = (PopJudge) o;
        if (popJudge.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, popJudge.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PopJudge{" +
            "id=" + id +
            ", userId='" + userId + "'" +
            ", colorFlag='" + colorFlag + "'" +
            ", voteStatus='" + voteStatus + "'" +
            ", userName='" + userName + "'" +
            '}';
    }
}
