package com.pop.pcms.domain;

import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * 投稿照片
 */
@ApiModel(description = "投稿照片")
@Entity
@Table(name = "pop_picture")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PopPicture implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "pic_path")
    private String picPath;

    @Column(name = "remark")
    private String remark;

    @Column(name = "shoot_address")
    private String shootAddress;

    @Column(name = "shoot_date")
    private LocalDate shootDate;

    @ManyToOne
    private PopContribute contribute;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPicPath() {
        return picPath;
    }

    public PopPicture picPath(String picPath) {
        this.picPath = picPath;
        return this;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    public String getRemark() {
        return remark;
    }

    public PopPicture remark(String remark) {
        this.remark = remark;
        return this;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getShootAddress() {
        return shootAddress;
    }

    public PopPicture shootAddress(String shootAddress) {
        this.shootAddress = shootAddress;
        return this;
    }

    public void setShootAddress(String shootAddress) {
        this.shootAddress = shootAddress;
    }

    public LocalDate getShootDate() {
        return shootDate;
    }

    public PopPicture shootDate(LocalDate shootDate) {
        this.shootDate = shootDate;
        return this;
    }

    public void setShootDate(LocalDate shootDate) {
        this.shootDate = shootDate;
    }

    public PopContribute getContribute() {
        return contribute;
    }

    public PopPicture contribute(PopContribute popContribute) {
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
        PopPicture popPicture = (PopPicture) o;
        if (popPicture.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, popPicture.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PopPicture{" +
            "id=" + id +
            ", picPath='" + picPath + "'" +
            ", remark='" + remark + "'" +
            ", shootAddress='" + shootAddress + "'" +
            ", shootDate='" + shootDate + "'" +
            '}';
    }
}
