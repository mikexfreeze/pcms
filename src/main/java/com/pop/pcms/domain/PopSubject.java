package com.pop.pcms.domain;

import com.pop.pcms.domain.enumeration.PopSubjectStatus;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * 活动主题
 */
@ApiModel(description = "活动主题")
@Entity
@Table(name = "pop_subject")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PopSubject implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "max_limit")
    private Long maxLimit;

    @Column(name = "group_max_limit")
    private Long groupMaxLimit;

    @Column(name = "asset_dir")
    private String assetDir;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private PopSubjectStatus status;

    @ManyToOne
    private PopCompetition competition;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public PopSubject name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getMaxLimit() {
        return maxLimit;
    }

    public PopSubject maxLimit(Long maxLimit) {
        this.maxLimit = maxLimit;
        return this;
    }

    public void setMaxLimit(Long maxLimit) {
        this.maxLimit = maxLimit;
    }

    public Long getGroupMaxLimit() {
        return groupMaxLimit;
    }

    public PopSubject groupMaxLimit(Long groupMaxLimit) {
        this.groupMaxLimit = groupMaxLimit;
        return this;
    }

    public void setGroupMaxLimit(Long groupMaxLimit) {
        this.groupMaxLimit = groupMaxLimit;
    }

    public String getAssetDir() {
        return assetDir;
    }

    public PopSubject assetDir(String assetDir) {
        this.assetDir = assetDir;
        return this;
    }

    public void setAssetDir(String assetDir) {
        this.assetDir = assetDir;
    }

    public PopSubjectStatus getStatus() {
        return status;
    }

    public PopSubject status(PopSubjectStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(PopSubjectStatus status) {
        this.status = status;
    }

    public PopCompetition getCompetition() {
        return competition;
    }

    public PopSubject competition(PopCompetition popCompetition) {
        this.competition = popCompetition;
        return this;
    }

    public void setCompetition(PopCompetition popCompetition) {
        this.competition = popCompetition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PopSubject popSubject = (PopSubject) o;
        if (popSubject.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, popSubject.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PopSubject{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", maxLimit='" + maxLimit + "'" +
            ", groupMaxLimit='" + groupMaxLimit + "'" +
            ", assetDir='" + assetDir + "'" +
            ", status='" + status + "'" +
            '}';
    }
}
