package com.pop.pcms.domain;

import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import com.pop.pcms.domain.enumeration.CompetitionStatus;

import com.pop.pcms.domain.enumeration.ArticleType;

import com.pop.pcms.domain.enumeration.CompetitionType;

/**
 * 活动
 */
@ApiModel(description = "活动")
@Entity
@Table(name = "pop_competition")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PopCompetition implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private CompetitionStatus status;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "stop_date")
    private LocalDate stopDate;

    @Column(name = "result_url")
    private String resultUrl;

    @Column(name = "content_url")
    private String contentUrl;

    @Column(name = "asset_dir")
    private String assetDir;

    @Enumerated(EnumType.STRING)
    @Column(name = "article_type")
    private ArticleType articleType;

    @Enumerated(EnumType.STRING)
    @Column(name = "competition_type")
    private CompetitionType competitionType;

    @Column(name = "remark")
    private String remark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public PopCompetition title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public CompetitionStatus getStatus() {
        return status;
    }

    public PopCompetition status(CompetitionStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(CompetitionStatus status) {
        this.status = status;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public PopCompetition startDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getStopDate() {
        return stopDate;
    }

    public PopCompetition stopDate(LocalDate stopDate) {
        this.stopDate = stopDate;
        return this;
    }

    public void setStopDate(LocalDate stopDate) {
        this.stopDate = stopDate;
    }

    public String getResultUrl() {
        return resultUrl;
    }

    public PopCompetition resultUrl(String resultUrl) {
        this.resultUrl = resultUrl;
        return this;
    }

    public void setResultUrl(String resultUrl) {
        this.resultUrl = resultUrl;
    }

    public String getContentUrl() {
        return contentUrl;
    }

    public PopCompetition contentUrl(String contentUrl) {
        this.contentUrl = contentUrl;
        return this;
    }

    public void setContentUrl(String contentUrl) {
        this.contentUrl = contentUrl;
    }

    public String getAssetDir() {
        return assetDir;
    }

    public PopCompetition assetDir(String assetDir) {
        this.assetDir = assetDir;
        return this;
    }

    public void setAssetDir(String assetDir) {
        this.assetDir = assetDir;
    }

    public ArticleType getArticleType() {
        return articleType;
    }

    public PopCompetition articleType(ArticleType articleType) {
        this.articleType = articleType;
        return this;
    }

    public void setArticleType(ArticleType articleType) {
        this.articleType = articleType;
    }

    public CompetitionType getCompetitionType() {
        return competitionType;
    }

    public PopCompetition competitionType(CompetitionType competitionType) {
        this.competitionType = competitionType;
        return this;
    }

    public void setCompetitionType(CompetitionType competitionType) {
        this.competitionType = competitionType;
    }

    public String getRemark() {
        return remark;
    }

    public PopCompetition remark(String remark) {
        this.remark = remark;
        return this;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PopCompetition popCompetition = (PopCompetition) o;
        if (popCompetition.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, popCompetition.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PopCompetition{" +
            "id=" + id +
            ", title='" + title + "'" +
            ", status='" + status + "'" +
            ", startDate='" + startDate + "'" +
            ", stopDate='" + stopDate + "'" +
            ", resultUrl='" + resultUrl + "'" +
            ", contentUrl='" + contentUrl + "'" +
            ", assetDir='" + assetDir + "'" +
            ", articleType='" + articleType + "'" +
            ", competitionType='" + competitionType + "'" +
            ", remark='" + remark + "'" +
            '}';
    }
}
