package com.zhw.blog.model;

import com.zhw.blog.util.DateUtils;

import java.io.Serializable;
import java.util.Date;

public class Article implements Serializable {
    private String articleId;
    private String title;
    private String content;
    // 是否显示 Y-是；N-否
    private String isShow;
    private Date createTime;
    private String createTimeString;
    private Date updateTime;
    private String updateTimeString;

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIsShow() {
        return isShow;
    }

    public void setIsShow(String isShow) {
        this.isShow = isShow;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        if (createTime != null) {
            this.createTimeString = DateUtils.formatDate(createTime, DateUtils.FORMAT_FULL);
        }
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        if (updateTime != null) {
            this.updateTimeString = DateUtils.formatDate(updateTime, DateUtils.FORMAT_FULL);
        }
        this.updateTime = updateTime;
    }

    public String getCreateTimeString() {
        return createTimeString;
    }

    public void setCreateTimeString(String createTimeString) {
        this.createTimeString = createTimeString;
    }

    public String getUpdateTimeString() {
        return updateTimeString;
    }

    public void setUpdateTimeString(String updateTimeString) {
        this.updateTimeString = updateTimeString;
    }
}
