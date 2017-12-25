package com.pop.pcms.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the PopObserver entity.
 */
public class PopObserverDTO implements Serializable {

    private Long id;

    private Long userId;

    private String userName;

    private Long appraiseId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getAppraiseId() {
        return appraiseId;
    }

    public void setAppraiseId(Long popAppraiseId) {
        this.appraiseId = popAppraiseId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PopObserverDTO popObserverDTO = (PopObserverDTO) o;

        if ( ! Objects.equals(id, popObserverDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PopObserverDTO{" +
            "id=" + id +
            ", userId='" + userId + "'" +
            ", userName='" + userName + "'" +
            '}';
    }
}
