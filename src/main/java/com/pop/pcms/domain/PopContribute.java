package com.pop.pcms.domain;

import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

import com.pop.pcms.domain.enumeration.ContributeType;

import com.pop.pcms.domain.enumeration.ContributeStatus;

/**
 * 投稿
 */
@ApiModel(description = "投稿")
@Entity
@Table(name = "pop_contribute")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PopContribute implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "contribute_type")
    private ContributeType contributeType;

    @Column(name = "title")
    private String title;

    @Column(name = "asset_dir")
    private String assetDir;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ContributeStatus status;

    @Column(name = "author")
    private Long author;

    @ManyToOne
    private PopSubject subject;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ContributeType getContributeType() {
        return contributeType;
    }

    public PopContribute contributeType(ContributeType contributeType) {
        this.contributeType = contributeType;
        return this;
    }

    public void setContributeType(ContributeType contributeType) {
        this.contributeType = contributeType;
    }

    public String getTitle() {
        return title;
    }

    public PopContribute title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAssetDir() {
        return assetDir;
    }

    public PopContribute assetDir(String assetDir) {
        this.assetDir = assetDir;
        return this;
    }

    public void setAssetDir(String assetDir) {
        this.assetDir = assetDir;
    }

    public ContributeStatus getStatus() {
        return status;
    }

    public PopContribute status(ContributeStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(ContributeStatus status) {
        this.status = status;
    }

    public Long getAuthor() {
        return author;
    }

    public PopContribute author(Long author) {
        this.author = author;
        return this;
    }

    public void setAuthor(Long author) {
        this.author = author;
    }

    public PopSubject getSubject() {
        return subject;
    }

    public PopContribute subject(PopSubject popSubject) {
        this.subject = popSubject;
        return this;
    }

    public void setSubject(PopSubject popSubject) {
        this.subject = popSubject;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PopContribute popContribute = (PopContribute) o;
        if (popContribute.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, popContribute.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PopContribute{" +
            "id=" + id +
            ", contributeType='" + contributeType + "'" +
            ", title='" + title + "'" +
            ", assetDir='" + assetDir + "'" +
            ", status='" + status + "'" +
            ", author='" + author + "'" +
            '}';
    }
}
