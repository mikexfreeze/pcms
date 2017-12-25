package com.pop.pcms.domain;

import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * 奖项设置
 */
@ApiModel(description = "奖项设置")
@Entity
@Table(name = "pop_award_config")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PopAwardConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "amount")
    private Integer amount;

    @Column(name = "background")
    private String background;

    @Column(name = "template")
    private String template;

    @ManyToOne
    private PopSubject subject;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public PopAwardConfig name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAmount() {
        return amount;
    }

    public PopAwardConfig amount(Integer amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getBackground() {
        return background;
    }

    public PopAwardConfig background(String background) {
        this.background = background;
        return this;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getTemplate() {
        return template;
    }

    public PopAwardConfig template(String template) {
        this.template = template;
        return this;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public PopSubject getSubject() {
        return subject;
    }

    public PopAwardConfig subject(PopSubject popSubject) {
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
        PopAwardConfig popAwardConfig = (PopAwardConfig) o;
        if (popAwardConfig.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, popAwardConfig.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PopAwardConfig{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", amount='" + amount + "'" +
            ", background='" + background + "'" +
            ", template='" + template + "'" +
            '}';
    }
}
