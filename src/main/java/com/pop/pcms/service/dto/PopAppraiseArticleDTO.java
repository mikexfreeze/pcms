package com.pop.pcms.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import com.pop.pcms.domain.enumeration.AppraiseArticleStatus;

/**
 * A DTO for the PopAppraiseArticle entity.
 */
public class PopAppraiseArticleDTO implements Serializable {

    private Long id;

    private AppraiseArticleStatus status;

    private String multipleFlag;

    private String multipleScore;

    private Boolean revote;

    private Long appraiseId;

    private Long contributeId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public AppraiseArticleStatus getStatus() {
        return status;
    }

    public void setStatus(AppraiseArticleStatus status) {
        this.status = status;
    }
    public String getMultipleFlag() {
        return multipleFlag;
    }

    public void setMultipleFlag(String multipleFlag) {
        this.multipleFlag = multipleFlag;
    }
    public String getMultipleScore() {
        return multipleScore;
    }

    public void setMultipleScore(String multipleScore) {
        this.multipleScore = multipleScore;
    }
    public Boolean getRevote() {
        return revote;
    }

    public void setRevote(Boolean revote) {
        this.revote = revote;
    }

    public Long getAppraiseId() {
        return appraiseId;
    }

    public void setAppraiseId(Long popAppraiseId) {
        this.appraiseId = popAppraiseId;
    }

    public Long getContributeId() {
        return contributeId;
    }

    public void setContributeId(Long popContributeId) {
        this.contributeId = popContributeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PopAppraiseArticleDTO popAppraiseArticleDTO = (PopAppraiseArticleDTO) o;

        if ( ! Objects.equals(id, popAppraiseArticleDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PopAppraiseArticleDTO{" +
            "id=" + id +
            ", status='" + status + "'" +
            ", multipleFlag='" + multipleFlag + "'" +
            ", multipleScore='" + multipleScore + "'" +
            ", revote='" + revote + "'" +
            '}';
    }
}
