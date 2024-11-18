package com.influence.cm.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class SysComment implements Serializable {

    private static final long serialVersionUID = 1L;

    // Comment ID
    private Long commentId;

    // The content of the comment
    @NotBlank(message = "Comment content cannot be empty")
    private String commentContent;

    // The user or author of the comment
    @NotBlank(message = "Author name cannot be empty")
    private String author;

    // The ID of the content being commented on (e.g., post ID, article ID)
    @NotNull(message = "Content ID cannot be null")
    private Long contentId;

    // The date and time when the comment was created
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Shanghai")
    private Date createdAt;

    // Optional field: The ID of the parent comment (if it's a reply)
    private Long parentCommentId;

    // Add this field
    private Long userId;

    // Add this field
    private String status;

    // Add this field
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Shanghai")
    private Date updatedAt;

    public SysComment() {
    }

    public SysComment(Long commentId, String commentContent, String author, Long contentId, Date createdAt, Long parentCommentId, Long userId, String status, Date updatedAt) {
        this.commentId = commentId;
        this.commentContent = commentContent;
        this.author = author;
        this.contentId = contentId;
        this.createdAt = createdAt;
        this.parentCommentId = parentCommentId;
        this.userId = userId;
        this.status = status;
        this.updatedAt = updatedAt;
    }

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
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

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Long getParentCommentId() {
        return parentCommentId;
    }

    public void setParentCommentId(Long parentCommentId) {
        this.parentCommentId = parentCommentId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SysComment that = (SysComment) o;
        return Objects.equals(commentId, that.commentId) &&
                Objects.equals(commentContent, that.commentContent) &&
                Objects.equals(author, that.author) &&
                Objects.equals(contentId, that.contentId) &&
                Objects.equals(createdAt, that.createdAt) &&
                Objects.equals(parentCommentId, that.parentCommentId) &&
                Objects.equals(userId, that.userId) &&
                Objects.equals(status, that.status) &&
                Objects.equals(updatedAt, that.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(commentId, commentContent, author, contentId, createdAt, parentCommentId, userId, status, updatedAt);
    }

    @Override
    public String toString() {
        return "SysComment{" +
                "commentId=" + commentId +
                ", commentContent='" + commentContent + '\'' +
                ", author='" + author + '\'' +
                ", contentId=" + contentId +
                ", createdAt=" + createdAt +
                ", parentCommentId=" + parentCommentId +
                ", userId=" + userId +
                ", status='" + status + '\'' +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
