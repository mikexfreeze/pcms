package com.pop.pcms.service.dto;

import java.time.LocalDate;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the PopPicture entity.
 */
public class PopPictureDTO implements Serializable {

    private Long id;

    private String picPath;

    private String remark;

    private String shootAddress;

    private LocalDate shootDate;

    private Long contributeId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
    public String getShootAddress() {
        return shootAddress;
    }

    public void setShootAddress(String shootAddress) {
        this.shootAddress = shootAddress;
    }
    public LocalDate getShootDate() {
        return shootDate;
    }

    public void setShootDate(LocalDate shootDate) {
        this.shootDate = shootDate;
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

        PopPictureDTO popPictureDTO = (PopPictureDTO) o;

        if ( ! Objects.equals(id, popPictureDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PopPictureDTO{" +
            "id=" + id +
            ", picPath='" + picPath + "'" +
            ", remark='" + remark + "'" +
            ", shootAddress='" + shootAddress + "'" +
            ", shootDate='" + shootDate + "'" +
            '}';
    }
}
