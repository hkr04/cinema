package com.influence.cm.domain.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * 封装评论查询的条件
 */
public class SysCommentVo implements Serializable {

    // The content of the comment (can be partial search)
    private String commentContent;

    // The author of the comment (can be partial search)
    private String author;

    // The ID of the content being commented on (e.g., post or movie ID)
    private Long contentId;

    // The start date for filtering comments (optional)
    private Date startDate;

    // The end date for filtering comments (optional)
    private Date endDate;

    // The ID of the user who made the comment (added missing property)
    private Long userId;

    // The status of the comment (added missing property)
    private String status;

    public SysCommentVo() {
    }

    public SysCommentVo(String commentContent, String author, Long contentId, Date startDate, Date endDate, Long userId, String status) {
        this.commentContent = commentContent;
        this.author = author;
        this.contentId = contentId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.userId = userId;
        this.status = status;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Long getContentId() {
        return contentId;
    }

    public void setContentId(Long contentId) {
        this.contentId = contentId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    // Getter and Setter for userId
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    // Getter and Setter for status
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SysCommentVo that = (SysCommentVo) o;
        return Objects.equals(commentContent, that.commentContent) &&
                Objects.equals(author, that.author) &&
                Objects.equals(contentId, that.contentId) &&
                Objects.equals(startDate, that.startDate) &&
                Objects.equals(endDate, that.endDate) &&
                Objects.equals(userId, that.userId) &&
                Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(commentContent, author, contentId, startDate, endDate, userId, status);
    }

    @Override
    public String toString() {
        return "SysCommentVo{" +
                "commentContent='" + commentContent + '\'' +
                ", author='" + author + '\'' +
                ", contentId=" + contentId +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", userId=" + userId +
                ", status='" + status + '\'' +
                '}';
    }
}
