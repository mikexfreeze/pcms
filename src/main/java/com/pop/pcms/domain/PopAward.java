package com.pop.pcms.domain;

import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

import com.pop.pcms.domain.enumeration.AwardStatus;

/**
 * 获奖记录
 */
@ApiModel(description = "获奖记录")
@Entity
@Table(name = "pop_award")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PopAward implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "content")
    private String content;

    @Column(name = "background")
    private String background;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private AwardStatus status;

    @ManyToOne
    private PopContribute contribute;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public PopAward name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public PopAward content(String content) {
        this.content = content;
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getBackground() {
        return background;
    }

    public PopAward background(String background) {
        this.background = background;
        return this;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public AwardStatus getStatus() {
        return status;
    }

    public PopAward status(AwardStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(AwardStatus status) {
        this.status = status;
    }

    public PopContribute getContribute() {
        return contribute;
    }

    public PopAward contribute(PopContribute popContribute) {
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
        PopAward popAward = (PopAward) o;
        if (popAward.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, popAward.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PopAward{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", content='" + content + "'" +
            ", background='" + background + "'" +
            ", status='" + status + "'" +
            '}';
    }
}
