package com.pop.pcms.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import com.pop.pcms.domain.enumeration.AwardStatus;

/**
 * A DTO for the PopAward entity.
 */
public class PopAwardDTO implements Serializable {

    private Long id;

    private String name;

    private String content;

    private String background;

    private AwardStatus status;

    private Long contributeId;

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
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }
    public AwardStatus getStatus() {
        return status;
    }

    public void setStatus(AwardStatus status) {
        this.status = status;
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

        PopAwardDTO popAwardDTO = (PopAwardDTO) o;

        if ( ! Objects.equals(id, popAwardDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PopAwardDTO{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", content='" + content + "'" +
            ", background='" + background + "'" +
            ", status='" + status + "'" +
            '}';
    }
}
