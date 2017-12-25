package com.pop.pcms.domain;

import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * 观察员
 */
@ApiModel(description = "观察员")
@Entity
@Table(name = "pop_observer")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PopObserver implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "user_name")
    private String userName;

    @ManyToOne
    private PopAppraise appraise;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public PopObserver userId(Long userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public PopObserver userName(String userName) {
        this.userName = userName;
        return this;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public PopAppraise getAppraise() {
        return appraise;
    }

    public PopObserver appraise(PopAppraise popAppraise) {
        this.appraise = popAppraise;
        return this;
    }

    public void setAppraise(PopAppraise popAppraise) {
        this.appraise = popAppraise;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PopObserver popObserver = (PopObserver) o;
        if (popObserver.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, popObserver.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PopObserver{" +
            "id=" + id +
            ", userId='" + userId + "'" +
            ", userName='" + userName + "'" +
            '}';
    }
}
