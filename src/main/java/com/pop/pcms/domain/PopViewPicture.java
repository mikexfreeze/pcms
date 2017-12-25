package com.pop.pcms.domain;

/**
 * Created by zhangjinye on 2017/3/7.
 */
public class PopViewPicture extends PopPicture {

    private String title;
    private String name;
    private Long contributeId;
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getContributeId() {
        return contributeId;
    }

    public void setContributeId(Long contributeId) {
        this.contributeId = contributeId;
    }
}
