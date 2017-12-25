package com.pop.pcms.service.dto;


import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the PopAwardConfig entity.
 */
public class PopAwardConfigDTO implements Serializable {

    @NotNull(message = "[id]不能为空!",profiles = "pf2")
    @NotEmpty(message = "[id]不能为空!",profiles = "pf2")
    private Long id;

    @NotNull(message = "奖项名称[name]不能为空!",profiles = "pf1")
    @NotEmpty(message = "奖项名称[name]不能为空!",profiles = "pf1")
    private String name;

    private Integer amount;

    private String background;

    private String template;

    @NotNull(message = "主题ID[subjectId]不能为空!",profiles = "pf3")
    @NotEmpty(message = "主题ID[subjectId]不能为空!",profiles = "pf3")
    private Long subjectId;

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
    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }
    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
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

        PopAwardConfigDTO popAwardConfigDTO = (PopAwardConfigDTO) o;

        if ( ! Objects.equals(id, popAwardConfigDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PopAwardConfigDTO{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", amount='" + amount + "'" +
            ", background='" + background + "'" +
            ", template='" + template + "'" +
            '}';
    }
}
