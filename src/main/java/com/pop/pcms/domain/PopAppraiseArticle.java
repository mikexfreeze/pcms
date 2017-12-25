package com.pop.pcms.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

import com.pop.pcms.domain.enumeration.AppraiseArticleStatus;

/**
 * A PopAppraiseArticle.
 */
@Entity
@Table(name = "pop_appraise_article")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PopAppraiseArticle implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private AppraiseArticleStatus status;

    @Column(name = "multiple_flag")
    private String multipleFlag;

    @Column(name = "multiple_score")
    private String multipleScore;

    @Column(name = "revote")
    private Boolean revote;

    @ManyToOne
    private PopAppraise appraise;

    @ManyToOne
    private PopContribute contribute;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AppraiseArticleStatus getStatus() {
        return status;
    }

    public PopAppraiseArticle status(AppraiseArticleStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(AppraiseArticleStatus status) {
        this.status = status;
    }

    public String getMultipleFlag() {
        return multipleFlag;
    }

    public PopAppraiseArticle multipleFlag(String multipleFlag) {
        this.multipleFlag = multipleFlag;
        return this;
    }

    public void setMultipleFlag(String multipleFlag) {
        this.multipleFlag = multipleFlag;
    }

    public String getMultipleScore() {
        return multipleScore;
    }

    public PopAppraiseArticle multipleScore(String multipleScore) {
        this.multipleScore = multipleScore;
        return this;
    }

    public void setMultipleScore(String multipleScore) {
        this.multipleScore = multipleScore;
    }

    public Boolean isRevote() {
        return revote;
    }

    public PopAppraiseArticle revote(Boolean revote) {
        this.revote = revote;
        return this;
    }

    public void setRevote(Boolean revote) {
        this.revote = revote;
    }

    public PopAppraise getAppraise() {
        return appraise;
    }

    public PopAppraiseArticle appraise(PopAppraise popAppraise) {
        this.appraise = popAppraise;
        return this;
    }

    public void setAppraise(PopAppraise popAppraise) {
        this.appraise = popAppraise;
    }

    public PopContribute getContribute() {
        return contribute;
    }

    public PopAppraiseArticle contribute(PopContribute popContribute) {
        this.contribute = popContribute;
        return this;
    }

    public void setContribute(PopContribute popContribute) {
        this.contribute = popContribute;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PopAppraiseArticle popAppraiseArticle = (PopAppraiseArticle) o;
        if (popAppraiseArticle.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, popAppraiseArticle.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PopAppraiseArticle{" +
            "id=" + id +
            ", status='" + status + "'" +
            ", multipleFlag='" + multipleFlag + "'" +
            ", multipleScore='" + multipleScore + "'" +
            ", revote='" + revote + "'" +
            '}';
    }
}
