package com.pop.pcms.domain;

import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

import com.pop.pcms.domain.enumeration.VoteStatus;

import com.pop.pcms.domain.enumeration.VoteType;

/**
 * 投票
 */
@ApiModel(description = "投票")
@Entity
@Table(name = "pop_vote")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PopVote implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private VoteStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "vote_type")
    private VoteType voteType;

    @ManyToOne
    private PopJudge judge;

    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private PopContribute contribute;

    @ManyToOne
    private PopAppraise appraise;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public VoteStatus getStatus() {
        return status;
    }

    public PopVote status(VoteStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(VoteStatus status) {
        this.status = status;
    }

    public VoteType getVoteType() {
        return voteType;
    }

    public PopVote voteType(VoteType voteType) {
        this.voteType = voteType;
        return this;
    }

    public void setVoteType(VoteType voteType) {
        this.voteType = voteType;
    }

    public PopJudge getJudge() {
        return judge;
    }

    public PopVote judge(PopJudge popJudge) {
        this.judge = popJudge;
        return this;
    }

    public void setJudge(PopJudge popJudge) {
        this.judge = popJudge;
    }

    public PopContribute getContribute() {
        return contribute;
    }

    public PopVote contribute(PopContribute popContribute) {
        this.contribute = popContribute;
        return this;
    }

    public void setContribute(PopContribute popContribute) {
        this.contribute = popContribute;
    }

    public PopAppraise getAppraise() {
        return appraise;
    }

    public PopVote appraise(PopAppraise popAppraise) {
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
        PopVote popVote = (PopVote) o;
        if (popVote.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, popVote.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PopVote{" +
            "id=" + id +
            ", status='" + status + "'" +
            ", voteType='" + voteType + "'" +
            '}';
    }
}
