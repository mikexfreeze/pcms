package com.pop.pcms.service.dto;

import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;
import com.pop.pcms.domain.enumeration.CompetitionStatus;
import com.pop.pcms.domain.enumeration.ArticleType;
import com.pop.pcms.domain.enumeration.CompetitionType;
import net.sf.oval.constraint.Length;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;


/**
 * A DTO for the PopCompetition entity.
 */
public class PopCompetitionDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "[id]不能为空!",profiles = "pf1")
    @NotEmpty(message = "[id]不能为空!",profiles = "pf1")
    private Long id;

    @NotNull(message = "主题名称[title]不能为空!",profiles = "pf2")
    @NotEmpty(message = "主题名称[title]不能为空!",profiles = "pf2")
    @Length(message = "主题名称[title]长度为不能超过256!", max = 256,profiles = "pf2")
    private String title;

    @NotNull(message = "活动状态[status]不能为空!",profiles = "pf3")
    @NotEmpty(message = "活动状态[status]不能为空!",profiles = "pf3")
    private CompetitionStatus status;

    @NotNull(message = "开始时间[startDate]不能为空!",profiles = "pf4")
    @NotEmpty(message = "开始时间[startDate]不能为空!",profiles = "pf4")
    private LocalDate startDate;

    @NotNull(message = "结束时间[stopDate]不能为空!",profiles = "pf5")
    @NotEmpty(message = "结束时间[stopDate]不能为空!",profiles = "pf5")
    private LocalDate stopDate;

    @Length(message = "结果页地址[resultUrl]长度为不能超过256!", max = 256)
    private String resultUrl;

    @Length(message = "征稿页URL[contentUrl]长度为不能超过256!", max = 256)
    private String contentUrl;

    @Length(message = "作品存放地址[assetDir]长度为不能超过256!", max = 256)
    private String assetDir;


    private ArticleType articleType;

    private CompetitionType competitionType;

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

    public void setTitle(String title) {
        this.title = title;
    }
    public CompetitionStatus getStatus() {
        return status;
    }

    public void setStatus(CompetitionStatus status) {
        this.status = status;
    }
    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
    public LocalDate getStopDate() {
        return stopDate;
    }

    public void setStopDate(LocalDate stopDate) {
        this.stopDate = stopDate;
    }
    public String getResultUrl() {
        return resultUrl;
    }

    public void setResultUrl(String resultUrl) {
        this.resultUrl = resultUrl;
    }
    public String getContentUrl() {
        return contentUrl;
    }

    public void setContentUrl(String contentUrl) {
        this.contentUrl = contentUrl;
    }
    public String getAssetDir() {
        return assetDir;
    }

    public void setAssetDir(String assetDir) {
        this.assetDir = assetDir;
    }
    public ArticleType getArticleType() {
        return articleType;
    }

    public void setArticleType(ArticleType articleType) {
        this.articleType = articleType;
    }
    public CompetitionType getCompetitionType() {
        return competitionType;
    }

    public void setCompetitionType(CompetitionType competitionType) {
        this.competitionType = competitionType;
    }
    public String getRemark() {
        return remark;
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

        PopCompetitionDTO popCompetitionDTO = (PopCompetitionDTO) o;

        if ( ! Objects.equals(id, popCompetitionDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PopCompetitionDTO{" +
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
