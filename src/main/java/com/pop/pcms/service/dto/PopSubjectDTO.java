package com.pop.pcms.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import com.pop.pcms.domain.enumeration.PopSubjectStatus;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;

/**
 * A DTO for the PopSubject entity.
 */
public class PopSubjectDTO implements Serializable {

    @NotNull(message = "[id]不能为空!",profiles = "pf1")
    @NotEmpty(message = "[id]不能为空!",profiles = "pf1")
    private Long id;

    @NotNull(message = "主题名称[name]不能为空!",profiles = "pf2")
    @NotEmpty(message = "主题名称[name]不能为空!",profiles = "pf2")
    private String name;

    @NotNull(message = "限制作品数量[maxLimit]不能为空!",profiles = "pf3")
    @NotEmpty(message = "限制作品数量[maxLimit]不能为空!",profiles = "pf3")
    private Long maxLimit;

    @NotNull(message = "限制组照数量[groupMaxLimit]不能为空!",profiles = "pf4")
    @NotEmpty(message = "限制组照数量[groupMaxLimit]不能为空!",profiles = "pf4")
    private Long groupMaxLimit;

    private String assetDir;

    private PopSubjectStatus status;

    @NotNull(message = "活动ID[competitionId]不能为空!",profiles = "pf5")
    @NotEmpty(message = "活动ID[competitionId]不能为空!",profiles = "pf5")
    private Long competitionId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public Long getMaxLimit() {
        return maxLimit;
    }

    public void setMaxLimit(Long maxLimit) {
        this.maxLimit = maxLimit;
    }
    public Long getGroupMaxLimit() {
        return groupMaxLimit;
    }

    public void setGroupMaxLimit(Long groupMaxLimit) {
        this.groupMaxLimit = groupMaxLimit;
    }
    public String getAssetDir() {
        return assetDir;
    }

    public void setAssetDir(String assetDir) {
        this.assetDir = assetDir;
    }
    public PopSubjectStatus getStatus() {
        return status;
    }

    public void setStatus(PopSubjectStatus status) {
        this.status = status;
    }

    public Long getCompetitionId() {
        return competitionId;
    }

    public void setCompetitionId(Long popCompetitionId) {
        this.competitionId = popCompetitionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PopSubjectDTO popSubjectDTO = (PopSubjectDTO) o;

        if ( ! Objects.equals(id, popSubjectDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PopSubjectDTO{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", maxLimit='" + maxLimit + "'" +
            ", groupMaxLimit='" + groupMaxLimit + "'" +
            ", assetDir='" + assetDir + "'" +
            ", status='" + status + "'" +
            '}';
    }
}
