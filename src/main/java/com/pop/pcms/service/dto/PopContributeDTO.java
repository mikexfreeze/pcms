package com.pop.pcms.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Objects;
import com.pop.pcms.domain.enumeration.ContributeType;
import com.pop.pcms.domain.enumeration.ContributeStatus;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;

/**
 * A DTO for the PopContribute entity.
 */
public class PopContributeDTO implements Serializable {

    @NotNull(message = "[id]不能为空!",profiles = "pf1")
    @NotEmpty(message = "[id]不能为空!",profiles = "pf1")
    private Long id;

    private ContributeType contributeType;

    @NotNull(message = "[title]不能为空!",profiles = "pf2")
    @NotEmpty(message = "[title]不能为空!",profiles = "pf2")
    private String title;

    private String assetDir;

    private ContributeStatus status;

    @NotNull(message = "[author]不能为空!",profiles = "pf3")
    @NotEmpty(message = "[author]不能为空!",profiles = "pf3")
    private Long author;

    @NotNull(message = "[subjectId]不能为空!",profiles = "pf4")
    @NotEmpty(message = "[subjectId]不能为空!",profiles = "pf4")
    private Long subjectId;

    //作品列表
    private List<PopPictureDTO> pictList;

    //活动ID
    @NotNull(message = "[competitionId]不能为空!",profiles = "pf5")
    @NotEmpty(message = "[competitionId]不能为空!",profiles = "pf5")
    private Long competitionId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public ContributeType getContributeType() {
        return contributeType;
    }

    public void setContributeType(ContributeType contributeType) {
        this.contributeType = contributeType;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public String getAssetDir() {
        return assetDir;
    }

    public void setAssetDir(String assetDir) {
        this.assetDir = assetDir;
    }
    public ContributeStatus getStatus() {
        return status;
    }

    public void setStatus(ContributeStatus status) {
        this.status = status;
    }
    public Long getAuthor() {
        return author;
    }

    public void setAuthor(Long author) {
        this.author = author;
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

        PopContributeDTO popContributeDTO = (PopContributeDTO) o;

        if ( ! Objects.equals(id, popContributeDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PopContributeDTO{" +
            "id=" + id +
            ", contributeType='" + contributeType + "'" +
            ", title='" + title + "'" +
            ", assetDir='" + assetDir + "'" +
            ", status='" + status + "'" +
            ", author='" + author + "'" +
            '}';
    }

    public List<PopPictureDTO> getPictList() {
        return pictList;
    }

    public void setPictList(List<PopPictureDTO> pictList) {
        this.pictList = pictList;
    }

    public Long getCompetitionId() {
        return competitionId;
    }

    public void setCompetitionId(Long competitionId) {
        this.competitionId = competitionId;
    }
}
